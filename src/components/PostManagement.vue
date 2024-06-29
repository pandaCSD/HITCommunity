<template>
  <v-data-table
    :headers="headers"
    :items="posts"
    :search="search"
  >
    <!-- 表格顶部插槽 -->
    <template #top>
        <v-toolbar flat>
          <v-toolbar-title>帖子列表</v-toolbar-title>
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
    <template v-slot:[`item.delete`]="{ item }">
        <v-btn icon @click="deletePost(item)">
          <v-icon color="red">mdi-trash-can</v-icon>
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
      posts: [],
      headers: [
        { title: '帖子ID', value: 'pid', sortable: true },
        { title: '时间', value: 'ptime', sortable: true },
        { title: '发布者ID', value: 'powner', sortable: true},
        { title: '帖子标题', value: 'title', sortable: false},
        { title: '帖子详情', key: 'eye', sortable: false },
        { title: '删除帖子', key: 'delete', sortable: false },
      ],
    };
  },
  methods: {
    async refresh(){
      try {
        const response = await this.$axios.get('/admin/all-post');
        if (response.data.success) {
          this.posts = response.data.data.map(post => ({
              pid: post.pid,
              ptime: post.ptime,
              powner: post.powner.uid,
              title: post.title,
            }));
        }
      } catch (error) {
        alert("网络错误");
      }
    },
    openPostDetail(item) {
      this.$router.push({name:'PostDetail',params:{id: item.pid}});
    },
    async deletePost(item) {
      try {
        const response = await this.$axios.delete('/admin/post/'+item.pid);
        if (response.data.success) {
          alert("删除成功");
        } else {
          alert("删除失败");
        }
      } catch (error) {
        alert("网络错误");
      }
    }
  },
};
</script>