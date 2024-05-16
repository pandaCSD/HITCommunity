package org.hit.hitcommunitybackend.service.serviceimpl;

import jakarta.annotation.Resource;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.repository.UserDao;
import org.hit.hitcommunitybackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource private UserDao userDao;


    @Override
    public User userRegisterService(String uname, String upassword) {
        return null;
    }

    @Override
    public User userLoginService(String uname, String upassword) {
        return null;
    }

    @Override
    public User userUpdateUnameService(Integer uid, String uname) {
        return null;
    }

    @Override
    public User userUpdateUpasswordService(Integer uid, String upassword) {
        return null;
    }

    @Override
    public boolean userDeleteService(Integer uid) {
        return false;
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
