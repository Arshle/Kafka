package com.jsptpd.kafka.nbiot;

import com.jsptpd.kafka.common.configuration.KafkaConfiguration;
import com.jsptpd.kafka.common.utils.SpringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NbiotKafkaApplicationTests {

    @Test
    public void contextLoads() {
        KafkaConfiguration configuration = SpringUtils.getBean(KafkaConfiguration.class);
        System.out.println(configuration.getKafkaAddr());
    }
}
