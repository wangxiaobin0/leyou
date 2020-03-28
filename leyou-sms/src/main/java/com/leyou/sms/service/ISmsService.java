package com.leyou.sms.service;

/**
 * @author
 * @date 2020/3/27
 */
public interface ISmsService {
    /**
     * 发送短信验证码
     * @param phone
     */
    void sendShortMsgForRegister(String phone);
}
