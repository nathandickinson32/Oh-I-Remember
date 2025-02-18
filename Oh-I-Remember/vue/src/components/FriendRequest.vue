<template>
  
    <div class="friend-card">
      <div class="sent-list" v-if="friendRequest.senderId===loggedInUserId">
        {{ friendRequest.receiver.firstName }} {{ friendRequest.receiver.lastName }}
        <button @click="cancelRequest">Cancel</button>
      </div>

      
      <div
        v-if="friendRequest.receiverId === loggedInUserId"
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
  computed: {
  loggedInUserId() {
    return this.$store.getters.loggedInUserId;
  }
},

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
      }).catch((error) => {
      console.error("Error canceling request:", error);
      window.alert("Failed to cancel request. Please try again.");
    });
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
            this.$router.push({ name: "friends-list" });
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

<style scoped>
@media (min-width: 768px) {
 button{
   margin-left: 25px;
  }
}
</style>
