import axios from "axios";


export default {
    getOrders() {
        return axios.get("orders").then(resp => resp.data["_embedded"].orders);
    },
    newOrder(order) {
        return axios.post("orders", order);
    },
    editOrder(editedOrder) {
        return axios.patch("orders/" + editedOrder.id, editedOrder);
    },
    deleteOrder(order) {
        return axios.delete("orders/" + order.id);
    },
    deleteOrderItem(payload) {
        return axios.delete("orders/" + payload.orderItem.orderId + "/products/" + payload.orderItem.orderId + "_" + payload.orderItem.productId, payload)
    }
}