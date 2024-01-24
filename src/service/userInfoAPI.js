import axios from 'axios'

let serverUrl = '//localhost:8082'

function userInfo(form) {
  return axios.post(serverUrl + '/user/myInfo', form);
}

export { userInfo };