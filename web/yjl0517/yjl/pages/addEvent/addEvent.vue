<template>
	<view class="container">
		<view class="view_name">
			<text class="">事件编号：{{mname}}{{nowDate}}</text>
		</view>
		<view class="map" @click="openMap">
			<map 
				style="width: 100%; height: 485rpx;" 
				:latitude="latitude" 
				:longitude="longitude" 
				:markers="covers"
			>
			</map>
		</view>
		<view class="btn" @click="openMap">
			选择地点
		</view>
		<view class="address">
			<text>事件地址：</text>
			<uni-easyinput disableColor v-model="data.address" placeholder="添加详细地址"  :inputBorder="false" :maxlength='20'  type="textarea" autoHeight />
		</view>
		<view class="remark">
			<text>事件备注：</text>
			<uni-easyinput disableColor v-model="data.remark" placeholder="设备备注"  :inputBorder="false" :maxlength='20'  type="textarea" autoHeight />
		</view>
		<view class="footer">
			<view class="footet_item">
				<view class="item_info">
					当前待命应急人员：{{peopleNumer}}人
				</view>
				<view class="item_btn" @click="showAlert">
					发起应急指令
				</view>
				<uni-popup ref="alertAdd" type="dialog">
					<uni-popup-dialog type="info" cancelText="关闭" confirmText="确认" title="通知" content="确认发起应急指令?" @confirm="save"
						@close="dialogClose"></uni-popup-dialog>
				</uni-popup>
			</view>
		</view>
	</view>
	
</template>

<script>	
	import {
		checkNumberInArray,
	} from '../../assets/utils.js'
	import {findEvent,getQueryProject,saveEvent ,
		getSendMsgPhone  } from '../../api/api.js';
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
				nowDate:'',
				checkNumberInArray,
				peopleNumer:2,
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
			openMap(){
				console.log(123456)
				// this.covers.push({
				// 	id:0,
				// 	latitude: 30.312919,
				// 	longitude: 120.066184,
				// 	iconPath: '/static/icon/makers.png'
				// })
				let that = this
				uni.chooseLocation({
					success: function (res) {
						that.latitude=res.latitude
						that.longitude=res.longitude
						that.covers = [{
							id:0,
							latitude: res.latitude,
							longitude: res.longitude,
							iconPath: '/static/icon/makers.png'
						}]
						that.data.address = res.address + res.name
						console.log('位置名称：' + res.name);
						console.log('详细地址：' + res.address);
						console.log('纬度：' + res.latitude);
						console.log('经度：' + res.longitude);
					}
				});
				console.log(this.covers)
			},
			showAlert(){
				this.$refs.alertAdd.open()
			},
			save(){
				let data = {...this.data}
				data.latitude = this.latitude,
				data.longitude = this.longitude,
				data.code = this.mname + this.nowDate
				data.optUsername = this.loginUserInfo.name
				data.title = this.data.remark
				data.mid = this.loginUserInfo.mid
				console.log(data)
				saveEvent(data).then(
					res =>{
						if(res.data.code == 200){
							
							uni.showToast({
								title: `应急指令已发送`
							})
							
							uni.navigateBack()
						}else{
							
							return uni.showToast({
								title: res.data.msg,
								icon: 'error',
							})
							
						}
					}
				)
			},
			getMerchant(){
				getQueryProject(
				{
					MId:this.loginUserInfo.mid
				}
				).then(
					res=>{
						// console.log('接口2',)
						if(res.data.code == 200){
							// this.From = {...res.data.data[0]}
							if(res.data.data.find(item => item.mid == this.loginUserInfo.mid)){
								this.mname = res.data.data.find(item => item.mid == this.loginUserInfo.mid).mname
							}
							console.log(this.mname)
						}
					}
				)
			},
			getPeopleNumber(){
				getSendMsgPhone().then(
					res => {
						if(res.data.code == 200){
							console.log(res.data)
							this.peopleNumer = res.data.data
						}
					}
				)
			}
		},
		onShow(){
			const now = new Date();
			const year = now.getFullYear();  
			const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，所以需要加1  
			const day = String(now.getDate()).padStart(2, '0');  
			const hours = String(now.getHours()).padStart(2, '0');  
			const minutes = String(now.getMinutes()).padStart(2, '0'); 
			const seconds = String(now.getSeconds()).padStart(2, '0');
			this.nowDate = `${year}${month}${day}${hours}${minutes}${seconds}`;
			this.getMerchant()
			this.getPeopleNumber()
			// console.log(this.loginUserInfo)
		},
		onReachBottom(){
			
			console.log('触底啦')
				
		
			
		}
		
	}
