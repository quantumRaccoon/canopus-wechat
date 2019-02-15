package com.canopus.website.api.utils;

import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.mq.rocket.Topic;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 10:33
 * @Description:
 */
public final class RocketMqUtils {
    private static final Map<Class<? extends Topic>, Topic> TOPIC_CACHE = new HashMap();

    private RocketMqUtils() {
    }

    public static Topic getTopic(Class<? extends Topic> topicType) {
        try {
            Topic topic = (Topic)TOPIC_CACHE.get(topicType);
            if (topic == null) {
                topic = (Topic)topicType.newInstance();
            }
            return topic;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
