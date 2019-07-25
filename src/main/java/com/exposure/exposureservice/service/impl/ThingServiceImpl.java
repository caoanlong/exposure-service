package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.entity.ThingLabel;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.entity.req.ThingDto;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.repository.ThingRepostory;
import com.exposure.exposureservice.service.ThingService;
import com.exposure.exposureservice.utils.SnowFlake;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ThingRepostory thingRepostory;

    @Override
    public List<Thing> findAll(String title) {
        return thingRepostory.findAll(title);
    }

    @Override
    public PageBean<List<Thing>> findList(
            String title,
            Integer type,
            Integer sex,
            Date birthDay,
            String area,
            Integer pageIndex,
            Integer pageSize
    ) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<Thing> things = thingRepostory.findList(title, birthDay, type, sex, area, pageStart, pageSize);
        Long total = thingRepostory.total(title, birthDay, type, sex, area);
        PageBean<List<Thing>> pageBean = new PageBean<>();
        pageBean.setList(things);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    @Transactional
    public Thing findById(Long id) {
        thingRepostory.updateViews(id);
        return thingRepostory.findById(id);
    }

    @Override
    @Transactional
    public void insert(ThingDto thingDto) {
        if (StringUtils.isBlank(thingDto.getTitle()))
            throw new CommonException(ErrorCode.TITLE_NOTNULL);
        if (StringUtils.isBlank(thingDto.getInfo()))
            throw new CommonException(ErrorCode.INFO_NOTNULL);
        if (null == thingDto.getImages())
            throw new CommonException(ErrorCode.IMG_NOTNULL);
        Thing thing = new Thing();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> images = thingDto.getImages();
        for (int i = 0; i < images.size(); i++) {
            stringBuilder.append(images.get(i));
            if (i < images.size() - 1) {
                stringBuilder.append(",");
            }
        }
        String imagesStr = stringBuilder.toString();

        Long id = snowFlake.nextId();
        thing.setId(id);
        thing.setTitle(thingDto.getTitle());
        thing.setType(thingDto.getType());
        thing.setBirthDay(thingDto.getBirthDay());
        thing.setSex(thingDto.getSex());
        thing.setArea(thingDto.getArea());
        thing.setInfo(thingDto.getInfo());
        thing.setCreateId(thingDto.getCreateUserId());
        thing.setImages(imagesStr);
        String avatar = thingDto.getAvatar();
        if (StringUtils.isBlank(avatar)) {
            avatar = images.get(0);
        }
        thing.setAvatar(avatar);
        thing.setViews(0);
        thing.setCreateTime(new Date());
        thingRepostory.insert(thing);

        List<Long> labelIds = thingDto.getLabelIds();
        if (!labelIds.isEmpty()) {
            List<ThingLabel> thingLabels = new ArrayList<>();
            for (int i = 0; i < labelIds.size(); i++) {
                ThingLabel thingLabel = new ThingLabel();
                thingLabel.setLabelId(labelIds.get(i));
                thingLabel.setThingId(id);
                thingLabels.add(thingLabel);
            }
            thingRepostory.insertThingLabels(thingLabels);
        }
    }

    @Override
    @Transactional
    public void update(ThingDto thingDto) {
        Long id = thingDto.getId();
        if (null == id)
            throw new CommonException(ErrorCode.ID_NOTNULL);
        List<Long> labelIds = thingDto.getLabelIds();
        if (null == labelIds || labelIds.isEmpty())
            throw new CommonException(ErrorCode.LABEL_NOT_NULL);
        thingRepostory.delThingLabelByThingId(id);
        List<ThingLabel> thingLabels = new ArrayList<>();
        for (int i = 0; i < labelIds.size(); i++) {
            ThingLabel thingLabel = new ThingLabel();
            thingLabel.setLabelId(labelIds.get(i));
            thingLabel.setThingId(id);
            thingLabels.add(thingLabel);
        }
        thingRepostory.insertThingLabels(thingLabels);

        Thing thing = new Thing();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> images = thingDto.getImages();
        for (int i = 0; i < images.size(); i++) {
            stringBuilder.append(images.get(i));
            if (i < images.size() - 1) {
                stringBuilder.append(",");
            }
        }
        String imagesStr = stringBuilder.toString();
        thing.setId(id);
        thing.setTitle(thingDto.getTitle());
        thing.setType(thingDto.getType());
        thing.setBirthDay(thingDto.getBirthDay());
        thing.setSex(thingDto.getSex());
        thing.setArea(thingDto.getArea());
        thing.setInfo(thingDto.getInfo());
        thing.setUpdateId(thingDto.getUpdateUserId());
        thing.setUpdateTime(new Date());
        thing.setImages(imagesStr);
        thing.setAvatar(thingDto.getAvatar());
        thingRepostory.update(thing);
    }

    @Override
    @Transactional
    public void del(Long id) {
        if (null == id)
            throw new CommonException(ErrorCode.ID_NOTNULL);
        thingRepostory.del(id);
    }

    @Override
    public Long total(String title, Date birthDay, Integer sex, Integer type, String area) {
        return thingRepostory.total(title, birthDay, sex, type, area);
    }
}
