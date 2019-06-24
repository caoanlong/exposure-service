package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.entity.req.ThingDto;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.repository.ThingRepostory;
import com.exposure.exposureservice.service.ThingService;
import com.exposure.exposureservice.utils.SnowFlake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ThingRepostory thingRepostory;

    @Override
    public List<Thing> findAll() {
        return thingRepostory.findAll();
    }

    @Override
    public PageBean<List<Thing>> findList(String title, Integer type, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<Thing> things = thingRepostory.findList(title, type, pageStart, pageSize);
        Long total = thingRepostory.total(title, type);
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
        thing.setInfo(thingDto.getInfo());
        thing.setImages(imagesStr);
        String avatar = images.get(0);
        thing.setAvatar(avatar);
        thing.setViews(0);
        thing.setCreateTime(new Date());
        thingRepostory.insert(thing);
    }

    @Override
    @Transactional
    public void update(ThingDto thingDto) {
        Long id = thingDto.getId();
        if (null == id)
            throw new CommonException(ErrorCode.ID_NOTNULL);
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
        thing.setInfo(thingDto.getInfo());
        thing.setImages(imagesStr);
        String avatar = images.get(0);
        thing.setAvatar(avatar);
        thingRepostory.update(thing);
    }

    @Override
    @Transactional
    public void del(Long id) {
        if (null == id)
            throw new CommonException(ErrorCode.ID_NOTNULL);
        thingRepostory.del(id);
    }
}
