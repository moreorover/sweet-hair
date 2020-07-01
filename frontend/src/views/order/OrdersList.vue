<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Order</v-toolbar-title>
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
                    link :to="{ name: 'Create Order' }"
            >New Order</v-btn>
        </v-toolbar>
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="order in ordersSorted" :key="order.id" :order="order">
                <OrderCard :key="order.id" :order="order" @deleted="reloadPage"></OrderCard>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import _ from 'lodash';
    import OrderCard from "../../components/OrderCard";
    export default {
        name: "OrderList",
        components: { OrderCard },
        props: {
            orders: {
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
            ordersSorted() {
                return _.orderBy(this.orders, 'purchasedAt', 'desc')
            }
        }
    }
</script>

<style scoped>

</style>