<template>
	<view>
		<view class="news_title">
			<!-- {{news.title}} -->
		</view>
		<view class="news_meta">
			<!-- {{news.issueTime}} -->
		</view>
		<view class="new_content">
			<!-- {{news.content}} -->
			<rich-text :nodes="htmlStr"></rich-text>
			<!-- <u-parse :content="htmlStr" noData="正在加载中..." />	 -->
			<!-- </rich-text> -->
		</view>
	</view>
</template>
 
<script>
	import { getDoc } from '../../api/api.js';
	export default {
		data() {
			return {
				gid:'',
				news:{},
				htmlStr:''
			}
		},
		onLoad(item) {
		    this.gid = item.gid
			console.log(this.gid)
		    // 传入需要跳转的链接 使用web-view标签进行跳转
		  },
		methods: {
			getDocList(gid){
				getDoc(
					{
						pageNum:1,
						pageSize:5,
						sort:0,
						gid:gid
					}
				).then(
					res => {
						console.log(res)
						if(res.data.code == 200){
						this.news = res.data.data.list[0]
						let text = res.data.data.list[0].content
						console.log(text) 
							// 将文本按换行符分割成段落  
						const paragraphs = text.split('\r\n');
						console.log(paragraphs)
						// 创建HTML字符串  
						let html = '';  
						  
						// 添加标题  
						html += '<h1>' + paragraphs[0] + '</h1>';  
						  
						// 遍历段落并添加到HTML中  
						for (let i = 1; i < paragraphs.length; i++) {  
							if (paragraphs[i].startsWith('信息来源：') || paragraphs[i].startsWith('发布日期：')) {  
								html += '<p class="info">' + paragraphs[i] + '</p>';  
							} else {  
								html += '<p>' + paragraphs[i] + '</p>';  
							}  
						}  
						  
						    html += '<div class="footer"></div>';  
						  
						   this.htmlStr = html
						
						  
					
							  
							// 输出或使用HTML  
							console.log(html);
						
						}
					}
				)
			},
		},
		onShow(){
			this.getDocList(this.gid)
		}
	}
</script>
 
<style>
	.new_content{
		width: 710rpx;
		margin: 20rpx auto;
	}
h1{
	margin-top: 25rpx;
	line-height: 64rpx;
	text-align: center;
}
 .info{
	 text-align: center;
 }
 p{
	line-height: 48rpx;
	 margin-top: 10rpx;
	 text-indent: 2em;
 }
 .footer{
	 height: 100rpx;
 }
</style>