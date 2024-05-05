<template>
	<div class="vbox">
		
		<view class="top">
			<view class="user_info">
				<img src="@/static/icon/userList.png" alt="" />
			</view>
			<view class="username">
				{{this.loginUser.name}}
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



			<view class="cell" @click="toMembers">
				<view class="cell-left">
					<!-- <image class="cell_icon" src="../../static/icon/account.png"></image> -->
					<text class="cell-text">人员管理</text>
				</view>
				<view class="cell-right">
					<image class="right-arrow" src="../../static/icon/right-arrow.png"></image>
				</view>
			</view>
			
			<view class="cell" @click="toProject">
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
		checkNumberInArray
	} from '../../assets/utils.js'
	export default {
		computed: {
			loginUser() {
				return this.$store.state.loginUser 
			},
			menu() {
				return this.$store.state.menu
			}
		},
		methods: {
			toEdit(){
				// setTimeout(function() {
					
					// }
				// , 300);
				if(checkNumberInArray(this.menu,'3')){
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


			}
		},
		onShow(){
			// console.log(checkNumberInArray(this.menu,'5'))
			// if(checkNumberInArray(this.menu,'5')){
			// 	return
			// }else{
			// 	uni.navigateBack()
			// }
		}
	}
</script>

<style scoped>
	

	.vbox {
		display: flex;
		flex-direction: column;
		align-items: center;
		background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%);
		height: 90vh;
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
