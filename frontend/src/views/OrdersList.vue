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
            <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                            color="primary"
                            dark
                            class="mb-2"
                            v-bind="attrs"
                            v-on="on"
                    >New Order</v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="headline">Order Control</span>
                    </v-card-title>

                    <v-card-text>
                        <v-form ref="form" v-model="formValid">
                            <v-dialog
                                    ref="dialog"
                                    v-model="dateDialog"
                                    :return-value.sync="editOrder.purchasedAt"
                                    persistent
                                    width="290px"
                            >
                                <template v-slot:activator="{ on, attrs }">
                                    <v-text-field
                                            v-model="editOrder.purchasedAt"
                                            label="Order date"
                                            prepend-icon="mdi-calendar"
                                            v-bind="attrs"
                                            v-on="on"
                                            readonly
                                            :rules="formRules.dateRule"
                                    ></v-text-field>
                                </template>
                                <v-date-picker v-model="editOrder.purchasedAt" scrollable>
                                    <v-spacer></v-spacer>
                                    <v-btn text color="primary" @click="dateDialog = false">Cancel</v-btn>
                                    <v-btn text color="primary" @click="$refs.dialog.save(editOrder.purchasedAt)">OK</v-btn>
                                </v-date-picker>
                            </v-dialog>
                            <v-select
                                    :items="supplierNames"
                                    label="Supplier"
                                    @input="supplierSelected"
                                    :rules="formRules.dateRule"
                                    :value="editOrder.supplierName"
                            ></v-select>
                            <v-select
                                    :items="currencies"
                                    label="Currency"
                                    v-model="editOrder.currency"
                                    :rules="formRules.dateRule"
                            ></v-select>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                        <v-btn color="blue darken-1" text @click="eventSave" :disabled="!formValid">Save</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-toolbar>
        <OrderCard v-for="order in orders" :key="order.id" :order="order" @edit="eventEdit" @delete="eventDelete"></OrderCard>
    </div>
</template>

<script>
    import OrderCard from "../components/OrderCard";
    import OrdersApi from "../api/OrdersApi";
    import SuppliersApi from "../api/SuppliersApi";
    export default {
        name: "OrdersList",
        components: { OrderCard },
        data() {
          return {
              dialog: false,
              dateDialog: false,
              formValid: false,
              orders: [],
              suppliers: [],
              currencies: ['GBP', 'EUR', 'USD'],
              editOrder: {
                  id: null,
                  purchasedAt: new Date().toISOString().substr(0, 10),
                  supplier: null,
                  total: 0,
                  itemsCount: 0,
                  currency: '',
                  supplierName: ''
              },
              defaultOrder: {
                  purchasedAt: new Date().toISOString().substr(0, 10),
                  supplier: null,
                  total: 0,
                  itemsCount: 0,
                  currency: '',
                  supplierName: ''
              },
              formRules: {
                  dateRule: [
                      v => !!v || 'Date is required'
                  ]
              }
          }
        },
        watch: {
            dialog(val) {
                val || this.close()
            }
        },
        created() {
            OrdersApi.getOrders()
            .then(orders => {
                this.orders = orders["_embedded"].orders;
            })
            SuppliersApi.getSuppliers()
                .then(suppliers => {
                    this.suppliers = suppliers["_embedded"].suppliers;
                })
        },
        computed: {
            supplierNames() {
                return this.suppliers.map(supplier => supplier.name);
            }
        },
        methods: {
            eventEdit(event) {
                this.editOrder = event
                this.dialog = true
            },
            eventDelete(event) {
                OrdersApi.deleteOrder(event).then(response => { console.log(response)})
            },
            eventSave() {
                if (this.editOrder.id === null) {
                    console.log("Saving new Order")
                    OrdersApi.newOrder(this.editOrder)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                    return
                }
                if (this.editOrder.id > 0) {
                    console.log("saving edited Order")
                    OrdersApi.editOrder(this.editOrder)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                }
            },
            close () {
                this.dialog = false
                this.$nextTick(() => {
                    this.editOrder = Object.assign({}, this.defaultOrder)
                })
            },
            supplierSelected(event) {
                const supplierObject = this.suppliers.find(supplier => supplier.name === event)
                this.editOrder.supplier = "api/suppliers/" + supplierObject.id
                this.editOrder.supplierName = supplierObject.name
            }
        }
    }
</script>

<style scoped>

</style>