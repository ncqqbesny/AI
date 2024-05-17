<template>
	<view class="container">
		<view class="roleFrom">
			<uni-forms ref="roleFrom" :modelValue="roleFrom">
				<uni-forms-item label="身份名称" name="roleName" label-width='250'>
					<uni-easyinput v-model="roleFrom.roleName" placeholder="请输入身份名称" class="input" />
				</uni-forms-item>
				<uni-forms-item label="身份描述" name="description" label-width='250'>
					<uni-easyinput v-model="roleFrom.description" type="textarea" autoHeight placeholder="请输入身份描述" class="input" />
				</uni-forms-item>
				<uni-forms-item label="身份权限" name="roleName" label-width='250'>
					<custom-tree-select :listData="menuList" v-model="roleFrom.menuIds" dataLabel='menuName'
						dataValue='menuId' dataChildren='children' :mutiple='true' :linkage='true' class="input"/>
				</uni-forms-item>
				<uni-forms-item label="数据权限" name="roleName" label-width='250'>
					<custom-tree-select :listData="permissionList" v-model="roleFrom.permissionIds" dataLabel='text'
						dataValue='value' dataChildren='children' :mutiple='true' :linkage='true' class="input"/>
				</uni-forms-item>
			</uni-forms>

			<view class="BtnGroup">
				<button type="primary" @click="showEdit()" class="btn" v-if="checkNumberInArray(menu,'28')">保存修改</button>
				<button type="error" @click="showDel()" class="btn" v-if="checkNumberInArray(menu,'29')">删除角色</button>
			</view>
			<uni-popup ref="alertDel" type="dialog">
				<uni-popup-dialog type="info" cancelText="取消" confirmText="确认" title="通知" content="确认删除角色吗?" @confirm="delUser()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
			<uni-popup ref="alertEdit" type="dialog">
				<uni-popup-dialog type="success" cancelText="取消" confirmText="确认" title="通知" content="确认保存角色信息?" @confirm="saveUser()"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>


	</view>
</template>

<script>
	import {
		getRoleList,
		getMenuList,
		setRoleInfo,
		delRoleInfo,
		getPermissionById
	} from '../../api/api.js';
	import {
		arrToTree,
		arrToTree2,
		checkNumberInArray
	} from '../../assets/utils.js'
	export default {
		data() {
			return {
				menuList: [],
				menuList2: [],
				roleFrom: {
					roleName: '',
					description: '',
					menuIds: []
				},
				roleId: '',
				checkNumberInArray,
				// 数据权限
				permissionList:[]
			}
		},
		computed: {
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
			getMenu() {
				getMenuList({
					pageNum: 1,
					pageSize: 999
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
				// this.roleFrom.
				console.log(this.roleFrom.permissionIds)
				let data = {}
				data = {
					...this.roleFrom
				}
				let str = this.roleFrom.menuIds.join(',');
				data.menuIds = str
				setRoleInfo(data).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
							uni.showToast({
								title: `保存成功`
							})
							uni.navigateBack()
						}else{
							uni.showToast({
								icon: 'error',
								title: res.data.msg
							})
						}
					}
				)
			},
			delUser() {
				delRoleInfo({
					ids: this.roleFrom.roleId
				}).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
							uni.showToast({
								title: `删除成功`
							})
							uni.navigateBack()
						}else{
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
			queryRole() {
				getRoleList({
					pageNum: 1,
					pageSize: 20,
					roleId: this.roleId
				}).then(
					res => {
						console.log(res.data.data)
						if(res.data.code == 200){
							let data = {
								...res.data.data.list[0]
							}
							let array
							let numberArray
							if (data.menuIds) {
								array = data.menuIds.split(',');
								numberArray = array.map(Number);
								numberArray = numberArray.filter((value, index, self) => {
									return self.indexOf(value) === index;
								});
								data.menuIds = numberArray
								
							}
							if (data.permissions && !data.permissionIds) {
								let pid = []
								pid = data.permissions.map(
									item => (item.permission_id )
								) 
								data.permissionIds = pid
							}
							this.roleFrom = data
							console.log(this.roleFrom.menuIds)
							
						}else{
							uni.showToast({
								icon: 'error',
								title: res.data.msg ? res.data.msg : '网络出现问题,请重试'
							})
						}
						
					}
				)
			}

		},
		onLoad(options) {
			// console.log(options)
			this.roleId = options.roleId
		},
		onShow() {
			this.getMenu()
			this.queryRole()
			this.getPermission()
		}
	}
</script>

<style scoped>
	.container {
		/* background: url() cover; */
		display: flex;
		flex-direction: column;
		align-items: center;
		/* background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%); */
		width: 750rpx;
		overflow-x: hidden;
		min-height: 96vh;
		font-family: alyuan;
	}

	.roleFrom {
		margin: 25rpx;
		width: 660rpx;
		background: #FFFFFF;
		border-radius: 25rpx;
		box-shadow: 5rpx 5rpx 5rpx #ccc;
		margin: 15rpx auto;
		padding: 20rpx;
		margin-top: 15rpx;
		/* border: #06BFF7 5rpx solid;
		box-shadow: 0 0 25px rgb(0, 106, 255) inset;
		color: #fff; */
	}

	.BtnGroup {
		margin-top: 35rpx;
	}

	.BtnGroup .btn {
		margin-top: 20rpx;
	}
	
	.input{
		border-radius: 15rpx;
		
	}
	
	.from .btn{
		width: 650rpx;
	}
</style>