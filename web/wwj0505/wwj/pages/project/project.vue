<template>
	<view class="container">
		<view class="">
			<view class="peopleList" v-for="(item,index) in tableData" :key='index'>
				<!-- :thumbnail="item.wxHeadurl" -->
				<uni-card :title="item.mname"  :extra="'项目编号:' + item.mid"  @click="toInfo(item.mid)">
					<view class="item_desc">
						<text>描述信息:{{item.mdesc ? item.mdesc : '暂无描述'}}</text>
					</view>
				</uni-card>
			</view>
		</view>
		<view class="moreMenu" @click="showDrawer" v-if="checkNumberInArray(menu,'17')">
			<img src="@/static/icon/menu.png" alt="" class="switchImg"  />
		</view>
		<uni-drawer ref="showLeft" mode="left" :width="320" @change="change($event,'showLeft')">
			<view class="menu_item" @click="toAddView">
				<view class="item_icon">
					<img src="@/static/icon/addProject.png" alt="" class="switchImg"  />
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
	import {getProjectList} from '../../api/api.js';
	export default {
		data() {
			return {
				searchFrom:{
					pageNum:1,
					pageSize:999
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
			getList(){
				getProjectList(this.searchFrom).then(res => {
					console.log(res.data.data.list)
					if( res.data.data.total - this.tableData.length > 8 ){
						// console.log('页数增加')
						this.tableData.push(...res.data.data.list)
						this.searchFrom.pageNum = this.searchFrom.pageNum + 1
					}else{
						this.tableData.push(...res.data.data.list)
						let uniqueTableData = this.tableData.filter((item, index, self) => {  
						    return self.findIndex(t => t.mid === item.mid) === index;  
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
							url:'../addProject/addProject'
						}
				)}, 300);
			},
			toInfo(id){
				setTimeout(function() {
					uni.navigateTo({
							url:'../projectInfo/projectInfo?mid=' + id
						}
				)}, 300);
			}
		},
		onShow: function() {
			this.searchFrom.pageNum = 1
			this.tableData = []
			// console.log(this.loginUser)
			if(checkNumberInArray(this.menu,'20')){
				this.getList()
			}else{
				uni.showToast({
					title: `没有查看权限`
				})
			}
			
		},
		onReachBottom(){
			console.log('触底啦')
			// this.getList()
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
	margin-top: 10rpx;
	margin-left: 5rpx;
}
.menu_item{
	display: flex;
	margin: 20rpx;
}
.item_icon img{
	width: 80rpx;
}
.item_label{
	padding-left: 25rpx;
	font-size: 35rpx;
	line-height: 80rpx;
}
</style>
