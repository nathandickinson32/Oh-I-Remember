<template>
  <button @click="addFriend = !addFriend">
    {{ addFriend ? "Cancel" : "Add Friend" }}
  </button>

  <div v-if="addFriend">
    <input type="text" v-model="userName" placeholder="Enter Friend Username" />
    <button @click="submitFriendRequest">Submit</button>
  </div>

  <button @click="friendRequestList('friend_request')">
    Friend Requests<span v-if="this.notificationCount > 0"
      >{{ this.notificationCount }}</span
    >
  </button>
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
</template>

<script>
import FriendService from "../services/FriendService";
import Friend from "./Friend.vue";
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
    getNotificationCount() {
      NotificationService.getNumberOfFriendRequestNotifications().then(
        (response) => {
          this.notificationCount = response.data;
        }
      );
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

      const lowerCaseUserName = this.userName.toLowerCase();

      const loggedInUserId = this.$store.getters.loggedInUserId;

      if (
        lowerCaseUserName === this.$store.getters.loggedInUserName.toLowerCase()
      ) {
        window.alert("You cannot send a request to yourself.");
        return;
      }

      if (
        this.friendsList.some(
          (friend) => friend.username.toLowerCase() === lowerCaseUserName
        )
      ) {
        window.alert("You are already friends with this user.");
        return;
      }

      if (
        this.pendingRequests.some(
          (request) =>
            request.receiverUsername.toLowerCase() === lowerCaseUserName &&
            request.senderId === loggedInUserId
        )
      ) {
        window.alert("You already have a pending request with this user.");
        return;
      }

      if (
        this.pendingRequests.some(
          (request) =>
            request.senderUsername.toLowerCase() === lowerCaseUserName &&
            request.receiverId === loggedInUserId
        )
      ) {
        window.alert(
          "This user has already sent you a friend request. Accept their request instead."
        );
        return;
      }

      FriendService.submitFriendRequest(lowerCaseUserName)
        .then((response) => {
          if (response.status === 200) {
            this.userName = "";
            this.addFriend = false;
            window.alert("Friend request sent successfully.");
          }
        })
       
    },

    getFriends() {
      FriendService.getFriendsByUserId().then((response) => {
        this.friendsList = response.data;
      });
    },
  },
};
</script>

<style scoped>
button span{
  font-size: 14px;
  background-color: #4a6fa5; 
  color: white;
  padding: 2px 6px;
  border-radius: 50%;
  margin-left: 10px;
}
</style>