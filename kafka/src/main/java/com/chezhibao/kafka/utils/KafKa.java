/*
 * FileName: KafKa.java
 * Author:   Arshle
 * Date:     2018年05月26日
 * Description:
 */
package com.chezhibao.kafka.utils;

import kafka.admin.AdminUtils;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafKa {

    private static Logger logger = Logger.getLogger("com.chezhibao.kafka.utils.Kafka");
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
            logger.warning(e.getMessage());
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
            logger.warning(e.getMessage());
        } finally {
            zkUtils.close();
        }
    }
}
