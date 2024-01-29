<template>
  <div class="board-detail">
    <div class="board-contents">
      <h3>{{ title }}</h3>
      <div>
        <strong class="w3-large p-r-10">{{ author }}</strong>
        <span class="cl12">|</span><span class="p-l-10">{{ createdAt }}</span>
      </div>
    </div>
    <div class="board-contents mh-200">
      <span v-html="contents"></span>
    </div>
    <div class="common-buttons">
      <button
        type="button"
        class="w3-button w3-round w3-blue-gray"
        v-on:click="fnUpdate"
      >
        수정</button
      >&nbsp;
      <button
        type="button"
        class="w3-button w3-round w3-red"
        v-on:click="fnDelete"
      >
        삭제</button
      >&nbsp;
      <button
        type="button"
        class="w3-button w3-round w3-gray"
        v-on:click="fnList"
      >
        목록
      </button>
    </div>
  </div>
</template>
  
<script>
import { userInfo } from "@/service/userInfoAPI";

export default {
  data() {
    //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      title: "",
      author: "",
      contents: "",
      createdAt: "",
      userName: "",
    };
  },
  mounted() {
    this.fnGetView();
    this.fnGetUserName();
  },
  methods: {
    fnGetView() {
      this.$axios
        .get(this.$serverUrl + "/board/" + this.idx, {
          params: this.requestBody,
        })
        .then((res) => {
          this.title = res.data.title;
          this.author = res.data.author;
          this.contents = res.data.contents;
          // alert();
          this.createdAt = res.data.createdAt;
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.");
          }
        });
    },
    async fnGetUserName(){
      this.form = {
        userId: sessionStorage.getItem("userId"),
      };

      const response = await userInfo(this.form);

      if (response.status == 200) {
        this.userName = response.data.user_name;
      } else {
        alert("에러: " + response.data);
      }
    },
    fnList() {
      delete this.requestBody.idx;
      this.$router.push({
        path: "./list",
        query: this.requestBody,
      });
    },
    fnUpdate() {
      if(this.author === this.userName) {
        this.$router.push({
          path: "./write",
          query: this.requestBody,
        });
      }else {
        alert("작성자 본인이 아닙니다.");
        return
      }
    },
    fnDelete() {
      if (!confirm("삭제하시겠습니까?")) return;

      this.$axios
        .delete(this.$serverUrl + "/board/" + this.idx, {})
        .then(() => {
          alert("삭제되었습니다.");
          this.fnList();
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.mh-200 {
  min-height: 300px;
}
</style>