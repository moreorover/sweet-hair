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
            <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                            color="primary"
                            dark
                            class="mb-2"
                            v-bind="attrs"
                            v-on="on"
                    >New Supplier</v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="headline">Supplier Control</span>
                    </v-card-title>

                    <v-card-text>
                        <v-form ref="form" v-model="formValid">
                            <v-text-field v-model="editSupplier.name" label="Supplier name" :rules="formRules.nameRules" required></v-text-field>
                            <v-text-field v-model="editSupplier.url" label="Supplier URL" :rules="formRules.nameRules" required></v-text-field>
                            <v-text-field v-model="editSupplier.logo" label="Supplier logo URL"></v-text-field>
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
        <SupplierCard v-for="supplier in suppliers" :key="supplier.id" :supplier="supplier" @edit="eventEdit" @delete="eventDelete"></SupplierCard>
    </div>
</template>

<script>
    import NProgress from 'nprogress';
    import SuppliersApi from "../api/SuppliersApi";
    import SupplierCard from "../components/SupplierCard";
    export default {
        name: "SuppliersList",
        components: { SupplierCard },
        comments: {
            SupplierCard
        },
        props: {
            suppliers: {
                type: Array,
                required: false
            }
        },
        data() {
          return {
              dialog: false,
              formValid: false,
              editSupplier: {
                  id: null,
                  name: '',
                  url: '',
                  logo: ''
              },
              defaultSuppler: {
                  id: null,
                  name: '',
                  url: '',
                  logo: ''
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
                this.editSupplier = event
                this.dialog = true
            },
            eventDelete(event) {
                NProgress.start()
                SuppliersApi.deleteSupplier(event)
                    .then(() => {
                        this.suppliers = this.suppliers.filter(supplier => supplier.id !== event.id)
                        NProgress.done()
                    })
            },
            eventSave() {
                NProgress.start()
                if (this.editSupplier.id === null) {
                    SuppliersApi.newSupplier(this.editSupplier)
                        .then(response => {
                            this.suppliers.push(response.data)
                            this.close()
                            NProgress.done()
                        })
                }
                if (this.editSupplier.id > 0) {
                    SuppliersApi.editSupplier(this.editSupplier)
                        .then(response => {
                            const matchSupplier = this.suppliers.find(supplier => supplier.id === response.data.id)
                            const indexSupplier = this.suppliers.indexOf(matchSupplier);
                            this.suppliers[indexSupplier] = response.data;
                            this.close()
                            NProgress.done()
                        })
                }
            },
            close () {
                this.dialog = false
                this.$nextTick(() => {
                    this.editSupplier = Object.assign({}, this.defaultSuppler)
                })
            }
        },
        beforeRouteEnter(routeTo, routeFrom, next) {
            SuppliersApi.getSuppliers()
                .then(suppliers => {
                    routeTo.params.suppliers = suppliers;
                    next()
                })
        },
        beforeRouteUpdate(routeTo, routeFrom, next) {
            SuppliersApi.getSuppliers()
                .then(suppliers => {
                    routeTo.params.suppliers = suppliers;
                    next()
                })
        }
    }
</script>

<style scoped>

</style>