<template>
  <div class="friend-card">
    <h3>{{ friend.firstName }} {{ friend.lastName }}</h3>
    <button @click="askQuestion">Ask Question</button>
  </div>
</template>

<script>
export default {
  props: {
    friend: {
      type: Object,
      required: true,
    },
  },
  methods: {
    askQuestion() {
      this.$router.push({
        name: "ask-question-by-id-view",
        params: { receiverId: Number(this.friend.id) },
      });
    },
  },
};
</script>

<style>
</style>