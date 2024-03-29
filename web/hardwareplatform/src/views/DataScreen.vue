<template>
    <div class="main">
        <div class="title">
            <img src="../assets/common/pic/logoTW2.png" alt="" class="logo">
            <span class="tit">云智能维护平台</span>
        </div>
        <div class="toPlatform" @click="toPlatform">
            <img src="../assets/common/icons/convert.png" alt="">
            操作中心
        </div>
        <div class="content">
            <div class="fl">
                <div class="area">
                    <div class="areaTit">设备地区分布</div>
                    <district></district>
                </div>
                <div class="ranking">
                    <div class="rankingTit">设备数量排行</div>
                    <span class="rankingFl ">设备名称</span>
                    <span class="rankingFr">设备数量</span>
                    <bar-chart></bar-chart>
                </div>
            </div>
            <div class="center">
                <div class="welcome">
                    欢迎来到LINKOM云智能维护平台！
                </div>
                <div class="sum">
                    <div class="online">
                        <div class="text">当前设备在线总数</div>
                        <div class="txt">{{ online }}</div>
                    </div>
                    <div class="offline">
                        <div class="text">当前设备离线总数</div>
                        <div class="txt">{{ offline }}</div>
                    </div>
                </div>
                <div class="map">
                    <chartsMap></chartsMap>
                </div>
            </div>
            <div class="fr">
                <div class="visitor">
                    <div class="visitorTit">平台访客数量</div>
                    <div class="visit">
                        <div class="txt">{{ visitNumberPeople }}</div>
                        <div class="pic">
                            <img src="../assets/common/pic/circle.png" alt="">
                        </div>
                        <div class="text">来访人数</div>
                    </div>
                    <div class="sign">
                        <div class="txt">{{ signNumberPeople }}</div>
                        <div class="pic">
                            <img src="../assets/common/pic/circle.png" alt="">
                        </div>
                        <div class="text">注册人数</div>
                    </div>
                </div>
                <div class="control">
                    <div class="controlTit">设备信息监控</div>
                    <control></control>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// echarts组件导入
// 左侧图表
import district from '../components/echarts/District.vue'
import barChart from '../components/echarts/BarChart.vue'

// 中间地图
import chartsMap from '../components/echarts/Map.vue'
import apiFun from '../network/api'
// 右侧图表
import control from '../components/echarts/Control.vue'

console.log(window.innerHeight)

export default {
    name: 'dataScreen',
    components: {
        district,
        chartsMap,
        control,
        barChart
    },
    data() {
        return {
            online: '',
            offline: '',
            visitNumberPeople: '',
            signNumberPeople: '',
        }
    },
    mounted() {
        // this.reSise()
        // window.addEventListener('resize', this.reSise)
        this.getOAndO()
    },
    methods: {
        toPlatform() {
            this.$router.push('/Platform')
        },
        // 设备在线数与离线数获取
        // 来访人数与注册人数获取
        getOAndO() {
            apiFun.getOAndO(
                {
                    // 发送mid参数
                    mId: JSON.parse(localStorage.user_mid)
                }
            ).then(
                res => {
                    console.log('设备在线数与离线数获取成功！')
                    console.log(res)
                    this.offline = res.data.onlineDevice[0].offline
                    this.online = res.data.onlineDevice[1].online
                    this.visitNumberPeople = res.data.onlineUser
                    this.signNumberPeople = res.data.regUser
                }
            )
        },
        // reSise() {
        // 判断窗口赋值高度
        //     const winHeight = window.innerHeight;
        //     if (winHeight > 1297) {
        //         console.log('全屏化')
        //         this.$refs.flBoxUp.style.height = '390px'
        //         this.$refs.flBoxMid.style.height = '390px'
        //         this.$refs.flBoxDown.style.height = '390px'
        //         this.$refs.centerBox.style.height = '982px'
        //         this.$refs.frBoxUp.style.height = '595px'
        //         this.$refs.frBoxDown.style.height = '595px'
        //     } else {
        //         console.log('窗口化')
        //         this.$refs.flBoxUp.style.height = '342.334px'
        //         this.$refs.flBoxMid.style.height = '342.334px'
        //         this.$refs.flBoxDown.style.height = '342.334px'
        //         this.$refs.centerBox.style.height = '839px'
        //         this.$refs.frBoxUp.style.height = '523px'
        //         this.$refs.frBoxDown.style.height = '523px'
        //     }
        // }
    },
}
</script>

<style scoped>
body {
    overflow: hidden;
}

/* 主体 */
.main {
    position: relative;
    padding-top: 10px;
    width: 100vw;
    height: 100vh;
    background-image: url("../assets/common/pic/bg.jpg");
    background-size: 100vw 100vh;
    background-repeat: no-repeat;
    background-position: center;
}

/* 标题 */
.main .title {
    text-align: center;
    margin-bottom: 40px;
}

.main .title .logo {
    margin-right: 15px;
    height: 60px;
    vertical-align: middle;
}

