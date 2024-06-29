<template>
  <v-data-table
    :headers="headers"
    :items="posts"
    :search="search"
  >
    <!-- 表格顶部插槽 -->
    <template #top>
        <v-toolbar flat>
          <v-toolbar-title>我的圈子</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon @click="refresh()">
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
        <v-btn icon @click="openPostDetail(item)">
          <v-icon>mdi-eye</v-icon>
        </v-btn>
    </template>
  </v-data-table>
</template>

<script>
export default {
  async mounted(){
    this.refresh();
  },
  data() {
    return {
      search: '',
      headers: [
        { title: '帖子详情', key: 'eye', sortable: false },
        { title: '帖子标题', value: 'title', sortable: false},
        { title: '发布者', value: 'powner', sortable: true},
        // { title: '帖子ID', value: 'pid', sortable: true },
        { title: '时间', value: 'ptime', sortable: true },
      ],
      posts: [],
    };
  },
  methods: {
    openPostDetail(item) {
      this.$router.push({name:'PostDetail',params:{id: item.pid}});
    },
    async refresh(){
      try {
        const response = await this.$axios.get('/post/mycircle');
        if (response.data.success) {
          this.posts = response.data.data.map(post => ({
              pid: post.pid,
              ptime: post.ptime,
              powner: post.powner.uname,
              title: post.title,
            }));
        }
      } catch (error) {
        alert("网络错误");
      }
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
  flex: 2;
  padding-right: 20px;
}

.repost-field {
  flex: 1;
  padding-right: 20px;
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
