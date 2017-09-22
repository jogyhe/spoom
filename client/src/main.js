import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import IconSvg from '@/components/Icon'
import App from './App'
import router from './router'
import store from './store'
import './permission' // 权限

Vue.component('icon-svg', IconSvg)
Vue.use(ElementUI)
Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: {
        App
    }
})
