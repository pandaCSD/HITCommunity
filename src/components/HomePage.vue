<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" app permanent>
      <v-list>
      <v-list-item-group>
        <v-list-item 
          v-for="item in menuItems" 
          :key="item.title"
          @click="handleClick(item)">
          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>

          <v-list-item-action v-if="item.children">
            <v-icon>mdi-chevron-down</v-icon>
          </v-list-item-action>

          <v-list-item-group v-if="item.children">
            <v-list-item 
              v-for="child in item.children" 
              :key="child.title"
              @click="currentComponent = child.component"
              >
              <v-list-item-content>
                <v-list-item-title v-text="child.title"></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list-item>
      </v-list-item-group>
    </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>哈工大圈子</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <keep-alive>
        <component :is="currentComponent"></component>
      </keep-alive>
    </v-main>
  </v-app>
</template>

<script>
import UserPage from '@/components/UserPage.vue';
import WelcomePage from '@/components/WelcomePage.vue';
import FriendListPage from '@/components/FriendListPage.vue'
import FriendRequestPage from '@/components/FriendRequestPage.vue'
import FriendFindPage from '@/components/FriendFindPage.vue'

export default {
  name: 'MainPage',
  components: {
    WelcomePage,
    UserPage,
    FriendListPage,
    FriendRequestPage,
    FriendFindPage,
  },
  data() {
    return {
      drawer: true, // 默认保持抽屉打开
      currentComponent: WelcomePage, // 当前显示的组件
      menuItems: [
        { title: '欢迎', component: 'WelcomePage'},
        { title: '用户', component: 'UserPage' },
        // 这里可以添加更多的菜单项
        {
          title: '朋友',
          icon: 'mdi-folder',
          children: [
            { title: '好友列表', component: 'FriendListPage' },
            { title: '好友搜索', component: 'FriendFindPage' },
            { title: '好友申请', component: 'FriendRequestPage' },
          ]
        },
      ],
    }
  },
  methods: {
    handleClick(item) {
      // Change the component only if there are no children
      if (!item.children) {
        this.currentComponent = item.component;
      }
    }
  }
};
</script>

<style scoped>
/* Your styles here */
</style>