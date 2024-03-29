import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Platform from '../views/Platform.vue'
import DataScreen from '../views/DataScreen'
// 移动端页面
import MLogin from '../views/Mobile/MLogin.vue'
import MUser from '../views/Mobile/MUser.vue'
import MUserSet from '../views/Mobile/MUserSet.vue'
import MEquipment from '../views/Mobile/MEquipment.vue'
import MEquipmentMsg from '../views/Mobile/MEquipmentMsg.vue'

// 引入vuex 
import store from '../store'

// 引入接口
import apiFun from "../network/api"

// 引入动态路由
// import AccountSecurity from '../views/AccountSecurity'
// import Cabinet from '../views/Cabinet'
// import Network from '../views/Network'
// import ProjectManage from '../views/ProjectManage'
// import Role from '../views/Role'
// import SelfInformation from '../views/SelfInformation'
// import SystemLog from '../views/SystemLog'
// import UserCenter from '../views/UserCenter'
// import UserManage from '../views/UserManage'

const routes = [
  // home页
  //  {
  //   name: 'Platform',
  //   path: '/Platform',
  //   component: Platform,
  // },
  // 路由重定向
  {
    path: '/',
    redirect: '/login',

  },
  // 登录页
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: {
      // 
      title: '登录页'
    }
  },
  {
    path: '/platform',
    name: 'Platform',
    component: Platform,
    meta: {
      // 
      title: '云智能维护管理平台'
    }
  },
  {
    path: '/dataScreen',
    name: 'DataScreen',
    component: DataScreen,
    meta: {
      // 
      title: '数据大屏'
    }
  },
  // {
  //   path: '/test',
  //   name: 'test',
  //   component: () => import('../views/test'),
  //   meta: {
  //     // 
  //     title: 'test'
  //   }
  // },
  // home页
  // {
  //   path: '/platform',
  //   name: '云智能维护平台',
  //   component: Platform,
  //   children: [
  //     {
  //       path: '/dataScreen',
  //       name: '数据大屏',
  //       component: () => import('../views/DataScreen'),
  //       meta: {
  //         title: '数据大屏'
  //       }
  //     },
  //     {
  //       path: '/network',
  //       name: '网络设备',
  //       component: () => import('../views/Network'),
  //       meta: {
  //         title: '网络设备'
  //       }
  //     },
  //     {
  //       path: '/cabinet',
  //       name: '器具柜设备',
  //       component: () => import('../views/Cabinet'),
  //       meta: {
  //         title: '器具柜设备'
  //       }
  //     },
  //     {
  //       path: '/selfInformation',
  //       name: '个人信息',
  //       component: () => import('../views/SelfInformation'),
  //       meta: {
  //         title: '个人信息'
  //       }
  //     },
  //     {
  //       path: '/accountSecurity',
  //       name: '账户安全',
  //       component: () => import('../views/AccountSecurity'),
  //       meta: {
  //         title: '账户安全'
  //       }
  //     },
  //     {
  //       path: '/projectManage',
  //       name: '项目管理',
  //       component: () => import('../views/ProjectManage'),
  //       meta: {
  //         title: '项目管理'
  //       }
  //     },
  //     {
  //       path: '/userManage',
  //       name: '用户管理',
  //       component: () => import('../views/UserManage'),
  //       meta: {
  //         title: '用户管理'
  //       }
  //     },
  //     {
  //       path: '/role',
  //       name: '角色管理',
  //       component: () => import('../views/Role'),
  //       meta: {
  //         title: '角色管理'
  //       }
  //     },
  //     {
  //       path: '/systemLog',
  //       name: '系统日志',
  //       component: () => import('../views/SystemLog'),
  //       meta: {
  //         title: '系统日志'
  //       }
  //     },
  //   ]
  // },

  // Phone页，移动端兜底
  {
    path: '/mLogin',
    name: 'MLogin',
    component: MLogin
  },
  {
    path: '/mUser',
    name: 'MUser',
    component: MUser
  },
  {
    path: '/mUserSet',
    name: 'MUserSet',
    component: MUserSet
  },
  {
    path: '/mEquipment',
    name: 'MEquipment',
    component: MEquipment
  },
  {
    path: '/mEquipmentMsg',
    name: 'MEquipmentMsg',
    component: MEquipmentMsg
  },

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// router.beforeEach(async(to,from,next)=>{
//   // console.log(to.path)
//   if(to.path != '/login'){
//     // if(localStorage.getItem('token')){
//       if(localStorage.getItem('token')){
//         if( store.getters.getUserRouters.length == 0) {
//           await apiFun.getMenu(
//             JSON.parse(localStorage.user_data).id
//           ).then(
//             res =>{
//               // 如果请求成功
//               if(res.code == 200){
//                 let userRouters = []
//                 res.data.forEach(
//                   item => {
//                     if(item.staticRouter){
//                       userRouters.push(
//                         {
//                           name:item.menuName,
//                           path:item.staticRouter,
//                           meta:{
//                             icon:item.icon,
//                             title:item.menuName
//                           },
//                           component: () => require.ensure([], (require) => require(`@/views${item.staticRouter}`))
//                         }
//                       )
//                     }

