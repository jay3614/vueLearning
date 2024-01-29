<template>
  <div class="board-detail">
    <div class="board-contents">
      <input
        type="text"
        :class="{ active: active.title }"
        v-model="title"
        class="w3-input w3-border"
        placeholder="제목을 입력해주세요."
        @input="resetActive('title')"
      />
      <input
        type="text"
        v-model="author"
        class="w3-input w3-border"
        v-if="idx === undefined"
        readonly
      />
    </div>
    <div class="board-contents">
      <textarea
        cols="30"
        rows="10"
        class="w3-input w3-border"
        :class="{ active: active.contents }"
        v-model="contents"
        style="resize: none"
        @input="resetActive('contents')"
      >
      </textarea>
    </div>
    <div class="common-buttons">
      <button
        type="button"
        class="w3-button w3-round w3-blue-gray"
        v-on:click="fnSave"
      >
        저장</button
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
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      title: "",
      author: "",
      contents: "",
      created_at: "",
      active: { title: false, contents: false},
    };
  },
  mounted() {
    this.fnGetView();
  },
  methods: {
    async fnGetView() {
      this.form = {
        userId: sessionStorage.getItem("userId"),
      };

      const response = await userInfo(this.form);

      if (response.status == 200) {
        this.author = response.data.user_name;
      } else {
        alert(response.data);
      }
      // 글 수정 시 기존 내용 가져오기
      if (this.idx !== undefined) {
        this.$axios.get(this.$serverUrl + '/board/' + this.idx, {
          params: this.requestBody
        }).then((res) => {
          this.title = res.data.title;
          this.author = res.data.author;
          // db에 저장된 <br>태그를 개행문자로 변환
          this.contents = res.data.contents.replace(/<br>/g, "\r\n");
          this.created_at = res.data.created_at;
        }).catch((err) => {
          console.log(err)
        })
      }
    },
    fnList() {
      delete this.requestBody.idx;
      this.$router.push({
        path: "./list",
        query: this.requestBody,
      });
    },
    fnView(idx) {
      this.requestBody.idx = idx;
      this.$router.push({
        path: "./detail",
        query: this.requestBody,
      });
    },
    fnSave() {
      let apiUrl = this.$serverUrl + "/board";
      this.form = {
        idx: this.idx,
        title: this.title,
        contents: this.contents,
        author: this.author,
      };

      if (this.title === "") {
        // 제목 공백 검사
        this.active.title = true;
        alert("제목을 입력하세요.");
        return;
      }
      if (this.contents === "") {
        // 내용 공백 검사
        this.active.contents = true;
        alert("내용을 입력하세요.");
        return;
      }

      // 줄바꿈 db에 변환해서 저장
      this.form.contents = this.contents.replace(/\n/g, "<br>");

      if (this.idx === undefined) {
        //INSERT
        this.$axios
          .post(apiUrl, this.form)
          .then((res) => {
            alert("글이 저장되었습니다.");
            this.fnView(res.data.idx);
          })
          .catch((err) => {
            if (err.message.indexOf("Network Error") > -1) {
              alert(
                "네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요."
              );
            }
          });
      } else {
        //UPDATE
        this.$axios
          .patch(apiUrl, this.form)
          .then((res) => {
            alert("글이 저장되었습니다.");
            this.fnView(res.data.idx);
          })
          .catch((err) => {
            if (err.message.indexOf("Network Error") > -1) {
              alert(
                "네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요."
              );
            }
          });
      }
    },
    resetActive(field) {
      this.active[field] = false;
    },
  },
};
</script>

<style scoped>
.active {
  background-color: blanchedalmond;
}
</style>