// import './assets/w3.css'
import './assets/common.css'

import { createApp } from 'vue'
import App from './App.vue'
// import axios from 'axios'
import axios from './utils/axios'
import router from './router'
import store from './vuex/store'

import '../src/assets/css/main.css'
import '../src/assets/css/util.css'
import '../src/assets/css/myCustom.css'
// import 'bootstrap'
// import 'bootstrap/dist/css/bootstrap.min.css'

const app = createApp(App)
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$serverUrl = '//localhost:8082'
app.config.globalProperties.$store = store
app.use(router).use(store).mount('#app')