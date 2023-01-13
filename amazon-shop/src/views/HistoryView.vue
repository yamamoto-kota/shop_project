<!-- eslint-disable no-undef -->
<template>
  <div class="index">
    <div>
      <div style="text-align: left">
        <br />
        <font size="6px">
          <b> {{ space }} 購入履歴</b></font
        >
      </div>
      <font size="7px">
        <el-table :data="slipList" style="width: 80%" class="cart">
          <el-table-column fixed prop="slipId" label="伝票番号" font-size="5px">
          </el-table-column>
          <el-table-column fixed prop="userId" label="購入者ID" font-size="5px">
          </el-table-column>
          <el-table-column
            fixed
            prop="purchasePrice"
            label="購入金額"
            font-size="5px"
          >
          </el-table-column>
          <el-table-column fixed prop="purchaseDate" label="購入日">
          </el-table-column>
        </el-table>
      </font>
    </div>
    <br />
    <el-button type="primary" plain @click="csvDownload(loginId)"
      >購入履歴ダウンロード</el-button
    >
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch, Prop } from "vue-property-decorator";
import store, { CartItem, Slip } from "../store/index";
import { Item } from "../store/index";
import axios from "axios";

@Component
export default class AMAZONSHOP extends Vue {
  loginId = store.state.loginUser.userId;
  slipList: Slip[] = store.state.slipList;
  itemVisible = false;
  defaultPrice = 0;
  space = "　 　";
  count = 0;

  async csvDownload(loginId: string) {
    const res = await axios.post("http://localhost:8080/csvDownload", loginId);
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
