/**
 * Copyright @2018 xiaolong.song All Rights Reserved
 */
package com.framework.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author xiaolong.song
 * @Description: Controller上标注此注解，表明为项目api接口，默认需要验证签名
 * @Package com.framework.annotation
 * @email loye.song@foxmail.com
 * @date 2018/4/19 10:16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@RestController
@RequestMapping("/api/")
public @interface ApiController {

    boolean sign() default true;//是否需要验证签名
}
