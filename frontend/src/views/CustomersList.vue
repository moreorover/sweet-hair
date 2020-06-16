<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Customers</v-toolbar-title>
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
                    >New Customer</v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="headline">Customer Control</span>
                    </v-card-title>

                    <v-card-text>
                        <v-form ref="form" v-model="formValid">
                            <v-text-field v-model="editCustomer.name" label="Customer name" :rules="formRules.nameRules" required></v-text-field>
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
        <CustomerCard v-for="customer in customers" :key="customer.id" :customer="customer" @edit="eventEdit" @delete="eventDelete"></CustomerCard>
    </div>
</template>

<script>
    import CustomerCard from "../components/CustomerCard";
    import CustomersApi from "../api/CustomersApi";
    export default {
        name: "CustomersList",
        components: { CustomerCard },
        data() {
          return {
              dialog: false,
              formValid: false,
              customers: [],
              editCustomer: {
                  id: null,
                  name: ''
              },
              defaultCustomer: {
                  name: ''
              },
              formRules: {
                  nameRules: [
                      v => !!v || 'Name is required',
                      v => (v && v.length >= 5) || 'Input must be greater than 5 characters',
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
            CustomersApi.getCustomers()
            .then(customers => {
                this.customers = customers["_embedded"].customers;
            })
        },
        methods: {
            eventEdit(event) {
                this.editCustomer = event
                this.dialog = true
            },
            eventDelete(event) {
                CustomersApi.deleteCustomer(event).then(response => { console.log(response)})
            },
            eventSave() {
                if (this.editCustomer.id === null) {
                    console.log("Saving new Supplier")
                    CustomersApi.newCustomer(this.editCustomer)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                    return
                }
                if (this.editCustomer.id > 0) {
                    console.log("saving edited Supplier")
                    CustomersApi.editCustomer(this.editSupplier)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                }
            },
            close () {
                this.dialog = false
                this.$nextTick(() => {
                    this.editCustomer = Object.assign({}, this.defaultCustomer)
                })
            }
        }
    }
</script>

<style scoped>

</style>