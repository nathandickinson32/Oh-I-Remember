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
  
  <style scoped>
.multiselect {
  width: 100%;
  max-width: 300px;
  margin-bottom: 20px;
}

.multiselect__placeholder {
  color: #4a6fa5;
}

.multiselect__content {
  max-height: 300px;
  overflow-y: auto;
}

::v-deep(.multiselect__option) {
  color: #4a6fa5 !important; 
  background-color: #f0f0f0 !important;
}

::v-deep(.multiselect__option--highlight) {
  background-color: #4a6fa5 !important; 
  color: white !important;
}

::v-deep(.multiselect__option--selected) {
  background-color: #4a6fa5 !important;
  color: white !important;
}

.filter-section {
  margin-top: 10px;
}

.clickable-label {
  cursor: pointer;
  color: #4a6fa5;
  font-weight: bold;
  font-size: 16px;
}

.clickable-label:hover {
  color: #ff5733;
}


</style>