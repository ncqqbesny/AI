<template>
	  <scroll-view class="container" :scroll-y='true'>
			<view class="banner" @click="toView()">
				<img src="~@/static/img/BANNER.png" alt="" />
			</view>
			<view class="navigateGroup">
				<div class="navigateBtn" @click="toOrderList">
					<img src="~@/static/homeIcon/vebr.png" alt="" />
					<text>一键应急</text>
				</div>
				<div class="navigateBtn">
					<img src="~@/static/homeIcon/warn.png" alt="" />
					<text>消防预警</text>
				</div>
				<div class="navigateBtn">
					<img src="~@/static/homeIcon/people.png" alt="" />
					<text>上门服务</text>
				</div>
				<div class="navigateBtn" @click="toEquipment">
					<img src="~@/static/homeIcon/fire.png" alt="" />
					<text>设备巡检</text>
				</div>
				<div class="navigateBtn" >
					<img src="~@/static/homeIcon/material.png" alt="" />
					<text>物资巡检</text>
				</div>

			</view>
			<view class="card">
				<view class="card_title">
					应急知识墙
				</view>
				<view class="card_item" v-for="(item,index) in knowboard" :key="index" @click="toHref(item)">
					<view class="item_title">
						{{'· ' + item.title}}
					</view>
					<view class="item_time">
						{{ item.issueTime}}
					</view>
				</view>
				<view class="no_item" v-show="shareboard.length < 5">
					无更多知识
				</view>
			</view>
			<swiper class="banner" circular :indicator-dots="true" :autoplay="true" :interval="3000"
				:duration="500">
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
			<view class="search">
				<view class="search_icon">
					<img src="~@/static/homeIcon/search3.png" alt="" />
				</view>
				<view class="serach_input">
					<input class="uni-input" confirm-type="search" placeholder="搜索" />
				</view>
			</view>
			<view class="card">
				<view class="card_title">
					好物分享
				</view>
				<view class="card_item" v-for="(item,index) in shareboard" :key="index">
					<view class="item_title">
						{{'· ' + item.title}}
					</view>
					<view class="item_time">
						{{ item.time}}
					</view>
				</view>
				<view class="no_item" v-show="shareboard.length < 5">
					无更多分享
				</view>
			</view>
	  </scroll-view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import { getDoc } from '../../api/api.js';
	export default {
		data() {
			return {
				knowboard:[
					{
						title:'应急、消防、灭火等技术介绍 xXXX',
						time:'2024.4.23'
					},
					{
						title:'应急、消防、灭火等技术介绍 xXXX',
						time:'2024.4.23'
					},
					{
						title:'应急、消防、灭火等技术介绍 xXXX',
						time:'2024.4.23'
					},
					{
						title:'应急、消防、灭火等技术介绍 xXXX',
						time:'2024.4.23'
					},
					{
						title:'应急、消防、灭火等技术介绍 xXXX',
						time:'2024.4.23'
					},
				],
				shareboard:[
					{
						title:'好物使用分享 xXXX',
						time:'2024.4.23'
					},
					{
						title:'好物使用分享 xXXX',
						time:'2024.4.23'
					},
					{
						title:'好物使用分享 xXXX',
						time:'2024.4.23'
					},
					{
						title:'好物使用分享 xXXX',
						time:'2024.4.23'
					},
					{
						title:'好物使用分享 xXXX',
						time:'2024.4.23'
					},
				]
			}
		},
		computed: {
			menu() {
				return this.$store.state.menu
			}
		},
		methods: {
			toEquipment(){
				
					setTimeout(function() {
						uni.navigateTo({
								url:'../equipment/equipment'
							}
					)}, 300);
				
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
			toOrderList(){
				setTimeout(function() {
					uni.navigateTo({
							url:'../addEvent/addEvent'
						}
				)}, 300);
			},
			getDocList(type){
				getDoc(
					{
						pageNum:1,
						pageSize:5,
						sort:0,
						type:type
					}
				).then(
					res => {
						if(res.data.code == 200){
							if(type == 1){
								console.log(1)
								this.knowboard = res.data.data.list
								console.log(this.knowboard)
							}else{
								console.log(2)
								this.shareboard = res.data.data.list
								console.log(this.shareboard)
							}
						}
					}
				)
			},
			toHref(item){
				// location.href = 'www.baidu.com';
				// let url = url;
				// uni.navigateTo({
				// 	url: '/pages/webView/webView?url=' + url
				// })
				uni.navigateTo({
					url:'../webView/webView?gid=' + item.gid
				});
			}
		},
		onReachBottom(){
			console.log('触底啦')
			
		},
		onShow(){
			this.getDocList(1)
			this.getDocList(2)
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
	.banner{
		width: 710rpx;
		height: 200rpx;
		margin: 20rpx auto;
		/* border: 3rpx solid #01B6F5; */
		border-radius: 25rpx;
	}
	.banner img{
		width: 710rpx;
		height: 200rpx;
	}
	.navigateGroup{
		color: #fff;
		display: flex;
		width: 710rpx;
		height: 126rpx;
		margin: 20rpx auto;
		justify-content: space-between;
	}
	.navigateGroup img{
		
		margin-top: 10rpx;
		width: 50rpx;
		height: 50rpx;
	}
	.navigateGroup .navigateBtn{
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 126rpx;
		height: 126rpx;
		font-size: 20rpx;
	}
	.navigateGroup > .navigateBtn:nth-child(1){
		background: #E78136;
		border-radius: 18rpx;
	}
	.navigateGroup > .navigateBtn:nth-child(2){
		/* background: #FCC103; */
		background: #A9A9A9;
		border-radius: 18rpx;
	}
	.navigateGroup > .navigateBtn:nth-child(3){
		background: #A9A9A9;
		border-radius: 18rpx;
	}
	.navigateGroup > .navigateBtn:nth-child(4){
		background: #6299D1;
		border-radius: 18rpx;
	}
	.navigateGroup > .navigateBtn:nth-child(5){
		/* background: #6FAD48; */
		background: #A9A9A9;
		border-radius: 18rpx;
	}
	.navigateGroup .navigateBtn text{
		letter-spacing:5rpx
	}
	.card{
		width: 710rpx;
		margin: 20rpx auto;
		background: #fff;
		border-radius: 25rpx;
		box-shadow: 10rpx 10rpx 10rpx rgba(0,0,0,0.1);
	}
	.card_title{
		background: #DDDDDD;
		text-align: center;
		line-height: 50rpx;
		height: 50rpx;
		border-radius: 25rpx 25rpx 0 0;
		letter-spacing: 10rpx;
	}
	.card_item{
		margin: 0 20rpx;
		display: flex;
		justify-content: space-between;
		line-height: 50rpx;
		height: 50rpx;
		width: 700rpx;
		border-bottom: 2rpx solid #F1F2F6;
	}
	.no_item{
		line-height: 50rpx;
		height: 50rpx;
		width: 700rpx;
		text-align: center;
	}
	.card_item .item_title{
		width: 550rpx;
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
	}
	.card_item .item_time{
		color: #ddd;
		width: 185rpx;
		/* text-overflow: ellipsis; */
		white-space: nowrap;
		overflow: hidden;
	}
	.search{
		display: flex;
		background-color: #fff;
		width: 710rpx;
		height: 60rpx;
		margin: 20rpx auto;
		border-radius: 12rpx;
		align-items: center;
	}
	.search .search_icon{
		width: 80rpx;
		height: 30rpx;
		display: flex;
		justify-content: center;
	}
	.search .search_icon img{
		width: 30rpx;
		height: 30rpx;
	}
	.search .serach_input{
		background: #F1F2F6;
		padding: 0 3rpx;
		border-radius: 0 10rpx 10rpx 0;
	}
	.search .serach_input >>>.uni-input-wrapper{
		width: 624rpx;
		font-size: 20rpx;
		padding-left: 20rpx;
	}
	.search .serach_input >>>.input-placeholder{
		font-size: 20rpx;
		color: #000;
		padding-left: 20rpx;
	}
</style>
