<template>
    <v-card class="mx-auto ma-3">
        <v-list-item four-line>
            <v-list-item-content>
                <div class="overline mb-4">{{ sale.soldAt }}</div>
                <v-list-item-title class="headline mb-1">From: {{ sale.customer.name }}</v-list-item-title>
                <v-list-item-subtitle>Total: {{sale.total | twoDecimal}} {{ sale.currency }}</v-list-item-subtitle>
                <v-list-item-subtitle>Item count: {{ sale.itemsCount }}</v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-card-actions>
            <v-spacer></v-spacer>
            <slot></slot>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
            <v-btn icon link :to="{ name: 'Edit Sale', params: { id: sale.id } }">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import _ from "lodash"
    import SaleApi from "../api/SalesApi";

    export default {
        name: "SaleCard",
        props: {
            sale: {
                id: 0,
                soldAt: '',
                total: 0.0,
                itemsCount: 0,
                currency: '',
                customer: {
                    id: null,
                    name: ''
                },
                products: []
            }
        },
        methods: {
            clickedDelete() {
                confirm('Are you sure you want to delete this sale?') &&
                SaleApi
                    .delete(this.sale)
                    .then(() => {
                        this.$emit('deleted', this.sale);
                    })
                    .catch(error => { console.log(error)})
            }
        },
        filters: {
            twoDecimal(value) {
                return _.round(value, 2)
            }
        }
    }
</script>

<style scoped>

</style>