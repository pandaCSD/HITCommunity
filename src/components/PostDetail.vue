<template>
  <div class="post-detail">
    <h1 class="post-title">{{ title }}</h1>
    <p class="post-text">{{ text }}</p>
    
    <!-- 图片展示部分 -->
    <div class="images-section" v-if="images.length > 0">
      <div class="images-container">
        <img v-for="image in images" :src="image.data" :key="image.url" class="post-image" @click="openImageDialog(image.data)" />
      </div>
      <p class="post-meta">
        Posted by: <strong>{{ postUname }}</strong>
      </p>
      <p class="post-meta">
        Posted at: <time>{{ time }}</time>
      </p>
    </div>
    
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
            <th>Comment Time</th>
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
    
    <!-- 图片放大对话框 -->
    <v-dialog v-model="dialog" max-width="800px">
      <v-img :src="selectedImage" max-width="800px" max-height="800px" />
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      postUname: '',
      comments: [],
      title: '',
      text: '',
      time: '',
      likes: 0,
      image_urls: [],
      newComment: '',
      images: [],
      dialog: false,
      selectedImage: '',
    };
  },
  async mounted() {
    try {
      const postId = this.$route.params.id;
      const response = await this.$axios.get(`/post/posts/${postId}`);
      if (response.data.success) {
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
        this.likes = likesResponse.data.data;
      } else {
        console.error('Error fetching likes:', likesResponse.data);
      }
    } catch (error) {
      console.error('Error fetching likes:', error);
    }

    // 获取图片存储的urls
    try {
      const postId = this.$route.params.id;
      const urls = await this.$axios.get(`/post/images/find/${postId}`);
      if (urls.data.success) {
        urls.data.data.forEach(url => {
          this.image_urls.push({ url: url.iurl });
        });
        console.log(this.image_urls);
        await this.loadImages(); // 调用方法加载图片
      } else {
        console.error('Error fetching likes:', urls.data);
      }
    } catch (error) {
      console.error('Error fetching likes:', error);
    }
  },
  methods: {
    async loadImages() {
      try {
        const imagePromises = this.image_urls.map(async (image) => {
          const imageUrl = `${image.url}`;
          const imageResponse = await this.$axios.get(imageUrl, { responseType: 'blob' });
          return {
            url: imageUrl,
            data: URL.createObjectURL(imageResponse.data), // 将blob转换为可用的URL
          };
        });
        this.images = await Promise.all(imagePromises);
      } catch (error) {
        console.error('Error loading images:', error);
      }
    },
    getImageUrl(imageUrl) {
      return imageUrl; // 直接返回从后端获取的图片 URL
    },
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
        alert('您已经点赞过了！！');
      }
    },
    async submitComment() {
      try {
        const postId = this.$route.params.id;
        const response = await this.$axios.post(`/post/comment/${postId}`, {
          ccontent: this.newComment,
        });
        if (response.data.success) {
          this.newComment = ''; // 清空输入框
        } else {
          console.log('Error submitting comment');
        }
      } catch (error) {
        console.error('Error submitting comment:', error);
        alert('Error submitting comment');
      }
    },
    openImageDialog(imageUrl) {
      this.selectedImage = imageUrl;
      this.dialog = true;
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

.images-section {
  margin-top: 20px;
}

.images-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 10px;
}

.post-image {
  width: 100%;
  max-width: 200px;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.post-image:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.like-section {
  display: flex;
  align-items: center;
  margin-top: 20px;
}

.like-section v-btn {
  margin-right: 10px;
}
</style>
