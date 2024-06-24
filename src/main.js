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

app.config.globalProperties.$uploadImage = async function (url, file) {
    const formData = new FormData();
    formData.append('file', file);
    try {
      await this.$axios.post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
    } catch (error) {
      console.error('Error uploading image:', error);
    }
};

app.config.globalProperties.$downloadImage = async function(url) {
    try {
        // 使用axios获取图像数据
        const response =  await this.$axios.get(url, {
            responseType: 'blob'  // 设置响应类型为blob，以便接收二进制文件
        });
        // 返回Blob对象给调用者
        return response.data;
    } catch (error) {
        console.error('Error downloading image:', error);
        return null; // 在发生错误时返回null，或者抛出错误等其他处理方式
    }
};

// 挂载 Vue 应用
app.mount('#app');