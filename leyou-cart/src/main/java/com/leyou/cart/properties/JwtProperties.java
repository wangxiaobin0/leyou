package com.leyou.cart.properties;

import com.leyou.auth.api.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author
 * @date 2020/3/28
 */
@Component
@ConfigurationProperties("leyou.jwt")
@Data
public class JwtProperties {

    private String publicKeyPath;
    private Integer cookieMaxAge;
    private String cookieName;

    private PublicKey publicKey;

    @PostConstruct
    void init() {
        try {
            PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);
            this.publicKey = publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
