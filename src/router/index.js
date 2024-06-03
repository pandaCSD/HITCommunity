import { createRouter, createWebHashHistory } from 'vue-router'; // 使用 createWebHashHistory
import Login from '../components/LoginPage.vue';
import Home from '../components/HomePage.vue';

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
    }
];

const router = createRouter({
    history: createWebHashHistory(), // 使用 createWebHashHistory
    routes
});

export default router;
