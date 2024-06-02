package org.hit.hitcommunitybackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;
import org.hit.hitcommunitybackend.domain.Repost;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.model.Result;
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

    public static final String SESSION_NAME = "user";

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // private final UserDao userDao;


    @PostMapping("/post")
    public Result<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();
        Post p = postService.postPublishService(uid,post);
        Result<Post> result = new Result<>();
        result.setData(p);
        result.setResultSuccess("Post created successfully, Post pid: " + post.getPid());
        return result;
    }

    // 这需要传入一个pid删除帖子
    @PostMapping("/delete/{pid}")
    public Result<Post> deletePost(@PathVariable Integer pid) {
        Post post = postService.postDeleteService(pid);
        Result<Post> result = new Result<>();

        // 删除失败返回null
        if (post == null) {
            result.setResultFailedWithErrorCode("delete Post err, Post"+pid,ErrPostIdNotFound);
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
    @PostMapping("/like/{pid}")
    public Result<Integer> likePost(@PathVariable Integer pid, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();

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

    @PostMapping("/comment/{pid}")
    public Result<Integer> commentPost(@PathVariable Integer pid, @RequestBody Comment comment, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();

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

    @PostMapping("/repost/{pid}")
    public Result<Repost> createRePost(@PathVariable Integer pid, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();
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
    @GetMapping("/posts")
    public Result<List<Post>> getPosts(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();

        List<Post> res = postService.getAllPost(uid);
        Result<List<Post>> result = new Result<>();
        result.setData(res);
        result.setResultSuccess("All posts who's uid is satisfied found Here!!");
        return result;
    }

}
