/**
 * Copyright @2018 xiaolong.song All Rights Reserved
 */
package com.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaolong.song
 * @Description: Controller的方法标记此注解可不被验证签名
 * @Package com.framework.annotation
 * @email loye.song@foxmail.com
 * @date 2018/4/23 9:34
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSign {
}
