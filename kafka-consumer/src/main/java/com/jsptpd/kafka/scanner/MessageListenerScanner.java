/*
 * FileName: MessageListenerScanner.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description: kafka消费者扫描器
 */
package com.jsptpd.kafka.scanner;

import com.jsptpd.kafka.annotation.KafkaListener;
import com.jsptpd.kafka.common.code.message.KafkaBaseExceptionType;
import com.jsptpd.kafka.common.exception.KafkaBaseException;
import com.jsptpd.kafka.common.intf.message.KafkaMessage;
import com.jsptpd.kafka.common.utils.SpringUtils;
import com.jsptpd.kafka.configuration.KafkaConsumerConfiguration;
import com.jsptpd.kafka.intf.KafkaMessageListener;
import com.jsptpd.kafka.thread.ConsumerThreadUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Properties;
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
            startConsumers(messageListener,messageClass);
        }
        return bean;
    }
    /**
     * 启动消费者
     * @param messageListener 消息监听类
     */
    private void startConsumers(KafkaMessageListener messageListener, Class<? extends KafkaMessage> messageClass){
        ThreadPoolExecutor executor = ConsumerThreadUtils.getThreadPoolExecutor();
        KafkaConsumerConfiguration consumerConfs = SpringUtils.getBean(KafkaConsumerConfiguration.class);
        Properties consumerProps = KafkaConsumerConfiguration.getConsumerConf();
        for(int i = 0; i < consumerConfs.getConcurrencyConsumer(); i ++){
            executor.execute(() -> {
                KafkaConsumer<String,String> consumer = new KafkaConsumer<>(consumerProps);
            });
        }
    }
}
