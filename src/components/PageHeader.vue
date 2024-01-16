<template>
  <header>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/myPage">myPage</router-link> |
      <router-link to="/board/list">게시판</router-link> |
      <router-link to="/login" v-if="!this.$store.state.isLogin"
        >로그인</router-link
      >
      <a v-if="this.$store.state.isLogin" @click="fnLogout">로그아웃</a>
    </div>
  </header>
  <hr />
</template>
  
  <script>
export default {
  methods: {
    fnLogout() {
      sessionStorage.removeItem("user_token");
      sessionStorage.removeItem("user_role");
      location.reload();
    },
  },
};
</script>
  
  <style scoped>
</style>