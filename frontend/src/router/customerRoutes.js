import CustomersApi from "../api/CustomersApi";

export default [
    {
        path: '/customers',
        name: 'Customer',
        component: () => import(/* webpackChunkName: "about" */ '../views/customer/CustomersList'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            CustomersApi
                .getAll()
                .then(response => {
                    routeTo.params.customers = response
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
        path: '/customers/create',
        name: 'Create Customer',
        // component: () => import(/* webpackChunkName: "about" */ '../views/CustomerDetails'),
        props: false
    },
    {
        path: '/customer/:id',
        name: 'Customer Details',
        component: () => import(/* webpackChunkName: "about" */ '../views/customer/CustomerDetails'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            CustomersApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.customer = response
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
        path: '/customer/:id/edit',
        name: 'Edit Customer',
        // component: () => import(/* webpackChunkName: "about" */ '../views/CustomerDetails'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            CustomersApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.customer = response
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