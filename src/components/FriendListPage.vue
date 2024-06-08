<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-btn color="primary" @click="fetchFriends">刷新朋友列表</v-btn>
        <v-list>
          <v-list-item v-for="friend in friends" :key="friend.uid">
            <v-list-item-content>
              <v-list-item-title>用户名:{{ friend.uname }}</v-list-item-title>
              <v-list-item-title>用户ID: {{ friend.uid }}</v-list-item-title>
            </v-list-item-content>
            <v-btn small color="error" @click="removeFriend(friend.uid)" style="font-size: 12px; padding: 4px 8px;">
              删除好友
            </v-btn>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'FriendListPage',
  data() {
    return {
      friends: [],
    };
  },
  methods: {
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
