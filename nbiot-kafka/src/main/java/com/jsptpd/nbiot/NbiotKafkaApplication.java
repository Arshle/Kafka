/*
 * FileName: NbiotKafkaApplication.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: springboot启动类
 */
package com.jsptpd.nbiot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 〈springboot启动类〉<br>
 * 〈springboot启动类〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@SpringBootApplication
@ComponentScan({"com.jsptpd.kafka","com.jsptpd.nbiot"})
public class NbiotKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NbiotKafkaApplication.class, args);
    }
}
