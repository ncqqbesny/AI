<template>
	<view>
		<view class="from">
			<view class="from_item">
				<view class="item_label">
					<text>应急理由</text>
				</view>
				<view class="item_value">
					<uni-easyinput type="textarea"  v-model="formData.yjDesc" placeholder="请说明应急理由" />
				</view>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>图片说明</text>
				</view>
				<view class="item_value">
					<uni-file-picker 
					ref='files'
						v-model="imageValue" 
						fileMediatype="image" 
						:limit='15'
						@select="select" 
						@progress="progress" 
						@success="success" 
						@fail="fail" 
						@delete="deleteFile"
					/>
				</view>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>物资数量</text>
				</view>
				<view class="item_value">
					<uni-number-box v-model="formData.number" :min='1'></uni-number-box>
				</view>
			</view>
			<view class="from_item">
				<view class="item_label">
					<text>备注信息</text>
				</view>
				<view class="item_value">
					<uni-easyinput type="textarea"  v-model="formData.remark" placeholder="请填写备注信息" />
				</view>
			</view>
			<view class="from_item">
				<view class="btn default" @click="save(0)">
					保存草稿
				</view>
				<view class="btn primary" @click="save(1)">
					提交单据
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getLoginUserInfo,upImg,saveOrder
	} from '../../api/api.js';
	export default {
		data() {
			return {
				formData:{
					yjDesc:'',
					number:1
				},
				imageValue:[],
				type:'',
				filePathsList:[],
				orderNumber:'',
				extendEventNumber:''
			}
		},
		computed:{
			loginUserInfo() {
				return this.$store.state.loginUserInfo 
			}
		},
		methods: {
			// 获取上传状态
			async select(e){
				console.log('选择文件：',e)
				console.log(this.imageValue)
				let data = e.tempFiles.map(item => ({  
				  ...item,  
				  name: 'files'  
				}));  
				console
				uni.uploadFile({
					url:uni.getStorageSync('hdUrl') + '/yjl/uploadFiles',
					files: data,
					header: {
						'token'  : uni.getStorageSync('user_token'),
						// "Content-Type": "multipart/form-data",
					},
					success: (res) => {
					       console.log('上传成功', res.data);
					       // uni.uploadFile返回来的结果默认是JSON格式字符串，需要用JSON.parse转换成js对象
					       console.log('上传数据转换', JSON.parse(res.data).showUrls);
					       let urls = JSON.parse(res.data).showUrls
						   
						   const objects = urls.map(url => ({  
						     url,  
						     name: "",  
						     extname: ""  
						   }));  
					       Array.prototype.push.apply(this.imageValue,objects)
						   console.log(this.imageValue)
					     },
				})
				
			},
			// 获取上传进度
			progress(e){
				console.log('上传进度：',e)
			},
			
			// 上传成功
			success(e){
				console.log('上传成功')
				console.log(this.imageValue,this.imageValue[0])
			},
			
			// 上传失败
			fail(e){
				console.log('上传失败：',e)
			},
			deleteFile(e){
				console.log(e)
				console.log(this.imageValue)
			},
			
			save(status){
				let data = {...this.formData}
				
				console.log(this.imageValue)
				//
				if(data.yjDesc == ''){
					uni.showToast({
						icon:'error',
						title: `请填写应急原因`
					})
				}else{
					const now = new Date();  
					const year = now.getFullYear();  
					const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，所以需要加1  
					const day = String(now.getDate()).padStart(2, '0');  
					const hours = String(now.getHours()).padStart(2, '0');  
					const minutes = String(now.getMinutes()).padStart(2, '0'); 
					const seconds = String(now.getSeconds()).padStart(2, '0');
					data.mid = this.loginUserInfo.mid
					data.urls = this.imageValue.map(obj => obj.url).join(","); 
					data.type = '1'
					data.applyName = this.loginUserInfo.name
					data.applyUser = this.loginUserInfo.username
					data.applyTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
					data.status = status + ''
					if(this.orderNumber != ''){
						data.extendOrderNumber = this.orderNumber
						data.type = this.type
					}
					if(this.extendEventNumber != ''){
						data.extendEventNumber = this.extendEventNumber
					}
					console.log(data)
					saveOrder(data).then(
						res => {
							if(res.data.code == 200){
								if(status == 0){
									uni.showToast({
										title: `草稿已保存`
									})
								}else{
									uni.showToast({
										title: `表单已上传`
									})
								}
								uni.navigateBack()
							}else{
								
							}
						}
					)
				}
			},
		},
		onLoad(options) {
			console.log(options.type,options.orderNumber)
			if(options.type){
				this.type = options.type
				this.orderNumber = options.orderNumber
			}
			if(options.code){
				this.extendEventNumber = options.code
				console.log(this.extendEventNumber)
			}
			// 
			// 
		},
		onShow(){
			console.log(this.loginUserInfo)
		}
	}
</script>

<style>
.from{
	width: 710rpx;
	margin: 20rpx;
	background: #fff;
	padding-bottom: 20rpx;
}
.from_item{
	margin: 10rpx;
}
.from_item .btn{
	border-radius: 10rpx;
	text-align: center;
	height: 80rpx;
	line-height: 80rpx;
	margin: 10rpx;
}
.from_item .btn.default{
	background: #fff;
	border: 1rpx solid #ccc;
}
.from_item .btn.primary{
	color: #fff;
	background: #007AFF;
	border: 1rpx solid #ccc;
}
.item_label{
	height: 80rpx;
	line-height: 80rpx;
}
</style>
