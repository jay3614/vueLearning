// import axios from 'axios';

// axios.interceptors.request.use(function (config) {
//     // const token = localStorage.getItem('user_token');
//     const token = sessionStorage.getItem('user_token');
//     config.headers.Authorization = "Bearer " + token;
//     return config;
// });

// axios.interceptors.response.use(function (config) {
//     return config
// });

// export default axios;



import axios from 'axios';
import store from '@/vuex/store'

axios.interceptors.request.use(function (config) {
  store.commit('LOADING_STATUS', true)

  const token = sessionStorage.getItem('user_token');
  config.headers.Authorization = "Bearer " + token;
  return config;
});

axios.interceptors.response.use(function (config) {
  store.commit('LOADING_STATUS', false)

  return config
});

export default axios;