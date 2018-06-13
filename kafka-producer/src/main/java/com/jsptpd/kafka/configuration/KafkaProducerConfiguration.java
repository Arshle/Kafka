/*
 * FileName: KafkaProducerConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka配置类
 */
package com.jsptpd.kafka.configuration;

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
public class KafkaProducerConfiguration {
    /**
     * kafka连接地址
     */
    @Value("${kafka.connect.addr:\"\"}")
    private String kafkaAddr;
    /**
     * zookeeper连接地址
     */
    @Value("${zookeeper.connect.addr:\"\"}")
    private String zkAddr;
    /**
     * zookeeper会话超时时间
     */
    @Value("${zookeeper.session.timeout:30000}")
    private int zkSessionTimeout;
    /**
     * zookeeper连接超时时间
     */
    @Value("${zookeeper.connect.timeout:30000}")
    private int zkConnectTimeout;
    /**
     * kafka消息key编码
     */
    @Value("${kafka.key.serializer:org.apache.kafka.common.serialization.StringSerializer}")
    private String keySerializer;
    /**
     * kafka消息value编码
     */
    @Value("${kafka.value.serializer:org.apache.kafka.common.serialization.StringSerializer}")
    private String valueSerializer;
    /**
     * kafka默认主题名称
     */
    @Value("${kafka.topic.default:jsptpd-kafka}")
    private String defaultTopic;
    /**
     * kafka自动冲刷
     */
    @Value("${kafka.autoFlush:true}")
    private boolean autoFlush;
    /**
     * kafka消息确认机制
     */
    @Value("${kafka.acks:1}")
    private String acks;
    /**
     * 批处理消息大小
     */
    @Value("${kafka.batch.size:16384}")
    private int batchSize;
    /**
     * 生产者缓冲区大小
     */
    @Value("${kafka.buffer.memory:33554432}")
    private long bufferMemory;
    /**
     * 生产者标识
     */
    @Value("${kafka.client.id:\"\"}")
    private String clientId;
    /**
     * 压缩类型
     */
    @Value("${kafka.compress.type:none}")
    private String compressType;
    /**
     * 连接空闲时间
     */
    @Value("${kafka.connections.max.idle.ms:540000}")
    private long connectMaxIdleTime;
    /**
     * 消息延迟发送时间
     */
    @Value("${kafka.linger.ms:0}")
    private long lingerTime;
    /**
     * 缓冲区不足时的阻塞时间
     */
    @Value("${kafka.max.block.ms:60000}")
    private int maxBlockTime;
    /**
     * 请求最大字节数
     */
    @Value("${kafka.max.request.size:1048576}")
    private int maxRequestSize;
    /**
     * 分区策略类名称
     */
    @Value("${kafka.partitioner.class:org.apache.kafka.clients.producer.internals.DefaultPartitioner}")
    private String partitionerClass;
    /**
     * socket接受缓冲空间大小
     */
    @Value("${kafka.receive.buffer.bytes:32768}")
    private int receiveBufferBytes;
    /**
     * 请求等待响应时间
     */
    @Value("${kafka.request.timeout.ms:30000}")
    private int requestTimeout;
    /**
     * 重试发送消息次数
     */
    @Value("${kafka.retries:0}")
    private int retries;
    /**
     * 每条消息发送顺序保证
     */
    @Value("${kafka.max.in.flight.requests.per.connection:5}")
    private int maxInFlightRequestsPerConnection;
    /**
     * 失败重试间隔时间
     */
    @Value("${kafka.retry.backoff.ms:100}")
    private long retryBackOffTime;
    /**
     * 并发发送消息数量
     */
    @Value("${kafka.concurrency.send:5}")
    private int concurrencySend;
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

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public long getBufferMemory() {
        return bufferMemory;
    }

    public void setBufferMemory(long bufferMemory) {
        this.bufferMemory = bufferMemory;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCompressType() {
        return compressType;
    }

    public void setCompressType(String compressType) {
        this.compressType = compressType;
    }

    public long getConnectMaxIdleTime() {
        return connectMaxIdleTime;
    }

    public void setConnectMaxIdleTime(long connectMaxIdleTime) {
        this.connectMaxIdleTime = connectMaxIdleTime;
    }

    public long getLingerTime() {
        return lingerTime;
    }

    public void setLingerTime(long lingerTime) {
        this.lingerTime = lingerTime;
    }

    public int getMaxBlockTime() {
        return maxBlockTime;
    }

    public void setMaxBlockTime(int maxBlockTime) {
        this.maxBlockTime = maxBlockTime;
    }

    public int getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(int maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public String getPartitionerClass() {
        return partitionerClass;
    }

    public void setPartitionerClass(String partitionerClass) {
        this.partitionerClass = partitionerClass;
    }

    public int getReceiveBufferBytes() {
        return receiveBufferBytes;
    }

    public void setReceiveBufferBytes(int receiveBufferBytes) {
        this.receiveBufferBytes = receiveBufferBytes;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getMaxInFlightRequestsPerConnection() {
        return maxInFlightRequestsPerConnection;
    }

    public void setMaxInFlightRequestsPerConnection(int maxInFlightRequestsPerConnection) {
        this.maxInFlightRequestsPerConnection = maxInFlightRequestsPerConnection;
    }

    public long getRetryBackOffTime() {
        return retryBackOffTime;
    }

    public void setRetryBackOffTime(long retryBackOffTime) {
        this.retryBackOffTime = retryBackOffTime;
    }

    public int getConcurrencySend() {
        return concurrencySend;
    }

    public void setConcurrencySend(int concurrencySend) {
        this.concurrencySend = concurrencySend;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
