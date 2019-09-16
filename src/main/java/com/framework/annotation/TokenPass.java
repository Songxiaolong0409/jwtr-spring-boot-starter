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
 * @Description: 表明不需要验证token的方法
 * @email loye.song@foxmail.com
 * @date 2019/3/14 9:14
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenPass {
}
