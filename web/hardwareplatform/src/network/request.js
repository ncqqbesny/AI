// 封装axios
import axios from 'axios'
// 序列化
import QS from 'qs';
// vuex
import store from '../store/index'

//这一步的目的是判断出当前是开发环境还是生成环境，方法不止一种，达到目的就行
// if(process.env.NODE_ENV=="development"){
//   baseURL=''
// }else{
//   baseURL=''
// }
// console.log(window.Glob.baseURL)
// 使用自定义的配置文件发送请求
const instance = axios.create({
    // 部署环境
    baseURL: window.Glob.baseURL,
    timeout: 1200000,
    headers: {
        // 'Content-Type': 'application/json;charset=UTF-8',
        // 'custome-header':'tianliangjiaoyu'
    }
});
// post请求的时候，我们需要加上一个请求头，所以可以在这里进行一个默认的设置，即设置post的请求头为
axios.defaults.headers.post['Content-Type'] = 'application/json;';
// 添加请求拦截器
instance.interceptors.request.use(function (config) {
    // 每次发送请求之前判断vuex中是否存在token        
    // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断 
    if (localStorage.getItem('token')) { //判断token是否存在
        config.headers.token = localStorage.getItem('token'); //将token设置成请求头
    }
    // token && (config.headers.Authorization = token);
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 这里说一下token，一般是在登录完成之后，将用户的token通过localStorage或者cookie存在本地，
// 然后用户每次在进入页面的时候（即在main.js中），会首先从本地存储中读取token，如果token存在说明用户已经登陆过，
// 则更新vuex中的token状态。然后，在每次请求接口的时候，都会在请求的header中携带token，
// 后台人员就可以根据你携带的token来判断你的登录是否过期，如果没有携带，则说明没有登录过。
// 添加响应拦截器
instance.interceptors.response.use(function (response) {
    if (response.status === 200) {
        return Promise.resolve(response);
    } else {
        return Promise.reject(response);
    }
}, function (error) {
    // 对响应错误做点什么
    if (error.response.status) {
        switch (error.response.status) {
            // 401: 未登录
            // 未登录则跳转登录页面，并携带当前页面的路径
            // 在登录成功后返回当前页面，这一步需要在登录页操作。                
            case 401:
                router.replace({
                    path: '/login',
                    query: {
                        redirect: router.currentRoute.fullPath
                    }
                });
                break;
                // 403 token过期
                // 登录过期对用户进行提示
                // 清除本地token和清空vuex中token对象
                // 跳转登录页面                
            case 400:
                this.$message({
                    message: '登录过期，请重新登录',
                    duration: 1000,
                    type: 'success'
                });
                // 清除token
                localStorage.removeItem('token');
                store.commit('loginSuccess', null);
                // 跳转登录页面，并将要浏览的页面fullPath传过去，登录成功后跳转需要访问的页面 
                setTimeout(() => {
                    router.replace({
                        path: '/login',
                        query: {
                            redirect: router.currentRoute.fullPath
                        }
                    });
                }, 1000);
                break;

                // 404请求不存在
            case 404:
                this.$message({
                    message: '网络请求不存在',
                    duration: 1500,
                    type: 'success'

                });
                break;
                // 其他错误，直接抛出错误提示
            default:
                this.$message({
                    message: error.response.data.message,
                    duration: 1500,
                    type: 'success'

                });
        }
        return Promise.reject(error.response);
    }
});

/** 
 * get方法，对应get请求 
 * @param {String} url [请求的url地址] 
 * @param {Object} params [请求时携带的参数] 
 */
export function get(url, params) {
    return new Promise((resolve, reject) => {
        instance.get(url, {
                params: params
            })
            .then(res => {
                resolve(res.data);
            })
            .catch(err => {
                reject(err.data)
            })
    });
}
/** 
 * post方法，对应post请求 
 * @param {String} url [请求的url地址] 
 * @param {Object} params [请求时携带的参数] 
 */
export function post(url, params, headers) {
    return new Promise((resolve, reject) => {
        instance.post(url, params, headers)
            .then(res => {
                resolve(res.data);
            })
            .catch(err => {
                reject(err.data)
            })
    });
}