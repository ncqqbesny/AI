import {
  createApp
} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 引入elementPlus文件
import ElementPlus from 'element-plus'
// 引入elementPlus样式文件
import 'element-plus/dist/index.css'
// 引入elementPlus字体图标文件
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 引入自定义样式文件
import './assets/common/css/base.css'
// 引入自定义elementMessage文件
import './assets/mobile/css/msg.css'
// 引入iconfont文件
import './assets/common/icon/iconfont.css'
// 引入自适应文件
import "lib-flexible-computer";
// 引入api接口文件
import apiFun from "./network/api";
//请求接口api
// Vue.prototype.$apiFun = apiFun;

const app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
// import jquery from "jquery";
// createApp(App).use(store).use(router).use(ElementPlus).mount('#app')
app.use(store)
app.use(router)
app.use(ElementPlus)
app.mount('#app')