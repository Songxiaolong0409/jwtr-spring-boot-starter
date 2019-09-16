/**
 * Copyright @2018 xiaolong.song All Rights Reserved
 */
package com.framework.exception;

/**
 * @author xiaolong.song
 * @Description: 定义接口异常
 * @Package com.framework.exception
 * @email loye.song@foxmail.com
 * @date 2018/3/19 13:05
 */
public class InterException extends Exception {

    public InterException() {
        super("接口异常");
    }

    public InterException(String message) {
        super(message);
    }
}
