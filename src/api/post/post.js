import axios from '@/plugins/axios'

// 查询帖子分类
export function getTopicList() {
    return axios({
        url: 'app/api/topicList',
        method: 'GET'
    })
}

export function savePost(postForm) {
    return axios({
        url: 'app/api/savePost',
        method: 'POST',
        data: JSON.stringify(postForm),
        // data:{
        //     ...postForm
        // },
        dataType: 'json',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            'jwt_token': window.localStorage.getItem('jwtToken')
        }
    })
}

export function getArticle(id) {
    return axios({
        url: `app/api/post/${id}`,
        method: 'GET'
    })
}

export function getPostUser(postId) {
    return axios({
        url: `app/api/post/user/${postId}`,
        method: 'GET'
    })
}


