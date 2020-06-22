<template>
    <v-card class="mx-auto ma-3">
        <v-card-title>
            {{ customer.name }}
        </v-card-title>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
                <v-btn icon link :to="{ name: 'Edit Customer', params: { id: customer.id } }">
                    <v-icon>mdi-pencil</v-icon>
                </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import CustomersApi from "../api/CustomersApi";

    export default {
        name: "CustomerCard",
        props: {
            customer: {
                id: null,
                name: ""
            }
        },
        methods: {
            clickedDelete() {
                confirm('Are you sure you want to delete this customer?') &&
                CustomersApi
                    .delete(this.customer)
                    .then(() => {
                        this.$emit('deleted', this.customer);
                    })
                    .catch(error => { console.log(error)})
            }
        }
    }
</script>

<style scoped>

</style>