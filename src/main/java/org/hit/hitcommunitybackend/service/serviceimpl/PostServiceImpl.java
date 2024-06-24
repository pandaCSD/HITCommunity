package org.hit.hitcommunitybackend.service.serviceimpl;

import jakarta.annotation.Resource;
import org.hit.hitcommunitybackend.domain.*;
import org.hit.hitcommunitybackend.repository.*;
import org.hit.hitcommunitybackend.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;
    @Resource
    private LikeDao likeDao;
    @Resource
    private CommentDao commentDao;
    @Autowired
    private RepostDao repostDao;
    @Autowired
    private ImageDao imageDao;

    @Override
    public Post postPublishService(Integer uid, Post post) {
        Optional<User> u = userDao.findById(uid);
        post.setPowner(u.orElse(null));
        return postDao.save(post);
    }

    @Override
    public Post postDeleteService(Integer pid, Integer uid) {
        Optional<Post> post = postDao.findById(uid);
        Optional<User> user = userDao.findById(uid);

        if (post.isPresent() && user.isPresent()) {

            Post postToDelete = post.get();
            User userToDelete = user.get();
            // Post的所有人不是当前传入的User
            if (postToDelete.getPowner().getUid() != userToDelete.getUid()){
                log.error("Post not owned by this user");
                return null;
            }
            postDao.delete(postToDelete);
            return postToDelete;
        }else if (post.isPresent()) {
            log.error("User not found in postDeleteService");
            return null;
        }else if (user.isPresent()) {
            log.error("Post not found in postDeleteService");
            return null;
        }

        return null;
    }

    @Override
    public Post postDeleteService(Integer pid) {
        Optional<Post> post = postDao.findById(pid);
        if (post.isPresent()) {
            postDao.deleteById(pid);
            log.info("Post num:{} deleted", pid);
        }else{
            log.error("Post not found");
            return null;
        }
        return post.get();
    }

    @Override
    public Integer postLikeService(Integer uid, Integer pid) {
        Like like = new Like();
        Optional<User> ret = userDao.findById(uid);
        Optional<Post> post = postDao.findById(pid);
        if (ret.isPresent()) {
            User user = ret.get();
            like.setUser(user);
        }else{
            log.error("User not found in like service");
            return null;
        }
        if (post.isPresent()) {
            Post post1 = post.get();
            like.setPost(post1);
        }else{
            log.error("Post not found in like service");
            return null;
        }
        likeDao.save(like);

        return like.getLid();
    }

    @Override
    public Integer postCommentService(Integer uid, Integer pid, Comment comment) {
        Optional<User> ret = userDao.findById(uid);
        Optional<Post> post = postDao.findById(pid);
        if (ret.isPresent()) {
            User user = ret.get();
            comment.setUser(user);
        }else{
            log.error("User not found in post_comment service");
            return null;
        }
        if (post.isPresent()) {
            Post post1 = post.get();
            comment.setPost(post1);
        }else{
            log.error("Post not found in post_comment service");
            return null;
        }
        commentDao.save(comment);
        return comment.getCid();
    }

    @Override
    public Repost repostService(Integer uid, Integer pid) {
        Optional<Post> post = postDao.findById(pid);
        Optional<User> user = userDao.findById(uid);
        Repost repost = new Repost();
        if (post.isPresent()) {
            Post post1 = post.get();
            repost.setOriginalPost(post1);
        }else{
            log.error("Post not found in post_repost");
            return null;
        }
        if (user.isPresent()) {
            User user1 = user.get();
            repost.setRowner(user1);
        }else{
            log.error("User not found in post_repost");
            return null;
        }
        Repost rp =  repostDao.save(repost);
        return rp;
    }

    @Override
    public List<Post> getAllPost(Integer uid) {
        List<Post> posts =  postDao.findAll();
        List<Post> needs = new ArrayList<>();
        for (Post post : posts) {
            if(post.getPowner().getUid()==uid){
                needs.add(post);
            }
        }
        return needs;
    }

    @Override
    public Optional<Post> getPostById(Integer pid) {
        Optional<Post> res = postDao.findById(pid);
        if (res.isPresent()) {
            return res;
        }else{
            log.error("Post not found in  getPostById()");
            return Optional.empty();
        }
    }
    
    @Override
    public List<Post> getAllofPost(){
      return postDao.findAll();
    }

    @Override
    public boolean uploadImageService(Integer pid, String url) {
        try {
            // 创建新的 Image 实体
            Image image = new Image(pid, url);
            // 保存 Image 实体到数据库
            imageDao.save(image);
            return true; // 成功保存，返回 true
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 保存失败，返回 false
        }
    }

    private static String IMAGES_FOLDER = "C:\\Users\\panda\\Pictures\\IMAGE\\";
    @Override
    public boolean deleteImageService(Integer pid) {
        List<Image> images = imageDao.findByPid(pid);
        for (Image image : images) {
            String pathString = IMAGES_FOLDER + image.getIurl();
            Path path = Paths.get(pathString);
            // 删除文件
            try {
                Files.delete(path);
                imageDao.delete(image);
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean adDeletePostService(Integer pid) {
        deleteImageService(pid);
        commentDao.deleteCommentsByPostId(pid);
        likeDao.deleteByPostId(pid);
        repostDao.deleteByPostId(pid);
        postDao.deleteById(pid);
        return true;
    }


}
