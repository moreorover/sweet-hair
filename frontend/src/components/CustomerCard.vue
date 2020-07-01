<template>
    <v-card class="ma-3">
        <v-card-title>
            {{ customer.name }}
        </v-card-title>
        <v-card-subtitle v-if="customer.sales.length > 0">
            Total sales: {{ customer.sales.length }}
        </v-card-subtitle>
        <v-card-text v-if="customer.sales.length > 0">
            <v-list dense>
                <v-list-item v-for="sale in customer.sales" :key="sale.id">
                    <v-list-item-content>
                        <v-list-item-title v-text="sale.soldAt"></v-list-item-title>
                        <v-list-item-subtitle v-text="'Total: ' + sale.total + sale.currency">></v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
                <v-btn icon link :to="{ name: 'Edit Customer', params: { id: customer.id } }">
                    <v-icon>mdi-pencil</v-icon>
                </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import CustomersApi from "../api/CustomersApi";

    export default {
        name: "CustomerCard",
        props: {
            customer: {
                id: null,
                name: ""
            }
        },
        methods: {
            clickedDelete() {
                confirm('Are you sure you want to delete this customer?') &&
                CustomersApi
                    .delete(this.customer)
                    .then(() => {
                        this.$emit('deleted', this.customer);
                    })
                    .catch(error => { console.log(error)})
            }
        }
    }
</script>

<style scoped>

</style>