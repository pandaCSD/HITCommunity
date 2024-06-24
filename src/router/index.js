import { createRouter, createWebHashHistory } from 'vue-router'; // 使用 createWebHashHistory
import Login from '../components/LoginPage.vue';
import Home from '../components/HomePage.vue';
import PostPage from '@/components/PostPage.vue';
import PostDetail from '@/components/PostDetail.vue';
import AdminHome from '@/components/AdminHomePage.vue'

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
        path: '/admin-home',
        name: 'AdminHome',
        component: AdminHome
    },
    {
        path: '/posttopic',
        name: 'Post',
        component: PostPage
    },
    {
        path: '/post/:id',
        name: 'PostDetail',
        component: PostDetail,
    },
];

const router = createRouter({
    history: createWebHashHistory(), // 使用 createWebHashHistory
    routes
});

export default router;
