<template>
  <div class="d-flex flex-c p-t-100">
    <div>
      <h3 class="p-b-20">
        비밀번호 찾기를 위한 아이디와 이메일을 입력해주세요.
      </h3>
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
        <div class="left">이메일</div>
        <div class="right">
          <input
            type="text"
            class="w-full"
            :class="{ active: active.user_email }"
            name="user_email"
            v-model="user_email"
            placeholder="4~12자"
            @input="resetActive('user_email')"
          />
        </div>
      </div>

      <div class="row">
        <button class="w3-button w3-black w3-round" @click="fnFind">
          찾기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { findPw } from "@/service/findPwAPI";

export default {
  data() {
    return {
      user_id: "",
      user_email: "",
      active: {
        user_id: false,
        user_email: false,
      },
    };
  },
  methods: {
    async fnFind() {
      if (this.user_id === "") {
        this.active.user_id = true;
        alert("이름을 입력하세요.");
        return;
      }

      this.form = { user_id: this.user_id };

      try {
        const response = await findPw(this.form);

        // 비밀번호 찾기에 성공했을 경우
        if (response.status == 200) {
          alert("임의의 비밀번호를 가입하신 이메일로 전송하였습니다.");
          this.$router.push("/login");
        } else {
          alert(response.data);
        }
      } catch (err) {
        console.error(err);
        alert("비밀번호 찾기에 실패했습니다.");
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