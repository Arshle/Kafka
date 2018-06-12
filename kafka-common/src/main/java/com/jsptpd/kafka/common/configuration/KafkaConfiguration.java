/*
 * FileName: KafkaConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: kafka配置类
 */
package com.jsptpd.kafka.common.configuration;

/**
 * 〈kafka配置类〉<br>
 * 〈设置kafka配置属性〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafkaConfiguration {

    private String kafkaAddr;

    private String zkAddr;

    private int zkSessionTimeout = 30000;

    private int zkConnectTimeout = 30000;


}
