import axios from 'axios'

const getUserInfo = (userId, userName, userPw) => {
  const reqData = {
    'user_id': userId,
    'user_name': userName,
    'user_pw': userPw
  }

  let serverUrl = '//localhost:8082'

  return axios.post(serverUrl + '/register', reqData, {
    headers: {
      'Content-type': 'application/json'
    }
  })
}

export default {
  async doRegister(userId, userName, userPw) {
    console.log("registerAPI의 doRegister: " + userId, userName, userPw)
    try {
      const getUserInfoPromise = getUserInfo(userId, userName, userPw)
      const [userInfoResponse] = await Promise.all([getUserInfoPromise])

      if (userInfoResponse.data.length === 0) {
        return 'notFound'
      } else {
        return userInfoResponse
      }
    } catch (err) {
      console.error(err)
    }
  }
}