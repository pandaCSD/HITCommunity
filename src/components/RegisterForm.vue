<template>
  <v-dialog v-model="isVisible" persistent max-width="400px">
    <v-card>
      <v-card-title class="text-h5">注册</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="register">
          <v-text-field
            v-model="username"
            label="用户名"
            prepend-icon="mdi-account"
            outlined
            required
            autocomplete="username"
          ></v-text-field>
          <v-text-field
            v-model="password"
            label="密码"
            prepend-icon="mdi-lock"
            type="password"
            outlined
            required
            autocomplete="new-password"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="grey darken-1" text @click="close">取消</v-btn>
        <v-btn color="primary" depressed @click="register">注册</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'RegisterForm',
  data() {
    return {
      isVisible: false,
      username: '',
      password: ''
    };
  },
  methods: {
    async register() {
      try {
        const response = await this.$axios.post('/user/register', {
          uid: -1,
          uname: this.username,
          upassword: this.password
        });
        if (response.data.success) {
          alert('注册成功');
          this.close(); // 关闭对话框
        } else {
          alert('注册失败');
        }
      } catch (error) {
        alert('注册失败');
      }
    },
    close() {
      this.isVisible = false;
    },
    open() {
      this.isVisible = true;
    }
  }
}
</script>