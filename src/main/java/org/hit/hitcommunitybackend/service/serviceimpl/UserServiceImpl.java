package org.hit.hitcommunitybackend.service.serviceimpl;

import jakarta.annotation.Resource;
import org.hit.hitcommunitybackend.domain.*;
import org.hit.hitcommunitybackend.repository.FriendDao;
import org.hit.hitcommunitybackend.repository.RequestDao;
import org.hit.hitcommunitybackend.repository.UserDao;
import org.hit.hitcommunitybackend.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource private UserDao userDao;
    @Resource private FriendDao friendDao;
    @Resource private RequestDao requestDao;

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
        FriendId fid = new FriendId(uid1, uid2);
        if(friendDao.existsById(fid)) {
            friendDao.deleteById(fid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean friendRequestSentService(Integer suid, Integer ruid) {
        // 检查请求是否已经存在
        RequestId rid = new RequestId(suid, ruid);
        requestDao.save(new Request(rid, "PENDING"));
        return true;
    }

    @Override
    public boolean friendRequestAccepteService(Integer suid, Integer ruid) {
        FriendId fid = new FriendId(suid, ruid);
        RequestId rid = new RequestId(suid, ruid);
        Optional<Request> r =  requestDao.findById(rid);
        if(r.isPresent()) {
            if(r.get().getStatus().equals("PENDING")) {
                requestDao.deleteById(rid);
                friendDao.save(new Friend(fid));
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean friendRequestRejectService(Integer suid, Integer ruid) {
        RequestId rid = new RequestId(suid, ruid);
        if (requestDao.existsById(rid)) {
            requestDao.save(new Request(rid, "REJECTED"));
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllFriendService(Integer uid) {
        List<User> friends = friendDao.findAllFriend(uid);
        for(User user : friends) {
            user.setUpassword("");
        }
        return friends;
    }

    @Override
    public List<User> getAllFriendRequestService(Integer uid) {
         return requestDao.findAllRequest(uid);
    }

    @Override
    public User userFindByUnameService(String uname) {
        return userDao.findByUname(uname);
    }

    @Override
    public User userFindByUidService(Integer uid) {
        return userDao.findByUid(uid);
    }
}