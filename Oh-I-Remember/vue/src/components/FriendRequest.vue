<template>
  <div class="content">
    <div class="small-container">
      <h3>{{ friendRequest.user.firstName }} {{ friendRequest.user.lastName }}</h3>
      <button @click="acceptRequest">Accept</button>
      <button @click="denyRequest">Deny</button>
    </div>
  </div>
</template>

<script>
import FriendService from "../services/FriendService";

export default {
  data() {
    return {
      friendRequestResponseDto: {
        requestId: null,
        statusId: null,
      },
    };
  },
  created() {
  console.log(this.friendRequest); // Add this to inspect the data passed to the component
},
  props: {
    friendRequest: {
      type: Object,
      required: true,
    },
  },
  methods: {
    acceptRequest() {

      this.friendRequestResponseDto.requestId = this.friendRequest.requestId;
      this.friendRequestResponseDto.statusId = 2;
      this.sendResponse();
    },

    denyRequest() {
      if (this.friendRequest && this.friendRequest.requestId) {
        this.friendRequestResponseDto.requestId = this.friendRequest.requestId; 
        this.friendRequestResponseDto.statusId = 3;
        this.sendResponse();
      } else {
        console.error("Friend request ID is missing.");
      }
    },

    sendResponse() {
      FriendService.friendRequestResponse(this.friendRequestResponseDto)
        .then((response) => {
          if (response.status === 200) {
            window.alert("Successfully responded");
            this.friendRequestResponseDto.statusId = null;
            window.location.reload();
          }
        })
        .catch((error) => {
          console.error("Error processing the request:", error);
          window.alert("An error occurred. Please try again.");
        });
    },
  },
};
</script>

<style>
</style>
