<template>
  <div class="content">
    <button @click="askQuestion">Ask New Question</button>
    <div class="filter-section">
        <label @click="showUnAnsweredQuestions" class="clickable-label">Unanswered</label>
        <span class="seperator"> | </span>
        <label @click="showAnsweredQuestions" class="clickable-label">answered</label>

    </div>
    <div class="question-list" v-if="filteredQuestions.length === 0">
      <h4>All of you questions have been answered!</h4>
    </div>

    
    <div class="question-list" v-else>
      <do-you-remember
        v-for="question in filteredQuestions"
        :key="question.questionId"
        :question="question"
      ></do-you-remember>
    </div>
  </div>
</template>

<script>
import DoYouRemember from "./DoYouRemember.vue";
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
    showAnsweredQuestions(){
        this.filterType ='answered'
    },
    showUnAnsweredQuestions(){
        this.filterType ='unanswered'
    },
    askQuestion(){
      this.$router.push({name: "ask-question-view"})
    }
  },
};
</script>

<style>
</style>