import axios from "axios";

export default {
    //CREATE
    submitFriendRequest(userName) {
        return axios.post('/friends/friend-request', { userName });
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

    getUserInfoByUsername(username){
        return axios.post('/get-user-by-username', username)
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