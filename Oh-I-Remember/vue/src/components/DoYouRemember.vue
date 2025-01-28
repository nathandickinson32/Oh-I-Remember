<template>
  <div class="content">
    <router-link class="small-container"
    :to="{
        name: 'edit-question',
        params: {questionId: question.questionId}
    }">
        {{ question.question }}<br/>
        <span class="label">You asked: </span>
        {{ question.receiverUserName }}
    </router-link>
</div>
</template>

<script>
import QuestionService from '../services/QuestionService';

export default {
props: {
    question: {
        type: Object,
        required: true,
    }
},
created(){
    this.getUser(this.question);
},
methods: {
    getUser(question) {
      QuestionService.getUserById(question.receiverId).then((response) => {
        question.receiverUserName =
          response.data.firstName + " " + response.data.lastName;
      });
    },
}
}
</script>

<style>

</style>