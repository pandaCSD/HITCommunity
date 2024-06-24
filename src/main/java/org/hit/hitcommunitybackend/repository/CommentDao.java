package org.hit.hitcommunitybackend.repository;

import jakarta.transaction.Transactional;
import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    // 使用@Modifying注解来指示这是一个修改数据库内容的操作
    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.post.pid = :postId")
    void deleteCommentsByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.user.uid = :userId")
    void deleteCommentsByUserId(Integer userId);
}
