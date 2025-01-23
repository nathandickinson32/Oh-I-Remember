<template>
  <div class="large-container">
    {{ question.question }}
    {{ senderUserName }}
  </div>
</template>

<script>
import Question from './Question.vue';

export default {
props: {
    question: {
        type: Object,
        required: true
    },
    senderUserName: {
        type: String,
        required: true
    }
}
}
</script>

<style>

</style>