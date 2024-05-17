
//let baseUrl = 'http://192.168.100.201:8060';
let baseUrl = 'https://yjlbase.linkom.com.cn';
let hdUrl = 'https://yjldevice.linkom.com.cn';
//let hdUrl = 'http://192.168.100.201:8061';
//let Material = " http://192.168.100.201:8089/pic/"
let Material = 'https://yjl.linkom.com.cn/loadfile/'
uni.setStorage({
	key:'baseUrl',
	data:baseUrl,
})
uni.setStorage({
	key:'hdUrl',
	data:hdUrl,
})
uni.setStorage({
	key:'Material',
	data:Material,
})
export const myRequest = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: baseUrl + options.url, //接口地址：前缀+方法中传入的地址
			method: options.method || 'GET', //请求方法：传入的方法或者默认是“GET”
			data: options.data || {}, //传递参数：传入的参数或者默认传递空集合
			header: {token:uni.getStorageSync('user_token')},
			success: (res) => {
				//返回的数据（不固定，看后端接口，这里是做了一个判断，如果不为true，用uni.showToast方法提示获取数据失败)
				if (res.data.status == 400) {
					return uni.showToast({
						title: res.data.msg,
						icon: 'none',
						success:function(){
							uni.redirectTo({
								url: '/pages/index/index'
							})
						}
					})
				}
				// 如果不满足上述判断就输出数据
				resolve(res)
			},
			// 这里的接口请求，如果出现问题就输出接口请求失败
			fail: (err) => {
				console.log(err)
				reject(err)
			}
		})
	})
}
export const unCodeRequest = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: baseUrl + options.url, //接口地址：前缀+方法中传入的地址
			method: options.method || 'GET', //请求方法：传入的方法或者默认是“GET”
			data: options.data || {}, //传递参数：传入的参数或者默认传递空集合
			header: {
				token:uni.getStorageSync('user_token'),
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: (res) => {
				//返回的数据（不固定，看后端接口，这里是做了一个判断，如果不为true，用uni.showToast方法提示获取数据失败)
				if (res.data.status == 400) {
					return uni.showToast({
						title: res.data.msg,
						icon: 'none',
						success:function(){
							uni.redirectTo({
								url: '/pages/index/index'
							})
						}
					})
				}
				// 如果不满足上述判断就输出数据
				resolve(res)
			},
			// 这里的接口请求，如果出现问题就输出接口请求失败
			fail: (err) => {
				console.log(err)
				reject(err)
			}
		})
	})
}
export const hdRequest = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: hdUrl + options.url, //接口地址：前缀+方法中传入的地址
			method: options.method || 'GET', //请求方法：传入的方法或者默认是“GET”
			data: options.data || {}, //传递参数：传入的参数或者默认传递空集合
			header: {token:uni.getStorageSync('user_token')},
			success: (res) => {
				//返回的数据（不固定，看后端接口，这里是做了一个判断，如果不为true，用uni.showToast方法提示获取数据失败)
				if (res.data.status == 400) {
					return uni.showToast({
						title: res.data.msg,
						icon: 'none',
						success:function(){
							uni.redirectTo({
								url: '/pages/index/index'
							})
						}
					})
				}
				// 如果不满足上述判断就输出数据
				resolve(res)
			},
			// 这里的接口请求，如果出现问题就输出接口请求失败
			fail: (err) => {
				console.log(err)
				reject(err)
			}
		})
	})
}
export const hdUncodeRequest = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: hdUrl + options.url, //接口地址：前缀+方法中传入的地址
			method: options.method || 'GET', //请求方法：传入的方法或者默认是“GET”
			data: options.data || {}, //传递参数：传入的参数或者默认传递空集合
			header: {
				token:uni.getStorageSync('user_token'),
				'Content-Type': 'application/x-www-form-urlencoded'},
			success: (res) => {
				//返回的数据（不固定，看后端接口，这里是做了一个判断，如果不为true，用uni.showToast方法提示获取数据失败)
				if (res.data.status == 400) {
					return uni.showToast({
						title: res.data.msg,
						icon: 'none',
						success:function(){
							uni.redirectTo({
								url: '/pages/index/index'
							})
						}
					})
				}
				// 如果不满足上述判断就输出数据
				resolve(res)
			},
			// 这里的接口请求，如果出现问题就输出接口请求失败
			fail: (err) => {
				console.log(err)
				reject(err)
			}
		})
	})
}