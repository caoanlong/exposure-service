package com.exposure.exposureservice.controller;

import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.utils.FileUtils;
import com.exposure.exposureservice.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Api(value = "CommonController", description = "通用")
@CrossOrigin
@RestController
@RequestMapping(value = {"/admin/common", "/app/common"})
public class CommonController {
    @Value("${file.path}")
    private String filePath;

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
}
