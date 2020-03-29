package com.leyou.auth.api.test;

import com.leyou.auth.api.constans.JwtConstant;
import com.leyou.auth.api.domain.UserInfo;
import com.leyou.auth.api.utils.JwtUtils;
import com.leyou.auth.api.utils.RsaUtils;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import sun.security.provider.PolicySpiFile;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author
 * @date 2020/3/28
 */
public class JwtTest {

    public static final String PUBLIC_KEY_PATH = "C:\\Users\\dell\\Desktop\\rsa\\ras.pub";
    public static final String PRIVATE_KEY_PATH = "C:\\Users\\dell\\Desktop\\rsa\\rsa.pri";
    public static final String REAL_SECRET = "123";
    public static final String FAKE_SECRET = "124";
    public static final Integer EXPIRE_TIME = 2;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * 步骤：
     *  1. 根据Secret生成公钥私钥
     *  2. 使用私钥生成token
     *  3. 使用公钥解密token
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(PUBLIC_KEY_PATH, PRIVATE_KEY_PATH, REAL_SECRET);
    }

    @Before
    public void testBefore() throws Exception {
        this.privateKey = RsaUtils.getPrivateKey(PRIVATE_KEY_PATH);
        this.publicKey = RsaUtils.getPublicKey(PUBLIC_KEY_PATH);
    }

    @Test
    public void testGenerateToken() {
        UserInfo userInfo = new UserInfo(1l,"admin");
        String token = Jwts.builder()
                .claim(JwtConstant.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstant.JWT_KEY_USER_NAME, userInfo.getUsername())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .setExpiration(DateTime.now().plusMinutes(EXPIRE_TIME).toDate())
                .compact();
        System.out.println(token);
    }

    @Test
    public void getUserInfo() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTU4NTM3MzI4NX0.MEXbRJnBX-NgSQ9mavqsOZFTRG0AdAT6lt4XhKk6URaa6LYggVgTLiRvMCGS2XAll3dHCgW0H-8W7wl7TikAOuxiaBud9xSOM0M5IxgUEtUiTxy8gD9o9D5JYSPITbd_XoZGcv4oI0l4RSC_6yyBmP1r01TTR5AnbetEu2fWKy4";
        Jwt jwt = Jwts.parser().setSigningKey(publicKey).parse(token);
        Object body = jwt.getBody();
        System.out.println(body);
    }
}
