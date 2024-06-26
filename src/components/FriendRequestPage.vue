<template>
  <v-data-table
      :headers="headers"
      :items="requests"
      :search="search"
  >
    <!-- 表格顶部插槽 -->
    <template #top>
        <v-toolbar flat>
            <v-toolbar-title>好友申请</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon @click="fetchRequests()">
                <v-icon>mdi-refresh</v-icon>
            </v-btn>
            <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
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
    <template v-slot:[`item.accept`]="{ item }">
        <v-btn icon @click="acceptRequest(item.uid)">
            <v-icon color="green">mdi-check</v-icon>
        </v-btn>
    </template>
    <template v-slot:[`item.reject`]="{ item }">
        <v-btn icon @click="rejectRequest(item.uid)">
            <v-icon color="red">mdi-close</v-icon>
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
  name: 'FriendRequestPage',
  components: {
    UserCard
  },
  data() {
    return {
      search: '',
      requests: [],
      selectedUser: {}, // 初始化selectedUser为一个空对象
      dialog: false,    // 初始化dialog为false，控制对话框的显示
      headers: [
        { title: '用户ID', value: 'uid', sortable: true },
        { title: '用户名', value: 'uname', sortable: true },
        { title: '用户名片', key: 'eye', sortable: false },
        { title: '接受申请', key: 'accept', sortable: false },
        { title: '拒绝申请', key: 'reject', sortable: false },
      ],
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
