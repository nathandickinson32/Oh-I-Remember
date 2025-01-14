<template>
  <div id="header">
    <h1>logo</h1>
  </div>
  <div id="nav">
    <router-link v-bind:to="{ name: 'home' }">Home</router-link>
    <span> | </span>
    <router-link v-bind:to="{ name: 'logout' }">Logout</router-link>
  </div>
</template>

<script>
import { useRoute } from 'vue-router';
export default {
    setup() {
    const route = useRoute();
    return { route };
  }
}
</script>

<style scoped>
.logo {
  width: 175px;
  height: auto;
}

#header {
  display: flex;
  background-color: white;
  justify-content: center;
  margin: 0 auto;
}

#nav {
  margin: 10px;
  width: auto;
  text-align: center;
}
</style>