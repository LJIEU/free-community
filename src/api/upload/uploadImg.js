import axios from "@/plugins/axios";

export function uploadImg(formData) {
    return axios({
        url: '/app/api/upload?module=articles',
        method: 'post',
        // data: this.$http.adornData(formData, false)
        data: formData,
        headers: {'Content-Type': 'multipart/form-data', 'jwtToken': window.localStorage.getItem('jwtToken')}
    })
}
