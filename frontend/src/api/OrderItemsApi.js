import axios from "axios";


export default {
    getOrderItems() {
        return axios.get("order_items").then(resp => resp.data);
    },
    newOrderItem(orderItem) {
        return axios.post("order_items", orderItem);
    },
    editOrderItem(editOrderItem) {
        return axios.patch("order_items/" + editOrderItem.id, editOrderItem);
    },
    deleteOrderItem(orderItem) {
        return axios.delete("order_items/" + orderItem.id);
    }
}