<template>
  <div id="app">
    <nav bgcolor="black" class="top">
      <body class="p-3 mb-2 bg-transparent text-dark">
        <el-row></el-row>
        <div style="text-align: left">
          <font color="white" size="5px"> AMAZON-SHOP</font>
          <font color="white" size="5px"> </font>
        </div>
        <table style="width: 100%">
          <tr>
            <th><router-link to="/">ログイン</router-link></th>
            <th>
              <router-link to="/index">商品一覧</router-link>
            </th>
            <th>
              <router-link to="/cart">カート</router-link>
            </th>
            <th>
              <router-link to="/purchase">購入</router-link>
            </th>
            <th>
              <router-link to="/history">購入履歴</router-link>
            </th>
          </tr>
        </table>
      </body>
    </nav>
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import store, { CartItem } from "./store/index";
import { Item } from "./store/index";
import axios from "axios";

@Component
export default class AMAZONSHOP extends Vue {
  cartItem: CartItem[] = store.state.cartList;
  defaultPrice = 0;
  loginUserId = store.state.loginUser.userId;
  get totalPrice() {
    this.defaultPrice = 0;
    for (var i = 0; i < store.state.cartList.length; i++) {
      this.defaultPrice =
        this.defaultPrice +
        store.state.cartList[i].price * store.state.cartList[i].quantity;
    }
    return this.defaultPrice;
  }
  created() {
    this.getData();
  }

  async getData() {
    const res = await axios.get("http://localhost:8080/");
    const viewData = res.data;
    store.commit("createData", viewData);
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.top {
  padding: 1px;
  background-color: black;
}

nav a {
  font-weight: bold;
  color: #fafad2;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
