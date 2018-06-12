/*
 * FileName: AbstractBaseMessage.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka消息基础类
 */
package com.jsptpd.kafka.common.entity.message;

import com.alibaba.fastjson.JSON;
import com.jsptpd.kafka.common.intf.message.KafkaMessage;

/**
 * 〈kafka消息基础类〉<br>
 * 〈基础消息,实现额外的消息封装〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public abstract class AbstractBaseMessage<T> implements KafkaMessage<T> {
    /**
     * 消息编号
     */
    private long messageId;
    /**
     * 消息类型
     */
    private int messageType;
    /**
     * Getters、Setters
     */
    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
