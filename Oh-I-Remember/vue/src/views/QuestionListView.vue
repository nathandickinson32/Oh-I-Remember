<template>
  <div class="content">
    <h1>Questions You Can Answer</h1>
    <question-list/>
  </div>
</template>

<script>
import QuestionList from '../components/QuestionList.vue';
export default {
components: {
    QuestionList
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