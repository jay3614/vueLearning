import axios from 'axios'

let serverUrl = '//localhost:8082'

function findPw(form) {
  return axios.post(serverUrl + '/user/findPw', form);
}

export { findPw };