<template>
	<view class="container">
		<view class="peopleList">
			<view class="people" v-for="(item,index) in tableData" :key='index'>
				<!-- :thumbnail="item.wxHeadurl" -->
				<uni-card :title="item.roleName" :sub-title="item.nickName" :extra="item.description"
					@click="toInfo(item.roleId)">
					<view class="item_desc">
						<text>描述信息:{{item.description ? item.description : '暂无描述'}}</text>
					</view>
				</uni-card>

			</view>
		</view>
		<view class="moreMenu" @click="showDrawer">
			<img src="@/static/icon/menu.png" alt="" class="switchImg" />
		</view>
		<uni-drawer ref="showLeft" mode="left" :width="320">
			<view class="menu_item" @click="toAddView">
				<view class="item_icon">
					<img src="@/static/icon/addUser.png" alt="" class="switchImg" />
				</view>
				<view class="item_label">
					新增权限
				</view>
			</view>
		</uni-drawer>
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {
		RoleQueryId,
		getRoleList
	} from '../../api/api.js';
	import store from '@/store/index.js'; //需要引入store
	export default {
		data() {
			return {
				searchFrom: {
					pageSize: 10,
					pageNum: 1
				},
				tableData: [],
				total: 0,
				totalPage: 1,
				checkNumberInArray,
				loading: false
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
			RoleList() {
				getRoleList(
					this.searchFrom
				).then(res => {
					if (res.data.code == 200) {
						this.loading = false
						this.total = res.data.data.total
						this.totalPage = res.data.data.totalPage
						const newlist = res.data.data.list
						this.tableData.push(...newlist)
					} else {
						console.log('请求数据失败')
						return uni.showToast({
							title: res.data.msg,
							icon: 'error',
						})
					}
				}).catch(err => {
					console.error(err);
				})
			},
			showDrawer() {
				this.$refs.showLeft.open()
			},
			toAddView() {
				setTimeout(function() {
					uni.navigateTo({
						url: '../addRole/addRole'
					})
				}, 300);
			},
			toInfo(roleId) {
				setTimeout(function() {
					uni.navigateTo({
						url: '../roleInfo/roleInfo?roleId=' + roleId
					})
				}, 300);
			}
		},
		onShow() {
			this.searchFrom.pageNum = 1
			this.tableData = []
			console.log(this.loginUser)

			if (checkNumberInArray(this.menu, '26')) {
				this.RoleList()
			} else {
				uni.showToast({
					title: `没有查看权限`
				})
			}
		},
		onReachBottom() {

			if (!this.loading) {

				if (this.tableData.length < this.total) {
					this.loading = true
					this.searchFrom.pageNum++;
					this.RoleList()
				} else {
					this.searchFrom.pageNum = this.totalPage
					console.log('已经加载全部数据')
				}
			} else {
				console.log('请等待加载结果')
			}
		}
	}
</script>

<style>
	.container {
		min-height: 100vh;
	}

	.peopleList {
		margin-bottom: 200rpx;
	}

	.moreMenu {
		position: fixed;
		bottom: 20rpx;
		width: 80rpx;
		height: 80rpx;
		background: #007AFF;
		border-radius: 0 20rpx 20rpx 0;
	}

	.moreMenu img {
		width: 60rpx;
		height: 60rpx;
		margin-top: 10rpx;
		margin-left: 5rpx;
	}

	.menu_item {
		display: flex;
		margin: 20rpx;
	}

	.item_icon img {
		width: 80rpx;
		height: 80rpx;
	}

	.item_label {
		padding-left: 25rpx;
		font-size: 35rpx;
		line-height: 80rpx;
	}
</style>