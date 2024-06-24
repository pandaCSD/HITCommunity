package org.hit.hitcommunitybackend.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hit.hitcommunitybackend.domain.Post;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.model.Result;
import org.hit.hitcommunitybackend.service.PostService;
import org.hit.hitcommunitybackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
public class AdminController {
    public static final String SESSION_NAME = "admin";

    private final UserService userService;
    private final PostService postService;

    public AdminController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/login")
    public Result<Void> login(@RequestBody User user, HttpServletRequest request) {
        Result<Void> result = new Result<>();
        if(Objects.equals(user.getUname(), "admin") && Objects.equals(user.getUpassword(), "123456")) {
            User x = new User();
            x.setUname("admin");
            request.getSession().setAttribute(SESSION_NAME, x);
            result.setResultSuccess("登录成功");
        } else {
            result.setResultFailed("登录失败");
        }
        return result;
    }

    @GetMapping("/is-login")
    public Result<User> isLogin(HttpServletRequest request) {
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute(SESSION_NAME);
        // 若session中没有用户信息这说明用户未登录
        if (u == null) {
            result.setResultFailed("未登录");
        } else {
            User x = new User();
            x.setUname(u.getUname());
            x.setUid(u.getUid());
            x.setUpassword("");
            result.setResultSuccess("已登录", x);
        }
        return result;
    }

    @GetMapping("/all-user")
    public Result<List<User>> allUser(HttpServletRequest request) {
        Result<List<User>> result = new Result<>();
        List<User> users = userService.getAllUserService();
        result.setResultSuccess("获取所有用户成功", users);
        return result;
    }

    @DeleteMapping("/user/{uid}")
    public Result<Void> deleteUser(@PathVariable Integer uid) {
        List<Post> posts = postService.getAllPost(uid);
        userService.adDeleteUserService(uid);
        for(Post p : posts) {
            postService.adDeletePostService(p.getPid());
        }
        userService.userDeleteService(uid);
        Result<Void> result = new Result<>();
        result.setResultSuccess("删除用户成功");
        return result;
    }

    @DeleteMapping("/post/{pid}")
    public Result<Void> deletePost(@PathVariable Integer pid) {
        postService.adDeletePostService(pid);
        Result<Void> result = new Result<>();
        result.setResultSuccess("删除帖子成功");
        return result;
    }
}
