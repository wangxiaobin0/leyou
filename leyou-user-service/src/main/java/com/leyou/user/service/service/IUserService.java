package com.leyou.user.service.service;

import com.leyou.user.api.domain.User;

/**
 * @author
 * @date 2020/3/26
 */
public interface IUserService {

    /**
     * 检查用户名或手机号是否唯一
     * @param data  数据
     * @param type  类型. 1. 用户名 2.手机号
     * @return true 唯一; false 已占用
     */
    boolean checkUsernameOrPhone(String data, Integer type);

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    boolean sendMobileMsg(String phone);

    /**
     * 注册用户
     * @param user
     */
    boolean register(User user, String code);

    /**
     * 根据用户名密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    User queryUser(String username, String password);
}
