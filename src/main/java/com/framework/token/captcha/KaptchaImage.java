/**
 * Copyright @2019/3/1 xiaolong.song All Rights Reserved
 */
package com.framework.token.captcha;

import com.framework.exception.AuthException;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;


/**
 * @author xiaolong.song
 * @Package com.framework.token.captcha
 * @Description:
 * @email loye.song@foxmail.com
 * @date 2019/3/1 17:08
 */
@Slf4j
@Controller
public class KaptchaImage {

    @Setter
    private Producer captchaProducer;

    @GetMapping("/verification")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (ObjectUtils.isEmpty(captchaProducer)) {
            throw new Exception("You need to use the '@EnableKaptcha' annotation to open the validation code.");
        }

        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    public static boolean validateVerify(String inputVerify) {
        //获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        Object object = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (ObjectUtils.isEmpty(object)) {
            throw new AuthException("Verification code expiration");
        }

        String validateCode = ((String) object).toLowerCase();
        inputVerify = inputVerify.toLowerCase();

        log.info("验证码：" + validateCode + "用户输入：" + inputVerify);

        if (validateCode.equals(inputVerify)) {
            //因为使用spring-session-redis后，放入session的验证码会自动保存到redis中，
            //所以当验证成功后，删除session中的验证码
            request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            return true;
        } else
            return false;
    }

    public void writeJson(HttpServletResponse response, String content) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            ServletOutputStream out = response.getOutputStream();
            out.print(content);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
