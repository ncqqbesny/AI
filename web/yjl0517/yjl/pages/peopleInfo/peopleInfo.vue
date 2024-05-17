<template>
	<view class="container">
		<!-- <view class="uni-padding-wrap uni-common-mt">
			<uni-segmented-control :current="current" :values="items" :style-type="styleType"
				:active-color="activeColor" @clickItem="onClickItem" />
		</view>
		<view class="content">
			<view v-if="current === 0">
				<view class="user_info">
					<uni-forms ref="baseForm" :modelValue="userFrom">
						<uni-forms-item label="登陆账号" required  label-width='250'>
							<uni-easyinput v-model="userFrom.username" placeholder="请输入用户名" />
						</uni-forms-item>
						<uni-forms-item label="显示昵称" required  label-width='250'>
							<uni-easyinput v-model="userFrom.nickName" placeholder="请输入用户名" />
						</uni-forms-item>
						<uni-forms-item label="电子邮箱" required  label-width='250'>
							<uni-easyinput v-model="userFrom.email" placeholder="请输入用户名" />
						</uni-forms-item>
					</uni-forms>
				</view>
			</view>
			<view v-if="current === 1">
				<uni-forms ref="baseForm" :modelValue="userFrom">
					<uni-forms-item label="真实姓名" required label-width='250'>
						<uni-easyinput v-model="userFrom.name" placeholder="请输入姓名" />
					</uni-forms-item>
					<uni-forms-item label="绑定手机" required  label-width='250'>
						<uni-easyinput v-model="userFrom.telephone" placeholder="请输入年龄" />
					</uni-forms-item>
	
				</uni-forms>
			</view>
		</view> -->
		<view class="from">
			<uni-forms ref="userFrom" :modelValue="userFrom">
				<uni-forms-item label="登陆账号"  name="username" label-width='250'>
					<uni-easyinput v-model="userFrom.username" placeholder="请输入登陆账号" class="input" />
				</uni-forms-item>
				<uni-forms-item label="显示昵称"  name="nickName" label-width='250'>
					<uni-easyinput v-model="userFrom.nickName" placeholder="请输入显示昵称" class="input"/>
				</uni-forms-item>
				<uni-forms-item label="电子邮箱"  name="email" label-width='250'>
					<uni-easyinput v-model="userFrom.email" placeholder="请输入电子邮箱" class="input"/>
				</uni-forms-item>
				<uni-forms-item label="真实姓名"  name="name" label-width='250'>
					<uni-easyinput v-model="userFrom.name"  placeholder="请输入真实姓名" class="input"/>
				</uni-forms-item>
				<uni-forms-item label="绑定手机"  name="telephone" label-width='250'>
					<uni-easyinput v-model="userFrom.telephone" placeholder="请输入手机号" class="input"/>
				</uni-forms-item>
				<uni-forms-item label="权限绑定"  name="merchant" label-width='250' >
					<uni-data-select
						 v-model="userFrom.roleId"
						 :localdata="roleList"
						 class="input"
						 @change="changeRoleId"
					   >
					</uni-data-select>
				</uni-forms-item>
				<uni-forms-item label="绑定项目"  name="merchant" label-width='250' >
					<uni-data-select
					     v-model="userFrom.mid"
					     :localdata="merChantList"
						 class="input"
						 @change="change"
					   >
					</uni-data-select>
					<!-- <custom-tree-select :listData="merChantList" v-model="userFrom.mid" dataLabel='mname'
						dataValue='mid' dataChildren='children' class="input"/> -->
				</uni-forms-item>
				
			</uni-forms>
			<button type="primary" @click="showEdit()" v-if="checkNumberInArray(menu,'23')">提交</button>
			<button type="warn" @click="showDel()" class="delBtn" v-if="checkNumberInArray(menu,'24')">删除</button>
			<uni-popup ref="alertDel" type="dialog">
				<uni-popup-dialog type="info" cancelText="取消" confirmText="确认" title="通知" content="确认删除用户吗?" @confirm="delUser()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
			<uni-popup ref="alertEdit" type="dialog">
				<uni-popup-dialog type="success" cancelText="取消" confirmText="确认" title="通知" content="是否保存用户信息?" @confirm="setUser()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import store from '@/store/index.js';//需要引入store
	import {
		arrToTree,arrToTree2,checkNumberInArray
	} from '../../assets/utils.js'
	import {queryId,setUserInfo,delUseInfo,getQueryProject,getRoleList } from '../../api/api.js';
	export default {
		data() {
			return {
				userFrom:{
					name:'',
					telephone:'',
					username:'',
				},
				searchFrom:{
					pageSize:100,
					pageNum:1,
				},
				sexs:[{
					text: '男',
					value: 1
				}, {
					text: '女',
					value: 2
				}],
				items: ['基本信息', '安全信息'],
				activeColor: '#007aff',
				current: 0,
				colorIndex: 0,
				
				roleList:[],
				merChantList:[],
				checkNumberInArray,
			}
		},
		computed: {
			peopleId() {
				return this.$store.state.peopleId 
			},
			loginUser() {
				return this.$store.state.loginUser 
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
			change(e){
				console.log(e)
			},
			changeRoleId(e){
				console.log(e)
				this.getMerchant(e)
				this.userFrom.mid = null
			},
			getUser(){
				queryId(this.peopleId).then(res => {
					console.log(res)
					if(res.data.code == 200){
						this.userFrom = {...res.data.data}
						// this.userFrom.roleId = res.data.data.roles
						console.log(res.data.data.roleIds)
						this.userFrom.mid = Number(res.data.data.mid)
						this.userFrom.roleId = res.data.data.roleIds[0]
						this.getMerchant(this.userFrom.roleId)
					}else{
						uni.showToast({
							icon:'error',
							title: res.data.msg
						})
					}
					
				}).catch(err => {
				  console.error(err);
				})
			},
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			setUser(){
				let data = this.userFrom
				data.roleIds = [this.userFrom.roleId]
				delete data.roles
				// data.roles = []
				data.mid = this.userFrom.mid
				data.opType = 2
				setUserInfo(this.userFrom).then(
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
			delUser(){
				delUseInfo({
					ids:this.userFrom.userId
				}).then(
					res =>{
						console.log(res)
						if(res.data.code == 200){
							uni.showToast({
								title: `删除成功`
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
			getMerchant(roleId){
				let data = {
					userId:this.loginUser.id
				}
				if(roleId){
					data.roleId = roleId
				}
				getQueryProject(
					data
				).then(
					res=>{
						console.log(res)
						if(res.data.code == 200){
							this.merChantList = res.data.data.map(item => ({  
							    text: item.mname,  
							    value: item.mid  
							})); 
							// this.merChantList = arrToTree2(res.data.data)
							// console.log(this.merChantList)
							// console.log('树形！！',tree)
						}
					}
				)
			},
			getRole(){
				getRoleList(
					this.searchFrom
				).then(res => {
					if(res.data.code == 200){
						this.roleList = res.data.data.list.map(item => ({  
						    text: item.description,  
						    value: item.roleId  
						})); 
						// console.log(this.merChantList)
					}
				}).catch(err => {
				  console.error(err);
				})
			}
		},
		onShow(){
			console.log(this.peopleId)
			this.getUser()
			this.getRole()
		}
	}
</script>

<style>
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
		padding: 25rpx;
		/* margin-top: 200rpx; */
		margin: 100rpx auto;
		background: #FFFFFF;
		border-radius: 25rpx;
		box-shadow: 5rpx 5rpx 5rpx #ccc;
		/* color: #fff; */
	}
.uni-forms-item__label{
 width: 200rpx;	
}
.user_info{
	width: 90%;
	margin: 0 auto;
}
.delBtn{
	margin: 15rpx 0;
}
		
		.from .btn{
			width: 650rpx;
		}
		.from .input{
			border-radius: 15rpx;
			/* border: #06BFF7 5rpx solid;
			box-shadow: 0 0 25px rgb(0, 106, 255) inset !important;
			color: #fff; */
		}
</style>
