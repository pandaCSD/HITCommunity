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

数据库表定义
- users 存储用户信息
  - uid:用户id
  - uname:用户名
  - upassword:用户密码
- posts 存储帖子信息
  - pid:帖子id
  - ptime:帖子发布时间
  - powner:帖子发布者
- reposts 存储转发信息
  - rid: 转发id
  - original_pid: 原始帖子id
  - rtime: 转发时间
  - rowner: 转发者
- comments 存储评论信息
  - cid: 评论id
  - pid: 被评论的帖子id
  - uid: 评论者id
  - ctime: 评论时间
  - ccontent: 评论内容
- likes 存储点赞信息
  - lid: 点赞id
  - pid: 被点赞的帖子id
  - uid: 点赞者id
  - ltime: 点赞时间
- images 存储图片信息
  - iid: 图片id
  - pid: 帖子id
  - iurl: 图片路径
