import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import LoginView from '@/views/common/LoginView.vue'
import store from "@/vuex/store";

const requireAuth = () => (from, to, next) => {
  // const token = localStorage.getItem('user_token')
  const token = sessionStorage.getItem('user_token')
  if (token) {
    store.state.isLogin = true
    return next()
  } // isLogin === true면 페이지 이동
  next('/login') // isLogin === false면 다시 로그인 화면으로 이동
}

const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: PageHome
  },
  {
    path: '/about',
    name: 'PageAbout',
    component: () => import('../views/PageAbout.vue')
  },
  {
    path: '/board/list',
    name: 'List',
    component: BoardList,
    beforeEnter: requireAuth()
  },
  {
    path: '/board/detail',
    name: 'BoardDetail',
    component: BoardDetail,
    beforeEnter: requireAuth()
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    component: BoardWrite,
    beforeEnter: requireAuth()
  },
  {
    path: '/login',
    name: 'LoginView',
    component: LoginView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router