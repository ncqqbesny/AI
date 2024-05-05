<template>
	<view class='content'>
		<!-- <iframe src="https://hdpt.linkom.com.cn/#/login" id="dtdt-map" class="dtdt-map" name="ifr1" scrolling="yes"></iframe> -->
		<!-- 基础显示区 -->
		<view class="view_name">
			<text class="">设备巡检</text>
		</view>
		<view class="viewShowArea">
			<view class="base_container">
				<!-- <text>111</text> -->
				<!-- 显示区 -->
				<view class="container_title">
					<view class="container_icon">
						<img src="~@/static/icon/equicon.png" alt="" />
					</view>
					<view class="container_name">
						<uni-easyinput v-if='editModel' v-model="smartInfo.displayDeviceName" placeholder="编辑设备名称" :inputBorder="false"  maxlength="8"/>
						<text v-else>{{smartInfo.displayDeviceName}}</text>
					</view>
					<view :class="smartInfo.status == 1 ? 'container_status online' : 'container_status offline'">
						{{smartInfo.status == 1 ? '在线' : '离线'}}
					</view>
				</view>
				<view class="base_item">
					<view class="item_label">
						设备编号:
					</view>
					<!-- <view class="item_value" v-if='editModel'>
						<uni-easyinput v-model="smartInfo.deviceSn" placeholder="设备编号" class="input" />
					</view> -->
					<view class="item_value" >
						{{smartInfo.deviceSn}}
					</view>
				</view>
				<view class="base_item">
					<view class="item_label">
						设备地址:
					</view>
					<view class="item_value">
						{{smartInfo.province,smartInfo.smartInfo,smartInfo.area , smartInfo.address}}
					</view>
				</view>
				<view class="base_item remark">
					<view class="item_label">
						设备备注:
					</view>
					<view class="item_value" v-if='editModel'>
						<uni-easyinput v-model="smartInfo.remark" placeholder="设备备注"  :inputBorder="false" :maxlength='20'/>
					</view>
					<view class="item_value" v-else>
						<text>{{smartInfo.remark}}</text> 
					</view>
				</view>
			</view>
			
			<view class="info_container">
				<view class="info_item">
					<text>
						物资舱温度：{{average(getNameBysignalingBit('Tm1-T01'),getNameBysignalingBit('Tm2-T01')) + '℃'}}
					</text>
					<text>
						湿度：{{average(getNameBysignalingBit('Tm1-H01') , getNameBysignalingBit('Tm2-H01')) +  '%'}}
					</text>
				</view>
				<view class="info_line">
					
				</view>
				<view class="info_item">
					<text>
						设备舱温度：{{average(getNameBysignalingBit('Tm3-T01'),getNameBysignalingBit('Tm3-T01')) +  '℃'}}
					</text>
					<text>
						湿度：{{average(getNameBysignalingBit('Tm3-H01') , getNameBysignalingBit('Tm3-H01')) +  '%'}}
					</text>
				</view>
				<view class="info_status">
					<view class="box1_lock">
						<img src="~@/static/icon/open.png" alt="" v-if="getStatusBysignalingBit('SD1-02') != 0"/>
						<img src="~@/static/icon/close.png" alt="" v-else>
					</view>
					<view class="box2_lock">
						<img src="~@/static/icon/open.png" alt="" v-if="getStatusBysignalingBit('SD2-02') != 0"/>
						<img src="~@/static/icon/close.png" alt="" v-else>
					</view>
					<view class="box1_door value">
						<view :class="getStatusBysignalingBit('MD1-02') == 0 ? '' : 'warn'">
							<text>设备舱左门</text>
						</view>
						<view class="line">
							<img src="~@/static/icon/c2.png" alt="" />
						</view>
						<view :class="getStatusBysignalingBit('MD2-02') == 0 ? '' : 'warn'">
							<text>设备舱右门</text>
						</view>
					</view>
					<view class="box2_walk_door value">
						<view :class="getStatusBysignalingBit('MD3-02') == 0 ?   '' :  'warn'">
							<text>物资舱通行门</text>
						</view>
					</view>
					<view class="box_top_light value">
						<view :class="getStatusBysignalingBit('LE1') == 0 ?   'warn' :  ''">
							<text>舱顶报警灯</text>
						</view>
					</view>
					<view class="info_box">
						<img src="~@/static/icon/box.png" alt="" />
					</view>
					<view class="box2_door value">
						<view :class="getStatusBysignalingBit('MD4-2') == 0 ?   '' :  'warn'">
							<text>物资舱右门</text>
						</view>
						<view class="line">
							<img src="~@/static/icon/c2.png" alt="" />
						</view>
						<view :class="getStatusBysignalingBit('MD5-2') == 0 ?   '' :  'warn'">
							<text>物资舱右门</text>
						</view>
						
					</view>
					<view class="box2_light value">
						<view :class="getStatusBysignalingBit('LAP1') == 0 ?   'warn' :  ''">
							<text>设备舱照明灯</text>
						</view>
						<view class="line">
							<img src="~@/static/icon/c2.png" alt="" />
						</view>
						<view :class="getStatusBysignalingBit('FAN2') == 0 ?   'warn' :  ''">
							<text>设备舱风扇</text>
						</view>
					</view>
					<view class="box1_light value2">
						<view :class="getStatusBysignalingBit('LAP2') == 0 ?   'warn' :  ''">
							<text>物资舱照明灯1</text>
						</view>
						<view :class="getStatusBysignalingBit('LAP3') == 0 ?   'warn' :  ''">
							<text>物资舱照明灯2</text>
						</view>
						<view class="line">
							<img src="~@/static/icon/c1.png" alt="" />
						</view>
						<view :class="getStatusBysignalingBit('FAN1') == 0 ?   'warn' :  ''">
							<text>物资舱风扇</text>
						</view>
					</view>
					<view class="prompt">
						<text>*绿字表示<text class="success">关闭</text>，红字表示<text class="warn">未关闭</text></text>
					</view>
				</view>
			</view>
		
		</view>
		
		<view :class="popshow ?  'moreMenu hidden' :  'moreMenu show' " @click="showPop" v-show="smartInfo.status != 0" v-if="checkNumberInArray(menu,'16')">
			<img src="@/static/icon/key.png" alt="" class="switchImg"  />
			<text>智能锁</text>
		</view>
		<view :class="popshow ?  'pop show' :  'pop hidden'" v-if="showClick">
			<view class="pop_title">
				<img src="@/static/icon/key.png" alt="" class="switchImg"  />
				<text>智能锁</text>
			</view>
			<!--  -->
			<view class="pop_item">
				<view class="item_label">
					<text>设备舱门锁</text>
				</view>
				<!-- large -->
				<view class="btnGroup">
					<view class="btn" @click="openLock2()">
						<img src="@/static/icon/closelock2.png" alt="" class="switchImg"  />
					</view>
					<view class="line">
						<img src="@/static/icon/c2.png" alt="" class="switchImg"  />
					</view>
					<view class="btn" @click="openLock2()">
						<img src="@/static/icon/open.png" alt="" class="switchImg"  />
					</view>
				</view>
			</view>
			<view class="pop_item">
				<view class="item_label large">
					<text >物资舱</text>
					<text>通行门锁</text>
				</view>
				<view class="btnGroup">
					<view class="btn" @click="closeLock()">
						<img src="@/static/icon/closelock2.png" alt="" class="switchImg"  />
					</view>
					<view class="line">
						<img src="@/static/icon/c2.png" alt="" class="switchImg"  />
					</view>
					<view class="btn" @click="openLock()">
						<img src="@/static/icon/open.png" alt="" class="switchImg"  />
					</view>
				</view>
			</view>
			<!-- <view class="pop_item group_item">
				<view class="changeBtn" @click="changeEditModel">
					 {{editModel ? '保存修改' : '编辑设备'}}
				</view>
				<view class="delBtn" @click="delEqu">
					删除设备
				</view>
				<uni-popup ref="alertDialog" type="dialog">
					<uni-popup-dialog :type="msgType" cancelText="关闭" confirmText="同意" title="通知" content="确认删除设备吗?" @confirm="dialogConfirm"
						@close="dialogClose"></uni-popup-dialog>
				</uni-popup>
			</view> -->
		</view>
		<view class="popDreaw" @click="hiddenPop" v-show="popshow">
		
		</view>
		<!-- 物资仓锁控 -->
			<view class="footer">
				<view class="group_item">
					<view class="delBtn" @click="delEqu" v-if="checkNumberInArray(menu,'15')">
						删除设备
					</view>
					<view class="changeBtn" @click="changeEditModel" v-if="checkNumberInArray(menu,'14')">
						 {{editModel ? '保存修改' : '编辑设备'}}
					</view>
				</view>
				<uni-popup ref="alertDialog" type="dialog">
					<uni-popup-dialog :type="msgType" cancelText="关闭" confirmText="同意" title="通知" content="确认删除设备吗?" @confirm="dialogConfirm"
						@close="dialogClose"></uni-popup-dialog>
				</uni-popup>
			</view>
		</view>
