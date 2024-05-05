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
						<view v-for="(item,index) in agreement" :key="item.value" @click="checkAlarm(index)">
							<checkbox :name="item.value" :checked="item.checked" @click="" />已阅读并同意 用户协议
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
		encode
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
						isChecked: false
					}

				]

			}
		},
		methods: {
			checkAlarm(index) {
				// console.log('alarm', index)
				this.agreement[0].isChecked = !this.agreement[0].isChecked //取反
				// }
				// this.calcCount();
				console.log(this.agreement)
			},
			encodepassword(password, eles = []) {
				eles.push(password);
				return encode("0x12", eles);
			},
			toHome() {
				let data = {}
				data.username = this.loginfrom.username
				data.password = this.encodepassword(this.loginfrom.password)
				if (this.agreement[0].isChecked) {
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
								// uni.setStorage({
								// 	key: 'user_token',
								// 	data: res.data.token,
								// 	success: function () {
								// 		console.log('success');
								// 	}
								// })
								uni.setStorage({
									key: 'user_token',
									data: res.data.token,
									success: (res) => {
										uni.switchTab({
											url: '../home/home'
										});
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
								this.getUser()
								this.queryRole(res.data['result'].roles.roleId)
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
						console.log(res.data.data.list[0].menuIds.split(','))
						store.commit('setMenu', res.data.data.list[0].menuIds.split(','))
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
	/* button {
		background: #007AFF !important;
	} */
	/* .uni-input-placeholder {
		font-size: 32rpx !important;
	}
	
	.uni-easyinput__content {
		width: 595rpx;
		height: 96rpx;
		border-radius: 32rpx;
		font-size: 32rpx !important;
		background: #D0D3D4 !important;
		color: #9E9E9E !important;
		font-family: alyuan;
	}
	
	uni-checkbox .uni-checkbox-input {
		height: 33rpx;
		width: 33rpx;
		border-radius: 33rpx;
	} */
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
</style>