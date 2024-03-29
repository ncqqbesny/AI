<template>
    <div>
        <div class="fl">
            <div class="title" :class="{ active: switchIndex == index }" v-for="(item, index) in icons" :key="item.name"
                @click="switchClick(index)" ref="userTitle">
                <span :class="'pic iconfont ' + item.name">{{ item.icon }}</span>
                <span class="tit">{{ item.title }}</span>
            </div>
        </div>
        <div class="fr">
            <div class="userMsg" v-if="switchIndex == 0" :data="companyData">
                <div class="userMsgUp">
                    <div class="circle">
                        <img src="../assets/common/pic/logoY.png" alt="" class="avatar">
                    </div>
                    <div class="companyMsg">
                        <div class="company">
                            <span>{{ companyData.companyName }}</span>
                            <el-button text @click="companyDialogVisible = true">
                                <span
                                    style="font-weight: 700; color: #C3A35D; text-decoration: underline; cursor: pointer;">修改</span>
                            </el-button>
                        </div>
                        <div>
                            <span class="userTime">使用：</span><span class="days">365天</span>
                        </div>
                    </div>
                </div>
                <div class="userMsgDown">
                    <div class="userMsgDownTitle">详细资料</div>
                    <div>
                        <div class="userMsgDownLi">
                            <span class="userMsgDownTit">公司名称</span>
                            <span class="userMsgDownDetails">{{ companyData.companyName }}</span>
                        </div>
                        <div class="userMsgDownLi">
                            <span class="userMsgDownTit">邮箱地址</span>
                            <span class="userMsgDownDetails">{{ companyData.email }}</span>
                        </div>
                        <div class="userMsgDownLi">
                            <span class="userMsgDownTit">联系方式</span>
                            <span class="userMsgDownDetails">{{ companyData.telephone }}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="accountMsg" v-if="switchIndex == 1" :data="userData">
                <div class="accountMsgTitle">账户</div>
                <div class="accountMsgLi email">
                    <span class="accountMsgTit">邮箱地址</span><span class="accountMsgDetails">{{ userData.email }}</span>
                    <el-button text @click="msgDialogVisible('email')" class="modify">
                        修改
                    </el-button>
                </div>
                <div class="accountMsgLi number">
                    <span class="accountMsgTit">联系方式</span><span class="accountMsgDetails">{{ userData.telephone }}</span>
                    <el-button text @click="msgDialogVisible('number')" class="modify">
                        修改
                    </el-button>
                </div>
                <div class="accountMsgLi password">
                    <span class="accountMsgTit">平台密码</span><span class="accountMsgDetails">********</span>
                    <el-button text @click="msgDialogVisible('password')" class="modify">
                        修改
                    </el-button>
                </div>
            </div>
            <div class="projectMsg" v-if="switchIndex == 2">
                <div class="projectMsgMain">
                    <div class="activeBar">
                        <el-select v-model="projectValue" placeholder="请选择" class="choose">
                            <el-option v-for="item in projectOptions" :key="item.projectValue" :label="item.label"
                                :value="item.projectValue" class="optionLi">
                            </el-option>
                        </el-select>
                        <div class="btn search" @click="projectListSearch">查询</div>
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
                                <el-button text @click="operationDialogVisible = true">
                                    <span style="color: #C3A35D; text-decoration: underline;">编辑</span>
                                </el-button>
                                <el-button text @click="bindingDialogVisible = true">
                                    <span style="color: #C3A35D; text-decoration: underline;">设备绑定</span>
                                </el-button>
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
            <div class="employeeMsg" v-if="switchIndex == 3">
                <div class="employeeMsgMain">
                    <div class="activeBar">
                        <el-select v-model="userValue" placeholder="请选择" class="choose">
                            <el-option v-for="item in userOptions" :key="item.userValue" :label="item.label"
                                :value="item.userValue" class="optionLi">
                            </el-option>
                        </el-select>
                        <div class="btn search">查询</div>
                        <div class="btn add" @click="addRoleDialogVisible = true">新增</div>
                        <div class="btn del" @click="delRoleDialogVisible">删除</div>
                    </div>
                    <div>
                        <el-table :data="roleData" border style="width: 100%" @selection-change=roleSelectionChange>
                            <el-table-column type="selection" class="selection"> </el-table-column>
                            <el-table-column prop="name" label="用户名称" class="projectNumber"> </el-table-column>
                            <el-table-column prop="mid" label="用户ID" class="projectName"> </el-table-column>
                            <el-table-column prop="telephone" label="联系方式" class="projectAddress"> </el-table-column>
                            <el-table-column prop="email" label="邮箱地址" class="projectUser"> </el-table-column>
                            <el-table-column prop="createTime" label="创建时间" class="projectRemarks"> </el-table-column>
                            <el-table-column label="操作" class="projectEdit">
                                <span style="color: #C3A35D; text-decoration: underline;">编辑</span>
                            </el-table-column>
                        </el-table>
                    </div>
                    <el-config-provider :locale="locale">
                        <div class="block">
                            <el-pagination @size-change="roleSizeChange" @current-change="roleCurrentChange"
                                v-model:currentPage="currentPage" :page-sizes="[10, 30, 50, 100]" :page-size="pageSize"
                                layout="total, sizes, prev, pager, next" :total="roletotal" background>
                            </el-pagination>
                        </div>
                    </el-config-provider>
                </div>
            </div>
        </div>
        <!-- 修改公司信息对话框 -->
        <el-dialog v-model="companyDialogVisible" title="修改公司名称" width="768px">
            <el-input v-model="companyInput" placeholder="请输入新的公司名称" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="companyDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="companyVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 修改用户信息对话框 -->
        <el-dialog v-model="dialogVisible" :title="msgDialogTitle" width="30%" :before-close="handleClose">
            <el-input v-model="emailInput" placeholder="请输入新的邮箱地址" v-show="type == 'email'" />
            <el-input v-model="numberInput" placeholder="请输入新的联系方式" v-show="type == 'number'" />
            <el-input v-model="passwordInput" placeholder="请输入新的平台密码" v-show="type == 'password'" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="msgVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 项目新增对话框 -->
        <el-dialog v-model="addProjectDialogVisible" title="新增项目信息" width="768px">
            <el-form-item label="项目编号">
                <el-input v-model="addProjectNumberInput" placeholder="请输入新的项目编号" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目名称">
                <el-input v-model="addProjectNameInput" placeholder="请输入新的项目名称" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目地址">
                <el-input v-model="addProjectAddressInput" placeholder="请输入新的项目地址" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="所属用户">
                <el-input v-model="addProjectUserInput" placeholder="请输入新的所属用户" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目备注">
                <el-input v-model="addProjectRemarksInput" placeholder="请输入新的项目备注" />
            </el-form-item>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addProjectDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addProjectVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 项目编辑对话框 -->
        <el-dialog v-model="operationDialogVisible" title="修改项目信息" width="768px">
            <el-form-item label="项目编号">
                <el-input v-model="projectNumberInput" placeholder="请输入新的项目编号" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目名称">
                <el-input v-model="projectNameInput" placeholder="请输入新的项目名称" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目地址">
                <el-input v-model="projectAddressInput" placeholder="请输入新的项目地址" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="所属用户">
                <el-input v-model="projectUserInput" placeholder="请输入新的所属用户" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="项目备注">
                <el-input v-model="projectRemarksInput" placeholder="请输入新的项目备注" />
            </el-form-item>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="operationDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="operationVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 设备绑定对话框 -->
        <el-dialog v-model="bindingDialogVisible" title="设备绑定" width="768px">
            <el-form>
                <el-form-item label="请选择设备类型" id="chooseEquipment">
                    <el-select v-model="equipmentValue" class="m-2" placeholder="请选择设备类型" size="large"
                        @change="valueChange">
                        <el-option v-for="item in equipmentOptions" :key="item.equipmentValue" :label="item.label"
                            :value="item.equipmentValue" />
                    </el-select>
                </el-form-item>
                <el-form-item label="请输入设备位置">
                    <el-input v-model="equipmentPleaceInput" placeholder="请输入设备位置" />
                </el-form-item>
                <el-form-item label="请输入设备编号">
                    <el-input v-model="equipmentNumberInput" placeholder="请输入设备编号" />
                </el-form-item>
                <el-form-item label="请输入设备MAC">
                    <el-input v-model="equipmentMacInput" placeholder="请输入设备MAC" />
                </el-form-item>
                <el-form-item label="请输入WEB端口号">
                    <el-input v-model="equipmentWebHostInput" placeholder="请输入WEB端口号" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="bindingDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="bindingVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 用户新增对话框 -->
        <el-dialog v-model="addRoleDialogVisible" title="新增用户信息" width="768px">
            <el-form-item label="用户名称">
                <el-input v-model="roleNameInput" placeholder="请输入新的用户名称" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="用户密码">
                <el-input v-model="rolePasswordInput" placeholder="请输入新的用户密码" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input v-model="roleuNumberInput" placeholder="请输入新的联系方式" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="邮箱地址">
                <el-input v-model="roleEmailInput" placeholder="请输入新的邮箱地址" style="margin-bottom: 10px;" />
            </el-form-item>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addRoleDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addRoleVisible">
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

