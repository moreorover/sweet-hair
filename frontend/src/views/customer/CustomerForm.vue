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
        <v-btn
                color="primary"
                dark
                class="mx-4"
                link :to="{ name: 'Customers' }"
        >List Customers</v-btn>
    </v-toolbar>

    <v-card class="mx-auto ma-3">
        <v-card-title>
            <span class="headline">{{ title }}</span>
        </v-card-title>

        <v-card-text>
            <v-form ref="customerForm" v-model="formValid">
                <v-text-field v-model="localCustomer.name" label="Customer name" :rules="formRules.nameRules" required></v-text-field>
            </v-form>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="save" :disabled="!formValid || formInputsChanged">Save</v-btn>
        </v-card-actions>
    </v-card>
    </div>
</template>

<script>
    import CustomersApi from "../../api/CustomersApi";
    import NProgress from "nprogress";

    export default {
        name: "CustomerForm",
        props: {
            customer: {
                type: Object,
                required: false
            }
        },
        data() {
            return {
                formValid: false,
                formRules: {
                    nameRules: [
                        v => !!v || 'Name is required',
                        v => (v && v.length >= 5) || 'Input must be greater than 5 characters',
                    ]
                },
                localCustomer: { ...this.customer }
            }
        },
        computed: {
            title() {
                if (this.customer) {
                    return 'Edit Customer'
                } else {
                    return 'New Customer'
                }
            },
            formInputsChanged() {
                return JSON.stringify(this.customer) === JSON.stringify(this.localCustomer)
            }
        },
        methods: {
            save() {
                NProgress.start()
                if (this.localCustomer.id){
                    CustomersApi
                        .update(this.localCustomer)
                        .then(result => {
                            this.$router.push({
                                name: "Customer Details",
                                params: {id: result.id}
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                } else {
                    CustomersApi
                        .create(this.localCustomer)
                        .then(result => {
                            this.$router.push({
                                name: "Customer Details",
                                params: { id: result.id }
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                }
            }
        }
    }
</script>

<style scoped>

</style>