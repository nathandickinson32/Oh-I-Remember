<template>
    <div class="content">
      <friend-request v-for="friendRequest in friendRequestList"
                      :key="friendRequest.requestId"
                      :friendRequest="friendRequest"/>
    </div>
  </template>
  
  <script>
  import FriendRequest from './FriendRequest.vue';
  import FriendService from '../services/FriendService';
  
  export default {
    data() {
      return {
        friendRequestList: []
      };
    },
    components: {
      FriendRequest
    },
    created() {
      this.getFriendRequests();
    },
    methods: {
      getFriendRequests() {
        FriendService.getFriendRequestsByUserId()
          .then((response) => {
            this.friendRequestList = response.data;
          });
      }
    }
  };
  </script>
  
  <style>
  </style>
  