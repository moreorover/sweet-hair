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
        <SupplierCard v-for="supplier in suppliers" :key="supplier.id" :supplier="supplier" @edit="eventEdit" @delete="eventDelete">{{ supplier.name }}</SupplierCard>
    </div>
</template>

<script>
    import SuppliersApi from "../api/SuppliersApi";
    import SupplierCard from "../components/SupplierCard";
    export default {
        name: "SuppliersList",
        components: { SupplierCard },
        comments: {
            SupplierCard
        },
        data() {
          return {
              dialog: false,
              formValid: false,
              suppliers: [],
              editSupplier: {
                  id: null,
                  name: '',
                  url: '',
                  logo: ''
              },
              defaultSuppler: {
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
        created() {
            SuppliersApi.getSuppliers()
            .then(suppliers => {
                this.suppliers = suppliers["_embedded"].suppliers;
            })
        },
        methods: {
            eventEdit(event) {
                this.editSupplier = event
                this.dialog = true
            },
            eventDelete(event) {
                SuppliersApi.deleteSupplier(event).then(response => { console.log(response)})
            },
            eventSave() {
                if (this.editSupplier.id === null) {
                    console.log("Saving new Supplier")
                    SuppliersApi.newSupplier(this.editSupplier)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                    return
                }
                if (this.editSupplier.id > 0) {
                    console.log("saving edited Supplier")
                    SuppliersApi.editSupplier(this.editSupplier)
                        .then(response => {
                            console.log(response)
                            this.close()
                        })
                }
            },
            close () {
                this.dialog = false
                this.$nextTick(() => {
                    this.editSupplier = Object.assign({}, this.defaultSuppler)
                })
            }
        }
    }
</script>

<style scoped>

</style>