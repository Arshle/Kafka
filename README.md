# Kafka
Kafka生产者和消费者二次开发封装

使用说明
若格式有问题可以在Idea里查看

一、基础组件引用
  
  1.本工程是基于spring boot进行开发,若普通的spring工程也可在指定配置文件
中的相关配置来完成生产者或消费者的装载;
  2.对于消息传输的载体pojo,需要继承com.jsptpd.kafka.common.entity.message.BaseConcurrencyMessage
或继承com.jsptpd.kafka.common.entity.message.BaseSimpleMessage,
两者的区别为是否支持并发修改消息属性,若没有并发对消息进行修改的场景则使用
BaseSimpleMessage即可,基础消息pojo用于统一消息载体的协议,并且提供统一的
消息附加属性如MessageId、MessageType和properties等,可独立于业务消息体,
具体的消息pojo参考nbiot-kafka工程中的UpdateDevDataNotify类;
  3.无论引用生产者还是消费者依赖,都需要在spring boot启动类或是spring
工程配置文件中优先扫描com.jsptpd.kafka包,随后再扫描业务组件包,参考
nbiot-kafka工程中的NbiotKafkaApplication:
@ComponentScan({"com.jsptpd.kafka","com.jsptpd.nbiot"})

二、生产者组件引用

  1.添加maven依赖:
<!--kafka-producer-->
<dependency>
    <groupId>com.jsptpd.kafka</groupId>
    <artifactId>kafka-producer</artifactId>
    <version>${kafka.producer.version}</version>
</dependency>
  2.在spring boot配置文件或是spring工程配置文件中加入如下最简配置:
kafka:
  connect:
    addr: 172.16.11.105:9092,172.16.11.106:9092,172.16.11.107:9092
zookeeper:
  connect:
    addr: 172.16.11.105:2181,172.16.11.106:2181,172.16.11.107:2181
以上配置为kafka和zookeeper的连接地址;
  3.若需要调优kafka其他生产者配置,参考配置类com.jsptpd.kafka.configuration.KafkaProducerConfiguration
中的所有成员变量,例如如果需要修改请求kafka最大字节数量,参照配置类中的maxRequestSize
字段,在spring boot或spring工程配置文件中新增名为kafka.max.request.size的配置:
kafka:
  connect:
    addr: 172.16.11.105:9092,172.16.11.106:9092,172.16.11.107:9092
  max: 
    request: 
      size: 2097152
zookeeper:
  connect:
    addr: 172.16.11.105:2181,172.16.11.106:2181,172.16.11.107:2181
  4.完成以上配置后保证kafka集群地址可用,使用com.jsptpd.kafka.producer.KafkaSender
提供的相关API即可完成生产者操作,常用的为KafkaSender.send()或KafkaSender.concurrentSend(),
若使用后者表示通过线程池并发发送kafka消息,并发量通过配置kafka.concurrency.senderPoolSize控制,
默认值为500,其余生产者方法如回调接口、分区、主题等与kafka原生API使用基本相似;

三、消费者组件引用

  1.添加maven依赖:
<!--kafka-consumer-->
<dependency>
    <groupId>com.jsptpd.kafka</groupId>
    <artifactId>kafka-consumer</artifactId>
    <version>${kafka.consumer.version}</version>
</dependency>
  2.在spring boot配置文件或是spring工程配置文件中加入如下最简配置:
kafka:
  connect:
    addr: 172.16.11.105:9092,172.16.11.106:9092,172.16.11.107:9092
消费端不需要配置zookeeper地址;
  3.若需要调优kafka其他消费者配置,参考配置类com.jsptpd.kafka.configuration.KafkaConsumerConfiguration
中的所有成员变量,修改方法同生产者
  4.消费者默认启动线程池并维护3个并发的消费者进行同一topic的消费,并发数量通过配置
kafka.concurrency.consumer限制,注意使用时需要和kafka集群的主题分区进行匹配,
避免出现消费者并发数大于kafka主题分区数导致的消费者线程空闲的情况
  5.使用方法参考nbiot-kafka工程的com.jsptpd.nbiot.listener.TestKafkaListener
类,消费者处理类必须实现com.jsptpd.kafka.intf.KafkaMessageListener<T>类,并实现
onMessage方法进行消息的处理,另外消费者类必须添加@KafkaListener注解,若不指定topic
则使用默认的topic,通过配置kafka.topic.default配置,若有额外需要可在注解中声明topic、
group、partition属性用来指定消费主题、消费组和消费分区。

四、Demo使用
具体的使用Demo参考nbiot-kafka工程,为单独的spring boot工程使用


