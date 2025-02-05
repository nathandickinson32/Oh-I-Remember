<template>
  <div class="content">
    <h4>Welcome, {{ $store.state.user.firstName }}!</h4>
    <br />
    <div class="small-container" id="home-title">
      <router-link @click="markAsRead('new_question')" v-bind:to="{ name: 'questions' }"
        >I Remember! <span v-if="this.questionNotificationCount>0">{{ this.questionNotificationCount }}</span></router-link
      >
    </div>
    <br />
    <div class="small-container" id="home-title">
      <router-link @click="markAsRead('answer_response')" v-bind:to="{ name: 'do-you-remember' }"
        >Do You Remember?<span v-if="this.answerNotificationCount>0">{{ this.answerNotificationCount }}</span></router-link
      >
    </div>
    <br />
    <div class="small-container" id="home-title">
      <router-link @click="markAsRead('new_friend')" v-bind:to="{ name: 'friends-list' }"
        >Friends List <span v-if="this.newFriendNotificationCount>0">{{ this.newFriendNotificationCount }}</span></router-link
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
h4 {
  text-align: center;
  margin: 0;
}

.content {
  flex-direction: column;
}
#home-title {
  text-align: center;
}
</style>
