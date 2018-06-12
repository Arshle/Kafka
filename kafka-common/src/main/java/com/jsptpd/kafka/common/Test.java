/*
 * FileName: Test.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description:
 */
package com.jsptpd.kafka.common;

import com.alibaba.fastjson.JSON;
import com.jsptpd.kafka.common.code.message.KafkaMessageType;
import com.jsptpd.kafka.common.entity.message.CloudMessage;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class Test {
    public static void main(String[] args) {
        CloudMessage message = new CloudMessage();
        message.setBranchName("DRAGON-1672");
        message.setDescription("test");
        message.setDomain("mychebao.com");
        message.setMessageType(KafkaMessageType.NB_IOT);
        message.setMessageId(2);
        message.addProperty("test","a");
        String str = JSON.toJSONString(message);
        System.out.println(str);
        CloudMessage message1 = JSON.parseObject(str, CloudMessage.class);
        System.out.println(message1.getMessageType().getDescription());
        System.out.println(message1.getProperty("test"));
    }
}
