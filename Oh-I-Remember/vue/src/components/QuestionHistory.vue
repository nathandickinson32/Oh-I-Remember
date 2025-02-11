<template>
  <div class="content">
    <multiselect
      v-model="selectedCategoryId"
      :options="categories"
      label="categoryName"
      track-by="categoryId"
      :multiple="false"
      placeholder="Search Categories"
      id="categories"
      @clear="clearCategoryFilter"
    />

    <div class="filter-section">
      <label @click="setUserTypeFilter('sender')" class="clickable-label"
        >Sender</label
      >
      <span class="separator"> | </span>
      <label @click="setUserTypeFilter('receiver')" class="clickable-label"
        >Receiver</label
      >
      <span class="separator"> | </span>
      <label @click="setUserTypeFilter('both')" class="clickable-label"
        >Both</label
      >
    </div>

    <div class="question-list" v-if="filteredQuestions.length === 0">
      <h4>No questions available in this category!</h4>
    </div>

    <div class="question-list" v-else>
      <sender-receiver-question
        v-for="question in filteredQuestions"
        :key="question.questionId"
        :question="question"
        :userType="userTypeFilter"
      />
    </div>
  </div>
</template>

<script>
import Multiselect from "vue-multiselect";
import SenderReceiverQuestion from "./SenderReceiverQuestion.vue";
import QuestionService from "../services/QuestionService";
import CategoryService from "../services/CategoryService";

export default {
  data() {
    return {
      questions: [],
      categories: [],
      selectedCategoryId: null,
      userTypeFilter: "both",
    };
  },

  components: {
    SenderReceiverQuestion,
    Multiselect,
  },

  created() {
    this.getCategories();
    this.getQuestions();
  },

  computed: {
    filteredQuestions() {
      const loggedInUserId = this.$store.getters.loggedInUserId;

      let filtered = this.selectedCategoryId
        ? this.questions.filter((question) =>
            question.categories.some(
              (category) =>
                category.categoryId === this.selectedCategoryId.categoryId
            )
          )
        : this.questions;

      if (this.userTypeFilter === "sender") {
        filtered = filtered.filter(
          (question) => question.senderId === loggedInUserId
        );
      } else if (this.userTypeFilter === "receiver") {
        filtered = filtered.filter(
          (question) => question.receiverId === loggedInUserId
        );
      }

      return filtered;
    },
  },

  methods: {
    getCategories() {
      CategoryService.getAllCategories().then((response) => {
        this.categories = response.data;
      });
    },
    getQuestions() {
      QuestionService.getQuestionsByUserId().then((response) => {
        this.questions = response.data;
      });
    },
    clearCategoryFilter() {
      this.selectedCategoryId = null;
    },
    setUserTypeFilter(type) {
      this.userTypeFilter = type;
    },
  },
};
</script>

<style scoped>
.multiselect {
  width: 100%;
  max-width: 300px;
  margin-bottom: 20px;
}

.multiselect__placeholder {
  color: #4a6fa5;
}

.multiselect__content {
  max-height: 300px;
  overflow-y: auto;
}

::v-deep(.multiselect__option) {
  color: #4a6fa5 !important; 
  background-color: #f0f0f0 !important;
}

::v-deep(.multiselect__option--highlight) {
  background-color: #4a6fa5 !important; 
  color: white !important;
}

::v-deep(.multiselect__option--selected) {
  background-color: #4a6fa5 !important;
  color: white !important;
}

.filter-section {
  margin-top: 10px;
}

.clickable-label {
  cursor: pointer;
  color: #4a6fa5;
  font-weight: bold;
  font-size: 16px;
}

.clickable-label:hover {
  color: #ff5733;
}

.separator {
  margin: 0 5px;
}

.question-list {
  margin-top: 20px;
}
</style>