</script>

<style >
	@import url('~@/static/font/fontStyle.css');
	.container{
		min-height: 100vh;
		padding-bottom: 45rpx;
	}
	.container::before{
		 content: "";
		display: table;
		height: 0;
	}
	.container::before{
		 content: "";
		display: table;
		height: 0;
	}
	.searchBox{
		margin: 25rpx;
	}
	.view_name{
		margin-left: 22rpx;
		margin-top: 22rpx;
		font-size: 39rpx;
		font-family: alihei;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.map{
		width: 630rpx;
		margin: 0 auto;/* 容器 6 */
		border-radius: 35rpx;
		margin-top: 68rpx;
		overflow: hidden;
		box-shadow: inset 0px 3rpx 3rpx 5px rgba(0, 0, 0, 0.3);
	}
	.btn{
		margin: 25rpx auto;
		width: 250rpx;
		display: flex;
		justify-content: center;
		height: 80rpx;
		align-items: center;
		background-color: #F5F4F6;
		box-shadow: 10rpx 0 10rpx rgba(0, 0, 0, 0.3);
		border-radius: 80rpx;
		font-family: alyuan;
		transition: 0.1s;
	}
	.btn:active {
		background-color: rgba(0, 0, 0, 0.2);
	}
	.address{
		margin: 0 auto;
		width: 630rpx;
		height: 141rpx;
		border-radius: 35rpx;
		overflow: hidden;
		background: #F0F0F3;
		font-family: alyuan;
		padding: 10rpx 14rpx;
		font-size: 27rpx;
		line-height: 40rpx;
		box-shadow: inset 0px 3rpx 3rpx rgba(0, 0, 0, 0.3);
	}
	
	.remark{
		margin: 23rpx auto 250rpx;
		width: 630rpx;
		height: 234px;
		overflow: hidden;
		border-radius: 35rpx;
		font-family: alyuan;
		font-size: 27rpx;
		padding: 10rpx 14rpx;
		line-height: 40rpx;
		background: #F0F0F3;
		box-shadow: inset 0px 3rpx 3rpx rgba(0, 0, 0, 0.3);
	}
	.footer{
		border-radius: 35rpx 35rpx 0 0;
		position: fixed;
		bottom: 0;
		width: 750rpx;
		height: 210rpx;
		background-color: #F5F4F6;
		box-shadow: 0 -10rpx 10rpx rgba(0, 0, 0, 0.3);
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.footet_item{
		width: 595rpx;
		height: 108rpx;
		border-radius: 54rpx;
		background: #005F7A;
		display: flex;
	}
	.item_info{
		width: 315rpx;
		height: 108rpx;
		line-height: 108rpx;
		text-align: center;
		font-size: 25rpx;
		color: #F0F0F3;
		font-family: alyuan;
	}
	.item_btn{
		width: 280rpx;
		height: 108rpx;
		border-radius: 54rpx;
		background-color: #87735F;
		text-align: center;
		line-height: 108rpx;
		font-size: 32rpx;
		xt-align: center;
		color: #F0F0F3;
		transition: 0.1s;
		}
	.item_btn:active {
		background-color: rgba(0, 0, 0, 0.2);
	}
	>>>.uni-easyinput__content{
		font-size: 27rpx !important;
		background: none !important;
		color: #9E9E9E !important;
		font-family: alyuan;
	}
	.uni-easyinput__content{
		font-size: 27rpx !important;
		background: none !important;
		color: #9E9E9E !important;
		font-family: alyuan;
	}
</style>
