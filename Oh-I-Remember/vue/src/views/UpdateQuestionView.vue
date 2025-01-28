<template>
  <div class="content">
<edit-my-question :question="question" :receiverUserName="receiverUserName"></edit-my-question>
  </div>
</template>

<script>
import EditMyQuestion from '../components/EditMyQuestion.vue';
import QuestionService from '../services/QuestionService';
export default {
components: {
    EditMyQuestion
},
data() {
    return{
        question: {},
        receiverUserName: ''
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
                    QuestionService.getUserById(this.question.receiverId)
                    .then(
                        (response1) => {
                            this.receiverUserName = response1.data.firstName + ' ' + response1.data.lastName;
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