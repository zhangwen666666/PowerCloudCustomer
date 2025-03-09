// 从vue-router依赖库中导入createRouter,createWebHistory两个函数
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
    // 路由历史，能记录下访问过的历史记录
    history:createWebHistory(),
    // 配置路由
    routes:[
        {
            path: '/', // 路由路径
            component : () => import('../view/LoginView.vue'), // 路由路径所对应的页面
        },
        {
            path: '/dashboard',
            component : () => import('../view/DashboardView.vue'),
        }
    ]
})

export default router;