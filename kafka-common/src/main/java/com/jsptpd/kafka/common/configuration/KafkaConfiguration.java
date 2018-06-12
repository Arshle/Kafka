/*
 * FileName: KafkaConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka配置类
 */
package com.jsptpd.kafka.common.configuration;

import com.alibaba.fastjson.JSON;

/**
 * 〈kafka配置类〉<br>
 * 〈设置kafka配置属性〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafkaConfiguration {
    /**
     * kafka连接地址
     */
    private String kafkaAddr;
    /**
     * zookeeper连接地址
     */
    private String zkAddr;
    /**
     * zookeeper会话超时时间
     */
    private int zkSessionTimeout = 30000;
    /**
     * zookeeper连接超时时间
     */
    private int zkConnectTimeout = 30000;
    /**
     * kafka消息key编码
     */
    private String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * kafka消息value编码
     */
    private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * kafka默认主题名称
     */
    private String defaultTopic = "jsptpd-kafka";
    /**
     * kafka自动刷新
     */
    private boolean autoRefresh = true;
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

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
