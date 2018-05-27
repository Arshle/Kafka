/*
 * FileName: SpringKafkaConsumerListener.java
 * Author:   Arshle
 * Date:     2018年05月27日
 * Description:
 */
package com.chezhibao.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("springKafkaConsumerListener")
public class SpringKafkaConsumerListener implements MessageListener<String,String> {

    private Logger logger = Logger.getLogger(SpringKafkaConsumerListener.class);

    @Override
    public void onMessage(ConsumerRecord<String, String> stringStringConsumerRecord) {
        if(stringStringConsumerRecord != null){
            logger.info("接受消息,主题:" + stringStringConsumerRecord.topic() + ",分区:" + stringStringConsumerRecord.partition()
            + ",消息key:" + stringStringConsumerRecord.key() + ",内容:" + stringStringConsumerRecord.value());
        }
    }
}
