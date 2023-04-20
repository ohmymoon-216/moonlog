<template>
  <el-header class="header">
    <div class="wrapper">
      <el-menu mode="horizontal">
        <el-menu-item index="1"><RouterLink to="/">Home</RouterLink></el-menu-item>
        <el-menu-item index="2"><RouterLink to="/write">글작성</RouterLink></el-menu-item>
      </el-menu>
    </div>
  </el-header>
</template>

<script setup lang="ts">

</script>

<style scoped>
.header {
  padding: 0;
}
</style>