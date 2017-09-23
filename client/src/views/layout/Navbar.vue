<template>
    <el-menu class="navbar" mode="horizontal">
        <el-submenu class="avatar-container" index="2">
            <template slot="title">
                <img class="user-avatar" :src="avatar">
                <span>{{nickName}}</span>
            </template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
            <el-menu-item index="2-3"><span @click="logout" style="display:block;">退出登录</span></el-menu-item>
        </el-submenu>
    </el-menu>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        computed: {
            ...mapGetters([
                'sidebar',
                'nickName',
                'avatar'
            ])
        },
        methods: {
            logout() {
                this.$store.dispatch('Logout').then(() => {
                    location.reload()// 为了重新实例化vue-router对象 避免bug
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .navbar {
        height: 50px;
        line-height: 50px;
        border-radius: 0px !important;
        .avatar-container {
            height: 50px;
            display: inline-block;
            position: absolute;
            right: 50px;
            .user-avatar {
                width: 35px;
                height: 35px;
                border-radius: 6px;
            }
        }
    }
</style>
