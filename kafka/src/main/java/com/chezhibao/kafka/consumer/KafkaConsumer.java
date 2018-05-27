/*
 * FileName: KafkaConsumer.java
 * Author:   Arshle
 * Date:     2018年05月27日
 * Description:
 */
package com.chezhibao.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Arrays;
import java.util.Properties;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafkaConsumer {

    private static Properties properties = new Properties();

    static {
        properties.put("bootstrap.servers","192.168.3.109:9092,192.168.3.110:9092,192.168.3.111:9092");
        properties.put("group.id","test");
        properties.put("key.deserializer",StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
    }

    public static void consume(){
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("chezhibao","stock-quotation"));
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.offset() + " " + record.partition() + " " + record.key() + " " + record.value());
            }
        }
    }

    public static void main(String[] args) {
        KafkaConsumer.consume();
    }
}
