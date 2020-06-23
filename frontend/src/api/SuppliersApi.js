import axios from "axios";


export default {
    getAll() {
        return axios.get("supplier").then(response => response.data);
    },
    getById(id) {
        return axios.get("supplier/" + id).then(response => response.data);
    },
    create(payload) {
        return axios.post("supplier", payload).then(response => response.data);
    },
    update(payload) {
        return axios.patch("supplier/" + payload.id, payload).then(response => response.data);
    },
    delete(payload) {
        return axios.delete("supplier/" + payload.id);
    }
}