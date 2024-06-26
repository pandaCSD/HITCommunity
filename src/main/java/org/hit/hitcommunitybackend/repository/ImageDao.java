package org.hit.hitcommunitybackend.repository;

import jakarta.transaction.Transactional;
import org.hit.hitcommunitybackend.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Integer> {
    List<Image> findByPid(Integer pid);

    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.pid = :postId")
    void deleteByPostId(Integer postId);
}
