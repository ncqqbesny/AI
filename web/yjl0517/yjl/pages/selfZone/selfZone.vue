<template>
	<div class="vbox">
		
		<view class="top">
			<view class="user_info">
				<img src="@/static/icon/userList.png" alt="" />
			</view>
			<view class="username">
				{{loginUserInfo.nickName}}
			</view>
		</view>
		<view class="middle">
			<view class="mname">
				{{mname}}
			</view>
		</view>
		<!-- 下半部分 -->
		<view class="index">
			<view class="cell" @click="toEdit()">
				<view class="cell-left">
					<!-- <image class="cell_icon" src="../../static/icon/account.png"></image> -->
					<text class="cell-text">修改密码</text>
				</view>
				<view class="cell-right">
					<image class="right-arrow" src="../../static/icon/right-arrow.png"></image>
				</view>
			</view>

			<view class="cell"  @click="dialogToggle()">
				<view class="cell-left">
					<!-- <image class="cell_icon" src="../../static/icon/account.png"></image> -->
					<text class="cell-text">当前版本</text>
				</view>
				<view class="cell-right">
					<image class="right-arrow" src="../../static/icon/right-arrow.png"></image>
				</view>
			</view>



			<view class="cell" @click="toMembers" v-show="
			checkNumberInArray(menu,'21') 
			|| checkNumberInArray(menu,'22') 
			|| checkNumberInArray(menu,'23') 
			|| checkNumberInArray(menu,'24')
			 
			 || checkNumberInArray(menu,'26')
			 
			 || checkNumberInArray(menu,'27')
			 
			 || checkNumberInArray(menu,'28')
			 
			 || checkNumberInArray(menu,'29')
			 ">
				<view class="cell-left">
					<!-- <image class="cell_icon" src="../../static/icon/account.png"></image> -->
					<text class="cell-text">人员管理</text>
				</view>
				<view class="cell-right">
					<image class="right-arrow" src="../../static/icon/right-arrow.png"></image>
				</view>
			</view>
			
			<view class="cell" @click="toProject" v-show="
			 checkNumberInArray(menu,'17')
			
			|| checkNumberInArray(menu,'18')
			
			|| checkNumberInArray(menu,'19')
			
			|| checkNumberInArray(menu,'20')">
				<view class="cell-left">
					<!-- <image class="cell_icon" src="../../static/icon/account.png"></image> -->
					<text class="cell-text">项目管理</text>
				</view>
				<view class="cell-right">
					<image class="right-arrow" src="../../static/icon/right-arrow.png"></image>
				</view>
			</view>
		</view>

		<!-- 退出登录 -->
		<view class="logout" style="width:80%;margin-top: 50upx;">
			<button type="warn" @click="logout">退出登录</button>
		</view>
		
		<view>
			<uni-popup ref="alertDialog" type="dialog">
				<uni-popup-dialog type="info" cancelText="关闭" confirmText="同意" title="通知" content="当前版本1.0" ></uni-popup-dialog>
			</uni-popup>
		</view>
	</div>
</template>

<script>
	import {
		checkNumberInArray,
	} from '../../assets/utils.js'
	import{
		getQueryProject
	} from '../../api/api.js'
	import store from '@/store/index.js'; //需要引入store
	export default {
		data() {
			return {
				mname: '',
				checkNumberInArray 
			}
		},
		computed: {
			loginUser() {
				return this.$store.state.loginUser 
			},
			loginUserInfo() {
				return this.$store.state.loginUserInfo 
			},
			menu() {
				return this.$store.state.menu
			}
		},
		methods: {
			toEdit(){
				if(checkNumberInArray(this.menu,'6')){
					setTimeout(function() {
						uni.navigateTo({
								url:'../editPassWord/editPassWord'
							}
						)}, 300);
				}else{
					uni.showToast({
						icon:'error',
						title: '无权限'
					})
				}
			},
			dialogToggle() {
				this.$refs.alertDialog.open()
			},
			toMembers(){
				
					setTimeout(function() {
						uni.navigateTo({
								url:'../members/members'
							}
					)}, 300);
				
			},
			toProject(){
				
					setTimeout(function() {
						uni.navigateTo({
								url:'../project/project'
							}
					)}, 300);
				
					
				
			},
			logout() {

				uni.showModal({
					title: '提示',
					content: '确认退出登陆？',
					success: function(res) {
						if (res.confirm) {
							store.commit('refState')
							uni.removeStorage({
								key: 'user_token',
								success(res) {
									uni.redirectTo({
										url: '/pages/index/index'
									})
								}
							})
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});


			},
			getMerchant(){
				getQueryProject(
				{
					MId:this.loginUserInfo.mid
				}
				).then(
					res=>{
						console.log('接口2',res)
						if(res.data.code == 200){
							// this.From = {...res.data.data[0]}
							if(res.data.data.find(item => item.mid == this.loginUserInfo.mid)){
								this.mname = res.data.data.find(item => item.mid == this.loginUserInfo.mid).mname
							}
							
						}
					}
				)
			}
		},
		onShow(){
			// console.log(checkNumberInArray(this.menu,'5'))
			// if(checkNumberInArray(this.menu,'5')){
			// 	return
			// }else{
			// 	uni.navigateBack()
			// }
			this.getMerchant()
			console.log(this.loginUser)
		}
	}
</script>

<style scoped>
	

	.vbox {
		display: flex;
		flex-direction: column;
		align-items: center;
		/* background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%); */
		width: 750rpx;
		overflow-x: hidden;
		
		height: 90vh;
		font-family: alyuan;
	}
	
	.vbox .top{
		width: 750rpx;
		background: #005F7A;
		display: flex;
		color:#F0F0F3;
		font-size: 31.25rpx;
		font-family: alyuan;
		padding: 62rpx 0;
	}
	
	.vbox .user_info img{
		width:  83rpx;
		height: 78rpx;
	}
	.vbox .user_info{
		margin-left: 39rpx;
		margin-right: 35rpx;
	}

	.vbox .username{
		height: 78rpx;
		line-height: 121rpx;
		text-shadow: 10rpx 10rpx 8rpx rgba(0, 0, 0, 0.3);
	}
	.middle{
		width: 750rpx;
		margin-left:154rpx;
		margin-top: 40rpx;
		font-size: 31rpx;
		text-align: start;
		color: #F0F0F3;
	}
	
	.index {
		display: flex;
		flex-direction: column;
		width: 100%;
		margin-top: 195rpx;
		/* background-color: white; */
		/* border-top: 1px solid #cccccc; */
	}
	.cell {
		display: flex;
		align-items: center;
		/* border-bottom: 1px solid #ccc; */
		background: #F0F0F3;
		border-radius: 0 17.5rpx 17.5rpx 0;
		margin-bottom: 11rpx;
		box-shadow: 10rpx 10rpx 10rpx rgba(0,0,0,0.3);
		height: 90upx;
		font-family: alyuan;
		font-size: 32rpx;
		align-items: center;
		justify-content: space-between;
	}

	.cell:active {
		background-color: #F5F5F5;
		color: white;
		/* box-shadow: 1upx 1upx 35upx #ccc; */
	}

	.cell-left {
		display: flex;
		align-items: center;
		margin-left: 40rpx;
	}

	.cell-text {
		/* margin-left: 25upx; */
	}

	.cell-right {
		margin-right: 45upx;
		height: 28upx;
	}

	.cell_icon {
		width: 40upx;
		height: 40upx;
		height: 40upx;
	}

	.right-arrow {
		color: #aaa;
		width: 15upx;
		height: 28upx;
	}
</style>
