package org.hit.hitcommunitybackend.repository;

import jakarta.transaction.Transactional;
import org.hit.hitcommunitybackend.domain.Friend;
import org.hit.hitcommunitybackend.domain.FriendId;
import org.hit.hitcommunitybackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendDao extends JpaRepository<Friend, FriendId> {
    @Query("SELECT u FROM User u JOIN Friend f ON (u.uid = f.uid1 OR u.uid = f.uid2) WHERE (f.uid1 = :uid OR f.uid2 = :uid) AND u.uid <> :uid")
    List<User> findAllFriend(@Param("uid") Integer uid);

    // 根据用户uid删除所有相关的朋友关系，无论是uid1还是uid2
    @Modifying
    @Transactional
    @Query("DELETE FROM Friend f WHERE f.uid1 = :uid OR f.uid2 = :uid")
    void deleteFriendByUserID(@Param("uid") Integer uid);
}