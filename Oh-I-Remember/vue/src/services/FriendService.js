import axios from "axios";

export default {
    //CREATE


    //READ
    getFriendsByUserId(){
        return axios.get('/friends/friends-list');
    }

    //UPDATE


    //DELETE
}