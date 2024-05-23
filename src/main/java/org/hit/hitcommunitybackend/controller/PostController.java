package org.hit.hitcommunitybackend.controller;

import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;
import org.hit.hitcommunitybackend.domain.Repost;
import org.hit.hitcommunitybackend.model.Result;
import org.hit.hitcommunitybackend.repository.UserDao;
import org.hit.hitcommunitybackend.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

import static org.hit.hitcommunitybackend.model.ErrorCode.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public static final String SESSION_NAME = "post";
    private final UserDao userDao;

    public PostController(PostService postService, UserDao userDao) {
        this.postService = postService;
        this.userDao = userDao;
    }

    @PostMapping("/{uid}/post")
    public Result<Post> createPost(@RequestBody Post post, @PathVariable Integer uid) {
        Post p = postService.postPublishService(uid,post);
        Result<Post> result = new Result<>();
        result.setData(p);
        result.setResultSuccess("Post created successfully, Post pid: " + post.getPid());
        return result;
    }

    // 这需要传入一个uid和一个pid删除帖子，已经验证了Post的uid和路径中的uid是否相等
    @PostMapping("/{uid}/delete/{pid}")
    public Result<Post> deletePost(@PathVariable Integer pid, @PathVariable Integer uid) {
        Post post = postService.postDeleteService(pid,uid);
        Result<Post> result = new Result<>();

        // 删除失败返回null
        if (post == null) {
            result.setResultFailedWithErrorCode("delete Post err, Post"+pid+" not owned by user " +uid,ErrPostIdNotFound);
        }else{
            result.setResultSuccess("Post deleted successfully");
            result.setData(post);
        }
        return result;
    }

    @GetMapping("/posts/{pid}")
    public Result<Post> getPost(@PathVariable Integer pid) {
        Optional<Post> opost = postService.getPostById(pid);
        Result<Post> result = new Result<>();
        if (opost.isPresent()){
            Post p = opost.get();
            result.setData(p);
            result.setResultSuccess("Post who's id:"+pid+" found");
        }
        return result;
    }

    // 这需要传入一个uid和一个pid喜欢帖子
    @PostMapping("/{uid}/like/{pid}")
    public Result<Integer> likePost(@PathVariable Integer pid, @PathVariable Integer uid){
        Result<Integer> result = new Result<>();
        Integer lid = postService.postLikeService(uid,pid);

        if (lid != null){
            result.setData(lid);
            result.setResultSuccess("Post like successfully");
        }else{
            result.setResultFailedWithErrorCode("User Not Found Or Post not found!!",ErrLikeNotFound);
        }

        return null;
    }

    @PostMapping("/{uid}/comment/{pid}")
    public Result<Integer> commentPost(@PathVariable Integer pid, @PathVariable Integer uid, @RequestBody Comment comment){
        Result<Integer> result = new Result<>();
        Integer lid = postService.postCommentService(uid,pid,comment);
        if (lid != null){
            result.setData(lid);
            result.setResultSuccess("Comment successfully");
        }else {
            result.setResultFailedWithErrorCode("User Not Found Or Post not found!!",ErrCommentFound);
        }
        return result;
    }

    @PostMapping("/{uid}/repost/{pid}")
    public Result<Repost> createRePost(@PathVariable Integer pid, @PathVariable Integer uid) {
        Repost p = postService.repostService(uid, pid);
        Result<Repost> result = new Result<>();

        if (p == null){
            result.setResultFailedWithErrorCode("RePost failed, Post Or User NotFound!!!",ErrRePostNotFound);
        }else {
            result.setData(p);
            result.setResultSuccess("RePost reposted successfully, RePost pid: " + p.getRid());
        }
        return result;
    }

    // 根据uid获取所有post
    @GetMapping("/{uid}/posts")
    public Result<List<Post>> getPosts(@PathVariable Integer uid) {
        List<Post> res = postService.getAllPost(uid);
        Result<List<Post>> result = new Result<>();
        result.setData(res);
        result.setResultSuccess("All posts who's uid is satisfied found Here!!");
        return result;
    }

}
