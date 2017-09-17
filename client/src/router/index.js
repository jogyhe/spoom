import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Question from '@/views/Question'

Vue.use(Router)
export default new Router({
    routes: [{
        path: '/',
        name: 'Question',
        component: Question
    }, {
        path: '/hello',
        name: 'Hello',
        component: Hello
    }]
})
