<template>
  <div class="content">
    <h1>Answer Question</h1>
      <span>{{ senderUserName }} asked you: </span><br />
      {{ question.question }}<br />
      <label id="answer-title">Answer:</label><br/>

      <div class="form-container"> 
          <form id="answer-form" @submit.prevent="answerQuestion">
              <div class="form-input-group">
                  <input type="text" v-model="answerDto.answer" id="answer" required />
              </div>
              <button type="submit">Answer Question</button>
          </form>
      </div>
  </div>
</template>


<script>
import QuestionService from "../services/QuestionService";

export default {
  data(){
    return {
        answerDto:{
          questionId: "",
          answer: "",
          senderId: ""
        }
    }
  },
  props: {
    question: {
      type: Object,
      required: true,
    },
    senderUserName: {
      type: String,
      required: true,
    },
  },
  methods: {
    answerQuestion(){
      this.answerDto.questionId=this.question.questionId;
      this.answerDto.senderId=this.question.senderId;
        QuestionService.answerQuestion(this.answerDto)
        .then(
          (response) => {
            if(response.status===200){
              window.alert("Question Answered!")
              this.$router.push({name: "questions"})
            }
          }
        )

        
    }
  }
};
</script>

<style>


</style>