import axios from "axios";


export default {
    getSuppliers() {
        return axios.get("suppliers").then(resp => resp.data);
    }
}