export default {
    name: 'userCenter',
    data() {
        return {
            pageNum: 1,
            pageSize: 10,
            total: 10,
            switchIndex: 0,
            companyDialogVisible: false,
            dialogVisible: false,
            addProjectDialogVisible: false,
            operationDialogVisible: false,
            bindingDialogVisible: false,
            addRoleDialogVisible: false,
            // 用户信息修改
            companyInput: '',
            emailInput: '',
            numberInput: '',
            passwordInput: '',
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
            equipmentPleaceInput: '',
            equipmentNumberInput: '',
            equipmentMacInput: '',
            equipmentWebHostInput: '',
            // 用户信息绑定
            roleNameInput: '',
            rolePasswordInput: '',
            roleuNumberInput: '',
            roleEmailInput: '',
            icons: [
                {
                    name: "user",
                    title: "个人信息",
                    icon: "\ue613"
                },
                {
                    name: "account",
                    title: "账号安全",
                    icon: "\ue8ab"
                },
                {
                    name: "project",
                    title: "项目管理",
                    icon: "\ue614"
                },
                {
                    name: "employee",
                    title: "用户管理",
                    icon: "\ue60a"
                },
            ],
            locale: zhCn,
            currentPage: 1,
            projectOptions: [
                {
                    projectValue: '1',
                    label: '不论状态',
                },
                {
                    projectValue: '2',
                    label: '当前在线',
                },
                {
                    projectValue: '3',
                    label: '掉线状态',
                }
            ],
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
            userOptions: [
                {
                    userValue: '1',
                    label: '网络设备',
                },
                {
                    userValue: '2',
                    label: '器具柜设备',
                },
                {
                    userValue: '3',
                    label: '器具柜设备',
                }
            ],
            companyData: [],
            userData: [],
            projectData: [],
            roleData: [],
            projectValue: '',
            equipmentValue: '',
            userValue: '',
            type: '',
        }
    },
    computed: {
        msgDialogTitle() {
            return this.type == 'email' ? '修改邮箱地址' : this.type == 'number' ? '修改联系方式' : '修改平台密码'
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryCompany();
        this.queryProject();
        this.queryRole();
    },
    methods: {
        // 左侧菜单栏切换控制
        switchClick(index) {
            this.switchIndex = index
            this.$refs.userTitle[index].classList.value += ' userTitle'
        },
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
        // 用户选择框事件
        roleSelectionChange(val) {
            this.selected = val
            console.log(this.selected)
        },
        // 用户修改页范围
        roleSizeChange(val) {
            this.pageSize = val
            this.queryRole()
            console.log(`共 ${val} 条数据`)
        },
        // 用户修改页码
        roleCurrentChange(val) {
            this.pageNum = val
            this.queryRole()
            console.log(`当前页: ${val}`)
        },
        // 修改用户信息对话框弹出控制
        msgDialogVisible(type) {
            this.type = type
            this.dialogVisible = true
        },
        // 获取公司信息并展示
        // 获取角色信息并展示
        // 获取用户列表并展示
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
        // 公司名称修改
        companyVisible() {
            if (this.companyInput == '') {
                this.$message({
                    message: '请输入您要修改的信息！',
                    type: 'warning'
                });
            } else {
                apiFun.postModifyCompany(
                    {
                        // 发送id参数
                        userId: JSON.parse(localStorage.user_data).id,
                        companyName: this.companyInput
                    }
                ).then(
                    res => {
                        console.log('公司信息提交成功！')
                        console.log(res)
                        this.queryCompany()
                    }
                )
                this.companyDialogVisible = false,
                    this.$message({
                        message: '公司名称修改成功',
                        type: 'success'
                    });
            }
        },
        // 用户信息修改
        msgVisible() {
            if (this.type == 'email' && this.emailInput == '' || this.type == 'number' && this.numberInput == '' || this.type == 'password' && this.passwordInput == '') {
                this.$message({
                    message: '请输入您要修改的信息！',
                    type: 'warning'
                });
            } else {
                let userData = {}
                if (this.emailInput !== '') {
                    userData.email = this.emailInput
                } if (this.numberInput !== '') {
                    userData.telephone = this.numberInput
                } if (this.passwordInput !== '') {
                    userData.password = this.passwordInput
                }
                userData.userId = JSON.parse(localStorage.user_data).id,
                    userData.username = JSON.parse(localStorage.user_data).name,
                    apiFun.postModifyUserMsg(
                        userData
                    ).then(
                        res => {
                            console.log('用户信息提交成功！')
                            console.log(res)
                            this.queryCompany()
                        }
                    )
                this.dialogVisible = false
                this.$message({
                    message: '用户信息修改成功！',
                    type: 'success'
                });
            }
        },
        // 项目列表新增
        addProjectVisible() {
            if (this.addProjectNumberInput == '' || this.addProjectNameInput == '' || this.addProjectAddressInput == '' || this.addProjectUserInput == '' || this.addProjectRemarksInput == '') {
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
                } if (this.addProjectAddressInput !== '') {
                    addProjectData.address = this.addProjectAddressInput
                } if (this.addProjectUserInput !== '') {
                    addProjectData.muserName = this.addProjectUserInput
                } if (this.addProjectRemarksInput !== '') {
                    addProjectData.mdesc = this.addProjectRemarksInput
                }
                addProjectData.isParent = '0'
                // addProjectData.pageNum = this.pageNum
                // addProjectData.pageSize = this.pageSize
                addProjectData.mid = JSON.parse(localStorage.user_mid),
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
                        message: '项目信息新建成功',
                        type: 'success'
                    });
            }
        },
        // 项目删除
        delProjectDialogVisible() {
            if (confirm('确定要删除吗？') == true) {
                let mids = []
                this.selected.forEach((project) => mids.push(project.mid))
                console.log(mids)
                let data = new URLSearchParams()
                data.append("mids", mids.join(','))
                apiFun.postPorjectDelete(
                    data
                ).then(
                    res => {
                        console.log('项目列表删除成功!')
                        console.log(res)
                        this.queryProject()
                    }
                )
            }
        },
        // 项目列表获取
        queryProject() {
            apiFun.getPagingPorject(
                {
                    isParent: '0',
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
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
        // 项目编辑修改
        operationVisible() {
            if (this.projectNumberInput == '' || this.projectNameInput == '' || this.projectAddressInput == '' || this.projectUserInput == '' || this.projectRemarksInput == '') {
                this.$message({
                    message: '请完善项目信息！',
                    type: 'warning'
                });
            } else {
                let projectData = {}
                if (this.projectNumberInput !== '') {
                    projectData.mcode = this.projectNumberInput
                } if (this.projectNameInput !== '') {
                    projectData.mname = this.projectNameInput
                } if (this.projectAddressInput !== '') {
                    projectData.address = this.projectAddressInput
                } if (this.projectUserInput !== '') {
                    projectData.muserName = this.projectUserInput
                } if (this.projectRemarksInput !== '') {
                    projectData.mdesc = this.projectRemarksInput
                }
                projectData.isParent = '0'
                // projectData.pageNum = this.pageNum
                // projectData.pageSize = this.pageSize
                projectData.mid = JSON.parse(localStorage.user_mid),
                    apiFun.postPorjectEdit(
                        projectData
                    ).then(
                        res => {
                            console.log('项目信息提交成功！')
                            console.log(res)
                            this.queryProject()
                        }
                    )
                this.operationDialogVisible = false,
                    this.$message({
                        message: '项目信息修改成功',
                        type: 'success'
                    });
            }
        },
        valueChange($event) {
            console.log($event)
        },
        // 设备信息绑定
        bindingVisible() {
            apiFun.postUserMsg(

            ).then(
                res => {
                    console.log(res)
                    let yesOrno = res.user.userType
                    console.log(yesOrno)
                    let value = this.equipmentValue
                    console.log(value)
                    function netWorkSend() {
                        if (this.equipmentPleaceInput == '' || this.equipmentNumberInput == '' || this.equipmentMacInput == '' || this.equipmentWebHostInput == '') {
                            this.$message({
                                message: '请完善设备信息！',
                                type: 'warning'
                            });
                        } else {
                            let netWork = {}
                            if (this.equipmentPleaceInput !== '') {
                                netWork.address = this.equipmentPleaceInput
                            } if (this.equipmentNumberInput !== '') {
                                netWork.acDeviceSn = this.equipmentNumberInput
                            } if (this.equipmentMacInput !== '') {
                                netWork.acMac = this.equipmentMacInput
                            } if (this.equipmentWebHostInput !== '') {
                                netWork.acIp = this.equipmentWebHostInput
                            }
                        }
                        apiFun.postNetworkEquipment(
                            netWork
                        ).then(
                            res => {
                                console.log(res)
                            }
                        )
                        this.bindingDialogVisible = false,
                            this.$message({
                                message: '设备信息提交成功！',
                                type: 'success'
                            });
                    }
                    function cabinetSend() {
                        if (this.equipmentPleaceInput == '' || this.equipmentNumberInput == '' || this.equipmentMacInput == '' || this.equipmentWebHostInput == '') {
                            this.$message({
                                message: '请完善设备信息！',
                                type: 'warning'
                            });
                        } else {
                            let cabinet = {}
                            if (this.equipmentPleaceInput !== '') {
                                cabinet.address = this.equipmentPleaceInput
                            } if (this.equipmentNumberInput !== '') {
                                cabinet.cabinetNo = this.equipmentNumberInput
                            } if (this.equipmentMacInput !== '') {
                                cabinet.mac = this.equipmentMacInput
                            } if (this.equipmentWebHostInput !== '') {
                                cabinet.ip = this.equipmentWebHostInput
                            }
                        }
                        apiFun.postCabinetEquipment(
                            cabinet
                        ).then(
                            res => {
                                console.log(res)
                            }
                        )
                        this.bindingDialogVisible = false,
                            this.$message({
                                message: '设备信息提交成功！',
                                type: 'success'
                            });
                    }
                    if (yesOrno == '0') {
                        if (value) {
                            netWorkSend()
                        } else {
                            cabinetSend()
                        }
                    } if (yesOrno == '1') {
                        netWorkSend()
                    } if (yesOrno == '2') {
                        cabinetSend()
                    }
                }
            )
            this.bindingDialogVisible = false,
                this.$message({
                    message: '设备绑定成功',
                    type: 'success'
                });
        },
        // 用户列表获取
        queryRole() {
            apiFun.getRoleMsg(
                {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
            ).then(
                res => {
                    console.log('用户列表获取成功！')
                    console.log(res)
                    this.roleData = res.data.list
                    this.roletotal = res.data.total
                    console.log(this.roleData)
                }
            )
        },
        // 用户列表新增
        addRoleVisible() {
            if (this.roleNameInput == '' || this.rolePasswordInput == '' || this.roleuNumberInput == '' || this.roleEmailInput == '') {
                this.$message({
                    message: '请完善项目信息！',
                    type: 'warning'
                });
            } else {
                let addRoletData = {}
                if (this.roleNameInput !== '') {
                    addRoletData.name = this.roleNameInput
                } if (this.rolePasswordInput !== '') {
                    addRoletData.password = this.rolePasswordInput
                } if (this.roleuNumberInput !== '') {
                    addRoletData.telephone = this.roleuNumberInput
                } if (this.roleEmailInput !== '') {
                    addRoletData.email = this.roleEmailInput
                }
                // addProjectData.mid = JSON.parse(localStorage.user_mid),
                apiFun.postPorjectAdd(
                    addRoletData
                ).then(
                    res => {
                        console.log('用户信息提交成功！')
                        console.log(res)
                        this.queryRole()
                    }
                )
                this.addRoleDialogVisible = false,
                    this.$message({
                        message: '用户信息新增成功！',
                        type: 'success'
                    });
            }
        },
        // 用户删除
        delRoleDialogVisible() {
            if (confirm('确定要删除吗？') == true) {
                let ids = []
                this.selected.forEach((role) => ids.push(role.userId))
                console.log(ids)
                let data = new URLSearchParams()
                data.append("ids", ids.join(','))
                apiFun.postRoleDelete(
                    data
                ).then(
                    res => {
                        console.log('角色列表删除成功!')
                        console.log(res)
                        this.queryRole()
                    }
                )
            }
        },
    },
}
</script>

<style scoped>
/* 左侧切换栏 */
.fl {
    padding-top: 10px;
    padding-bottom: 10px;
    width: 340px;
    height: 305px;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

.fl .title {
    width: 300px;
    height: 56px;
}

.fl .title.active {
    background-color: #0C2135;
    border-top-right-radius: 28px;
    border-bottom-right-radius: 28px;
}

.fl .title.active .tit {
    color: #F1F2F6;
}

.fl .title .iconfont {
    color: #333333;
    font-size: 20px;
}

.fl .title.active .iconfont {
    color: #F1F2F6;
    font-size: 20px;
}

.fl .title:nth-child(2) {
    margin-top: 20px;
    margin-bottom: 20px;
}

.fl .title:nth-child(3) {
    margin-bottom: 20px;
}

.fl .projectTitle {
    background-color: #0C2135;
    border-top-right-radius: 28px;
    border-bottom-right-radius: 28px;
    color: #F1F2F6;
}

.fl .pic {
    display: inline-block;
    margin-left: 20px;
    margin-right: 10px;
    width: 25px;
    height: 25px;
}

.fl .tit {
    font-size: 16px;
    line-height: 56px;
    color: #333333;
}

/* 右侧内容栏 */
.fr {
    width: 1820px;
    min-height: 400px;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

.fr .userMsg,
.accountMsg {
    padding: 40px;
}

.fr .projectMsg,
.employeeMsg {
    padding: 20px;
}

/* 个人信息内容 */
.userMsgUp {
    margin-bottom: 40px;
    width: 1742px;
    height: 140px;
    border-bottom: 1px solid #D6D8DC;
}

.userMsgUp .circle {
    display: inline-block;
    margin-right: 20px;
    text-align: center;
    line-height: 94px;
    width: 100px;
    height: 100px;
    border-radius: 50px;
    border: 1px solid #D6D8DC;
}

.userMsgUp .circle .avatar {
    width: 82px;
}

.userMsgUp .companyMsg {
    display: inline-block;
}

.userMsgUp .companyMsg .company {
    font-size: 18px;
    font-weight: 700;
    color: #333333;
}

.userMsgUp .companyMsg .userTime {
    font-size: 16px;
    color: #333333;
}

.userMsgUp .companyMsg .days {
    font-size: 16px;
    color: #C3A35D;
}

.fr .userMsg .userMsgDownTitle {
    font-size: 18px;
    font-weight: 700;
    color: #333333;
    margin-bottom: 20px;
}

.fr .userMsg .userMsgDownLi {
    margin-bottom: 10px;
}

.fr .userMsg .userMsgDownTit {
    margin-right: 10px;
    font-size: 16px;
    color: #D6D8DC;
}

.fr .userMsg .userMsgDownDetails {
    font-size: 16px;
    color: #333333;
}

/* 账号安全内容 */
.accountMsg .accountMsgTitle {
    margin-bottom: 20px;
    font-size: 18px;
    font-weight: 700;
    color: #333333;
}

.accountMsg .accountMsgLi {
    width: 1742px;
    height: 60px;
    line-height: 60px;
    border-bottom: 1px solid #D6D8DC;
}

.accountMsg .accountMsgTit {
    margin-right: 40px;
    font-size: 16px;
    color: #D6D8DC;
}

.accountMsg .accountMsgDetails {
    margin-right: 40px;
    font-size: 16px;
    color: #333333;
}

.accountMsg .modify {
    font-size: 16px;
    color: #C3A35D;
}

/* 项目管理内容 */

/* 搜索栏 */
.activeBar {
    margin-bottom: 20px;
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
