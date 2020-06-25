import axios from "axios";


export default {
    getAll() {
        return axios.get("order").then(response => response.data);
    },
    getById(id) {
        return axios.get("order/" + id).then(response => response.data);
    },
    create(payload) {
        return axios.post("order", payload).then(response => response.data);
    },
    update(payload) {
        return axios.patch("order/" + payload.id, payload).then(response => response.data);
    },
    delete(payload) {
        return axios.delete("order/" + payload.id);
    }
}