<template>
    <v-container>
        <v-layout row wrap>
            <v-flex xs12 sm6>
                <v-card>
                    <v-card-title class="justify-center">
                        Total profits from sales {{ totalProfit }}
                    </v-card-title>
                    <v-card-text>
                        <LineChart label="Sales" :labels="sparkLineData.labels" :values="sparkLineData.values"></LineChart>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import _ from 'lodash';
    import LineChart from "../components/LineChart";

    export default {
        name: "Dashboard",
        components: {
            LineChart
        },
        props: {
            orders: {
                type: Array,
                required: true
            },
            sales: {
                type: Array,
                required: true
            }
        },
        computed: {
            sparkLineData() {
                const cumulativeSum = (sum => value => sum += value)(0);

                let ordersConverted = [];
                this.orders.forEach(o => {
                    o.total = o.total * -1
                    ordersConverted.push(o)
                })
                let joinedArray = _.concat(ordersConverted, this.sales)
                joinedArray = _.orderBy(joinedArray, 'operationDate', 'asc')

                console.log(joinedArray)

                return {
                    labels: joinedArray.map(x => x.operationDate),
                    values: joinedArray.map(x => x.total).map(cumulativeSum)
                }
            },
            totalProfit() {
                return _.round(_.last(this.sparkLineData.values), 2)
            }
        },
    }
</script>

<style scoped>

</style>