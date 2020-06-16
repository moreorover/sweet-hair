import axios from "axios";


export default {
    getCustomers() {
        return axios.get("customers").then(resp => resp.data);
    },
    newCustomer(customer) {
        return axios.post("customers", customer);
    },
    editCustomer(editCustomer) {
        return axios.patch("customers/" + editCustomer.id, editCustomer);
    },
    deleteCustomer(customer) {
        return axios.delete("customers/" + customer.id);
    }
}