const app = {
    state: {
        sidebar: {
            toggle: localStorage.getItem('sidebarToggle')
        }
    },
    mutations: {
        TOGGLE_SIDEBAR: state => {
            if (state.sidebar.toggle) {
                localStorage.setItem('sidebarStatus', 0)
            } else {
                localStorage.setItem('sidebarStatus', 1)
            }
            state.sidebar.toggle = !state.sidebar.toggle
        }
    },
    actions: {
        ToggleSideBar({ commit }) {
            commit('TOGGLE_SIDEBAR')
        }
    }
}

export default app
