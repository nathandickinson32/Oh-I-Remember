<template>
  <div class="content">
    <h4>Welcome, {{ $store.state.user.firstName }}!</h4><br/>
    <div class="small-container" id="home-title">
      <router-link v-bind:to="{ name: 'questions' }">I Remember! {{ this.questionNotificationCount }}</router-link>
    </div>
    <br/>
    <div class="small-container" id="home-title">
      <router-link v-bind:to="{ name: 'do-you-remember' }">Do You Remember? {{ this.answerNotificationCount }}</router-link>

    </div>
    <br/>
    <div class="small-container" id="home-title">
      <router-link v-bind:to="{ name: 'friends-list' }">Friends List {{ this.newFriendNotificationCount }}</router-link>

    </div>
       

     

  </div>
</template>

<script>
import NotificationService from "../services/NotificationService";

export default {
  data(){
    return{
      questionNotificationCount: "",
      answerNotificationCount: "",
      newFriendNotificationCount: "",

    }
  },
  created(){
    this.getNumberOfQuestionNotifications();
    this.getNumberOfAnswerNotifications();
    this.getNewFriendNotificationCount();


  },
  methods: {
    getNumberOfQuestionNotifications() {
        NotificationService.getNumberOfQuestionNotifications().then((response) => {
          this.questionNotificationCount = response.data;
        })
    },
    getNumberOfAnswerNotifications() {
        NotificationService.getNumberOfNewAnswerNotifications().then((response) => {
          this.answerNotificationCount = response.data;
        })
    },
    getNewFriendNotificationCount() {
        NotificationService.getNumberOfNewFriendNotifications().then((response) => {
          this.newFriendNotificationCount = response.data;
        })
    },
  }
};
</script>
<style scoped>
h4 {
  text-align: center;
  margin: 0;
}

.content {
  flex-direction: column;
}
#home-title{
  text-align: center;
}
</style>
