<template>
    <v-card class="mx-auto ma-3">
        <v-card-title>
            {{ product.name }}
        </v-card-title>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
            <v-btn icon link :to="{ name: 'Edit Product', params: { id: product.id } }">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import ProductsApi from "../api/ProductsApi";

    export default {
        name: "ProductCard",
        props: {
            product: {
                id: null,
                name: ""
            }
        },
        methods: {
            clickedDelete() {
                confirm('Are you sure you want to delete this product?') &&
                ProductsApi
                    .delete(this.product)
                    .then(() => {
                        this.$emit('deleted', this.product);
                    })
                    .catch(error => { console.log(error)})
            }
        }
    }
</script>

<style scoped>

</style>