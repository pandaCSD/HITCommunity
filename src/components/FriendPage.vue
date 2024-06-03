<template>
    <div class="friend-page">
      <v-container>
        <v-row>
          <!-- 申请朋友信息模块 -->
          <v-col cols="12" md="6">
            <v-card>
              <v-card-title>申请朋友信息</v-card-title>
              <v-card-text>
                <v-form @submit.prevent="applyForFriendship">
                  <v-text-field
                    v-model="newFriend.name"
                    label="朋友的名字"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="newFriend.email"
                    label="朋友的邮箱"
                    required
                  ></v-text-field>
                  <v-btn color="primary" type="submit">申请</v-btn>
                </v-form>
              </v-card-text>
            </v-card>
          </v-col>
  
          <!-- 展示所有朋友模块 -->
          <v-col cols="12" md="6">
            <v-card>
              <v-card-title>所有朋友</v-card-title>
              <v-card-text>
                <v-list>
                  <v-list-item v-for="(friend, index) in friends" :key="index">
                    <v-list-item-content>
                      <v-list-item-title>{{ friend.name }}</v-list-item-title>
                      <v-list-item-subtitle>{{ friend.email }}</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </template>
  
  <script>
  export default {
    name: 'FriendPage',
    data() {
      return {
        newFriend: {
          name: '',
          email: '',
        },
        friends: [],
      };
    },
    methods: {
      applyForFriendship() {
        if (this.newFriend.name && this.newFriend.email) {
          this.friends.push({ ...this.newFriend });
          this.newFriend.name = '';
          this.newFriend.email = '';
          // 这里可以添加更多逻辑，例如发送请求到服务器
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .friend-page {
    padding: 20px;
  }
  </style>
  