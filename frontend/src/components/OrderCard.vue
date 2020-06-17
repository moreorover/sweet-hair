<template>
    <v-card class="mx-auto ma-3">
        <v-list-item four-line>
            <v-list-item-content>
                <div class="overline mb-4">{{ order.purchasedAt }}</div>
                <v-list-item-title class="headline mb-1">From: {{ order.supplierName }}</v-list-item-title>
                <v-list-item-subtitle>Total: {{order.total}} {{ order.currency }}</v-list-item-subtitle>
                <v-list-item-subtitle>Item count: {{ order.itemsCount }}</v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-card-actions>
            <v-spacer></v-spacer>
            <slot></slot>
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