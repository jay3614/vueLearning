import axios from 'axios'

const getUserInfo = (userId, userPw) => {
  const reqData = {
    'user_id': userId,
    'user_pw': userPw
  }

  let serverUrl = '//localhost:8082'

  return axios.post(serverUrl + '/user/login', reqData, {
    headers: {
      'Content-type': 'application/json'
    }
  })
}

export default {
  async doLogin(userId, userPw) {
    console.log("loginAPI의 doLogin: " + userId, userPw)
    try {
      const getUserInfoPromise = getUserInfo(userId, userPw)
      const [userInfoResponse] = await Promise.all([getUserInfoPromise])

      if (userInfoResponse.data.length === 0) {
        return 'notFound'
      } else {
        sessionStorage.setItem('userId', userId);
        sessionStorage.setItem('user_token', userInfoResponse.data.user_token);
        sessionStorage.setItem('user_role', userInfoResponse.data.user_role);
        return userInfoResponse
      }
    } catch (err) {
      console.error(err)
    }
  }
}