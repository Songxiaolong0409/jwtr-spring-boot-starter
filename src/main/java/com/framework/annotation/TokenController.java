/**
 * Copyright @2019/3/14 xiaolong.song All Rights Reserved
 */
package com.framework.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author xiaolong.song
 * @Package com.framework.annotation
 * @Description: Controller上标注此注解代替RestController，表明此类请求需要验证token
 * @email loye.song@foxmail.com
 * @date 2019/3/14 9:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@RestController
@RequestMapping("/app/")
public @interface TokenController {

}
