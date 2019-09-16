/**
 * Copyright @2019/3/14 xiaolong.song All Rights Reserved
 */
package com.framework.token;

import com.framework.exception.AuthException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author xiaolong.song
 * @Package com.framework.token
 * @Description:
 * @email loye.song@foxmail.com
 * @date 2019/3/14 15:55
 */
public class TokenAuthUtil {

    /**
     * 当前登录用户信息
     *
     * @return
     */
    public static <T extends UserDetails> T getCurrentUser() throws AuthException {
        try {
            String token = getRequest().getHeader(JwtUtil.TOKEN);

            AuthException.checkException(JwtUtil.TOKEN, token);

            Map<String, Object> map = JwtUtil.validateToken(token);
            String userName = map.get(JwtUtil.USERNAME).toString();

            Object object = TokenAuthUtil.getSession().getAttribute(userName);
            if (ObjectUtils.isEmpty(object))
                throw new AuthException("Session timeout Need to login");
            else
                return (T) object;
        } catch (AuthException e) {
            throw new AuthException("getCurrentUser Error:" + e.getMessage());
        }
    }


    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        return sra.getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getHeaderToken() {
        return getRequest().getHeader(JwtUtil.TOKEN);
    }

    /**
     * 验证request里是否存在token
     *
     * @throws Exception
     */
    public static void verificationRequestToken() throws Exception {
        //验证Token
        try {
            String token = getHeaderToken();

            AuthException.checkException(JwtUtil.TOKEN, token);

            //检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
            JwtUtil.validateToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthException(e.getMessage());
        }
    }
}
