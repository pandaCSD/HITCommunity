package org.hit.hitcommunitybackend.controller;

import org.hit.hitcommunitybackend.domain.Post;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.model.ErrorCode;
import org.hit.hitcommunitybackend.model.Result;
import org.hit.hitcommunitybackend.repository.PostDao;
import org.hit.hitcommunitybackend.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.hit.hitcommunitybackend.model.ErrorCode.ErrPostIdNotFound;

@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public static final String SESSION_NAME = "post";

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public Result<Post> createPost(@RequestBody Post post) {

        return null;
    }

    // 这里只是传入一个pid就可以删除帖子。。。。
    @PostMapping("/delete")
    public Result<Post> deletePost(@RequestBody Integer pid) {
        Post post = postService.postDeleteService(pid);
        Result<Post> result = new Result<>();
        if (post != null) {
            result.setResultSuccess("Post who's id:"+pid+" deleted");
        } else{
            log.error("Post with id {} not found, cannot delete in PostController::deletePost()", pid);
            result.setResultFailed("Post with id "+ pid +" not found, cannot delete in PostController::deletePost()", ErrPostIdNotFound);
        }
        return result;
    }



}
