package com.leyou.auth.api.utils;

import com.leyou.auth.api.constans.JwtConstant;
import com.leyou.auth.api.domain.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import javax.xml.crypto.Data;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
//        return Jwts.builder()
//                //声明一个k-v
//                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
//                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getUsername())
//                //设置过期时间
//                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
//                //使用的算法和私钥
//                .signWith(SignatureAlgorithm.RS256, privateKey)
//                .compact();
        return Jwts.builder()
                //token中的用户数据
                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getId())
                //加密算法和私钥
                .signWith(SignatureAlgorithm.RS256, privateKey)
                //设置过期时间
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getUsername())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toLong(body.get(JwtConstant.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstant.JWT_KEY_USER_NAME))
        );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toLong(body.get(JwtConstant.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstant.JWT_KEY_USER_NAME))
        );
    }
}