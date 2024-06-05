<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-text-field label="用户ID" v-model="uid" disabled></v-text-field>
      </v-col>
      <v-col cols="12">
        <v-text-field label="用户名" v-model="uname"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" @click="updateUsername">重设用户名</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-text-field label="旧密码" v-model="oldPassword" type="password"></v-text-field>
      </v-col>
      <v-col cols="12">
        <v-text-field label="新密码" v-model="newPassword" type="password"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" @click="updatePassword">重设密码</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "UserPage",
  data() {
    return {
      uid: "", // 初始化为空，稍后通过API获取
      uname: "", // 初始化为空，稍后通过API获取
      oldPassword: "",
      newPassword: ""
    };
  },
  methods: {
    async fetchUserData() {
      try{  
        const response = await this.$axios.get('/user/is-login');
        if (response.data.success) {
          this.uid = response.data.data.uid;
          this.uname = response.data.data.uname;
        } else {
          console.error('Fetch user failed:', response.data.message);
          alert('Fetch user failed: ' + response.data.message);
        }
      } catch (error) {
        console.error('Error during fetch user:', error);
        alert('An error occurred during fetch user.');
      }
      this.oldPassword = "";
      this.newPassword = "";
    },
    async updateUsername() {
      try{
        const response = await this.$axios.put('/user/update/username?uname='+ this.uname);
        if (response.data.success) {
          alert('更新用户名成功');
        } else {
          console.error('Update username failed:', response.data.message);
          alert('Update username failed: ' + response.data.message);
        }
      } catch (error) {
        console.error('Error during updating username:', error);
        alert('An error occurred during updating username.');
      }
      this.fetchUserData();
    },
    async updatePassword() {
      // 更新密码的逻辑
      try{  
        const response = await this.$axios.put(
          '/user/update/password?oldPassword='
          + this.oldPassword
          + '&newPassword='
          +this.newPassword
        );
        if (response.data.success) {
          alert('更新密码成功');
        } else {
          console.error('Update Password failed:', response.data.message);
          alert('Update Password failed: ' + response.data.message);
        }
      } catch (error) {
        console.error('Error during updating Password:', error);
        alert('An error occurred during updating Password.');
      }
      this.fetchUserData();
    }
  },
  mounted() {
    // 页面挂载时调用fetchUserData函数
    this.fetchUserData();
  }
};
</script>

<style scoped>
/* 可以在这里添加一些样式 */
</style>
