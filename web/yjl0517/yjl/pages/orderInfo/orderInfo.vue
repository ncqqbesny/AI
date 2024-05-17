<template>
	<view class="container">
		<view class="view_title">
			<text class="">{{formData.type == 1 ? '物资调用申请' : '物资归还申请'}} </text>
		</view>
		<view class="eventInfo">
			<view class="event_id">
				事件编号：{{code}}
			</view>
			<view class="event_address">
				<view class="title">
					处理地址：
				</view>
				{{eventData.address}}
			</view>
			<view class="evenet_remark">
				事件备注：{{eventData.remark}}
			</view>
			<view class="evenet_desc">
				物资取用说明:{{formData.yjDesc}}
			</view>
			<view class="event_img">
				<uni-file-picker readonly :value="imgValue" file-mediatype="image">
				</uni-file-picker>
			</view>
			<view class="event_footer">
				{{ StatustoString(formData.status,formData.type) }}
			</view>
		</view>
		<view class="btnGroup">
			<view class="btn toAdd" @click="toAdd(formData.orderNumber,2)" v-if="formData.status == 2  && checkNumberInArray(menu,'9')" >
				发起物资归还申请
			</view>
			<view class="btn toList" @click="toList(code,'2')" v-if="formData.status == 2 && checkNumberInArray(menu,'8')">
				查看物资归还申请
			</view>
			<view class="btn toAudit" v-if="formData.status == 1 && checkNumberInArray(menu,'10')">
				<view class="notapproved" @click="showNot()">
					驳回
				</view>
				<view class="approved" @click="showApproved()">
					批准
				</view>
				<uni-popup ref="alertNot" type="dialog">
					<uni-popup-dialog type="info" cancelText="取消" confirmText="确认" title="通知" content="确认驳回此条申请?" @confirm="changeStatus(3)"
						@close="dialogClose"></uni-popup-dialog>
				</uni-popup>
				<uni-popup ref="alertApproved" type="dialog">
					<uni-popup-dialog type="success" cancelText="取消" confirmText="确认" title="通知" content="确认同意此条申请?" @confirm="changeStatus(2)"
						@close="dialogClose"></uni-popup-dialog>
				</uni-popup>
			</view>
		</view>
		
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {
		saveOrder,
		delOrder,
		findEventInfo
	} from '../../api/api.js';
	export default {
		data() {
			return {
				formData: {

				},
				code:'',
				address:'',
				eventData:{},
				imgValue: [],
				checkNumberInArray,
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
			
			showNot(){
				this.$refs.alertNot.open()
			},
			showApproved(){
				this.$refs.alertApproved.open()
			},
			StatustoString(status, type) {
				let text = ''
				if (type == 1) {
					switch (status) {
						case 0:
							text = '待提交'
							break;
						case 1:
							text = '待审核'
							break
						case 2:
							text = '已审核'
							break;
						case 3:
							text = '已驳回'
							break
						case 4:
							text = '已完成'
							break
						default:
							text = ''
							break;
					}
				} else {
					switch (status) {
						case 0:
							text = '待提交'
							break;
						case 1:
							text = '待审核'
							break
						case 2:
							text = '已审核'
							break;
						case 3:
							text = '已驳回'
							break
						default:
							text = '已完成'
							break;
					}
				}
			
				return text
			},
			changeStatus(status) {
				let data = {}
				data = {
					...this.formData
				}
				
				const now = new Date();
				const year = now.getFullYear();
				const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，所以需要加1  
				const day = String(now.getDate()).padStart(2, '0');
				const hours = String(now.getHours()).padStart(2, '0');
				const minutes = String(now.getMinutes()).padStart(2, '0');
				const seconds = String(now.getSeconds()).padStart(2, '0');
				if (status == 0) {
					data.rejectName = this.loginUserInfo.name
					data.rejectUser = this.loginUserInfo.username
					data.rejectTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
					data.rejectStatus = 1
				}else if(status == 1){
					data.rejectStatus = 0
					data.rejectDesc = null
					data.applyName = this.loginUserInfo.name
					data.applyUser = this.loginUserInfo.username
					data.applyTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
				} else if(status == 2){
					data.rejectStatus = 0
					data.rejectDesc = null
					data.checkName = this.loginUserInfo.name
					data.checkUser = this.loginUserInfo.username
					data.checkTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
				}
				
				data.status = status
				
				saveOrder(data).then(
					res => {
						if (res.data.code == 200) {
							if (status == 0) {
								uni.showToast({
									title: `草稿已保存`
								})
							} else {
								uni.showToast({
									title: `表单已上传`
								})
							}
							uni.navigateBack()
						}else{
							return uni.showToast({
								title: res.data.msg,
								icon: 'none'
							})
						} 
						if (res.data.status == 400) {
							return uni.showToast({
								title: res.data.msg,
								icon: 'none'
							})
						}
					}
				)
			},
			delAlert() {
				this.$refs.alertDialog.open()
				// delOrder(this.formData.orderNumber).then()
			},
			dialogConfirm() {
				console.log('点击确认')
				// "BF202404251131777793"
				delOrder([this.formData.orderNumber]).then(
					res => {
						if (res.data.code == 200) {
							console.log(res)
							uni.showToast({
								title: `草稿已删除`
							})
							uni.navigateBack()
						} else {
							uni.showToast({
								icon: 'error',
								title: res.data.msg
							})
						}
					}
				)
			},
			dialogClose() {
				console.log('点击关闭')
			},
			toAdd(orderNumber, type) {
				let code = this.code
				console.log(orderNumber, type)
				setTimeout(function() {
					uni.redirectTo({
						url: '../addOrder/addOrder?type=' + type + '&code=' + code + '&orderNumber=' + orderNumber
					})
				}, 300);
			},
			findData(){
				findEventInfo(
					{code:this.code}
				).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
							this.eventData = res.data.data[0]
						}
					}
				)
			},
			toList(code,type){
				setTimeout(function() {
					uni.navigateTo({
							url:'../orderList/orderList?code=' + code + '&type=' + type
						}
				)}, 300);
			}
		},
		onLoad(options){
			if(options.code){
				this.code = options.code
				this.address = options.address
			}	
		},
		onShow() {
			this.formData = uni.getStorageSync('orderItem')
			this.findData()
			console.log(this.formData)
			this.imgValue = []
			let array
			if (this.formData.urls != '') {
				array = this.formData.urls.split(",")
				console.log(this.imgValue)
				this.imgValue = array.map(url => ({
					url,
					name: "",
					extname: ""
				}));
			}

		},

	}
