<template>
    <OrderCard :order="order" :key="order.id" @deleted="deleted"></OrderCard>
</template>

<script>
    import OrderCard from "../../components/OrderCard";
    export default {
        name: "OrderDetails",
        components: { OrderCard },
        props: {
            order: {
                type: Object,
                required: true
            }
        },
        methods: {
            deleted() {
                this.$router.push({
                    name: "Orders"
                })
            }
        }
    }
</script>

<style scoped>

</style>