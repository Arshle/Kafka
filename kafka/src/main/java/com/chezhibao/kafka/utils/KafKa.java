/*
 * FileName: KafKa.java
 * Author:   Arshle
 * Date:     2018年05月26日
 * Description:
 */
package com.chezhibao.kafka.utils;

import com.chezhibao.kafka.entity.StockQuotationInfo;
import kafka.admin.AdminUtils;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafKa {

    private static Logger logger = Logger.getLogger(KafKa.class);
    /**
     * kafka连接地址
     */
    private static final String KAFKA_CONNECT = "192.168.3.109:9092,192.168.3.110:9092,192.168.3.111:9092";
    /**
     * kafka生产者配置
     */
    private static Properties kafkaProps = new Properties();
    /**
     * zookeeper连接地址
     */
    private static final String ZK_CONNECT = "192.168.3.109:2181,192.168.3.110:2181,192.168.3.111:2181";
    /**
     * zookeeper会话超时时间
     */
    private static final int SESSION_TIMEOUT = 30000;
    /**
     * zookeeper连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 30000;

    static {
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,KAFKA_CONNECT);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
    }
    /**
     * 获取kafka生产者
     * @return 生产者
     */
    private static KafkaProducer<String,String> getProducer(){
        return new KafkaProducer<>(kafkaProps);
    }
    /**
     * 获取ZkUtils客户端
     * @return 实例
     */
    private static ZkUtils getZkUtils(){
        return ZkUtils.apply(ZK_CONNECT,SESSION_TIMEOUT,CONNECT_TIMEOUT,JaasUtils.isZkSecurityEnabled());
    }
    /**
     * 创建主题
     * @param topic 主题名称
     * @param partition 分区数量
     * @param replica 副本数量
     * @param properties 主题配置
     */
    public static void createTopic(String topic, int partition, int replica, Properties properties){
        ZkUtils zkUtils = getZkUtils();
        try {
            if(!AdminUtils.topicExists(zkUtils,topic)){
                AdminUtils.createTopic(zkUtils,topic,partition,replica,properties,AdminUtils.createTopic$default$6());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            zkUtils.close();
        }
    }
    /**
     * 修改主题配置
     * @param topic 主题
     * @param properties 主题配置
     */
    public static void modifyTopicConfig(String topic,Properties properties){
        ZkUtils zkUtils = getZkUtils();
        try {
            Properties config = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
            config.putAll(properties);
            AdminUtils.changeTopicConfig(zkUtils,topic,config);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            zkUtils.close();
        }
    }
    /**
     * 发送kafka消息
     * @param topic 主题名称
     * @param stockQuotationInfo 股票信息
     */
    public static void sendMessage(String topic, StockQuotationInfo stockQuotationInfo){
        try (KafkaProducer<String, String> producer = getProducer()) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, stockQuotationInfo.getTradeTime(), stockQuotationInfo.getStockCode(), stockQuotationInfo.toString());
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            System.out.println((metadata.topic() + "|" + metadata.offset() + "|" + metadata.partition()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            StockQuotationInfo info = new StockQuotationInfo();
            Random r = new Random();
            int stockCode = 600100 + r.nextInt(10);
            float random = (float)Math.random();
            if(random / 2 < 0.5){
                random = -random;
            }
            DecimalFormat format = new DecimalFormat(".00");
            info.setCurrentPrice(Float.valueOf(format.format(11 + random)));
            info.setPreClosePrice(11.80f);
            info.setOpenPrice(11.5f);
            info.setLowPrice(10.5f);
            info.setHighPrice(12.5f);
            info.setStockCode(String.valueOf(stockCode));
            info.setTradeTime(System.currentTimeMillis());
            info.setStockName("股票-" + stockCode);
            KafKa.sendMessage("stock-quotation",info);
        }
    }
}
