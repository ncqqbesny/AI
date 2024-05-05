<template>
	<view class="container">
		<view class="peopleList">
			<view class="people" v-for="(item,index) in tableData" :key='index'>
				<!-- :thumbnail="item.wxHeadurl" -->
				<uni-card :title="item.roleName" :sub-title="item.nickName" :extra="item.description"  @click="toInfo(item.roleId)">
					<view class="item_desc">
						<text>描述信息:{{item.description ? item.description : '暂无描述'}}</text>
					</view>
				</uni-card>
				
			</view>
		</view>
		<view class="moreMenu" @click="showDrawer">
			<img src="@/static/icon/menu.png" alt="" class="switchImg"  />
		</view>
		<uni-drawer ref="showLeft" mode="left" :width="320">
			<view class="menu_item" @click="toAddView">
				<view class="item_icon">
					<img src="@/static/icon/addUser.png" alt="" class="switchImg"  />
				</view>
				<view class="item_label">
					新增权限
				</view>
			</view>
		</uni-drawer>
	</view>
</template>

<script>
	import {checkNumberInArray} from '../../assets/utils.js'
	import {RoleQueryId,getRoleList} from '../../api/api.js';
	import store from '@/store/index.js';//需要引入store
	export default {
		data() {
			return {
				searchFrom:{
					pageSize:999,
					pageNum:1
				},
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
			RoleList(){
				getRoleList(
					this.searchFrom
				).then(res => {
					console.log(res.data.data.list)
					if( res.data.data.total - this.tableData.length > 8 ){
						// console.log('页数增加')
						this.tableData.push(...res.data.data.list)
						this.searchFrom.pageNum = this.searchFrom.pageNum + 1
					}else{
						this.tableData.push(...res.data.data.list)
						let uniqueTableData = this.tableData.filter((item, index, self) => {  
						    return self.findIndex(t => t.roleId === item.roleId) === index;  
						});  
						  
						// 现在 uniqueTableData 就是去重后的数组  
						this.tableData = uniqueTableData;
						console.log(this.tableData)
					}
				}).catch(err => {
				  console.error(err);
				})
			},
			showDrawer() {
				this.$refs.showLeft.open()
			},
			toAddView(){
				setTimeout(function() {
					uni.navigateTo({
							url:'../addRole/addRole'
						}
				)}, 300);
			},
			toInfo(roleId){
				setTimeout(function() {
					uni.navigateTo({
							url:'../roleInfo/roleInfo?roleId=' + roleId
						}
				)}, 300);
			}
		},
		onShow() {
			this.tableData = []
			console.log(this.loginUser)
			
			if(checkNumberInArray(this.menu,'26')){
				this.RoleList()
			}else{
				uni.showToast({
					title: `没有查看权限`
				})
			}
		},
		onReachBottom(){
			// this.searchFrom.pageNum = 1
			// this.tableData = []
			console.log('触底啦')
			// this.RoleList()
		}
	}
</script>

<style>
.container{
	
}
.peopleList{
	margin-bottom: 200rpx;
}
.moreMenu{
	position: fixed;
	bottom: 20rpx;
	width: 80rpx;
	height: 80rpx;
	background: #007AFF;
	border-radius: 0 20rpx 20rpx 0;
}
.moreMenu img{
	width: 60rpx;
	height: 60rpx;
	margin-top: 10rpx;
	margin-left: 5rpx;
}
.menu_item{
	display: flex;
	margin: 20rpx;
}
.item_icon img{
	width: 80rpx;
	height: 80rpx;
}
.item_label{
	padding-left: 25rpx;
	font-size: 35rpx;
	line-height: 80rpx;
}
</style>
