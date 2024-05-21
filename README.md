# HITCommunity
工大圈子第一期  
语言:JAVA  
后端:springboot  
数据库:mysql  
{  
"ip":"49.232.224.58",  
"username":"remote",  
"password":"790513*Remote"  
}  
服务器  
{  
"ip":"49.232.224.58",  
"username":"lighthouse",  
"password":"790513csd"  
}   

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  
!!!!!https://blog.csdn.net/weixin_44043758/article/details/118367899 ！看看这个教程!!!!!  
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  


public interface PostService {  
    Post postPublishService(Integer uid, Post post); //上传帖子  
    Post postDeleteService(Integer pid); //删除帖子  
    Post postLikeService(Integer uid, Integer pid); //喜欢帖子  
    Post postCommentService(Integer uid, Integer pid, Comment comment); //评论帖子  
    Post repostService(Integer uid, Integer pid); //转发帖子  
    List<Post> getAllPost(Integer uid); //帖子列表  
}  
