<template>
    <CustomerCard :customer="customer" :key="customer.id" @deleted="deleted"></CustomerCard>
</template>

<script>
    import CustomerCard from "../../components/CustomerCard";
    export default {
        name: "CustomerDetails",
        components: {CustomerCard},
        props: {
            customer: {
                type: Object,
                required: true
            }
        },
        methods: {
            deleted() {
                this.$router.push({
                    name: "Customers"
                })
            }
        }
    }
</script>

<style scoped>

</style>