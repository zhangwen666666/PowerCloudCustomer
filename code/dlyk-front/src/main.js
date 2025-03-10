import { createApp } from 'vue' // 从vue框架导入createApp函数
// import './style.css' // 导入css样式
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue' // 从./App.vue页面导入App组件
// 利用上面所导入的createApp()函数，创建一个vue应用，
// mount是挂载到#app的地方(id为app的标签中)
// vue运行的结果会渲染到#app中
// var app = createApp(App);
// app.use(ElementPlus)
// app.mount('#app')
// import LoginView from "./view/LoginView.vue";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router/router.js';
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus).use(router).mount('#app')
