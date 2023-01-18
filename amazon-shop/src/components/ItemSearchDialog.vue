<template>
  <div>
    <form action="#">
      <el-radio v-model="radio" label="All">全て</el-radio>
      <el-radio v-model="radio" label="food">食べ物</el-radio>
      <el-radio v-model="radio" label="notEat">その他</el-radio>
      <br />
      <input type="text" v-model="searchWord" />
      <input type="submit" value="検索" @click="search(), closeDialog()" />
    </form>
  </div>
</template>
<script lang="ts">
import { Component, Emit, Vue } from "vue-property-decorator";
import axios from "axios";
import { emit } from "process";
import store from "@/store";

@Component
export default class ItemSearchDialog extends Vue {
  radio = "All";
  searchWord = "";
  visivle = false;
  //商品検索
  async search() {
    const res = await axios.post(
      "http://localhost:8080/searchItem",
      (this.radio, this.searchWord)
    );
    var searchItem = res.data;
    store.commit("createData", searchItem);
  }
  //ダイアログ閉じる
  @Emit()
  closeDialog() {
    console.log("");
  }
}
</script>
