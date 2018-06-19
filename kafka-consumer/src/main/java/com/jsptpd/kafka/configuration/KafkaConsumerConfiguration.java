/*
 * FileName: KafkaConsumerConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月14日
 * Description: kafka消费者配置
 */
package com.jsptpd.kafka.configuration;

import com.alibaba.fastjson.JSON;
import com.jsptpd.kafka.common.utils.SpringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Properties;

/**
 * 〈kafka消费者配置〉<br>
 * 〈消费者详细配置〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("kafkaConsumerConfiguration")
public class KafkaConsumerConfiguration {
    /**
     * kafka连接地址
     */
    @Value("${kafka.connect.addr:}")
    private String kafkaAddr;
    /**
     * 消费者所属组
     */
    @Value("${kafka.group.id:}")
    private String groupId;
    /**
     * 客户端编号
     */
    @Value("${kafka.client.id:}")
    private String clientId;
    /**
     * 每次抓取消息的最大字节数
     */
    @Value("${kafka.fetch.max.bytes:5242880}")
    private int fetchMaxBytes;
    /**
     * 分区抓取消息最大字节数
     */
    @Value("${kafka.max.partition.fetch.bytes:1048576}")
    private int fetchPartitionsMaxBytes;
    /**
     * 自动提交消息偏移量
     */
    @Value("${kafka.auto.commit.enable:true}")
    private boolean autoCommitEnable;
    /**
     * 自动提交偏移量时间间隔
     */
    @Value("${kafka.auto.commit.interval.ms:5000}")
    private int autoCommitInterval;
    /**
     * 拉取消息最大时间间隔
     */
    @Value("${kafka.max.poll.interval.ms:300000}")
    private int maxPollInterval;
    /**
     * 抓取消息最大数量
     */
    @Value("${kafka.max.poll.records:500}")
    private int maxPollRecords;
    /**
     * 抓取数据返回最小字节数
     */
    @Value("${kafka.fetch.min.bytes:1}")
    private int fetchMinBytes;
    /**
     * 抓取最大等待时间
     */
    @Value("${kafka.fetch.max.wait.ms:500}")
    private int fetchMaxWait;
    /**
     * 接收消息缓冲大小
     */
    @Value("${kafka.receive.buffer.bytes:65536}")
    private int receiveBufferBytes;
    /**
     * 连接空闲时间
     */
    @Value("${kafka.connections.max.idle.ms:540000}")
    private long connectMaxIdleTime;
    /**
     * 首次订阅消费的偏移量
     */
    @Value("${kafka.auto.offset.reset:latest}")
    private String autoOffsetReset;
    /**
     * 重新连接前的时间
     */
    @Value("${kafka.reconnect.backoff.ms:50}")
    private long reconnectBackoff;
    /**
     * 是否将内部topic暴露给consumer
     */
    @Value("${kafka.exclude.internal.topics:true}")
    private boolean excludeInternalTopics;
    /**
     * 分区流分配策略
     */
    @Value("${kafka.paritition.assignment.strategy:org.apache.kafka.clients.consumer.RangeAssignor}")
    private String partitionAssignmentStrategy;
    /**
     * 重试之前时间
     */
    @Value("${kafka.retry.backoff.ms:100}")
    private long retryBackoff;
    /**
     * 心跳间隔时间
     */
    @Value("${kafka.heartbeat.interval.ms:3000}")
    private int heartbeatInterval;
    /**
     * 请求超时时间
     */
    @Value("${kafka.request.timeout.ms:305000}")
    private int requestTimeout;
    /**
     * 消息key解码类
     */
    @Value("${kafka.key.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String keyDeserializer;
    /**
     * 消息体解码类
     */
    @Value("${kafka.value.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String valueDeserializer;
    /**
     * kafka默认主题名称
     */
    @Value("${kafka.topic.default:jsptpd-kafka}")
    private String defaultTopic;
    /**
     * 消费者并发数量
     */
    @Value("${kafka.concurrency.consumer:3}")
    private int concurrencyConsumer;
    /**
     * 消费者配置
     */
    private static final Properties CONSUMER_PROPS = new Properties();
    /**
     * 获取消费者配置信息
     * @return 配置信息
     */
    public static Properties getConsumerConf(){
        if(CONSUMER_PROPS.size() == 0){
            KafkaConsumerConfiguration consumerConf = SpringUtils.getBean(KafkaConsumerConfiguration.class);
            CONSUMER_PROPS.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,consumerConf.getKafkaAddr());
            CONSUMER_PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,consumerConf.getKeyDeserializer());
            CONSUMER_PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerConf.getValueDeserializer());
            CONSUMER_PROPS.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,consumerConf.getAutoCommitInterval());
            CONSUMER_PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,consumerConf.getAutoOffsetReset());
            CONSUMER_PROPS.put(ConsumerConfig.CLIENT_ID_CONFIG,consumerConf.getClientId());
            CONSUMER_PROPS.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, consumerConf.getConnectMaxIdleTime());
            CONSUMER_PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,consumerConf.isAutoCommitEnable());
            CONSUMER_PROPS.put(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG,consumerConf.isExcludeInternalTopics());
            CONSUMER_PROPS.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG,consumerConf.getFetchMaxBytes());
            CONSUMER_PROPS.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,consumerConf.getFetchMaxWait());
            CONSUMER_PROPS.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, consumerConf.getFetchMinBytes());
            CONSUMER_PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, consumerConf.getGroupId());
            CONSUMER_PROPS.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, consumerConf.getFetchPartitionsMaxBytes());
            CONSUMER_PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, consumerConf.getMaxPollInterval());
            CONSUMER_PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, consumerConf.getHeartbeatInterval());
            CONSUMER_PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerConf.getMaxPollRecords());
            CONSUMER_PROPS.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, consumerConf.getPartitionAssignmentStrategy());
            CONSUMER_PROPS.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG, consumerConf.getReceiveBufferBytes());
            CONSUMER_PROPS.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, consumerConf.getReconnectBackoff());
            CONSUMER_PROPS.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, consumerConf.getRequestTimeout());
            CONSUMER_PROPS.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG,consumerConf.getRetryBackoff());

        }
        return CONSUMER_PROPS;
    }
    /**
     * Getters、Setters
     */
    public String getKafkaAddr() {
        return kafkaAddr;
    }

    public void setKafkaAddr(String kafkaAddr) {
        this.kafkaAddr = kafkaAddr;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getFetchMaxBytes() {
        return fetchMaxBytes;
    }

    public void setFetchMaxBytes(int fetchMaxBytes) {
        this.fetchMaxBytes = fetchMaxBytes;
    }

    public int getFetchPartitionsMaxBytes() {
        return fetchPartitionsMaxBytes;
    }

    public void setFetchPartitionsMaxBytes(int fetchPartitionsMaxBytes) {
        this.fetchPartitionsMaxBytes = fetchPartitionsMaxBytes;
    }

    public boolean isAutoCommitEnable() {
        return autoCommitEnable;
    }

    public void setAutoCommitEnable(boolean autoCommitEnable) {
        this.autoCommitEnable = autoCommitEnable;
    }

    public int getAutoCommitInterval() {
        return autoCommitInterval;
    }

    public void setAutoCommitInterval(int autoCommitInterval) {
        this.autoCommitInterval = autoCommitInterval;
    }

    public int getMaxPollInterval() {
        return maxPollInterval;
    }

    public void setMaxPollInterval(int maxPollInterval) {
        this.maxPollInterval = maxPollInterval;
    }

    public int getMaxPollRecords() {
        return maxPollRecords;
    }

    public void setMaxPollRecords(int maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
    }

    public int getFetchMinBytes() {
        return fetchMinBytes;
    }

    public void setFetchMinBytes(int fetchMinBytes) {
        this.fetchMinBytes = fetchMinBytes;
    }

    public int getFetchMaxWait() {
        return fetchMaxWait;
    }

    public void setFetchMaxWait(int fetchMaxWait) {
        this.fetchMaxWait = fetchMaxWait;
    }

    public int getReceiveBufferBytes() {
        return receiveBufferBytes;
    }

    public void setReceiveBufferBytes(int receiveBufferBytes) {
        this.receiveBufferBytes = receiveBufferBytes;
    }

    public long getConnectMaxIdleTime() {
        return connectMaxIdleTime;
    }

    public void setConnectMaxIdleTime(long connectMaxIdleTime) {
        this.connectMaxIdleTime = connectMaxIdleTime;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public long getReconnectBackoff() {
        return reconnectBackoff;
    }

    public void setReconnectBackoff(long reconnectBackoff) {
        this.reconnectBackoff = reconnectBackoff;
    }

    public boolean isExcludeInternalTopics() {
        return excludeInternalTopics;
    }

    public void setExcludeInternalTopics(boolean excludeInternalTopics) {
        this.excludeInternalTopics = excludeInternalTopics;
    }

    public String getPartitionAssignmentStrategy() {
        return partitionAssignmentStrategy;
    }

    public void setPartitionAssignmentStrategy(String partitionAssignmentStrategy) {
        this.partitionAssignmentStrategy = partitionAssignmentStrategy;
    }

    public long getRetryBackoff() {
        return retryBackoff;
    }

    public void setRetryBackoff(long retryBackoff) {
        this.retryBackoff = retryBackoff;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    public int getConcurrencyConsumer() {
        return concurrencyConsumer;
    }

    public void setConcurrencyConsumer(int concurrencyConsumer) {
        this.concurrencyConsumer = concurrencyConsumer;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
