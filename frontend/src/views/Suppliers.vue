<template>
    <div>
        <Row v-for="supplier in suppliers" :key="supplier.id" :data="supplier" @edit="eventEdit" @delete="eventDelete">{{ supplier.name }}</Row>
    </div>
</template>

<script>
    import Suppliers from "../api/Suppliers";
    import Row from "../components/Row";
    export default {
        name: "Suppliers",
        components: {Row},
        comments: {
            Row
        },
        data() {
          return {
              suppliers: []
          }
        },
        created() {
            Suppliers.getSuppliers()
            .then(suppliers => {
                this.suppliers = suppliers["_embedded"].suppliers;
            })
        },
        methods: {
            eventEdit(event) {
                console.log(event)
            },
            eventDelete(event) {
                console.log(event)
            }
        }
    }
</script>

<style scoped>

</style>