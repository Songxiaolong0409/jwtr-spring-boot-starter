/**
 * Copyright @2019/3/14 xiaolong.song All Rights Reserved
 */
package com.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaolong.song
 * @Package com.framework.annotation
 * @Description: 标注需要header 里 有token的方法，同AppController
 * @email loye.song@foxmail.com
 * @date 2019/3/14 16:43
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenEnable {
}
