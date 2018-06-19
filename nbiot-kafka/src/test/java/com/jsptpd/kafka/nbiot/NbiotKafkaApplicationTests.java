package com.jsptpd.kafka.nbiot;

import com.jsptpd.kafka.common.code.message.KafkaMessageType;
import com.jsptpd.kafka.common.entity.message.CloudMessage;
import com.jsptpd.kafka.producer.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NbiotKafkaApplicationTests {

    @Test
    public void contextLoads() throws ExecutionException, InterruptedException {
        CloudMessage message = new CloudMessage();
        message.setBranchName("DRAGON-1672");
        message.setDomain("jsptpd.com");
        message.setDescription("测试消息");
        KafkaSender.sendByPartition("jsptpd", 0, message);
        Thread.sleep(Integer.MAX_VALUE);
//        Future<RecordMetadata> future = KafkaSender.send(message);
//        RecordMetadata metadata = future.get();
//        System.out.println(metadata.offset() + "|" + metadata.partition() + "|" + metadata.serializedKeySize() + "|" + metadata.serializedValueSize() + "|" + metadata.topic());
    }
}
