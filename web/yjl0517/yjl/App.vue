<script>
	import {
		login,
		getLoginUserInfo,
		getRoleList
	} from './api/api.js';
	import store from '@/store/index.js';
	export default {
		computed:{
			loginUserInfo() {
				return this.$store.state.loginUserInfo 
			},
		},
		methods:{
			getUser() {
				getLoginUserInfo().then(
					res => {
						console.log(res.data.user)
						store.commit('setLoginUserInfo', res.data.user)
					}
				)
			},
			queryRole(roleId) {
				getRoleList({
					pageNum: 1,
					pageSize: 20,
					roleId: roleId
				}).then(
					res => {
						// console.log(res.data.data.list[0].menuIds.split(','))
						store.commit('setMenu', res.data.data.list[0].menuIds.split(','))
					}
				)
			}
		},
		onLaunch: function() {
			// console.warn('当前组件仅支持 uni_modules 目录结构 ，请升级 HBuilderX 到 3.1.0 版本以上！')
			// console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
			if(this.loginUserInfo.roles){
				// console.log(this.loginUserInfo.roles)
			}
			if(uni.getStorageSync('user_token')){
				// console.log(uni.getStorageSync('user_token'))
			}
		},
		onHide: function() {
			// console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import '@/uni_modules/uni-scss/index.scss';
	/* #ifndef APP-NVUE */
	@import '@/static/customicons.css';
	// 设置整个项目的背景色
	page {
		// background-color: #f1f2f6;
		background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%);
		background-repeat:no-repeat;/*设置背景不重复*/
			background-attachment:fixed; /*背景图片不会随着页面的滚动而滚动。*/
			background-size:cover;/*	此时会保持图像的纵横比并将图像缩放成将完全覆盖背景定位区域的最小大小。*/

	}
	
	
	/* #endif */
	.example-info {
		font-size: 14px;
		color: #333;
		padding: 10px;
	}
</style>
