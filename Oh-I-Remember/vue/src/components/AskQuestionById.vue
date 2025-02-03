<template>
  <div class="content">
    <h3>What Would you like to ask?</h3>
    <div class="document-container">
      <form id="question-form" @submit.prevent="askQuestion">
        <input type="text" v-model="askQuestionDto.question" required />
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import QuestionService from "../services/QuestionService";
export default {
  props: {
    receiverId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      askQuestionDto: {
        receiverId: "",
        question: "",
      },
    };
  },
  methods: {
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