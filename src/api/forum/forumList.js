import axios from '@/plugins/axios'

export function getForumList() {
    return axios({
        url: 'app/api/postList',
        method: 'GET'
    })
}
