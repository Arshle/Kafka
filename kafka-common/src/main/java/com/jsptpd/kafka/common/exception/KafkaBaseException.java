/*
 * FileName: KafkaBaseException.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description: kafka异常
 */
package com.jsptpd.kafka.common.exception;

/**
 * 〈kafka异常〉<br>
 * 〈统一异常编码和异常内容〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class KafkaBaseException extends RuntimeException {

    private static final long serialVersionUID = -8075746284920487318L;
    /**
     * 异常编码
     */
    private String code;

    public KafkaBaseException(){}

    public KafkaBaseException(String code){
        this.code = code;
    }

    public KafkaBaseException(String code,String message){
        super(message);
        this.code = code;
    }
}
