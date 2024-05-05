<template>
	<view class="container">
		<view class="view_name">
			<text class="">应急事件</text>
		</view>
		<view class="dataBox">
			<view class="dataBox_title">
				事件编号：{{code}}
			</view>
			<view class="dataBox_address">
				<view class="address_label">
					<text class="label">事件地址：</text>
					<text class="copyBtn" @click="copyAddress">复制地址</text>
				</view>
				<view class="dataBox_value">
					{{data.address}}
				</view>
			</view>
			<view class="map" @click="openMap">
				<map 
					style="width: 562rpx; height: 248rpx;" 
					:latitude="latitude" 
					:longitude="longitude" 
					:markers="covers"
				>
				</map>
			</view>
			<view class="remark">
				<text>事件备注: {{data.remark}}</text>
			</view>
		</view>
		<view class="btnGroup">
			<view class="btn toAdd" @click="toAdd(code)" v-if="checkNumberInArray(menu,'9')">
				发起物资调用申请
			</view>
			<view class="btn toList" @click="toList" v-if="checkNumberInArray(menu,'8')">
				查看物资调用申请
			</view>
		</view>
	</view>
	
</template>

<script>	
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {findEvent,getQueryProject,findEventInfo   } from '../../api/api.js';
	export default {
		data() {
			return {
				latitude: 30.312919,
				longitude: 120.066184,
				covers: [],
				data:{
					address:'',
					remark:''
				},
				mname:'',
				noeDate:'',
				extendEventNumber:'',
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
			},
			code(){
				return this.$store.state.eventCode
			}
		},
		methods: {
			findData(){
				findEventInfo(
					{code:this.code}
				).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
							this.data = res.data.data[0]
							this.latitude = this.data.latitude,
							this.longitude = this.data.longitude,
							this.covers = [{
								id:0,
								latitude: this.data.latitude,
								longitude: this.data.longitude,
								iconPath: '/static/icon/makers.png'
							}]
						}
					}
				)
			},
			toAdd(code){
				setTimeout(function() {
					uni.navigateTo({
							url:'../addOrder/addOrder?code=' + code 
						}
				)}, 300);
			},
			toList(){
				setTimeout(function() {
					uni.navigateTo({
							url:'../orderList/orderList'
						}
				)}, 300);
			}
		},
		onLoad(options){
			
		},
		onShow(){
			console.log(this.code)
			this.findData()
		},
		onReachBottom(){
			
			console.log('触底啦')
				
		
			
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
	.dataBox{
		width: 630rpx;
		height: 902rpx;
		margin: 54rpx auto;
		background: #F0F0F3;
		border-radius: 35rpx;
		overflow: hidden;
		box-shadow: 0rpx 0rpx 10rpx rgba(0,0,0,0.3);
	}
	.dataBox_title{
		margin-left: 30rpx;
		margin-top: 22rpx;
		font-size: 27rpx;
		width: 451rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.dataBox_address{
		margin-left: 30rpx;
		margin-top: 36rpx;
	}
	.map{
		width: 562rpx;
		margin: 0 auto;/* 容器 6 */
		border-radius: 35rpx;
		margin-top: 68rpx;
		overflow: hidden;
		box-shadow: 0rpx 0rpx 10rpx rgba(0,0,0,0.3);
	}
	
	.remark{
		margin-top: 30rpx;
		margin-left: 30rpx;
	}
	.btnGroup{
		width: 630rpx;
		margin: 0 auto;
	}
	.btnGroup .btn{
		height: 118rpx;
		border-radius: 59rpx;
		font-family: alyuan;
		font-size: 35rpx;
		line-height: 118rpx;
		text-align: center;
		color: #F0F0F3;
	}
	.btn.toAdd{
		background: #005F7A;
		margin-bottom: 16rpx;
	}
	.btn.toList{
		background: #87735F;
	}
</style>