//                   }
//                 )
//                 store.commit('setUserRouter',userRouters)
//                 // console.log(userRouters)
//                 // console.log('添加前路由0',router.getRoutes())
//                 store.getters.getUserRouters.forEach(
//                   item =>{
//                     // console.log('这是item',item)
//                     router.addRoute('Platform',{...item})
//                   }
//                 )
//                 router.addRoute('Platform',{
//                       path: '/SubCabs',
//                       name: 'SubCabs',
//                       component: import('../views/SubCabs'),
//                       meta:{
//                         // 
//                         title: '副柜信息'
//                     }
//                   })
//                 console.log('当前路由',router.getRoutes())
//               }else if(res.status == 400){
//                 // 如果显示token已过期
//                 next('/login')
//               }

//             })
//           await next({ ...to, replace: true });   
//         }else{
//           next()
//         }
//       }else{
//         next('')
//       }
//   }else{
//     next()
//   }
// })

router.beforeEach(async (to, from, next) => {
  console.log(to.path)
  // 判断浏览设备
  let flag = navigator.userAgent.match(
    /(nokia|iphone|android|motorola|^mot-|softbank|foma|docomo|kddi|up.browser|up.link|htc|dopod|blazer|netfront|helio|hosin|huawei|novarra|CoolPad|webos|techfaith|palmsource|blackberry|alcatel|amoi|ktouch|nexian|samsung|^sam-|s[cg]h|^lge|ericsson|philips|sagem|wellcom|bunjalloo|maui|symbian|smartphone|midp|wap|phone|windows ce|iemobile|^spice|^bird|^zte-|longcos|pantech|gionee|^sie-|portalmmm|jigs browser|hiptop|^benq|haier|^lct|operas*mobi|opera*mini|320x320|240x320|176x220)/i
  );
  console.log(flag)
  // if (flag = true) {
  //   alert('欢迎进入移动版宁讯科技云智能维护平台！')
  //   let regex = /\/m/;
  //   if (regex.test(to.path)) {
  //     next()
  //   } else {
  //     next(to.path.replace(/\//g, "/m"))
  //   }
  // } else {
  //   next()
  // }
  if (window.innerWidth < 768) {
    // alert('欢迎进入移动版宁讯科技云智能维护平台！')
    let regex = /\/m/;
    if (regex.test(to.path)) {
      next()
    } else {
      next(to.path.replace(/\//g, "/m"))
    }
    if (to.path != '/mLogin') {
      // if(localStorage.getItem('token')){
      if (localStorage.getItem('token')) {
        if (store.getters.getUserRouters.length == 0) {
          await apiFun.getMenu(
            JSON.parse(localStorage.user_data).id
          ).then(
            res => {
              // 如果请求成功
              if (res.code == 200) {
                let userRouters = []
                res.data.forEach(
                  item => {
                    if (item.staticRouter) {
                      userRouters.push(
                        {
                          name: item.menuName,
                          path: item.staticRouter,
                          meta: {
                            icon: item.icon,
                            title: item.menuName
                          },
                          component: () => require.ensure([], (require) => require(`@/views${item.staticRouter}`))
                        }
                      )
                    }

                  }
                )
                store.commit('setUserRouter', userRouters)
                // console.log(userRouters)
                // console.log('添加前路由0',router.getRoutes())
                store.getters.getUserRouters.forEach(
                  item => {
                    // console.log('这是item',item)
                    router.addRoute('Platform', { ...item })
                  }
                )
                router.addRoute('Platform', {
                  path: '/SubCabs',
                  name: 'SubCabs',
                  component: import('../views/SubCabs'),
                  meta: {
                    // 
                    title: '副柜信息'
                  }
                })
                console.log('当前路由', router.getRoutes())
              } else if (res.status == 400) {
                // 如果显示token已过期
                next('/login')
              }

            })
          await next({ ...to, replace: true });
        } else {
          next()
        }
      } else {
        next('')
      }
    } else {
      next()
    }
  } else {
    if (to.path != '/login') {
      // if(localStorage.getItem('token')){
      if (localStorage.getItem('token')) {
        if (store.getters.getUserRouters.length == 0) {
          await apiFun.getMenu(
            JSON.parse(localStorage.user_data).id
          ).then(
            res => {
              // 如果请求成功
              if (res.code == 200) {
                let userRouters = []
                res.data.forEach(
                  item => {
                    if (item.staticRouter) {
                      userRouters.push(
                        {
                          name: item.menuName,
                          path: item.staticRouter,
                          meta: {
                            icon: item.icon,
                            title: item.menuName
                          },
                          component: () => require.ensure([], (require) => require(`@/views${item.staticRouter}`))
                        }
                      )
                    }

                  }
                )
                store.commit('setUserRouter', userRouters)
                // console.log(userRouters)
                // console.log('添加前路由0',router.getRoutes())
                store.getters.getUserRouters.forEach(
                  item => {
                    // console.log('这是item',item)
                    router.addRoute('Platform', { ...item })
                  }
                )
                router.addRoute('Platform', {
                  path: '/SubCabs',
                  name: 'SubCabs',
                  component: import('../views/SubCabs'),
                  meta: {
                    // 
                    title: '副柜信息'
                  }
                })
                console.log('当前路由', router.getRoutes())
              } else if (res.status == 400) {
                // 如果显示token已过期
                next('/login')
              }

            })
          await next({ ...to, replace: true });
        } else {
          next()
        }
      } else {
        next('')
      }
    } else {
      next()
    }
  }
})

export default router
