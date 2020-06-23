import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import NProgress from 'nprogress'

import customerRoutes from "./customerRoutes";
import supplierRoutes from "./supplierRoutes";
import productRoutes from "./productRoutes";
import orderRoutes from "./orderRoutes";

Vue.use(VueRouter)

  const baseRoutes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const routes = baseRoutes
    .concat(
        customerRoutes,
        orderRoutes,
        productRoutes,
        supplierRoutes
    )

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((routeTo, routeFrom, next) => {
  NProgress.start()
  next()
})

router.afterEach(() => {
  NProgress.done()
})

export default router
