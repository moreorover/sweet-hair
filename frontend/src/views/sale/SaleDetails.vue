<template>
    <SaleCard :sale="sale" :key="sale.id" @deleted="deleted"></SaleCard>
</template>

<script>
    import SaleCard from "../../components/SaleCard";
    export default {
        name: "SaleDetails",
        components: { SaleCard },
        props: {
            sale: {
                type: Object,
                required: true
            }
        },
        methods: {
            deleted() {
                this.$router.push({
                    name: "Sales"
                })
            }
        }
    }
</script>

<style scoped>

</style>