<template>
  <v-data-table
      :headers="headers"
      :items="searchResults"
  >
    <!-- 表格顶部插槽 -->
    <template #top>
        <v-toolbar flat>
            <v-toolbar-title>好友搜索</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon @click="searchUser()">
                <v-icon>mdi-magnify</v-icon>
            </v-btn>
            <v-text-field
                v-model="searchQuery"
                label="搜索"
                single-line
                hide-details
            ></v-text-field>
        </v-toolbar>
    </template>
    <template v-slot:[`item.eye`]="{ item  }">
        <v-btn icon @click="openUserCard(item)">
                <v-icon>mdi-eye</v-icon>
        </v-btn>
    </template>
    <template v-slot:[`item.add`]="{ item }">
        <v-btn icon @click="sendFriendRequest(item.uid)">
          <v-icon>mdi-account-plus</v-icon>
        </v-btn>
    </template>
  </v-data-table>

  <v-dialog v-model="dialog" persistent max-width="500px">
    <v-card-actions>
      <v-btn @click="dialog = false">
          <v-icon color="red">mdi-close</v-icon>
      </v-btn>
    </v-card-actions>
    <UserCard :uid="selectedUser.uid" :uname="selectedUser.uname" />
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
      headers: [
        { title: '用户ID', value: 'uid', sortable: true },
        { title: '用户名', value: 'uname', sortable: true },
        { title: '用户名片', key: 'eye', sortable: false },
        { title: '添加好友', key: 'add', sortable: false },
      ],
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
          alert('发送成功');
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