package com.exposure.exposureservice.entity;

import java.io.Serializable;

public class PageBean<T> implements Serializable {
    private Integer pageIndex;
    private Integer pageSize;
    private Integer pages;
    private Long total;
    private T list;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        Float temp = (float) total / (float) this.pageSize;
        Integer pageNum = (int) Math.ceil(temp);
        this.pages = pageNum == 0 ? 1 : pageNum;
    }

    public Integer getPages() {
        return pages;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
