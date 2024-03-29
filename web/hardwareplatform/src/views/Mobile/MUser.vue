<template>
    <div class="bg">
        <div class="userBox">
            <div class="up">
                <div class="circle">
                    <img src="../../assets/mobile/pic/avatar.png" alt="" class="avatar">
                </div>
                <div class="company">
                    <div class="companyName">
                        <span class="text">{{ companyData.companyName }}</span>
                        <img src="../../assets/mobile/pic/shezhi.png" alt="" @click="toSet">
                    </div>
                    <div class="day">使用：365天</div>
                </div>
            </div>
            <div class="down">
                <div class="title">详细资料</div>
                <div class="tit">
                    <span class="text">公司名称</span>
                    <span class="txtli">{{ companyData.companyName }}</span>
                </div>
                <div class="tit">
                    <span class="text">邮箱地址</span>
                    <span class="txtli">{{ companyData.email }}</span>
                </div>
                <div class="tit">
                    <span class="text">联系方式</span>
                    <span class="txtli">{{ companyData.telephone }}</span>
                </div>
            </div>
        </div>
        <div class="txt">专注无线智能产品及方案</div>
        <tabBar></tabBar>
    </div>
</template>
  
<script>
import tabBar from "../../components/tabBar.vue"
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import apiFun from '../../network/api';

export default {
    components: {
        tabBar
    },
    data() {
        return {
            companyData: [],
            userData: [],
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryCompany();
    },
    methods: {
        // 跳转至信息修改页面
        toSet() {
            this.$router.push('/mUserSet')
        },
        // 获取用户公司信息
        queryCompany() {
            apiFun.getCompany(
                // 发送id参数
                JSON.parse(localStorage.user_data).id
            ).then(
                res => {
                    console.log('公司信息获取成功！')
                    console.log(res)
                    if (JSON.parse(localStorage.user_data).id == res.data[0].userId) {
                        this.companyData = res.data[0]
                        console.log(this.companyData)
                    }
                }
            )
            apiFun.postUserMsg(
            ).then(
                res => {
                    console.log('角色信息获取成功！')
                    console.log(res)
                    if ((localStorage.user_data).id == res.userId) {
                        this.userData = res.user
                        console.log(this.userData)
                    }
                }
            )
        },
    }
}
</script>
  
<style scoped>
/* 登陆背景图 */
.bg {
    position: fixed;
    top: 0;
    left: 0;
    padding: 100px;
    padding-bottom: 0;
    width: 100vw;
    height: 100vh;
    background: url('../../assets/mobile/pic/bg.png');
    background-size: 100vw 100vh;
    background-repeat: no-repeat;
    background-position: center;
}

/* 用户信息窗口 */
.userBox {
  margin: 0 auto;
  padding: 100px;
  width: 100%;
  min-height: 400px;
  border-radius: 50px;
  background: rgba(40, 113, 181, .6);
}

.userBox .up {
    margin-bottom: 100px;
}

.userBox .up .circle {
    margin-right: 50px;
    padding-top: 20px;
    width: 500px;
    height: 500px;
    text-align: center;
    border: 1px solid #D6D8DC;
    border-radius: 300px;
}

.userBox .up .circle .avatar {
    width: 450px;
    height: 450px;
}

.userBox .up .circle, .company {
    display: inline-block;
}

.userBox .up .company {
    vertical-align: top;
    padding-top: 100px;
}

.userBox .up .company .companyName .text {
    font-size: 120px;
    font-weight: 700;
    color: #F5F5F5;
}

.userBox .up .company .companyName img {
    margin-left: 200px;
    width: 150px;
    height: 140px;
}

.userBox .up .company .day {
    font-size: 100px;
    color: #F5F5F5;
}

.userBox .down {
    border-top: 1px solid #F5F5F5;
}

.userBox .down .title {
    margin-top: 100px;
    margin-bottom: 100px;
    font-weight: 700;
    font-size: 120px;
    color: #F5F5F5;
}

.userBox .down .tit {
    margin-bottom: 100px;
    font-size: 100px;
    color: #F5F5F5;
}

.userBox .down .tit .text {
    margin-right: 50px;
    color: #D6D8DC;
}

.userBox .down .tit .txtli {
    color: #F5F5F5;
}

/* 底部小字 */
.bg .txt {
    margin-top: 100px;
    font-family: kaiti;
    text-align: center;
    color: #BBBBBB;
    font-size: 100px;
}
</style>