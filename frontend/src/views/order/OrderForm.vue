<template>
    <div>
    <v-toolbar>
        <v-toolbar-title>Orders</v-toolbar-title>
        <v-divider
                class="mx-4"
                inset
                vertical
        ></v-divider>
        <v-spacer></v-spacer>
        <v-btn
                color="primary"
                dark
                class="mx-4"
                link :to="{ name: 'Orders' }"
        >List Orders</v-btn>
    </v-toolbar>

    <v-card class="mx-auto ma-3">
        <v-card-title>
            <span class="headline">{{ title }}</span>
        </v-card-title>

        <v-card-text>
            <v-form ref="customerForm" v-model="formValid">
                <v-row>
                    <v-col cols="12" md="4">
                        <v-row justify="center">
                            <v-date-picker v-model="localOrder.purchasedAt" :rules="formRules.dateRules"></v-date-picker>
                        </v-row>
                    </v-col>
                    <v-col cols="12" md="8">
                        <v-select
                                :items="currencies"
                                label="Currency"
                                v-model="localOrder.currency"
                                :rules="formRules.currencyRules"
                        ></v-select>
                        <v-select
                                :items="suppliersSorted"
                                label="Supplier"
                                item-value="value"
                                v-model="localOrder.supplier"
                                :rules="formRules.supplierRules"
                        ></v-select>
                        <v-container v-for="product in localOrder.products" :key="product.id">
                            <v-autocomplete
                                    :items="productsSorted"
                                    label="Pick Product"
                                    item-value="value"
                                    v-model="product.product"
                                    :rules="formRules.productRules"
                            ></v-autocomplete>
                            <v-row>
                                <v-col>
                                    <v-text-field label="Quantity" type="number" min="1" v-model.number="product.quantity" :rules="formRules.numberRules" dense></v-text-field>
                                </v-col>
                                <v-col>
                                    <v-text-field label="Unit Price" type="float" v-model.number="product.unitPrice" :rules="formRules.numberRules" dense></v-text-field>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-chip
                                        class="ma-2"
                                        color="secondary"
                                >Total: {{ product.total }} {{ localOrder.currency }}</v-chip>
                                <v-spacer></v-spacer>
                                <v-btn @click="removeProduct(product.product.id)">Remove</v-btn>
                            </v-row>
                        </v-container>
                        <v-card @click="addProduct" color="indigo lighten-5" class="mx-auto ma-3" :disabled="!formValid">
                            <v-card-title class="justify-center">Add Product</v-card-title>
                        </v-card>
                        <v-container d-flex class="mx-auto ma-3 justify-space-around">
                                <v-chip color="secondary"
                                >Product Count: {{ localOrder.itemsCount }}</v-chip>
                                <v-chip color="secondary"
                                >Total: {{ localOrder.total }} {{ localOrder.currency }}</v-chip>
                        </v-container>
                    </v-col>
                </v-row>
            </v-form>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="save" :disabled="!formValid || formInputsChanged">Save</v-btn>
        </v-card-actions>
    </v-card>
    </div>
</template>

<script>
    import _ from 'lodash';
    import OrdersApi from "../../api/OrdersApi";
    import NProgress from "nprogress";

    export default {
        name: "OrderForm",
        props: {
            order: {
                type: Object,
                required: false
            },
            suppliers: {
                type: Array,
                required: true
            },
            products: {
                type: Array,
                required: true
            }
        },
        data() {
            return {
                formValid: false,
                currencies: ['GBP', 'EUR', 'USD'],
                formRules: {
                    dateRules: [
                        v => !!v || 'Date is required'
                    ],
                    currencyRules: [
                        v => !!v || 'Currency is required',
                    ],
                    supplierRules: [
                        v => !!v || 'Supplier is required',
                        v => v.id > 0 || 'Supplier id invalid'
                    ],
                    productRules: [
                        v => !!v || 'Product is required',
                        v => v.id > 0 || 'Product id invalid'
                    ],
                    numberRules: [
                        v => !!v || 'Field is required',
                        v => v > 0 || 'Must be more that 0'
                    ]

                },
                localOrder: null,
                defaultOrder: {
                    purchasedAt: new Date().toISOString().substr(0, 10),
                    total: 0.0,
                    itemsCount: 0,
                    currency: '',
                    supplier: {
                        id: 0,
                        name: '',
                        url: '',
                        logo: ''
                    },
                    products: []
                },
                defaultProduct: {
                    quantity: null,
                    unitPrice: null,
                    total: 0,
                    product: {
                        id: null,
                        name: ''
                    }
                }
            }
        },
        created() {
            if (this.order) {
                this.localOrder = { ...this.order }
            } else {
                this.localOrder = { ...this.defaultOrder }
            }
        },
        watch: {
            'localOrder.products': {
                handler: function(arrayUpdated) {
                    this.localOrder.total = 0
                    this.localOrder.itemsCount = 0
                    arrayUpdated.forEach(product => {
                        const total = product.quantity * product.unitPrice
                        product.total = _.round(total, 2)
                        this.localOrder.total += _.round(total, 2)
                        this.localOrder.itemsCount += product.quantity

                    })
                },
                deep: true
            }
        },
        computed: {
            title() {
                if (this.order) {
                    return 'Edit Order'
                } else {
                    return 'New Order'
                }
            },
            formInputsChanged() {
                return JSON.stringify(this.order) === JSON.stringify(this.localOrder)
            },
            suppliersSorted() {
                let sa = []
                this.suppliers.forEach(supplier => sa.push({ text: supplier.name, value: supplier }))
                return _.orderBy(sa, 'text')
            },
            productsSorted() {
                let pa = []
                this.products.forEach(product => pa.push({ text: product.name, value: product }))
                return _.orderBy(pa, 'text')
            }
        },
        methods: {
            save() {
                NProgress.start()
                if (this.localOrder.id){
                    OrdersApi
                        .update(this.localOrder)
                        .then(result => {
                            this.$router.push({
                                name: "Order Details",
                                params: {id: result.id}
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                } else {
                    OrdersApi
                        .create(this.localOrder)
                        .then(result => {
                            this.$router.push({
                                name: "Order Details",
                                params: { id: result.id }
                            })
                        })
                        .catch(error => {
                            console.log(error)
                            NProgress.done()
                        })
                }
            },
            addProduct() {
                this.localOrder.products.push({ ...this.defaultProduct })
            },
            removeProduct(event) {
                this.localOrder.products = this.localOrder.products.filter(product => product.product.id !== event)
            }
        }
    }
</script>

<style scoped>

</style>