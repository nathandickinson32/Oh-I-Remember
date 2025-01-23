<template>
  <div class="content">
<answer-question :question="question" :senderUserName="senderUserName"></answer-question>
  </div>
</template>

<script>
import AnswerQuestion from '../components/AnswerQuestion.vue';
import QuestionService from '../services/QuestionService';
export default {
    components: {
        AnswerQuestion
    },
    data(){
        return{
            question: {},
            senderUserName: ''
        }
    },
    created(){
        this.getQuestion();
    },

    methods: {
        getQuestion(){
            QuestionService.getQuestionByQuestionId(this.$route.params.questionId)
            .then(
                (response) => {
                    this.question = response.data;
                    QuestionService.getUserById(this.question.senderId)
                    .then(
                        (response1) => {
                            this.senderUserName = response1.data.firstName + ' ' + response1.data.lastName;
                        }
                    )
                }
            )
        }
    }

}
</script>

<style>

</style>