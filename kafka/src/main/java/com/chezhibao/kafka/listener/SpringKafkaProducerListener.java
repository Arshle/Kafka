/*
 * FileName: SpringKafkaProducerListener.java
 * Author:   Arshle
 * Date:     2018年05月27日
 * Description:
 */
package com.chezhibao.kafka.listener;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("kafkaProducerListener")
public class SpringKafkaProducerListener implements ProducerListener<String,String> {

    private Logger logger = Logger.getLogger(SpringKafkaProducerListener.class);

    @Override
    public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
        logger.info("消息发送成功,主题:" + topic + ",分区:" + partition + ",消息key:" + key + ",内容:" + value);
    }

    @Override
    public void onError(String topic, Integer partition, String key, String value, Exception e) {
        logger.info("消息发送失败,主题" + topic + ",分区:" + partition + ",消息key:" + key + ",内容:" + value);
        logger.error(e.getMessage(),e);
    }

    @Override
    public boolean isInterestedInSuccess() {
        return true;
    }
}
