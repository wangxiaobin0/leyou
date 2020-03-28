package com.leyou.sms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author
 * @date 2020/3/27
 */
@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
