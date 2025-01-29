<template>
  <div class="content">
    <div class="small-container">
      <h3>{{ friend.firstName }}  {{ friend.lastName }}</h3>
        
    </div>
  </div>
</template>

<script>
export default {
props: {
  friend: {
    type: Object,
    required: true,
  }
}
}
</script>

<style>

</style>