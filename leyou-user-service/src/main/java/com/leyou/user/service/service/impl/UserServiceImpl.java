package com.leyou.user.service.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.leyou.user.api.domain.User;
import com.leyou.user.service.dao.IUserDao;
import com.leyou.user.api.domain.MessageBody;
import com.leyou.user.service.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author
 * @date 2020/3/26
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    IUserDao userDao;
    public static final String CODE_PREFIX = "leyou:user:register:phone:";
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Override
    public boolean checkUsernameOrPhone(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
        }
        return userDao.selectCount(user) == 0;
    }

    @Override
    public boolean sendMobileMsg(String phone) {
        try {
            MessageBody<String> msg = new MessageBody<String>();
            msg.setData(phone);
            rabbitTemplate.convertAndSend("leyou.user.register", msg);
            System.out.println("消息发送成功:" + msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean register(User user, String code) {
        boolean b = checkUsernameOrPhone(user.getUsername(), 1);
        if (!b) {
            return false;
        }
        String sessionCode = (String) redisTemplate.opsForValue().get(CODE_PREFIX + user.getPhone());
        if (code == null || !sessionCode.equals(code)) {
            return false;
        }
        String salt = UUID.fastUUID().toString().replaceAll("-", "");
        user.setSalt(salt);
        user.setCreated(new Date());
        userDao.insert(user);

        return true;
    }

    @Override
    public User queryUser(String username, String password) {
        User u = new User();
        u.setUsername(username);
        User user = userDao.selectOne(u);
        if (! password.equals(user.getPassword())) {
            return null;
        }
        return user;
    }
}
