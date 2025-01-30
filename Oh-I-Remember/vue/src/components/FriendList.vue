<template>
  <div class="content">
    <button @click="addFriend = !addFriend">
      {{ addFriend ? "Cancel" : "Add Friend" }}
    </button>

    <div v-if="addFriend">
      <input type="number" v-model="receiverId" placeholder="Enter Friend ID" />
      <button @click="submitFriendRequest">Submit</button>
    </div>
    <div class="friend-list" v-if="friendsList.length === 0">
      <h4>Be sure to add friends!</h4>
    </div>
    <div class="friends-list" v-else>
      <Friend
        v-for="friend in friendsList"
        :key="friend.friendId"
        :friend="friend"
      />
    </div>
  </div>
</template>

<script>
import FriendService from "../services/FriendService";
import Friend from "../components/Friend.vue";
export default {
  data() {
    return {
      friendsList: [],
      addFriend: false,
      receiverId: ""
    };
  },
  components: {
    Friend
  },
  created() {
    this.getFriends();
  },
  methods: {
    submitFriendRequest() {
    if (!this.receiverId) return;
    
    FriendService.submitFriendRequest(this.receiverId)
        .then((response) => {
            // Check if the response status is 200 (success)
            if (response.status === 200) {
                this.receiverId = "";
                this.addFriend = false;
                window.alert("Success");
            }
        })
        .catch((error) => {
            if (error.response) {
                // If the status is 409 (Conflict), show the duplicate request message
                if (error.response.status === 409) {
                    window.alert(error.response.data.message || "Friend request already pending.");
                } else {
                    // For other errors, show a more general failure message
                    window.alert(error.response.data.message || "Failed to send friend request.");
                }
            } else {
                // If there was an unexpected error (network issues, etc.), log and alert
                console.error("Error adding friend:", error);
                window.alert("An unexpected error occurred. Please try again.");
            }
        });
},

    
    getFriends() {
      FriendService.getFriendsByUserId().then((response) => {
        this.friendsList = response.data;
      }
    );
      
    },
  },
};
</script>

<style>
</style>