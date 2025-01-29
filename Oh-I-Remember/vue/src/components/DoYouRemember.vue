<template>
  <div class="content">
    <div class="small-container">
        {{ question.question }}<br/>
        <span class="label">You asked: </span>
        {{ question.receiverUserName }}
        <div v-if="question.answered===true">
          {{ question.answer }}
        </div>
        <button v-if="question.answered===false" @click="editQuestion">Edit Question</button>
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
    editQuestion(){
        this.$router.push({ name: "edit-question", params: {questionId: this.question.questionId} });

    }
}
}
</script>

<style>

</style>