package com.canopus.website.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/2/14 14:10
 * @Description:
 */
public class PageUtils {

    public static PageResult<?> assemblyPageResult(PageInfo info) {

        PageResult pageResult = new PageResult();

        Map<String, Object> pageInfo = new HashMap<String, Object>();
        //当前页
        pageInfo.put("page", info.getPageNum());
        //每页的数量
        pageInfo.put("rows", info.getPageSize());
        //总记录数
        pageInfo.put("total", info.getTotal());
        //总页数
        pageInfo.put("pages", info.getPages());
        //是否为第一页
        pageInfo.put("isFirstPage", info.isIsFirstPage());
        //是否为最后一页
        pageInfo.put("isLastPage", info.isIsLastPage());
        //是否有前一页
        pageInfo.put("hasPreviousPage", info.isHasPreviousPage());
        //是否有下一页
        pageInfo.put("hasNextPage", info.isHasNextPage());
        pageResult.setPageInfo(pageInfo);
        pageResult.setList(info.getList());

        return pageResult;
    }
}
