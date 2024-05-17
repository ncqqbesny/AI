<template>
	<view class="container">
		<view class="">
			<view class="peopleList" v-for="(item,index) in tableData" :key='index'>
				<!-- :thumbnail="item.wxHeadurl" -->
				<uni-card :title="item.mname" :extra="'项目编号:' + item.mid" @click="toInfo(item.mid)">
					<view class="item_desc">
						<text>描述信息:{{item.mdesc ? item.mdesc : '暂无描述'}}</text>
					</view>
				</uni-card>
			</view>
		</view>
		<view class="moreMenu" @click="showDrawer" v-if="checkNumberInArray(menu,'17')">
			<img src="@/static/icon/menu.png" alt="" class="switchImg" />
		</view>
		<uni-drawer ref="showLeft" mode="left" :width="320" @change="change($event,'showLeft')">
			<view class="menu_item" @click="toAddView">
				<view class="item_icon">
					<img src="@/static/icon/addProject.png" alt="" class="switchImg" />
				</view>
				<view class="item_label">
					新增项目
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
		getProjectList
	} from '../../api/api.js';
	export default {
		data() {
			return {
				searchFrom: {
					pageNum: 1,
					pageSize: 10
				},
				total: 0,
				tableData: [],
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
			getList() {
				let data = {
					...this.searchFrom
				}
				data.userId = this.loginUser.id
				getProjectList(data).then(res => {
					if (res.data.code == 200) {
						// 取消加载状态
						this.loading = false
						this.total = res.data.data.total
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
						url: '../addProject/addProject'
					})
				}, 300);
			},
			toInfo(id) {
				setTimeout(function() {
					uni.navigateTo({
						url: '../projectInfo/projectInfo?mid=' + id
					})
				}, 300);
			}
		},
		onShow: function() {
			this.searchFrom.pageNum = 1
			this.tableData = []
			// console.log(this.loginUser)
			if (checkNumberInArray(this.menu, '20')) {
				this.getList()
			} else {
				uni.showToast({
					title: `没有查看权限`
				})
			}

		},
		onReachBottom() {
			console.log('触底啦')
			// this.getList()

			if (!this.loading) {
				if (this.tableData.length < this.total) {
					this.loading = true
					this.searchFrom.pageNum++;
					this.getList()
				} else {
					console.log('已经加载全部数据')
				}
			}
		}
	}
</script>

<style>
	.container {
		min-height: 100vh;
	}

	.container::before {
		content: "";
		display: table;
		height: 0;
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
		margin-top: 10rpx;
		margin-left: 5rpx;
	}

	.menu_item {
		display: flex;
		margin: 20rpx;
	}

	.item_icon img {
		width: 80rpx;
	}

	.item_label {
		padding-left: 25rpx;
		font-size: 35rpx;
		line-height: 80rpx;
	}
</style>