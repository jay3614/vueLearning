import axios from 'axios'

let serverUrl = '//localhost:8082'

function findId(form) {
  return axios.post(serverUrl + '/user/findId', form);
}

export { findId };