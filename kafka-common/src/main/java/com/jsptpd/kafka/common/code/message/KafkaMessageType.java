/*
 * FileName: KafkaMessageType.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka消息类型
 */
package com.jsptpd.kafka.common.code.message;

/**
 * 〈kafka消息类型〉<br>
 * 〈结合具体业务消息类型统一规划〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public enum KafkaMessageType {
    /**
     * NB-IoT通信消息
     */
    NB_IOT(1,"NB-IoT通信消息"),
    LoRa(2,"LoRa通信消息"),
    ;

    KafkaMessageType(int type,String description){
        this.type = type;
        this.description = description;
    }
    /**
     * 消息类型
     */
    private int type;
    /**
     * 类型描述
     */
    private String description;
    /**
     * Getters、Setters
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
