/*
 * FileName: TestKafkaListener.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description:
 */
package com.jsptpd.nbiot.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsptpd.kafka.annotation.KafkaListener;
import com.jsptpd.kafka.intf.KafkaMessageListener;
import com.jsptpd.nbiot.entity.UpdateDevDataNotify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@KafkaListener(topic = "iotsz",group = "test")
public class TestKafkaListener implements KafkaMessageListener<UpdateDevDataNotify> {

    private static final AtomicInteger COUNT = new AtomicInteger();

    @Override
    public void onMessage(UpdateDevDataNotify message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("接受kafka消息:" + mapper.writeValueAsString(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
