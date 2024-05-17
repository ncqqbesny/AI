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
					     @change="change"
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
			<view class="from_item" v-if="checkNumberInArray(menu,'18')">
				<button type="primary" @click="showEdit()" class="btn">保存修改</button>
			</view>
			<view class="from_item" v-if="checkNumberInArray(menu,'19')">
				<button type="warn" @click="showDel()" class="btn">删除项目</button>
			</view>
			<uni-popup ref="alertDel" type="dialog">
				<uni-popup-dialog type="info" cancelText="取消" confirmText="确认" title="通知" content="确认删除项目吗?" @confirm="del()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
			<uni-popup ref="alertEdit" type="dialog">
				<uni-popup-dialog type="success" cancelText="取消" confirmText="确认" title="通知" content="确认提交表单吗?" @confirm="add()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
		<!-- <button  @click="showPicker()">修改菜单权限</button> -->
		
		
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {getQueryProject,editProject,delProject } from '../../api/api.js';
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
				mid:'',
				merChantList:[],
				checkNumberInArray
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
			showDel(){
				this.$refs.alertDel.open()
			},
			showEdit(){
				this.$refs.alertEdit.open()
			},
			add(){
				editProject(this.From).then(
					res =>{
						console.log(res)
						
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
			del(){
				delProject(
					{
						mids:this.mid
					}
				).then(
					res => {
						console.log()
						
						if(res.data.code == 200){
							uni.showToast({
								title: `删除`
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
							this.merChantList = res.data.data.filter(item => item.mid != this.mid).map(item => ({  
									text: item.mname,   
									value: item.mid 
								})); 
							console.log(this.merChantList)
						}
					}
				)
			},
			getMerchant2(){
				getQueryProject(
				{
					MId:this.mid
				}
				).then(
					res=>{
						console.log('接口2',res.data.data.find(item => item.mid == this.mid))
						if(res.data.code == 200){
							// CONS
							this.From = {...res.data.data.find(item => item.mid == this.mid)}
							console.log(this.From)
						}
					}
				)
			}
		},
		onLoad(options) {
			this.mid = options.mid
		},
		onShow(){
			this.getMerchant()
			this.getMerchant2()
		}
	}
</script>

<style scoped>
		.container{
			min-height: 100vh;
		}
		.container::before{
			 content: "";
			display: table;
			height: 0;
		}
		.from{
			width: 650rpx;
			margin: 0 auto;
			background: #FFFFFF;
			border-radius: 25rpx;
			box-shadow: 5rpx 5rpx 5rpx #ccc;
			/* border: #06BFF7 5rpx solid; */
			/* box-shadow: 0 0 25px rgb(0, 106, 255) inset !important; */
			/* color: #fff; */
			margin-top: 300rpx;
			padding: 25rpx;
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
			width: 650rpx;
		}
</style>
