<template>
  <div class="welcome-container">
    <h1>欢迎来到哈工大圈子</h1>
    <p>这里是哈尔滨工业大学学生和校友交流的社区平台，让我们一起分享知识，交流经验，共创美好未来。</p>
    <img :src="require('@/assets/images/welcome.jpg')">
  </div>
</template>

<script>
export default {
  name: 'WelcomePage'

}
</script>

<style scoped>
.welcome-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  margin-top: 50px;
}
h1 {
  color: #2a5298;
}
p {
  width: 60%;
  margin-top: 20px;
  font-size: 16px;
  line-height: 1.5;
}
img {
  margin-top: 30px;
  width: 100%;
  max-width: 600px;
}
</style>
