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