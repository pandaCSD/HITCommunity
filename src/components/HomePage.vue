<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" app permanent>
      <!-- <v-list>
        <v-list-item
          v-for="item in menuItems"
          :key="item.title"
          @click="currentComponent = item.component"
        >
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list> -->
      <v-list>
      <v-list-item-group>
        <v-list-item 
          v-for="item in menuItems" 
          :key="item.title"
          @click="currentComponent = item.component">
          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>

          <v-list-item-action v-if="item.children">
            <v-icon>mdi-chevron-down</v-icon>
          </v-list-item-action>

          <v-list-item-action v-else>
            <v-icon>mdi-chevron-right</v-icon>
          </v-list-item-action>

          <v-list-item-group v-if="item.children">
            <v-list-item v-for="(child, i) in item.children" :key="i">
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
      <v-toolbar-title>Application bar</v-toolbar-title>
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

export default {
  name: 'MainPage',
  components: {
    WelcomePage,
    UserPage,
  },
  data() {
    return {
      drawer: true, // 默认保持抽屉打开
      currentComponent: null, // 当前显示的组件
      menuItems: [
        { title: 'UserPage', component: 'UserPage' },
        // 这里可以添加更多的菜单项
        { title: 'WelcomePage', component: 'WelcomePage'},
        {
          title: 'A栏目',
          icon: 'mdi-folder',
          children: [
            { title: 'UserPage', component: 'UserPage' },
            { title: 'WelcomePage', component: 'WelcomePage'},
          ]
        },
      ],
    };
  },
};
</script>

<style scoped>
/* Your styles here */
</style>
