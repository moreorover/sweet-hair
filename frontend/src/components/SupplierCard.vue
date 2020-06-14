<template>
    <v-card class="mx-auto ma-3">
        <v-card-title>
            {{ supplier.name }}
        </v-card-title>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
            <v-btn icon @click="clickedEdit">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon :href="supplier.url" target="_blank">
                <v-icon>mdi-open-in-new</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "SupplierCard",
        props: {
            supplier: {
                id: null,
                name: "",
                url: "",
                logo: ""
            }
        },
        methods: {
            clickedEdit() {
                this.$emit('edit', this.supplier)
            },
            clickedDelete() {
                confirm('Are you sure you want to delete this supplier?') && this.$emit('delete', this.supplier)
            }
        }
    }
</script>

<style scoped>

</style>