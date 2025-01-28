<template>
  <div class="content">
    <div class="small-container">
        {{ question.question }}
        {{ question.receiverUserName }}
    </div>
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