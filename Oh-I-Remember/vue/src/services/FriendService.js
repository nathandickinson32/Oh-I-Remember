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

    getUserInfo(userIds){
        return axios.post('/friends/user-info', userIds)
    },

    //UPDATE
    friendRequestResponse(friendRequestResponseDto) {
        return axios.put('/friends/request-response',  friendRequestResponseDto );
    },

    

    //DELETE
    deleteFriendRequest(requestId){
        return axios.delete(`/friends/delete-request/${requestId}`)
    }
}