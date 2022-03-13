package com.life.decision.support.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtils {

    public static PageInfo<?> getPageResult(Integer page, Integer size, List<?> list) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(list);
    }
}
