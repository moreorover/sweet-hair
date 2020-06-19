import axios from "axios";


export default {
    getSuppliers() {
        return axios.get("suppliers").then(resp => resp.data["_embedded"].suppliers);
    },
    newSupplier(supplier) {
        return axios.post("suppliers", supplier);
    },
    editSupplier(editedSupplier) {
        return axios.patch("suppliers/" + editedSupplier.id, editedSupplier);
    },
    deleteSupplier(supplier) {
        return axios.delete("suppliers/" + supplier.id);
    }
}