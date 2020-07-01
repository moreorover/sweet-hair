<template>
    <v-card class="ma-3">
        <v-card-title>
            {{ supplier.name }}
            <v-btn icon :href="supplier.url" target="_blank">
                <v-icon>mdi-open-in-new</v-icon>
            </v-btn>
        </v-card-title>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
            <v-btn icon link :to="{ name: 'Edit Supplier', params: { id: supplier.id } }">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import SuppliersApi from "../api/SuppliersApi";

    export default {
        name: "SupplierCard",
        props: {
            supplier: {
                id: null,
                name: "",
                url: '',
                logo: ''
            }
        },
        methods: {
            clickedDelete() {
                confirm('Are you sure you want to delete this supplier?') &&
                SuppliersApi
                    .delete(this.supplier)
                    .then(() => {
                        this.$emit('deleted', this.supplier);
                    })
                    .catch(error => { console.log(error)})
            }
        }
    }
</script>

<style scoped>

</style>