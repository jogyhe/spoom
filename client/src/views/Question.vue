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
            var _this = this
            axios.get('http://localhost:8081/exam/question/2', {
                headers: { 'Token': 'rTrwXSt/f1SQbVNN+Yyk155BklR/C+gcTeJZQCT/kAIoAPx1cKck43DWu4AaY2rcd7s4lwy6dLIiaKmsvtvKXA==' }
            }).then(function (response) {
                _this.question = response.data.data
                console.log(response)
            }).catch(function (error) {
                console.log(error)
            })
        }
    }
</script>
