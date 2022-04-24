package com.life.decision.support.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.life.decision.support.dto.UserInformationDto;
import com.life.decision.support.pojo.UserInformation;
import com.life.decision.support.service.IUserInformationService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    IUserInformationService userInformationService;

    private <T> T getBean(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (userInformationService == null) {
            userInformationService = getBean(IUserInformationService.class, request);
        }
        String author = request.getHeader("author");
        if (StrUtil.isBlank(author)) {
            setReturn(response, 403, "权限认证失败");
            return false;
        }
        UserInformation userInformation = new UserInformation();
        userInformation.setId(author);
        UserInformationDto userMsg = userInformationService.getUserMsg(userInformation);
        if (userMsg == null) {
            setReturn(response, 403, "用户未登录");
            return false;
        }
        return true;
    }

    //返回错误信息
    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        response.setStatus(status);
        //UTF-8编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("msg", msg);
        response.getWriter().print(jsonObject);
    }

}
