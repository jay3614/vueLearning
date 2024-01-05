import axios from 'axios'

let serverUrl = '//localhost:8082'

function register(form) {
  return axios.post(serverUrl + '/user/register', form);
}

export { register };