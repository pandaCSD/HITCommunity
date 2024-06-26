<template>
  <v-data-table
      :headers="headers"
      :items="friends"
      :search="search"
  >
    <!-- 表格顶部插槽 -->
    <template #top>
        <v-toolbar flat>
            <v-toolbar-title>好友列表</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon @click="fetchFriends()">
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
    
    <template v-slot:[`item.delete`]="{ item }">
        <v-btn icon @click="removeFriend(item.uid)">
            <v-icon color="red">mdi-trash-can</v-icon>
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
  name: 'FriendListPage',
  mounted() {
    this.fetchFriends();
  },
  components: {
    UserCard
  },
  data() {
    return {
      friends: [],
      search: '',
      selectedUser: {}, // 初始化selectedUser为一个空对象
      dialog: false,     // 初始化dialog为false，控制对话框的显示
      headers: [
        { title: '用户ID', value: 'uid', sortable: true },
        { title: '用户名', value: 'uname', sortable: true },
        { title: '用户名片', key: 'eye', sortable: false },
        { title: '删除好友', key: 'delete', sortable: false },
      ],
    };
  },
  methods: {
    openUserCard(user) {
      this.selectedUser = user;
      this.dialog = true;  // 打开对话框
    },
    async fetchFriends() {
      try {
        const response = await this.$axios.get('/user/friends');
        if (response.data.success) {
          this.friends = response.data.data;
        } else {
          console.error('Failed to fetch friends:', response.data.message);
          alert('Failed to fetch friends: ' + response.data.message);
        }
      } catch (error) {
        console.error('Error fetching friends:', error);
        alert('An error occurred while fetching friends.');
      }
    }, 
    async removeFriend(uid) {
      try {
        const response = await this.$axios.delete(`/user/friends/${uid}`);
        if (response.data.success) {
          this.friends = this.friends.filter(f => f.uid !== uid);
        } else {
          console.error('Failed to delete friend:', response.data.message);
          alert('Failed to delete friend: ' + response.data.message);
        }
      } catch (error) {
        console.error('Error deleting friend:', error);
        alert('An error occurred while deleting friend. Please try again later.');
      }
    },
  },
};
</script>
