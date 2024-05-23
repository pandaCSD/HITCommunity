package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {
    Comment save(Comment comment);
    Optional<Comment> findById(Integer integer);
}
