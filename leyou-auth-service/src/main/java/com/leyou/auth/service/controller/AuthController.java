package com.leyou.auth.service.controller;

import com.leyou.auth.api.domain.UserInfo;
import com.leyou.auth.api.utils.CookieUtil;
import com.leyou.auth.service.properties.JwtProperties;
import com.leyou.auth.service.service.IAuthService;
import com.leyou.user.api.domain.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author
 * @date 2020/3/28
 */
@RestController
public class AuthController {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity authentication(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        String token = authService.authentication(username, password);
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        Cookie cookie = new Cookie(jwtProperties.getCookieName(), token);
        cookie.setHttpOnly(false);
        cookie.setDomain("http://localhost:9003");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/verify")
    public ResponseEntity verify(String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtProperties.getPublicKey()).parse(token);
            return ResponseEntity.ok(parse.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
