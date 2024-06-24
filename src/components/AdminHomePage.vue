<template>
    <v-app>
      <v-container>
        <v-row>
          <v-col cols="12" md="6">
            <v-card>
              <v-card-title>
                用户列表
                <v-spacer></v-spacer>
                <v-text-field
                  v-model="userSearch"
                  append-icon="mdi-magnify"
                  label="搜索"
                  single-line
                  hide-details
                ></v-text-field>
              </v-card-title>
              <v-card-text>
                <v-list>
                  <v-list-item-group v-model="selectedUser">
                    <v-list-item
                      v-for="user in filteredUsers"
                      :key="user.id"
                    >
                      <v-list-item-content>
                        <v-list-item-title>{{ user.name }}</v-list-item-title>
                      </v-list-item-content>
                      <v-list-item-action>
                        <v-btn icon @click="deleteUser(user.id)">
                          <v-icon>mdi-delete</v-icon>
                        </v-btn>
                      </v-list-item-action>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>
              </v-card-text>
            </v-card>
          </v-col>
          
          <v-col cols="12" md="6">
            <v-card>
              <v-card-title>
                帖子列表
                <v-spacer></v-spacer>
                <v-text-field
                  v-model="postSearch"
                  append-icon="mdi-magnify"
                  label="搜索"
                  single-line
                  hide-details
                ></v-text-field>
              </v-card-title>
              <v-card-text>
                <v-list>
                  <v-list-item-group v-model="selectedPost">
                    <v-list-item
                      v-for="post in filteredPosts"
                      :key="post.id"
                    >
                      <v-list-item-content>
                        <v-list-item-title>{{ post.title }}</v-list-item-title>
                      </v-list-item-content>
                      <v-list-item-action>
                        <v-btn icon @click="deletePost(post.id)">
                          <v-icon>mdi-delete</v-icon>
                        </v-btn>
                      </v-list-item-action>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-app>
  </template>
  
  <script>
  export default {
    data: () => ({
      userSearch: '',
      postSearch: '',
      selectedUser: null,
      selectedPost: null,
      users: [
        { id: 1, name: 'Alice' },
        { id: 2, name: 'Bob' },
        { id: 3, name: 'Charlie' }
      ],
      posts: [
        { id: 1, title: 'Post 1' },
        { id: 2, title: 'Post 2' },
        { id: 3, title: 'Post 3' }
      ]
    }),
    computed: {
      filteredUsers() {
        return this.users.filter(user =>
          user.name.toLowerCase().includes(this.userSearch.toLowerCase())
        );
      },
      filteredPosts() {
        return this.posts.filter(post =>
          post.title.toLowerCase().includes(this.postSearch.toLowerCase())
        );
      }
    },
    methods: {
      deleteUser(id) {
        this.users = this.users.filter(user => user.id !== id);
      },
      deletePost(id) {
        this.posts = this.posts.filter(post => post.id !== id);
      }
    }
  }
  </script>
  