package org.hit.hitcommunitybackend.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import org.hit.hitcommunitybackend.domain.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepostDao extends JpaRepository<Repost, Integer> {
    Repost save(Repost repost);

    @Query("SELECT r FROM Repost r WHERE r.rowner.uid = :rownerUid")
    List<Repost> findAllByRowner_Uid(@Param("rownerUid") Integer rownerUid);

    // 根据帖子ID删除所有转发记录
    @Modifying
    @Transactional
    @Query("DELETE FROM Repost r WHERE r.originalPost.pid = :postId")
    void deleteByPostId(@Param("postId") Integer postId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Repost r WHERE r.rowner.uid = :userID")
    void deleteByUserId(@Param("userID") Integer userID);
}
