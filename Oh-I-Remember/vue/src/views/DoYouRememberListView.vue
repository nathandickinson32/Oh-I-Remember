<template>
  <div class="content">
    <h1>Questions You Have Asked</h1>

    <do-you-remember-list />
  </div>
</template>

<script>
import DoYouRememberList from "../components/DoYouRememberList.vue";
export default {
  components: {
    DoYouRememberList,
  },
  methods: {
    createQuestion() {
      this.$router.push({ name: "create-question" });
    },
  },
};
</script>

<style>
</style>