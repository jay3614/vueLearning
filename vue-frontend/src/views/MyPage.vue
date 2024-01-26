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
              <td class="txt-left"><strong>{{ user_id }}</strong></td>
            </tr>

            <tr>
              <th>이름</th>
              <td class="txt-left"><strong>{{ user_name }}</strong></td>
            </tr>

            <tr id="email-area">
              <th>이메일</th>
              <td class="txt-left"><strong id="currentEmail">{{ user_email }}</strong>
              </td>
            </tr>
          </tbody>
        </table>
        <router-link to="/updateInfo" class="float-r btn btn-outline-dark">회원정보 변경하기</router-link>
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
      user_email: "",
      user_name: ""
    };
  },
  mounted() {
    this.fnGetView();
  },
  methods: {
    async fnGetView() {
      this.form = {
        userId: sessionStorage.getItem("userId"),
        userName: this.user_name,
        userEmail: this.user_email
      };

      const response = await userInfo(this.form);

      if (response.status == 200) {
        this.user_id = response.data.user_id;
        this.user_name = response.data.user_name;
        this.user_email = response.data.user_email;
      } else {
        alert(response.data);
      }
    }
  }
}
</script>

<style>
* {
  font-family: musinsa-Medium, "Apple SD Gothic Neo", "Noto Sans KR", sans-serif !important;
}
</style>