.main .title .tit {
    font-size: 60px;
    background-image: linear-gradient(to bottom, #b5d1dd, #85d6ef);
    color: transparent;
    -webkit-background-clip: text;
}

/* 跳转按钮 */
.toPlatform {
    position: absolute;
    right: 60px;
    top: 60px;
    cursor: pointer;
    border-radius: 25px;
    width: 120px;
    height: 35px;
    text-align: center;
    line-height: 35px;
    font-size: 18px;
    color: #fff;
    background-color: #2a6cf0;
    box-shadow: 0 0 20px rgba(113, 200, 245, 0.3);
    vertical-align: middle;
}

.toPlatform img {
    width: 20px;
}

/* 左列 */
.content .fl {
    margin-right: 20px;
    padding-left: 40px;
    width: 600px;
    height: 100%;
    color: #fff;
}

.content .fl .area {
    width: 600px;
    height: 400px;
}

.content .fl .area .areaTit {
    padding-left: 40px;
    margin-bottom: 35px;
    width: 540px;
    height: 40px;
    background-image: url("../assets/common/pic/titleBox.png");
    background-size: 540px 40px;
    background-repeat: no-repeat;
    background-position: center;
    line-height: 40px;
    font-size: 24px;
}

.content .fl .ranking .rankingTit {
    padding-left: 40px;
    margin-bottom: 20px;
    width: 540px;
    height: 40px;
    background-image: url("../assets/common/pic/titleBox.png");
    background-size: 540px 40px;
    background-repeat: no-repeat;
    background-position: center;
    line-height: 40px;
    font-size: 24px;
}

.content .fl .ranking .rankingFl {
    margin-left: 20px;
    font-size: 16px;
    color: #70c7f4;
}

.content .fl .ranking .rankingFr {
    margin-right: 45px;
    float: right;
    font-size: 16px;
    color: #70c7f4;
}

/* 中间 */
.content .center {
    display: inline-block;
    margin-right: 20px;
    width: 1320px;
    height: 100%;
    color: #fff;
}

.content .center .welcome {
    margin: 0 auto;
    margin-bottom: 15px;
    padding-left: 60px;
    width: 1100px;
    height: 40px;
    background-image: url("../assets/common/pic/welcome.png");
    background-size: 1100px 40px;
    background-repeat: no-repeat;
    background-position: center;
    font-size: 18px;
    line-height: 40px;
}

.content .center .sum {
    margin: 0 auto;
    padding-left: 295px;
}

.content .center .sum .online {
    margin-right: 200px;
    text-align: center;
    display: inline-block;
}

.content .center .sum .offline {
    text-align: center;
    display: inline-block;
}

.content .center .sum .online .text {
    font-size: 35px;
    margin-bottom: 10px;
}

.content .center .sum .offline .text {
    font-size: 35px;
    margin-bottom: 10px;
}

.content .center .sum .online .txt {
    margin: 0 auto;
    width: 250px;
    height: 100px;
    background-image: url("../assets/common/pic/box.png");
    background-size: 250px 100px;
    background-repeat: no-repeat;
    background-position: center;
    font-size: 35px;
    color: #70c7f4;
    line-height: 100px;
}

.content .center .sum .offline .txt {
    margin: 0 auto;
    width: 250px;
    height: 100px;
    background-image: url("../assets/common/pic/box.png");
    background-size: 250px 100px;
    background-repeat: no-repeat;
    background-position: center;
    font-size: 35px;
    color: #70c7f4;
    line-height: 100px;
}

/* 右列 */
.content .fr {
    width: 600px;
    height: 100%;
    color: #fff;
}

.content .fr .visitor {
    width: 600px;
    height: 320px;
}

.content .fr .visitor .visitorTit {
    margin-bottom: 50px;
    padding-left: 40px;
    width: 540px;
    height: 40px;
    background-image: url("../assets/common/pic/titleBox.png");
    background-size: 540px 40px;
    background-repeat: no-repeat;
    background-position: center;
    line-height: 40px;
    font-size: 24px;
}

.content .fr .visitor .visit {
    display: inline-block;
    margin-right: 100px;
    padding-left: 60px;
}

.content .fr .visitor .visit .txt {
    margin-top: 20px;
    text-align: center;
    color: #70c7f4;
    font-size: 24px;
}

.content .fr .visitor .visit .pic {
    margin-top: 20px;
}

.content .fr .visitor .visit .text {
    margin-top: 20px;
    text-align: center;
    color: #70c7f4;
    font-size: 24px;
}

.content .fr .visitor .sign {
    display: inline-block;
}

.content .fr .visitor .sign .txt {
    margin-top: 20px;
    text-align: center;
    color: #70c7f4;
    font-size: 24px;
}

.content .fr .visitor .sign .pic {
    margin-top: 20px;
}

.content .fr .visitor .sign .text {
    margin-top: 20px;
    text-align: center;
    color: #70c7f4;
    font-size: 24px;
}

.content .fr .control .controlTit {
    margin-bottom: 40px;
    padding-left: 40px;
    width: 540px;
    height: 40px;
    background-image: url("../assets/common/pic/titleBox.png");
    background-size: 540px 40px;
    background-repeat: no-repeat;
    background-position: center;
    line-height: 40px;
    font-size: 24px;
}
</style>
