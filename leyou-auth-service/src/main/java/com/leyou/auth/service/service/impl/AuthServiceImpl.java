package com.leyou.auth.service.service.impl;

import com.leyou.auth.api.constans.JwtConstant;
import com.leyou.auth.service.api.IUserApi;
import com.leyou.auth.service.properties.JwtProperties;
import com.leyou.auth.service.service.IAuthService;
import com.leyou.user.api.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2020/3/28
 */
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    IUserApi userApi;

    @Autowired
    JwtProperties jwtProperties;

    @Override
    public String authentication(String username, String password) {
        User user = userApi.queryUser(username, password);
        if (user == null) {
            return null;
        }
        String token = Jwts.builder()
                .claim(JwtConstant.JWT_KEY_ID, user.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, user.getUsername())
                .signWith(SignatureAlgorithm.RS256, jwtProperties.getPrivateKey())
                .setExpiration(DateTime.now().plusMinutes(jwtProperties.getExpireTime()).toDate())
                .compact();

        return token;
    }
}
