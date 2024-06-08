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
            Search
          </v-btn>
        </v-col>
      </v-row>
      <v-row v-if="searchResults.length">
        <v-col cols="12">
          <v-list>
            <v-list-item v-for="user in searchResults" :key="user.uid">
              <v-list-item-content>
                <v-list-item-title>{{ user.uname }}</v-list-item-title>
                <v-list-item-subtitle>UID: {{ user.uid }}</v-list-item-subtitle>
              </v-list-item-content>
              <v-btn color="primary" @click="sendFriendRequest(user.uid)">
                发送好友申请
              </v-btn>
            </v-list-item>
          </v-list>
        </v-col>
      </v-row>
      <v-row v-else>
        <v-col cols="12">
          <v-alert type="info" text>
            用户不存在
          </v-alert>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    name: 'FriendFindPage',
    data() {
      return {
        searchResults: [],
        searchQuery: ''
      };
    },
    methods: {
      async searchUser() {
        const u = this.searchQuery;
        // 模拟搜索逻辑
        if(!isNaN(u)) {
          try {
            const response = await this.$axios.get(`/user/find/uid/`+u);
            if (response.data.success) {
              this.searchResults.push(response.data.data);
            }
          } catch (error) {
            console.error('Error finding user:', error);
            alert('An error occurred while finding user. Please try again later.');
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
          console.error('Error finding user:', error);
          alert('An error occurred while finding user. Please try again later.');
        }
      },
      async sendFriendRequest(uid) {
        // 实际的发送好友申请逻辑
        try {
          const response = await this.$axios.post(`/user/friend-requests/send/`+uid);
          if (response.data.success) {
            this.searchResults = this.searchResults.filter(f => f.uid !== uid);
          } else {
            console.error('Failed to send request:', response.data.message);
            alert('Failed to send request:' + response.data.message);
          }
        } catch (error) {
          console.error('Error sending request:', error);
          alert('An error occurred while sending request. Please try again later.');
        }
      },
    },
  };
  </script>
  
  <style>
  .v-btn {
    margin-top: 25px; /* 确保按钮与搜索框在同一水平线上 */
  }
  </style>
  