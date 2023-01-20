import { TimePicker } from "element-ui";
import { userInfo } from "os";
import Vue from "vue";
import Vuex, { Store } from "vuex";

Vue.use(Vuex);

export interface CartItem {
  cartItemId: number;
  itemId: number;
  userId: string;
  itemName: string;
  genre: string;
  price: number;
  img: string;
  quantity: number;
}

export interface Item {
  itemId: number;
  itemName: string;
  genre: string;
  price: number;
  img: string;
}

export interface User {
  userId: string;
  userName: string;
  address: string;
  mail: string;
  money: number;
  password: string;
}

export interface Slip {
  slipId: number;
  userId: string;
  allItemName: string;
  purchasePrice: number;
  purchaseDate: string;
}

export default new Vuex.Store({
  state: {
    serverTest: "",
    allItem: [] as Item[],
    searchItem: [] as Item[],
    cartList: [] as CartItem[],
    loginUser: {
      userId: "",
      userName: "",
      address: "",
      mail: "",
      money: 0,
      password: "",
    } as User,
    slipList: [] as Slip[],
  },
  getters: {},
  mutations: {
    getSlip(state, slipList) {
      state.slipList = slipList;
    },
    loginCart(state, cartItem) {
      state.cartList = cartItem;
    },
    createData(state, allData) {
      state.allItem = allData;
    },
    login(state, user) {
      state.loginUser = user;
    },
    addData(state, newItem) {
      state.cartList.push(newItem);
    },
    addQuantity(state, id) {
      state.cartList.find((item) => {
        if (item.itemId == id) {
          item.quantity += 1;
        }
      });
    },
    delData(state, id) {
      state.cartList = state.cartList.filter((item) => item.itemId != id);
    },
    delQuantity(state, id) {
      state.cartList.find((item) => {
        if (item.itemId == id) {
          item.quantity -= 1;
        }
      });
    },
    resetCart(state) {
      state.cartList = [];
    },
    addSlip(state, newslip) {
      state.slipList.push(newslip);
    },
    purchase(state, totalPrice) {
      state.loginUser.money = state.loginUser.money - totalPrice;
    },
    logout(state) {
      state.loginUser = {
        userId: "",
        userName: "ゲスト",
        address: "",
        mail: "",
        money: 0,
        password: "",
      };
    },
  },
  actions: {
    // hadleLogin({ commit }, loginId) {
    //   const tmp = this.state.userList.find((user) => user.userId == loginId);
    //   if (tmp != undefined) {
    //     commit("login", tmp);
    //   }
    // },
    handleAdd({ commit }, itemId) {
      const check = this.state.cartList.some((item) => item.itemId == itemId);
      if (check == true) {
        commit("addQuantity", itemId);
      } else {
        const tmp = this.state.allItem.find((item) => item.itemId == itemId);
        if (tmp != undefined) {
          const newItem: CartItem = {
            cartItemId: this.state.cartList.length - 1,
            itemId: itemId,
            userId: "lk2889",
            itemName: tmp.itemName,
            genre: tmp.genre,
            price: tmp.price,
            img: tmp.img,
            quantity: 1,
          };
          commit("addData", newItem);
        }
      }
    },
    handleDel({ commit }, id) {
      const tmp = this.state.cartList.find((item) => item.itemId == id);
      if (tmp != undefined) {
        if (tmp.quantity == 1) {
          commit("delData", id);
        } else {
          commit("delQuantity", id);
        }
      }
    },
    handlePurchase({ commit }, { sendData, totalPrice }) {
      const tmpDate = new Date();
      const slip = {
        slipId: this.state.slipList.length + 1,
        userId: sendData.userId,
        allItemName: "ItemName",
        purchasePrice: totalPrice,
        purchaseDate:
          tmpDate.getMonth() +
          1 +
          "/" +
          tmpDate.getDate() +
          "  " +
          tmpDate.getHours() +
          ":" +
          tmpDate.getMinutes(),
      };
      commit("addSlip", slip);
      commit("resetCart");
      commit("purchase", slip.purchasePrice);
    },
  },
  modules: {},
});
