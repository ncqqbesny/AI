<template>
	<view class="container" >
		<view class="searchFrom">
			
		</view>
		<view class="peopleList">
			<view class="people" v-for="(item,index) in tableData" :key='index'>
				<!-- :thumbnail="item.wxHeadurl" -->
				<uni-card :title="item.username" :sub-title="item.nickName" :extra="item.name"  @click="toInfo(item.userId)">
					<view class="">
						<text class="item_label">城市：</text>
						<text class="item_value">{{item.address}}</text>
					</view>
					<view class="">
						<text class="item_label">联系方式：</text>
						<text class="item_value">{{item.telephone}}</text>
					</view>
				</uni-card>
				
			</view>
		</view>
		<view class="moreMenu" @click="showDrawer" v-if="checkNumberInArray(menu,'22')">
			<img src="@/static/icon/menu.png" alt="" class="switchImg"  />
		</view>
		<uni-drawer ref="showLeft" mode="left" :width="320" >
			<view class="menu_item" @click="toAddView">
				<view class="item_icon">
					<img src="@/static/icon/addUser.png" alt="" class="switchImg"  />
				</view>
				<view class="item_label">
					新增用户
				</view>
			</view>
		</uni-drawer>
	</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import {getUserList} from '../../api/api.js';
	import store from '@/store/index.js';//需要引入store
	export default {
		data() {
			return {
				searchFrom:{
					pageSize:800,
					pageNum:1
				},
				tableData:[],
				checkNumberInArray
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
			UserList(){
				getUserList(this.searchFrom).then(res => {
					console.log(res)
					if( res.data.data.total - this.tableData.length > 8 ){
						// console.log('页数增加')
						this.tableData.push(...res.data.data.list)
						this.searchFrom.pageNum = this.searchFrom.pageNum + 1
					}else{
						this.tableData.push(...res.data.data.list)
						let uniqueTableData = this.tableData.filter((item, index, self) => {  
						    return self.findIndex(t => t.userId === item.userId) === index;  
						});  
						  
						// 现在 uniqueTableData 就是去重后的数组  
						this.tableData = uniqueTableData;
					}
				}).catch(err => {
				  console.error(err);
				})
			},
			toInfo(id){
				console.log('即将跳转至用户详情页')
				store.commit('setPeopleId', id)
				setTimeout(function() {
					uni.navigateTo({
							url:'../peopleInfo/peopleInfo'
						}
				)}, 300);
			},
			showDrawer() {
				this.$refs.showLeft.open()
			},
			toAddView(){
				setTimeout(function() {
					uni.navigateTo({
							url:'../addUser/addUser'
						}
				)}, 300);
			}
		},
		onShow: function() {
			this.searchFrom.pageNum = 1
			this.tableData = []
			if(checkNumberInArray(this.menu,'21')){
				this.UserList()
			}else{
				uni.showToast({
					title: `没有查看权限`
				})
			}
			
		},
		onReachBottom(){
			console.log('触底啦')
			// this.UserList()
		}
	}
</script>

<style>
.container{
	
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
.peopleList .item_label{
	padding-left: 25rpx;
	font-size: 35rpx;
	line-height: 80rpx;
	/* color: #fff; */
}
.peopleList .item_value{
	/* color: #fff; */
}
.item_label{
	padding-left: 25rpx;
	font-size: 35rpx;
	line-height: 80rpx;
}
</style>
