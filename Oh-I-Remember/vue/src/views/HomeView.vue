<template>
  <div class="content">
    <h1 class="home-header">Welcome, {{ $store.state.user.firstName }}!</h1>
   
    <div class="card" id="home-title">
      <router-link class="styled-link"  @click="markAsRead('new_question')" v-bind:to="{ name: 'questions' }"
        >Oh, I Remember! <span v-if="this.questionNotificationCount>0">{{ this.questionNotificationCount }}</span></router-link
      >
    </div>
    
    <div class="card" id="home-title">
      <router-link class="styled-link"  v-bind:to="{ name: 'do-you-remember' }"
        >Do You Remember?</router-link
      >
    </div>
   
    <div class="card" id="home-title">
      <router-link class="styled-link"  @click="markAsRead('new_friend')" v-bind:to="{ name: 'friends-list' }"
        >Friends List <span v-if="this.newFriendNotificationCount>0">{{ this.newFriendNotificationCount }}</span></router-link
      >
    </div>
    <div class="card" id="home-title">
      <router-link class="styled-link" @click="markAsRead('answer_response')"  v-bind:to="{ name: 'question-history' }"
        >Question History <span v-if="this.answerNotificationCount>0">{{ this.answerNotificationCount }}</span></router-link
      >
    </div>
  </div>
</template>

<script>
import NotificationService from "../services/NotificationService";

export default {
  data() {
    return {
      questionNotificationCount: "",
      answerNotificationCount: "",
      newFriendNotificationCount: "",
      notificationReadRequestDto: { type: "" },
    };
  },
  created() {
    this.getNumberOfQuestionNotifications();
    this.getNumberOfAnswerNotifications();
    this.getNewFriendNotificationCount();
  },
  methods: {

    markAsRead(type){
      this.notificationReadRequestDto.type = type;
      NotificationService.markAsRead(this.notificationReadRequestDto);
    },
    getNumberOfQuestionNotifications() {
      NotificationService.getNumberOfQuestionNotifications().then(
        (response) => {
          this.questionNotificationCount = response.data;
        }
      );
    },
    getNumberOfAnswerNotifications() {
      NotificationService.getNumberOfNewAnswerNotifications().then(
        (response) => {
          this.answerNotificationCount = response.data;
        }
      );
    },
    getNewFriendNotificationCount() {

      NotificationService.getNumberOfNewFriendNotifications().then(
        (response) => {
          this.newFriendNotificationCount = response.data;
        }
      );
    },
  },
};
</script>
<style scoped>
.styled-link {
  font-size: 20px;
  font-weight: bold;
  color: #4a6fa5; 
  text-decoration: none;
  display: inline-block;
  margin-bottom: 10px;
  transition: color 0.3s, text-decoration 0.3s;
}


.styled-link:hover {
  color: #ff5733;  
  text-decoration: underline;
}


.styled-link span {
  font-size: 14px;
  background-color: #f79c42; 
  color: white;
  padding: 2px 6px;
  border-radius: 50%;
  margin-left: 10px;
}
.home-header{
  text-align: center;
}
</style>
