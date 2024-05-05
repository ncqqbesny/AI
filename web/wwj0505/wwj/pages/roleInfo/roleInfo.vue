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
			</uni-forms>

			<view class="BtnGroup">
				<button type="primary" @click="saveUser()" class="btn" v-if="checkNumberInArray(menu,'28')">保存修改</button>
				<button type="error" @click="delUser('warn')" class="btn" v-if="checkNumberInArray(menu,'29')">删除角色</button>
			</view>
			<uni-popup ref="alertDialog" type="dialog">
				<uni-popup-dialog :type="msgType" cancelText="取消" confirmText="删除" title="通知" content="确认删除角色吗?"
					@confirm="dialogConfirm" @close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>


	</view>
</template>

<script>
	import {
		getRoleList,
		getMenuList,
		setRoleInfo,
		delRoleInfo
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
				msgType: 'success',
				checkNumberInArray,
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
			saveUser() {
				console.log(this.roleFrom.menuIds)
				// this.roleFrom.
				let data = {}
				data = {
					...this.roleFrom
				}
				let str = this.roleFrom.menuIds.join(',');
				data.menuIds = str
				setRoleInfo(data).then(
					res => {
						console.log(res)
						uni.showToast({
							title: `保存成功`
						})
						uni.navigateBack()
					}
				)
			},
			delUser(type) {
				this.msgType = type
				this.$refs.alertDialog.open()
			},
			dialogConfirm() {
				console.log('点击确认')
				delRoleInfo({
					ids: this.roleFrom.roleId
				}).then(
					res => {
						console.log(res)
						uni.showToast({
							title: `删除成功`
						})
						uni.navigateBack()
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
						this.roleFrom = data
						console.log(this.roleFrom.menuIds)
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
		}
	}
</script>

<style scoped>
	.container {
		/* background: url() cover; */
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