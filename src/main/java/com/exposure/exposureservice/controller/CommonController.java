package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.AreaStatistics;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.service.AreaStatisticsService;
import com.exposure.exposureservice.service.MemberService;
import com.exposure.exposureservice.service.ThingService;
import com.exposure.exposureservice.utils.FileUtils;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(value = "CommonController", description = "通用")
@RestController
@RequestMapping(value = {"/common", "/admin/common", "/app/common"})
public class CommonController {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ThingService thingService;

    @Autowired
    private AreaStatisticsService areaStatisticsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/tokenNull")
    public ResultBean<Object> tokenNull() {
        return ResultUtils.error(ErrorCode.TOKEN_NOTNULL);
    }

    @RequestMapping("/tokenError")
    public ResultBean<Object> tokenError() {
        return ResultUtils.error(ErrorCode.TOKEN_ERROR);
    }

    @ApiOperation(value = "upload", notes = "上传图片")
    @PostMapping("/upload")
    public ResultBean<Object> imageUpload(@RequestParam("image") MultipartFile[] files) {
        List<String> uploads = new ArrayList<>();
        for (MultipartFile file: files) {
            String upload = FileUtils.upload(file, filePath, file.getOriginalFilename());
            uploads.add(upload);
        }
        if (null != uploads || uploads.isEmpty()) {
            return ResultUtils.success(uploads);
        } else {
            return ResultUtils.error(ErrorCode.IMG_UPLOAD_ERROR);
        }
    }

    @GetMapping("/total")
    public ResultBean<Object> total() {
        // 会员总数
        Long memberTotal = memberService.total(null, null, null);
        // 激活会员总数
        Long memberActiveTotal = memberService.total(null, null, 1);

        // 事物总数
        Long thingTotal = thingService.total(null, null, null, null, null);

        Set<String> keys = stringRedisTemplate.keys("isOnline:*");
        Long memberOnlineTotal = new Long((long) keys.size());

        HashMap<String, Object> total = new HashMap<>();
        total.put("memberOnlineTotal", memberOnlineTotal);
        total.put("memberTotal", memberTotal);
        total.put("memberActiveTotal", memberActiveTotal);
        total.put("thingTotal", thingTotal);

        return ResultUtils.success(total);
    }

    @GetMapping("/statistics")
    public ResultBean<Object> statistics() {
        List<AreaStatistics> all = areaStatisticsService.findAll();
        return ResultUtils.success(all);
    }
}
