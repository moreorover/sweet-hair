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
            <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                            color="primary"
                            dark
                            class="mb-2"
                            v-bind="attrs"
                            v-on="on"
                    >New Product</v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="headline">Product Control</span>
                    </v-card-title>

                    <v-card-text>
                        <v-form ref="form" v-model="formValid">
                            <v-text-field v-model="editProduct.name" label="Supplier name" :rules="formRules.nameRules" required></v-text-field>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                        <v-btn color="blue darken-1" text @click="eventSave" :disabled="!formValid">Save</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-toolbar>
        <ProductCard v-for="product in products" :key="product.id" :product="product" @edit="eventEdit" @delete="eventDelete"></ProductCard>
    </div>
</template>

<script>
    import NProgress from 'nprogress';
    import ProductsApi from "../api/ProductsApi";
    import ProductCard from "../components/ProductCard";
    export default {
        name: "ProductsList",
        components: { ProductCard },
        props: {
            products: {
                type: Array,
                required: true
            }
        },
        data() {
          return {
              dialog: false,
              formValid: false,
              editProduct: {
                  id: null,
                  name: ''
              },
              defaultProduct: {
                  id: null,
                  name: ''
              },
              formRules: {
                  nameRules: [
                      v => !!v || 'Name is required',
                      v => (v && v.length >= 5) || 'Input must be greater than 5 characters',
                  ]
              }
          }
        },
        watch: {
            dialog(val) {
                val || this.close()
            }
        },
        methods: {
            eventEdit(event) {
                this.editProduct = event
                this.dialog = true
            },
            eventDelete(event) {
                NProgress.start()
                ProductsApi.deleteProduct(event).then(() => {
                    this.products = this.products.filter(product => product.id === event.id);
                    NProgress.done()
                })
            },
            eventSave() {
                NProgress.start()
                if (this.editProduct.id === null) {
                    ProductsApi.newProduct(this.editProduct)
                        .then(response => {
                            this.products.push(response.data)
                            this.close()
                            NProgress.done()
                        })
                    return
                }
                if (this.editProduct.id > 0) {
                    ProductsApi.editProduct(this.editProduct)
                        .then(response => {
                            const matchProduct = this.products.find(product => product.id === response.data.id)
                            const indexProduct = this.products.findIndex(matchProduct);
                            this.products[indexProduct] = response.data;
                            this.close()
                            NProgress.done()
                        })
                }
            },
            close () {
                this.dialog = false
                this.formValid = false
                this.editProduct = Object.assign({}, this.defaultProduct)
                // this.$nextTick(() => {
                //     this.editProduct = Object.assign({}, this.defaultProduct)
                // })
            }
        },
        beforeRouteEnter(routeTo, routeFrom, next) {
            ProductsApi.getProducts()
                .then(products => {
                    routeTo.params.products = products;
                    next()
                })
        },
        beforeRouteUpdate(routeTo, routeFrom, next) {
            ProductsApi.getProducts()
                .then(products => {
                    routeTo.params.products = products;
                    next()
                })
        }
    }
</script>

<style scoped>

</style>