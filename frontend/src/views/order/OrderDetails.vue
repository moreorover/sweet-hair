<template>
    <SupplierCard :supplier="supplier" :key="supplier.id" @deleted="deleted"></SupplierCard>
</template>

<script>
    import SupplierCard from "../../components/SupplierCard";
    export default {
        name: "SupplierDetails",
        components: { SupplierCard },
        props: {
            supplier: {
                type: Object,
                required: true
            }
        },
        methods: {
            deleted() {
                this.$router.push({
                    name: "Suppliers"
                })
            }
        }
    }
</script>

<style scoped>

</style>