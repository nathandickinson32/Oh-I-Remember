import axios from "axios";
export default{

    //READ
    getQuestionsByReceiverId(){
        return axios.get('/questions/questions');
    },
    getQuestionsBySenderId(){
        return axios.get('/questions/do-you-remember');
    },
    getQuestionByQuestionId(questionId){
        return axios.get(`/questions/question-id/${questionId}`);
    },
    getUserById(id) {
        return axios.get(`/get-user/${id}`)
    },
    answerQuestion(answerDto) {
        return axios.put('/questions/answer', answerDto)
    }
}