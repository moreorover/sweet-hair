<template>
    <CustomerCard :customer="customer" :key="customer.id"></CustomerCard>
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
        }
    }
</script>

<style scoped>

</style>