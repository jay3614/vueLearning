<template>
  <div class="myPage" id="wrapper">
    <nav class="mypage-sidenav">
      <div class="test1">
        <h3>나의 활동</h3>
        <router-link to="#" class="d-flex p-l-10">메뉴1</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴2</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴3</router-link>
      </div>
      <div class="test1">
        <h3>내 정보</h3>
        <router-link to="#" class="d-flex p-l-10">메뉴1</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴2</router-link>
        <router-link to="#" class="d-flex p-l-10">메뉴3</router-link>
      </div>
    </nav>
    <div class="mypage-cont">
      <div class="test2">

      </div>
    </div>
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