package com.exposure.exposureservice.utils;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    /**
     * 由字符串生成加密key
     * @return
     */
    private SecretKey generalKey() {
        String stringKey = Constant.JWT_SECRET;
        // 本地的密码解码
        byte[] encodeKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     */
    public String createJWT(String id, String issuer, String subject, long ttlMillis) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成jwt的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("uid", "fuckyou2020");
        claims.put("user_name", "caoanlong");
        claims.put("nick_name","longge");

        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()					// 这里其实就是new一个JwtBuilder，设置jwt的body
                .setId(id)									// 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setClaims(claims)							// 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuer(issuer)							// issuer：jwt签发人
                .setSubject(subject)						// sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setIssuedAt(now)							// iat: jwt的签发时间
                .signWith(signatureAlgorithm, secretKey);	// 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new CommonException(ErrorCode.TOKEN_ERROR);
        }
        return claims;
    }
}
