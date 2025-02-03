<template>
  <ask-question-by-id :receiverId="receiverId" />
</template>

<script>
import AskQuestionById from '../components/AskQuestionById.vue';

export default {
  components: { AskQuestionById },

  computed: {
    receiverId() {
      return Number(this.$route.params.receiverId);
    }
  }
};
</script>

<style>

</style>
