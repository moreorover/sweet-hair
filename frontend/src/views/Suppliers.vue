<template>
    <div>
        <h1 v-for="supplier in suppliers" :key="supplier.id">{{ supplier.name }}</h1>
    </div>
</template>

<script>
    import Suppliers from "../api/Suppliers";
    export default {
        name: "Suppliers",
        data() {
          return {
              suppliers: []
          }
        },
        created() {
            Suppliers.getSuppliers()
            .then(suppliers => {
                console.log(suppliers["_embedded"].suppliers);
                this.suppliers = suppliers["_embedded"].suppliers;
            })
        }
    }
</script>

<style scoped>

</style>