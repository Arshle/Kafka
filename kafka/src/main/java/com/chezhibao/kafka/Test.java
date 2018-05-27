/*
 * FileName: Test.java
 * Author:   Arshle
 * Date:     2018年05月27日
 * Description:
 */
package com.chezhibao.kafka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class Test {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:producer.xml","classpath:consumer.xml");
        KafkaTemplate<String,String> kafkaTemplate = context.getBean(KafkaTemplate.class);
        for(int i = 0; i < 100; i ++){
            kafkaTemplate.sendDefault(i + "", "testaaaaaaaaa" + i + i + i);
        }
    }
}
