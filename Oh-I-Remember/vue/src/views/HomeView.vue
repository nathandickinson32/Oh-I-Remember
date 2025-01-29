<template>
  <div class="content">
    <h4>Welcome, {{ $store.state.user.firstName }}!</h4><br/>
    <div class="small-container" id="home-title">
      <router-link v-bind:to="{ name: 'questions' }">I Remember!</router-link>
    </div>
    <br/>
    <div class="small-container" id="home-title">
      <router-link v-bind:to="{ name: 'do-you-remember' }">Do You Remember?</router-link>

    </div>
    <br/>
    <div class="small-container" id="home-title">
      <router-link v-bind:to="{ name: 'friends-list' }">Friends List</router-link>

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
#home-title{
  text-align: center;
}
</style>
