<template>
  <div class="content">
    <div class="large-container">
      {{ question.question }}<br />
      {{ senderUserName }}<br />
      <label id="answer-title">Answer:</label><br/>

      <form id="answer-form" @submit.prevent="answerQuestion">
          <div class="form-input-group">
            <input type="text" v-model="answerDto.answer" id="answer" required/>
          </div>
          <button type="submit">Answer Question</button>
      </form>
    </div>
  </div>
</template>

<script>
import Question from "./Question.vue";
import QuestionService from "../services/QuestionService";

export default {
  data(){
    return {
        answerDto:{
          questionId: "",
          answer: ""
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
        QuestionService.answerQuestion(this.answerDto)
        .then(
          (response) => {
            if(response.status===200){
              window.alert("Question Answered!")
            }
          }
        )

        
    }
  }
};
</script>

<style scoped>
#answer {
  font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif;
  width: 100%;
  height: 10em;
  resize: none;
  border-radius: 5px;
}
</style>