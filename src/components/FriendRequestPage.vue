<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <!-- <h1>好友请求</h1> -->
        <v-btn color="primary" @click="fetchRequests">刷新请求列表</v-btn>
        <v-list>
          <v-list-item v-for="request in requests" :key="request.suid" @click="openUserCard(request)">
            <v-list-item-content>
              <v-list-item-title>用户ID: {{ request.uid }}</v-list-item-title>
              <v-list-item-subtitle>用户名: {{ request.uname }}</v-list-item-subtitle>
              <!-- <v-list-item-subtitle>请求时间: {{ request.requestTime }}</v-list-item-subtitle> -->
            </v-list-item-content>
            <v-list-item-action>
              <v-btn color="green" @click.stop="acceptRequest(request.uid)">接受</v-btn>
              <v-btn color="red" @click.stop="rejectRequest(request.uid)">拒绝</v-btn>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
  <v-dialog v-model="dialog" persistent max-width="500px">
    <UserCard :uid="selectedUser.uid" :uname="selectedUser.uname" />
    <v-card-actions>
      <v-btn color="red" text @click="dialog = false">Close</v-btn>
    </v-card-actions>
  </v-dialog>
</template>

<script>
import UserCard from '@/components/UserCard.vue';

export default {
  name: 'FriendRequestPage',
  components: {
    UserCard
  },
  data() {
    return {
      requests: [],
      selectedUser: {}, // 初始化selectedUser为一个空对象
      dialog: false,     // 初始化dialog为false，控制对话框的显示
    };
  },
  mounted() {
    this.fetchRequests();
  },
  methods: {
    openUserCard(user) {
      this.selectedUser = user;
      this.dialog = true;  // 打开对话框
    },
    async fetchRequests() {
      try {
        const response = await this.$axios.get('/user/friend-requests');
        if (response.data.success) {
          this.requests = response.data.data;
        } else {
          alert("网络错误");
        }
      } catch (error) {
        alert("网络错误");
      }
    },
    async acceptRequest(suid) {
      // 发送接受请求的API调用
      try {
        const response = await this.$axios.post('/user/friend-requests/accept/'+suid);
        if (!response.data.success) {
          alert("网络错误");
        }
      } catch (error) {
        alert("网络错误");
      }
    },
    async rejectRequest(suid) {
      // 发送拒绝请求的API调用
      try {
        const response = await this.$axios.post('/user/friend-requests/reject/'+suid);
        if (!response.data.success) {
          alert("网络错误");
        }
      } catch (error) {
        alert("网络错误");
      }
    }
  }
};
</script>
