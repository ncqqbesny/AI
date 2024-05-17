<template>
	<view class="container">
		<view class="content">
			<view class="from">
				<view class="from_item">
					<view class="item_label">
						设备编号
					</view>
					<view class="item_value">
						<uni-easyinput v-model="smartFrom.deviceSn" placeholder="请输入设备编号" class="input"/>
					</view>
				</view>
				<view class="from_item">
					<view class="item_label">
						显示名称
					</view>
					<view class="item_value">
						<uni-easyinput v-model="smartFrom.displayDeviceName" placeholder="请输入显示名称" class="input" />
					</view>
				</view>
				
				<view class="from_item">
					<view class="item_label">
						绑定项目
					</view>
					<view class="item_value">
						<uni-data-select
						     v-model="smartFrom.mid"
						     :localdata="merChantList"
						     @change="change"
							 class="select"
						   >
						</uni-data-select>
					</view>
				</view>
				<view class="from_item remark" >
					<view class="item_label">
						设备备注:
					</view>
					<view class="item_value" style="height: 200rpx; display: flex;">
						<uni-easyinput v-model="smartFrom.remark" placeholder="设备备注" type="textarea" class="textarea" />
					</view>
				</view>
			</view>
			<view class="btnGroup">
				<button type="primary" @click="showAlert()" >新增设备</button>
			</view>
			<uni-popup ref="alertAdd" type="dialog">
				<uni-popup-dialog type="info" cancelText="关闭" confirmText="确认" title="通知" content="确认提交表单?" @confirm="addSmart()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
		
	</view>
</template>

<script>
	import {setSmart,getQueryProject} from '../../api/api.js';
	export default {
		data() {
			return {
				smartFrom:{
					deviceSn:'',
					displayDeviceName:'',
					deviceSw:'',
					mid:'',
					deviceTypeCode:"0103",
					remark:''
				},
				merChantList:[]
			}
		},
		computed: {
			loginUser() {
				return this.$store.state.loginUser 
			}
		},
		methods: {
			showAlert(){
				this.$refs.alertAdd.open()
			},
			addSmart(){
				setSmart(this.smartFrom).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
							uni.showToast({
								title: `新增成功`
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
					userId:this.loginUser.id
				}
				).then(
					res=>{
						console.log(res)
						if(res.data.code == 200){
							this.merChantList = res.data.data.map(item => ({  
							    text: item.mname,  
							    value: item.mid  
							})); 
							console.log(this.merChantList)
						}
					}
				)
			},
			change(e) {
				console.log("e:", e);
			  },
		},
		onShow(){
			console.log(this.loginUser.id)
			this.getMerchant()
		}
	}
</script>

<style>
	.container{
		min-height: 100vh;
		padding-bottom: 15rpx;
	}
	.container::before{
		 content: "";
		display: table;
		height: 0;
	}
	.content{
		width: 710rpx;
		/* height: 40vh; */
		margin: 0 autp;
		background: #FFFFFF;
		display: flex;
		flex-wrap: wrap;
		border-radius: 25rpx;
		box-shadow: 5rpx 5rpx 5rpx #ccc;
		margin: 50rpx auto;
		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset;
		color: #fff; */
	}
	.from{
		padding: 25rpx;
	}
	.from_item{
		display: flex;
		margin: 15rpx;
	}
	.from_item.remark{
		display: flex;
		margin: 15rpx;
		height: 200rpx;
	}
	.from_item.remark .textarea{
		width: 500rpx;
		height: 180rpx;
	}
	.input{
		line-height: 50rpx;
		width: 500rpx;
	}
	.select{
		width: 510rpx;
	}
	
	.item_label{
		width: 125rpx;
		line-height: 60rpx;
	}
.confirmInfo{
	background: #007AFF;
	color: #fff;
	border-radius: 25rpx;
	padding: 25rpx 0;
}
.confirmInfo .info_title{
	text-align: center;
	font-size: 36rpx;
}
.confirmInfo .info_item{
	margin: 15rpx 25rpx;
}
.btnGroup{
	/* position: absolute; */
	/* bottom: 0; */
	width: 100%;
	/* margin-top: 150rpx; */
}
.btnGroup button{
	margin: 25rpx;
}
	.from .input{
			border-radius: 15rpx;
			
		}
		.select{
			border-radius: 15rpx;
		
		}
		.from .textarea{
			border-radius: 15rpx;
		
		}
	.from .btn{
		width: 650rpx;
	}
</style>
