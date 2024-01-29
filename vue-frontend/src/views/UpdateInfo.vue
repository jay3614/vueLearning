<template>
  <div class="myPage" id="wrapper">
    <nav class="mypage-sidenav">
      <div class="my-activity">
        <h3>나의 활동</h3>
        <router-link to="#" class="d-flex p-l-10">메뉴1</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴2</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴3</router-link>
      </div>
      <div class="my-info">
        <h3>내 정보</h3>
        <router-link to="#" class="d-flex p-l-10">메뉴1</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴2</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴3</router-link>
      </div>
    </nav>
    <section class="mypage-cont my-info-modify">
      <header class="info_views-area">
        <div class="section-title">
          <h1 class="tit">회원 정보 변경</h1>
        </div>
      </header>

      <div class="table-responsive">
        <table class="table">
          <tbody>
            <tr>
              <th>아이디</th>
              <td class="txt-left">
                <input type="text"
                  :class="{ active: active.user_id }"
                  name="user_id"
                  v-model="user_id"
                  @input="resetActive('user_id')"
                  readonly
                >
              </td>
            </tr>

            <tr>
              <th>비밀번호</th>
              <td class="txt-left">
                <input type="password"
                  :class="{ active: active.user_pw }"
                  name="user_pw"
                  v-model="user_pw"
                  placeholder="변경할 비밀번호를 입력하세요."
                  @input="resetActive('user_pw')"
                >
              </td>
            </tr>

            <tr>
              <th>이름</th>
              <td class="txt-left">
                <input type="text"
                  :class="{ active: active.user_name }"
                  name="user_name"
                  v-model="user_name"
                  @input="resetActive('user_name')"
                >
              </td>
            </tr>

            <tr id="email-area">
              <th>이메일</th>
              <td class="txt-left">
                <input type="text"
                  :class="{ active: active.user_email }"
                  name="user_email"
                  v-model="user_email"
                  @input="resetActive('user_email')"
                >
              </td>
            </tr>
          </tbody>
        </table>
        <button class="float-r btn btn-outline-dark" v-on:click="fnUpdate">회원정보 수정하기</button>
      </div>
    </section>
  </div>
</template>

<script>
import { userInfo } from "@/service/userInfoAPI";

export default {
  data() {
    return {
      user_id: "",
      user_pw: "",
      user_email: "",
      user_name: "",
      active: { user_id: false, user_email: false, user_name: false, user_pw: false },
    };
  },
  mounted() {
    this.fnGetView();
  },
  methods: {
    async fnGetView() {

      this.form = {
        userId: sessionStorage.getItem("userId"),
        userPw: this.user_pw,
        userName: this.user_name,
        userEmail: this.user_email
      };

      const response = await userInfo(this.form);

      if (response.status == 200) {
        this.user_id = response.data.user_id;
        this.user_pw = response.data.user_pw;
        this.user_name = response.data.user_name;
        this.user_email = response.data.user_email;
      } else {
        alert(response.data);
      }
    },

    fnUpdate() {
      let apiUrl = this.$serverUrl + "/user/updateInfo";
      this.form = {
        userId: this.user_id,
        userPw: this.user_pw,
        userName: this.user_name,
        userEmail: this.user_email,
      };

      alert(this.form.userPw);

      if (this.user_id === "") {
        // 아이디 공백 검사
        this.active.user_id = true;   // 빈칸으로 제출 시 div 색 변환
        alert("ID를 입력하세요.");
        return;
      }
      if (this.user_pw === undefined) {
        // 비밀번호 공백 검사
        this.active.user_pw = true;
        alert("비밀번호를 입력하세요.");
        return;
      }
      if (this.user_name === "") {
        // 이름 공백 검사
        this.active.user_name = true;
        alert("이름을 입력하세요.");
        return;
      }
      if (this.user_email === "") {
        // 이메일 공백 검사
        this.active.user_email = true;
        alert("이메일을 입력하세요.");
        return;
      }

      this.$axios
        .patch(apiUrl, this.form)
        .then((res) => {
          alert("정보가 수정되었습니다.");

          sessionStorage.removeItem(res.data.userId);
          sessionStorage.removeItem("user_token");
          sessionStorage.removeItem("user_role");
          location.reload();
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert(
              "네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요."
            );
          }
        });
    },
    fnView() {
      this.$router.push({
        path: "./myPage"
      });
    },

    resetActive(field) {
      this.active[field] = false;
    },
  }
}
</script>

<style scoped>
* {
  font-family: musinsa-Medium, "Apple SD Gothic Neo", "Noto Sans KR", sans-serif !important;
}

.active {
  background-color: blanchedalmond;
}
</style>