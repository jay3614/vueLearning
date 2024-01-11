<template>
  <div id="wrapper">
    <div>
      <h2>로그인</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input
              class="w3-input"
              :class="{ active: active.user_id }"
              name="user_id"
              placeholder="Enter your ID"
              v-model="user_id"
              @input="resetActive('user_id')"
            /><br />
          </p>
          <p>
            <input
              class="w3-input"
              :class="{ active: active.user_pw }"
              name="user_pw"
              placeholder="Enter your password"
              v-model="user_pw"
              type="password"
              @input="resetActive('user_pw')"
            />
          </p>
          <p class="p-t-20">
            <button type="submit" class="w-full w3-button w3-green w3-round">
              Login
            </button>
          </p>
        </form>
        <div class="p-t-20">
          <ul class="flex-c">
            <li class="float-l login_btn">
              <router-link to="/register">회원가입</router-link>
            </li>
            <p class="float-l">|</p>
            <li class="float-l login_btn">
              <!-- <router-link to="/findID">ID 찾기</router-link> -->
              <a href="javascript:void(window.open('/findId', '_blank','width=800, height=500, top=' + (screen.height/2 - 250) + ', left=' + (screen.width/2 - 400)))">ID 찾기</a>
            </li>
            <p class="float-l">|</p>
            <li class="float-l login_btn">
              <a href="javascript:void(window.open('/findPw', '_blank','width=800, height=400, top=' + (screen.height/2 - 250) + ', left=' + (screen.width/2 - 400)))">비밀번호 찾기</a
              >
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      user_id: "",
      user_pw: "",
      active: { user_id: false, user_pw: false },
    };
  },
  methods: {
    ...mapActions(["login"]),

    async fnLogin() {
      // 아이디 입력 여부 검사
      if (this.user_id === "") {
        this.active.user_id = true;
        alert("ID를 입력하세요.");
        return;
      }

      // 비밀번호 입력 여부 검사
      if (this.user_pw === "") {
        this.active.user_pw = true;
        alert("비밀번호를 입력하세요.");
        return;
      }

      try {
        let loginResult = await this.login({
          user_id: this.user_id,
          user_pw: this.user_pw,
        });
        if (loginResult) {
          this.goToPages();
        }
      } catch (err) {
        if (err.message.indexOf("Network Error") > -1) {
          alert("서버에 접속할 수 없습니다. 상태를 확인해주세요.");
        } else {
          console.log(err);
          alert("로그인 정보를 확인할 수 없습니다.");
        }
      }
    },
    goToPages() {
      this.$router.push({
        name: "List",
      });
    },
    resetActive(field) {
      this.active[field] = false;
    },
  },
  computed: {
    ...mapGetters({
      errorState: "getErrorState",
    }),
  },
};
</script>
  
<style>
#loginForm {
  width: 500px;
  margin: auto;
}

.active {
  background-color: blanchedalmond;
}

.login_btn {
  width: 30%;
  font-size: 18px;
}
</style>