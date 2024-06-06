import { createRouter, createWebHashHistory } from 'vue-router'; // 使用 createWebHashHistory
import Login from '../components/LoginPage.vue';
import Home from '../components/HomePage.vue';
import PostPage from '@/components/PostPage.vue';

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/home',
        name: 'Home',
        component: Home
    },
    {
        path: '/posttopic',
        name: 'Post',
        component: PostPage
    }
];

const router = createRouter({
    history: createWebHashHistory(), // 使用 createWebHashHistory
    routes
});

export default router;
