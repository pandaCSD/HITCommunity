package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Friend;
import org.hit.hitcommunitybackend.domain.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao extends JpaRepository<Friend, FriendId> {

}