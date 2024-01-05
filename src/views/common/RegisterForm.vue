<template>
  <div class="d-flex flex-c">
    <div>
      <div class="row">
        <div class="left">아이디</div>
        <div class="right">
          <input
            type="text"
            class="w-full"
            :class="{ active: active.user_id }"
            name="user_id"
            v-model="user_id"
            placeholder="4~12자"
            @input="resetActive('user_id')"
          />
        </div>
      </div>

      <div class="row">
        <div class="left">이름</div>
        <div class="right">
          <input
            type="text"
            class="w-full"
            :class="{ active: active.user_name }"
            name="user_name"
            v-model="user_name"
            @input="resetActive('user_name')"
          />
        </div>
      </div>

      <div class="row">
        <div class="left">비밀번호</div>
        <div class="right">
          <input
            type="password"
            class="w-full"
            :class="{ active: active.user_pw }"
            name="user_pw"
            v-model="user_pw"
            placeholder="4~20자리로 입력해주세요."
            @input="resetActive('user_pw')"
          />
        </div>
      </div>

      <div class="row">
        <button class="w3-button w3-black w3-round" @click="fnRegister">
          가입하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from "@/service/registerAPI";

export default {
  data() {
    return {
      user_id: "",
      user_name: "",
      user_pw: "",
      active: { user_id: false, user_name: false, user_pw: false },
    };
  },
  methods: {
    async fnRegister() {
      if (this.user_id === "") {
        // 아이디 검사
        this.active.user_id = true;
        alert("ID를 입력하세요.");
        return;
      }
      if (this.user_pw === "") {
        // 비밀번호 검사
        this.active.user_pw = true;
        alert("비밀번호를 입력하세요.");
        return;
      }
      if (this.user_name === "") {
        // 이름 검사
        this.active.user_name = true;
        alert("이름을 입력하세요.");
        return;
      }

      this.form = {
        userId: this.user_id,
        userName: this.user_name,
        userPw: this.user_pw,
      };

      try {
        const response = await register(this.form);

        // 회원가입에 성공했을 경우
        if (response.status == 200) {
          alert("환영합니다.");
          this.$router.push("/login");
        } else {
          alert(response.data);
        }
      } catch (err) {
        // 이미 userId가 존재할 경우
        if (
          err.response &&
          err.response.status == 400 &&
          err.response.data === "이미 존재하는 아이디 입니다."
        ) {
          alert("이미 존재하는 아이디입니다.");
          location.reload();
        } else {
          console.error(err);
          alert("회원가입에 실패했습니다.");
          location.reload();
        }
      }
    },
    resetActive(field) {
      this.active[field] = false;
    },
  },
};
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
  background: #9fa8da;
}

.right {
  width: 380px;
  height: 36px;
  padding: 8px;
  float: left;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  background: #c5cae9;
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