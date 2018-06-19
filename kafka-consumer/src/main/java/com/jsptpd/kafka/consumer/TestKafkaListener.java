/*
 * FileName: TestKafkaListener.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description:
 */
package com.jsptpd.kafka.consumer;

import com.jsptpd.kafka.annotation.KafkaListener;
import com.jsptpd.kafka.common.entity.message.CloudMessage;
import com.jsptpd.kafka.intf.KafkaMessageListener;
import java.io.Serializable;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@KafkaListener(topic = "jsptpd",group = "test")
public class TestKafkaListener implements Serializable,KafkaMessageListener<CloudMessage> {
    private static final long serialVersionUID = 2905769438069905468L;

    @Override
    public void onMessage(CloudMessage message) {
        System.out.println(message.getBranchName() + "|" + message.getDescription() + "|" + message.getDomain());
    }
}
