/*
 * FileName: ProducerThreadUtils.java
 * Author:   Arshle
 * Date:     2018年06月13日
 * Description: 生产者线程池工具类
 */
package com.jsptpd.kafka.thread;

import com.jsptpd.kafka.common.utils.SpringUtils;
import com.jsptpd.kafka.configuration.KafkaConsumerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈生产者线程池工具类〉<br>
 * 〈用于生产者并发发送消息线程管理〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class ConsumerThreadUtils {

    private static Logger logger = LoggerFactory.getLogger(ConsumerThreadUtils.class);
    /**
     * 线程工厂
     */
    private static final ThreadFactory THREAD_FACTORY = new ProducerThreadFactory();
    /**
     * 环境创建线程工厂
     * @author Arshle
     * */
    private static class ProducerThreadFactory implements ThreadFactory {
        /**
         * 线程名称前缀
         * */
        private final String namePrefix = "kafka-consumer-";
        /**
         * 线程分组
         * */
        private final ThreadGroup group;
        /**
         * 线程计数
         * */
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        /**
         * 构造方法
         * */
        ProducerThreadFactory(){
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
        }
        /**
         * 构造新线程
         * @param runnable 线程接口
         * @return 线程
         * */
        @SuppressWarnings("NullableProblems")
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(group, runnable,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if(thread.isDaemon()){
                thread.setDaemon(false);
            }
            if(thread.getPriority() != Thread.NORM_PRIORITY){
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }
    /**
     * 线程池保证单例
     */
    private static class ThreadPoolClass{
        /**
         * 线程池对象
         */
        private static ThreadPoolExecutor threadPoolExecutor;

        static{
            //设置线程池容量
            int coreSize;
            try {
                KafkaConsumerConfiguration kafkaConf = SpringUtils.getBean(KafkaConsumerConfiguration.class);
                coreSize = kafkaConf.getConcurrencyConsumer();
            } catch (Exception e) {
                logger.info(e.getMessage(),e);
                coreSize = Runtime.getRuntime().availableProcessors() * 2;
            }
            threadPoolExecutor = new ThreadPoolExecutor(coreSize, coreSize * 2, 0L,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(coreSize), THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());
        }
    }
    /**
     * 获取公共线程池
     * @return 线程池
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(){
        return ThreadPoolClass.threadPoolExecutor;
    }
}
