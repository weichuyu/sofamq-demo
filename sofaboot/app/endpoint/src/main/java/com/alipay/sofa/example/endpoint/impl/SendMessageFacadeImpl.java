/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.alipay.sofa.example.endpoint.impl;

import com.alipay.sofa.sofamq.client.UserPropKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.sofa.example.endpoint.facade.SendMessageFacade;
import com.alipay.sofa.example.endpoint.mq.MqConfig;

import io.openmessaging.api.Message;
import io.openmessaging.api.Producer;
import io.openmessaging.api.SendResult;

@Component
public class SendMessageFacadeImpl implements SendMessageFacade {
    @Autowired
    Producer producer;
    @Autowired
    MqConfig mqConfig;

    @Override
    public String send() {
        Message message = new Message(mqConfig.getTopic(), mqConfig.getTag(), "hello world".getBytes());
        // LDC 场景如果需要 RZONE 消费消息需要设置 CELL_UID
        // message.putUserProperties(UserPropKey.CELL_UID, "XX");
        try {
            SendResult sendResult = producer.send(message);
            return sendResult.getMessageId();
        } catch (Throwable e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}