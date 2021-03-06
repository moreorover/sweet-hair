import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify';
import lodash from "./plugins/lodash";
import 'nprogress/nprogress.css'

axios.defaults.baseURL = process.env.VUE_APP_AXIOS_BASE_URL

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  lodash,
  render: h => h(App)
}).$mount('#app')
