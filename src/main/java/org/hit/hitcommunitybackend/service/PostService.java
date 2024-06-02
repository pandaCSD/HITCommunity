package org.hit.hitcommunitybackend.service;

import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;

import java.util.List;

public interface PostService {
    Post postPublishService(Integer uid, Post post); //上传帖子
    Post postDeleteService(Integer pid); //删除帖子
    Post postLikeService(Integer uid, Integer pid); //喜欢帖子
    Post postCommentService(Integer uid, Integer pid, Comment comment); //评论帖子
    Post repostService(Integer uid, Integer pid); //转发帖子
    List<Post> getAllPost(Integer uid); //帖子列表
}
