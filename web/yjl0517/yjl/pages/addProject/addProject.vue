<template>
	<view class="container">
		<view ref="From" :modelValue="From" class="from">
			<view class="from_item">
				<view class="from_label">
					项目名称
				</view>
				<view class="from_value">
					<uni-easyinput v-model="From.mname" placeholder="请输入项目名称" class="input"/>
				</view>
			</view>
			
			<view class="from_item">
				<view class="from_label">
					上级项目
				</view>
				<view class="from_value">
					<uni-data-select
					     v-model="From.parentMid"
					     :localdata="merChantList"
						 class="input"
					   >
					</uni-data-select>
				</view>
			</view>
			
			<view class="from_item">
				<view class="from_label">
					项目描述
				</view>
				<view class="from_value">
					<uni-easyinput v-model="From.mdesc" type="textarea" autoHeight placeholder="请输入项目描述" class="input" />
				</view>
			</view>
			<view class="from_item">
				<button type="primary" @click="showAlert()" class="btn">新增项目</button>
			</view>
			<uni-popup ref="alertAdd" type="dialog">
				<uni-popup-dialog type="info" cancelText="关闭" confirmText="确认" title="通知" content="确认提交表单?" @confirm="add()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>	
	</view>
</template>

<script>
	import {addProject,getQueryProject } from '../../api/api.js';
	import { arrToTree } from '../../assets/utils.js'
	export default {
		data() {
			return {
				menuList:[],
				menuList2:[],
				From:{
					mname:'',
					parentMid:'',
					mdesc:''
				},
				roleId:'',
				merChantList:[]
			}
		},
		computed: {
			loginUserInfo() {
				return this.$store.state.loginUserInfo 
			},
			loginUser() {
				return this.$store.state.loginUser 
			}
		},
		methods: {
			showAlert(){
				this.$refs.alertAdd.open()
			},
			add(){
				this.From.parentMid = this.loginUserInfo.mid
				addProject(this.From).then(
					res =>{
						if(res.data.code == 200){
							uni.showToast({
								title: `保存成功`
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
							this.From.parentMid = this.loginUserInfo.mid
							this.merChantList = res.data.data.map(item => ({  
							    text: item.mname,  
							    value: item.mid  
							})); 
							console.log(this.From.parentMid)
						}
					}
				)
			},
		},
		onShow(){
			console.log(this.loginUserInfo)
			this.getMerchant()
		}
	}
</script>

<style scoped>
		.container{
			min-height: 100vh;
			padding-bottom: 15rpx;
		}
		.container::before{
			 content: "";
			display: table;
			height: 0;
		}
		.from{
			width: 710rpx;
			margin: 0 auto;
			
			margin-top: 300rpx;
			padding: 25rpx;
			background: #fff;
		}
		.from_item{
			display: flex;
			margin: 15rpx 0;
		}
		.from_label{
			margin-left: 25rpx;
			width: 150rpx;
			/* color: #fff; */
			height: 60rpx;
			line-height: 60rpx;
		}
		.from_value{
			width: 550rpx;
		}
		.from .input{
			border-radius: 15rpx;
			/* border: #06BFF7 5rpx solid;
			box-shadow: 0 0 25px rgb(0, 106, 255) inset !important;
			color: #fff; */
		}
		.from .btn{
			width: 710rpx;
		}
</style>
