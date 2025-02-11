<template>
 
    
    <div clas="question-list" v-if="questions.length === 0">
      <h4>No Questions to answer</h4>
    </div>
    <div class="question-list" v-else>
      <question
        v-for="question in questions"
        :key="question.questionId"
        :question="question"
      ></question>
    </div>
 
</template>

<script>
import Question from "./Question.vue";
import QuestionService from "../services/QuestionService";
export default {

  data() {
    return {
      questions: [],
    };
  },
  components: {
    Question,
  },
  created() {
    this.getQuestions();
  },
  methods: {
  
    getQuestions() {
      QuestionService.getQuestionsByReceiverId().then((response) => {
        this.questions = response.data;
      });
    },
  
  },
};
</script>

<style>

</style>