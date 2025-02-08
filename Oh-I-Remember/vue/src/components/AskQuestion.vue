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
        <input type="text" v-model="askQuestionDto.question" required />
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>
  
  <script>
import QuestionService from "../services/QuestionService";
import FriendService from "../services/FriendService";
export default {
  data() {
    return {
      askQuestionDto: {
        receiverId: "",
        question: "",
      },
      friendsList: [],
    };
  },
  created() {
    this.getFriends();
  },
  methods: {
    askQuestion() {
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
  },
};
</script>
  
  <style >


</style>