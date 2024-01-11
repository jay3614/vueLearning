<template>
  <PageHeader v-if="!hideHeader" />
  <router-view />
  <PageFooter v-if="!hideFooter" />
  <LoadingBar :loading="this.$store.state.loadingStatus"></LoadingBar>
</template>

<script>
import PageHeader from "@/components/PageHeader";
import PageFooter from "@/components/PageFooter";
import LoadingBar from "@/views/common/LoadingBar";

export default {
  name: "App",
  components: {
    PageFooter,
    PageHeader,
    LoadingBar,
  },
  data() {
    return {
      hideHeader: false,
      hideFooter: false,
    };
  },
  created() {
    // findId와 findPw인 경우엔 header와 footer 없애기
    if (
      document.location.pathname === "/findId" ||
      document.location.pathname === "/findPw"
    ) {
      this.hideHeader = true;
      this.hideFooter = true;
    }
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>