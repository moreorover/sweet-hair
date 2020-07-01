<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Sale</v-toolbar-title>
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
                    link :to="{ name: 'Create Sale' }"
            >New Sale</v-btn>
        </v-toolbar>
        <v-layout row wrap>
        <v-flex xs12 sm6 md4 lg3 v-for="sale in salesSorted" :key="sale.id" :sale="sale">
            <SaleCard :key="sale.id" :sale="sale" @deleted="reloadPage"></SaleCard>
        </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import _ from 'lodash';
    import SaleCard from "../../components/SaleCard";
    export default {
        name: "OrderList",
        components: { SaleCard },
        props: {
            sales: {
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
            salesSorted() {
                return _.orderBy(this.sales, 'soldAt', 'desc')
            }
        }
    }
</script>

<style scoped>

</style>