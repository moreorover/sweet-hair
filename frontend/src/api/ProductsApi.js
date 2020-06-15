import axios from "axios";


export default {
    getProducts() {
        return axios.get("products").then(resp => resp.data);
    },
    newProduct(product) {
        return axios.post("products", product);
    },
    editProduct(editedProduct) {
        return axios.patch("products/" + editedProduct.id, editedProduct);
    },
    deleteProduct(product) {
        return axios.delete("products/" + product.id);
    }
}