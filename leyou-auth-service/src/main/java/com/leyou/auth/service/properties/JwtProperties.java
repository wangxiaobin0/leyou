package com.leyou.auth.service.properties;

import com.leyou.auth.api.utils.RsaUtils;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author
 * @date 2020/3/28
 */
@ConfigurationProperties(prefix = "leyou.jwt")
@Component
@Data
public class JwtProperties {
    private String secret;
    private String publicKeyPath;
    private String privateKeyPath;
    private Integer expireTime;
    private Integer cookieMaxAge;
    private String cookieName;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void init() {
        try {
            //生成公钥和私钥
            RsaUtils.generateKey(publicKeyPath, privateKeyPath, secret);
            this.publicKey = RsaUtils.getPublicKey(publicKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(privateKeyPath);
        } catch (Exception e) {
            System.out.println("密钥生成失败");
        }
    }

}
