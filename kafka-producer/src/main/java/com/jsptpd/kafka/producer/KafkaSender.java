/*
 * FileName: KafkaSender.java
 * Author:   Arshle
 * Date:     2018年06月13日
 * Description: kafka生产者封装
 */
package com.jsptpd.kafka.producer;

import com.jsptpd.kafka.configuration.KafkaProducerConfiguration;
import com.jsptpd.kafka.common.intf.message.KafkaMessage;
import com.jsptpd.kafka.common.utils.SpringUtils;
import com.jsptpd.kafka.common.utils.StringUtils;
import com.jsptpd.kafka.thread.ProducerThreadUtils;
import kafka.admin.AdminUtils;
import kafka.admin.BrokerMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.security.JaasUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.Map;
import scala.collection.Seq;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 〈kafka生产者封装〉<br>
 * 〈封装主题创建修改以及发送消息等〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafkaSender {

    private static Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    /**
     * kafka生产者单例内部类
     */
    private static class KafkaProducerInner{
        /**
         * kafka生产者
         */
        private static KafkaProducer<String,String> producer;

        static{
            //初始化kafka生产者
            initProducer();
        }
        /**
         * 初始化生产者
         */
        private static void initProducer(){
            try {
                //加载kafka连接配置
                KafkaProducerConfiguration kafkaConf = SpringUtils.getBean(KafkaProducerConfiguration.class);
                //初始化生产者
                Properties props = new Properties();
                props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConf.getKafkaAddr());
                props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaConf.getKeySerializer());
                props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConf.getValueSerializer());
                props.put(ProducerConfig.ACKS_CONFIG,kafkaConf.getAcks());
                props.put(ProducerConfig.BATCH_SIZE_CONFIG,kafkaConf.getBatchSize());
                props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,kafkaConf.getBufferMemory());
                props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConf.getClientId());
                props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaConf.getCompressType());
                props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, kafkaConf.getConnectMaxIdleTime());
                props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConf.getLingerTime());
                props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,kafkaConf.getMaxBlockTime());
                props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafkaConf.getMaxRequestSize());
                props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, kafkaConf.getPartitionerClass());
                props.put(ProducerConfig.RECEIVE_BUFFER_CONFIG, kafkaConf.getReceiveBufferBytes());
                props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaConf.getRequestTimeout());
                props.put(ProducerConfig.RETRIES_CONFIG, kafkaConf.getRetries());
                props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,kafkaConf.getMaxInFlightRequestsPerConnection());
                props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaConf.getRetryBackOffTime());
                producer = new KafkaProducer<>(props);
            } catch (Exception e) {
                logger.error("初始化kafka生产者异常",e);
                producer = null;
            }

        }
    }
    /**
     * 获取zk连接工具
     * @return zk连接工具
     */
    private static ZkUtils getZkUtils(){
        //加载kafka连接配置
        KafkaProducerConfiguration kafkaConf = SpringUtils.getBean(KafkaProducerConfiguration.class);
        //初始化zk连接工具
        try {
            return ZkUtils.apply(kafkaConf.getZkAddr(),kafkaConf.getZkSessionTimeout(),
                    kafkaConf.getZkConnectTimeout(),JaasUtils.isZkSecurityEnabled());
        } catch (Exception e) {
            logger.error("zk连接工具初始化异常",e);
        }
        return null;
    }
    /**
     * 获取kafka生产者
     * @return 生产者
     */
    private static KafkaProducer<String,String> getProducer(){
        return KafkaProducerInner.producer;
    }
    /**
     * 创建主题
     * @param topic 主题名称
     * @param partition 分区数量
     * @param replica 副本数量
     * @param properties 主题配置
     */
    public static void createTopic(String topic, int partition, int replica, Properties properties){
        if(StringUtils.isEmpty(topic) || partition <= 0 || replica <= 0){
            return;
        }
        ZkUtils zkUtils = getZkUtils();
        try {
            if(zkUtils != null && !AdminUtils.topicExists(zkUtils,topic)){
                AdminUtils.createTopic(zkUtils,topic,partition,replica,
                        properties,AdminUtils.createTopic$default$6());
            }
        } catch (Exception e) {
            logger.error("创建主题异常",e);
        } finally {
            if(zkUtils != null){
                zkUtils.close();
            }
        }
    }
    /**
     * 修改主题配置
     * @param topic 主题
     * @param properties 主题配置
     */
    public static void modifyTopicConfig(String topic,Properties properties){
        if(StringUtils.isEmpty(topic) || properties == null){
            return;
        }
        ZkUtils zkUtils = getZkUtils();
        try {
            if(zkUtils != null){
                Properties config = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
                config.putAll(properties);
                AdminUtils.changeTopicConfig(zkUtils,topic,config);
            }
        } catch (Exception e) {
            logger.error("修改主题配置异常",e);
        } finally {
            if(zkUtils != null){
                zkUtils.close();
            }
        }
    }
    /**
     * 重新制定主题分区数量
     * @param topic 主题名称
     * @param totalPartitions 分区总数
     */
    public static void configurePartitions(String topic,int totalPartitions){
        if(StringUtils.isEmpty(topic) || totalPartitions <= 0){
            return;
        }
        ZkUtils zkUtils = getZkUtils();
        try {
            if(zkUtils != null){
                AdminUtils.addPartitions(zkUtils,topic,totalPartitions,null,
                        true, AdminUtils.createTopic$default$6());
            }
        } catch (Exception e) {
            logger.error("指定主题分区数错误",e);
        } finally {
            if(zkUtils != null){
                zkUtils.close();
            }
        }
    }
    /**
     * 重新分配主题的分区副本
     * @param topic 主题名称
     * @param partitions 分区
     * @param replicas 副本
     */
    public static void assignTopicPartitions(String topic,int partitions,int replicas){
        if(StringUtils.isEmpty(topic) || partitions <= 0 || replicas <= 0){
            return;
        }
        ZkUtils zkUtils = getZkUtils();
        try {
            if(zkUtils != null){
                //获取现有的分区副本分配方案
                Seq<BrokerMetadata> brokerMetadatas = AdminUtils.getBrokerMetadatas(zkUtils,
                        AdminUtils.getBrokerMetadatas$default$2(),
                        AdminUtils.getBrokerMetadatas$default$3());
                //生成分区副本分配方案
                Map<Object, Seq<Object>> replicaAssign = AdminUtils.assignReplicasToBrokers(brokerMetadatas, partitions, replicas,
                        AdminUtils.assignReplicasToBrokers$default$4(),
                        AdminUtils.assignReplicasToBrokers$default$5());
                //修改副本分区方案
                AdminUtils.createOrUpdateTopicPartitionAssignmentPathInZK(zkUtils,topic,replicaAssign,null,true);
            }
        } catch (Exception e) {
            logger.error("重分配分区副本异常",e.getMessage());
        } finally {
            if(zkUtils != null){
                zkUtils.close();
            }
        }
    }
    /**
     * 删除主题
     * @param topic 主题名称
     */
    public static void deleteTopic(String topic){
        if(StringUtils.isEmpty(topic)){
            return;
        }
        ZkUtils zkUtils = getZkUtils();
        try {
            //删除主题
            if(zkUtils != null){
                AdminUtils.deleteTopic(zkUtils,topic);
            }
        } catch (Exception e) {
            logger.error("删除主题异常",e);
        } finally {
            if(zkUtils != null){
                zkUtils.close();
            }
        }
    }
    /**
     * 发送kafka消息
     * @param message 消息体
     * @return 发送后的future对象
     */
    public static Future<RecordMetadata> send(KafkaMessage message){
        KafkaProducerConfiguration kafkaConf = SpringUtils.getBean(KafkaProducerConfiguration.class);
        return send(kafkaConf.getDefaultTopic(),message);
    }
    /**
     * 发送kafka消息
     * @param topic 主题名称
     * @param message 消息体
     * @return 发送后的future对象
     */
    public static Future<RecordMetadata> send(String topic, KafkaMessage message){
        return sendByPartition(topic, null, message);
    }
    /**
     * 指定分区发送消息
     * @param partition 分区编号
     * @param message 消息体
     * @return 发送后的future对象
     */
    public static Future<RecordMetadata> sendByPartition(Integer partition,KafkaMessage message){
        KafkaProducerConfiguration kafkaConf = SpringUtils.getBean(KafkaProducerConfiguration.class);
        return sendByPartition(kafkaConf.getDefaultTopic(),partition,message);
    }
    /**
     * 指定分区发送消息
     * @param topic 主题名称
     * @param partition 分区编号
     * @param message 消息体
     * @return 发送后的future对象
     */
    public static Future<RecordMetadata> sendByPartition(String topic,Integer partition,KafkaMessage message){
        if(StringUtils.isEmpty(topic) || message == null){
            logger.warn("kafka主题或消息为空");
            return null;
        }
        Future<RecordMetadata> future = null;
        try (KafkaProducer<String,String> producer = getProducer()) {
            String uuid = StringUtils.getUUID();
            long now = System.currentTimeMillis();
            //构建消息记录
            ProducerRecord<String,String> record = new ProducerRecord<>(topic,
                    partition, now, uuid, message.toString());
            //发送消息
            if(producer != null){
                future = producer.send(record);
                logger.info("发送kafka数据|topic:" + topic + "|partition:" + partition + "|key:" + uuid + "|value:" + message.toString() + "|timestamp:" + now);
            }
        } catch (Exception e) {
            logger.error("发送kafka数据异常",e);
        }
        return future;
    }
    /**
     * 并发发送kafka消息
     * @param message 消息体
     */
    public static void concurrentSend(KafkaMessage message){
        concurrentSend(message,null);
    }
    /**
     * 并发发送消息
     * @param message 消息体
     * @param callback 回调接口
     */
    public static void concurrentSend(KafkaMessage message,Callback callback){
        KafkaProducerConfiguration kafkaConf = SpringUtils.getBean(KafkaProducerConfiguration.class);
        concurrentSendByPartitions(kafkaConf.getDefaultTopic(),null,message,callback);
    }
    /**
     * 并发发送kafka消息
     * @param topic 主题名称
     * @param message 消息体
     */
    public static void concurrentSend(String topic,KafkaMessage message){
        concurrentSendByPartitions(topic,null,message);
    }
    /**
     * 并发发送kafka消息
     * @param topic 主题名称
     * @param message 消息体
     * @param callback 回调接口
     */
    public static void concurrentSend(String topic,KafkaMessage message,Callback callback){
        concurrentSendByPartitions(topic,null,message,callback);
    }
    /**
     * 并发指定分区发送kafka消息
     * @param partition 分区编号
     * @param message 消息体
     */
    public static void concurrentSendByPartitions(Integer partition,KafkaMessage message){
        concurrentSendByPartitions(partition,message,null);
    }
    /**
     * 并发指定分区发送kafka消息
     * @param partition 分区编号
     * @param message 消息体
     * @param callback 回调接口
     */
    public static void concurrentSendByPartitions(Integer partition,KafkaMessage message,Callback callback){
        KafkaProducerConfiguration kafkaConf = SpringUtils.getBean(KafkaProducerConfiguration.class);
        concurrentSendByPartitions(kafkaConf.getDefaultTopic(),partition,message,callback);
    }
    /**
     * 并发指定分区发送kafka消息
     * @param topic 主题名称
     * @param partition 分区编号
     * @param message 消息体
     */
    public static void concurrentSendByPartitions(String topic, Integer partition, KafkaMessage message){
        concurrentSendByPartitions(topic,partition,message,null);
    }
    /**
     * 并发指定分区发送kafka消息
     * @param topic 主题名称
     * @param partition 分区编号
     * @param message 消息体
     */
    public static void concurrentSendByPartitions(String topic, Integer partition, KafkaMessage message, Callback callback){
        if(StringUtils.isEmpty(topic) || message == null){
            logger.warn("kafka主题或消息为空");
            return;
        }
        ThreadPoolExecutor executor = ProducerThreadUtils.getThreadPoolExecutor();
        executor.execute(() -> {
            try (KafkaProducer<String,String> producer = getProducer()) {
                String uuid = StringUtils.getUUID();
                long now = System.currentTimeMillis();
                //构建消息记录
                ProducerRecord<String,String> record = new ProducerRecord<>(topic,
                        partition, now, uuid, message.toString());
                //发送消息
                if(producer != null){
                    producer.send(record,callback);
                    logger.info("发送kafka数据|topic:" + topic + "|partition:" + partition + "|key:" + uuid + "|value:" + message.toString() + "|timestamp:" + now);
                }
            } catch (Exception e) {
                logger.error("发送kafka数据异常",e);
            }
        });
    }
}
