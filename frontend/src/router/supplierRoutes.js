import SuppliersApi from "../api/SuppliersApi";

export default [
    {
        path: '/suppliers',
        name: 'Suppliers',
        component: () => import(/* webpackChunkName: "about" */ '../views/supplier/SuppliersList'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            SuppliersApi
                .getAll()
                .then(response => {
                    routeTo.params.suppliers = response
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
        path: '/suppliers/create',
        name: 'Supplier Customer',
        component: () => import(/* webpackChunkName: "about" */ '../views/supplier/SupplierForm'),
        props: false
    },
    {
        path: '/supplier/:id',
        name: 'Supplier Details',
        component: () => import(/* webpackChunkName: "about" */ '../views/supplier/SupplierDetails'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            SuppliersApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.supplier = response
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
        path: '/supplier/:id/edit',
        name: 'Edit Supplier',
        component: () => import(/* webpackChunkName: "about" */ '../views/supplier/SupplierForm'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            SuppliersApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.supplier = response
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
    }
]