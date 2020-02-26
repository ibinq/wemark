package com.ibinq.wemark.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ibinq.wemark.annotations.PassToken;
import com.ibinq.wemark.annotations.UserLoginToken;
import com.ibinq.wemark.bean.User;
import com.ibinq.wemark.dao.UserDao;
import com.ibinq.wemark.service.impl.UserServiceImpl;
import com.ibinq.wemark.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static final String USER_INFO_KEY = "user_info_key";


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //  获取用户 token
        String token = request.getHeader(JwtUtils.getHeaderKey());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(JwtUtils.getHeaderKey());
        }
        //  token为空
        if(StringUtils.isBlank(token)) {
          /*  this.writerErrorMsg(CommonConstant.UNAUTHORIZED,
                    JwtUtils.getHeaderKey() + " can not be blank",
                    response);*/
            return false;
        }
        //  校验并解析token，如果token过期或者篡改，则会返回null
        Claims claims = JwtUtils.verifyAndGetClaimsByToken(token);
        if(null == claims){
           /* this.writerErrorMsg(CommonConstant.UNAUTHORIZED,
                    JwtUtils.getHeaderKey() + "失效，请重新登录",
                    response);*/
            return false;
        }
        //  校验通过后，设置用户信息到request里，在Controller中从Request域中获取用户信息
        request.setAttribute(USER_INFO_KEY, claims);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion...");
    }
//            /**
//             * 利用response直接输出错误信息
//             * @param code
//             * @param msg
//             * @param response
//             * @throws IOException
//             */
//    private void writerErrorMsg (String code, String msg, HttpServletResponse response) throws IOException {
//        ModelResult<Void> result = new ModelResult<>();
//        result.setCode(code);
//        result.setMsg(msg);
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(JSON.toJSONString(result));
//    }
            public static void main(String[] args) {
//                Executors.newCachedThreadPool();
//                Executors.newFixedThreadPool(10);
//                Executors.newSingleThreadExecutor();



            }

     public class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }




}
