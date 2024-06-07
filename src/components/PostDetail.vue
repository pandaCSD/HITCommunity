<template>
    <div class="post-detail">
      <h1 class="post-title">{{ post.title }}</h1>
      <p class="post-text">{{ post.text }}</p>
      <p class="post-meta">
        Posted by: <strong>{{ post.powner.uname }}</strong>
      </p>
      <p class="post-meta">
        Posted at: <time>{{ post.ptime }}</time>
      </p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        post: {},
      };
    },
    async created() {
      try {
        const postId = this.$route.params.id;
        const response = await this.$axios.get(`/post/posts/${postId}`);
        if (response.data.success) {
          this.post = response.data.data;
        } else {
          console.error('Unexpected response format:', response.data);
        }
      } catch (error) {
        console.error('Error fetching post:', error);
      }
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
  