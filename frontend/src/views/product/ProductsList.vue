<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Products</v-toolbar-title>
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
                    link :to="{ name: 'Create Product' }"
            >New Product</v-btn>
        </v-toolbar>
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="product in productsSorted" :key="product.id" :product="product">
                <ProductCard :key="product.id" :product="product" @deleted="reloadPage"></ProductCard>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import _ from 'lodash';
    import ProductCard from "../../components/ProductCard";
    export default {
        name: "SuppliersList",
        components: { ProductCard },
        props: {
            products: {
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
            productsSorted() {
                return _.orderBy(this.products, 'name')
            }
        }
    }
</script>

<style scoped>

</style>