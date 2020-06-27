<template>
    <div>
    <v-toolbar>
        <v-toolbar-title>Products</v-toolbar-title>
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
                link :to="{ name: 'Products' }"
        >List Products</v-btn>
    </v-toolbar>

    <v-card class="mx-auto ma-3">
        <v-card-title>
            <span class="headline">{{ title }}</span>
        </v-card-title>

        <v-card-text>
            <v-form ref="customerForm" v-model="formValid">
                <v-text-field v-model="localProduct.name" label="Product Name" :rules="formRules.nameRules" required></v-text-field>
                <v-text-field v-model.number="localProduct.inStockCount" label="In Stock Count" :rules="formRules.nameRules" disabled></v-text-field>
                <v-text-field v-model.number="localProduct.size" label="Product Size" :rules="formRules.sizeRules" required></v-text-field>
                <v-text-field v-model="localProduct.sizeUnit" label="Product Size Unit (g, ml, etc...)" :rules="formRules.sizeUnitRules" required></v-text-field>
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
    import ProductsApi from "../../api/ProductsApi";
    import NProgress from "nprogress";

    export default {
        name: "ProductForm",
        props: {
            product: {
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
                    ],
                    sizeUnitRules: [
                        v => !!v || 'Size Unit is required',
                        v => (v && v.length <= 3) || 'Input must be less or equal to 3 characters',
                    ],
                    sizeRules: [
                        v => !!v || 'Size is required',
                        v => (v && v > 0) || 'Input must be greater than 0',
                    ]
                },
                localProduct: { ...this.product }
            }
        },
        computed: {
            title() {
                if (this.product) {
                    return 'Edit Product'
                } else {
                    return 'New Product'
                }
            },
            formInputsChanged() {
                return JSON.stringify(this.product) === JSON.stringify(this.localProduct)
            }
        },
        methods: {
            save() {
                NProgress.start()
                if (this.localProduct.id){
                    ProductsApi
                        .update(this.localProduct)
                        .then(result => {
                            this.$router.push({
                                name: "Product Details",
                                params: {id: result.id}
                            })
                        })
                        .catch(() => {
                            NProgress.done()
                        })
                } else {
                    ProductsApi
                        .create(this.localProduct)
                        .then(result => {
                            this.$router.push({
                                name: "Product Details",
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