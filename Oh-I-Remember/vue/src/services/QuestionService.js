import axios from "axios";
export default{

    //CREATE
    askQuestionByReceiverId(questionDto){
        return axios.post('/questions/create', questionDto );
    },


    //READ
    getQuestionsByReceiverId(){
        return axios.get('/questions/questions');
    },
    getQuestionsBySenderId(){
        return axios.get('/questions/do-you-remember');
    },
    getQuestionsByUserId(){
        return axios.get('/questions/questions-by-user-id');
    },
    getQuestionByQuestionId(questionId){
        return axios.get(`/questions/question-id/${questionId}`);
    },
    getUserById(id) {
        return axios.get(`/get-user/${id}`)
    },

    //UPDATE
    answerQuestion(answerDto) {
        return axios.put('/questions/answer', answerDto)
    },
    updateQuestion(updateQuestionDto) {
        return axios.put('/questions/update-question', updateQuestionDto)
    },
    updateQuestionCategories(updateQuestionDto) {
        return axios.put('/questions/update-question-categories', updateQuestionDto)
    },


    //DELETE
   deleteQuestion(questionId){
        return axios.delete(`/questions/delete/${questionId}`);
    },
}