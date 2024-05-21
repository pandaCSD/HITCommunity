package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Request;
import org.hit.hitcommunitybackend.domain.RequestId;
import org.hit.hitcommunitybackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestDao extends JpaRepository<Request, RequestId> {
    @Query("SELECT u FROM User u JOIN Request r ON u.uid = r.suid WHERE r.ruid = :uid AND r.status = 'PENDING'")
    List<User> findAllRequest(@Param("uid") Integer uid);
}

