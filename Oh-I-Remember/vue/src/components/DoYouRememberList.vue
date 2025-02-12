<template>
 
    <button @click="askQuestion">Ask New Question</button>
   
    <div class="question-list" v-if="questions.length === 0">
      <h4>No questions need answered!</h4>
    </div>

    
    <div class="question-list" v-else>
      <do-you-remember
        v-for="question in questions"
        :key="question.questionId"
        :question="question"
      ></do-you-remember>
    </div>

</template>

<script>
import DoYouRemember from "./DoYouRemember.vue";
import QuestionService from "../services/QuestionService";
export default {
  
  data() {
    return {
      questions: [],
    };
  },

  components: {
    DoYouRemember,
  },
  created() {
    this.getQuestions();
  },
  methods: {
    getQuestions() {
      QuestionService.getQuestionsBySenderId().then((response) => {
        this.questions = response.data;
      });
    },
    askQuestion(){
      this.$router.push({name: "ask-question-view"})
    }
  },
};
</script>

<style>
</style>