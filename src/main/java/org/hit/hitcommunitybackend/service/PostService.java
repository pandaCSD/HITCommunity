package org.hit.hitcommunitybackend.service;

import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;
import org.hit.hitcommunitybackend.domain.Repost;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post postPublishService(Integer uid, Post post); //上传帖子
    Post postDeleteService(Integer pid, Integer uid); //删除帖子
    Post postDeleteService(Integer pid);
    Integer postLikeService(Integer uid, Integer pid); //喜欢帖子
    Integer postCommentService(Integer uid, Integer pid, Comment comment); //评论帖子
    Repost repostService(Integer uid, Integer pid); //转发帖子
    List<Post> getAllPost(Integer uid); //帖子列表
    Optional<Post> getPostById(Integer pid);
    List<Post> getAllofPost();
    boolean uploadImageService(Integer pid, String url);
    List<String> getImagesService(Integer pid);
}
