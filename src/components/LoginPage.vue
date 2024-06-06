<template>
  <div class="loginBox">
    <h2>Login</h2>
    <form @submit.prevent="submitForm">
      <div class="item">
        <input type="text" v-model="username" required>
        <label v-if="username">Username</label>
      </div>
      <div class="item">
        <input type="password" v-model="password" required>
        <label v-if="password">Password</label>
      </div>
      <!-- 登录按钮 -->
      <button class="btn" type="submit">Login</button>
      <!-- 注册按钮 -->
      <button class="btn register-btn" type="button" @click="openRegisterForm">Register</button>
      <!-- 忘记密码按钮 -->
      <button class="btn forgot-btn" type="button" @click="game">A Little Game
      </button>
   
    </form>
    <RegisterForm ref="registerForm"/>
  </div>
</template>


<script>
import RegisterForm from './RegisterForm.vue';

export default {
  name: 'LoginComponent',
  data() {
    return {
      uid : 0,
      username: '',
      password: '',
    };
  },
  components: {
    RegisterForm
  },
  methods: {
    async submitForm() {
      try {
        const response = await this.$axios.post('/user/login', {
          uid : -1,
          uname: this.username,
          upassword: this.password
        });
        if (response.data.success) {
          console.log('Login successful:', response.data);
          // Handle successful login, e.g., redirect to another page
          this.$router.push('/home');
        } else {
          console.error('Login failed:', response.data.message);
          alert('Login failed: ' + response.data.message);
        }
      } catch (error) {
        this.$router.push('/home');
        console.error('Error during login:', error);
        alert('An error occurred during login.');
      }
    },
    openRegisterForm() {
      this.$refs.registerForm.open();
    },
    game(){
      window.location.href='game/game.html';
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

a {
  text-decoration: none;
}

input, button {
  background: transparent;
  border: 0;
  outline: none;
}

body {
  height: 100vh;
  background: linear-gradient(#141e30, #243b55);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  color: #03e9f4;
}

.loginBox {
  width: 400px;
  height: 364px;
  background-color: #0c1622;
  margin: 100px auto;
  border-radius: 10px;
  box-shadow: 0 15px 25px 0 rgba(0, 0, 0, .6);
  padding: 40px;
  box-sizing: border-box;
}

h2 {
  text-align: center;
  color: aliceblue;
  margin-bottom: 30px;
  font-family: 'Courier New', Courier, monospace;
}

.item {
  height: 45px;
  border-bottom: 1px solid #fff;
  margin-bottom: 40px;
  position: relative;
}

.item input {
  width: 100%;
  height: 100%;
  color: #fff;
  padding-top: 20px;
  box-sizing: border-box;
}

.item input:focus+label, .item input:valid+label {
  top: 0px;
  font-size: 2px;
}

.item label {
  position: absolute;
  left: 0;
  top: 12px;
  transition: all 0.5s linear;
}

.btn {
  padding: 10px 20px;
  margin-top: 30px;
  color: #03e9f4;
  position: relative;
  overflow: hidden;
  text-transform: uppercase;
  letter-spacing: 2px;
  left: 35%;
}

.btn:hover {
  border-radius: 5px;
  color: #fff;
  background: #03e9f4;
  box-shadow: 0 0 5px 0 #03e9f4,
  0 0 25px 0 #03e9f4,
  0 0 50px 0 #03e9f4,
  0 0 100px 0 #03e9f4;
  transition: all 1s linear;
}

.btn>span {
  position: absolute;
}

.btn>span:nth-child(1),
.btn>span:nth-child(3) {
  width: 100%;
  height: 2px;
}

.btn>span:nth-child(2),
.btn>span:nth-child(4) {
  width: 2px;
  height: 100%;
}

.register-btn {
  margin-top: 10px; /* 添加一些顶部边距 */
  background-color: #0c5a82; /* 不同的背景颜色 */
}

.register-btn:hover {
  background-color: #0389f4; /* 悬停时的背景颜色 */
  color: white;
}
</style>
