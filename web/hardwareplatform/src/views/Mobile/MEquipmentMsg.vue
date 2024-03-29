<template>
    <div class="bg">
        <div class="navBar">
            <div>
                <span class="back" @click="backEquipment">
                    返回
                </span>
                <span class="pageName">设备信息</span>
            </div>
        </div>
        <div class="detailMsg">
            <div class="detail">
                <span class="tit">设备名称</span>
                <span class="text">无线控制器</span>
            </div>
            <div class="detail">
                <span class="tit">设备型号</span>
                <span class="text">AC-WG958-1U</span>
            </div>
            <div class="detail">
                <span class="tit">绑定时间</span>
                <span class="text">XX - XX - XX</span>
            </div>
            <div class="detail">
                <span class="tit">工作时长</span>
                <span class="text">XX - XX - XX</span>
            </div>
            <div class="detail">
                <span class="tit">当前版本</span>
                <span class="text">V1.0.0</span>
            </div>
            <div class="detail">
                <span class="tit">接入AP</span>
                <span class="text">XXX</span>
            </div>
            <div class="detail">
                <span class="tit">在线用户</span>
                <span class="text">XXX</span>
            </div>
        </div>
        <div class="txt">专注无线智能产品及方案</div>
    </div>
</template>
  
<script>
export default {
    data() {
        return {

        }
    },
    computed: {
        getNetworkInfo(){
            return this.$store.getters.getNetworkInfo
        }
    },
    methods: {
        backEquipment() {
            this.$router.push('/mEquipment')
        },
    },
    mounted() {
        console.log(this.getNetworkInfo)
    }
}
</script>

<style scoped>
/* 登陆背景图 */
.bg {
    width: 100vw;
    height: 100vh;
    background: url('../../assets/mobile/pic/bg.png');
    background-size: 100vw 100vh;
    background-repeat: no-repeat;
    background-position: center;
}

/* 导航栏 */
.navBar {
    position: relative;
    margin-bottom: 100px;
    padding-left: 100px;
    padding-right: 100px;
    width: 100vw;
    height: 300px;
    text-align: center;
    line-height: 300px;
    background: rgba(40, 113, 181, .6);
    color: #ffffff;
}

.navBar .back {
    position: absolute;
    left: 100px;
    height: 300px;
    line-height: 300px;
    font-size: 100px;
}

.navBar img {
    vertical-align: middle;
    width: 150px;
    height: 150px;
}

.navBar .pageName {
    font-size: 120px;
    font-weight: 700;
}

/* 信息栏 */
.detailMsg {
    padding-left: 100px;
    padding-right: 100px;
    background: rgba(40, 113, 181, .6);
    color: #ffffff;
}

.detailMsg .detail {
    height: 300px;
    line-height: 300px;
    border-bottom: 1px solid #2871B5;
}

.detailMsg .detail:last-child {
    border-bottom: none;
}

.detailMsg .detail .tit {
    margin-right: 100px;
    font-size: 100px;
    color: #D6D8DC;
}

.detailMsg .detail .text {
    font-size: 100px;
    color: #ffffff;
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