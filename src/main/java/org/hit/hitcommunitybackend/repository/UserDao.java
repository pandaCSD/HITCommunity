package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUid(Integer uid);
    User findByUname(String username);
    User findByUnameAndUpassword(String username, String password);
    Boolean deleteByUname(String username);
    Boolean deleteByUid(Integer uid);
    User saveUser(User user);
}
