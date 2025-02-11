<template>
    <div class="card">
      <h3>{{ question.question }}</h3>
  
      <p class="user-info">
        Asked By: {{ senderUserName }}<br />
        {{ userType === 'sender' ? 'Sent to' : 'Received from' }}: {{ receiverUserName }}
      </p>
  
      <div v-if="question.answered">
        <strong>You answered:</strong>
        <p>{{ question.answer }}</p>
      </div>
  
      <div
        class="question-category-list"
        v-for="category in question.categories"
        :key="category.categoryId"
      >
        {{ category.categoryName }}
      </div><br />
  
      <button v-if="question.answered === false && userType === 'sender'" @click="answerQuestion">
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
      userType: {
        type: String,
        required: true,
      },
    },
  
  
    data() {
      return {
        senderUserName: '',
        receiverUserName: '',
      };
    },
  
    created() {
      this.getUsers(this.question);
    },
  
    methods: {
      answerQuestion() {
        this.$router.push({
          name: "answer-question",
          params: { questionId: this.question.questionId },
        });
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
  
  <style scoped>
  .card {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  
  .user-info {
    font-style: italic;
  }
  
  .question-category-list {
    display: inline-block;
    background-color: #e0e0e0;
    padding: 5px 10px;
    border-radius: 3px;
    margin-right: 10px;
  }
  
  button {
    background-color: #4a90e2;
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #357ab7;
  }
  </style>
  