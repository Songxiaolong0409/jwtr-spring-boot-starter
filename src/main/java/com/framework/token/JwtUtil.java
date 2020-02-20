/**
 * Copyright @2019/3/13 xiaolong.song All Rights Reserved
 */
package com.framework.token;

import com.framework.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolong.song
 * @Package com.framework.token
 * @Description:
 * @email loye.song@foxmail.com
 * @date 2019/3/13 14:24
 */
public class JwtUtil {

    static final String SECRET = "JwtR_Distributed_Framework_Token";

    public static final String TOKEN = "token";

    public static final String USERNAME = "username";

    public static String generateToken(Map<String, Object> map) {
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000_000L))// 1000 hour
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return "Bearer " + jwt; //jwt前面一般都会加Bearer
    }

    /**
     * 生成一个有效期1000小时的jwt Token
     *
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        Map<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put(USERNAME, username);
        return generateToken(map);
    }

    /**
     * 验证JWT是否有效, 如果JWT有效则返回用户名, 否则抛出Exception
     *
     * @param token
     */
    public static Map<String, Object> validateToken(String token) throws AuthException {
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            return body;
        } catch (Exception e) {
            throw new AuthException("Invalid Token. " + e.getMessage());
        }
    }

    /**
     * 获取token中的失效时间
     * @param token
     * @return
     */
    public static Date getTokenExpiration(String token){
        Date date = null;
        try{
            Claims claims = Jwts.parser().
                    setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            //未失效，正常获取
            date = claims.getExpiration();
        }catch (ExpiredJwtException e){
            //已经失效了，改从异常中获取
            date = e.getClaims().getExpiration();
        }

        return date;
    }
}
