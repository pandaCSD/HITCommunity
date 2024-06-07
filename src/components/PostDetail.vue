<template>
    <div class="post-detail">
      <h1 class="post-title">{{ this.title }}</h1>
      <p class="post-text">{{ this.text }}</p>
      <p class="post-meta">
        Posted by: <strong>{{ postUname}}</strong>
      </p>
      <p class="post-meta">
        Posted at: <time>{{time}}</time>
      </p>
      
        <!-- 点赞按钮和点赞数展示 -->
        <div class="like-section">
        <v-btn color="primary" @click="likePost">点赞</v-btn>
        <p>Likes: {{ likes }}</p>
        </div>

        <!-- 评论展示部分 -->
    <div class="comments-section">
      <h2>评论:</h2>
      <table>
        <thead>
          <tr>
            <th>User</th>
            <th>Comment</th>
            <th>Comment At</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="comment in comments" :key="comment.cid">

            <td>{{ comment.uname }}</td>
            <td>{{ comment.ccontent }}</td>
            <td>{{ comment.ctime }}</td>
          </tr>
        </tbody>
      </table>
    </div>

     <!-- 添加评论表单 -->
     <div class="add-comment-section">
      <h3>发表评论:</h3>
      <v-text-field
        v-model="newComment"
        label="Your comment"
        outlined
        dense
        solo
      ></v-text-field>
      <v-btn color="success" @click="submitComment">添加评论</v-btn>
    </div>
      
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        postUname: {},
        comments: [],
        title: {},
        text: {},
        time: {},
        likes: 0,
      };
    },
    async mounted() {
      try {
        const postId = this.$route.params.id;
        const response = await this.$axios.get(`/post/posts/${postId}`);
        if (response.data.success) {
            // console.log(response.data.data);
          this.postUname = response.data.data.powner.uname;
          this.text = response.data.data.text;
          this.title = response.data.data.title;
          this.time = response.data.data.ptime;
        } else {
          console.error('Unexpected response format:', response.data);
        }
      } catch (error) {
        console.error('Error fetching post:', error);
      }

      try {
        const postId = this.$route.params.id;
        const response = await this.$axios.get(`/post/comments/${postId}`);
        if (response.data.success) {
            
            response.data.data.forEach(comment => {
                this.comments.push({
                    ccontent: comment.ccontent,
                    cid: comment.cid,
                    ctime: comment.ctime,
                    pid: comment.post.pid,
                    uid: comment.user.uid,
                    uname: comment.user.uname,
                });
            });
            console.log(this.comments);
        } else {
          console.error('Unexpected response format:', response.data);
        }
      } catch (error) {
        console.error('Error fetching post:', error);
      }

       // 获取帖子点赞数
        try {
            const postId = this.$route.params.id;
            const likesResponse = await this.$axios.get(`/post/likes/${postId}`);
            if (likesResponse.data.success) {
                //console.log(likesResponse.data.data);
                this.likes = likesResponse.data.data;
            } else {
            console.error('Error fetching likes:', likesResponse.data);
            }
        } catch (error) {
            console.error('Error fetching likes:', error);
        }
    },
    methods: {
        async likePost() {
            try {
                const postId = this.$route.params.id;
                const response = await this.$axios.post(`/post/like/${postId}`);
                
                if (response.data.success) {
                    this.likes += 1; // 假设点赞成功后服务器没有返回更新后的点赞数
                } else {
                    console.log('您已经点赞过了！！');
                }
            } catch (error) {
                alert('您已经点赞过了！！')
                // console.log();
                // console.error('Error liking post:', error);
            }
        },
        async submitComment() {
            try {
                const postId = this.$route.params.id;
                const response = await this.$axios.post(`/post/comment/${postId}`, {
                    content: this.newComment,
                });
                
                if (response.data.success) {
                // 添加新评论到本地评论列表
                this.comments.push({
                    ccontent: this.newComment,
                    cid: response.data.data.cid, // 假设后端返回新评论的ID
                    ctime: new Date().toISOString(), // 设置当前时间
                    pid: postId,
                    uid: this.$store.state.user.id, // 假设有登录用户的ID
                    uname: this.$store.state.user.uname, // 假设有登录用户的用户名
                });
                this.newComment = ''; // 清空输入框
                } else {
                console.log('Error submitting comment');
                }
            } catch (error) {
                console.error('Error submitting comment:', error);
                alert('Error submitting comment');
            }
        },
    },
  };
  </script>
  
  <style scoped>
  .post-detail {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .post-title {
    font-size: 2em;
    margin-bottom: 0.5em;
  }
  
  .post-text {
    font-size: 1.2em;
    margin-bottom: 1em;
  }
  
  .post-meta {
    font-size: 0.9em;
    color: #555;
  }
  
  .post-meta strong {
    font-weight: bold;
  }
  </style>
  <style scoped>
  .post-detail {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .post-title {
    font-size: 2em;
    margin-bottom: 0.5em;
  }
  
  .post-text {
    font-size: 1.2em;
    margin-bottom: 1em;
  }
  
  .post-meta {
    font-size: 0.9em;
    color: #555;
  }
  
  .post-meta strong {
    font-weight: bold;
  }
  
  .comments-section {
    margin-top: 20px;
  }
  
  .comments-section table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .comments-section th, .comments-section td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
  }
  
  .comments-section th {
    background-color: #f2f2f2;
  }
  </style>
  