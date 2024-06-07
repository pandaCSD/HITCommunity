package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {
    Comment save(Comment comment);
    Optional<Comment> findById(Integer integer);
    // 查找所有Comment
    List<Comment> findAll();
    
    @Query("SELECT c FROM Comment c WHERE c.post.pid = :postId")
    List<Comment> findByPostId(@Param("postId") Integer postId);
}
