<template>
    <v-card class="mx-auto ma-3">
        <v-card-title>
            {{ order }}
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
        name: "SupplierCard",
        props: {
            order: {
                id: null,
                name: "",
                url: "",
                logo: ""
            }
        },
        methods: {
            clickedEdit() {
                this.$emit('edit', this.order)
            },
            clickedDelete() {
                confirm('Are you sure you want to delete this item?') && this.$emit('delete', this.order)
            }
        }
    }
</script>

<style scoped>

</style>