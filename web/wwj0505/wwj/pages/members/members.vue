<template>
	<view class="container">

		<view class="btnGroup">
			<view class="card-shadow">
				<view class="switchBox-large boxStyle1" @click='toPeopleList'>
					<img src="@/static/icon/people.png" alt="" class="switchImg" />
					<text class="switchText">用户管理</text>
					<view class="bgCircle1"></view>
					<view class="bgCircle2"></view>
					<view class="bgCircle3"></view>
				</view>
			</view>
		</view>

		<view class="btnGroup">
			<view class="card-shadow">
				<view class="switchBox-large boxStyle2" @click='toRoleList'>
					<img src="@/static/icon/auth.png" alt="" class="switchImg" />
					<text class="switchText">权限管理</text>
					<view class="bgCircle1"></view>
					<view class="bgCircle2"></view>
					<view class="bgCircle3"></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	export default {
		data() {
			return {

			}
		},
		computed: {
			menu() {
				return this.$store.state.menu
			}
		},

		methods:{
			toPeopleList(){
				if(checkNumberInArray(this.menu,'4')){
						setTimeout(function() {
							uni.navigateTo({
									url:'../peopleList/peopleList'
								}
						)}, 300);
					}else{
						uni.showToast({
							icon:'error',
							title: '无权限'
						})
					}
			},

			toRoleList(){
				if(checkNumberInArray(this.menu,'7')){
					setTimeout(function() {
						uni.navigateTo({
								url:'../roleList/roleList'
							}
					)}, 300);
				}else{
					uni.showToast({
						icon:'error',
						title: '无权限'
					})
				}
			}
		}
	}
</script>

<style>
	.container {
		
	}
	.btnGroup{
		width: 710rpx;
		margin: 25rpx auto;
	}
	.btnGroup .card-shadow {
		/* box-shadow: 10rpx 10rpx 10rpx rgba(0, 0, 0, 0.3); */
		width: 700rpx;
		
		border-radius: 25rpx;
	}

	.switchBox-large {
		position: relative;
		display: flex;
		justify-content: center;
		flex-direction: column;
		align-items: center;
		height: 450rpx;
		border-radius: 25rpx;
		overflow: hidden;
		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset;
		color: #fff; */
	}

	.switchBox-medium {
		position: relative;
		display: flex;
		justify-content: center;
		flex-direction: column;
		align-items: center;
		height: 225rpx;
		border-radius: 25rpx;
		overflow: hidden;
		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset;
		color: #fff; */
	}

	.switchImg {
		width: 32%;
		height: 45%;
	}

	.switchText {
		margin-top: 15rpx;
		color: #fff;
		font-size: 40rpx;
		font-family: "microsoft yahei";
	}

	.switchBox-large{
		background: #FCC103;
		border-radius: 25rpx;
		box-shadow: 5rpx 5rpx 5rpx #ccc;
		transition: .2s;
	}
	.boxStyle2{
		background: #6299D1;
	}
	.switchBox-large:active:hover {
		box-shadow: 0rpx 0rpx 25rpx rgb(0, 106, 255);
		cursor: pointer;
	}

	.switchBox-medium:active:hover {
		box-shadow: 0rpx 0rpx 25rpx rgb(0, 106, 255);
		cursor: pointer;
	}

	.bgCircle1 {
		position: absolute;
		top: -100rpx;
		left: -100rpx;
		height: 200rpx;
		width: 200rpx;
		border-radius: 200rpx;
		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset; */
	}

	.bgCircle2 {
		position: absolute;
		bottom: -150rpx;
		right: -150rpx;
		height: 300rpx;
		width: 300rpx;
		border-radius: 150rpx;

		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset; */
	}

	.bgCircle3 {
		position: absolute;
		bottom: 150rpx;
		right: 85rpx;
		height: 100rpx;
		width: 100rpx;
		border-radius: 100rpx;
		/* background-color: rgba(255, 255, 255, 0.3); */
	/* 	border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset; */
	}

	.switchBox-medium .bgCircle1 {
		position: absolute;
		top: -50rpx;
		left: -50rpx;
		height: 100rpx;
		width: 100rpx;
		border-radius: 100rpx;

		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset; */
	}

	.switchBox-medium .bgCircle2 {
		position: absolute;
		bottom: -75rpx;
		right: -75rpx;
		height: 150rpx;
		width: 150rpx;
		border-radius: 150rpx;

		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset; */
	}

	.switchBox-medium .bgCircle3 {
		position: absolute;
		bottom: 75rpx;
		right: 37.5rpx;
		height: 50rpx;
		width: 50rpx;
		border-radius: 25rpx;

	/* 	border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset; */
	}
</style>