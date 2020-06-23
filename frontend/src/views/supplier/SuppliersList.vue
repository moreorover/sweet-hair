<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Suppliers</v-toolbar-title>
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
                    link :to="{ name: 'Create Supplier' }"
            >New Supplier</v-btn>
        </v-toolbar>
        <SupplierCard v-for="supplier in suppliersSorted" :key="supplier.id" :supplier="supplier" @deleted="reloadPage"></SupplierCard>
    </div>
</template>

<script>
    import _ from 'lodash';
    import SupplierCard from "../../components/SupplierCard";
    export default {
        name: "SuppliersList",
        components: { SupplierCard },
        props: {
            suppliers: {
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
            suppliersSorted() {
                return _.orderBy(this.suppliers, 'name')
            }
        }
    }
</script>

<style scoped>

</style>