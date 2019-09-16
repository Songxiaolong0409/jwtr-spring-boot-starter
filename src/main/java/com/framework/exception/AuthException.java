/**
 * Copyright @2019/3/6 xiaolong.song All Rights Reserved
 */
package com.framework.exception;

import org.springframework.util.Assert;

/**
 * @author xiaolong.song
 * @Package com.framework.exception
 * @Description: 登录验证异常
 * @email loye.song@foxmail.com
 * @date 2019/3/6 14:17
 */
public class AuthException extends Exception {

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }

    public static void checkException(String key, String value) throws AuthException {
        try {
            Assert.hasLength(value, "[AuthException] - '" + key + "'  must have length; it must not be null or empty");
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

    public static void checkException(String key, Object value) throws AuthException {
        try {
            Assert.notNull(value, "[AuthException] -  '" + key + "' this argument is required; it must not be null");
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

}
