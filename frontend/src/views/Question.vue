<template>
    <div class="hello">
        <h1>{{question.content}}</h1>
        <h2>Essential Links</h2>
        <h3>{{question.subjectName}}</h3>
    </div>
</template>

<script>
    import axios from 'axios'

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
        mounted() {
            var _this = this;
            axios.get('http://localhost:8080/exam/question/4', {
                auth: {
                    username: 'spoomlzx@qq.com',
                    password: 'asdf'
                },
                headers: { 'X-TOKEN': 'admin' }
            })
                .then(response => {
                    if (response.data.data) {
                        _this.question = response.data.data
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }
</script>
