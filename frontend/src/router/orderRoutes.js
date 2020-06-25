import axios from "axios";
import OrdersApi from "../api/OrdersApi";
import ProductsApi from "../api/ProductsApi";
import SuppliersApi from "../api/SuppliersApi";

export default [
    {
        path: '/orders',
        name: 'Orders',
        component: () => import(/* webpackChunkName: "about" */ '../views/order/OrdersList'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            OrdersApi
                .getAll()
                .then(response => {
                    routeTo.params.orders = response
                    next()
                })
                .catch(error => {
                    console.log(error)
                    // if (error.response && error.response.status === 404) {
                    //     next({ name: '404', params: { resource: 'event' } })
                    // } else {
                    //     next({ name: 'network-issue' })
                    // }
                })
        }
    },
    {
        path: '/orders/create',
        name: 'Create Order',
        component: () => import(/* webpackChunkName: "about" */ '../views/order/OrderForm'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            axios.all([ProductsApi.getAll(), SuppliersApi.getAll()])
                .then(axios.spread(function(products, suppliers) {
                    routeTo.params.products = products;
                    routeTo.params.suppliers = suppliers;
                    next();
                }))
        }
    },
    {
        path: '/order/:id',
        name: 'Order Details',
        component: () => import(/* webpackChunkName: "about" */ '../views/order/OrderDetails'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            OrdersApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.order = response
                    next()
                })
                .catch(error => {
                    console.log(error)
                    // if (error.response && error.response.status === 404) {
                    //     next({ name: '404', params: { resource: 'event' } })
                    // } else {
                    //     next({ name: 'network-issue' })
                    // }
                })
        }
    },
    {
        path: '/order/:id/edit',
        name: 'Edit Order',
        component: () => import(/* webpackChunkName: "about" */ '../views/order/OrderForm'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            axios.all([OrdersApi.getById(routeTo.params.id), ProductsApi.getAll(), SuppliersApi.getAll()])
                .then(axios.spread(function(order, products, suppliers) {
                    routeTo.params.order = order;
                    routeTo.params.products = products;
                    routeTo.params.suppliers = suppliers;
                    next();
                }))
        }
    }
]