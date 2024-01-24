import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import MyPage from '@/views/MyPage.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from '@/views/board/BoardDetail.vue'
import BoardWrite from '@/views/board/BoardWrite.vue'
import LoginView from '@/views/common/LoginView.vue'
import RegisterForm from '@/views/common/RegisterForm.vue'
import FindIdForm from '@/views/common/FindIdForm.vue'
import FindPwForm from '@/views/common/FindPwForm.vue'
import store from "@/vuex/store";

const requireAuth = () => (from, to, next) => {
  const token = sessionStorage.getItem('user_token');
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
    path: '/myPage',
    name: 'MyPage',
    component: MyPage,
    beforeEnter: requireAuth()
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
  {
    path: '/register',
    name: 'RegisterForm',
    component: RegisterForm
  },
  {
    path: '/findId',
    name: 'FindIdForm',
    component: FindIdForm
  },
  {
    path: '/findPw',
    name: 'FindPwForm',
    component: FindPwForm
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router