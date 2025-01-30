import axios from "axios";

export default {
    //CREATE
    submitFriendRequest(receiverId) {
        return axios.post('/friends/friend-request', { receiverId });
    },


    //READ
    getFriendsByUserId(){
        return axios.get('/friends/friends-list');
    },

    getFriendRequestsByUserId(){
        return axios.get('/friends/friend-requests');
    },

    //UPDATE
    friendRequestResponse(friendRequestResponseDto) {
        return axios.put('/friends/request-response',  friendRequestResponseDto );
    },

    //DELETE
}