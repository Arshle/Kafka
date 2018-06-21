package com.jsptpd.nbiot;

import com.jsptpd.kafka.common.entity.message.CloudMessage;
import com.jsptpd.kafka.producer.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NbiotKafkaApplicationTests {

    @Test
    public void contextLoads() throws InterruptedException {
        /*for(int i = 0; i < 500; i ++){
            CloudMessage message = new CloudMessage();
            message.setBranchName("DRAGON-" + i);
            message.setDomain("sit" + i + ".jsptpd.com");
            message.setDescription("测试消息" + i);
            message.setIngressIp("172.16.12." + i);
            KafkaSender.send("jsptpd",message);
        }*/
        /*CloudMessage message = new CloudMessage();
        message.setBranchName("DRAGON-1");
        message.setDomain("sit1.jsptpd.com");
        message.setDescription("测试消息1");
        message.setIngressIp("172.16.12.1");
        KafkaSender.concurrentSend("jsptpd",message);*/
        Thread.sleep(Integer.MAX_VALUE);
//        Future<RecordMetadata> future = KafkaSender.send(message);
//        RecordMetadata metadata = future.get();
//        System.out.println(metadata.offset() + "|" + metadata.partition() + "|" + metadata.serializedKeySize() + "|" + metadata.serializedValueSize() + "|" + metadata.topic());
    }
}
