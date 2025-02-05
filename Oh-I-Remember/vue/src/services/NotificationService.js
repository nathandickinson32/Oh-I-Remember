import axios from "axios";

export default {

    //READ
    getNumberOfQuestionNotifications(){
        return axios.get('/notifications/question-notifications')
    },

    getNumberOfNewAnswerNotifications(){
        return axios.get('/notifications/answer-notifications')
    },

    getNumberOfFriendRequestNotifications(){
        return axios.get('/notifications/friend-request-notifications')
    },

    getNumberOfNewFriendNotifications(){
        return axios.get('/notifications/new-friends-notifications')
    }

}