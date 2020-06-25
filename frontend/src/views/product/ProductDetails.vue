<template>
    <ProductCard :product="product" :key="product.id" @deleted="deleted"></ProductCard>
</template>

<script>
    import ProductCard from "../../components/ProductCard";
    export default {
        name: "SupplierDetails",
        components: { ProductCard },
        props: {
            product: {
                type: Object,
                required: true
            }
        },
        methods: {
            deleted() {
                this.$router.push({
                    name: "Products"
                })
            }
        }
    }
</script>

<style scoped>

</style>