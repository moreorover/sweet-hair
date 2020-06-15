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
    import ProductsApi from "../api/ProductsApi";
    import ProductCard from "../components/ProductCard";
    export default {
        name: "ProductsList",
        components: { ProductCard },
        data() {
          return {
              dialog: false,
              formValid: false,
              products: [],
              editProduct: {
                  id: null,
                  name: ''
              },
              defaultProduct: {
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
        created() {
            ProductsApi.getProducts()
            .then(suppliers => {
                this.products = suppliers["_embedded"].products;
            })
        },
        methods: {
            eventEdit(event) {
                this.editProduct = event
                this.dialog = true
            },
            eventDelete(event) {
                ProductsApi.deleteProduct(event).then(response => { console.log(response)})
            },
            eventSave() {
                if (this.editProduct.id === null) {
                    console.log("Saving new Supplier")
                    ProductsApi.newProduct(this.editProduct)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                    return
                }
                if (this.editProduct.id > 0) {
                    console.log("saving edited Supplier")
                    ProductsApi.editProduct(this.editProduct)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                }
            },
            close () {
                this.dialog = false
                this.$nextTick(() => {
                    this.editProduct = Object.assign({}, this.defaultProduct)
                })
            }
        }
    }
</script>

<style scoped>

</style>