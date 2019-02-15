package com.canopus.website.api.mq.rocket;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.utils.HessianUtil;
import com.canopus.website.api.utils.HostUtil;
import com.canopus.website.api.utils.RocketMqUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:11
 * @Description:
 */
//@Slf4j
//@Service("msgSender")
public class MsgSenderService implements MsgSender{

//    private DefaultMQProducer producer;
//    @Value("${rocketmq.namesrv.domain}")
//    private String nameServer;
//    private boolean test = false;
//
//    public MsgSenderService() {
//    }
//
//    @PostConstruct
//    public void init() {
//        if (!StringUtils.isBlank(this.nameServer) && !"null".equalsIgnoreCase(this.nameServer) && !"localTest".equalsIgnoreCase(this.nameServer)) {
//            this.producer = new DefaultMQProducer("isesol");
//
//            try {
//                this.producer.setNamesrvAddr(this.nameServer);
//                this.producer.setInstanceName("DEFAULT_MSG_SENDER-" + HostUtil.getHostName());
//                this.producer.setVipChannelEnabled(false);
//                this.producer.setSendMessageWithVIPChannel(false);
//                this.producer.start();
//                log.info("MQ 发送端启动成功,rocketmq.namesrv.domain={}", this.nameServer);
//            } catch (MQClientException var2) {
//                log.error("MQ 发送端启动失败!nameServer={},excepton={} ", new Object[]{this.nameServer, var2.getMessage(), var2});
//            }
//
//        } else {
//            this.test = true;
//        }
//    }
//
//    public SendResult sendMessage(Serializable message, Class<? extends Topic> topicType) {
//        Topic topic = RocketMqUtils.getTopic(topicType);
//        return this.sendMessage(message, topic.getTopic(), topic.getTags());
//    }
//
//    public SendResult sendMessage(Serializable message, String topic, String tag) {
//        SendResult sendResult = new SendResult();
//        try {
//            if (this.test) {
//                sendResult.setSendStatus(SendStatus.SEND_OK);
//            } else {
//                log.debug("发送MQ消息, message={}", message.toString());
//                Message msg = new Message(topic, tag, HessianUtil.encode(message));
//                sendResult = this.producer.send(msg);
//                log.info("sendResult={},topic={},tags={},msgId={}", new Object[]{sendResult.getSendStatus(), topic, tag, sendResult.getMsgId()});
//                if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
//                    log.warn("MQ send fail,topic={},tags={},msgId=", new Object[]{sendResult.getSendStatus(), topic, tag, sendResult.getMsgId()});
//                }
//            }
//        } catch (Exception e) {
//            throw new ServiceException(e.getMessage());
//        }
//        return sendResult;
//    }
//
//    @PreDestroy
//    public void destroy() {
//        if (this.producer != null) {
//            this.producer.shutdown();
//            log.info("MQ 发送端关闭成功;rocketmq.namesrv.domain={}", this.nameServer);
//        }
//
//    }
}
