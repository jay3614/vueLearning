<template>
  <div class="d-flex flex-c">
    <div>
      <div class="row">
        <div class="left">
          아이디
        </div>
        <div class="right">
          <input type="text" class="w-full" :class="{active: active.user_id}" name="user_id" v-model="user_id" placeholder="4~12자">
        </div>
      </div>

      <div class="row">
        <div class="left">
          이름
        </div>
        <div class="right">
          <input type="text" class="w-full" :class="{active: active.user_name}" name="user_name" v-model="user_name">
        </div>
      </div>

      <div class="row">
        <div class="left">
          비번
        </div>
        <div class="right">
          <input type="password" class="w-full" :class="{active: active.user_pw}" name="user_pw" v-model="user_pw" placeholder="4~20자리로 입력해주세요.">
        </div>
      </div>

      <div class="row">
        <button class="w3-button w3-black w3-round" @click="fnRegister">가입하기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user_id: '',
      user_name: '',
      user_pw: '',
      active: {user_id: false, user_name: false, user_pw: false}
    }
  },
  methods: {
    fnRegister() {
      if (this.user_id === '') {
        this.active.user_id = true
        alert('ID를 입력하세요.')
        return
      }
      if (this.user_pw === '') {
        this.active.user_pw = true
        alert('비밀번호를 입력하세요.')
        return
      }
      if (this.user_name === '') {
        this.active.user_name = true
        alert('이름을 입력하세요.')
        return
      }

      let apiUrl = this.$serverUrl + '/register'
      
      this.form = {
        "user_id": this.user_id,
        "user_name": this.user_name,
        "user_pw": this.user_pw
      }

      if (this.user_id2 === undefined) {
        this.$axios.post(apiUrl, this.form)
        .then(        // 여기로 넘어가는데 백엔드쪽에서 토큰 에러 메세지가 뜸
          alert('회원가입 되었습니다.')
        ).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      } else {
        alert('이미 존재하는 아이디 입니다.')
      }
    }
  }
}
</script>

<style scoped>
.row {
  width: 500px;
  height: 80px;
  word-break: break-all;
  word-wrap: break-word;
}

.left {
  width: 120px;
  height: 36px;
  padding: 8px;
  float: left;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  background: #9FA8DA;
}

.right {
  width: 380px;
  height: 36px;
  padding: 8px;
  float: left;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  background: #C5CAE9;
}

.error-text {
  margin-left: 130px;
  font-size: small;
  color: red;
}

.active {
  background-color: blanchedalmond;
}
</style>