<template>
	<view class="container">
		<!-- <navigator url="/pages/home/home" open-type="switchTab" hover-class="other-navigator-hover"> -->
		<view class="content">
			<view class="LoginFrom">
				<view class="index_png">
					<img :src="Login_png" alt="" />
				</view>
				<view class="index_title">
					<text>应急联 账号登录</text>
				</view>
				<view class="from_item">

					<view class="from_value">
						<uni-easyinput v-model="loginfrom.username" placeholder="请输入账号" class="from_input" />
					</view>
				</view>
				<view class="from_item">

					<view class="from_value">
						<uni-easyinput v-model="loginfrom.password" type="password" placeholder="请输入密码"
							class="from_input" />
					</view>

				</view>
				<view class="from_item agreement">
					<checkbox-group>
						<view v-for="(item,index) in agreement" :key="item.value" >
							<checkbox :name="item.value" :checked="item.checked" @click="checkAlarm(index)" />已阅读并同意 用户协议
						</view>
					</checkbox-group>

				</view>
				<view @click='toHome' class="loginBtn">
					登录
				</view>
			</view>

		</view>
		<!-- </navigator> -->
	</view>
</template>

<script>
	import {
		login,
		getLoginUserInfo,
		getRoleList
	} from '../../api/api.js';
	import {
		encode,checkNumberInArray
	} from '../../assets/utils.js'
	import store from '@/store/index.js'; //需要引入store
	export default {
		data() {
			return {
				// href: 'https://uniapp.dcloud.io/component/README?id=uniui'
				Login_png: '',
				loginfrom: {
					username: '',
					password: ''
				},
				agreement: [{
						value: 'user-agreement',
						checked: false,
					}

				],
				isChecked: false,
				roleId:'',
				checkNumberInArray

			}
		},
		methods: {
			checkAlarm(index) {
				this.isChecked = !this.isChecked
				console.log(this.isChecked)
			},
			encodepassword(password, eles = []) {
				eles.push(password);
				return encode("0x12", eles);
			},
			toHome() {
				let data = {}
				data.username = this.loginfrom.username
				data.password = this.encodepassword(this.loginfrom.password)
				if (this.isChecked) {
					login(data)
						.then(res => {
							if (res.data.status == 400) {
								uni.showToast({
									icon: 'error',
									title: res.data.msg
								})
							}
							console.log(res);
							if (res.data.code == 200) {
								this.roleId = res.data['result'].roles.roleId
								let that = this
								uni.setStorage({
									key: 'user_token',
									data: res.data.token,
									success: (res) => {
										that.getUser()
										
									}
								})
								store.commit('setLoginUser', res.data['result'])
								
								// console.log(res.data['result'])
								// uni.getStorage({
								// 	key: 'user_token',
								// 	success: function (res) {
								// 		console.log(res.data);
								// 	}
								// });
								
							}
						})
						.catch(err => {
							console.error(err);
						});
				} else {
					console.log(this.agreement)
					uni.showToast({
						icon: 'error',
						title: '请阅读并同意用户协议'
					})
				}


			},
			getUser() {
				getLoginUserInfo().then(
					res => {
						console.log(res.data.user)
						store.commit('setLoginUserInfo', res.data.user)
						this.queryRole(this.roleId)
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
						let menu = res.data.data.list[0].menuIds.split(',')
						// console.log(menu)
						store.commit('setMenu', menu)
						
						uni.switchTab({
							url: '../home/home'
						})
					}
				)
			}
		},
		onLoad() {
			// console.log('now:' + this.nowTime());  
		},
		onShow() {
			this.loginfrom = {
				username: '',
				password: ''
			}
			this.Login_png = uni.getStorageSync('Material') + '/Designer_11.png'
			console.log(this.Login_png)
			// 判断token是否失效
			if(uni.getStorageSync('user_token')){
				console.log('没有过期')
				this.getUser()
			}else{
				console.log('过期了')
			}
		}
	}
</script>

<style>
	@import url('~@/static/font/fontStyle.css');

	.container {
		background: #F0F0F3;
		height: 100vh;
		padding: 0 !important;
		background-size: 100% 100%;
		background-attachment: fixed;
		overflow: hidden;
		font-family: alyuan;
	}

	.LoginFrom {
		margin: 0 auto;
		overflow: hidden;
		display: flex;
		flex-direction: column;
	}

	.index_png {
		margin: 261rpx auto 41rpx;
	}

	.index_png img {
		width: 260rpx;
		height: 260rpx;
	}

	.index_title {
		margin: 0 auto 14rpx;
		font-size: 40rpx;
		font-family: alihei;
	}

	.from_item {
		display: flex;
		width: 595rpx;
		margin: 8rpx auto;

	}
	
	>>>.uni-input-placeholder {
		font-size: 32rpx !important;
	}
	
	>>>.uni-easyinput__content {
		width: 595rpx;
		height: 96rpx;
		border-radius: 32rpx;
		font-size: 32rpx !important;
		background: #D0D3D4 !important;
		color: #9E9E9E !important;
		font-family: alyuan;
	}

	>>>uni-checkbox .uni-checkbox-input {
		height: 33rpx;
		width: 33rpx;
		border-radius: 33rpx;
	}

	.agreement {
		margin-top: 47rpx;
		font-family: alyuan;
	}

	.loginBtn {
		width: 595rpx;
		height: 96rpx;
		margin: 60rpx auto 0;
		text-align: center;
		line-height: 96rpx;
		font-size: 40rpx;
		border-radius: 48rpx;
		background: #005F7A;
		color: #F5F4F6;
		font-family: alihei;
	}

	/* 微信小程序兼容样式 */
	
	.uni-easyinput{
		width: 595rpx  !important;
		height: 96rpx  !important;
		border-radius: 32rpx !important;
		font-size: 32rpx !important;
		background: #D0D3D4 !important;
		color: #9E9E9E !important;
		font-family: alyuan;
	}
	.uni-easyinput__content{
		border-radius: 32rpx !important;
	}
	checkbox .wx-checkbox-input{
	  width: 40rpx; 
	  height: 40rpx; 
	  border-radius: 50%;
	  background: #D0D3D4;
	}
	
	checkbox .wx-checkbox-input.wx-checkbox-input-checked::before{
	  width: 40rpx;
	  height: 40rpx;
	  line-height: 40rpx;
	  border-radius: 50%;
	  text-align: center;
	  font-size:32rpx; 
	  color:#FFF; 
	  background: transparent;
	  transform:translate(-50%, -50%) scale(1);
	  -webkit-transform:translate(-50%, -50%) scale(1);
	}
</style>