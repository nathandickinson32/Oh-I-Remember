<template>
  <div class="content">
    <h1>Questions You Have Asked</h1>
<do-you-remember-list/>
  </div>
</template>

<script>
import DoYouRememberList from '../components/DoYouRememberList.vue';
export default {
components: {
    DoYouRememberList
}
}
</script>

<style scoped>
h1 {
    text-align: center;
    margin-top: 0;
}
.content {
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

</style>