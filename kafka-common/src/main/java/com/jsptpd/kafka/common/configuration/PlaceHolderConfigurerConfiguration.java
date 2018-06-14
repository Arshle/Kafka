/*
 * FileName: PlaceHolderConfigurerConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月13日
 * Description: PropertySourcesPlaceholderConfigurer重写
 */
package com.jsptpd.kafka.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 〈PropertySourcesPlaceholderConfigurer重写〉<br>
 * 〈设置跳过无法找到的属性，防止程序报错〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Configuration
public class PlaceHolderConfigurerConfiguration {
    /**
     * 自定义configure
     * @return 配置
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[] {
                new ClassPathResource("classpath:application.yaml"),
                new ClassPathResource("classpath:application.yml"),
                new ClassPathResource("classpath:application.properties")};
        configurer.setIgnoreResourceNotFound(true);
        configurer.setLocations(resources);
        return configurer;
    }
}
