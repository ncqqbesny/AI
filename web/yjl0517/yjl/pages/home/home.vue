<template>
	<view class="container">
		<scroll-view class="" :scroll-y='true'>
			<view class="view_title">
				<text>{{loginUserInfo.nickName}}，欢迎使用应急联</text>
			</view>
			<view class="banner" @click="toView()">
				<img src="~@/static/img/BANNER.png" alt="" />
			</view>
			<view class="navigateGroup">
				<div :class="checkNumberInArray(menu,'31') ? 'navigateBtn' : 'navigateBtn disable'" @click="toOrderList">
					<text>一键应急</text>
					<view class="img">
						<img src="~@/static/homeIcon/vebr.png" alt="" style="width: 95rpx;height: 111rpx;" />
					</view>
					
				</div>
				<div class="navigateBtn disable">
					<text>上门服务</text>
					
					<view class="img">
					<img src="~@/static/homeIcon/people.png" alt="" style="width: 117rpx;height: 85rpx;" />
					</view>
				</div>
				<div :class="checkNumberInArray(menu,'12') 
				|| checkNumberInArray(menu,'13')
				|| checkNumberInArray(menu,'14')
				|| checkNumberInArray(menu,'15')
				|| checkNumberInArray(menu,'16') ? 'navigateBtn' : 'navigateBtn disable'" @click="toEquipment">
					<text>设备巡检</text>
					
					<view class="img">
						<img src="~@/static/homeIcon/fire.png" alt="" style="width: 87rpx;height: 87rpx;"/>
					</view>
					
				</div>
				<div class="navigateBtn disable">
					<text>物资巡检</text>
					<view class="img">
						<img src="~@/static/homeIcon/material.png" alt="" style="width: 87rpx;height: 76rpx;"/>
					</view>
					
				</div>

			</view>
			<view class="card">
				<view class="card_title">
					应急知识
				</view>
				<view class="card_item" v-for="(item,index) in knowboard" :key="index" @click="toHref(item)">
					<view class="item_title">
						{{'· ' + item.title}}
					</view>
					
				</view>
				
			</view>
			
			<view class="search">
				<view class="search_icon">
					<img src="~@/static/homeIcon/search3.png" alt="" />
				</view>
				<view class="serach_input">
					<input class="uni-input" confirm-type="search" placeholder=" " />
				</view>
			</view>
			<swiper class="banner2" circular :indicator-dots="true" :autoplay="true" :interval="3000" :duration="500">
				<swiper-item>
					<img src="~@/static/img/swiper.png" alt="" />
				</swiper-item>
				<swiper-item>
					<img src="~@/static/img/swiper.png" alt="" />
				</swiper-item>
				<swiper-item>
					<img src="~@/static/img/swiper.png" alt="" />
				</swiper-item>
			</swiper>
			<view class="card">
				<view class="card_title">
					好物分享
				</view>
				<view class="card_item" v-for="(item,index) in shareboard" :key="index">
					<view class="item_title">
						{{'· ' + item.title}}
					</view>
					
				</view>
				
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {
		getDoc
	} from '../../api/api.js';
	export default {
		data() {
			return {
				knowboard: [{
						title: '应急、消防、灭火等技术介绍 xXXX',
						time: '2024.4.23'
					},
					{
						title: '应急、消防、灭火等技术介绍 xXXX',
						time: '2024.4.23'
					},
					{
						title: '应急、消防、灭火等技术介绍 xXXX',
						time: '2024.4.23'
					},
					{
						title: '应急、消防、灭火等技术介绍 xXXX',
						time: '2024.4.23'
					},
					{
						title: '应急、消防、灭火等技术介绍 xXXX',
						time: '2024.4.23'
					},
				],
				shareboard: [{
						title: '好物使用分享 xXXX',
						time: '2024.4.23'
					},
					{
						title: '好物使用分享 xXXX',
						time: '2024.4.23'
					},
					{
						title: '好物使用分享 xXXX',
						time: '2024.4.23'
					},
					{
						title: '好物使用分享 xXXX',
						time: '2024.4.23'
					},
					{
						title: '好物使用分享 xXXX',
						time: '2024.4.23'
					},
				],
				checkNumberInArray,
				pages: {
						index:1,
						visible:false,
						pagePath:"/pages/emergencyList/emergencyList",
						text:"事件",
						iconPath:"/static/icon/walk.png",
						selectedIconPath:"/static/icon/walk.png"
				}
			}
		},
		computed: {
			loginUserInfo() {
				return this.$store.state.loginUserInfo 
			},
			menu() {
				return this.$store.state.menu
			}
		},
		methods: {
			toEquipment() {
				if(checkNumberInArray(this.menu,'12') 
				|| checkNumberInArray(this.menu,'13')
				|| checkNumberInArray(this.menu,'14')
				|| checkNumberInArray(this.menu,'15')
				|| checkNumberInArray(this.menu,'16')){
					setTimeout(function() {
						uni.navigateTo({
							url: '../equipment/equipment'
						})
					}, 300);
				}else{
					uni.showToast({
						icon:'error',
						title: '无权限'
					})
				}
				

			},
			toMembers() {
				setTimeout(function() {
					uni.navigateTo({
						url: '../members/members'
					})
				}, 300);
			},
			toProject() {
				setTimeout(function() {
					uni.navigateTo({
						url: '../project/project'
					})
				}, 300);
			},
			toOrderList() {
				if(checkNumberInArray(this.menu,'31')){
					setTimeout(function() {
						uni.navigateTo({
							url: '../addEvent/addEvent'
						})
					}, 300);
				}else{
					uni.showToast({
						icon:'error',
						title: '无权限'
					})
				}
			},
			getDocList(type) {
				getDoc({
					pageNum: 1,
					pageSize: 5,
					sort: 0,
					type: type
				}).then(
					res => {
						if (res.data.code == 200) {
							if (type == 1) {
								console.log(1)
								this.knowboard = res.data.data.list
								console.log(this.knowboard)
							} else {
								console.log(2)
								this.shareboard = res.data.data.list
								console.log(this.shareboard)
							}
						}
					}
				)
			},
			toHref(item) {
				// location.href = 'www.baidu.com';
				// let url = url;
				// uni.navigateTo({
				// 	url: '/pages/webView/webView?url=' + url
				// })
				uni.navigateTo({
					url: '../webView/webView?gid=' + item.gid
				});
			}
		},
		onReachBottom() {
			console.log('触底啦')

		},
		onLoad(){
			
		},
		onShow() {
			this.getDocList(1)
			this.getDocList(2)
			if(this.checkNumberInArray(this.menu,'32')){
				this.pages.visible = true
				uni.setTabBarItem(this.pages)
				console.log('显示图标')
				
			}else{
				this.pages.visible = false
				uni.setTabBarItem(this.pages)
				console.log('不显示图标')
			}
			
			
			
		},
		onPullDownRefresh() {
			// 模拟数据加载
			setTimeout(() => {
				console.log('数据加载完成');
				// 停止下拉刷新
				uni.stopPullDownRefresh();
			}, 2000);
		}
	}
