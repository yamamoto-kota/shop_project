<template>
  <div id="app">
    <nav bgcolor="black">
      <body class="p-3 mb-2 bg-transparent text-dark">
        <el-row></el-row>
        <div style="text-align: left">
          <font color="white" size="5px"> AMAZON-SHOP</font>
        </div>
        <table style="width: 100%">
          <tr>
            <th><router-link to="/">ログイン</router-link></th>
            <th>
              <router-link to="/index">商品一覧</router-link>
            </th>
            <th>
              <router-link to="/purchase">購入</router-link>
            </th>
            <th>
              <router-link to="/cart">カート</router-link>
            </th>
          </tr>
        </table>
      </body>
    </nav>
    <router-view />
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
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
