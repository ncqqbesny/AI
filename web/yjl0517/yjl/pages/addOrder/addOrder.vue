<template>
	<view class="container">
		<view class="view_title">
			<text class="">{{type == 1 ? '发起物资调用' : '发起物资归还'}} </text>
		</view> 
		<view class="orderInfo">
			<view class="info_item order_number">
				物资调用单号：{{ formData.orderNumber }}
			</view>
			<view class="info_item order_telephone">
				物资调用发起人手机号：{{formData.applyPhone}}
			</view>
			<view class="info_item order_name">
				物资调用发起人真实姓名：{{formData.applyName}}
			</view>
			<view class="info_item event_code">
				事件编号：{{eventData.code}}
			</view>
			<view class="info_item event_address">
				<view class="title">
					处理地址：
				</view>
				{{eventData.address}}
			</view>
			<view class="info_item evenet_remark">
				事件备注：{{eventData.remark}}
			</view>
			<view class="info_item evenet_desc2" v-show="type == 2">
				物资取用说明：{{returnData.yjDesc}}
			</view>
			<view class="info_item evenet_desc2" v-show="type == 2">
				<uni-file-picker readonly :value="imgValue2" file-mediatype="image">
				</uni-file-picker>
			</view>
			<view class="info_item evenet_desc">
				{{type == '1' ? '物资取用说明：' : '物资归还说明：'}}
			</view>
			<view class="textarea">
				<uni-easyinput disableColor v-model="formData.yjDesc" placeholder="设备备注"  :inputBorder="false" :maxlength='20'  type="textarea" autoHeight />
			</view>
			<view class="info_item order_img">
				{{type == '1' ?  '物资装车图片：' : '物资入库图片：'}}
				<view class="item_value" >
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
		</view>
		<view class="warn_message">
			*请按需申请物资，故意毁弃可依法追究法律责任。
		</view>
		<view class="btnGroup">
			<view class="btn toAdd" @click="showAlert(1)"  >
				 {{type == 1 ? '发起物资调用申请' : '发起物资归还申请'}}
			</view>
			<uni-popup ref="alertAdd" type="dialog">
				<uni-popup-dialog type="info" cancelText="关闭" confirmText="确认" title="通知" content="确认提交表单?" @confirm="save(nowStatus)"
					@close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import {
		getLoginUserInfo,upImg,saveOrder,findEventInfo,getOrderList  
	} from '../../api/api.js';
	export default {
		data() {
			return {
				// 提交用
				formData:{
					yjDesc:'',
					number:1
				},
				imageValue:[],
				type:'1',
				filePathsList:[],
				orderNumber:'',
				extendEventNumber:'',
				nowStatus:0,
				
				eventData:{
					
				},
				// 读取用
				returnData:{
					
				},
				// 读取用图片
				imgValue2:[]
			}
		},
		computed:{
			loginUserInfo() {
				return this.$store.state.loginUserInfo 
			}
		},
		methods: {
			showAlert(e){
				this.$refs.alertAdd.open()
				this.nowStatus = e
			},
			// 获取上传状态
			async select(e){
				
				console.log('选择文件：',e)
				Array.prototype.push.apply(this.imageValue,e.tempFiles)
				
				e.tempFilePaths.forEach((item,index)=>{
					console.log(item)
					uni.uploadFile({
					    url: uni.getStorageSync('hdUrl') + '/yjl/uploadFiles', //仅为示例，非真实的接口地址
					    filePath: item,
						name: 'files',
					    header: {
					    		'token'  : uni.getStorageSync('user_token'),
						},
					    success: (uploadFileRes) => {
					    	// 根据接口具体返回格式   赋值具体对应url
					        console.log(uploadFileRes.data);
							let urls = JSON.parse(uploadFileRes.data).showUrls
							const objects = urls.map(url => ({
									     url,  
									     name: item,  
									     extname: ""  
									   }));
							Array.prototype.push.apply(this.filePathsList,objects)
							// this.imageValue[index] = JSON.parse(uploadFileRes.data).showUrls
							console.log('当前的图片',this.filePathsList)
							console.log('本地图片',this.imageValue)
					    }
					});
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
					
				for (let i = 0; i < this.imageValue.length; i++) {  
					
					if(e.tempFilePath == this.filePathsList[i].name){
						console.log(`检测到匹配的了`,i); 
						//删除图片
						this.filePathsList.splice(i,1)
						// this.imageValue.splice(i,1)
						console.log('当前图片',this.filePathsList)
						return
					
					}
				} 
			},
			getOrder(){
				getOrderList(
					{
						orderNumber:this.orderNumber
					}
				).then(
					res => {
						if(res.data.code == 200){
							this.returnData = res.data.data[0]
							this.imgValue2 = []
							let array
							if (this.formData.urls != '') {
								array = this.returnData.urls.split(",")
								console.log(this.imgValue2)
								this.imgValue2 = array.map(url => ({
									url,
									name: "",
									extname: ""
								}));
							}
						}
					}
				)
			},
			findData(){
				findEventInfo(
					{code:this.extendEventNumber}
				).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
							this.eventData = res.data.data[0]
						}
					}
				)
			},
			save(status){
				let data = {...this.formData}
				
				console.log(this.filePathsList)
				//
				if(data.yjDesc == ''){
					uni.showToast({
						icon:'error',
						title: `请填写应急说明`
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
					data.urls = this.filePathsList.map(obj => obj.url).join(","); 
					data.type = this.type
					data.applyTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
					data.status = status + ''
					if(this.orderNumber != ''){
						data.extendOrderNumber = this.orderNumber
						
					}
					
					data.extendEventNumber = this.eventData.code
					
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
								
									return uni.showToast({
										title: res.data.msg,
										icon: 'error',
									})
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
			}
			if(options.orderNumber){
				this.orderNumber = options.orderNumber
			}
			if(options.code){
				this.extendEventNumber = options.code;
				console.log(this.extendEventNumber)
			}
			// 
			// 
		},
		onShow(){
			console.log(this.loginUserInfo)
			this.formData.applyName = this.loginUserInfo.name
			this.formData.applyUser = this.loginUserInfo.username
			this.formData.applyPhone = this.loginUserInfo.telephone
			const now = new Date();
			const year = now.getFullYear();  
			const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，所以需要加1  
			const day = String(now.getDate()).padStart(2, '0');  
			const hours = String(now.getHours()).padStart(2, '0');  
			const minutes = String(now.getMinutes()).padStart(2, '0'); 
			const seconds = String(now.getSeconds()).padStart(2, '0');
			const time = now.getTime()%1000
			if(this.type == '1' && this.orderNumber == ''){
				// 代表是新增归还单
				this.formData.orderNumber = 'DY' + `${year}${month}${day}${hours}${minutes}${seconds}` + time;
				
			}else{
				// this.get
				this.getOrder()
				this.formData.orderNumber = 'GH' + `${year}${month}${day}${hours}${minutes}${seconds}` + time;
				
			}
			this.findData()
		}
	}
</script>

<style>
.container{
		min-height: 100vh;
		font-family: alyuan;
}
.container::before{
	 content: "";
	display: table;
	height: 0;
}
.view_title{
	width: 706rpx;
	margin: 22rpx auto 0;
	font-size: 39rpx;
	font-weight: bold;
}
.orderInfo{
	position: relative;
	background: #fff;
	width: 702rpx;
	/* height:1135rpx; */
	margin: 61rpx auto 0;
	font-size: 23rpx;
	color: #9E9E9E;
	font-weight: bold;
	border-radius: 39rpx;
	box-shadow: 0rpx 0rpx 10rpx rgba(0,0,0,0.3);
}

.order_number{
	margin-top: 35rpx; ;
}
.info_item{
	margin-left: 37rpx;
	font-size: 23rpx;
	color: #9E9E9E;
}
.event_code{
	margin-top: 40rpx;
}
.evenet_desc{
	margin-top: 22rpx;
	color: #000;
}

.order_number,.order_telephone,.order_name{
	color: #87735F;
}
.textarea{
	width: 630rpx;
	height: 437rpx;
	margin: 16rpx auto 0;
	overflow: hidden;
	border-radius: 35rpx;
	font-family: alyuan;
	font-size: 27rpx;
	padding: 10rpx 14rpx;
	line-height: 40rpx;
	background: #F0F0F3;
	box-shadow: inset 0px 3rpx 3rpx rgba(0, 0, 0, 0.3);
}
>>>.uni-easyinput__content{
	font-size: 39rpx !important;
	font-weight: bold !important;
	background: none !important;
	color: #000 !important;
	font-family: alyuan;
}
.uni-easyinput__content{
	font-size: 39rpx !important;
	font-weight: bold !important;
	background: none !important;
	color: #000 !important;
	font-family: alyuan;
}
.order_img{
	margin-bottom: 33rpx;
}
.orderInfo::before,.orderInfo::after{
	content: "";
	display: table;
	height: 0;
}
.warn_message{
	margin-top: 30rpx;
	padding-left: 14rpx;
	color: #C80000;
	font-size: 26rpx;
	background-color: #D7CBBE;
}
.btnGroup{
		width: 702rpx;
		margin: 21rpx auto 0;
	}
.btnGroup .btn{
		height: 118rpx;
		border-radius: 59rpx;
		font-family: alyuan;
		font-size: 35rpx;
		line-height: 118rpx;
		text-align: center;
		color: #F0F0F3;
	}
	.btn.toAdd{
		background: #005F7A;
		margin-bottom: 16rpx;
	}

</style>
