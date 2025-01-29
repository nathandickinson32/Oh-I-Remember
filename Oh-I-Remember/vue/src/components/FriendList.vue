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
    submitFriendRequest(){
        if(!this.receiverId) return;
        FriendService.submitFriendRequest(this.receiverId)
        .then(
          (response) => {
            if(response.status===200){
            this.receiverId="";
            this.addFriend= false;
            window.alert("Success")
            }
          
          }
        ).catch((error) => {
          console.error("Error adding friend:", error);
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