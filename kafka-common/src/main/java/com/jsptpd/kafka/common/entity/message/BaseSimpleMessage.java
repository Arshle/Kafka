/*
 * FileName: BaseSimpleMessage.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka基础消息
 */
package com.jsptpd.kafka.common.entity.message;

import com.jsptpd.kafka.common.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈kafka基础消息〉<br>
 * 〈kafka基础消息，非线程安全〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class BaseSimpleMessage extends AbstractBaseMessage {
    /**
     * 消息属性
     */
    private Map<String,String> properties = new HashMap<>(1);
    /**
     * 新增消息属性
     * @param propertyName 属性名称
     * @param propertyValue 属性内容
     */
    @Override
    public void addProperty(String propertyName, String propertyValue) {
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
    public String getProperty(String propertyName) {
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
    public Map<String, String> getProperties() {
        return properties;
    }
}
