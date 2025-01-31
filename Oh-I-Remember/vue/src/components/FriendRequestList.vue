<template>
    <div class="content">
      <div class="filter-section">
        <div class="clickable-label" @click="showSentRequests">Sent</div>
        <div class="clickable-label" @click="showReceivedRequests">Received</div>
      </div>
   

      <div class="request-list" v-if="friendRequestList.length===0"><h3>No Pending Requests</h3></div>
      <div class="request-list" v-else>
   <friend-request v-for="friendRequest in filteredRequestList"
                      :key="friendRequest.requestId"
                      :friendRequest="friendRequest"/>
      </div>
   
    </div>
  </template>
  
  <script>
  import FriendRequest from './FriendRequest.vue';
  import FriendService from '../services/FriendService';
  
  export default {
    computed: {
        filteredRequestList(){
          const loggedInUserId = this.$store.getters.loggedInUserId
          if(this.filterType==='Received'){
            return this.friendRequestList.filter(friendRequest => friendRequest.receiverId === loggedInUserId)
          }else if(this.filterType === 'Sent'){
            return this.friendRequestList.filter(friendRequest => friendRequest.senderId === loggedInUserId)
          }
          return [];
        },
    },
    data() {
      return {
        friendRequestList: [],
        filterType: 'Received',
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
      },
      showSentRequests(){
        this.filterType = 'Sent'
      },
      showReceivedRequests(){
        this.filterType = 'Received'
      }
    }
  };
  </script>
  
  <style>
  </style>
  