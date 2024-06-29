package org.hit.hitcommunitybackend.repository;
import org.hit.hitcommunitybackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    // 保存或更新Post
    Post save(Post post);
    // 根据ID查找Post
    Optional<Post> findById(Integer id);
    // 查找所有Post
    List<Post> findAll();
    List<Post> findByPid(Integer pid);
    List<Post> findByPowner_Uid(Integer uid);
    // 根据ID删除Post
    void deleteById(Integer id);
}