</template>

<script>
	import {
		checkNumberInArray
	} from '../../assets/utils.js'
	import { getSmart,setSmart,getBitList,operBit,delSmart  } from '../../api/api.js';
	export default {
		data() {
			return {
				// 设备编号
				deviceSn:'',
				smartInfo:{
					displayDeviceName:'',
					
				},
				
				editModel:false,
				BitCtrlArr:[],
				BitValueArr:[],
				BitStatusArr:[],
				msgType: 'success',
				popshow:false,
				showClick:false,
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
			getSmartInfo(){
				getSmart({
					deviceSn:this.deviceSn
				}).then(
					res=>{
						console.log(res)
						if(res.data.code = 200){
							// 请求成功
							console.log(res.data.data)
							this.smartInfo = res.data.data[0]
						}
					}
				)
			},
			changeEditModel(){
				if(this.editModel == true){
					setSmart(this.smartInfo).then(
						res => {
							if(res.data.code == 200){
								uni.showToast({
									title: `保存成功`
								})
							}
							
						}
					)
				}
				this.editModel = !this.editModel
			},
			changeModel(searchTerm ){  
				console.log(searchTerm)
				let data = this.BitCtrlArr.filter(item => item.remark.includes(searchTerm));  
				console.log(data)
				operBit(data).then(
					res => {
						// console.log(res)
						
						uni.showToast({
							title: `操作完成`
						})
					}
				)
			},
			openLock(){
				 let data = [
					{
						deviceSn: this.deviceSn ,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo: this.smartInfo.stationNo,
						ctrlNo: 'D00016',
						ctrlNum: 2,
						ctrlType: 0,
						type: 2,
						status: 1
					},
					{
						deviceSn: this.deviceSn,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo: this.smartInfo.stationNo,
						ctrlNo: 'D00015',
						ctrlNum: 1,
						ctrlType: 0,
						type: 0,
						status: 1
					}
				]
				operBit(data).then(
					res => {
						// console.log(res)
						
						uni.showToast({
							title: `操作完成`
						})
					}
				)
			},
			closeLock(){
				 let data = [
					{
						deviceSn: this.deviceSn ,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo:  this.smartInfo.stationNo,
						ctrlNo: 'D00015',
						ctrlNum: 1,
						ctrlType: 0,
						type: 2,
						status: 1
					},
					{
						deviceSn: this.deviceSn ,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo:  this.smartInfo.stationNo,
						ctrlNo: 'D00016',
						ctrlNum: 2,
						ctrlType: 0,
						type: 0,
						status: 1
					}
				]
				operBit(data).then(
					res => {
						// console.log(res)
							// console.log(res)
						uni.showToast({
							title: `操作完成`
						})
					}
					
				)
			},
			
			openLock2(){
				 let data = [
					{
						deviceSn: this.deviceSn ,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo: this.smartInfo.stationNo,
						ctrlNo: 'D00012',
						ctrlNum: 2,
						ctrlType: 0,
						type: 2,
						status: 1
					},
					{
						deviceSn: this.deviceSn,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo: this.smartInfo.stationNo,
						ctrlNo: 'D00011',
						ctrlNum: 1,
						ctrlType: 0,
						type: 0,
						status: 1
					}
				]
				operBit(data).then(
					res => {
						// console.log(res)
						
						uni.showToast({
							title: `操作完成`
						})
					}
				)
			},
			closeLock2(){
				 let data = [
					{
						deviceSn: this.deviceSn ,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo:  this.smartInfo.stationNo,
						ctrlNo: 'D00011',
						ctrlNum: 1,
						ctrlType: 0,
						type: 2,
						status: 1
					},
					{
						deviceSn: this.deviceSn ,
						cabinetNo: this.smartInfo.cabinetNo,
						stationNo:  this.smartInfo.stationNo,
						ctrlNo: 'D00012',
						ctrlNum: 2,
						ctrlType: 0,
						type: 0,
						status: 1
					}
				]
				operBit(data).then(
					res => {
						// console.log(res)
							// console.log(res)
						uni.showToast({
							title: `操作完成`
						})
					}
					
				)
			},
			
			getNameBysignalingBit(signalingBit) {  
			    const obj = this.BitValueArr.find(item => item.signalingBit === signalingBit);  
			    return obj ? Number(obj.analogNum)  : null;  
			},
			// 通过signalingBit查询值
			getStatusBysignalingBit(signalingBit) {  
			    const obj = this.BitStatusArr.find(item => item.signalingBit === signalingBit);  
			    return obj ? obj.type : null;  
			},
			getBitCtrl(){
				getBitList(
					{
						pageNum:1,
						pageSize:999,
						deviceSn:this.deviceSn,
						ctrlTypes:'1'
					}
				).then(
					res => {
						if(res.data.code == 200){
							this.BitCtrlArr = res.data.data.list
						}
					}
				)
			},
			getBitValue(){
				getBitList(
					{
						pageNum:1,
						pageSize:999,
						deviceSn:this.deviceSn,
						ctrlTypes:'2,3'
					}
				).then(
					res => {
						if(res.data.code == 200){
							this.BitValueArr = res.data.data.list
						}
					}
				)
			},
			getBitStatus(){
				getBitList(
					{
						pageNum:1,
						pageSize:999,
						deviceSn:this.deviceSn,
						ctrlTypes:'1'
					}
				).then(
					res => {
						if(res.data.code == 200){
							this.BitStatusArr = res.data.data.list
						}
					}
				)
			},
			delEqu(){
				this.msgType = 'error'
				this.$refs.alertDialog.open()
				// delSmart([this.smartInfo.deviceGid]).then(
				// )
			},
			showDrawer() {
				this.$refs.showLeft.open()
			},
			dialogConfirm() {
				console.log('点击确认')
				delSmart([this.smartInfo.deviceGid]).then(
					res =>{
						console.log(res)
						uni.showToast({
							title: `删除成功`
						})
						uni.navigateBack()
					}
				)
			},
			average(num1, num2) {  
			    // 验证输入是否都是数字  
			    
		
			    // 如果只传入了一个数字，则平均值就是该数字本身  
			    if (num2 === undefined) {  
			        return parseFloat(num1.toFixed(1));  
			    }  
			  
			    // 计算两个数字的平均值  
			    const avg = (num1 + num2) / 2;  
			  
			    // 保留一位小数并返回  
			    return parseFloat(avg.toFixed(1));  
			},
			showPop(){
				this.showClick = true
				this.popshow = true
			},
			hiddenPop(){
				this.popshow = false
			}
		},
		onLoad(options) {
			// console.log(options)
			this.deviceSn = options.deviceSn
		},
		onShow(){
			this.getSmartInfo()
			this.getBitCtrl()
			this.getBitValue()
			this.getBitStatus()
		}
	}
</script>

<style>
.content{
	background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%);
	height: 100vh;
	width: 750rpx;
	overflow: scroll;
	/* position: relative; */
	/* padding-bottom: 150rpx; */
}
.view_name{
	margin-left: 22rpx;
	margin-top: 44rpx;
	font-size: 39rpx;
	font-family: alihei;
	margin-bottom: 78rpx;
}
.base_container{
	width: 630rpx;
	height: 351rpx;
	padding: 5rpx ;
	background: #FFFFFF;
	border-radius: 25rpx;
	box-shadow: 0rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	margin: 25rpx auto;
}
.base_container .container_title{
	width: 527rpx;
	height: 58rpx;
	line-height: 58rpx;
	margin: 33rpx auto;
	font-size: 32rpx;
	font-family: alyuan;
	display: flex;
	justify-content: space-between;
}
.base_container .container_title .container_status{
	font-size: 27rpx;
}
.container_status.online{
	color: #009C1A;
}
.container_status.offline{
	color: #C80000;
}
.container_icon img{
	width: 58rpx;
	height: 58rpx;
}

