package com.jsptpd.kafka.nbiot;

import com.jsptpd.kafka.common.code.message.KafkaMessageType;
import com.jsptpd.kafka.common.entity.message.CloudMessage;
import com.jsptpd.kafka.producer.KafkaSender;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NbiotKafkaApplicationTests {

    @Test
    public void contextLoads() throws ExecutionException, InterruptedException {
        CloudMessage message = new CloudMessage();
        message.setBranchName("DRAGON-1672");
        message.setDescription("test");
        message.setDomain("mychebao.com");
        message.setMessageType(KafkaMessageType.NB_IOT);
        message.setMessageId(2);
        message.addProperty("test","a");
        Future<RecordMetadata> future = KafkaSender.send(message);
        RecordMetadata metadata = future.get();
        System.out.println(metadata.offset() + "|" + metadata.partition() + "|" + metadata.serializedKeySize() + "|" + metadata.serializedValueSize() + "|" + metadata.topic());
    }
}
