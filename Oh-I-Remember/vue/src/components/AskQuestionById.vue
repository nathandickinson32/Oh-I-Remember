<template>
  <div class="content">
    <h1>What would you like to ask {{ receiverName }}?</h1>
    <div class="form-container">
    
      <form id="question-form" @submit.prevent="askQuestion">
        <input type="text" v-model="askQuestionDto.question" required />
        <multiselect
  v-model="askQuestionDto.categoryIds"
  :options="categoryList"
  label="categoryName"
  track-by="categoryId"
  :multiple="true"
  placeholder="Select Categories"
  id="categories"
/>
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import CategoryService from "../services/CategoryService";
import Multiselect from "vue-multiselect";
import QuestionService from "../services/QuestionService";
import FriendService from "../services/FriendService";
export default {
  components: {
    Multiselect
  },
  props: {
    receiverId: {
      type: Number,
      required: true,
    },
  },
  created(){
    this.getUser(Number(this.receiverId));
    this.getAllCategories();

  },
  data() {
    return {
      askQuestionDto: {
        receiverId: "",
        question: "",
        categoryIds: []
      },
      receiverName: "",
      categoryList: []
    };
  },
  methods: {
    getAllCategories(){
      CategoryService.getAllCategories().then((response) => {
        this.categoryList = response.data;
      })
    },
    getUser(receiverId) {
      QuestionService.getUserById(receiverId).then((response) => {
        this.receiverName =
          response.data.firstName + " " + response.data.lastName;
      });
    },
    askQuestion() {
      this.askQuestionDto.categoryIds = this.askQuestionDto.categoryIds.map((category) => category.categoryId);
       this.askQuestionDto.receiverId = Number(this.receiverId);
      QuestionService.askQuestionByReceiverId(this.askQuestionDto).then((response) => {
        if(response.status===201){
            window.alert("Success!")
            this.$router.push({name: "do-you-remember"})
        }
      })
    },
  },
};
</script>

<style>
</style>