.base_item{
	display: flex;
	margin: 0 56rpx;
	color: #9E9E9E;
	font-size: 25rpx;
	padding: 0 20rpx;
	width: 710rpx;
	height: 40rpx;
	font-family: alyuan;
}
.info_container{
	width: 630rpx;
	padding: 5rpx ;
	background: #FFFFFF;
	display: flex;
	flex-wrap: wrap;
	border-radius: 25rpx;
	box-shadow: 0rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
	margin: 25rpx auto;
}
.info_container .info_item{
	font-size: 32rpx;
	font-family: alyuan;
	width: 543rpx;
	margin: 26rpx 43rpx;
}
.info_container .info_item text:nth-child(1){
	margin-right: 40rpx;
}
.info_line{
	margin: 0 50rpx;
	width: 520rpx;
	height: 4rpx;
	border-radius: 2rpx;
	background-color: #D7CBBE;
}
.info_status{
	position: relative;
}
.info_status .info_box img{
	width: 290rpx;
	height: 185rpx;
	margin:  80rpx 163rpx 263rpx;
}
.box1_lock{
	position: absolute;
	top:190rpx;
	left: 143rpx;
}
.box2_lock{
	position: absolute;
	top:150rpx;
	left: 245rpx;
}
.box1_lock img,.box2_lock img{
	width: 42rpx;
	height: 40rpx;
}
.box1_door{
	position: absolute;
	top: 110rpx ;
	left: 50rpx;
}
.box2_walk_door{
	position: absolute;
	left: 235rpx;
	top: 50rpx;
}
.box2_door{
	position: absolute;
	top: 110rpx ;
	right: 50rpx;
}
.info_status .value{
	width: 66rpx;
	font-size: 21rpx;
	font-family: alyuan;
	text-align: center;
	color: #009C1A;
}

