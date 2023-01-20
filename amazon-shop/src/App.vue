<template>
  <div id="app">
    <nav bgcolor="black" class="top">
      <body class="p-3 mb-2 bg-transparent text-dark">
        <el-row></el-row>
        <div>
          <p style="text-align: left" class="app">
            <font color="white" size="5px"> AMAZON-SHOP</font>
          </p>
          <font color="white" size="5px"></font>
          <p style="text-align: right" class="login">
            <el-dropdown @command="handleDropdow">
              <span class="el-dropdown-link">
                {{ loginUserId }}{{ loginUser
                }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown" class="droppdown">
                <el-dropdown-item v-if="loginUser == 'ゲスト'">
                  <router-link to="/"> ログイン </router-link>
                </el-dropdown-item>
                <el-dropdown-item v-if="loginUser != 'ゲスト'" command="a"
                  ><span />ログアウト</el-dropdown-item
                ><el-dropdown-item v-if="loginUser != 'ゲスト'" command="b"
                  ><span />アカウント情報</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </p>
        </div>

        <table style="width: 100%">
          <tr>
            <th>
              <router-link to="/">ログイン</router-link>
            </th>
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
    <router-view @add-user="addUser()" />
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import store, { CartItem } from "./store/index";
import { Item } from "./store/index";
import axios from "axios";
import HomeView from "./views/HomeView.vue";

@Component({
  components: {
    homeView: HomeView,
  },
})
export default class AMAZONSHOP extends Vue {
  cartItem: CartItem[] = store.state.cartList;
  command = "";
  defaultPrice = 0;
  loginUserId = "";
  loginUser = "ゲスト";
  get totalPrice() {
    this.defaultPrice = 0;
    for (var i = 0; i < store.state.cartList.length; i++) {
      this.defaultPrice =
        this.defaultPrice +
        store.state.cartList[i].price * store.state.cartList[i].quantity;
    }
    return this.defaultPrice;
  }
  test() {
    alert("mouseover");
  }
  created() {
    this.getData();
  }

  handleDropdow(command: any) {
    if (command == "a") {
      this.logout();
    }
  }

  async getData() {
    const res = await axios.get("http://localhost:8080/");
    const viewData = res.data;
    store.commit("createData", viewData);
  }

  async addUser() {
    const res = await axios.get("http://localhost:8080/");
    this.loginUserId = store.state.loginUser.userId;
    this.loginUser = store.state.loginUser.userName;
  }
  //ログアウト
  logout() {
    alert("ログアウトしました。");
    store.commit("logout");
    this.loginUser = store.state.loginUser.userName;
    this.loginUserId = store.state.loginUser.userId;
  }
}
</script>

<style>
.el-dropdown-link {
  cursor: pointer;
  color: white;
  font-size: 30px;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.app {
  text-align: left; /* 文章を左寄せする(※) */
  float: left;
}
.login {
  text-align: right;
}
.top {
  padding: 1px;
  background-color: black;
  position: sticky;
}

nav a {
  font-weight: bold;
  color: #fafad2;
}

nav a.router-link-exact-active {
  color: #42b983;
}
el-dropdown-item {
  color: black;
}
</style>
