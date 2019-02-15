package com.canopus.website.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/2/14 14:11
 * @Description:
 */
@Data
public class PageResult<T> implements Serializable {

    private Map<String, Object> pageInfo = new HashMap<String, Object>(18);

    private List<T> list;
}
