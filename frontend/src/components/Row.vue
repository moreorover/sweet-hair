<template>
    <v-card class="mx-auto ma-3">
        <v-card-title>
            {{ data.name }}
        </v-card-title>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon @click="clickedDelete">
                <v-icon color="red">mdi-delete</v-icon>
            </v-btn>
            <v-btn icon @click="clickedEdit">
                <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon :href="data.url" target="_blank">
                <v-icon>mdi-open-in-new</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "Row",
        props: {
            data: {
                id: null,
                name: "",
                url: "",
                logo: ""
            }
        },
        methods: {
            clickedEdit() {
                this.$emit('edit', this.data)
            },
            clickedDelete() {
                this.$emit('delete', this.data)
            }
        }
    }
</script>

<style scoped>

</style>