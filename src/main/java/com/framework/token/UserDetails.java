/**
 * Copyright @2019/3/13 xiaolong.song All Rights Reserved
 */
package com.framework.token;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolong.song
 * @Package com.framework.token
 * @Description: 登录传输信息
 * @email loye.song@foxmail.com
 * @date 2019/3/13 14:18
 */
@Data
public class UserDetails implements Serializable {

    private String username,

    password,

    token,

    verifycode,//验证码

    smscode;//短信验证码

    /**
     * 默认开启密码验证
     * <p>
     * beforeLogin() 若进行app短信验证，不需要密码验证则设置为false
     */
    private boolean passwordEnable = true;

}
