import axios from "axios";


export default {
    getAll() {
        return axios.get("sale").then(response => response.data);
    },
    getById(id) {
        return axios.get("sale/" + id).then(response => response.data);
    },
    create(payload) {
        return axios.post("sale", payload).then(response => response.data);
    },
    update(payload) {
        return axios.patch("sale/" + payload.id, payload).then(response => response.data);
    },
    delete(payload) {
        return axios.delete("sale/" + payload.id);
    }
}