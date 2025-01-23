<template>
    <router-link class="small-container"  :to="{name: 'answer-question', params: {questionId: question.questionId}}">  
        <h3>{{ question.question }}</h3>
        <h5>{{ question.senderUserName }}</h5>
    </router-link>
</template>

<script>
import QuestionService from '../services/QuestionService';

export default {
props: {
    question: {
        type: Object,
        required: true
    }
}, 

created(){
    this.getUser(this.question);
    
},
methods: {
    getUser(question){
        QuestionService.getUserById(question.senderId)
        .then(
            (response) => {
                question.senderUserName = response.data.firstName + ' ' + response.data.lastName;
            }
        )
    }
}

}
</script>

<style>

</style>