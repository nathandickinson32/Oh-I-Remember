<template>
  <div class="content">
    <h1>Edit Question to {{ receiverUserName }}</h1>
    <div class="form-container">
      <p>{{ question.question }}</p>
      <form id="question-form" @submit.prevent="updateQuestion">
        <div class="form-input-group">
          <label for="question">Question</label>
          <input
            type="text"
            v-model="updateQuestionDto.question"
            id="question"
            required
          />
        </div>
        <label for="categories">Select all categories that apply</label>
        <multiselect
          v-model="updateQuestionDto.categoryIds"
          :options="categoryList"
          label="categoryName"
          track-by="categoryId"
          :multiple="true"
          placeholder="Search Categories"
          id="categories"
        />
        <button type="submit">Update Question</button>
      </form>
    </div>
  </div>
</template>

<script>
import QuestionService from "../services/QuestionService";
import Multiselect from "vue-multiselect";
import CategoryService from "../services/CategoryService";

export default {
  components: {
    Multiselect
  },
  data() {
    return {
      updateQuestionDto: {
        questionId: "",
        question: "",
        categoryIds: [],
      },
      categoryList: [],
    };
  },
  mounted() {
    if (this.question) {
      this.updateQuestionDto = {
        questionId: this.question.questionId,
        question: this.question.question,
        categoryIds: this.question.categories || [], // Only map the categoryIds if present
      };
    }
    this.getAllCategories();
  },
  props: {
    question: {
      type: Object,
      required: true,
    },
    receiverUserName: {
      type: String,
      required: true,
    },
  },
  methods: {
    updateQuestion() {
      QuestionService.updateQuestion(this.updateQuestionDto).then(
        (response) => {
          if (response.status === 200) {
            window.alert("Question Updated!");
            this.$router.push({ name: "do-you-remember" });
          }
        }
      ).catch((error) => {
        console.error("Error updating question:", error);
        window.alert("An error occurred while updating the question.");
      });
    },
    getAllCategories() {
      CategoryService.getAllCategories().then((response) => {
        this.categoryList = response.data;
      }).catch((error) => {
        console.error("Error fetching categories:", error);
        window.alert("An error occurred while fetching categories.");
      });
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
</style>
