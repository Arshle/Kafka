/*
 * FileName: KafkaConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka配置类
 */
package com.jsptpd.kafka.common.configuration;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 〈kafka配置类〉<br>
 * 〈设置kafka配置属性〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("kafkaConfiguration")
public class KafkaConfiguration {
    /**
     * kafka连接地址
     */
    @Value("${kafka.connect.addr}")
    private String kafkaAddr;
    /**
     * zookeeper连接地址
     */
    @Value("${zookeeper.connect.addr}")
    private String zkAddr;
    /**
     * zookeeper会话超时时间
     */
    @Value("${zookeeper.session.timeout}")
    private int zkSessionTimeout = 30000;
    /**
     * zookeeper连接超时时间
     */
    @Value("${zookeeper.connect.timeout}")
    private int zkConnectTimeout = 30000;
    /**
     * kafka消息key编码
     */
    @Value("${kafka.key.serializer}")
    private String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * kafka消息value编码
     */
    @Value("${kafka.value.serializer}")
    private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * kafka默认主题名称
     */
    @Value("${kafka.topic.default}")
    private String defaultTopic = "jsptpd-kafka";
    /**
     * kafka自动冲刷
     */
    @Value("${kafka.autoFlush}")
    private boolean autoFlush = true;
    /**
     * Getters、Setters
     */
    public String getKafkaAddr() {
        return kafkaAddr;
    }

    public void setKafkaAddr(String kafkaAddr) {
        this.kafkaAddr = kafkaAddr;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public void setZkAddr(String zkAddr) {
        this.zkAddr = zkAddr;
    }

    public int getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(int zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public int getZkConnectTimeout() {
        return zkConnectTimeout;
    }

    public void setZkConnectTimeout(int zkConnectTimeout) {
        this.zkConnectTimeout = zkConnectTimeout;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public boolean isAutoFlush() {
        return autoFlush;
    }

    public void setAutoFlush(boolean autoFlush) {
        this.autoFlush = autoFlush;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
