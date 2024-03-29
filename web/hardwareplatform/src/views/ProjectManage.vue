<template>
    <div class="content">
        <div class="projectMsg">
            <div class="projectMsgMain">
                <div class="activeBar">
                    <el-input v-model="inputContent" class="choose" placeholder="请输入项目编号"></el-input>
                    <div class="btn search" @click="queryProject">查询</div>
                    <div class="btn add" @click="addProjectDialogVisible = true">新增</div>
                    <div class="btn del" @click="delProjectDialogVisible">删除</div>
                </div>
                <div>
                    <el-table :data="projectData" border style="width: 100%" @selection-change="handleSelectionChange">
                        <el-table-column type="selection" class="selection"> </el-table-column>
                        <el-table-column prop="mcode" label="项目编号" class="projectNumber"> </el-table-column>
                        <el-table-column prop="mname" label="项目名称" class="projectName"> </el-table-column>
                        <el-table-column prop="address" label="项目地址" class="projectAddress"> </el-table-column>
                        <el-table-column prop="musername" label="所属用户" class="projectUser"> </el-table-column>
                        <el-table-column prop="mdesc" label="项目备注" class="projectRemarks"> </el-table-column>
                        <el-table-column label="操作" class="projectEdit">
                            <template #default="scope">
                                <el-button text @click="operation(scope.row)">
                                    <span style="color: #C3A35D; text-decoration: underline;">编辑</span>
                                </el-button>
                                <el-button text @click="DialogVisible(scope.row)">
                                    <span style="color: #C3A35D; text-decoration: underline;">设备绑定</span>
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <el-config-provider :locale="locale">
                    <div class="block">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            v-model:currentPage="currentPage" :page-sizes="[10, 30, 50, 100]" :page-size="pageSize"
                            layout="total, sizes, prev, pager, next" :total="total" background>
                        </el-pagination>
                    </div>
                </el-config-provider>
            </div>
        </div>
        <!-- 项目新增对话框 -->
        <el-dialog v-model="addProjectDialogVisible" title="新增项目信息" width="700">
            <el-form-item label="项目编号">
                <el-input v-model="addProjectNumberInput" placeholder="请输入新的项目编号" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目名称">
                <el-input v-model="addProjectNameInput" placeholder="请输入新的项目名称" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目地址">
                <el-input v-model="addProjectAddressInput" placeholder="请输入新的项目地址" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目备注">
                <el-input v-model="addProjectRemarksInput" placeholder="请输入新的项目备注" />
            </el-form-item>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addProjectDialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="addProjectVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 项目编辑对话框 -->
        <el-dialog v-model="operationDialogVisible" title="修改项目信息" width="768px">
            <el-form-item label="项目编号">
                <el-input v-model="form.mcode" placeholder="请输入新的项目编号" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目名称">
                <el-input v-model="form.mname" placeholder="请输入新的项目名称" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目地址">
                <el-input v-model="form.address" placeholder="请输入新的项目地址" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="所属用户">
                <el-input v-model="form.musername" placeholder="请输入新的所属用户" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目备注">
                <el-input v-model="form.mdesc" placeholder="请输入新的项目备注" />
            </el-form-item>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="operationDialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="operationVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 设备绑定对话框 -->
        <el-dialog v-model="bindingDialogVisible" title="设备绑定" width="768px">
            <el-form label-width="130px">
                <el-form-item label="请选择设备类型" id="chooseEquipment" v-show="deathOrlife">
                    <el-select v-model="equipmentValue" class="m-2" placeholder="请选择设备类型" size="large"
                        @change="valueChange">
                        <el-option v-for="item in equipmentOptions" :key="item.equipmentValue" :label="item.label"
                            :value="item.equipmentValue" />
                    </el-select>
                </el-form-item>
                <el-form-item label="请选择设备位置 省">
                    <!-- <el-input v-model="form.address" /> -->
                    <el-select v-model="form.province" placeholder="请选择省份" class="choose">
                        <el-option v-for="item in cityData" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native="cityChange(item.districts)">
                            <!-- @click.native ="areaChange(item.areaList)"  -->
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="市">
                    <!-- <el-input v-model="form.address" /> -->
                    <el-select v-model="form.city" placeholder="请选择市级" class="choose">
                        <el-option v-for="item in cityList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native="areaChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="区" v-if="form.city != ''">
                    <!-- <el-input v-model="form.address" /> -->
                    <el-select v-model="form.area" placeholder="请选择市级" class="choose">
                        <el-option v-for="item in areaList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="请输入设备MAC">
                    <el-input v-model="equipmentMacInput" placeholder="请输入设备MAC" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="bindingDialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="bindingVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import apiFun from '../network/api';
