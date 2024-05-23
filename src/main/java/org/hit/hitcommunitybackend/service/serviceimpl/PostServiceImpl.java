package org.hit.hitcommunitybackend.service.serviceimpl;

import jakarta.annotation.Resource;
import org.hit.hitcommunitybackend.domain.*;
import org.hit.hitcommunitybackend.repository.*;
import org.hit.hitcommunitybackend.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Post postPublishService(Integer uid, Post post) {
        Post newPost = new Post();
        Optional<User> ret = userDao.findById(uid);
        if (ret.isPresent()) {
            User user = ret.get();
            newPost.setPowner(user);
        }else{
            log.error("User not found");
            return null;
        }
        return postDao.save(newPost);
    }

    @Override
    public Post postDeleteService(Integer pid) {
        Post post = postDao.findById(pid).orElse(null);
        postDao.deleteById(pid);
        if (post!=null){
            log.info("Post num:{} deleted", pid);
        }
        return post;
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
    public Post repostService(Integer uid, Integer pid) {
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
        repostDao.save(repost);
        return post.get();
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
}
