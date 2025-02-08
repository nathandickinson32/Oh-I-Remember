<template>
 
    <div class="filter-section">
      <label @click="showUnAnsweredQuestions" class="clickable-label"
        >Unanswered</label
      >
      <span class="seperator"> | </span>
      <label @click="showAnsweredQuestions" class="clickable-label"
        >answered</label
      >
    </div>
    <div clas="question-list" v-if="filteredQuestions.length === 0">
      <h4>No Questions to answer</h4>
    </div>
    <div class="question-list" v-else>
      <question
        v-for="question in filteredQuestions"
        :key="question.questionId"
        :question="question"
      ></question>
    </div>
 
</template>

<script>
import Question from "./Question.vue";
import QuestionService from "../services/QuestionService";
export default {
  computed: {
    filteredQuestions() {
      if (this.filterType === "unanswered") {
        return this.questions.filter((question) => question.answered === false);
      } else if (this.filterType === "answered") {
        return this.questions.filter((question) => question.answered === true);
      }
      return [];
    },
  },
  data() {
    return {
      questions: [],
      filterType: "unanswered",
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
    showAnsweredQuestions() {
      this.filterType = "answered";
    },
    showUnAnsweredQuestions() {
      this.filterType = "unanswered";
    },
  },
};
</script>

<style>

</style>