import { cityData } from "../assets/common/js/cityData"

export default {
    name: 'userCenter',
    data() {
        return {
            pageNum: 1,
            pageSize: 10,
            total: 10,
            addProjectDialogVisible: false,
            operationDialogVisible: false,
            bindingDialogVisible: false,
            // 项目信息修改
            addProjectNumberInput: '',
            addProjectNameInput: '',
            addProjectAddressInput: '',
            addProjectUserInput: '',
            addProjectRemarksInput: '',
            // 项目信息修改
            projectNumberInput: '',
            projectNameInput: '',
            projectAddressInput: '',
            projectUserInput: '',
            projectRemarksInput: '',
            // 设备信息绑定
            projectMid: '',
            equipmentMacInput: '',
            locale: zhCn,
            currentPage: 1,
            equipmentOptions: [
                {
                    equipmentValue: '1',
                    label: '网络设备',
                },
                {

                    equipmentValue: '2',
                    label: '器具柜设备',
                }
            ],
            projectData: [],
            projectValue: '',
            equipmentValue: '',
            userValue: '',
            deathOrlife: false,
            // 省市区数据
            form: {},
            cityData: [],
            cityList: [],
            areaList: [],
            inputContent: '',
            projectList: {
                mname: ''
            }
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryProject();
        // 省市区数据获取
        this.getCityData(
            window.Glob.GETCITY_URL,
            {
                key: window.Glob.GETCITY_KEY,
                subdistrict: 3
            }
        )
    },
    methods: {
        // 选择框事件
        handleSelectionChange(val) {
            this.selected = val
            console.log(this.selected)
        },
        // 修改页范围
        handleSizeChange(val) {
            this.pageSize = val
            this.queryProject()
            console.log(`共 ${val} 条数据`)
        },
        // 修改页码
        handleCurrentChange(val) {
            this.pageNum = val
            this.queryProject()
            console.log(`当前页: ${val}`)
        },
        projectSearch() {
            console.log('1')
        },
        // 项目列表新增
        addProjectVisible() {
            if (this.addProjectNumberInput == '' || this.addProjectNameInput == '' || this.addProjectAddressInput == '' || this.addProjectRemarksInput == '') {
                this.$message({
                    message: '请完善项目信息！',
                    type: 'warning'
                });
            } else {
                let addProjectData = {}
                if (this.addProjectNumberInput !== '') {
                    addProjectData.mcode = this.addProjectNumberInput
                } if (this.addProjectNameInput !== '') {
                    addProjectData.mname = this.addProjectNameInput
                } if (JSON.parse(localStorage.user_mid) !== null) {
                    addProjectData.isParent = '1'
                } else {
                    addProjectData.isParent = '0'
                }
                // addProjectData.pageNum = this.pageNum
                // addProjectData.pageSize = this.pageSize
                addProjectData.address = this.addProjectAddressInput
                addProjectData.mdesc = this.addProjectRemarksInput
                addProjectData.parentMid = JSON.parse(localStorage.user_mid)
                apiFun.postPorjectAdd(
                    addProjectData
                ).then(
                    res => {
                        console.log('项目新建提交成功！')
                        console.log(res)
                        this.queryProject()
                    }
                )
                this.addProjectDialogVisible = false,
                    this.$message({
                        message: '项目信息新建成功!',
                        type: 'success'
                    });
            }
        },
        // 项目删除
        delProjectDialogVisible() {
            this.$confirm('确定删除吗', '项目列表删除', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(
                () => {
                    let mids = []
                    this.selected.forEach((project) => mids.push(project.mid))
                    console.log(mids)
                    let data = new URLSearchParams()
                    data.append("mids", mids.join(','))
                    apiFun.postPorjectDelete(
                        data
                    ).then(
                        res => {
                            this.$message({
                                message: '项目列表删除成功!',
                                type: 'success'
                            });
                            console.log(res)
                            this.queryProject()
                        }
                    )
                    return
                }
            ).catch(
                () => {
                    this.$message({
                        message: '取消删除',
                        grouping: true,
                        type: 'message',
                    })
                    return
                }
            )
        },
        // 项目列表获取
        queryProject() {
            let projectList = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                MId: JSON.parse(localStorage.user_mid),
            }
            if (JSON.parse(localStorage.user_mid) !== null) {
                projectList.isParent = '1'
            } else {
                projectList.isParent = '0'
            }
            if (this.inputContent.length != 0) {
                projectList.MName = this.inputContent
            }
            apiFun.getPagingPorject(
                projectList
            ).then(
                res => {
                    console.log('项目列表获取成功！')
                    console.log(res)
                    this.projectData = res.data.list
                    this.total = res.data.total
                    console.log(this.projectData)
                }
            )
        },
        // 项目编辑弹窗
        operation(item) {
            this.operationDialogVisible = true
            this.form = item
        },
        // 项目编辑修改
        operationVisible() {
            if (this.form.mcode == '' || this.form.mname == '' || this.form.address == '' || this.form.musername == '' || this.form.mdesc == '') {
                this.$message({
                    message: '请完善项目信息！',
                    type: 'warning'
                });
            } else {
                let projectData = {}
                if (this.form.mcode !== '') {
                    projectData.mcode = this.form.mcode
                } if (this.form.mname !== '') {
                    projectData.mname = this.form.mname
                } if (this.form.address !== '') {
                    projectData.address = this.form.address
                } if (this.form.musername !== '') {
                    projectData.muserName = this.form.musername
                } if (this.form.mdesc !== '') {
                    projectData.mdesc = this.form.mdesc
                } if (JSON.parse(localStorage.user_mid) !== '') {
                    projectData.isParent = '1'
                } else {
                    projectData.isParent = '0'
                }
                projectData.mid = this.form.mid,
                    apiFun.postPorjectEdit(
                        projectData
                    ).then(
                        res => {
                            this.operationDialogVisible = false,
                                this.$message({
                                    message: '项目信息修改成功!',
                                    type: 'success'
                                });
                            console.log(res)
                            this.queryProject()
                        }
                    )
            }
        },
        // 网络设备信息保存
        netWorkSend() {
            if (this.form.province == '' || this.form.city == '' || this.form.area == '' || this.equipmentMacInput == '') {
                this.$message({
                    message: '请完善设备信息！',
                    type: 'warning'
                });
            } else {
                let netWork = {}
                if (this.form.province !== '') {
                    netWork.province = this.form.province
                } if (this.form.city !== '') {
                    netWork.city = this.form.city
                } if (this.form.area !== '') {
                    netWork.area = this.form.area
                } if (this.equipmentMacInput !== '') {
                    netWork.acMac = this.equipmentMacInput
                }
                netWork.mid = this.projectMid
                netWork.url = ''
                apiFun.postNetworkEquipment(
                    [netWork]
                ).then(
                    res => {
                        if (res.code == 200) {
                            this.bindingDialogVisible = false,
                                this.$message({
                                    message: '设备信息提交成功！!',
                                    type: 'success'
                                });
                        } else {
                            this.$message({
                                message: '存在相同设备编号！不能保存！',
                                type: 'warning'
                            });
                        }
                    }
                )
            }

        },
        // 器具柜设备信息保存
        cabinetSend() {
            if (this.form.province == '' || this.form.city == '' || this.form.area == '' || this.equipmentMacInput == '') {
                this.$message({
                    message: '请完善设备信息！',
                    type: 'warning'
                });
            } else {
                let cabinet = {}
                if (this.form.province !== '') {
                    cabinet.province = this.form.province
                } if (this.form.city !== '') {
                    cabinet.city = this.form.city
                } if (this.form.area !== '') {
                    cabinet.area = this.form.area
                } if (this.equipmentNumberInput !== '') {
                    cabinet.cabinetNo = this.equipmentNumberInput
                } if (this.equipmentMacInput !== '') {
                    cabinet.mac = this.equipmentMacInput
                } if (this.equipmentWebHostInput !== '') {
                    cabinet.ip = this.equipmentWebHostInput
                }
                cabinet.mid = this.projectMid
                cabinet.cabinetId = 0,
                    cabinet.cabinetNo = ''
                cabinet.ventilation = ''
                apiFun.postCabinetEquipment(
                    cabinet
                ).then(
                    res => {
                        if (res.code == 200) {
                            this.bindingDialogVisible = false,
                                this.$message({
                                    message: '设备信息提交成功！',
                                    type: 'success'
                                });
                        } else {
                            this.$message({
                                message: '存在相同设备编号！不能保存！',
                                type: 'warning'
                            });
                        }
                    }
                )
            }
        },
        // 设备信息绑定弹窗
        DialogVisible(item) {
            this.projectMid = item.mid
            apiFun.postUserMsg(

            ).then(
                res => {
                    this.bindingDialogVisible = true
                    console.log(res)
                    let yesOrno = res.user.userType
                    console.log(yesOrno)
                    if (yesOrno == '0' || yesOrno == null) {
                        this.deathOrlife = true;
                    } else {
                        this.deathOrlife = false;
                    }
                }
            )
        },
        // 设备信息绑定
        bindingVisible() {
            let value = this.equipmentValue
            apiFun.postUserMsg(

            ).then(
                res => {
                    let yesOrno = res.user.userType
                    // value = yesOrno
                    console.log(value)
                    if (value == '1') {
                        this.netWorkSend()
                    } if (value == '2') {
                        this.cabinetSend()
                    } if (yesOrno == '1') {
                        this.netWorkSend()
                    } if (yesOrno == '2') {
                        this.cabinetSend()
                    }
                }
            )
        },
        // 获取省市区数据
        getCityData(url, params) {
            // 使用fetch避免出现高德地图接口出现跨域问题，fetch可以调用所有的方法，但是传参困难
            // 拼接参数保证所有参数的传递
            if (params) {
                let paramsArray = [];
                // 拼接参数通过forEach遍历对象内所有属性
                Object.keys(params).forEach(key => paramsArray.push(key + '=' + params[key]))
                if (url.search(/\?/) === -1) {
                    url += '?' + paramsArray.join('&')
                } else {
                    url += '&' + paramsArray.join('&')
                }
            }
            // console.log(url)
            fetch(
                url,
                {
                    // 使用get方法
                    method: 'get'
                }
            ).then(
                response => response.json()
            )
                .then(
                    data => {
                        console.log(data)
                        this.cityData = data.districts[0].districts
                        console.log('当前城市数据', this.cityData)
                    }
                )
        },
        // 省市区切换
        cityChange(val) {
            this.cityList = val
        },
        areaChange(val) {
            console.log('区级', val)
            this.areaList = val
        },
    },
}
</script>

<style scoped>
/* 新增弹窗 */
.optional {
    display: inline-block;
    margin-left: 10px;
    font-size: 14px;
    color: #bbbbbb;
}

/* 右侧内容栏 */
.content {
    width: 2180;
    min-height: 710px;
    height: auto;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

.content .projectMsg {
    padding: 20px;
}

/* 搜索栏 */
.activeBar {
    margin-bottom: 20px;
}

.activeBar .txt {
    margin-right: 20px;
    font-size: 14px;
    color: #333333;
}

.activeBar .choose {
    width: 300px;
    height: 40px;
    border-radius: 0;
}

.el-input__wrapper {
    width: 300px;
    height: 40px;
    border-radius: 0;
    border: 1px solid #D6D8DC;
}

.el-select-dropdown__item {
    height: 40px;
    line-height: 40px;
    color: #333333;
}

/* 按钮样式 */
.activeBar .btn {
    display: inline-block;
    cursor: pointer;
    width: 80px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    font-size: 16px;
    color: #F1F2F6;
    background-color: #C3A35D;
}

.activeBar .search {
    margin-right: 20px;
}

.activeBar .add {
    margin-right: 20px;
    border-radius: 5px;
}

.activeBar .del {
    border-radius: 5px;
}

/* 表格样式 */
.el-table {
    font-size: 16px;
    color: #333333;
}

/* 表格宽度 */
.selection {
    width: 50px;
}

.projectNumber {
    width: 290px;
}

.projectName {
    width: 290px;
}

.projectAddress {
    width: 290px;
}

.projectUser {
    width: 280px;
}

.projectRemarks {
    width: 290px;
}


.projectEdit {
    width: 290px;
}
</style>

<style>
/* 搜索框样式 */
.el-input__wrapper {
    width: 300px;
    height: 40px;
    border-radius: 0;
}

/* 表格样式 */
.el-table .cell {
    padding: 0;
    text-align: center;
}

.el-table__cell {
    padding: 0;
    height: 50px;
}

.el-table__row .el-table__cell:last-child {
    cursor: pointer;
    color: #C3A35D;
    /* text-decoration: underline; */
}

.el-table__body-wrapper .el-table-column--selection>.cell,
.el-table__header-wrapper .el-table-column--selection>.cell {
    display: flex !important;
    justify-content: center;
}

/* 分页器样式 */
.block {
    float: right;
    margin-top: 20px;
    margin-bottom: 20px;
}
</style>
