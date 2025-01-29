<template>
  <div class="content">
    <div class="friend-list" v-if="friends.length === 0">
      <h4>Be sure to add friends!</h4>
    </div>
    <div class="friends-list" v-else>
      <friend/>
    </div>
  </div>
</template>

<script>
import FriendService from "../services/FriendService";
import Friend from "../components/Friend.vue"
export default {
  data() {
    return {
      friendsList: [],
    };
  },
  components: {
    Friend
  },
  created() {
    this.getFriends();
  },
  methods: {
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