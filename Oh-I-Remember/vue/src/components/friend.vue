<template>
  <div class="content">
    <div class="small-container">
      <h3>{{ friend.firstName }}  {{ friend.lastName }}</h3>
        <button @click="askQuestion">Ask Question</button>
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
},
methods: {
  askQuestion(){
    this.$router.push({name: "ask-question-by-id-view", params: {receiverId: Number(this.friend.id)}})
  }
}
}
</script>

<style>

</style>