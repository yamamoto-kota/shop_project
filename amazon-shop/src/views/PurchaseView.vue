<template>
  <div class="purchase">
    <h1>購入画面</h1>
    <br />
    <div class="form" style="width: 50%">
      <el-form
        style="width: 100%"
        label-position="right"
        label-width="120px"
        :model="sendData"
      >
        <el-form-item label="氏名">
          <el-input v-model="sendData.userName"></el-input>
        </el-form-item>
        <el-form-item label="パスワード">
          <el-input v-model="sendData.password" :type="textType"></el-input>
        </el-form-item>
        <el-form-item label="送り先住所">
          <el-input v-model="sendData.address"></el-input>
        </el-form-item>
        <el-form-item label="メールアドレス">
          <el-input v-model="sendData.mail"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="purchase(sendData)"
            >購入確定</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <!-- //商品詳細ダイアログ -->
    <el-dialog :visible.sync="purchaseflag">
      <h6>商品を購入しました。</h6>
      <h6>商品一覧ページに戻ります。</h6>
      <nav>
        <router-link to="/index"
          ><el-button type="primary" plain>OK</el-button></router-link
        >
      </nav>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import store, { CartItem, User } from "../store/index";
import { Item } from "../store/index";
import axios from "axios";

@Component
export default class AMAZONSHOP extends Vue {
  purchaseflag = false;
  textType = "password";
  defaultPrice = 0;
  money = store.state.loginUser.money;
  tmpUser = store.state.loginUser;
  sendData: User = {
    userId: this.tmpUser.userId,
    userName: this.tmpUser.userName,
    address: "",
    mail: this.tmpUser.mail,
    money: this.tmpUser.money,
    password: "",
  };
  get totalPrice() {
    this.defaultPrice = 0;
    for (var i = 0; i < store.state.cartList.length; i++) {
      this.defaultPrice =
        this.defaultPrice +
        store.state.cartList[i].price * store.state.cartList[i].quantity;
    }
    return this.defaultPrice;
  }
  async purchase(sendData: User) {
    if (this.totalPrice <= sendData.money) {
      var totalPrice = this.totalPrice;
      const slip = store.dispatch("handlePurchase", { sendData, totalPrice });
      this.purchaseflag = true;
      const res = await axios.post(
        //所持金に反映
        "http://localhost:8080/purchase",
        store.state.loginUser
      );
      //カートリセット
      const res2 = await axios.post(
        "http://localhost:8080/cartReset",
        store.state.loginUser
      );
      //伝票作成
      const res3 = await axios.post(
        "http://localhost:8080/createSlip",
        store.state.slipList.slice(-1)[0]
      );
    } else alert("残高が不足しています");
  }
}
</script>

<style>
.form {
  margin-right: auto;
  margin-left: auto;
}
</style>
