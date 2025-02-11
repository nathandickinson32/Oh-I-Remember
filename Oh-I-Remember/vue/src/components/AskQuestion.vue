<template>
  <div class="content">
    <h1>Ask A New Question</h1>
    <div class="form-container">
      <form id="question-form" @submit.prevent="askQuestion">
        <label for="friend-id">Select Friend</label>
        <select name="friend-id" id="friend-id" v-model="askQuestionDto.receiverId" :value="askQuestionDto.receiverId" required>
            <option value="" disabled>Select Friend</option>
            <option v-for="friend in friendsList" v-bind:key="friend.id" :value="friend.id">
                {{ friend.firstName }} {{ friend.lastName }}
            </option>
        </select>

      <label for="question">Enter your question</label>
        <input id="question" type="text" v-model="askQuestionDto.question" required />

        <label for="categories">Select all categories that apply</label>
<multiselect
  v-model="askQuestionDto.categoryIds"
  :options="categoryList"
  label="categoryName"
  track-by="categoryId"
  :multiple="true"
  placeholder="Search Categories"
  id="categories"
/>


        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>
  
  <script>
import Multiselect from "vue-multiselect";
import CategoryService from "../services/CategoryService";
import QuestionService from "../services/QuestionService";
import FriendService from "../services/FriendService";
export default {
  components: {
    Multiselect
  },
  data() {
    return {
      askQuestionDto: {
        receiverId: "",
        question: "",
        categoryIds: []
      },
      friendsList: [],
      categoryList: []
    };
  },
  created() {
    this.getFriends();
    this.getAllCategories();
  },
  methods: {
    askQuestion() {
      this.askQuestionDto.categoryIds = this.askQuestionDto.categoryIds.map((category) => category.categoryId)
      QuestionService.askQuestionByReceiverId(this.askQuestionDto).then(
        (response) => {
          if (response.status === 201) {
            console.log(this.askQuestionDto);
            window.alert("Success!");
            this.$router.push({ name: "do-you-remember" });
          }
        }
      );
    },
    getFriends() {
      FriendService.getFriendsByUserId().then((response) => {
        this.friendsList = response.data;
      });
    },
    getAllCategories(){
      CategoryService.getAllCategories().then((response) => {
        this.categoryList = response.data;
      })
    },
  },
};
</script>
  
  <style >



</style>