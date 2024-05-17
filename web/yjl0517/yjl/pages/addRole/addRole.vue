<template>
	<view class="container">
		<view class="from">
			<uni-forms ref="roleFrom" :modelValue="roleFrom">
				<uni-forms-item label="身份名称" name="roleName" label-width='250'>
					<uni-easyinput v-model="roleFrom.roleName" placeholder="请输入身份名称"  class="input" />
				</uni-forms-item>
				<uni-forms-item label="身份描述" name="description" label-width='250'>
					<uni-easyinput v-model="roleFrom.description" type="textarea" autoHeight placeholder="请输入身份描述"  class="input" />
				</uni-forms-item>
				<uni-forms-item label="身份权限" name="roleName" label-width='250'>
					<custom-tree-select :listData="menuList" v-model="roleFrom.menuIds" dataLabel='menuName'
						dataValue='menuId' dataChildren='children' :mutiple='true' :linkage='true' class="input" />
				</uni-forms-item>
				<uni-forms-item label="数据权限" name="roleName" label-width='250'>
					<custom-tree-select :listData="permissionList" v-model="roleFrom.permissionIds" dataLabel='text'
						dataValue='value' dataChildren='children' :mutiple='true' :linkage='true' class="input"/>
				</uni-forms-item>
			</uni-forms>
			<!-- <button  @click="showPicker()">修改菜单权限</button> -->
			<button type="primary" @click="showAlert()">新增角色</button>
			<uni-popup ref="alertAdd" type="dialog">
				<uni-popup-dialog type="info" cancelText="关闭" confirmText="确认" title="通知" content="确认提交表单?" @confirm="saveUser()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
		

	</view>
</template>

<script>
	import {
		getMenuList,
		addRoleInfo,
		getPermissionById
	} from '../../api/api.js';
	import {
		arrToTree
	} from '../../assets/utils.js'
	export default {
		data() {
			return {
				menuList: [],
				menuList2: [],
				roleFrom: {
					roleId: '0',
					mId: '0',
					roleStatus: '1',
					roleName: '',
					description: '',
					menuIds: [],
					permissionIds:0,
				},
				roleId: '',
				permissionList:[],
			}
		},
		methods: {
			showAlert(){
				this.$refs.alertAdd.open()
			},
			getMenu() {
				getMenuList({
					pageNum: 1,
					pageSize: 20
				}).then(
					res => {
						console.log(res.data.data)
						if (res.data.code == 200) {
							this.menuList = arrToTree(res.data.data.list, 0)
							this.menuList2 = res.data.data.list
							console.log('转化成=>')
							console.log(this.menuList)
						}
					}
				)
			},	
			getPermission(){
				getPermissionById().then(
					res => {
						if (res.data.code == 200) {
							this.permissionList = res.data.data.map(item => ({
							    text: item.permission_name,  
							    value: item.permission_id  
							})); 
							console.log('数据权限',this.permissionList)
						}
					}
				)
			},
			saveUser() {
				console.log(this.roleFrom.menuIds)
				let data = {}
				data = this.roleFrom
				data.roleIds = [this.roleFrom.roleId]
				let str = data.menuIds.join(',');
				data.menuIds = str
				// this.roleFrom.
				console.log(data)
				addRoleInfo(data).then(
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
			}
		},
		onShow() {
			this.getMenu()
			this.getPermission()
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

	.from {
		width: 650rpx;
		padding: 25rpx;
		/* margin-top: 200rpx; */
		margin: 100rpx auto;
		
		border-radius: 15rpx;
		
		background: none;
	}

	.from .btn {
		width: 650rpx;
	}

	.from .input {
		border-radius: 15rpx;
		background: #fff;
	}
</style>