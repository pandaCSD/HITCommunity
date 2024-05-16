package org.hit.hitcommunitybackend.service.serviceimpl;

import jakarta.annotation.Resource;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.repository.UserDao;
import org.hit.hitcommunitybackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource private UserDao userDao;


    @Override
    public User userRegisterService(String uname, String upassword) {
        // 创建新用户对象
        User newUser = new User();
        newUser.setUname(uname);
        newUser.setUpassword(upassword);

        // 保存新用户到数据库
        return userDao.save(newUser);
    }

    @Override
    public User userLoginService(String uname, String upassword) {
        // 通过用户名查找用户
        return userDao.findByUnameAndUpassword(uname, upassword);
    }

    @Override
    public User userUpdateUnameService(Integer uid, String uname) {
        // 根据 uid 从数据库中获取用户对象
        Optional<User> optionalUser = userDao.findById(uid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // 更新用户名
            user.setUname(uname);
            // 保存更新后的用户对象到数据库中
            return userDao.save(user);
        } else {
            // 如果找不到对应的用户，返回 null 或者抛出异常，视情况而定
            return null;
        }
    }

    @Override
    public User userUpdateUpasswordService(Integer uid, String upassword) {
        // 根据 uid 从数据库中获取用户对象
        Optional<User> optionalUser = userDao.findById(uid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // 更新用户密码
            user.setUpassword(upassword);
            // 保存更新后的用户对象到数据库中
            return userDao.save(user);
        } else {
            // 如果找不到对应的用户，返回 null 或者抛出异常，视情况而定
            return null;
        }
    }

    @Override
    public boolean userDeleteService(Integer uid) {
        // 判断用户是否存在
        if (userDao.existsById(uid)) {
            // 存在则删除用户
            userDao.deleteById(uid);
            return true;
        } else {
            // 不存在则返回 false
            return false;
        }
    }


    @Override
    public boolean userDeleteFriendService(Integer uid1, Integer uid2) {
        return false;
    }

    @Override
    public boolean friendRequestSentService(Integer uid1, Integer uid2) {
        return false;
    }

    @Override
    public boolean friendRequestAccepteService(Integer uid1, Integer uid2) {
        return false;
    }

    @Override
    public boolean friendRequestRejecteService(Integer uid1, Integer uid2) {
        return false;
    }

    @Override
    public List<User> getAllFriendService(Integer uid) {
        return List.of();
    }

    @Override
    public List<User> getAllFriendRequestService(Integer uid) {
        return List.of();
    }
}
