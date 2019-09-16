/**
 * Copyright @2019/3/14 xiaolong.song All Rights Reserved
 */
package com.framework.interceptor;

import com.framework.annotation.TokenEnable;
import com.framework.exception.AuthException;
import com.framework.token.TokenAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author xiaolong.song
 * @Package com.framework.interceptor
 * @Description:
 * @email loye.song@foxmail.com
 * @date 2019/3/14 16:55
 */
@Component
@Aspect
@Slf4j
public class JwtAuthenticationPointcut {

    @Before("@annotation(tokenEnable)")
    public void doBeforeAdvice(TokenEnable tokenEnable) throws AuthException {
        //验证Token
        try {
            TokenAuthUtil.verificationRequestToken();

            log.info("Token验证通过");
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }
}
