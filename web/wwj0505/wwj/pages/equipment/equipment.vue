<template>
	<view class="container">
		<view class="moreMenu" @click="showDrawer" v-if="checkNumberInArray(menu,'13')">
			<img src="@/static/icon/menu.png" alt="" class="switchImg"  />
		</view>
		<uni-drawer ref="showLeft" mode="left" :width="320">
			<view class="menu_item" @click="toAddSmart">
				<view class="item_icon">
					<img src="@/static/icon/addSmart.png" alt="" class="switchImg"  />
				</view>
				<view class="item_label">
					新增设备
				</view>
			</view>
		</uni-drawer>
		<view class="view_name">
			<text class="">设备巡检</text>
		</view>
		<view class="view_title">
			<text>{{mname}}</text>
		</view>
		<view class="equ-item" v-for="(item,index) in equList" :key="index" >
			<view class="equCard" @click="toInfo(item.deviceSn)">
						<view class="cardBox">
							<view class="card-header">
								<view class="cardName">
									{{item.displayDeviceName}}
								</view>
								<view :class="item.status == 1 ? 'cardType onLine' : 'cardType offLine'">
									{{item.status == 1 ? '在线' : '离线'}}
								</view>
							</view>
							<view class="card-body">
								<view class="body-icon">
									<img src="~@/static/icon/equicon.png" alt="" />
								</view>
								<view class="body-desc">
									<view class="body-item">
										<view class="label">
											<!-- 设备Gid： -->
										</view>
										<view class="value">
											<!-- {{item.deviceGid}} -->
										</view>
									</view>
									<view class="body-item">
										<view class="label">
											设备地址：
										</view>
										<view class="value">
											{{item.province,item.city,item.area , item.address}}
										</view>
									</view>
									<view class="body-item">
										<view class="label">
											设备编号：
										</view>
										<view class="value">
											{{item.deviceSn}}
										</view>
									</view>
								</view>
							</view>
							<view class="card-footer" >
								<view class="equ-Details">
									*点击可查看详情
								</view>
							</view>
						</view>
						
					</view>
			</view>
		</view>
		
	</view>
</template>
<script>	
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {getSmartList,getProjectList,getQueryProject } from '../../api/api.js';
	export default {
		data() {
			return {
				equList:[
					
				],
				searchFrom:{
					pageNum:1,
					pageSize:999,
					sort:'0',
					deviceTypeCode: "0103"
				},
				mname:'',
				checkNumberInArray,
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
			toInfo(deviceSn){
				setTimeout(function() {
					uni.navigateTo({
							url:'../equinfo/equinfo?deviceSn=' + deviceSn
						}
				)}, 300);
			},
			getSmart(){
				getSmartList(this.searchFrom).then(
					res => {
						console.log(res)
						if(res.statusCode == 200){
							if(res.data.code == 200){
								// this.equList = {...res.data.data.list}
								if( res.data.data.total - this.equList.length > 8 ){
									// console.log('页数增加')
									this.equList.push(...res.data.data.list)
									this.searchFrom.pageNum = this.searchFrom.pageNum + 1
								}else{
									this.equList.push(...res.data.data.list)
									let uniqueTableData = this.equList.filter((item, index, self) => {  
									    return self.findIndex(t => t.deviceGid === item.deviceGid) === index;  
									});  
									  
									// 现在 uniqueTableData 就是去重后的数组  
									this.equList = uniqueTableData;
									console.log(this.equList)
								}
							}
							
						}
						
					}
				)
			},
			showDrawer() {
				this.$refs.showLeft.open()
			},
			toAddSmart(){
				setTimeout(function() {
					uni.navigateTo({
							url:'../addSmart/addSmart'
						}
				)}, 300);
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
							this.mname = res.data.data.find(item => item.mid == this.loginUserInfo.mid).mname
						}
					}
				)
			}
		},
		onShow(){
			this.equList = []
			this.getMerchant()
			if(checkNumberInArray(this.menu,'8')){
				this.getSmart()
			}else{
				uni.showToast({
					title: `没有查看权限`
				})
			}
		},
		onReachBottom(){
			// this.searchFrom.pageNum = 1
			/*  */
			console.log('触底啦')
				
			// this.getSmart()
			
		}
		
	}
</script>

<style >
	@import url('~@/static/font/fontStyle.css');
	.container{
		background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%);
		height: 100vh;
		overflow: scroll;
	}
	.searchBox{
		margin: 25rpx;
	}
	.view_name{
		margin-left: 22rpx;
		margin-top: 22rpx;
		font-size: 39rpx;
		font-family: alihei;
	}
	.view_title{
		/* margin-top: ; */
		text-align: center;
		width: 710rpx;
		margin: 39rpx auto;
		font-family: alyuan;
	}
	.uni-input{
		/* background-color: white;
		border-radius: 25rpx;
		/* margin: 0 25rpx; */
		padding: 0 25rpx;
		height: 60rpx;
		box-shadow: 10rpx 10rpx 10rpx rgba(0,0,0,0.3);
	}
	.equ-item{
		margin: 50rpx 0;
	}
	.equCard{
		margin: 0 25rpx;
		border-radius: 25rpx;
		box-shadow: 5rpx 5rpx 10rpx rgba(0,0,0,0.3);
		overflow: hidden;
		background: #F0F0F3;
		font-family: alyuan;
	}
	.card-body{
		color: black;
		padding: 0 40rpx; 
		display: flex;
	}
	
	.card-body .body-icon img{
		width: 64rpx;
		height: 64rpx;
	}
	.body-desc{
		margin-left: 26rpx;
	}
	.card-header{
		background-color: none;
		display: flex;
		padding: 0 40rpx; 
		height: 84rpx;
		margin-top: 10rpx;
		font-size: 30rpx;
		border-radius: 25rpx 25rpx 0 0;
		justify-content: space-between;
		align-items: center;
		float: inherit;
		position: relative;
	}
	.cardName{
		margin-left: 90rpx;
		font-size: 32rpx;
	}
	.cardType{
		position: absolute;
		right: 40rpx;
		top: 40rpx;
		font-size: 30rpx;
		font-family: alyuan;
		/* color: darkgrey; */
	}
	.cardType.onLine{
		
		color: #009C1A;
	}
	.cardType.offline{
		color: #C80000;
	}
	.body-item{
		display: flex;
		color: #9E9E9E;
	}
	.equ-Description{
		height: 180rpx;
		width: 100%;
		margin-top: 20rpx;
		
	}
	.card-footer{
		background-color: none;
		padding: 0 40rpx; 
		border-radius: 0 0 25rpx 25rpx;
	}
	.equ-Details{
		height: 60rpx;
		display: flex;
		justify-content: right;
		align-items: center;
		color: #9E9E9E;
	}
	.moreMenu{
		position: fixed;
		top: 180rpx;
		width: 80rpx;
		height: 80rpx;
		background: #007AFF;
		border-radius: 0 20rpx 20rpx 0;
	}
	.moreMenu img{
		width: 60rpx;
		height: 60rpx;
		margin-top: 10rpx;
		margin-left: 5rpx;
	}
	.menu_item{
		display: flex;
		margin: 20rpx;
	}
	.item_icon img{
		width: 80rpx;
		height: 80rpx;
	}
	.item_label{
		padding-left: 25rpx;
		font-size: 35rpx;
		line-height: 80rpx;
	}
</style>
