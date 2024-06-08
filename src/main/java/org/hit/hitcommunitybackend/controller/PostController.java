package org.hit.hitcommunitybackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hit.hitcommunitybackend.domain.Comment;
import org.hit.hitcommunitybackend.domain.Post;
import org.hit.hitcommunitybackend.domain.Repost;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.model.Result;
import org.hit.hitcommunitybackend.repository.CommentDao;
import org.hit.hitcommunitybackend.repository.LikeDao;
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
    private final CommentDao commentDao;
    private final LikeDao likeDao;
    
    public PostController(PostService postService, CommentDao commentDao, LikeDao likeDao) {
        this.postService = postService;
        this.commentDao = commentDao;
        this.likeDao = likeDao;
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
            result.setResultSuccess("Post who's id:"+pid+" found");
            result.setData(p);
        }
        return result;
    }

    // 这需要传入一个pid喜欢帖子
    @PostMapping("/like/{pid}")
    public Result<Integer> likePost(@PathVariable Integer pid, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();

        Result<Integer> result = new Result<>();
        Integer lid = postService.postLikeService(uid,pid);

        if (lid != null){
            result.setResultSuccess("Post like successfully");
            result.setData(lid);
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
            result.setResultSuccess("Comment successfully");
            result.setData(lid);
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
        result.setResultSuccess("All posts who's uid is satisfied found Here!!");
        result.setData(res);
        return result;
    }
    
    // 根据uid获取所有post
    @GetMapping("/allposts")
    public Result<List<Post>> getallPosts(HttpServletRequest request) {
        List<Post> res = postService.getAllofPost();
        // 调试输出以确认是否获取到了数据
        if (res == null || res.isEmpty()) {
            System.out.println("No posts found");
        } else {
            System.out.println("Posts found: " + res.size());
        }
        
        Result<List<Post>> result = new Result<>();
        result.setResultSuccess("All posts who's uid is satisfied found Here!!");
        result.setData(res);
        return result;
    }
    
    @GetMapping("/comments/{pid}")
    public Result<List<Comment>> getCommentsById(@PathVariable Integer pid, HttpServletRequest request) {
        List<Comment>comments = commentDao.findByPostId(pid);
        Result<List<Comment>> result = new Result<>();
        result.setResultSuccess("All comments who's pid is satisfied found Here!!");
        result.setData(comments);
        return result;
    }
    
    @GetMapping("/likes/{pid}")
    public Result<Integer> getPostLikes(@PathVariable Integer pid, HttpServletRequest request) {
        Integer res = likeDao.findByPostId(pid);
        Result<Integer> result = new Result<>();
        result.setResultSuccess("All likes count by pid found Here!!");
        result.setData(res);
        return result;
    }
    
}