<template>
	<view class="container">
		<view class="view_name">
			<text class="">应急事件</text>
		</view>
		<view class="equ-item" v-for="(item,index) in tableData" :key="index">
			<view class="equCard" @click="toInfo(item.code)">
				<view class="cardBox">
					<view class="card-header">
						<view class="cardName">
							{{item.code}}
						</view>
					</view>
					<view class="card-body">
						<view class="body-desc">
							<view class="body-item">
								<view class="label">
									<!-- 设备Gid： -->
								</view>
								<view class="value">
									<!-- {{item.deviceGid}} -->
								</view>
							</view>
							<view class="body-item">
								<view class="label">
									事件地址：
								</view>
								<view class="value">
									{{item.address}}
								</view>
							</view>
							<view class="body-item">
								<view class="label">
									事件备注：
								</view>
								<view class="value">
									{{item.remark}}
								</view>
							</view>
						</view>
					</view>
					<view class="card-footer">
						<view class="equ-Details">
							*点击可查看详情
						</view>
					</view>
				</view>

			</view>
		</view>
	</view>

	</view>

</template>

<script>
	import store from '@/store/index.js';
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {
		findEvent
	} from '../../api/api.js';
	export default {
		data() {
			return {
				tableData: [

				],
				searchFrom: {
					pageSize: 10,
					pageNum: 1,
					sort: 0,
				},
				total: 0,
				mname: '',
				checkNumberInArray,
				loading: false,
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
			getData() {
				let data = {...this.searchFrom}
				data.MId = this.loginUserInfo.mid
				findEvent(data).then(
					res => {
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
					}
				)
			},
			toInfo(code) {
				setTimeout(function() {
					uni.navigateTo({
						url: '../eventInfo/eventInfo'
					})
				}, 300);
				store.commit('setEventCode', code)
				// console.log(code)
			},

			showDrawer() {
				this.$refs.showLeft.open()
			},
			toAddSmart() {
				setTimeout(function() {
					uni.navigateTo({
						url: '../addSmart/addSmart'
					})
				}, 300);
			}

		},
		onShow() {
			this.searchFrom.pageNum = 1
			this.tableData = []
			// this.getData()
			if (this.checkNumberInArray(this.menu, '32')) {
				this.getData()
			} else {
				uni.switchTab({
					url: '../home/home'
				});
			}
		},
		onReachBottom() {
			// this.searchFrom.pageNum = 1
			/*  */
			console.log('触底啦')

			// this.getSmart()

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
	@import url('~@/static/font/fontStyle.css');

	.container {
		/* background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%); */
		/* height: 100vh;
		overflow: scroll; */
		padding-bottom: 15rpx;
	}

	.searchBox {
		margin: 25rpx;
	}

	.view_name {
		margin-left: 22rpx;
		margin-top: 22rpx;
		font-size: 39rpx;
		font-family: alihei;
	}

	.uni-input {
		/* background-color: white;
		border-radius: 25rpx;
		/* margin: 0 25rpx; */
		padding: 0 25rpx;
		height: 60rpx;
		box-shadow: 10rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	}

	.equ-item {
		margin: 50rpx 0;
	}

	.equCard {
		margin: 0 25rpx;
		border-radius: 25rpx;
		box-shadow: 5rpx 5rpx 10rpx rgba(0, 0, 0, 0.3);
		overflow: hidden;
		background: #F0F0F3;
		font-family: alyuan;
	}

	.card-body {
		color: black;
		padding: 0 40rpx;
		display: flex;
	}

	.body-desc {
		margin-left: 26rpx;
	}

	.card-header {
		background-color: none;
		display: flex;
		padding: 0 40rpx;
		height: 84rpx;
		margin-top: 10rpx;
		font-size: 30rpx;
		border-radius: 25rpx 25rpx 0 0;
		justify-content: space-between;
		align-items: center;
		float: inherit;
		position: relative;
	}

	.cardName {
		margin-left: 20rpx;
		font-size: 32rpx;
		font-family: alyuan;
	}

	.cardType {
		position: absolute;
		right: 40rpx;
		top: 40rpx;
		font-size: 30rpx;
		font-family: alyuan;
		/* color: darkgrey; */
	}

	.cardType.onLine {

		color: #009C1A;
	}

	.cardType.offline {
		color: #C80000;
	}

	.body-item {
		width: 580rpx;
		display: flex;
		color: #9E9E9E;
		white-space: nowrap;
	}

	.body-item .label {
		width: 140rpx;
	}

	.body-item .value {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.equ-Description {
		height: 180rpx;
		width: 100%;
		margin-top: 20rpx;

	}

	.card-footer {
		background-color: none;
		padding: 0 40rpx;
		border-radius: 0 0 25rpx 25rpx;
	}

	.equ-Details {
		height: 60rpx;
		display: flex;
		justify-content: right;
		align-items: center;
		color: #9E9E9E;
	}

	.moreMenu {
		position: fixed;
		top: 180rpx;
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