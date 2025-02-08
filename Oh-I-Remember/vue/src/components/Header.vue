<template>
  <div id="header">
    <img src="\assets\images\Remember_Logo.png" alt="logo" class="logo" />
  </div>
  <div id="nav">
    <router-link v-bind:to="{ name: 'home' }">Home</router-link>

    <span v-if="route.name ==='answer-question'"> | </span>
    <router-link v-if="route.name ==='answer-question'" :to="{name: 'questions'}"> Back to Questions </router-link>

    <span v-if="route.name ==='edit-question'"> | </span>
    <router-link v-if="route.name ==='edit-question'" :to="{name: 'do-you-remember'}"> Back to Questions </router-link>

    <span> | </span>
    <router-link v-bind:to="{ name: 'logout' }">Logout</router-link>



  </div>
</template>

<script>
import { useRoute } from "vue-router";
export default {
  setup() {
    const route = useRoute();
    return { route };
  },
};
</script>

<style>

</style>