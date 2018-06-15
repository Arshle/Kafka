/*
 * FileName: KafkaMessageListener.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description: kafka消费者接口
 */
package com.jsptpd.kafka.intf;

import com.jsptpd.kafka.common.intf.message.KafkaMessage;

/**
 * 〈kafka消费者接口〉<br>
 * 〈用于接收kafka消息〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public interface  KafkaMessageListener<K extends KafkaMessage> {
    /**
     * 接收消息
     * @param message 消息
     */
    void onMessage(K message);
}
