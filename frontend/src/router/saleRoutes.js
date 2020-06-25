import axios from "axios";
import ProductsApi from "../api/ProductsApi";
import SalesApi from "../api/SalesApi";
import CustomersApi from "../api/CustomersApi";

export default [
    {
        path: '/sales',
        name: 'Sales',
        component: () => import(/* webpackChunkName: "about" */ '../views/sale/SalesList'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            SalesApi
                .getAll()
                .then(response => {
                    routeTo.params.sales = response
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
        path: '/sales/create',
        name: 'Create Sale',
        component: () => import(/* webpackChunkName: "about" */ '../views/sale/SaleForm'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            axios.all([ProductsApi.getAll(), CustomersApi.getAll()])
                .then(axios.spread(function(products, customers) {
                    routeTo.params.products = products;
                    routeTo.params.customers = customers;
                    next();
                }))
        }
    },
    {
        path: '/sale/:id',
        name: 'Sale Details',
        component: () => import(/* webpackChunkName: "about" */ '../views/sale/SaleDetails'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            SalesApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.sale = response
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
        path: '/sale/:id/edit',
        name: 'Edit Sale',
        component: () => import(/* webpackChunkName: "about" */ '../views/sale/SaleForm'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            axios.all([SalesApi.getById(routeTo.params.id), ProductsApi.getAll(), CustomersApi.getAll()])
                .then(axios.spread(function(sale, products, customers) {
                    routeTo.params.sale = sale;
                    routeTo.params.products = products;
                    routeTo.params.customers = customers;
                    next();
                }))
        }
    }
]