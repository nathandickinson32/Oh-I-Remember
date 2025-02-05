import axios from "axios";

export default {

    //READ
    getNumberOfQuestionNotifications(){
        return axios.get('/notifications/question-notifications')
    }

}