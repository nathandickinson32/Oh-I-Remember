<template>
    <div class="card">
      <h3>{{ question.question }}</h3>
  
      <p class="user-info">
        Asked By: {{ senderUserName }}<br />
        {{ userType === 'sender' ? 'Sent to' : 'Received By' }}: {{ receiverUserName }}
      </p>
  
        <h3>{{ question.answer }}</h3>
  
      <div
        class="question-category-list"
        v-for="category in question.categories"
        :key="category.categoryId"
      >
        {{ category.categoryName }}
      </div><br />

      <!-- <button @click="editQuestionCategories">
      Edit Question Categories
    </button> -->
  
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
  import MessageModal from "../components/MessageModal.vue";
  import QuestionService from "../services/QuestionService";  
  export default {
    components: {
    MessageModal,
  },
    props: {
      question: {
        type: Object,
        required: true,
      },
      userType: {
        type: String,
        required: true,
      },
    },
  
  
    data() {
      return {
        senderUserName: '',
        receiverUserName: '',
        message: "",
      type: "",
      isModalVisible: false,
      };
    },
  
    created() {
      this.getUsers(this.question);
    },
  
    methods: {
      editQuestionCategories() {
      this.$router.push({
        name: "edit-question-categories",
        params: { questionId: this.question.questionId },
      });
    },
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
    deleteQuestion() {
      QuestionService.deleteQuestion(this.question.questionId).then(
        (response) => {
          if (response.status === 200) {
            window.alert("Success!");
          }
        }
      );
    },
  
      getUsers(question) {
        QuestionService.getUserById(question.senderId).then((response) => {
          this.senderUserName = response.data.firstName + " " + response.data.lastName;
        });
  
        QuestionService.getUserById(question.receiverId).then((response) => {
          this.receiverUserName = response.data.firstName + " " + response.data.lastName;
        });
      },
    },
  };
  </script>
  
  <style >
 
  </style>
  