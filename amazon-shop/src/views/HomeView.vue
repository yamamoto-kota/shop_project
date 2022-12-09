<template>
  <div class="home">
    <!-- <el-form
      status-icon
      ref="ruleForm"
      label-width="120px"
      class="demo-ruleForm"
    >
      <el-form-item label="Password" prop="pass">
        <el-input
          type="password"
          v-model="ruleForm.pass"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="Confirm" prop="checkPass">
        <el-input
          type="password"
          v-model="ruleForm.checkPass"
          autocomplete="off"
        ></el-input>
      </el-form-item>
    </el-form> -->
    <form action="#">
      ユーザーID <input type="text" /> <input type="submit" value="ログイン" />
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
export default Vue.extend({
  name: "HomeView",
  components: {},
});
</script>
