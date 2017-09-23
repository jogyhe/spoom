const getters = {
    sidebar: state => state.app.sidebar,
    token: state => state.user.token,
    avatar: state => state.user.avatar,
    nickName: state => state.user.nickName,
    gender: state => state.user.gender,
    status: state => state.user.status,
    roles: state => state.user.roles,
    setting: state => state.user.setting,
    routers: state => state.permission.routers,
    addRouters: state => state.permission.addRouters
}

export default getters
