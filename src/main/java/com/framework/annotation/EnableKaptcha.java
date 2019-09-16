/**
 * Copyright @2019/3/15 xiaolong.song All Rights Reserved
 */
package com.framework.annotation;

import org.springframework.context.annotation.ImportResource;

import java.lang.annotation.*;

/**
 * @author xiaolong.song
 * @Package com.framework.annotation
 * @Description:
 * @email loye.song@foxmail.com
 * @date 2019/3/15 9:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ImportResource(locations = {"classpath:kaptcha/kaptcha.xml"})
public @interface EnableKaptcha {
}
