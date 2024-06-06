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
      <p class="item-topic">{{ item.topic }}</p>
      <p class="item-activity">活跃时间：{{ item.activity }}</p>
      <p class="item-replies">评论数：{{ item.replies }}</p>
      <p class="item-views">热度：{{ item.views }}</p>
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
  export default {
    data() {
      return {
        search: '',
        headers: [
          { text: '主题', value: 'topic' },
          { text: '回复', value: 'replies' },
          { text: '查看', value: 'views' },
          { text: '活动', value: 'activity' },
          { text: '操作', value: 'action', sortable: false },
        ],
        topics: [
          {
            topic: '欢迎来到 Rust 编程语言用户论坛',
            replies: 3,
            views: '42.7k',
            activity: '2022年6月',
          },
          {
            topic: '论坛代码格式化和语法高亮',
            replies: 5,
            views: '42.7k',
            activity: '2020年5月',
          },
          {
            topic: 'Rayon 并发写入数组？',
            replies: 10,
            views: 106,
            activity: '4分钟',
          },
          {
            topic: '我想在实时视频中检测图像',
            replies: 2,
            views: 309,
            activity: '1小时',
          },
          {
            topic: '关于多个 match 表达式的潜在优化遗漏',
            replies: 4,
            views: 220,
            activity: '4小时',
          },
        ],
      };
    },
    methods: {
      createNewTopic() {
        // 创建新主题的逻辑
        // alert('将在此处实现新主题创建功能。');
        this.$router.push("/posttopic");
      },
      goToTopic(item) {
        // 导航到主题的逻辑
        alert(`导航到主题: ${item.topic}`);
      },
    },
  };
  </script>
  
  <style>
  body {
    font-family: Arial, sans-serif;
  }
  </style>
  
<style>
.item-container {
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
  transition: background-color 0.3s ease;
}

.item-container:hover {
  background-color: #f9f9f9;
}

.v-btn {
  margin-right: 10px;
}

.item-content {
  display: flex;
  flex-direction: column;
}

.item-content p {
  margin: 2px 0;
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
</style>