<template>
    <div class="searchBar">
        <!-- <img src="../assets/common/pic/search.png" alt=""> -->
        <div class="seachBar_left">
            <el-select v-model="searchForm.mid" class="searchSelect" placeholder="请选择" filterable>
                <el-option v-for="item in midList" :key="item.mid" :label="item.mname" :value="item.mid" />
            </el-select>
            <div class="btn" @click="changeMid()">
                查询
            </div>
            <div class="btn add" @click="toPath()">
                新增
            </div>
            <div class="flag">

            </div>
            <div class="flag">

            </div>
            <div class="flagTitle">
                点位之间距离为
                <span class="unit">
                    20
                </span>
                m
            </div>
        </div>
        <div class="searchBar_right">
            <div class="terminal">
                <span>
                    巡检终端
                </span>
                <img src="../assets/common/pic/terminal.png" alt="">
            </div>
            <div class="ap">
                <span>
                    无线AP
                </span>
                <img src="../assets/common/pic/ap.png" alt="">
            </div>
        </div>
    </div>
    <div class="main">
        <div class="gallery" v-for="(item, index) in newList">
            <div class="line">
                <div class="AP_box" v-for="item2, index2 in item">
                    <div class="bit">
                        <span class="bit_name">{{ item2.displayDeviceName ? item2.displayDeviceName : ' ' }}</span>
                        <div class="bit_img" v-show="item2.status == '1'" @click="visibeChange(item2)">
                            <img src="../assets/common/pic/terminal2.png" alt="">
                        </div>
                        <div class="bit_img" v-show="item2.status != '1'" @click="visibeChange(item2)">
                            <img src="../assets/common/pic/terminal3.png" alt="">
                        </div>
                        <div class="icon">
                            <img src="../assets/common/pic/ap.png" alt="">
                        </div>
                        <div class="car_icon" v-show="checkId(0, item2.car)">
                            <img src="../assets/common/pic/terminal.png" alt="">
                        </div>
                        <div class="car_icon" v-show="carList.length == 0 && index + index2 == 0">
                            <img src="../assets/common/pic/terminal.png" alt="">
                        </div>
                        <div class="infoBox" v-show="getinfoVisible(item2.id)">
                            <div class="ap">
                                <div class="info_title">
                                    <img src="../assets/common/pic/ap.png" alt="">
                                    <span>{{ item2.sortorderDeviceName ? item2.sortorderDeviceName :  '无线接入点'  }}</span>
                                </div>
                                <div class="info_name">
                                    <span>设备名称: &nbsp;&nbsp;{{ item2.acDeviceName }}</span>
                                </div>
                                <div class="info_status">
                                    <span>连接状态: &nbsp;&nbsp;{{ item2.status == '0' ? '断开' : '已连接' }}</span>
                                    <img src="../assets/common/pic/disconnect.png" alt="" v-if="item2.status == '0'">
                                    <img src="../assets/common/pic/link.png" alt="" v-else>
                                </div>
                                <div class="info_ip">
                                    <span>ip地址:&nbsp;&nbsp; {{ item2.acIp }}</span>
                                </div>
                                <div class="info_ip">
                                    <span>MAC地址:&nbsp;&nbsp; {{ item2.acMac }}</span>
                                </div>
                            </div>
                            <div class="carList" v-for="caritem in item2.car">
                                <div class="info_title">
                                    <img src="../assets/common/pic/terminal.png" alt="">
                                    <span>巡检终端</span>
                                </div>
                                <div class="info_name">
                                    <span>设备名称:&nbsp;&nbsp;{{ caritem.acDeviceName }}</span>
                                </div>
                                <div class="info_status">
                                    <span>连接状态:&nbsp;&nbsp;{{ caritem.status == '0' ? '断开' : '已连接' }}</span>
                                    <img src="../assets/common/pic/disconnect.png" alt="" v-if="caritem.status == '0'">
                                    <img src="../assets/common/pic/link.png" alt="" v-else>
                                </div>
                                <div class="info_ip">
                                    <span>ip地址:&nbsp;&nbsp;{{ caritem.acIp }}</span>
                                </div>
                                <div class="info_ip">
                                    <span>MAC地址:&nbsp;&nbsp;{{ caritem.acMac }}</span>
                                </div>
                                
                                <div class="info_ip">
                                    <span>信号:&nbsp;&nbsp;{{ caritem.staSignal + 'db' }}</span>
                                </div>
                                <div class="info_ip">
                                    <span>距离:&nbsp;&nbsp;{{ '基站→' + caritem.displaySortorder + 'm' }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div :class="checkId(20, item2.car) ? 'son_bit' : 'son_bit Offline'">
                        <div class="car_icon" v-show="checkId(20, item2.car)">
                            <img src="../assets/common/pic/terminal.png" alt="">
                        </div>
                    </div>
                    <div :class="checkId(40, item2.car) ? 'son_bit' : 'son_bit Offline'">
                        <div class="car_icon" v-show="checkId(40, item2.car)">
                            <img src="../assets/common/pic/terminal.png" alt="">
                        </div>
                    </div>

                    <div :class="checkId(60, item2.car) ? 'son_bit' : 'son_bit Offline'">
                        <div class="car_icon" v-show="checkId(60, item2.car)">
                            <img src="../assets/common/pic/terminal.png" alt="">
                        </div>
                    </div>

                    <div :class="checkId(80, item2.car) ? 'son_bit' : 'son_bit Offline'">
                        <div class="car_icon" v-show="checkId(80, item2.car)">
                            <img src="../assets/common/pic/terminal.png" alt="">
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!-- <img src="../assets/common/pic/test2.png" alt=""> -->
    </div>
</template>


<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import apiFun from "../network/api"
export default {
    data() {
        return {
            searchForm: {
                mid: ''
            },
            midList: [],
            galleryList: [],
            carList: [],
            newList: [],
            visibleList: []
        }
    },
    methods: {
        // 去项目管理
        toPath() {
            this.$router.replace({ path: '/ProjectManage' })
        },
        // 

        // changeMid
        changeMid() {
            // console.log(this.searchForm.mid)
        },
        async changeMidList() {
            let data = new URLSearchParams()
            data.append("paramCode", JSON.parse(localStorage.user_data).name)
            data.append("paramType", 'ap_location_default')
            await apiFun.getMidList(
                {
                    userId: JSON.parse(localStorage.user_data).id
                }
            ).then(
                res => {
                    // console.log(res)
                    if (res.code == 200) {
                        this.midList = res.data
                        // console.log(this.midList)
                    } else {
                        // alert(res.msg)
                        console.log('查询失败', res)
                    }
                }
            )
            await apiFun.getParams(
                data
            ).then(
                res => {
                    if (res.code == 200) {
                        console.log(res)
                        if (res.data.length > 0) {
                            this.searchForm.mid = parseInt(res.data[0].paramVal)
                        }
                    }
                }

            )
        },
        // 获取管廊内节点数据
        async getGallery() {
            let data = {
                mIds: this.searchForm.mid,
                deviceTypes: 2
            }
            if(!data.mIds){
                return
            }
            
            await apiFun.getNetList(
                data
            ).then(
                res => {
                    if (res.code == 200) {
                        this.galleryList = this.groupArray(res.data, 8);
                        data.deviceTypes = 7
                    }
                }
            )

            // 获取小车位置
            await apiFun.getNetList(
                data
            ).then(
                res => {
                    if (res.code == 200) {
                        // console.log('车的位置',res)
                        this.carList = res.data
                        // 开始融合数组
                        this.newList = this.mergeArray(this.galleryList, this.carList)
                        
                        if (this.visibleList.length == 0) {
                            this.visibleList = JSON.parse(JSON.stringify(this.newList));
                            for (const arr of this.visibleList) {
                                for (const obj of arr) {
                                    obj.infoVisible = false;
                                }
                            }
                            // console.log(this.visibleList)
                        } else {
                            this.upList()
                        }
                        // console.log('融合后的数组',this.newList)
                    }
                }
            )

        },
        // 拆分管廊数据
        groupArray(inputArray, size) {
            const result = [];
            for (let i = 0; i < inputArray.length; i += size) {
                const chunk = inputArray.slice(i, i + size);
                const newChunk = chunk.map(item => ({ ...item, car: [], infoVisible: false }));
                while (newChunk.length <= size) {
                    newChunk.push({ acDeviceName: '', car: [] })
                }
                // console.log('素材1',newChunk)
                result.push(newChunk);
            }
            while (result.length < 3) {
                let data = []
                while (data.length <= size) {
                    data.push({ acDeviceName: '', car: [] })
                }
                result.push(data)
            }
            if (result.length > 3) {
                result.length = 3
            }
            return result;
        },

        // 融合小车和管廊数组
        mergeArray(arrayA, arrayB) {
            let newlist = arrayA
            for (let i = 0; i < newlist.length; i++) {
                // 遍历数组A的每个子数组中的每个对象  
                for (let j = 0; j < newlist[i].length; j++) {
                    // 遍历数组B的每个对象  
                    for (let k = 0; k < arrayB.length; k++) {
                        // 如果数组B的parentId与数组A的id匹配  
                        // console.log('匹配到了！')
                        if (arrayB[k].parentId === newlist[i][j].id) {
                            // 在数组A的对象的car属性中存储匹配的数组B的对象  
                            newlist[i][j].car.push(arrayB[k]);
                            // break;  // 找到匹配项后，无需再检查其他对象，因此使用break跳出循环  
                        }
                    }
                }
            }
            return newlist
        },
        checkId(A, B) {
            // 判断参数B是否为空  
            if (!B || B.length === 0) {
                return false;
            }

            // 遍历参数B中的每一个对象  
            for (let i = 0; i < B.length; i++) {
                // 判断参数A是否与当前对象的displaySortorder字段值相等  
                if (A === B[i].displaySortorder) {
                    return true;
                }
            }

            // 如果参数A与所有对象的displaySortorder字段值都不相等，则返回false  
            return false;
        },
        visibeChange(item) {
            let id = item.id
            // console.log(item.id)
            // 遍历数组中的每个子数组  
            for (let i = 0; i < this.visibleList.length; i++) {
                // 遍历子数组中的每个对象  
                for (let j = 0; j < this.visibleList[i].length; j++) {
                    // 如果对象的id与传入的id匹配  
                    if (this.visibleList[i][j].id === id) {
                        // 更新对象的infoVisible值  
                        this.visibleList[i][j].infoVisible = !this.visibleList[i][j].infoVisible;
                        // console.log(this.visibleList) 
                        // 找到匹配项后，退出循环  

                        return;
                    }
                }
            }
        },
        getinfoVisible(id) {
            if (id) {
                // 遍历this.newList中的每个数组  
                for (let i = 0; i < this.visibleList.length; i++) {
                    // 遍历每个数组中的每个对象  
                    for (let j = 0; j < this.visibleList[i].length; j++) {
                        // 如果找到匹配的id  
                        if (this.visibleList[i][j].id === id) {
                            // 返回该对象的infoVisible字段  
                            return this.visibleList[i][j].infoVisible;
                        }
                    }
                }
            } else {
                // 如果没有找到匹配的id，返回null  
                return false;
            }
        },
        // 更新visiableList
        upList() {
            for (let i = 0; i < this.newList.length; i++) {
                for (let j = 0; j < this.newList[i].length; j++) {
                    for (let key in this.newList[i][j]) {
                        // console.log(this.newList[i][j])
                        if (key !== 'infoVisible') {
                            this.visibleList[i][j][key] = this.newList[i][j][key];
                        }
                    }
                }
            }
            // console.log(this.visibleList)
        }
    },
    mounted() {
        this.changeMidList()
        // this.getGallery()
        
        this.timer = setInterval(() => {
            this.getGallery()
        }, 1000)
    },
    beforeUnmount() {
        if (this.timer) {
            clearInterval(this.timer);
        }
    },
}
</script>

<style scoped>
/* 搜索栏 */
.searchBar {
    width: 2180px;
    height: auto;
    margin: 10px 0 20px 0;
    max-width: 100%;
    border-radius: 5px;
    background: #fff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
    display: flex;
    padding: 20px;
    align-items: center;
    justify-content: space-between;
}

.seachBar_left {
    display: flex;
    align-items: center;
}

:deep(.searchSelect .el-input .el-input__wrapper) {
    height: 50px;
}

.searchBar .btn {
    height: 50px;
    width: 75px;
    background-color: #c3a35d;
    line-height: 50px;
    color: #fff;
    text-align: center;
    margin-right: 20px;
}

.searchBar_right {
    display: flex;
}

.searchBar_right .terminal,
.searchBar_right .ap {
    display: flex;
    flex-direction: column;
    margin-left: 20px;
}

.searchBar_right .terminal img {
    height: 50px;
    width: 85px;
    margin-top: 10px;
}

.searchBar_right .ap img {
    width: 50px;
    height: 50px;
    margin-top: 10px;
}

.main {
    padding: 20px;
    width: 2180px;
    min-height: 714px;
    height: 72vh;
    max-width: 100%;
    overflow: scroll;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

img {
    width: 100%;
}

.flag {
    width: 30px;
    height: 30px;
    border-radius: 15px;
    border: 1px #fff solid;
    background: #009dd9;
    margin-right: 20px;
    box-shadow: 1px 1px 5px 1px rgba(0, 0, 0, 0.2);

}

.flagTitle {
    display: flex;
    height: 40px;
    line-height: 40px;
}

.flagTitle .unit {
    display: inline-block;
    color: #009dd9;
    font-size: 25px;
    line-height: 35px;
}

.gallery {
    display: flex;
    background-image: url(../assets/common/pic/gallery.png);
    background-size: 100% 100%;
    background-repeat: no-repeat;
    height: 200px;
    margin-bottom: 85px;

}

.gallery .line {
    margin-top: 120px;
    margin-left: 80px;
    margin-right: 80px;
    display: flex;
}

.gallery .line .AP_box {
    display: flex;

}

.gallery .line .AP_box .bit {
    position: relative;
}

.gallery .line .AP_box .bit_img {
    height: 50px;
    width: 35px;
    margin: 0 auto;
}

.gallery .line .AP_box .bit_name {
    top: -30px;
    display: inline-block;

}

.gallery .line .AP_box .icon {
    width: 35px;
    height: 35px;
    position: absolute;
    bottom: -40px;
    margin-left: 3px;
}

.gallery .line .AP_box .bit .car_icon img {
    width: 35px;
    height: 15px;
    position: absolute;
    bottom: -80px;
    margin-left: 3px;
}

.gallery .line .AP_box .son_bit {
    position: relative;
    width: 30px;
    height: 30px;
    border-radius: 15px;
    border: 1px #fff solid;
    background: #009dd9;
    margin-right: 12px;
    box-shadow: 1px 1px 5px 1px rgba(0, 0, 0, 0.2);
    margin-top: 40px;
}

.gallery .line .AP_box .son_bit.Offline {
    background: #a8a8a8;
}

.gallery .line .AP_box .son_bit .car_icon {
    width: 35px;
    height: 15px;
    position: absolute;
    bottom: -40px;
}

.infoBox {
    position: absolute;
    width: 320px;
    /* height: 180px; */
    background: #F5F5F5;
    top: 100px;
    border-radius: 15px;
    left: -100px;
    z-index: 999;
    box-shadow: 5px 5px 5px 5px rgba(0, 0, 0, 0.35);
    padding: 20px 30px;
}

.infoBox>div>div {
    line-height: 30px;
    height: 30px;
    font-size: 14px;
    overflow: hidden;
}

.infoBox .info_title {
    line-height: 50px;
    height: 40PX;
    font-size: 18px;
}

.infoBox .ap .info_title img {
    height: 30px;
    width: 30px;
    margin-right: 40px;
}

.infoBox .carList .info_title img {
    height: 30px;
    width: 50px;
    margin-right: 40px;
}

.infoBox .carList {
    margin-top: 10px;
    border-top: 1px solid #000;
}

.infoBox .info_status img {
    height: 20px;
    width: 20px;
}
</style>
