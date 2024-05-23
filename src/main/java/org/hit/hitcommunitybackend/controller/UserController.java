package org.hit.hitcommunitybackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.model.Result;
import org.hit.hitcommunitybackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public static final String SESSION_NAME = "user";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        User u = userService.userRegisterService(user.getUname(), user.getUpassword());
        Result<User> result = new Result<>();
        if(u == null) {
            result.setResultFailed("创建用户失败", pid);
        } else {
            result.setResultSuccess("创建用户成功", u);
        }
        return result;
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user, HttpServletRequest request) {
        Result<User> result = new Result<>();
        User u = userService.userLoginService(user.getUname(), user.getUpassword());
        if(u == null) {
            result.setResultFailed("登录失败", pid);
        } else {
            u.setUpassword("");
            result.setResultSuccess("登录成功", u);
            request.getSession().setAttribute(SESSION_NAME, u);
        }
        return result;
    }

    @GetMapping("/is-login")
    public Result<User> isLogin(HttpServletRequest request) {
        // 传入session到用户服务层
        //return userService.isLogin(request.getSession());
        return null;
    }

    @PutMapping("/{uid}/username")
    public User updateUsername(@PathVariable Integer uid, @RequestParam String uname) {
        return userService.userUpdateUnameService(uid, uname);
    }

    @PutMapping("/{uid}/password")
    public User updatePassword(@PathVariable Integer uid, @RequestParam String upassword) {
        return userService.userUpdateUpasswordService(uid, upassword);
    }

    @DeleteMapping("/{uid}")
    public boolean deleteUser(@PathVariable Integer uid) {
        return userService.userDeleteService(uid);
    }

    @DeleteMapping("/{uid1}/friends/{uid2}")
    public boolean deleteFriend(@PathVariable Integer uid1, @PathVariable Integer uid2) {
        return userService.userDeleteFriendService(uid1, uid2);
    }

    @PostMapping("/{suid}/friend-requests/{ruid}")
    public boolean sendFriendRequest(@PathVariable Integer suid, @PathVariable Integer ruid) {
        return userService.friendRequestSentService(suid, ruid);
    }

    @PostMapping("/{suid}/friend-requests/{ruid}/accept")
    public boolean acceptFriendRequest(@PathVariable Integer suid, @PathVariable Integer ruid) {
        return userService.friendRequestAccepteService(suid, ruid);
    }

    @PostMapping("/{suid}/friend-requests/{ruid}/reject")
    public boolean rejectFriendRequest(@PathVariable Integer suid, @PathVariable Integer ruid) {
        return userService.friendRequestRejectService(suid, ruid);
    }

    @GetMapping("/{uid}/friends")
    public List<User> getAllFriends(@PathVariable Integer uid) {
        return userService.getAllFriendService(uid);
    }

    @GetMapping("/{uid}/friend-requests")
    public List<User> getAllFriendRequests(@PathVariable Integer uid) {
        return userService.getAllFriendRequestService(uid);
    }
}
