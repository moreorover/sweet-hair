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
                    class="mb-2"
                    link :to="{ name: 'Create Customer' }"
            >New Customer</v-btn>
        </v-toolbar>
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="customer in customersSorted" :key="customer.id" :customer="customer">
                <CustomerCard :key="customer.id" :customer="customer" @deleted="reloadPage"></CustomerCard>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import _ from 'lodash';
    import CustomerCard from "../../components/CustomerCard";
    export default {
        name: "CustomersList",
        components: { CustomerCard },
        props: {
            customers: {
                type: Array,
                required: true
            }
        },
        methods: {
            reloadPage() {
                this.$router.go(0)
            }
        },
        computed: {
            customersSorted() {
                return _.orderBy(this.customers, 'name')
            }
        }
    }
</script>

<style scoped>

</style>