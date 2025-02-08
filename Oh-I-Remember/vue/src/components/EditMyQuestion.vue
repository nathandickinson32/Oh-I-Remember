<template>
  <div class="content">
    <h1>Edit Question</h1>
    <div class="form-container">
        {{ question.question }}
        <form id="question-form" @submit.prevent="updateQuestion">
            <div class="form-input-group">
                <input type="text" v-model="updateQuestionDto.question" id="quesstion" required/>
            </div>
            <button type="submit">Update Question</button>
        </form>
    </div>
  </div>
</template>

<script>
import QuestionService from '../services/QuestionService';
export default {
    data(){
    return {
        updateQuestionDto:{
          questionId: "",
          question: ""
        }
    }
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
    updateQuestion(){
      this.updateQuestionDto.questionId=this.question.questionId;
        QuestionService.updateQuestion(this.updateQuestionDto)
        .then(
          (response) => {
            if(response.status===200){
              window.alert("Question Updated!")
              this.$router.push({name: "do-you-remember"})
            }
          }
        )

        
    }
  }
}
</script>

<style>


</style>