<template>
	<view class="container">
		<view class="step__line">
			<uni-steps :options="stepList" :active="active" />
			<uni-forms-item label="登录账号"  name="username" label-width='250' v-show="active==0">
				<uni-easyinput v-model="userFrom.username" placeholder="请输入登录账号" class="input" />
			</uni-forms-item>
			<uni-forms-item label="显示昵称"  name="nickName" label-width='250' v-show="active==0">
				<uni-easyinput v-model="userFrom.nickName" placeholder="请输入显示昵称"  class="input"/>
			</uni-forms-item>
			<uni-forms-item label="登录密码"  name="password" label-width='250' v-show="active==0">
				<uni-easyinput v-model="userFrom.password" placeholder="请输入登录密码" type='password'  class="input" />
			</uni-forms-item>
			<uni-forms-item label="确认密码"  name="password" label-width='250' v-show="active==0">
				<uni-easyinput v-model="confirmPassword" placeholder="请输入确认密码" type='password' class="input" />
			</uni-forms-item>
			<uni-forms-item label="电子邮箱"  name="email" label-width='250' v-show="active==1">
				<uni-easyinput v-model="userFrom.email" placeholder="请输入电子邮箱"  class="input" />
			</uni-forms-item>
			<uni-forms-item label="真实姓名"  name="name" label-width='250' v-show="active==1">
				<uni-easyinput v-model="userFrom.name"  placeholder="请输入姓名"  class="input" />
			</uni-forms-item>
			<uni-forms-item label="绑定手机"  name="telephone" label-width='250' v-show="active==1">
				<uni-easyinput v-model="userFrom.telephone" type="number" placeholder="请输入手机号"   class="input"/>
			</uni-forms-item>
			<uni-forms-item label="权限绑定"  name="merchant" label-width='250' v-show="active==1">
				<uni-data-select
					 v-model="userFrom.roleId"
					 :localdata="roleList"
					 class="input"
					 @change="changeRoleId"
				   >
				</uni-data-select>
			</uni-forms-item>
			<uni-forms-item label="绑定项目"  name="merchant" label-width='250' v-show="active==1">
				<uni-data-select
				     v-model="userFrom.mid"
				     :localdata="merChantList"
					  class="input"
				   >
				</uni-data-select>
			</uni-forms-item>
			
			
			<view class="confirmInfo" v-show="active==2">
				<view class="info_title">
					新用户信息
				</view>
				<view class="info_item">
					登录账号：{{userFrom.username}}
				</view>
				<view class="info_item">
					显示昵称：{{userFrom.nickName}}
				</view>
				<view class="info_item">
					登录密码：{{userFrom.password}}
				</view>
				<view class="info_item">
					电子邮箱：{{userFrom.email}}
				</view>
				<view class="info_item">
					真实姓名：{{userFrom.name}}
				</view>
				<view class="info_item">
					绑定手机：{{userFrom.telephone}}
				</view>
				<view class="info_item">
					绑定角色：{{ getNameById(roleList,userFrom.roleId)}}
				</view>
				<view class="info_item">
					绑定项目：{{ getNameById(merChantList,userFrom.mid)}}
				</view>
				
			
				
			</view>
			<view class="btnGroup">
					<button  @click="subtractActive()" v-show="active!=0">返回上一步</button>
					<button type="primary" @click="addActive()"  v-show="active!=2">下一步</button>
					<button type="primary" @click="showAlert()"  v-show="active==2">提交信息</button>
			</view>
			<uni-popup ref="alertAdd" type="dialog">
				<uni-popup-dialog type="info" cancelText="关闭" confirmText="确认" title="通知" content="确认提交表单?" @confirm="addUser()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
		
	</view>
</template>

<script>
	import { encode,arrToTree2 } from '../../assets/utils.js'
	import {addUserInfo,getQueryProject,getRoleList } from '../../api/api.js';
	export default {
		data() {
			return {
				stepList: [{
					title: '用户信息'
				}, {
					title: '绑定信息' 
				}, {
					title: '添加完成'
				}],
				active: 0,
				userFrom:{
					name:'',
					telephone:'',
					username:'',
					nickName:'',
					email:'',
					mid:0,
					roleId:''
				},
				confirmPassword:'',
				merChantList:[],
				roleList:[],
				searchFrom:{
					pageSize:100,
					pageNum:1,
				},
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
			encodepassword(password, eles = []) {
				eles.push(password);
				return encode("0x12", eles);
			},
			// 手机号验证
			isValidPhoneNumber(phoneNumber) {  
			    const regex = /^1[3456789]\d{9}$/;  
			    return regex.test(phoneNumber);  
			},
			addActive(){
				if(this.active==0){
					if(this.userFrom.username == '' || this.userFrom.nickName == '' || this.userFrom.password == '' || this.confirmPassword == ''){
						uni.showToast({
							icon:'error',
							title: `含有未填项`
						})
					}else if(this.userFrom.password.length < 6 || this.confirmPassword.length < 6 ){
						uni.showToast({
							icon:'error',
							title: `密码长度至少为6`
						})
					}else if(this.userFrom.password != this.confirmPassword ){
						uni.showToast({
							icon:'error',
							title: `密码不一致`
						})
					}else{
						this.active++ 
					}
				}else if(this.active == 1){
					if(this.userFrom.email == '' || this.userFrom.name == '' || this.userFrom.telephone == '' || this.userFrom.roleId == ''){
						uni.showToast({
							icon:'error',
							title: `含有未填项`
						})
					}else if(this.userFrom.telephone.length != 11){
						uni.showToast({
							icon:'error',
							title: `手机号应为11位`
						})
					}else if(!this.isValidPhoneNumber(this.userFrom.telephone)){
						uni.showToast({
							icon:'error',
							title: `请输入正确手机号`
						})
					}else{
						this.active++ 
					}
					
				}
			},
			subtractActive(){
				if(this.active != 0){
					this.active--
				}
				
			},
			addUser(){
				let data = {}
				data = {... this.userFrom}
				data.roleIds = [this.userFrom.roleId]
				data.password = this.encodepassword(data.password)
				addUserInfo(data).then(
					res =>{
						console.log(res)
						if(res.data.code == 200){
							console.log(res)
							uni.showToast({
								title: `用户添加成功`
							})
							uni.navigateBack()
						}else{
							uni.showToast({
								icon:'error',
								title: res.data.msg
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
							console.log(this.merChantList)
						}
					}
				)
			},
			change(e) {
				console.log("e:", e);
				console.log(this.userFrom.mid)
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
			  },
			  changeRoleId(e){
			  	console.log(e)
			  	this.getMerchant(e)
				this.userFrom.mid = null
			  },
			  getNameById(arr, id) {  
			      const obj = arr.find(item => item.value === id);  
			      return obj ? obj.text : null;  
			  }
		},
		
		onShow(){
			console.log(this.loginUser.id)
			this.getMerchant()
			this.getRole()
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
	position: absolute;
	bottom: 0;
	width: 710rpx;
}
.btnGroup button{
	margin: 25rpx;
}


	.step__line {
		width: 710rpx;
		padding: 25rpx;
		/* margin-top: 200rpx; */
		margin: 100rpx auto;
		
		border-radius: 15rpx;
		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset !important;
		color: #fff; */
		background: #fff;
	}
	.step__line .input{
		border-radius: 15rpx;
		/* box-shadow: 0 0 25px rgb(0, 106, 255) inset !important; */
		/* color: #fff; */
	}

	.from .btn{
		width: 650rpx;
	}

	.from .input {
		border-radius: 15rpx;
		border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset !important;
		color: #fff;
	}
</style>
