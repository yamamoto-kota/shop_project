<!-- eslint-disable no-undef -->
<template>
  <div class="index">
    <br />
    <form action="#">
      <input type="text" />
      <input type="submit" value="検索" @click="search()" />
    </form>
    <div class="box-parrent">
      <div class="box-child">
        <el-col
          :span="5"
          v-for="(item, o, index) in displayItem"
          :key="o"
          :offset="index > 0 ? 1 : 1"
          ><br />
          <el-card
            class="card"
            :body-style="{ padding: '20px', height: '10%' }"
          >
            <img :src="require(`@/assets/${item.img}.png`)" class="image" />
            <div style="padding: 14px">
              <span>{{ item.itemName }} </span>
              <span>{{ item.price }} 円</span>
              <br />
              <span>ID{{ item.itemId }} </span>
              <div class="bottom clearfix">
                <el-button
                  type="text"
                  class="button"
                  @click="itemDialog(item.itemId)"
                  >商品詳細</el-button
                >
              </div>
            </div>
          </el-card>
        </el-col>
      </div>
    </div>
    <div>
      <!-- //商品詳細ダイアログ -->
      <el-dialog title="商品情報" :visible.sync="itemVisible">
        <!-- <img :src="require(`@/assets/${itemditail.img}.png`)" class="image2" /> -->
        <img :src="require(`@/assets/${itemditail.img}.png`)" class="image" />
        <br />
        <font size="5px">
          {{ itemditail.img }}
          {{ itemditail.itemName }} {{ itemditail.price }} 円</font
        >
        <br />
        <el-button
          type="primary"
          plain
          class="button"
          @click="addCart(itemditail.itemId)"
          >カートに追加</el-button
        >
      </el-dialog>
      <!-- <el-dialog title="商品情報" :visible.sync="searchDialog.visible">
        <h1>dialogTest</h1>
      </el-dialog> -->
      <div><itemSearchDialog :visible.sync="searchDialogVisible" /></div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { Component, Vue, Watch } from "vue-property-decorator";
import { component } from "vue/types/umd";
import store, { CartItem } from "../store/index";
import { Item } from "../store/index";
import ItemSearchDialog from "../components/ItemSearchDialog.vue";

@Component({
  components: {
    itemSearchDialog: ItemSearchDialog,
  },
})
export default class AMAZONSHOP extends Vue {
  displayItem: Item[] = store.state.allItem;
  itemVisible = false;
  searchVisible = false;
  logintest = store.state.loginUser.userId;
  logintest2 = store.state.loginUser.money;
  cartItem: CartItem[] = store.state.cartList;
  itemditail: Item = {
    itemId: 5,
    itemName: "",
    genre: "",
    price: 0,
    img: "pan",
  };
  searchDialogVisible = false;
  search() {
    this.searchDialogVisible = true;
    console.log("ssssssssss");
  }

  itemDialog(id: number) {
    this.itemVisible = true;
    const tmpItem = store.state.allItem.find((item) => item.itemId == id);
    if (tmpItem != undefined) {
      this.itemditail = tmpItem;
    }
  }

  async addCart(itemId: number) {
    store.dispatch("handleAdd", itemId);
    const tmp = store.state.cartList;
    if (tmp != undefined) {
      this.cartItem = tmp;
    }
    const res = await axios.post(
      "http://localhost:8080/addItem",
      this.cartItem.find((item) => item.itemId == itemId)
    );
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
</style>
