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
              <td class="txt-left"><strong>[[${member.username}]]</strong></td>
            </tr>

            <tr>
              <th>이름</th>
              <td class="txt-left"><strong>[[${member.name}]]</strong></td>
            </tr>

            <tr id="email-area">
              <th>이메일</th>
              <td class="txt-left"><strong id="currentEmail"> [[${member.email}]]</strong>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>

<style>
* {
  font-family: musinsa-Medium, "Apple SD Gothic Neo", "Noto Sans KR", sans-serif !important;
}

.test1 {
  border: 1px solid black;
  height: 200px;
}

.test2 {
  border: 1px solid black;
  height: 500px;
}
</style>