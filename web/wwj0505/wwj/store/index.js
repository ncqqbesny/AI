// 页面路径：store/index.js 
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);//vue的插件机制

//Vuex.Store 构造器选项
const store = new Vuex.Store({
	state:{//存放状态
		peopleId:'',
		loginUser:{},
		loginUserInfo:{},
		menu:[],
		eventCode:''
	},
	mutations: {
		setPeopleId(state,id) {
			// 变更状态
			state.peopleId = id
		},
		setLoginUser(state,user){
			state.loginUser = user
		},
		setLoginUserInfo(state,user){
			state.loginUserInfo = {...user}
		},
		setMenu(state,menu){
			state.menu = menu
		},
		setEventCode(state,code){
			state.eventCode = code
		}
	}
})
export default store
