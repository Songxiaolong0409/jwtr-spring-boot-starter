/**
 * Copyright @2019/3/13 xiaolong.song All Rights Reserved
 */
package com.framework.interceptor;

import com.framework.annotation.TokenController;
import com.framework.annotation.TokenPass;
import com.framework.exception.AuthException;
import com.framework.token.TokenAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @author xiaolong.song
 * @Package com.framework.interceptor
 * @Description:
 * @email loye.song@foxmail.com
 * @date 2019/3/13 15:00
 */
@Component
@Aspect
@Slf4j
public class JwtAuthenticationAspect {

    @Before("@within(tokenController)")
    public void doBeforeAdvice(JoinPoint joinPoint, TokenController tokenController) throws AuthException {
        log.info("接口Token验证...");
        Signature signature = joinPoint.getSignature();
        log.info("调用方法名{}", signature);

        Class<?> tclas = joinPoint.getTarget().getClass();
        Method[] methods = tclas.getDeclaredMethods();

        for (Method method : methods) {
            if (signature.getName().equals(method.getName())) {
                TokenPass pass = method.getAnnotation(TokenPass.class);

                if (ObjectUtils.isEmpty(pass)) {
                    //验证Token
                    try {
                        TokenAuthUtil.verificationRequestToken();

                        log.info("Token验证通过");
                    } catch (Exception e) {
                        throw new AuthException(e.getMessage());
                    }
                } else
                    log.info("方法{}无需验证Token", method.getName());
            }
        }
    }

}