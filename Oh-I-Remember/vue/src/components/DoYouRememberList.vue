<template>
  <div class="content">
    <div class="question-list" v-if="questions.length===0"><h4>All of you questions have been answered!</h4></div>
    <div class="question-list" v-else>
<do-you-remember v-for="question in questions" :key="question.questionId" :question="question"></do-you-remember>
    </div>
  </div>
</template>

<script>
import DoYouRemember from './DoYouRemember.vue';
import QuestionService from '../services/QuestionService';
export default {
    data() {
        return {
            questions: []
        }
    },

    components: {
        DoYouRemember
    },
    created(){
        this.getQuestions();

    },
    methods: {
getQuestions(){
    QuestionService.getQuestionsBySenderId()
    .then(
        (response) => {
            this.questions = response.data;
        }
    )
}
}

}
</script>

<style>

</style>