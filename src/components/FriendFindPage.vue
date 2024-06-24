<template>
  <v-container>
    <v-row>
      <v-col cols="8">
        <v-text-field
          label="用户ID或用户名"
          v-model="searchQuery"
        ></v-text-field>
      </v-col>
      <v-col cols="4">
        <v-btn color="primary" @click="searchUser()">
          搜索
        </v-btn>
      </v-col>
    </v-row>
    <v-row v-if="searchResults.length">
      <v-col cols="12">
        <v-list>
          <v-list-item v-for="user in searchResults" :key="user.uid" @click="openUserCard(user)">
            <v-list-item-content>
              <v-list-item-title>{{ user.uname }}</v-list-item-title>
              <v-list-item-subtitle>UID: {{ user.uid }}</v-list-item-subtitle>
            </v-list-item-content>
            <v-btn color="primary" @click.stop="sendFriendRequest(user.uid)">
              发送好友申请
            </v-btn>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
  <!-- 添加v-dialog -->
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
  name: 'FriendFindPage',
  components: {
    UserCard
  },
  data() {
    return {
      searchResults: [],
      searchQuery: '',
      selectedUser: {}, // 初始化selectedUser为一个空对象
      dialog: false,     // 初始化dialog为false，控制对话框的显示
    };
  },
  methods: {
    openUserCard(user) {
      this.selectedUser = user;
      this.dialog = true;  // 打开对话框
    },
    async searchUser() {
      const u = this.searchQuery;
      // 搜索逻辑
      if(!isNaN(u)) {
        try {
          const response = await this.$axios.get(`/user/find/uid/`+u);
          if (response.data.success) {
            this.searchResults.push(response.data.data);
          }
        } catch (error) {
          alert('网络错误');
        }
      }
      try {
        const response = await this.$axios.get(`/user/find/uname/`+u);
        if (response.data.success) {
          const newUid = response.data.data.uid;
          const exists = this.searchResults.some(element => element.uid === newUid);
          // 如果不存在，则将新的数据加入列表
          if (!exists) {
            this.searchResults.push(response.data.data);
          }
        }
      } catch (error) {
        alert('网络错误');
      }
    },
    async sendFriendRequest(uid) {
      // 实际的发送好友申请逻辑
      try {
        const response = await this.$axios.post(`/user/friend-requests/send/`+uid);
        if (response.data.success) {
          this.searchResults = this.searchResults.filter(f => f.uid !== uid);
        } else {
          alert('发送好友申请失败');
        }
      } catch (error) {
        alert('网络错误');
      }
    },
  },
};
</script>