</script>

<style>
	.container{
		min-height: 100vh;
		font-family: alyuan;
	}
	.container::before{
		content: "";
		display: table;
		height: 0;
	}
	.view_title{
		width: 706rpx;
		margin: 22rpx auto 0;
		font-size: 39rpx;
		font-weight: bold;
	}
	.eventInfo{
		position: relative;
		background: #fff;
		width: 702rpx;
		min-height: 911rpx;
		margin: 61rpx auto 0;
		font-size: 23rpx;
		color: #9E9E9E;
		font-weight: bold;
		border-radius: 39rpx;
		box-shadow: 0rpx 0rpx 10rpx rgba(0,0,0,0.3);
		padding-bottom: 60rpx;
	}
	.eventInfo::before{
		content: "";
		display: table;
		height: 0;
	}
	.eventInfo>view{
		margin-left: 35rpx;
		margin-right: 35rpx;
	}
	.eventInfo .event_id{
		margin-top: 24rpx;
	}
	.event_address,.evenet_remark{
		margin-top: 42rpx;
	}
	.evenet_desc{
		margin-top: 85rpx;
	}
	.event_img{
		margin-top: 85rpx;
	}
	.eventInfo .event_id{
		font-size: 30rpx;
		color: #000;
	}
	.event_footer{
		position: absolute;
		bottom: 0;
		width: 668rpx;
		color: #87735F;
		text-align: center;
		margin-bottom: 20rpx;
	}
	.from {
		width: 710rpx;
		margin: 20rpx;
		background: #fff;
		padding-bottom: 20rpx;
	}

	.from_item {
		margin: 10rpx;
	}

	.from_item .btn {
		border-radius: 10rpx;
		text-align: center;
		height: 80rpx;
		line-height: 80rpx;
		margin: 10rpx;
	}

	.from_item .btn.default {
		background: #fff;
		border: 1rpx solid #ccc;
	}

	.from_item .btn.primary {
		color: #fff;
		background: #007AFF;
		border: 1rpx solid #ccc;
	}

	.from_item .btn.warn {
		color: #fff;
		background: #E64340;
		border: 1rpx solid #ccc;
	}

	.item_label {
		height: 80rpx;
		line-height: 80rpx;

	}

	.item_value>text {
		text-indent: 2em;
		margin-left: 2em;
	}
	.btnGroup{
		width: 702rpx;
		margin: 150rpx auto 0;
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
	.btn.toAudit{
		background: #C80000;
		display: flex;
		justify-content: space-between;
	}
	.btn.toAudit>view{
		text-align: center;
	}
	.btn.toAudit .notapproved{
		
			width: 318rpx;
	}
	.btn.toAudit .approved{
		width: 384rpx;
		background: #009C1A;
		border-radius: 59rpx;
	}
</style>