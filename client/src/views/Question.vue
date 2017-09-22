<template>
    <div class="hello">
        <h1>{{question.content}}</h1>
        <h2>Essential Links</h2>
        <h3>{{question.subjectName}}</h3>
        <el-button @click="logout">退出</el-button>
    </div>
</template>

<script>
    import fetch from '@/utils/fetch'
    import { removeToken } from '@/utils/auth'

    export default {
        name: 'hello',
        data() {
            return {
                question: {
                    content: 'content',
                    subjectName: 'subject'
                }
            }
        },
        methods: {
            logout() {
                fetch({
                    url: '/auth/logout',
                    method: 'post'
                }).then(response => {
                    if (response.code === 1) {
                        removeToken()
                        this.$router.push({ path: '/login' })
                    }
                }).catch(error => {
                    console.log(error)
                })
            }
        },
        mounted() {
            var _this = this
            fetch({
                url: '/exam/question/2',
                method: 'get'
            }).then(response => {
                _this.question = response.data
            }).catch(error => {
                console.log(error)
            })
        }
    }
</script>
