<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <template v-slot:activator="{ on, attrs }">
            <v-btn
                    color="blue darken-1"
                    dark
                    v-bind="attrs"
                    v-on="on"
                    outlined
            >
                Items
            </v-btn>
        </template>

        <v-card>
            <v-card-title>
                <span class="headline">Items List</span>
            </v-card-title>
                <v-card>
                    <v-card-title>Add Item to Order</v-card-title>
                    <v-card-text>
                        <v-form ref="form" v-model="formValid">
                            <v-text-field label="Search Product" v-model="editOrderItems.product" dense></v-text-field>
                            <v-row>
                                <v-col>
                                    <v-text-field label="Quantity" v-model="editOrderItems.quantity" dense></v-text-field>
                                </v-col>
                                <v-col>
                                    <v-text-field label="Unit Price" v-model="editOrderItems.unitPrice" dense></v-text-field>
                                </v-col>
                            </v-row>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn text color="primary">Reset</v-btn>
                        <v-btn text color="primary">Add</v-btn>
                    </v-card-actions>
                </v-card>
            <v-card-text>

            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        color="primary"
                        text
                        @click="dialog = false"
                >
                    I accept
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: "OrderItemsDialog",
        props: {
            order: {},
            supplier: {}
        },
        data() {
            return {
                dialog: false,
                formValid: false,
                orderItems: [],
                editOrderItems: {
                    order: '',
                    product: '',
                    quantity: 0,
                    unitPrice: 0
                },
                defaultOrderItems: {
                    order: '',
                    product: '',
                    quantity: 0,
                    unitPrice: 0
                }
            }
        }
    }
</script>

<style scoped>

</style>