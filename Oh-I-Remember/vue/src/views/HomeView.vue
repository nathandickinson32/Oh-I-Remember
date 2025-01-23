<template>
  <div class="content">
    <h4>Welcome, {{ $store.state.user.firstName }}!</h4>
    <div class="menu-grid">
    
        <router-link v-bind:to="{ name: 'questions' }">Questions</router-link>

     

    </div>
  </div>
</template>

<script>
export default {
};
</script>
<style scoped>
h4 {
  text-align: center;
  margin: 0;
}

.content {
  flex-direction: column;
}
</style>
