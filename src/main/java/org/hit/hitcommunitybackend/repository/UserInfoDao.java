package org.hit.hitcommunitybackend.repository;
import org.hit.hitcommunitybackend.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUid(Integer uid);
}