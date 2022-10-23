<template>
  <img ref="img" alt=""/>
</template>

<script>
export default {
  name: 'authImg',
  props: {
    authSrc: {
      type: String,
      required: false,
      default: ''
    }
  },
  mounted () {
    let token = sessionStorage.getItem('token')
    Object.defineProperties(Image.prototype, ' authSrc', {
      writable: true,
      enumerable: true,
      configurable: true
    })
    let img = this.$refs.img
    let request = new XMLHttpRequest()
    request.responseType = 'blob'
    request.open('get', this.authSrc, true)
    request.setRequestHeader('token', token)
    request.onreadystatechange = e => {
      if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
        img.src = URL.createObjectURL(request.response)
        img.onload = () => {
          URL.revokeObjectURL(img.src)
        }
      }
    }
    request.send(null)
  }
}
</script>
