/*
 * FileName: AbstractBaseMessage.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka消息基础类
 */
package com.jsptpd.kafka.common.entity.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsptpd.kafka.common.code.message.KafkaMessageType;
import com.jsptpd.kafka.common.intf.message.KafkaMessage;
import java.util.Date;

/**
 * 〈kafka消息基础类〉<br>
 * 〈基础消息,实现额外的消息封装〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public abstract class AbstractBaseMessage implements KafkaMessage {
    /**
     * 消息编号
     */
    private String messageId;
    /**
     * 消息类型
     */
    private KafkaMessageType messageType;
    /**
     * 创建时间
     */
    private Date creationTime = new Date();
    /**
     * Getters、Setters
     */
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public KafkaMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(KafkaMessageType messageType) {
        this.messageType = messageType;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "jackson write error.";
        }
    }
}
