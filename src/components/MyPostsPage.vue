<template>
    <v-data-table
      :headers="headers"
      :items="posts"
      :search="search"
    >
      <!-- 表格顶部插槽 -->
      <template #top>
          <v-toolbar flat>
            <v-toolbar-title>我的帖子</v-toolbar-title>
            <v-spacer></v-spacer>
            发布
            <v-btn icon @click="this.dialog = true">
                <v-icon>mdi-plus-box</v-icon>
            </v-btn>
            刷新
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
          <v-btn icon @click="deletePost(item.pid)">
            <v-icon color="red">mdi-trash-can</v-icon>
          </v-btn>
      </template>
    </v-data-table>

    <v-data-table
      :headers="repost_headers"
      :items="reposts"
      :search="search"
    >
      <!-- 表格顶部插槽 -->
      <template #top>
          <v-toolbar flat>
            <v-toolbar-title>我的转发</v-toolbar-title>
            <v-spacer></v-spacer>
            刷新
            <v-btn icon @click="refreshRepost()">
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
          <v-btn icon @click="deleteRepost(item.rid)">
            <v-icon color="red">mdi-trash-can</v-icon>
          </v-btn>
      </template>
    </v-data-table>

    <v-dialog v-model="dialog" persistent max-width="600px">
        <v-btn @click="dialog = false">
            <v-icon color="red">mdi-close</v-icon>
        </v-btn>
        <PostPage></PostPage>
    </v-dialog>
</template>
    
<script>  
import PostPage from '@/components/PostPage.vue'
export default {
    name: 'MyPostsPage',
    components: {
        PostPage
    },
    async mounted(){
      this.refresh();
      this.refreshRepost();
    },
    data() {
      return {
        search: '',
        posts: [],
        reposts: [],
        dialog: false,
        headers: [
            { title: '帖子ID', value: 'pid', sortable: true },
            { title: '发布时间', value: 'ptime', sortable: true },
            { title: '帖子标题', value: 'title', sortable: false},
            { title: '帖子详情', key: 'eye', sortable: false },
            { title: '删除帖子', key: 'delete', sortable: false },
        ],
        repost_headers:[
            { title: '帖子ID', value: 'pid', sortable: true},
            { title: '转发时间', value: 'rtime', sortable: true},
            { title: '帖子标题', value: 'title', sortable: false},
            { title: '帖子详情', key: 'eye', sortable: false },
            { title: '撤销转发', key: 'delete', sortable: false },
        ]
      };
    },
    methods: {
    async refresh(){
      try {
        const response = await this.$axios.get('/post/myposts');
        if (response.data.success) {
          this.posts = response.data.data.map(post => ({
              pid: post.pid,
              ptime: post.ptime,
              title: post.title,
          }));
        }
      } catch (error) {
          alert("网络错误");
      }
    },
    async refreshRepost(){
        try {
          const response = await this.$axios.get('/post/myreposts');
          if (response.data.success) {
            this.reposts = response.data.data.map(repost => ({
                rid: repost.rid,
                pid: repost.originalPost.pid,
                title: repost.originalPost.title,
                rtime: repost.rtime,
            }));
        }
        } catch (error) {
            alert("网络错误");
        }
    },
    openPostDetail(item) {
      this.$router.push({name:'PostDetail',params:{id: item.pid}});
    },
    async deletePost(pid) {
        try {
            const response = await this.$axios.delete('/post/post/'+pid);
            if (response.data.success) {
              alert("撤销成功");
            } else {
              alert("撤销失败");
            }
        } catch (error) {
            alert("网络错误");
        }
    },
    async deleteRepost(rid) {
        try {
            const response = await this.$axios.delete('/post/repost/'+rid);
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