.info_status .value .line img{
	width: 40rpx;
	height: 4rpx;
}
.info_status .value .warn{
	color: #C80000;
}
.box_top_light{
	position: absolute;
	left:345rpx;
}
.box2_light{
	position: absolute;
	left:175rpx;
	top: 280rpx;
	/* bottom: 0; */
}
.info_status .value2 > view{
	width: 80rpx;
	font-size: 21rpx;
	font-family: alyuan;
	text-align: center;
	color: #009C1A;
}
.info_status .value2 > view.warn{
	color: #C80000;
}
.info_status .value2 > view{
	position: absolute;
}
.info_status .value2 > view:nth-child(2){
	left: 95rpx;
}
.info_status .value2 > view:nth-child(3){
	left: 48rpx;
	top: 64rpx;
}
.info_status .value2 > view:nth-child(4){
	left: 48rpx;
	top: 88rpx;
}
.info_status .value2 .line img{
	width: 40rpx;
	height: 28rpx;
}

.prompt{
	position: absolute;
	color: #9E9E9E;
	font-size: 21rpx;
	bottom: 25rpx;
	right: 25rpx;
	font-family: alyuan;
}
.prompt .success{
	color: #009C1A;
}
.prompt .warn{
	color: #C80000;
}
.box1_light{
	position: absolute;
	left:300rpx;
	top: 280rpx;
}

