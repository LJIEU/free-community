import Vue from 'vue'
import SvgIcon from '@/components/icon-svg/index'// svg component 注册的svg全局组件

// register globally
Vue.component('svg-icon', SvgIcon) // 全局注册SvgIcon组件

const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
// const result = requireAll(req)
// console.log('result>>>>>>>>',result); // 可以打印遍历的所有svg适量图形
