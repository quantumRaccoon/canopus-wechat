package com.canopus.website.api.model;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:18
 * @Description:
 */
@Data
public class RestRequest {
    Request request;
    HttpServletRequest httpRequest;
}
