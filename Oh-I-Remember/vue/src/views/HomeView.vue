<template>
  <div class="content">
    <h1 class="home-header">Welcome, {{ $store.state.user.firstName }}!</h1>
   
    <div class="card" id="home-title">
      <router-link class="styled-link"  @click="markAsRead('new_question')" v-bind:to="{ name: 'questions' }"
        >Oh, I Remember! <span v-if="questionNotificationCount>0">{{ questionNotificationCount }}</span></router-link
      >
    </div>
    
    <div class="card" id="home-title">
      <router-link class="styled-link"  v-bind:to="{ name: 'do-you-remember' }"
        >Do You Remember?</router-link
      >
    </div>
   
    <div class="card" id="home-title">
      <router-link class="styled-link"  @click="markAsRead('new_friend')" v-bind:to="{ name: 'friends-list' }"
        >Friends List <span v-if="notificationCount>0">{{ notificationCount}}</span></router-link
      >
    </div>
    <div class="card" id="home-title">
      <router-link class="styled-link" @click="markAsRead('answer_response')"  v-bind:to="{ name: 'question-history' }"
        >Question History <span v-if="answerNotificationCount>0">{{ answerNotificationCount }}</span></router-link
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
      notificationReadRequestDto: { type: "" },
      notificationCount: 0,
    };
  },
  created() {
    this.getNumberOfQuestionNotifications();
    this.getNumberOfAnswerNotifications();
    this.getfriendNotificationCount();
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
    getfriendNotificationCount() {
  Promise.all([
    NotificationService.getNumberOfNewFriendNotifications(),
    NotificationService.getNumberOfFriendRequestNotifications()
  ]).then(([newFriendResponse, friendRequestResponse]) => {
    this.newFriendNotificationCount = newFriendResponse.data;
    this.newFriendRequestNotificationCount = friendRequestResponse.data;

    this.notificationCount =
      Number(this.newFriendNotificationCount) + Number(this.newFriendRequestNotificationCount);
  });
}
  
   
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
