package com.canopus.website.api.mq.rocket;

import lombok.Data;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:10
 * @Description:
 */
@Data
public class BaseTopic implements Topic{
    private String topic;
    private String tags;
    private String desc;
}
