<template>
	<view class="container">
		<scroll-view class="list" :scroll-y='true' @scrolltolower="ReTableList">
			<view class="list_item" v-for="(item,index) in tableData" :key='index'  @click="toInfo(item)">
				<!-- :thumbnail="item.wxHeadurl" -->
				<!-- <uni-card :title="applyNameToString(item.applyName,item.type)" :extra="StatustoString(item.status)"  @click="toInfo(item.roleId)">
					<view class="item_desc">
						<text>应急理由:{{item.yjDesc ? item.yjDesc : '暂无描述'}}</text>
					</view>
				</uni-card> -->
				<view class="card">
					<view class="card_title">
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
						
					</view>
				</view>
			</view>
		</scroll-view>
	
		
	</view>
</template>

<script>
	import {findOrder} from '../../api/api.js';
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import store from '@/store/index.js';//需要引入store
	export default {
		data() {
			return {
				formData:{
					pageSize:999,
					pageNum:1,
					sort:0
				},
				total:0,
				tableData:[],
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
			getData(){
				findOrder(this.formData).then(res=>{
					//请求数据成功
					console.log(res)
					if(res.data.code == 200){
						this.tableData = res.data.data.list
					}else{
						 console.log('请求数据失败')		
					}
				})
			},
			// 下拉触发
			ReTableList(){
				console.log('到底了')
			},
			StatustoString(status,type){
				let text = ''
				if(type == 1){
					switch (status){
						case 0:
							text = '待提交'
							break;
						case 1:
							text = '待审核'
							break
						case 2:
							text = '待处理'
							break;
						case 3:
							text = '处理中'
							break
						case 4:
							text = '已完成'
							break
						default:
							text = ''
							break;
					}
				}else{
					switch (status){
						case 0:
							text = '待提交'
							break;
						case 1:
							text = '待审核'
							break
						case 2:
							text = '待处理'
							break;
						case 3:
							text = '已完成'
							break
						default:
							text = '已完成'
							break;
					}
				}
				
				return text
			},
			applyNameToString(applyName,type){
				let types = type == 1 ? '申请' : type == 2 ? '归还' : '报废'
				let name = applyName ?  applyName : '成员'
				return name + '提交的' + types + '单'
			},
			
			toInfo(item){
				console.log(item)
				uni.setStorage({
					key:'orderItem',
					data:item,
					success: (res) => {
						uni.navigateTo({
							url:'../orderInfo/orderInfo'
						});
					}
				})
			},
		},
		onShow: function() {
			this.tableData = []
			
			if(checkNumberInArray(this.menu,'8')){
				this.getData()
			}else{
				uni.showToast({
					title: `没有查看权限`
				})
			}
		},
		onReachBottom(){
			
		}
	}
</script>

<style>

.item_icon img{
	width: 80rpx;
	height: 80rpx;
}
.item_label{
	padding-left: 25rpx;
	font-size: 35rpx;
	line-height: 80rpx;
}

.card{
	width: 670rpx;
	margin: 20rpx auto;
	border-radius: 20rpx;
	background: #fff;
	box-shadow: 0rpx 0rpx 10rpx rgba(0,0,0,0.3);
	padding: 20rpx;
}

.card_title{
	height: 80rpx;
	display: flex;
	justify-content: space-between;
	line-height: 80rpx;
	border-bottom: ;
}
.card_title .title{
	font-size: 30rpx;
}
.card_title .status{
	font-size: 24rpx;
	color: #909399;
}
</style>
