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
            <v-btn icon @click="clickedEdit">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "ProductCard",
        props: {
            product: {
                id: null,
                name: ""
            }
        },
        methods: {
            clickedEdit() {
                this.$emit('edit', this.product)
            },
            clickedDelete() {
                confirm('Are you sure you want to delete this product?') && this.$emit('delete', this.product)
            }
        }
    }
</script>

<style scoped>

</style>