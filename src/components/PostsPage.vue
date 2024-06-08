<template>
  <v-app>
    <v-container>
      <v-row>
        <v-col>
          <v-btn color="primary" class="my-4" @click="createNewTopic">
            新主题
          </v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-data-table
            :headers="headers"
            :items="topics"
            class="elevation-1"
            :search="search"
          >
            <!-- 表格顶部插槽 -->
            <template #top>
              <v-toolbar flat>
                <v-toolbar-title>最新</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="搜索"
                  single-line
                  hide-details
                ></v-text-field>
              </v-toolbar>
            </template>

            <template v-slot:item="{ item }">
              <div class="item-container">
                <v-btn icon @click="goToTopic(item)">
                  <v-icon>mdi-eye</v-icon>
                </v-btn>
                <div class="item-content">
                  <div class="item-field topic-field">
                    <span class="item-topic">{{ item.topic }}</span>
                  </div>
                  <div class="item-field">
                    <span class="item-activity">活跃时间：{{ item.activity }}</span>
                  </div>
                  <div class="item-field">
                    <span class="item-replies">评论数：{{ item.replies }}</span>
                  </div>
                  <div class="item-field">
                    <span class="item-views">热度：{{ item.views }}</span>
                  </div>
                </div>
              </div>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
// import axios from 'axios';
export default {
  async mounted(){
    try {
      const response = await this.$axios.get('/post/allposts');
      
      // 确认请求成功，并打印完整的响应数据
      console.log('get posts successful:', response.data);
      
      // 检查响应数据的格式
      if (response.data.success) {
        console.log('Posts data:', response.data.data);
        // 这里可以将 posts 数据保存到组件的状态中
        this.posts = response.data.data;
        // 将posts的数据存入topics中
        this.posts.forEach(post => {
          this.topics.push({
            topic: post.title,
            replies: post.replies || 0,
            views: post.views || 0,
            activity:post.ptime,
            id: post.pid,
            owner: post.owner,
            content: post.text,
          });
        });
      } else {
        console.error('Unexpected response format:', response.data);
      }
    } catch (error) {
      console.error('getting posts error: ', error);
    }
  },
  data() {
    return {
      search: '',
      headers: [
      ],
      topics: [
      ],
    };
  },
  methods: {
    createNewTopic() {
      // 创建新主题的逻辑
      this.$router.push("/posttopic");
    },
    goToTopic(item) {
      // 导航到主题的逻辑
      // alert(`导航到主题: ${item.topic}`);
      this.$router.push({name:'PostDetail',params:{id: item.id}});
    },
  },
};
</script>

<style>
body {
  font-family: Arial, sans-serif;
}

.item-container {
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
  transition: background-color 0.3s ease;
  width: 100%;
}

.item-container:hover {
  background-color: #f9f9f9;
}

.v-btn {
  margin: 0;
  margin-right: 10px;
}

.item-content {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.item-field {
  flex: 1;
  padding: 0 10px;
}

.topic-field {
  flex: 2; /* 增加第一个字段的宽度 */
  padding-right: 20px; /* 增加第一个字段与其他字段之间的间隔 */
}

.item-topic {
  font-weight: bold;
  font-size: 1.1em;
}

.item-activity,
.item-replies,
.item-views {
  color: #757575;
  font-size: 0.9em;
}

/* 新增样式 */
.v-toolbar {
  background-color: #fafafa;
  color: #424242;
}

.v-data-table {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
}

.v-data-table__actions {
  background-color: #f5f5f5;
}

.v-data-table__actions .v-toolbar__content {
  padding: 0 16px;
}

.v-data-table__actions .v-text-field {
  margin: 0;
}

.v-btn--primary {
  background-color: #3f51b5 !important;
}

.v-icon {
  color: #3f51b5;
}
</style>