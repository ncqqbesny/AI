<template>
    <div class="bg">
        <div class="container">
            <div class="filterBar">
                <span class="title">设备状态</span>
                <span class="fr" @click="toggle">
                    <span class="filter">切换</span>
                    <img src="../../assets/mobile/pic/qiehuan.png" alt="">
                </span>
            </div>
            <div class="main">
                <div class="equipment" v-for="(item, index) in networkData" v-show="!cabinet" @click="toMsg">
                    <div class="equipmentBar">
                        <span class="name">{{ item.acDeviceName }}</span>
                        <span :class="item.status == 1 ? 'on' : 'off'" >{{ item.status == 1 ?  '正常' : '掉线'}}</span>
                    </div>
                    <div class="equipmentMsg">
                        <span class="pic">
                            <img src="../../assets/mobile/pic/1.png" alt="">
                        </span>
                        <span class="locationAndTime">
                            <div>
                                <img src="../../assets/mobile/pic/weibiaoti-3.png" alt="">
                                <span>{{ item.province }}{{ item.city }}{{ item.area }}</span>
                            </div>
                            <div>
                                <img src="../../assets/mobile/pic/31shijian.png" alt="">
                                <span>{{ item.createTime }}</span>
                            </div>
                        </span>
                    </div>
                </div>
                <div class="equipment" v-for="(item, index) in cabinetData" v-show="cabinet" @click="toMsg">
                    <div class="equipmentBar">
                        <span class="name">{{ item.cabinetName }}</span>
                        <span :class="item.status == 1 ? 'on' : 'off'" >{{ item.status == 1 ?  '正常' : '掉线'}}</span>
                    </div>
                    <div class="equipmentMsg">
                        <span class="pic">
                            <img src="../../assets/mobile/pic/4.png" alt="">
                        </span>
                        <span class="locationAndTime">
                            <div>
                                <img src="../../assets/mobile/pic/weibiaoti-3.png" alt="">
                                <span>{{ item.province }}{{ item.city }}{{ item.area }}</span>
                            </div>
                            <div>
                                <img src="../../assets/mobile/pic/31shijian.png" alt="">
                                <span>{{ item.createTime }}</span>
                            </div>
                        </span>
                    </div>
                </div>
            </div>
            <!-- <div class="txt">专注无线智能产品及方案</div> -->
            <tabBar></tabBar>
        </div>
    </div>
</template>

<script>
import tabBar from '../../components/tabBar.vue';
import apiFun from '../../network/api';

export default {
    components: {
        tabBar
    },
    data() {
        return {
            cabinetData: [],
            networkData: [],
            cabinet: false
        }
    },
    mounted() {
        this.getNetWorkList()
        this.getCabList()
    },
    methods: {
        // 设备详情页
        // toMsg(index) {
        //     console.log(this.networkData[index])
        //     this.$store.commit('setNetworkInfo', this.networkData[index])
        //     this.$router.push('/mEquipmentMsg')
        // },
        // 网络设备与器具柜设备切换
        toggle() {
            this.cabinet = !this.cabinet
            console.log('切换', this.cabinet)
        },
        // 获取网络设备
        getNetWorkList() {
            console.log("网络设备列表获取")
            let data = {
                pageNum: 1,
                pageSize: 10,
                deviceType: this.deviceType,
                sort: 0,
                MId: localStorage.getItem("user_mid")
            }
            apiFun.getNetWorkList(
                data
            ).then(
                res => {
                    console.log(res)
                    if (res.code == 200) {
                        this.networkData = res.data.list
                        this.total = res.data.total
                    } else {
                        console.log('查询网络设备列表失败', res)
                    }
                }
            )
        },
        // 获取器具柜设备
        getCabList() {
            console.log("器具柜列表获取")
            let data = {
                pageNum: 1,
                pageSize: 10,
                sort: 0,
                isMain: 1,
                MId: localStorage.getItem("user_mid")
            }
            apiFun.getPageCabList(
                data
            ).then(
                res => {
                    console.log(res)
                    if (res.code == 200) {
                        this.cabinetData = res.data.list
                        this.total = res.data.total
                    } else {
                        console.log('查询器具柜设备列表失败', res)
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
    width: 100vw;
    height: 100vh;
    background: url('../../assets/mobile/pic/bg.png');
    background-size: 100vw 100vh;
    background-repeat: no-repeat;
    background-position: center;
}

.container {
    margin-left: 50px;
    margin-right: 50px;
    overflow: scroll;
    height: 100vh;
}

/* 筛选栏 */
.filterBar {
    margin-top: 50px;
    margin-bottom: 50px;
    padding-left: 100px;
    padding-right: 100px;
    width: 100%;
    height: 300px;
    line-height: 300px;
    border-radius: 30px;
    background: rgba(40, 113, 181, 1);
    color: #ffffff;
    font-size: 100px;
}

.filterBar .title {
    float: left;
}

.filterBar img {
    margin-left: 50px;
    width: 100px;
    height: 100px;
}

/* 设备盒 */
.main {
    /* margin-top: 400px; */
    height: 100vh;
    overflow: scroll;
}

.equipment {
    margin-bottom: 50px;
    padding-left: 50px;
    padding-right: 50px;
    border-radius: 30px;
    width: 100%;
    height: 1050px;
    background: rgba(40, 113, 181, .6);
}

.equipment:last-child {
    margin-bottom: 0;
}

.equipment .equipmentBar {
    padding-left: 50px;
    padding-right: 50px;
    height: 300px;
    line-height: 300px;
    text-align: right;
    border-bottom: 1px solid #2871B5;
}

.equipment .equipmentBar .name {
    float: left;
    color: #ffffff;
    font-size: 100px;
}

.equipment .equipmentBar .on {
    display: none;
    display: inline-block;
    width: 250px;
    height: 150px;
    text-align: center;
    line-height: 150px;
    font-size: 80px;
    border-radius: 30px;
    color: #4B7902;
    background-color: #CAF982;
    border: 10px solid #70B603;
}

.equipment .equipmentBar .off {
    display: none;
    display: inline-block;
    width: 250px;
    height: 150px;
    text-align: center;
    line-height: 150px;
    font-size: 80px;
    border-radius: 30px;
    color: #A30014;
    background-color: #EC808D;
    border: 10px solid #D9001B;
}

.equipment .equipmentMsg {
    position: relative;
    height: 700px;
}

.equipment .equipmentMsg .pic img {
    position: absolute;
    left: 50px;
    bottom: 50px;
    width: 400px;
}

.equipment .equipmentMsg .locationAndTime {
    position: absolute;
    right: 50px;
    bottom: 50px;
    text-align: right;
    font-size: 100px;
    color: #ffffff;
}

.equipment .equipmentMsg .locationAndTime img {
    margin-right: 5px;
    width: 120px;
    vertical-align: middle;
}

/* 底部小字 */
.bg .txt {
    margin-top: 100px;
    margin-bottom: 100px;
    font-family: kaiti;
    text-align: center;
    color: #BBBBBB;
    font-size: 100px;
}
</style>