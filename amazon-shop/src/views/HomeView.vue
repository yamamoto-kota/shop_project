<template>
  <div class="home">
    <br />
    <br />
    <createUserForm v-if="createUserForm" />
    <form action="#">
      <nav v-if="loginForm">
        ユーザーID <input type="text" v-model="loginId" />
        <router-link to="/index">
          <input
            type="submit"
            value="ログイン"
            @click="login(loginId), addUser()"
          />
        </router-link>
        <input type="submit" value="新規登録" @click="showCreateUserForm()" />
      </nav>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Emit, Vue, Watch } from "vue-property-decorator";
import store, { CartItem, Slip, User } from "../store/index";
import { Item } from "../store/index";
import axios from "axios";
import { CarouselItem } from "element-ui";
import CreateUserForm from "../components/CreateUserForm.vue";

@Component({
  components: {
    createUserForm: CreateUserForm,
  },
})
export default class AMAZONSHOP extends Vue {
  createUserForm = false;
  loginForm = true;
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
  //新規ユーザー登録フォーム表示用
  showCreateUserForm() {
    this.createUserForm = true;
    this.loginForm = false;
  }
  @Emit()
  addUser() {
    console.log("");
  }
}
</script>
