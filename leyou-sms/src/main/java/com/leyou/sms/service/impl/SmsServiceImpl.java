package com.leyou.sms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.leyou.sms.service.ISmsService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2020/3/27
 */
@Service
public class SmsServiceImpl implements ISmsService {
    @Autowired
    RedisTemplate redisTemplate;

    public static final String CODE_PREFIX = "leyou:user:register:phone:";
    @Override
    public void sendShortMsgForRegister(String phone) {
        String code = RandomUtil.randomNumbers(6);
        String content = "您本次注册的验证码是:" + code + ". 验证码20秒内有效";
        redisTemplate.opsForValue().set(CODE_PREFIX + phone, code, 60, TimeUnit.SECONDS);
        System.out.println(content);
        System.out.println(redisTemplate.opsForValue().get(CODE_PREFIX + phone));
    }


}
