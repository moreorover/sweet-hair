import axios from "axios";
import OrdersApi from "../api/OrdersApi";
import SalesApi from "../api/SalesApi";

export default [
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import(/* webpackChunkName: "about" */ '../views/Dashboard'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            axios.all([OrdersApi.getAll(), SalesApi.getAll()])
                .then(axios.spread(function(orders, sales) {
                    routeTo.params.orders = orders;
                    routeTo.params.sales = sales;
                    next();
                }))
        }
    }
]