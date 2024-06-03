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
        // 检查是否为OPTIONS请求
        if (request.getMethod().equals("OPTIONS")) {
            return true; // 直接允许OPTIONS请求通过
        }

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
