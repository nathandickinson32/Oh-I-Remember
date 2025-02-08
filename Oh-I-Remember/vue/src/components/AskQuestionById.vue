<template>
  <div class="content">
    <h1>What would you like to ask {{ receiverName }}?</h1>
    <div class="form-container">
      <form id="question-form" @submit.prevent="askQuestion">
        <input type="text" v-model="askQuestionDto.question" required />
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import QuestionService from "../services/QuestionService";
import FriendService from "../services/FriendService";
export default {
  props: {
    receiverId: {
      type: Number,
      required: true,
    },
  },
  created(){
    this.getUser(Number(this.receiverId));
  },
  data() {
    return {
      askQuestionDto: {
        receiverId: "",
        question: "",
      },
      receiverName: ""
    };
  },
  methods: {
    getUser(receiverId) {
      QuestionService.getUserById(receiverId).then((response) => {
        this.receiverName =
          response.data.firstName + " " + response.data.lastName;
      });
    },
    askQuestion() {
       this.askQuestionDto.receiverId = Number(this.receiverId)
      QuestionService.askQuestionByReceiverId(this.askQuestionDto).then((response) => {
        if(response.status===201){
            window.alert("Success!")
            this.$router.push({name: "do-you-remember"})
        }
      })
    },
  },
};
</script>

<style>
</style>