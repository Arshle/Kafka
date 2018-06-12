/*
 * FileName: KafkaMessage.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka消息基础接口
 */
package com.jsptpd.kafka.common.intf.message;

import java.util.Map;

/**
 * 〈kafka消息基础接口〉<br>
 * 〈具备kafka消息的具体属性〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public interface KafkaMessage<T> {
    /**
     * 新增消息属性
     * @param propertyName 属性名称
     * @param propertyValue 属性内容
     */
    void addProperty(String propertyName,T propertyValue);
    /**
     * 获取消息属性
     * @param propertyName 属性名称
     * @return 属性内容
     */
    T getProperty(String propertyName);
    /**
     * 获取消息属性
     * @return 消息属性
     */
    Map<String,T> getProperties();
}
