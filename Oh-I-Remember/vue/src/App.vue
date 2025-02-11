<template>
  <div id="oh-i-remember-app">
<Header></Header>
<router-view />
  </div>
</template>

<script>
import 'vue-multiselect/dist/vue-multiselect.min.css';
import Header from './components/Header.vue';
export default {
components: {
  Header
}
}
</script>

<style>

</style>