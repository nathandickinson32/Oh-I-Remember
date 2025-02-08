<template>
  
    <div class="friend-card">
      <div class="sent-list" v-if="this.friendRequest.senderId===this.$store.getters.loggedInUserId">
        {{ friendRequest.receiver.firstName }} {{ friendRequest.receiver.lastName }}
        <button @click="cancelRequest">Cancel</button>
      </div>

      
      <div
        v-if="friendRequest.receiverId === this.$store.getters.loggedInUserId"
      >
      {{ friendRequest.sender.firstName }} {{ friendRequest.sender.lastName }}


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

  props: {
    friendRequest: {
      type: Object,
      required: true,
    },
   
  },
  methods: {
    cancelRequest(){
      FriendService.deleteFriendRequest(this.friendRequest.requestId).then((response) =>{
        if (response.status === 200) {
            window.alert("Successfully Canceled");
            

          }
      })
    },

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
