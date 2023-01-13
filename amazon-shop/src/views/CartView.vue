<!-- eslint-disable no-undef -->
<template>
  <div class="index">
    <div>
      <div style="text-align: left">
        <br />
        <font size="6px">
          {{ space }} 合計金額 <b>{{ totalPrice }}</b> 円</font
        >
      </div>
      <font size="7px">
        <el-table :data="cartItem" style="width: 80%" class="cart">
          <el-table-column fixed prop="itemName" label="商品名" font-size="5px">
          </el-table-column>
          <el-table-column prop="price" label="価格"></el-table-column>
          <el-table-column>
            <template slot-scope="scope"
              ><el-button type="text" @click="itemDialog(scope.row.itemId)"
                >詳細</el-button
              >
            </template></el-table-column
          >
          <el-table-column width="40">
            <template slot-scope="scope"
              ><el-button type="text" @click="delQuantity(scope.row.itemId)"
                ><i class="el-icon-remove-outline" type="danger"></i
              ></el-button> </template></el-table-column
          ><el-table-column
            prop="quantity"
            label=""
            width="40"
            style="center"
          ></el-table-column>
          <el-table-column width="80">
            <template slot-scope="scope"
              ><el-button type="text" @click="addQuantity(scope.row.itemId)"
                ><i class="el-icon-circle-plus-outline"></i
              ></el-button> </template
          ></el-table-column>
        </el-table>
        <nav>
          <router-link to="/purchase"
            ><el-button type="success" plain
              >レジに進む ({{ itemCount }} 個の商品)</el-button
            ></router-link
          >
        </nav>
      </font>
      <br />
      <!-- //商品詳細ダイアログ -->
      <el-dialog title="商品情報" :visible.sync="itemVisible">
        <!-- <img :src="require(`@/assets/${itemditail.img}.png`)" class="image" /> -->
        <br />
        <font size="5px">
          {{ itemditail.itemName }} {{ itemditail.price }} 円</font
        >
      </el-dialog>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch, Prop } from "vue-property-decorator";
import store, { CartItem } from "../store/index";
import { Item } from "../store/index";
import axios from "axios";

@Component
export default class AMAZONSHOP extends Vue {
  cartItem: CartItem[] = store.state.cartList;
  itemVisible = false;
  defaultPrice = 0;
  space = "　 　";
  count = 0;
  get itemCount() {
    this.count = 0;
    for (var i = 0; i < store.state.cartList.length; i++) {
      this.count = this.count + store.state.cartList[i].quantity;
    }
    return this.count;
  }
  get totalPrice() {
    this.defaultPrice = 0;
    for (var i = 0; i < store.state.cartList.length; i++) {
      this.defaultPrice =
        this.defaultPrice +
        store.state.cartList[i].price * store.state.cartList[i].quantity;
    }
    return this.defaultPrice;
  }
  itemditail: Item = {
    itemId: 5,
    itemName: "",
    genre: "",
    price: 0,
    img: "pan",
  };

  itemDialog(id: number) {
    this.itemVisible = true;
    const tmpItem = store.state.allItem.find((item) => item.itemId == id);
    if (tmpItem != undefined) {
      this.itemditail = tmpItem;
    }
  }

  async addQuantity(itemId: number) {
    store.commit("addQuantity", itemId);
    const tmp = store.state.cartList;
    if (tmp != undefined) {
      this.cartItem = tmp;
    }
    const res = await axios.post(
      "http://localhost:8080/addItem",
      this.cartItem.find((item) => item.itemId == itemId)
    );
  }

  async delQuantity(itemId: number) {
    const tmp = store.state.cartList;
    if (tmp != undefined) {
      this.cartItem = tmp;
    }
    const res = await axios.post(
      "http://localhost:8080/delItem",
      this.cartItem.find((item) => item.itemId == itemId)
    );
    store.dispatch("handleDel", itemId);
    this.cartItem = store.state.cartList;
  }
}
</script>
<style>
.time {
  font-size: 13px;
  color: #999;
}

.card {
  height: auto;
}
.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.cart {
  margin: auto;
}
</style>
