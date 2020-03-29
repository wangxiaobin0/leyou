package com.leyou.cart.interceptor;

import com.leyou.auth.api.domain.UserInfo;
import com.leyou.auth.api.utils.RsaUtils;
import com.leyou.cart.properties.JwtProperties;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;

/**
 * @author
 * @date 2020/3/28
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    JwtProperties jwtProperties;

    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjksInVzZXJuYW1lIjoiZ3Vlc3QiLCJleHAiOjE1OTczODM3ODR9.H4--TV4LCjTdfGhhhLrzKWdmAh_hMIUl2_ZclKeuBGdL8RHHXa502n3TEnbEXag_cNOs0h6u_GaISOn0i4E0Ns1fnyJoL3R9fHHqDozUfrTjl5pJUwNNzJ-yrgCz4hJ_G1yQewPC23RL-MIRnWnjd4s8nmZbT5aA6lma820N4jc";
        if (StringUtils.isEmpty(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try{
            String publicKeyPath = "C:\\Users\\dell\\Desktop\\rsa\\rsa.pub";
            PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);
            Jwt jwt = Jwts.parser().setSigningKey(publicKey).parse(token);
            DefaultClaims o = (DefaultClaims)jwt.getBody();
            String id = o.get("id").toString();
            String username = o.get("username").toString();
            THREAD_LOCAL.set(new UserInfo(Long.parseLong(id), username));
            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        THREAD_LOCAL.remove();
    }

    public static ThreadLocal getThreadLocal() {
        return THREAD_LOCAL;
    }
}
