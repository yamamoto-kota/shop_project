<template>
  <div class="home">
    <br />
    <br />
    <form action="#">
      <nav>
        ユーザーID <input type="text" v-model="loginId" />

        <router-link to="/index">
          <input type="submit" value="ログイン" @click="login(loginId)" />
        </router-link>
      </nav>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import store, { CartItem, Slip, User } from "../store/index";
import { Item } from "../store/index";
import axios from "axios";
import { CarouselItem } from "element-ui";

@Component
export default class AMAZONSHOP extends Vue {
  loginId = store.state.loginUser.userId;
  testData = "";
  newslip: Slip[] = store.state.slipList;
  newCart: CartItem[] = store.state.cartList;
  loginUser: User = {
    userId: "",
    userName: "",
    address: "",
    mail: "",
    money: 0,
    password: "",
  };
  async login(loginId: string) {
    const res = await axios.post("http://localhost:8080/login", loginId);
    this.loginUser = res.data;
    store.commit("login", this.loginUser);
    //カート情報の取得
    const cart = await axios.post(
      "http://localhost:8080/cartitem",
      store.state.loginUser.userId
    );
    this.newCart = cart.data;
    store.commit("loginCart", this.newCart);
    //購入履歴の取得
    const slipList = await axios.post(
      "http://localhost:8080/returnSlip",
      store.state.loginUser.userId
    );
    this.newslip = slipList.data;
    store.commit("getSlip", this.newslip);
  }
}
</script>
