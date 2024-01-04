<template>
  <div class="d-flex flex-c">
    <div>
      <form @submit.prevent="fnRegister">
        <div class="row">
          <div class="left">
            아이디
          </div>
          <div class="right">
            <input type="text" class="w-full" name="user_id" v-model="user_id" placeholder="4~12자">
          </div>
        </div>

        <div class="row">
          <div class="left">
            이름
          </div>
          <div class="right">
            <input type="text" class="w-full" name="user_name" v-model="user_name">
          </div>
        </div>

        <div class="row">
          <div class="left">
            비번
          </div>
          <div class="right">
            <input type="password" class="w-full" name="user_pw" v-model="user_pw" placeholder="4~20자리로 입력해주세요.">
          </div>
        </div>

        <div class="row">
          <!-- <button class="w3-button w3-black w3-round" v-on:click="fnRegister">가입하기</button> -->
          <button class="w3-button w3-black w3-round">가입하기</button>
        </div>
    </form>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      requestBody: this.$route.query,

      user_id: '',
      user_name: '',
      user_pw: '',
    }
  },
  methods: {
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './board/list',
        query: this.requestBody
      })
    },
    fnRegister() {

      if (this.user_id === '') {
        alert('ID를 입력하세요.')
        return
      }
      if (this.user_pw === '') {
        alert('비밀번호를 입력하세요.')
        return
      }
      if (this.user_name === '') {
        alert('이름을 입력하세요.')
        return
      }

      let apiUrl = this.$serverUrl + '/register'
      this.form = {
        "user_id": this.user_id,
        "user_name": this.user_name,
        "user_pw": this.user_pw
      }
      
      alert(this.user_id)
      if (this.user_id2 === undefined) {
        this.$axios.post(apiUrl, this.form)
        .then((res) => {
          alert('회원가입 되었습니다.')
          this.fnView(res.data.idx)
        }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      } else {  // 현재 여기로 넘어가짐
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

</style>