<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <template v-slot:activator="{ on, attrs }">
            <v-btn
                    color="blue darken-1"
                    dark
                    v-bind="attrs"
                    v-on="on"
                    outlined
            >
                Items
            </v-btn>
        </template>
        <v-card>
            <v-card-title>
                <span class="headline">Items List</span>
            </v-card-title>
            <v-card-text>
                <v-card>
                    <v-card-title>Add Item to Order</v-card-title>
                    <v-card-text>
                        <v-form ref="form" v-model="formValid">
                            <v-autocomplete :items="productNames" @input="productSelected" v-model="selectedProduct" label="Search Product" dense></v-autocomplete>
                            <v-row>
                                <v-col>
                                    <v-text-field label="Quantity" type="number" min="1" v-model.number="editOrderItems.quantity" dense></v-text-field>
                                </v-col>
                                <v-col>
                                    <v-text-field label="Unit Price" type="float" v-model.number="editOrderItems.unitPrice" dense></v-text-field>
                                </v-col>
                            </v-row>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="resetForm">Reset</v-btn>
                        <v-btn text color="primary" @click="addItemToOrder">Submit</v-btn>
                    </v-card-actions>
                </v-card>
                <v-list two-line subheader>
                    <v-subheader inset>Order Items</v-subheader>

                    <v-list-item
                            v-for="product in order.products"
                            :key="product.id"
                    >
                        <v-list-item-content>
                            <v-list-item-title v-text="fetchProductName(product.orderItem.productId)"></v-list-item-title>
                            <v-list-item-subtitle>Quantity: {{ product.quantity}} | Unit Price: {{ product.unitPrice}} {{order.currency}}</v-list-item-subtitle>
                        </v-list-item-content>

                        <v-list-item-action>
                            <v-row>
                                <v-btn icon >
                                    <v-icon color="red" @click="clickedDelete(product)">mdi-delete</v-icon>
                                </v-btn>
                                <v-btn icon @click="clickedEdit(product)">
                                    <v-icon>mdi-pencil</v-icon>
                                </v-btn>
                            </v-row>
                        </v-list-item-action>
                    </v-list-item>
                </v-list>
            </v-card-text>
            <v-divider></v-divider>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        color="primary"
                        text
                        @click="dialog = false"
                >
                    I accept
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import NProgress from "nprogress";
    import OrdersApi from "../api/OrdersApi";
    import ProductsApi from "../api/ProductsApi";
    import OrderItemsApi from "../api/OrderItemsApi";

    export default {
        name: "OrderItemsDialog",
        props: {
            order: {}
        },
        data() {
            return {
                dialog: false,
                formValid: false,
                editOrderItems: {
                    orderItem: {
                        orderId: this.order.id,
                        productId: 0
                    },
                    order: '/api/orders/' + this.order.id,
                    product: '',
                    quantity: 0,
                    unitPrice: 0
                },
                defaultOrderItems: {
                    orderItem: {
                        orderId: this.order.id,
                        productId: 0
                    },
                    order: '/api/orders/' + this.order.id,
                    product: '',
                    quantity: 0,
                    unitPrice: 0
                },
                products: [],
                selectedProduct: ''
            }
        },
        created() {
            ProductsApi.getProducts().then(products => this.products = products)
        },
        computed: {
            productNames() {
                // let productIds = this.order.products.map(product => product.orderItem.productId)
                // let products = this.products.filter(product => productIds.indexOf(product.id) === -1)
                // return products.map(productName => productName.name);

                return this.products.map(product => product.name);
            }
        },
        methods: {
            resetForm() {
                this.editOrderItems = Object.assign({}, this.defaultOrderItems);
            },
            productSelected(event) {
                this.editOrderItems.orderItem.productId = this.products.find(p => p.name === event).id;
                this.editOrderItems.product = '/api/products/' + this.products.find(p => p.name === event).id;
            },
            addItemToOrder() {
                NProgress.start()
                const findMatch = this.order.products.find(product => product.orderItem.productId === this.editOrderItems.orderItem.productId);

                if (findMatch) {
                    // save edited item
                    OrderItemsApi.editOrderItem(this.editOrderItems).then(response => {
                        const matchProduct = this.order.products.find(product => product.orderItem.productId === response.data.orderItem.productId);
                        const indexProduct = this.order.products.indexOf(matchProduct);
                        this.editOrderItems = Object.assign({}, this.defaultOrderItems);
                        this.order.products[indexProduct] = response.data;
                        NProgress.done()
                    })
                } else {
                    OrderItemsApi.newOrderItem(this.editOrderItems).then(result => {
                        this.order.products.push(result.data)
                        this.editOrderItems = Object.assign({}, this.defaultOrderItems);
                        NProgress.done()
                    })
                }
            },
            fetchProductName(event) {
                let p = this.products.find(p => p.id === event)
                if (p) {
                    return p.name
                }
            },
            clickedDelete(event) {
                console.log(event)
                NProgress.start()
                OrdersApi.deleteOrderItem(event).then(() => {
                    this.order.products = this.order.products.filter(product => product.orderItem.productId !== event.orderItem.productId);
                    NProgress.done()
                })
            },
            clickedEdit(event) {
                this.editOrderItems = Object.assign({}, event);
                this.selectedProduct = this.fetchProductName(event.orderItem.productId);
            }
        }
    }
</script>

<style scoped>

</style>