/*
 * FileName: BaseConcurrencyMessage.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: 基础并发消息实体类
 */
package com.jsptpd.kafka.common.entity.message;

import com.jsptpd.kafka.common.utils.StringUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 〈基础并发消息实体类〉<br>
 * 〈线程安全的消息实体〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class BaseConcurrencyMessage<T> extends AbstractBaseMessage<T> {
    /**
     * 消息属性
     */
    ConcurrentMap<String,T> properties = new ConcurrentHashMap<>(1);
    /**
     * 新增消息属性
     * @param propertyName 属性名称
     * @param propertyValue 属性内容
     */
    @Override
    public void addMessageProperties(String propertyName, T propertyValue) {
        if(StringUtils.isEmpty(propertyName) || propertyValue == null){
            return;
        }
        this.properties.put(propertyName, propertyValue);
    }
    /**
     * 获取消息属性
     * @param propertyName 属性名称
     * @return 属性内容
     */
    @Override
    public T getMessageProperty(String propertyName) {
        if(StringUtils.isEmpty(propertyName)){
            return null;
        }
        return this.properties.get(propertyName);
    }
    /**
     * 获取消息属性
     * @return 消息属性
     */
    @Override
    public Map<String, T> messageProperties() {
        return this.properties;
    }
}
