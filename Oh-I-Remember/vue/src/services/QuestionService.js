import axios from "axios";
export default{

    //READ
    getQuestionsByReceiverId(){
        return axios.get('/questions/questions');
    },
    getQuestionByQuestionId(questionId){
        return axios.get(`/questions/question-id/${questionId}`);
    },
    getUserById(id) {
        return axios.get(`/get-user/${id}`)
    }
}