.moreMenu{
	position: fixed;
	z-index: 999;
	top: 376rpx;
	right: 0;
	width: 236rpx;
	height: 100rpx;
	background: #F5F4F6;
	border-radius:  118rpx 0 0 118rpx ;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 31rpx;
	font-family: alyuan;
	box-shadow: -5rpx 10rpx 10rpx rgba(0, 0, 0, 0.3);
}

.moreMenu img{
	margin-right: 15rpx;
	width: 70rpx;
	height: 40rpx;
}
.pop{
	width: 630rpx;
	height: 814rpx;
	border-radius: 70rpx 0 0 0;
	position: fixed;
	bottom: 0;
	right: -635rpx;
	z-index: 3;
	background-color: #F5F4F6;
	box-shadow: -5rpx -5rpx 10rpx rgba(0, 0, 0, 0.3);
}
@keyframes frame{
	from{
		right:-635rpx
	}
	to{
		right: 0rpx;
	}
}
@keyframes frame2{
	from{
		right:0rpx
	}
	to{
		right: -635rpx;
	}
}
.pop.show{
	animation: frame 0.3s linear;
	animation-fill-mode: forwards;
}
.pop.hidden{
	animation: frame2 0.3s linear;
	animation-fill-mode: forwards;
}
.pop_title{
	margin: 40rpx 0 103rpx;
	display: flex;
	justify-content: center;
	font-size: 31rpx;
	font-family: alyuan;
}
.pop_title img{
	margin-right: 15rpx;
	width: 70rpx;
	height: 40rpx;
}
.pop_item{
	display: flex;
	justify-content: center;
	margin-bottom: 137rpx;
}
.pop_item .btnGroup{
	display: flex;
}
.pop_item .btn{
	margin: 0 56rpx ;
}
.pop_item .btn img{
	width: 70rpx;
	height: 78rpx;
}
.pop_item .line img{
	height:  58rpx;
	width: 4rpx;
}
.pop_item .item_label{
	width: 130rpx;
	text-align: center;
}
.pop_item .item_label text{
	display: block;
	width: 101rpx;
	text-align: center;
	font-size: 32rpx;
	margin: 0 auto;
}
.pop_item .item_label.large text{
	width: 130rpx;
}
.popDreaw{
	background-color: rgba(0, 0, 0, 0);
	position: fixed;
	z-index: 2;
	top: 0;
	left: 0;
	width: 100vw;
	height: 100vh;
}

