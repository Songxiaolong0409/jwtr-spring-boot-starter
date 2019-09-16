package com.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
public class SignUtil {

    /**
     * 获取签名
     *
     * @param verifyCode 拼写的参数字符窜
     * @return
     * @throws Exception
     */
    public static String getSign(String verifyCode) {
        try {
            log.info("获取签名");
            if (StringUtils.isEmpty(verifyCode))
                return null;
            //升序拼写字符窜
            List<String> list = new ArrayList<>();
            String[] array = verifyCode.split("&");
            Arrays.sort(array);
            for (String str : array) {
                list.add(str);
            }
            StringBuffer param = new StringBuffer();
            for (String str : list) {
                param.append(str).append("&");
            }
            verifyCode = param.toString();

            verifyCode = new StringBuffer(verifyCode).deleteCharAt(verifyCode.length() - 1).toString();
            log.info("升序拼写字符窜:{}", verifyCode);
            //转换小写
            verifyCode = verifyCode.toLowerCase();
            log.info("转换小写:{}", verifyCode);
            //Base64加密
            verifyCode = Base64.getEncoder().encodeToString(verifyCode.getBytes("UTF-8"));
            log.info("Base64加密:{}", verifyCode);
            //倒序
            verifyCode = new StringBuffer(verifyCode).reverse().toString();
            log.info("倒序:{}", verifyCode);
            //MD5加密
            verifyCode = MD5Util.getMD5(verifyCode);

            log.info("倒序:{}", verifyCode);

            return verifyCode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
