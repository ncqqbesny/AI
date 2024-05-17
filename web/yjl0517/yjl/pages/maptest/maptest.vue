<template>
	<view>
		<view>
			<!-- <map style="width: 100%; height: 400upx;margin-top: 20upx;" :scale="scale" :latitude="latitude"
				:longitude="longitude" :enable-poi="true" :markers="covers">
			</map> -->
			<map style="width: 100%; height: 300px;" :latitude="latitude" :longitude="longitude" :markers="covers">
							</map>
		</view>
		<button @click="open">使用当前位置</button>
		<button @click="openMap">打开地图</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
					id:0, // 使用 marker点击事件 需要填写id
					title: 'map',
					latitude: 30.312919,
					longitude: 120.066184,
					covers: [{
						id:0,
						latitude: 30.312919,
						longitude: 120.066184,
						iconPath: '/static/icon/makers.png'
					}, {
						id:1,
						latitude: 30.31,
						longitude: 120.06,
						iconPath: '/static/icon/makers.png'
					}]
			}
		},
		methods: {
			test(){
				this.latitude = 30.312909
				this.longitude = 120.066183
			},
			open(){
				let that = this
				uni.getLocation({
					type: 'gcj02',
					success: function (res) {
						// console.log(window)
						that.latitude = res.latitude 
						that.longitude = res.longitude
						console.log(that.latitude,that.longitude)
						console.log('当前位置的经度：' + res.longitude);
						console.log('当前位置的纬度：' + res.latitude);
					}
				});
			},
			openMap(){
				uni.chooseLocation({
					success: function (res) {
		
							this.latitude=res.latitude
							this.longitude=res.longitude
						// console.log(res)
						console.log('位置名称：' + res.name);
						console.log('详细地址：' + res.address);
						console.log('纬度：' + res.latitude);
						console.log('经度：' + res.longitude);
					}
				});

			}
		},
		onShow(){
			this.open()
		}
	}
</script>

<style>

</style>
