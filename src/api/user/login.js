import axios from '@/plugins/axios'

export function login(loginForm) {
    return axios({
        url: 'app/api/login',
        method: 'POST',
        data: {
            ...loginForm // 转JSON数据发送
        }
    })
}

export function getUser(name){
    return axios({
        url: `app/api/user/userInfo/${name}`,
        method: 'GET'
    })
}
