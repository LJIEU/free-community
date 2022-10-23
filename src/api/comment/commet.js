import axios from '@/plugins/axios'

export function getParent(commentId) {
    return axios({
        url: `app/api/comment/list/getParent/${commentId}`,
        method: 'GET'
    })
}


export function getList(articleId, userName) {
    return axios({
        url: `app/api/comment/list/${articleId}/${userName}`,
        method: 'GET'
    })
}

export function saveComment(commentInfo) {
    return axios({
        url: 'app/api/comment/saveComment',
        method: 'post',
        data: JSON.stringify(commentInfo),
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            'jwt_token': window.localStorage.getItem('jwtToken')
        }

    })
}

export function updateLike(info) {
    return axios({
        url: 'app/api/commentLike/isLike',
        method: 'POST',
        data: JSON.stringify(info),
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            'jwt_token': window.localStorage.getItem('jwtToken')
        }
    })
}
