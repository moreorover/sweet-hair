<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Sales</v-toolbar-title>
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
            >List Sales
            </v-btn>
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
                                <v-date-picker v-model="localSale.soldAt"
                                               :rules="formRules.dateRules"></v-date-picker>
                            </v-row>
                        </v-col>
                        <v-col cols="12" md="8">
                            <v-select
                                    :items="currencies"
                                    label="Currency"
                                    v-model="localSale.currency"
                                    :rules="formRules.currencyRules"
                            ></v-select>
                            <v-autocomplete
                                    :items="customersSorted"
                                    label="Customer"
                                    item-value="value"
                                    v-model="localSale.customer"
                                    :rules="formRules.customerRules"
                            ></v-autocomplete>
                            <v-card v-for="product in localSale.products" :key="product.id" class="mx-auto ma-3">
                                <v-card-text>
                                    <v-autocomplete
                                            :items="productsSorted"
                                            label="Pick Product"
                                            item-value="value"
                                            v-model="product.product"
                                            :rules="formRules.productRules"
                                    ></v-autocomplete>
                                    <v-row>
                                        <v-col>
                                            <v-text-field label="Quantity" type="number" min="1"
                                                          v-model.number="product.quantity"
                                                          :rules="formRules.numberRules" dense></v-text-field>
                                        </v-col>
                                        <v-col>
                                            <v-text-field label="Unit Price" type="float"
                                                          v-model.number="product.unitPrice"
                                                          :rules="formRules.numberRules" dense></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-card-text>
                                <v-card-actions>
                                    <v-chip
                                            class="ma-2"
                                            color="secondary"
                                    >Total: {{ product.total }} {{ localSale.currency }}
                                    </v-chip>
                                    <v-spacer></v-spacer>
                                    <v-btn @click="removeProduct(product.product.id)">Remove</v-btn>
                                </v-card-actions>
                            </v-card>
                            <v-card @click="addProduct" color="indigo lighten-5" class="mx-auto ma-3"
                                    :disabled="!formValid">
                                <v-card-title class="justify-center">Add Product</v-card-title>
                            </v-card>
                            <v-card d-flex class="mx-auto ma-3">
                                <v-card-title>
                                    <v-spacer></v-spacer>
                                    <v-chip color="secondary"
                                    >Product Count: {{ localSale.itemsCount }}
                                    </v-chip>
                                    <v-spacer></v-spacer>
                                    <v-chip color="secondary"
                                    >Total: {{ localSale.total }} {{ localSale.currency }}
                                    </v-chip>
                                    <v-spacer></v-spacer>
                                </v-card-title>
                            </v-card>
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
    import SalesApi from "../../api/SalesApi";
    import NProgress from "nprogress";

    export default {
        name: "SaleForm",
        props: {
            sale: {
                type: Object,
                required: false
            },
            customers: {
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
                    customerRules: [
                        v => !!v || 'Customer is required',
                        v => v.id > 0 || 'Customer id invalid'
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
                localSale: null,
                defaultSale: {
                    soldAt: new Date().toISOString().substr(0, 10),
                    total: 0.0,
                    itemsCount: 0,
                    currency: '',
                    customer: {
                        id: 0,
                        name: ''
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
            if (this.sale) {
                this.localSale = {...this.sale}
            } else {
                this.localSale = {...this.defaultSale}
            }
        },
        watch: {
            'localSale.products': {
                handler: function (arrayUpdated) {
                    this.localSale.total = 0
                    this.localSale.itemsCount = 0
                    arrayUpdated.forEach(product => {
                        const total = product.quantity * product.unitPrice
                        product.total = _.round(total, 2)
                        this.localSale.total += _.round(total, 2)
                        this.localSale.itemsCount += product.quantity

                    })
                },
                deep: true
            }
        },
        computed: {
            title() {
                if (this.sale) {
                    return 'Edit Sale'
                } else {
                    return 'New Sale'
                }
            },
            formInputsChanged() {
                return JSON.stringify(this.sale) === JSON.stringify(this.localSale)
            },
            customersSorted() {
                let ca = []
                this.customers.forEach(customer => ca.push({text: customer.name, value: customer}))
                return _.orderBy(ca, 'text')
            },
            productsSorted() {
                let pa = []
                this.products.forEach(product => pa.push({text: product.name, value: product}))
                return _.orderBy(pa, 'text')
            }
        },
        methods: {
            save() {
                NProgress.start()
                if (this.localSale.id) {
                    SalesApi
                        .update(this.localSale)
                        .then(result => {
                            this.$router.push({
                                name: "Sale Details",
                                params: {id: result.id}
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                } else {
                    SalesApi
                        .create(this.localSale)
                        .then(result => {
                            this.$router.push({
                                name: "Sale Details",
                                params: {id: result.id}
                            })
                        })
                        .catch(error => {
                            console.log(error)
                            NProgress.done()
                        })
                }
            },
            addProduct() {
                this.localSale.products.push({...this.defaultProduct})
            },
            removeProduct(event) {
                this.localSale.products = this.localSale.products.filter(product => product.product.id !== event)
            }
        }
    }
</script>

<style scoped>

</style>