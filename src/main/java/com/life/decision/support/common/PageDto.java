package com.life.decision.support.common;


import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.util.Objects;

public abstract class PageDto implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 100;

    private String orderBy;

    private String sortCol;

    private String sortType;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        if (orderBy == null) {
            if (StrUtil.isNotEmpty(sortCol)) {
                orderBy = StrUtil.toUnderlineCase(sortCol) + " " + sortType;
            }
        }
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortCol() {
        return sortCol;
    }

    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageDto pageDto = (PageDto) o;
        return Objects.equals(pageNum, pageDto.pageNum) && Objects.equals(pageSize, pageDto.pageSize) && Objects.equals(orderBy, pageDto.orderBy) && Objects.equals(sortCol, pageDto.sortCol) && Objects.equals(sortType, pageDto.sortType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, pageSize, orderBy, sortCol, sortType);
    }
}
