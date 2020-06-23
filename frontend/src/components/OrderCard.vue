<template>
    <v-card class="mx-auto ma-3">
        <v-list-item four-line>
            <v-list-item-content>
                <div class="overline mb-4">{{ order.purchasedAt }}</div>
                <v-list-item-title class="headline mb-1">From: {{ order.supplier.name }}</v-list-item-title>
                <v-list-item-subtitle>Total: {{order.total}} {{ order.currency }}</v-list-item-subtitle>
                <v-list-item-subtitle>Item count: {{ order.itemsCount }}</v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-card-actions>
            <v-spacer></v-spacer>
            <slot></slot>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
            <v-btn icon link :to="{ name: 'Edit Order', params: { id: order.id } }">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import OrderApi from "../api/OrdersApi";

    export default {
        name: "OrderCard",
        props: {
            order: {
                id: 0,
                purchasedAt: '',
                total: 0.0,
                itemsCount: 0,
                currency: '',
                supplier: {
                    id: 1,
                    name: '',
                    url: '',
                    logo: ''
                },
                products: []
            }
        },
        methods: {
            clickedDelete() {
                confirm('Are you sure you want to delete this order?') &&
                OrderApi
                    .delete(this.order)
                    .then(() => {
                        this.$emit('deleted', this.order);
                    })
                    .catch(error => { console.log(error)})
            }
        }
    }
</script>

<style scoped>

</style>