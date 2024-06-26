<template>
  <v-app>
    <v-container fill-height fluid class="pa-0 ma-0 login-background">
      <v-row class="fill-height" align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12 login-card">
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>工大圈子</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form @submit.prevent="submitForm">
                <v-text-field
                  v-model="username"
                  label="用户名"
                  prepend-icon="mdi-account"
                  required
                ></v-text-field>
                <v-text-field
                  v-model="password"
                  label="密码"
                  prepend-icon="mdi-lock"
                  type="password"
                  required
                ></v-text-field>
                <v-checkbox
                  v-model="isChecked"
                  label="我是管理员"
                  color="primary"
                ></v-checkbox>
                <v-btn :loading="loading" type="submit" color="primary" block>登录</v-btn>
                <v-btn text @click="openRegisterForm" color="primary" block>注册</v-btn>
              </v-form>
            </v-card-text>
          </v-card>
          <RegisterForm ref="registerForm"/>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import RegisterForm from './RegisterForm.vue';

export default {
  name: 'LoginComponent',
  components: {
    RegisterForm
  },
  data() {
    return {
      username: '',
      password: '',
      isChecked: false,
      loading: false
    };
  },
  methods: {
    async submitForm() {
      this.loading = true;
      try {
        const payload = this.isChecked ? { uid: -1, uname: "admin", upassword: this.password } : { uid: -1, uname: this.username, upassword: this.password };
        const path = this.isChecked ? '/admin/login' : '/user/login';
        const response = await this.$axios.post(path, payload);
        if (response.data.success) {
          this.$router.push(this.isChecked ? '/admin-home' : '/home');
        } else {
          alert('用户名或密码错误');
        }
      } catch (error) {
        alert('网络故障');
      }
      this.loading = false;
    },
    openRegisterForm() {
      this.$refs.registerForm.open();
    }
  }
}
</script>

<style scoped>
.login-background {
  background-image: url('../assets/images/bg1.jpg');
  background-size: cover;
  background-position: center;
  height: 100vh;
}

.login-card {
  background: rgba(255, 255, 255, 0.9);
}
</style>