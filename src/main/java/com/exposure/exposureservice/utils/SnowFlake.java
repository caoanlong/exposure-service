package com.exposure.exposureservice.utils;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

/**
 * twitter的snowflake算法 -- java实现
 */
@Component
public class SnowFlake {
    /**
     * 起始的时间戳
     */
    private final static Long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static Integer SEQUENCE_BIT = 12; //序列号占用的位数
    private final static Integer MACHINE_BIT = 5;   //机器标识占用的位数
    private final static Integer DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static Long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static Long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static Long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static Integer MACHINE_LEFT = SEQUENCE_BIT;
    private final static Integer DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static Integer TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private Long datacenterId;  //数据中心
    private Long machineId;     //机器标识
    private Long sequence = 0L; //序列号
    private Long lastStmp = -1L;//上一次时间戳

    public SnowFlake() {
        Long datacenterId = 2L;
        Long machineId = 3L;
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized Long nextId() {
        Long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        Long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }
}
