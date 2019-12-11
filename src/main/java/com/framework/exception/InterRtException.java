/**
 * Copyright @2019/3/6 xiaolong.song All Rights Reserved
 */
package com.framework.exception;

import org.springframework.util.Assert;

/**
 * @author xiaolong.song
 * @Package com.framework.exception
 * @Description: 定义接口异常
 * @email loye.song@foxmail.com
 * @date 2019/3/6 14:17
 */
public class InterRtException extends RuntimeException {

    public InterRtException(String msg, Throwable t) {
        super(msg, t);
    }

    public InterRtException(String msg) {
        super(msg);
    }

    public static void checkException(String key, String value) {
        try {
            Assert.hasLength(value, "[InterRtException] - '" + key + "'  must have length; it must not be null or empty");
        } catch (Exception e) {
            throw new InterRtException(e.getMessage());
        }
    }

    public static void checkException(String key, Object value) {
        try {
            Assert.notNull(value, "[InterRtException] -  '" + key + "' this argument is required; it must not be null");
        } catch (Exception e) {
            throw new InterRtException(e.getMessage());
        }
    }

}
