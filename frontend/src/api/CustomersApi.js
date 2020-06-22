import axios from "axios";


export default {
    getAll() {
        return axios.get("customer").then(response => response.data);
    },
    getById(id) {
        return axios.get("customer/" + id).then(response => response.data);
    },
    create(payload) {
        return axios.post("customer", payload).then(response => response.data);
    },
    update(payload) {
        return axios.patch("customer/" + payload.id, payload).then(response => response.data);
    },
    delete(payload) {
        return axios.delete("customer/" + payload.id);
    }
}