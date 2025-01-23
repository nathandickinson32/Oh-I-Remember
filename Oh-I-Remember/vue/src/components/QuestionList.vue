<template>
  <div class="content">
    <div clas="question-list" v-if="questions.length ===0"><h4>No Questions to answer</h4></div>
    <div class="question-list" v-else>
        <question v-for="question in questions" :key="question.questionId" :question="question"></question>
    </div>
  </div>
</template>

<script>
import Question from './Question.vue';
import QuestionService from '../services/QuestionService';
export default {
    data(){
        return{
            questions: []
        }
    },
components: {
    Question
},
created(){
    this.getQuestions();
},
methods: {
getQuestions(){
    QuestionService.getQuestionsByReceiverId()
    .then(
        (response) => {
            this.questions = response.data;
        }
    )
}
}

}
</script>

<style scoped>

.content {
    flex-direction: column;
    width: 100%;
    box-sizing: border-box;
    gap: 10px;
}
.question-list {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    width: 100%;
}
</style>