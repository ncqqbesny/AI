<template>
	<view class="container">
		<view class="view_title">
			<text class="">{{type == 1 ? '物资调用申请' : '物资归还申请'}} </text>
		</view>
		<view class="view_name">
			<text>{{code}}</text>
		</view>
		<view class="activeBox">
			<view @click="changeActive()">
				全部
				<view class="active_line" v-if="!active">

				</view>
			</view>
			<view @click="changeActive(1)">
				待审核
				<view class="active_line" v-if="active == 1">

				</view>
			</view>
			<view @click="changeActive(2)">
				已批准
				<view class="active_line" v-if="active == 2">

				</view>
			</view>
			<view @click="changeActive(3)">
				已驳回
				<view class="active_line" v-if="active == 3">

				</view>
			</view>
			<!-- <text>全部</text> -->
		</view>
		<scroll-view class="list" :scroll-y='true' @scrolltolower="ReTableList">
			<view class="list_item" v-for="(item,index) in tableData" :key='index' @click="toInfo(item)">
				<!-- :thumbnail="item.wxHeadurl" -->
				<!-- <uni-card :title="applyNameToString(item.applyName,item.type)" :extra="StatustoString(item.status)"  @click="toInfo(item.roleId)">
					<view class="item_desc">
						<text>应急理由:{{item.yjDesc ? item.yjDesc : '暂无描述'}}</text>
					</view>
				</uni-card> -->
				<view class="card">
					<view class="card_left">
						<image :src="imgsToArr(item.urls)" mode="aspectFit"></image>
					</view>
					<view class="card_right">
						<view class="card_title">
							物资调用单号：{{item.orderNumber}}
						</view>
						<view class="card_info">
							<view class="user">
								<text>{{item.applyPhone + ' '}}</text>
								<text>{{ ' ' + item.applyName}}</text>
							</view>
							<view class="status">
								<text>{{ StatustoString(item.status,item.type) }}</text>
							</view>
						</view>
					</view>
					<!-- <view class="card_title">
						<view class="title">
							{{ applyNameToString(item.applyName,item.type) }}
						</view>
						<view class="status">
							{{ StatustoString(item.status,item.type) }}
						</view>
					</view>
					<view class="card_body">
						<view class="label">
							处理地址：
						</view>
						<view class="value">
							{{item.province,item.city,item.area , item.address}}
						</view>
						<view class="card_item">
							<text>应急理由:{{item.yjDesc ? item.yjDesc : '暂无描述'}}</text>
						</view>
						
					</view> -->
				</view>
			</view>
		</scroll-view>


	</view>
</template>

<script>
	import {
		findOrder
	} from '../../api/api.js';
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import store from '@/store/index.js'; //需要引入store
	export default {
		data() {
			return {
				searchFrom: {
					pageSize: 10,
					pageNum: 1,
					sort: 0
				},
				type: '1',
				code: '',
				address: '',
				total: 0,
				active: null,
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
			getData() {
				let data = {
					...this.searchFrom
				}
				if (this.code != '') {
					data.extendEventNumber = this.code
				}
				if (this.active) {
					data.status = this.active
				}
				if (this.type) {
					data.type = this.type
				}
				findOrder(data).then(res => {
					//请求数据成功
					console.log(res)
					if (res.data.code == 200) {
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
				})
			},
			// 下拉触发
			ReTableList() {
				console.log('到底了')
			},
			StatustoString(status, type) {
				let text = ''
				if (type == 1) {
					switch (status) {
						case 0:
							text = '待提交'
							break;
						case 1:
							text = '待审核'
							break
						case 2:
							text = '已审核'
							break;
						case 3:
							text = '已驳回'
							break
						case 4:
							text = '已完成'
							break
						default:
							text = ''
							break;
					}
				} else {
					switch (status) {
						case 0:
							text = '待提交'
							break;
						case 1:
							text = '待审核'
							break
						case 2:
							text = '已审核'
							break;
						case 3:
							text = '已驳回'
							break
						default:
							text = '已完成'
							break;
					}
				}

				return text
			},
			changeActive(e) {
				if (e) {
					this.active = e
					console.log(this.active)
				} else {
					this.active = null
				}
				this.tableData = []
				this.getData()
			},
			imgsToArr(urls) {
				let array = urls.split(",")
				return array[0]
			},
			applyNameToString(applyName, type) {
				let types = type == 1 ? '申请' : type == 2 ? '归还' : '报废'
				let name = applyName ? applyName : '成员'
				return name + '提交的' + types + '单'
			},

			toInfo(item) {
				console.log(item)
				uni.setStorage({
					key: 'orderItem',
					data: item,
					success: (res) => {
						uni.navigateTo({
							url: '../orderInfo/orderInfo?code=' + this.code
						});
					}
				})
			},
		},
		onLoad(options) {
			console.log(options.code)
			if (options.code) {
				this.code = options.code
			}
			if (options.type) {
				this.type = options.type
			}
		},
		onShow: function() {
			this.searchFrom.pageNum = 1
			this.tableData = []
			if (checkNumberInArray(this.menu, '8')) {
				this.getData()
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
					this.getData()
				} else {
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
		/* background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%); */
		height: 100vh;
		/* overflow: scroll; */
		font-family: alyuan;
	}

	.view_title {
		width: 706rpx;
		margin: 22rpx auto 0;
		font-size: 39rpx;
	}

	.view_name {
		width: 706rpx;
		margin: 34rpx auto;
		font-size: 30rpx;
		text-align: center;
	}

	.item_icon img {
		width: 80rpx;
		height: 80rpx;
		font-weight: bold;
	}

	.item_label {
		padding-left: 25rpx;
		font-size: 35rpx;
		line-height: 80rpx;
		font-weight: bold;
	}

	.activeBox {
		margin-top: 64rpx;
		width: 579rpx;
		font-size: 23rpx;
		color: #87735F;
		font-weight: bold;
		margin: 0 auto;
		display: flex;
		justify-content: space-between;
	}

	.active_line {
		height: 5rpx;
		margin-left: 25%;
		width: 75%;
		background: #C80000;
	}

	.card {
		width: 702rpx;
		margin: 26rpx auto 0;
		border-radius: 20rpx;
		background: #fff;
		box-shadow: 0rpx 0rpx 10rpx rgba(0, 0, 0, 0.3);
		/* padding: 20rpx; */
		display: flex;
	}

	.card_left image {
		margin: 35rpx;
		width: 142rpx;
		height: 106rpx;
	}

	.card_right {
		width: 463rpx;
		margin-top: 35rpx;
		color: #87735F;
		font-size: 23rpx;
		margin-right: 38rpx;
	}

	.card_right .card_info {
		margin-top: 10rpx;
		display: flex;
		justify-content: space-between;
		color: #9E9E9E;
	}

	.card_right .card_info .status {
		margin-right: 20rpx;
	}
</style>