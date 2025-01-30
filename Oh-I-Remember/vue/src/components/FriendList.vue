<template>
  <div class="content">
    <button @click="addFriend = !addFriend">
      {{ addFriend ? "Cancel" : "Add Friend" }}
    </button>

    <div v-if="addFriend">
      <input type="number" v-model="receiverId" placeholder="Enter Friend ID" />
      <button @click="submitFriendRequest">Submit</button>
    </div>

    <button @click="friendRequestList">Friend Requests</button>
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
      receiverId: "",
    };
  },
  components: {
    Friend,
  },
  created() {
    this.getFriends();
  },
  methods: {
    friendRequestList() {
      this.$router.push({ name: "friend-requests" });
    },

    submitFriendRequest() {
      if (!this.receiverId) return;

      const loggedInUserId = this.$store.getters.loggedInUserId;
      if(parseInt(this.receiverId) === loggedInUserId){
        window.alert("You cannot send a request to yourself");
        return;
      }

      if (this.friendsList.some(friend => friend.id === this.receiverId)) {
    window.alert("You are already friends with this user.");
    return;
  }
  if (this.friendsList.some(friend => friend.receiverId === this.receiverId && friend.status === 'pending')) {
    window.alert("You already have a pending request with this user.");
    return;
  }
      FriendService.submitFriendRequest(this.receiverId)
        .then((response) => {
          if (response.status === 200) {
            this.receiverId = "";
            this.addFriend = false;
            window.alert("Success");
          }
        })
        .catch((error) => {
          if (error.response) {
            if (error.response.status === 409) {
              window.alert(
                error.response.data.message || "Friend request already pending."
              );
            } else {
              window.alert(
                error.response.data.message || "Failed to send friend request."
              );
            }
          } else {
            console.error("Error adding friend:", error);
            window.alert("An unexpected error occurred. Please try again.");
          }
        });
    },

    getFriends() {
      FriendService.getFriendsByUserId().then((response) => {
        this.friendsList = response.data;
      });
    },
  },
};
</script>

<style>
</style>