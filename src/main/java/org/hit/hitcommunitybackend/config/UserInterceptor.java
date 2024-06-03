package org.hit.hitcommunitybackend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hit.hitcommunitybackend.controller.UserController;
import org.hit.hitcommunitybackend.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器
 */
public class UserInterceptor implements HandlerInterceptor {
    // Controller方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 同样在这里调用用户服务传入session，判断用户是否登录或者有效
        // 未登录则重定向至主页（假设主页就是/）
        // 从session中取出用户信息
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(UserController.SESSION_NAME);
        // 若session中没有用户信息这说明用户未登录
        if (sessionUser == null) {
            response.sendRedirect("/");
            return false;
        } else {
            return true;
        }
    }

    // Controller方法执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 整个请求完成后（包括Thymeleaf渲染完毕）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
