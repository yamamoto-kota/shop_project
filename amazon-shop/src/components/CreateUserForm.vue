<template>
  <div>
    <el-form
      style="width: 50%"
      label-position="right"
      label-width="120px"
      :model="newUser"
      class="form"
    >
      <el-form-item label="ユーザーID">
        <el-input v-model="newUser.userId" placeholder="lk1111"></el-input>
      </el-form-item>
      <el-form-item label="ユーザー名">
        <el-input v-model="newUser.userName" placeholder="山田太郎"></el-input>
      </el-form-item>
      <el-form-item label="メールアドレス">
        <el-input v-model="newUser.mail" placeholder="aaa@bbb.co.jp"></el-input>
      </el-form-item>
      <el-form-item label="住所">
        <el-input
          v-model="newUser.address"
          placeholder="神奈川県横浜市〇〇町1－１"
        ></el-input>
      </el-form-item>
      <el-form-item label="パスワード">
        <el-input v-model="newUser.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="パスワード(再入力)">
        <el-input v-model="confirmationPassword" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <router-link to="/index">
          <el-button
            type="primary"
            @click="
              checkPass(newUser.password, confirmationPassword, newUser),
                closeCreateUserForm()
            "
            >登録</el-button
          >
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>
<script lang="ts">
import { Component, Emit, Vue } from "vue-property-decorator";
import axios from "axios";
import { emit } from "process";
import store, { User } from "@/store";
import { PropValidator } from "vue/types/options";
import { isNull } from "util";

@Component
export default class CreateUserDialog extends Vue {
  confirmationPassword = "";
  newUser: User = {
    userId: "",
    userName: "",
    address: "",
    mail: "",
    money: 0,
    password: "",
  };
  //入力確認用メソッド
  checkPass(password: string, confirmationPassword: string, newUser: User) {
    //null,emptyチェック
    if (
      newUser.userId != null &&
      newUser.userName != null &&
      newUser.address != null &&
      newUser.password != null &&
      confirmationPassword != null &&
      newUser.userId != "" &&
      newUser.userName != "" &&
      newUser.address != "" &&
      newUser.password != "" &&
      confirmationPassword != ""
    ) {
      //入力したパスワードが一致すれば新規登録メソッド呼び出し
      if (password == confirmationPassword) {
        this.createNewUser(newUser);
      } else {
        alert("パスワードが一致しません");
      }
    } else {
      alert("未入力の項目があります");
    }
  }

  //新規ユーザー情報登録
  async createNewUser(newUser: User) {
    await axios.post("http://localhost:8080/createNewUser", newUser);
    alert("登録が完了しました");
  }
  //ユーザー登録画面閉じる
  @Emit()
  closeCreateUserForm() {
    console.log("closeCreateUser");
  }
}
</script>
<style>
.form {
  margin-right: auto;
  margin-left: auto;
}
</style>
