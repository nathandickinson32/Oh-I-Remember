<template>
  <div class="card">
    <h3>{{ question.question }}</h3>
   

    <p class="user-info">Asked By: {{ question.senderUserName }}</p>
    <div v-if="question.answered">
      <strong>You answered:</strong>

      <p>{{ question.answer }}</p>
    </div>

    <div
      class="question-category-list"
      v-for="category in question.categories"
      v-bind:key="category.categoryId"
    >
      {{ category.categoryName }}
    </div><br/>
    <button v-if="question.answered === false" @click="answerQuestion">
      Answer Question
    </button>
  </div>
</template>

<script>
import QuestionService from "../services/QuestionService";

export default {
  props: {
    question: {
      type: Object,
      required: true,
    },
  },

  created() {
    this.getUser(this.question);
  },
  methods: {
    answerQuestion() {
      this.$router.push({
        name: "answer-question",
        params: { questionId: this.question.questionId },
      });
    },
    getUser(question) {
      QuestionService.getUserById(question.senderId).then((response) => {
        question.senderUserName =
          response.data.firstName + " " + response.data.lastName;
      });
    },
  },
};
</script>

<style>
</style>