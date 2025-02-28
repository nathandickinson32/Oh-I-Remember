<template>
    <edit-question-categories :question="question" :receiverUserName="receiverUserName"></edit-question-categories>
    </template>
    
    <script>
    import EditQuestionCategories from '../components/EditQuestionCategories.vue';
    import QuestionService from '../services/QuestionService';
    export default {
    components: {
        EditQuestionCategories
    },
    data() {
        return{
            question: {},
            receiverUserName: ''
        }
    },
    created(){
        this.getQuestion();
    },
    methods: {
            getQuestion(){
                QuestionService.getQuestionByQuestionId(this.$route.params.questionId)
                .then(
                    (response) => {
                        this.question = response.data;
                        QuestionService.getUserById(this.question.receiverId)
                        .then(
                            (response1) => {
                                this.receiverUserName = response1.data.firstName + ' ' + response1.data.lastName;
                            }
                        )
                    }
                )
            }
        }
    
    }
    </script>
    
    <style>
    
    </style>