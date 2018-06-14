/*
 * FileName: KafkaListener.java
 * Author:   Arshle
 * Date:     2018年06月14日
 * Description: Kafka消费者注解
 */
package com.jsptpd.kafka.annotation;

import org.springframework.stereotype.Component;

/**
 * 〈Kafka消费者注解〉<br>
 * 〈标识kafka消费者〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component
public @interface KafkaListener {
    /**
     * 主题名称
     * @return 主题名称
     */
    String topic() default "";
    /**
     * 消费者所属组
     * @return 消费者所属组
     */
    String group() default "";
    /**
     * 消费者订阅指定主题分区
     * @return 主题分区
     */
    int[] partitions() default {};
}
