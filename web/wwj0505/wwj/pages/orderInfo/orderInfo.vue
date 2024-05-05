<template>
	<view>
		<view class="from">
			<view class="from_item">
				<view class="item_label">
					<text>应急单号</text>
				</view>
				<view class="item_value">
					<text>{{formData.orderNumber}}</text>
				</view>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>申请人员</text>
				</view>
				<view class="item_value">
					<text>{{formData.applyUser}}</text>
				</view>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>申请人员姓名</text>
				</view>
				<view class="item_value">
					<text>{{formData.applyName}}</text>
				</view>
			</view>

			<view class="from_item" v-if="formData.status >= 2">
				<view class="item_label">
					<text>申请时间</text>
				</view>
				<view class="item_value">
					<text>{{formData.applyTime}}</text>
				</view>
			</view>
			<view class="from_item" v-if="formData.status >= 2">
				<view class="item_label">
					<text>审核人员</text>
				</view>
				<view class="item_value">
					<text>{{formData.checkUser}}</text>
				</view>
			</view>
			<view class="from_item" v-if="formData.status >= 2">
				<view class="item_label">
					<text>审核人员姓名</text>
				</view>
				<view class="item_value">
					<text>{{formData.checkName}}</text>
				</view>
			</view>

			<view class="from_item">
				<view class="item_label">
					<text>审核时间</text>
				</view>
				<view class="item_value">
					<text>{{formData.checkTime}}</text>
				</view>
			</view>

			<view class="from_item">
				<view class="item_label">
					<text>应急理由</text>
				</view>
				<view class="item_value">
					<text v-show="formData.status != 0">{{formData.yjDesc}}</text>
					<uni-easyinput v-show="formData.status == 0" type="textarea" v-model="formData.yjDesc"
						placeholder="请说明应急理由" />
				</view>
			</view>
			<view class="from_item" v-if='formData.urls != ""'>
				<view class="item_label">
					<text>应急图片</text>
				</view>
				<uni-file-picker readonly :value="imgValue" file-mediatype="image">
				</uni-file-picker>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>物资数量</text>
					<text v-show="formData.status != 0">:{{formData.number}}</text>
				</view>
				<view class="item_value">
					<uni-number-box v-show="formData.status == 0" v-model="formData.number" :min='1'></uni-number-box>
				</view>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>备注信息</text>
				</view>
				<view class="item_value">
					<text v-show="formData.status != 0">{{formData.remark}}</text>
					<uni-easyinput v-show="formData.status == 0" type="textarea" v-model="formData.remark"
						placeholder="请填写备注信息" />
				</view>
			</view>
			<view class="from_item" v-show="formData.status != 2 && formData.status != 0" >
				<view class="item_label" >
					<text>驳回原因</text>
				</view>
				<view class="item_value">
					<text v-show="formData.rejectStatus == 1">{{formData.rejectDesc}}</text>
					<uni-easyinput v-show="formData.status == 1 && formData.rejectStatus == 0" type="textarea" v-model="formData.rejectDesc"
						placeholder="请填写驳回原因" />
				</view>
			</view>
			<view class="from_item" v-if="formData.status == 0 ">
				<!-- <text>{{formData.applyUser}}/{{loginUserInfo.username}}</text> -->
				<view class="btn default" @click="delAlert()" v-if="formData.applyUser == loginUserInfo.username">
					删除草稿
				</view>
				<view class="btn primary" @click="changeStatus(1)" v-if="checkNumberInArray(menu,'9')">
					提交申请单
				</view>
			</view>
			<view class="from_item" v-if="formData.status == 1 && checkNumberInArray(menu,'10')">
				<view class="btn warn" @click="changeStatus(0)" v-show="formData.rejectDesc">
					驳回审核
				</view>
				<view class="btn primary" @click="changeStatus(2)">
					通过审核
				</view>
			</view>
			<view class="from_item" v-if="formData.status == 2 && formData.type == 1 && checkNumberInArray(menu,'11')">
				<view class="btn primary" @click="changeStatus(3)">
					去处理
				</view>
			</view>
			<view class="from_item" v-if="formData.status == 3 && formData.type != 1 && formData.extendOrderNumber == null && checkNumberInArray(menu,'9')">
				<!-- <text>{{formData.applyUser}}/{{loginUserInfo.username}}</text> -->
				<!--  -->
				<view class="btn warn" @click="toAdd(formData.orderNumber,3)">
					申请报废
				</view>
				<view class="btn primary" @click="toAdd(formData.orderNumber,2)">
					申请归还
				</view>
			</view>
			<view class="from_item" v-if="formData.status == 2 && formData.type != 1 && formData.extendOrderNumber != null && checkNumberInArray(menu,'9')">
				<!-- <text>{{formData.applyUser}}/{{loginUserInfo.username}}</text> -->
				<!--  -->
				<view class="btn primary" @click="changeStatus(3)" v-show="formData.type == 3">
					报废完成
				</view>
				<view class="btn primary" @click="changeStatus(3)" v-show="formData.type == 2">
					归还完成
				</view>
			</view>
		</view>
		<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog type="warn" cancelText="取消" confirmText="删除" title="通知" content="确认删除草稿吗?"
				@confirm="dialogConfirm" @close="dialogClose"></uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {
		saveOrder,
		delOrder
	} from '../../api/api.js';
	export default {
		data() {
			return {
				formData: {

				},
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
			del() {

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
				console.log(orderNumber, type)
				setTimeout(function() {
					uni.redirectTo({
						url: '../addOrder/addOrder?type=' + type + '&orderNumber=' + orderNumber
					})
				}, 300);
			}
		},
		onShow() {
			this.formData = uni.getStorageSync('orderItem')
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
</style>