</script>

<style>
	.container{
		/* background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%); */
		height: 100vh;
		overflow: scroll;
		font-family: alyuan;
	}
	.view_title{
		width: 710rpx;
		margin: 20rpx auto;
		font-weight: bold;
	}
	.banner {
		width: 666rpx;
		height: 209rpx;
		margin: 20rpx auto;
		/* border: 3rpx solid #01B6F5; */
		border-radius: 35rpx;
		overflow: hidden;
		box-shadow: 0rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	}
	.banner2 {
		width: 746rpx;
		height: 303rpx;
		margin: 20rpx auto;
		/* border: 3rpx solid #01B6F5; */
		border-radius: 35rpx;
		overflow: hidden;
		box-shadow: 0rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	}
	.banner img {
		width: 666rpx;
		height: 209rpx;
	}
	.banner2 img{
		width: 746rpx;
		height: 303rpx;
	}
	.navigateGroup {
		display: flex;
		flex-wrap: wrap;
		width: 666rpx;
		/* height: 126rpx; */
		margin: 20rpx auto;
		justify-content: space-between;
		
	}

	

	.navigateGroup .navigateBtn {
		display: flex;
		flex-direction: column;
		width: 313rpx;
		font-weight: bold;
		height: 261rpx;
		margin-bottom: 21rpx;
		font-size: 39rpx;
		background-color: #F0F0F3;
		border-radius: 35rpx;
		box-shadow: 0rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	}
	.navigateGroup .navigateBtn.disable{
		filter: grayscale(1);
		color:#9E9E9E;
	}
	.navigateGroup .navigateBtn .img{
		display: flex;
		justify-content: flex-end;
		margin-top: 35rpx;
		margin-right: 23rpx;
	}

	

	.navigateGroup .navigateBtn text {
		letter-spacing: 5rpx;
		margin-top: 31rpx;
		margin-left: 34rpx;
	}

	.card {
		width: 666rpx;
		margin: 20rpx auto;
		background: #fff;
		border-radius: 25rpx;
		box-shadow: 0rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	}

	.card_title {
		color: #87735F;
		line-height: 60rpx;
		height: 60rpx;
		border-radius: 25rpx 25rpx 0 0;
		font-weight: bold;
		padding-left: 25rpx;
	}

	.card_item {
		margin: 0 20rpx;
		display: flex;
		justify-content: space-between;
		line-height: 73rpx;
		height: 73rpx;
		width: 556rpx;
		border-bottom: 2rpx solid #A19284;
	}

	.no_item {
		line-height: 50rpx;
		height: 50rpx;
		width: 700rpx;
		text-align: center;
	}

	.card_item .item_title {
		width: 481rpx;
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
		font-size: 23rpx;
	}
	.card .card_item:last-child{
		border: none;
	}
	
	.search {
		display: flex;
		background-color: #fff;
		width: 730rpx;
		height: 95rpx;
		margin: 20rpx auto;
		border-radius: 32rpx;
		align-items: center;
	}

	.search .search_icon {
		width: 85rpx;
		height: 85rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.search .search_icon img {
		width: 47rpx;
		height: 61rpx;
	}

	.search .serach_input {
		background: #F1F2F6;
		padding: 0 3rpx;
		margin: 0 7rpx;
		border-radius: 32rpx;
	}
	.search >>>.uni-input{
		width: 631rpx;
		height: 78rpx;
	}
	.search .serach_input>>>.uni-input-wrapper {
		
		font-size: 20rpx;
		padding-left: 20rpx;
	}

	.search .serach_input>>>.input-placeholder {
		font-size: 20rpx;
		color: #000;
		padding-left: 20rpx;
	}
</style>