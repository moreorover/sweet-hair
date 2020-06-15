<template>
  <div class="about">
    <h1>This is an about page</h1>
    <v-row>
      <v-col cols="12" md="4">
        <OrdersList/>
      </v-col>
      <v-col cols="12" md="4">
        <SuppliersList/>
      </v-col>
      <v-col cols="12" md="4">
        <ProductsList/>
      </v-col>
    </v-row>
  </div>
</template>
<script>
  import OrdersList from "./OrdersList";
  import SuppliersList from "./SuppliersList";
  import ProductsList from "./ProductsList";

  export default {
    name: "About",
    components: {
      OrdersList, ProductsList, SuppliersList
    }
  }
</script>