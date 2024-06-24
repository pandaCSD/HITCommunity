package org.hit.hitcommunitybackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hit.hitcommunitybackend.domain.*;
import org.hit.hitcommunitybackend.model.Result;
import org.hit.hitcommunitybackend.repository.*;
import org.hit.hitcommunitybackend.service.PostService;
import org.hit.hitcommunitybackend.utility.COSUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import static org.hit.hitcommunitybackend.model.ErrorCode.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    public static final String SESSION_NAME = "user";
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final LikeDao likeDao;
    private final ImageDao imageDao;
    private final COSUtility cosUtility;
    private final FriendDao friendDao;

    public PostController(PostService postService, CommentDao commentDao, LikeDao likeDao, ImageDao imageDao, FriendDao friendDao, PostDao postDao, COSUtility cosUtility) {
        this.postService = postService;
        this.commentDao = commentDao;
        this.likeDao = likeDao;
        this.imageDao = imageDao;
        this.cosUtility = cosUtility;
        this.friendDao = friendDao;
        this.postDao = postDao;
    }
    // private final UserDao userDao;


    @PostMapping("/post")
    public Result<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();
        Post p = postService.postPublishService(uid,post);
        Result<Post> result = new Result<>();
        result.setResultSuccess("Post created successfully, Post pid: " + post.getPid(), p);
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
    
    // 获取所有post
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

    // 根据 uid 获取所有自己的帖子和好友的帖子
    @GetMapping("/my_posts")
    public Result<List<Post>> getMyPosts(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();
        List<Post> res = postService.getAllPost(uid);
        List<User> friends = friendDao.findAllFriend(uid);
        for (User u : friends){
         List<Post> friendPosts = postService.getAllPost(u.getUid());
         res.addAll(friendPosts);
        }
        Result<List<Post>> result = new Result<>();
        result.setResultSuccess("All posts and friends' posts who's uid is satisfied found Here!!");
        result.setData(res);
        return result;
    }

    // 根据 uid 获取自己转发的帖子和朋友转发的帖子
    @GetMapping("/reposts")
    public Result<List<Repost>> getMyReposts(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();
        List<Repost> res = postService.getRepostByUId(uid);
        List<User> friends = friendDao.findAllFriend(uid);
        for (User u : friends){
            List<Repost> friendPosts = postService.getRepostByUId(u.getUid());
            res.addAll(friendPosts);
        }
        Result<List<Repost>> result = new Result<>();
        result.setResultSuccess("All reposts and friends' posts who's uid is satisfied found Here!!");
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

    @PostMapping("/images/upload")
    public Result<Void> uploadImage(@RequestParam("file") MultipartFile file,
                                    @RequestParam("pid") int pid,
                                    HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_NAME);
        Integer uid = user.getUid();
        // 检查文件是否为空
        if (file.isEmpty()) {
            // 可以根据需要抛出异常或返回错误响应
            Result<Void> result = new Result<>();
            result.setResultFailed("文件为空");
            return result;
        }
        try {
            // 获取文件字节
            byte[] bytes = file.getBytes();
            // 定义文件保存路径
            // 获取当前时间并格式化
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            // 生成唯一的UUID
            String uuid = UUID.randomUUID().toString();
            // 构建新的文件名
            String newFileName = uid + "_" + pid + "_" + time + "_" + uuid;
            // 完整文件名
            String halfPath = newFileName + ".jpg";
            cosUtility.putData("/post_images/"+halfPath, bytes);
            // 存入数据库的路径进行修改
            postService.uploadImageService(pid, halfPath);
            Result<Void> result = new Result<>();
            result.setResultSuccess("图片上传成功");
            return result;
        } catch (IOException e) {
            // 可以根据需要抛出异常或返回错误响应
            Result<Void> result = new Result<>();
            result.setResultFailed("图片上传失败");
            return result;
        }
    }

    // get urls
    @GetMapping("/images/find/{pid}")
    public Result<List<Image>> findImages(@PathVariable Integer pid,HttpServletRequest request) {
        List<Image> ret = imageDao.findByPid(pid);
        Result<List<Image>> result = new Result<>();
        result.setResultSuccess("All images found Here!!", ret);
        return result;
    }

    @GetMapping("/images/get/{url}")
    public ResponseEntity<byte[]> getImage(@PathVariable String url) {
        try {
            String path = "/post_images/" + url;
            byte[] bytes = cosUtility.getData(path);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 设置内容类型为JPEG图像
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // 内部服务器错误，可以记录错误详情
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}