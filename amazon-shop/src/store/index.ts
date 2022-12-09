import Vue from "vue";
import Vuex from "vuex";

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
  totalPrice: number;
  purchaseDate: Date;
}

export default new Vuex.Store({
  state: {
    allItem: [
      {
        itemId: 1,
        itemName: "bread",
        genre: "food",
        price: 100,
        img: "パン",
      },
      {
        itemId: 2,
        itemName: "apple",
        genre: "food",
        price: 200,
        img: "リンゴ",
      },
      {
        itemId: 3,
        itemName: "Logo",
        genre: "food",
        price: 300,
        img: "logo",
      },
      {
        itemId: 4,
        itemName: "bread",
        genre: "food",
        price: 100,
        img: "パン",
      },
      {
        itemId: 5,
        itemName: "apple",
        genre: "food",
        price: 200,
        img: "リンゴ",
      },
      {
        itemId: 6,
        itemName: "Logo",
        genre: "food",
        price: 100,
        img: "logo",
      },
      {
        itemId: 7,
        itemName: "はなちゃん",
        genre: "amimal",
        price: 5,
        img: "hana",
      },
    ] as Item[],
    searchItem: [
      {
        itemId: 1,
        itemName: "bread",
        genre: "food",
        price: 100,
        img: "パン",
      },
      {
        itemId: 2,
        itemName: "apple",
        genre: "food",
        price: 200,
        img: "リンゴ",
      },
      {
        itemId: 3,
        itemName: "bread",
        genre: "food",
        price: 100,
        img: "logo",
      },
    ] as Item[],
    cartList: [
      {
        cartItemId: 1,
        itemId: 1,
        userId: "lk2889",
        itemName: "bread",
        genre: "food",
        price: 100,
        img: "パン",
        quantity: 1,
      },
      {
        cartItemId: 2,
        itemId: 2,
        userId: "lk2889",
        itemName: "apple",
        genre: "food",
        price: 200,
        img: "リンゴ",
        quantity: 5,
      },
      {
        cartItemId: 2,
        itemId: 7,
        userId: "lk2889",
        itemName: "はなちゃん",
        genre: "animal",
        price: 5,
        img: "hana",
        quantity: 3,
      },
    ] as CartItem[],
    userList: [
      {
        userId: "lk2889",
        userName: "kota",
        address: "fukushima",
        mail: "aa@bb.co.jp",
        money: 700,
        password: "12345",
      },
    ] as User[],
    slipList: [
      {
        slipId: 1,
        userId: "lk2889",
        allItemName: "bread,apple",
        totalPrice: 300,
        purchaseDate: new Date(),
      },
    ],
  },
  getters: {},
  mutations: {
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
  },
  actions: {
    handleAdd({ commit }, itemId) {
      const check = this.state.cartList.some((item) => item.itemId == itemId);
      if (check == true) {
        commit("addQuantity", itemId);
      } else {
        //commit("addData", itemId);
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
  },
  modules: {},
});
