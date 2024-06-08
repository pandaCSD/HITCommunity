import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';
import router from './router';
import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';

// 创建 axios 实例并配置默认值
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true // 确保发送请求时携带 cookies
});

// 加载字体
loadFonts();

// 创建 Vue 应用实例
const app = createApp(App);

// 使用路由和 Vuetify
app.use(router);
app.use(vuetify);

// 将 axios 实例添加到 Vue 全局属性，这样在组件中可以通过 this.$axios 访问
app.config.globalProperties.$axios = axiosInstance;

// 挂载 Vue 应用
app.mount('#app');
