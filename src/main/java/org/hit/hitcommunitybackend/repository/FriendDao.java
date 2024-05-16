package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Friend;
import org.hit.hitcommunitybackend.domain.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendDao extends JpaRepository<Friend, Integer> {

    // 根据 uid1 和 uid2 查找朋友关系
    List<Friend> findByUid1AndUid2(Integer uid1, Integer uid2);

    // 根据 uid1 或 uid2 查找朋友关系
    List<Friend> findByUid1OrUid2(Integer uid1, Integer uid2);

    // 根据 uid1 查找朋友关系
    List<Friend> findByUid1(Integer uid1);

    // 根据 uid2 查找朋友关系
    List<Friend> findByUid2(Integer uid2);

    // 根据状态查找朋友关系
    List<Friend> findByStatus(FriendshipStatus status);

    // 根据状态和 uid 查找朋友关系
    List<Friend> findByStatusAndUid1OrStatusAndUid2(FriendshipStatus status1, Integer uid1, FriendshipStatus status2, Integer uid2);

}