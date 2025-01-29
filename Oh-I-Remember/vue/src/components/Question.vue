<template>
  <div class="content">
    <div
      class="small-container"
      :to="{
        name: 'answer-question',
        params: { questionId: question.questionId },
      }"
    >
      <h3>{{ question.question }}</h3>
      <span>Asked By: <h5>{{ question.senderUserName }}</h5></span>
      <div v-if="question.answered===true">
         You answered:<br/> {{ question.answer }}
        </div>
        <button v-if="question.answered===false" @click="answerQuestion">Answer Question</button>

    </div>
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
    
    answerQuestion(){
        this.$router.push({ name: "answer-question", params: {questionId: this.question.questionId} });

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