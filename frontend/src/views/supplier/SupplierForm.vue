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
                class="mx-4"
                link :to="{ name: 'Suppliers' }"
        >List Suppliers</v-btn>
    </v-toolbar>

    <v-card class="mx-auto ma-3">
        <v-card-title>
            <span class="headline">{{ title }}</span>
        </v-card-title>

        <v-card-text>
            <v-form ref="customerForm" v-model="formValid">
                <v-text-field v-model="localSupplier.name" label="Supplier Name" :rules="formRules.nameRules" required></v-text-field>
                <v-text-field v-model="localSupplier.url" label="Supplier URL" :rules="formRules.nameRules" required></v-text-field>
                <v-text-field v-model="localSupplier.logo" label="Supplier Logo URL"></v-text-field>
            </v-form>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="save" :disabled="!formValid || formInputsChanged">Save</v-btn>
        </v-card-actions>
    </v-card>
    </div>
</template>

<script>
    import SuppliersApi from "../../api/SuppliersApi";
    import NProgress from "nprogress";

    export default {
        name: "SupplierForm",
        props: {
            supplier: {
                type: Object,
                required: false
            }
        },
        data() {
            return {
                formValid: false,
                formRules: {
                    nameRules: [
                        v => !!v || 'Name is required',
                        v => (v && v.length >= 5) || 'Input must be greater than 5 characters',
                    ]
                },
                localSupplier: { ...this.supplier }
            }
        },
        computed: {
            title() {
                if (this.customer) {
                    return 'Edit Supplier'
                } else {
                    return 'New Supplier'
                }
            },
            formInputsChanged() {
                return JSON.stringify(this.supplier) === JSON.stringify(this.localSupplier)
            }
        },
        methods: {
            save() {
                NProgress.start()
                if (this.localSupplier.id){
                    SuppliersApi
                        .update(this.localSupplier)
                        .then(result => {
                            this.$router.push({
                                name: "Supplier Details",
                                params: {id: result.id}
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                } else {
                    SuppliersApi
                        .create(this.localSupplier)
                        .then(result => {
                            this.$router.push({
                                name: "Supplier Details",
                                params: { id: result.id }
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                }
            }
        }
    }
</script>

<style scoped>

</style>