import axios from "axios";

export default {
    //CREATE
    submitFriendRequest(receiverId) {
        return axios.post('/friends/friend-request', { receiverId });
    },

    //READ
    getFriendsByUserId(){
        return axios.get('/friends/friends-list');
    }

    //UPDATE


    //DELETE
}