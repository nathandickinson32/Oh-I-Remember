<template>
  <div class="content">
    <button @click="addFriend = !addFriend">
      {{ addFriend ? "Cancel" : "Add Friend" }}
    </button>

    <div v-if="addFriend">
      <input type="text" v-model="userName" placeholder="Enter Friend Username" />
      <button @click="submitFriendRequest">Submit</button>
    </div>

    <button @click="friendRequestList('friend_request')">Friend Requests:<span v-if="this.notificationCount>0">{{ this.notificationCount }}</span>    </button>
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
import NotificationService from "../services/NotificationService";
export default {
  data() {
    return {
      friendsList: [],
      pendingRequests: [],
      addFriend: false,
      userName: "",
      notificationCount: "",
      notificationReadRequestDto: { type: "" },

    };
  },
  components: {
    Friend,
  },
  created() {
    this.getFriends();
    this.getPendingRequests();
    this.getNotificationCount();
  },
  methods: {
    getNotificationCount(){
      NotificationService.getNumberOfFriendRequestNotifications().then((response) =>{
        this.notificationCount = response.data;
      })
    },
    getPendingRequests() {
      FriendService.getFriendRequestsByUserId().then((response) => {
        this.pendingRequests = response.data;
      });
    },
    friendRequestList(type) {
      this.notificationReadRequestDto.type = type;
      NotificationService.markAsRead(this.notificationReadRequestDto);
      this.$router.push({ name: "friend-requests" });
    },

    submitFriendRequest() {
  if (!this.userName) return;

  const loggedInUserId = this.$store.getters.loggedInUserId;


  if (this.userName === this.$store.getters.loggedInUserName) {
    window.alert("You cannot send a request to yourself.");
    return;
  }

  if (this.friendsList.some((friend) => friend.username === this.userName)) {
    window.alert("You are already friends with this user.");
    return;
  }

  if (
    this.pendingRequests.some(
      (request) => request.receiverUsername === this.userName && request.senderId === loggedInUserId
    )
  ) {
    window.alert("You already have a pending request with this user.");
    return;
  }

  if (
    this.pendingRequests.some(
      (request) => request.senderUsername === this.userName && request.receiverId === loggedInUserId
    )
  ) {
    window.alert("This user has already sent you a friend request. Accept their request instead.");
    return;
  }

  FriendService.submitFriendRequest(this.userName)
    .then((response) => {
      if (response.status === 200) {
        this.userName = "";
        this.addFriend = false;
        window.alert("Friend request sent successfully.");
      }
    })
    .catch((error) => {
      if (error.response) {
        const { status, data } = error.response;

        if (status === 404) {
          window.alert(data.message || "The username does not exist.");
        } else if (status === 400) {
          window.alert(data.message || "Invalid username provided.");
        } else if (status === 409) {
          window.alert(data.message || "A pending friend request already exists.");
        } else if (status === 500) {
          window.alert(data.message || "An unexpected server error occurred. Please try again later.");
        } else {
          window.alert("An error occurred. Please try again.");
        }
      } else {
        console.error("Error sending friend request:", error);
        window.alert("An unexpected error occurred. Please try again.");
      }
    });
},


    getFriends() {
      FriendService.getFriendsByUserId().then((response) => {
        this.friendsList = response.data;
      });
    },
  }
};
</script>

<style>
</style>