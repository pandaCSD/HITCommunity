<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" @click="fetchFriends">刷新朋友列表</v-btn>
        <v-list>
          <v-list-item v-for="friend in friends" :key="friend.uid" @click="openUserCard(friend)">
            <v-list-item-content>
              <v-list-item-title>用户名:{{ friend.uname }}</v-list-item-title>
              <v-list-item-title>用户ID: {{ friend.uid }}</v-list-item-title>
            </v-list-item-content>
            <v-btn small color="error" @click.stop="removeFriend(friend.uid)" style="font-size: 12px; padding: 4px 8px;">
              删除好友
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
  name: 'FriendListPage',
  components: {
    UserCard
  },
  data() {
    return {
      friends: [],
      selectedUser: {}, // 初始化selectedUser为一个空对象
      dialog: false,     // 初始化dialog为false，控制对话框的显示
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