@keyframes frame3{
	from{
		right:-241rpx
	}
	to{
		right: 0rpx;
	}
}
@keyframes frame4{
	from{
		right:0rpx
	}
	to{
		right: -241rpx;
	}
}
.moreMenu.show{
	animation: frame3 0.3s linear;
	animation-fill-mode: forwards;
}
.moreMenu.hidden{
	animation: frame4 0.3s linear;
	animation-fill-mode: forwards;
}
.menu_item{
	display: flex;
	margin: 20rpx;
	border-bottom: #000 solid 1rpx;
}
.menu_item .item_label{
	width: 300rpx;
	text-align-last:start;
}
.menu_item:active{
	background: #fff;
}
.menu_item .item_icon img{
	width: 60rpx;
	height: 60rpx;
}
.footer{
	margin-top: 20rpx;
	width: 630rpx;
	margin: 0 auto;
}
.footer .group_item{
	display: flex;
	justify-content: space-between;
}
.group_item > view{
	width: 250rpx;
	display: flex;
	justify-content: center;
	height: 80rpx;
	align-items: center;
	background-color: #F5F4F6;
	box-shadow: 10rpx 0 10rpx rgba(0, 0, 0, 0.3);
	border-radius: 80rpx;
	font-family: alyuan;
	transition: 0.1s;
}
.group_item > view:active {
    background-color: rgba(0, 0, 0, 0.2);
}
.group_item .btnIcon{
	width: 40rpx;
	height: 40rpx;	
}
.delBtn{
	color: #ED585B;
}
.changeBtn{
	color: #6EBA8C;
	/* color: #fff; */
}
/* 小程序样式兼容 */
.uni-easyinput{
	width: 240rpx;
	height: 33rpx;
}
</style>
