package org.hit.hitcommunitybackend.service;

import org.hit.hitcommunitybackend.domain.Request;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.domain.UserInfo;

import java.util.List;

public interface UserService {
    User userRegisterService(String uname, String upassword);
    User userLoginService(String uname, String upassword);
    User userUpdateUnameService(Integer uid, String uname);
    User userUpdateUpasswordService(Integer uid, String upassword);
    boolean userDeleteService(Integer uid);
    boolean userDeleteFriendService(Integer uid1, Integer uid2);
    boolean friendRequestSentService(Integer suid, Integer ruid);
    boolean friendRequestAccepteService(Integer suid, Integer ruid);
    boolean friendRequestRejectService(Integer suid, Integer ruid);
    List<User> getAllFriendService(Integer uid);
    List<User> getAllFriendRequestService(Integer uid);
    User userFindByUnameService(String uname);
    User userFindByUidService(Integer uid);

    UserInfo userGetInfoService(Integer uid);
    UserInfo userUpdateInfoService(UserInfo userInfo);

    List<User> getAllUserService();
    void adDeleteUserService(Integer uid);
}
