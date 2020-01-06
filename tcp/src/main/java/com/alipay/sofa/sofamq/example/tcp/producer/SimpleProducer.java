/**
 * Copyright (C) 2010-2016 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.sofamq.example.tcp.producer;

import java.util.Properties;

import com.alipay.sofa.sofamq.client.PropertyKeyConst;
import com.alipay.sofa.sofamq.example.tcp.MqConfig;

import io.openmessaging.api.Message;
import io.openmessaging.api.MessagingAccessPoint;
import io.openmessaging.api.OMS;
import io.openmessaging.api.OMSBuiltinKeys;
import io.openmessaging.api.Producer;
import io.openmessaging.api.SendResult;

/**
 * MQ发送普通消息示例 Demo
 */
public class SimpleProducer {

    public static void main(String[] args) {
        Properties credentials = new Properties();
        credentials.setProperty(OMSBuiltinKeys.ACCESS_KEY, MqConfig.ACCESS_KEY);
        credentials.setProperty(OMSBuiltinKeys.SECRET_KEY, MqConfig.SECRET_KEY);

        MessagingAccessPoint accessPoint = OMS.builder().driver("sofamq").endpoint(MqConfig.ENDPOINT)
            .withCredentials(credentials).build();

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.INSTANCE_ID, MqConfig.INSTANCE);
        properties.setProperty(PropertyKeyConst.GROUP_ID, MqConfig.GROUP_ID);
        Producer producer = accessPoint.createProducer(properties);
        producer.start();

        Message message = new Message("$topic", "YOUR_TAG", "hello world".getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult);
    }
}
