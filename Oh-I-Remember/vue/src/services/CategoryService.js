import axios from "axios";
export default {
    //READ
    getAllCategories(){
        return axios.get('/categories/categories')
    }
}