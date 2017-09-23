<template>
    <el-menu mode="vertical" theme="light" unique-opened :default-active="$route.path" router :collapse="isCollapse">
        <template v-for="item in routers" v-if='!item.hidden'>
            <el-submenu :index="item.path" v-waves v-if="item.children&&item.children.length>0">
                <template slot="title">
                    <icon-svg class="menu-icon" v-if='item.icon' :icon-class="item.icon"></icon-svg>
                    <span class="menu-title" slot="title">{{item.name}}</span>
                </template>
                <el-menu-item :index="child.path" v-for='(child,index) in item.children' :key='index'>
                    <icon-svg class="menu-icon" v-if='child.icon' :icon-class="child.icon"></icon-svg>
                    <span class="menu-title">{{child.name}}</span>
                </el-menu-item>
            </el-submenu>
            <el-menu-item :index='item.path' v-else>
                <icon-svg class="menu-icon" v-if='item.icon' :icon-class="item.icon"></icon-svg>
                <span class="menu-title" slot="title">{{item.name}}</span>
            </el-menu-item>
        </template>
    </el-menu>
</template>


<script>
    import { mapGetters } from 'vuex'
    import waves from '@/directive/waves.js' // 水波纹指令

    export default {
        directives: {
            waves
        },
        computed: {
            ...mapGetters([
                'routers',
                'sidebar'
            ]),
            isCollapse() {
                return this.sidebar.toggle
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .menu-title{
        font-size: 14px;
        line-height: 16px;
    }
    .menu-icon {
        font-size: 16px;
        margin-right: 10px;
        color: #2944a0;
    }
</style>
