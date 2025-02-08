<template>
  <div class="card">
    <strong>You asked: {{ question.receiverUserName }}</strong>
   <p>{{ question.question }}</p> 
    

    <div v-if="question.answered">
      <strong>{{ question.receiverUserName }} Answered:</strong>
      <p>{{ question.answer }}</p>
    </div>
    <button v-if="question.answered === false" @click="editQuestion">
      Edit Question
    </button>
    <button @click="showModal">Delete Question</button>
    <message-modal
      :message="message"
      :type="type"
      v-if="isModalVisible"
      @cancelModal="cancelModal"
      @deleteModal="deleteModal"
    />
  </div>
</template>

<script>
import QuestionService from "../services/QuestionService";
import MessageModal from "../components/MessageModal.vue";
export default {
  components: {
    MessageModal,
  },
  data() {
    return {
      message: "",
      type: "",
      isModalVisible: false,
    };
  },

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
    showModal() {
      this.message = "Are you sure you want to delete this question?";
      this.type = "WARNING";
      this.isModalVisible = true;
    },
    deleteModal() {
      this.deleteQuestion();
      this.isModalVisible = false;
      this.$router.push({ name: "home" });
    },
    cancelModal() {
      this.isModalVisible = false;
      this.$router.push({ name: "do-you-remember" });
    },
    getUser(question) {
      QuestionService.getUserById(question.receiverId).then((response) => {
        question.receiverUserName =
          response.data.firstName + " " + response.data.lastName;
      });
    },
    editQuestion() {
      this.$router.push({
        name: "edit-question",
        params: { questionId: this.question.questionId },
      });
    },
    deleteQuestion() {
      QuestionService.deleteQuestion(this.question.questionId).then(
        (response) => {
          if (response.status === 200) {
            window.alert("Success!");
          }
        }
      );
    },
  },
};
</script>

<style>
</style>