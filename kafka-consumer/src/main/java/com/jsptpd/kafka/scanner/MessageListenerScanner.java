/*
 * FileName: MessageListenerScanner.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description: kafka消费者扫描器
 */
package com.jsptpd.kafka.scanner;

import com.alibaba.fastjson.JSON;
import com.jsptpd.kafka.annotation.KafkaListener;
import com.jsptpd.kafka.common.code.message.KafkaBaseExceptionType;
import com.jsptpd.kafka.common.exception.KafkaBaseException;
import com.jsptpd.kafka.common.intf.message.KafkaMessage;
import com.jsptpd.kafka.common.utils.SpringUtils;
import com.jsptpd.kafka.common.utils.StringUtils;
import com.jsptpd.kafka.configuration.KafkaConsumerConfiguration;
import com.jsptpd.kafka.intf.KafkaMessageListener;
import com.jsptpd.kafka.thread.ConsumerThreadUtils;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 〈kafka消费者扫描器〉<br>
 * 〈spring初始化加载所有消息监听器并订阅kafka消息〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("messageListenerScanner")
public class MessageListenerScanner implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(MessageListenerScanner.class);
    /**
     * 消费者线程池容量与CPU核心数比例
     */
    private static final int PROCESSORS_RATE = 2;
    /**
     * bean装载完成后操作
     * @param bean 装载后的bean
     * @param beanName bean的名称
     * @return 装载后的bean
     * @throws BeansException 异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //获取当前类所有注解
        Class<?> clazz = bean.getClass();
        //获取类上注解
        KafkaListener listener = clazz.getAnnotation(KafkaListener.class);
        if(listener != null){
            //获取类所有接口
            Class<?>[] interfaces = clazz.getInterfaces();
            //是否合法的kafka消费者
            boolean isValid = false;
            Class messageClass = null;
            for(Class<?> intf : interfaces){
                //kafka消费者必须实现KafkaMessageListener接口
                if (intf.isAssignableFrom(KafkaMessageListener.class)){
                    isValid = true;
                    TypeVariable<? extends Class<?>>[] parameters = intf.getTypeParameters();
                    //获取消息类型
                    for(TypeVariable<?> parameter : parameters){
                        try {
                            Type messageType = parameter.getBounds()[0];
                            messageClass = Class.forName(messageType.getTypeName());
                        } catch (Exception e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                    break;
                }
            }
            if(!isValid){
                throw new KafkaBaseException(KafkaBaseExceptionType.E0001.getCode(),"kafkaListener must implements interface com.jsptpd.kafka.intf.KafkaMessageListener");
            }
            KafkaMessageListener messageListener = (KafkaMessageListener) bean;
            //启动消费者
            startConsumers(messageListener,messageClass,listener);
        }
        return bean;
    }
    /**
     * 启动消费者
     * @param messageListener 消息监听类
     */
    @SuppressWarnings({"unchecked", "InfiniteLoopStatement"})
    private void startConsumers(KafkaMessageListener messageListener, Class<? extends KafkaMessage> messageClass, KafkaListener listener){
        ThreadPoolExecutor executor = ConsumerThreadUtils.getThreadPoolExecutor();
        KafkaConsumerConfiguration consumerConfs = SpringUtils.getBean(KafkaConsumerConfiguration.class);
        Properties consumerProps = KafkaConsumerConfiguration.getConsumerConf();
        //并发数控制在2倍CPU核心数以内
        if (consumerConfs.getConcurrencyConsumer() < 1
                || consumerConfs.getConcurrencyConsumer() > (PROCESSORS_RATE * Runtime.getRuntime().availableProcessors())) {
            consumerConfs.setConcurrencyConsumer(3);
        }
        //订阅的主题
        final String topic;
        if(StringUtils.isEmpty(listener.topic())){
            topic = consumerConfs.getDefaultTopic();
        }else{
            topic = listener.topic();
        }
        //消费者分组
        String groupId = listener.group();
        if(StringUtils.isNotEmpty(groupId)){
            consumerConfs.setGroupId(groupId);
        }else{
            consumerConfs.setGroupId(StringUtils.getUUID());
        }
        //分区汇总
        int partitionLength = listener.partitions().length;
        List<TopicPartition> partitionList = new ArrayList<>(partitionLength);
        for(int partition : listener.partitions()){
            partitionList.add(new TopicPartition(topic,partition));
        }
        for(int i = 0; i < consumerConfs.getConcurrencyConsumer(); i ++){
            //启动消费者线程
            executor.execute(() -> {
                KafkaConsumer<String,String> consumer = new KafkaConsumer<>(consumerProps);
                if(partitionLength > 0){
                    //订阅指定分区
                    consumer.assign(partitionList);
                }else{
                    //订阅指定主题
                    consumer.subscribe(Collections.singletonList(topic), new ConsumerRebalanceListener() {
                        @Override
                        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                            //提交偏移量
                            consumer.commitSync();
                        }

                        @Override
                        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                            //已经消费的偏移量
                            long committedOffset;
                            for(TopicPartition partition : partitions){
                                //回退至已经消费的偏移量下一个位置
                                committedOffset = consumer.committed(partition).offset();
                                consumer.seek(partition,committedOffset + 1);
                            }
                        }
                    });
                }
                //拉取消息
                while(true){
                    ConsumerRecords<String, String> records = consumer.poll(1000);
                    for(ConsumerRecord<String,String> record : records){
                        try {
                            logger.info("接收kafka消息|topic:" + record.topic() + "|partition:" + record.partition() + "|key:" + record.key() + "|value:" + record.value() + "|timestamp:" + record.timestamp());
                            //转化消息并执行消息监听器的方法
                            KafkaMessage message = JSON.parseObject(record.value(), messageClass);
                            messageListener.onMessage(message);
                        } catch (Exception e) {
                            logger.error("接受kafka消息异常",e);
                        }
                    }
                }
            });
        }
    }
}
