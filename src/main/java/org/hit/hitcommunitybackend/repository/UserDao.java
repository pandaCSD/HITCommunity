package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUname(String uname);
    User findByUnameAndUpassword(String uname, String upassword);
    User findByUid(Integer uid);
}
