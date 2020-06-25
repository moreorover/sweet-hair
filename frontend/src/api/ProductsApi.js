import axios from "axios";


export default {
    getAll() {
        return axios.get("product").then(response => response.data);
    },
    getById(id) {
        return axios.get("product/" + id).then(response => response.data);
    },
    create(payload) {
        return axios.post("product", payload).then(response => response.data);
    },
    update(payload) {
        return axios.patch("product/" + payload.id, payload).then(response => response.data);
    },
    delete(payload) {
        return axios.delete("product/" + payload.id);
    }
}