import ProductsApi from "../api/ProductsApi";

export default [
    {
        path: '/products',
        name: 'Products',
        component: () => import(/* webpackChunkName: "about" */ '../views/product/ProdudctsList'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            ProductsApi
                .getAll()
                .then(response => {
                    routeTo.params.products = response
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
        name: 'Create Product',
        component: () => import(/* webpackChunkName: "about" */ '../views/product/ProductForm'),
        props: false
    },
    {
        path: '/product/:id',
        name: 'Product Details',
        component: () => import(/* webpackChunkName: "about" */ '../views/product/ProductDetails'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            ProductsApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.product = response
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
        path: '/product/:id/edit',
        name: 'Edit Product',
        component: () => import(/* webpackChunkName: "about" */ '../views/product/ProductForm'),
        props: true,
        beforeEnter(routeTo, routeFrom, next) {
            ProductsApi
                .getById(routeTo.params.id)
                .then(response => {
                    routeTo.params.product = response
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