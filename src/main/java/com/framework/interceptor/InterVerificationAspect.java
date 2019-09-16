/**
 * Copyright @2018 xiaolong.song@foxmail.com All Rights Reserved
 */
package com.framework.interceptor;

import com.framework.annotation.ApiController;
import com.framework.annotation.NotSign;
import com.framework.exception.InterException;
import com.framework.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author xiaolong.song
 * @Description: 外部接口签名验证
 * @Package com.framework.interceptor
 * @email loye.song@foxmail.com
 * @date 2018/3/19 11:13
 */
@Component
@Aspect
@Slf4j
public class InterVerificationAspect {

    @Before("@within(apiController)")
    public void doBeforeAdvice(JoinPoint joinPoint, ApiController apiController) throws InterException {
        log.info("接口签名验证...");
        Signature signature = joinPoint.getSignature();
        log.info("调用方法名{}", signature);

        if (apiController.sign()) {
            Class<?> tclas = joinPoint.getTarget().getClass();
            Method[] methods = tclas.getDeclaredMethods();

            for (Method method : tclas.getDeclaredMethods()) {
                if (signature.getName().equals(method.getName())) {
                    NotSign bind = method.getAnnotation(NotSign.class);
                    if (ObjectUtils.isEmpty(bind)) {
                        this.checkSign(joinPoint);
                    } else
                        log.info("方法{}无需签名", method.getName());
                }
            }
        } else {
            log.info("ApiController sign=false 无需签名", signature);
        }
    }

    private void checkSign(JoinPoint joinPoint) throws InterException {
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        if (!ObjectUtils.isEmpty(obj)) {
            //获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            //从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            //的到签名参数，和拼写待签名参数
            String param_sign, requestParam;
            if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod())) {
                Map<String, Object> map = convertObjToMap(obj[0]);
                if (ObjectUtils.isEmpty(map))
                    throw new InterException("接口参数转换异常");

                String timestamp = null != map.get("timestamp") ? map.get("timestamp").toString() : "";
                param_sign = null != map.get("sign") ? map.get("sign").toString() : "";
                //参数验证
                if (StringUtils.isEmpty(timestamp))
                    throw new InterException("时间戳'timestamp'不能为空");
                if (StringUtils.isEmpty(param_sign))
                    throw new InterException("签名'sign'不能为空");

                map.remove("sign");

                StringBuffer param = new StringBuffer();
                for (String key : map.keySet()) {
                    Object mapObj = map.get(key);
                    if (!ObjectUtils.isEmpty(mapObj)) {
                        if (mapObj instanceof ArrayList)
                            for (int i = 0; i < ((ArrayList) mapObj).size(); i++) {
                                Object object = ((ArrayList) mapObj).get(i);
                                Map<String, Object> map2 =convertObjToMap(object);
                                if (!ObjectUtils.isEmpty(map2)) {
                                    for (String key2 : map2.keySet())
                                        if (!ObjectUtils.isEmpty(map2.get(key2)))
                                            param.append(key2).append("=").append(map2.get(key2)).append("&");
                                }
                            }
                        else if (!ObjectUtils.isEmpty(map.get(key)))
                            param.append(key).append("=").append(map.get(key)).append("&");
                    }
                }
                requestParam = param.deleteCharAt(param.length() - 1).toString();
            } else {
                String timestamp = obj[obj.length - 2].toString();
                param_sign = obj[obj.length - 1].toString();

                //参数验证
                if (StringUtils.isEmpty(timestamp))
                    throw new InterException("时间戳'timestamp'不能为空");
                if (StringUtils.isEmpty(param_sign))
                    throw new InterException("签名'sign'不能为空");

                StringBuffer param = new StringBuffer();
                for (int i = 0; i < obj.length - 1; i++) {
                    if (!ObjectUtils.isEmpty(obj[i]))
                        param.append(obj[i]).append("&");
                }
                requestParam = param.deleteCharAt(param.length() - 1).toString();
            }

            //获取签名，验证签名
            String sign = SignUtil.getSign(requestParam);
            if (StringUtils.isEmpty(sign))
                throw new InterException("获取签名失败");
            if (!sign.equals(param_sign))
                throw new InterException("验证签名失败");
        } else {
            throw new InterException("验证签名，参数异常，参数为空");
        }
    }


    private Map<String, Object> convertObjToMap(Object obj) {
        try {
            Map<String, Object> reMap = new HashMap<>();
            if (obj == null)
                return null;
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = obj.getClass().getDeclaredField(fields[i].getName());
                f.setAccessible(true);
                Object o = f.get(obj);
                reMap.put(fields[i].getName(), o);
            }
            return reMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
