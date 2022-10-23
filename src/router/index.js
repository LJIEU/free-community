import Vue from 'vue'
import VueRouter from 'vue-router'
import getPageTitle from '@/util/get-page-title'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        component: () => import('@/views/login/LoginOrRegister'),
        meta: {title: '登录'}
    },
    {
        path: '/',
        component: () => import('@/views/Index'),
        redirect: '/home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/views/home/ForumHome'),
                meta: {title: '首页'}
            },
            {
                path: '/tag/:name',
                name: 'tag',
                component: () => import('@/views/tag/Tag'),
                meta: {title: '标签'}
            },
            {
                path: '/learning',
                name: 'learning',
                component: () => import('@/views/learning/Learning'),
                meta: {title: '学习'}
            },
            {
                path: '/find',
                name: 'find',
                component: () => import('@/views/find/Find'),
                meta: {title: '发现'}
            },
            {
                path: '/question',
                name: 'question',
                component: () => import('@/views/question/Question'),
                meta: {title: '你问我答'}
            },
            {
                path: '/hot-list',
                name: 'hot-list',
                component: () => import('@/views/hotlist/HotList'),
                meta: {title: '首页'}
            },
            {
                path: '/video',
                name: 'video',
                component: () => import('@/views/video/Video'),
                meta: {title: '视频'}
            },
            {
                path: '/homePage',
                name: 'homePage',
                component: () => import('@/views/homepage/HomePage'),
                meta: {title: '主页'}
            }
        ]
    },
    {
        path: '/write',
        name: 'write',
        component: () => import('@/views/write/Write'),
        meta: {title: '写文章'}
    },
    /* 文章 */
    {
        path: '/article/:id',
        name: 'article',
        component: () => import('@/views/article/Article'),
        meta: {title: '文章'}
    },
    {
        path: '*',
        redirect: '/404',
    },
    {
        path: '/404',
        name: '404',
        component: () => import('@/views/comment/404'),
        meta: {title: '404页面丢失'}
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//挂载路由守卫
router.beforeEach((to, from, next) => {
    document.title = getPageTitle(to.meta.title)
    next()
})

export default router
