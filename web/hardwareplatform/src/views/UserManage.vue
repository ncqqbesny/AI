<template>
    <div class="content">
        <div class="employeeMsg">
            <div class="employeeMsgMain">
                <div class="activeBar">
                    <el-input v-model="inputContent" class="choose" placeholder="请输入用户名称"></el-input>
                    <div class="btn search" @click="queryRole">查询</div>
                    <div class="btn add" @click="addRoleDialogVisible = true">新增</div>
                    <div class="btn del" @click="delRoleDialogVisible">删除</div>
                </div>
                <div>
                    <el-table :data="roleList" border style="width: 100%" @selection-change=roleSelectionChange>
                        <el-table-column type="selection" class="selection"> </el-table-column>
                        <el-table-column prop="userId" label="用户ID" class="projectName"> </el-table-column>
                        <el-table-column prop="username" label="用户名称" class="projectNumber"> </el-table-column>
                        <el-table-column prop="telephone" label="联系方式" class="projectAddress"> </el-table-column>
                        <el-table-column prop="email" label="邮箱地址" class="projectUser"> </el-table-column>
                        <el-table-column prop="mname" label="项目" class="project"> </el-table-column>
                        <el-table-column prop="name" label="角色" class="role"> </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" class="projectRemarks"> </el-table-column>
                        <el-table-column label="操作" class="projectEdit">
                            <template #default="scope">
                                <span style="color: #C3A35D; text-decoration: underline;"
                                    @click="showDrawer(scope.row)">编辑</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <el-config-provider :locale="locale">
                    <div class="block">
                        <el-pagination @size-change="roleSizeChange" @current-change="roleCurrentChange"
                            v-model:currentPage="currentPage" :page-sizes="[10, 30, 50, 100]" :page-size="pageSize"
                            layout="total, sizes, prev, pager, next" :total="total" background>
                        </el-pagination>
                    </div>
                </el-config-provider>
            </div>
        </div>
        <!-- 用户新增对话框 -->
        <el-dialog v-model="addRoleDialogVisible" title="新增用户信息" width="700px">
            <el-form-item label="绑定角色">
                <el-select v-model="chooseProjectUser" class="m-2" placeholder="请选择要绑定的角色" size="large"
                    @change="valueChange">
                    <el-option v-for="item in userList" :key="item.roleId" :label="item.roleName" :value="item.roleId" />
                </el-select>
            </el-form-item>
            <el-form-item label="绑定项目">
                <el-select v-model="chooseProjectValue" class="m-2" placeholder="请选择要绑定的项目" size="large"
                    @change="valueChange">
                    <el-option v-for="item in projectList" :key="item.mid" :label="item.mname" :value="item.mid" />
                </el-select>
                <span class="optional">选填</span>
            </el-form-item>
            <el-form-item label="用户名称">
                <el-input v-model="roleNameInput" placeholder="请输入新的用户名称" style="margin-bottom: 10px;" />
            </el-form-item>
            <el-form-item label="用户密码">
                <el-input v-model="rolePasswordInput" type="password" placeholder="请输入新的用户密码"
                    style="margin-bottom: 10px;" />
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
                    <el-button type="primary" :plain="true" @click="addRoleVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 用户编辑抽屉 -->
        <el-drawer v-model="drawer" title="用户编辑" :with-header="true">
            <el-form :model="form">
                <el-form-item label="绑定角色">
                    <el-select v-model="chooseProjectUser" class="m-2" placeholder="请选择要绑定的角色" size="large"
                        @change="valueChange">
                        <el-option v-for="item in userList" :key="item.roleId" :label="item.roleName"
                            :value="item.roleId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="绑定项目">
                    <el-select v-model="chooseProjectValue" class="m-2" placeholder="请选择要绑定的项目" size="large"
                        @change="valueChange" filterable>
                        <el-option v-for="item in projectList" :key="item.mid" :label="item.mname" :value="item.mname" />
                    </el-select>
                </el-form-item>
                <el-form-item label="用户名称">
                    <el-input v-model="form.name" placeholder="请输入新的用户名称" style="margin-bottom: 10px;" />
                </el-form-item>
                <el-form-item label="用户密码">
                    <el-input v-model="editPasswordInput" placeholder="请输入新的用户密码" style="margin-bottom: 10px;" />
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="form.telephone" placeholder="请输入新的联系方式" style="margin-bottom: 10px;" />
                </el-form-item>
                <el-form-item label="邮箱地址">
                    <el-input v-model="form.email" placeholder="请输入新的邮箱地址" style="margin-bottom: 10px;" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="drawer = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="editRoleVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-drawer>
    </div>
</template>

<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import apiFun from '../network/api';
import { encode } from "../assets/common/js/encryption"

export default {
    name: 'userCenter',
    data() {
        return {
            form: {},
            pageNum: 1,
            pageSize: 10,
            total: 10,
            drawer: false,
            addRoleDialogVisible: false,
            projectList: [],
            userList: [],
            // 用户信息绑定
            roleNameInput: '',
            rolePasswordInput: '',
            roleuNumberInput: '',
            roleEmailInput: '',
            // 用户信息编辑
            editNameInput: '',
            editPasswordInput: '',
            edituNumberInput: '',
            editEmailInput: '',
            locale: zhCn,
            currentPage: 1,
            userOptions: [
                {
                    userOptionsValue: '1',
                    label: '管理员',
                },
                {
                    userOptionsValue: '2',
                    label: '超级管理员',
                },
            ],
            chooseProjectOptions: [
                {
                    chooseProjectValue: '1',
                    label: '项目1',
                },
                {
                    chooseProjectValue: '2',
                    label: '项目2',
                },
            ],
            roleList: [],
            userOptionsValue: '',
            chooseProjectValue: '',
            chooseProjectUser: '',
            inputContent: '',
            roleData: {
                name: ''
            }
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryRole();
        this.queryProject();
        this.queryUser();
    },
    methods: {
        // 明文加密
        encodepassword(password, eles = []) {
            eles.push(password);
            return encode("0x12", eles);
        },
        // 选择框事件
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
        // 抽屉事件
        showDrawer(item) {
            this.drawer = true
            this.form = item
        },
        // 用户列表获取
        queryRole() {
            let roleData = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                MId: JSON.parse(localStorage.user_mid),
            }
            if (this.inputContent.length != 0) {
                roleData.name = this.inputContent
            }
            apiFun.getRoleMsg(
                roleData
            ).then(
                res => {
                    console.log('用户列表获取成功！')
                    console.log(res)
                    this.roleList = res.data.list
                    this.roletotal = res.data.total
                    console.log(this.roleList)
                }
            )
        },
        // 获取项目列表
        queryProject() {
            apiFun.getPorject(
                {
                    MId: JSON.parse(localStorage.user_mid)
                }
            ).then(
                res => {
                    console.log('项目列表获取成功！')
                    console.log(res)
                    this.projectList = res.data
                    console.log(this.projectList)
                }
            )
        },
        // 获取角色列表
        queryUser() {
            let userData = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                mId: JSON.parse(localStorage.user_mid)
            }
            apiFun.getRoleList(
                userData
            ).then(
                res => {
                    console.log('角色列表获取成功！')
                    console.log(res)
                    this.userList = res.data.list
                    console.log(this.userList)
                }
            )
        },
        // 用户列表新增
        addRoleVisible() {
            if (this.chooseProjectUser == '' || this.roleNameInput == '' || this.rolePasswordInput == '' || this.roleuNumberInput == '' || this.roleEmailInput == '') {
                this.$message({
                    message: '请完善项目信息！',
                    type: 'warning'
                });
            } else {
                let addRoletData = {
                    name: ''
                }
                if (this.chooseProjectUser !== '') {
                    let user = this.chooseProjectUser
                    addRoletData.roleIds = [user]
                } if (this.chooseProjectValue !== '') {
                    let mid = this.chooseProjectValue
                    addRoletData.mid = mid
                } if (this.roleNameInput !== '') {
                    addRoletData.username = this.roleNameInput
                } if (this.rolePasswordInput !== '') {
                    addRoletData.password = this.encodepassword(this.rolePasswordInput)
                } if (this.roleuNumberInput !== '') {
                    addRoletData.telephone = this.roleuNumberInput
                } if (this.roleEmailInput !== '') {
                    addRoletData.email = this.roleEmailInput
                }
                console.log(addRoletData)
                apiFun.postRoleAdd(
                    addRoletData
                ).then(
                    res => {
                        console.log('用户信息提交成功！')
                        this.addRoleDialogVisible = false,
                            this.$message({
                                message: '用户信息新增成功！',
                                type: 'success'
                            });
                        console.log(res)
                        this.queryRole()
                    }
                )
            }
        },
        // 用户列表删除
        delRoleDialogVisible() {
            this.$confirm('确定删除吗', '用户列表删除', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(
                () => {
                    let ids = []
                    this.selected.forEach((role) => ids.push(role.userId))
                    console.log(ids)
                    let data = new URLSearchParams()
                    data.append("ids", ids.join(','))
                    apiFun.postRoleDelete(
                        data
                    ).then(
                        res => {
                            this.$message({
                                message: '角色列表删除成功！',
                                type: 'success'
                            });
                            console.log(res)
                            this.queryRole()
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
        // 用户列表编辑
        editRoleVisible() {
            if (this.chooseProjectUser == '' || this.chooseProjectValue == '' || this.form.name == '' || this.form.telephone == '' || this.form.email == '') {
                this.$message({
                    message: '请完善用户信息！',
                    type: 'warning'
                });
            } else {
                let editRoletData = {
                    name: '',
                    password: this.encodepassword(this.editPasswordInput),
                }
                if (this.chooseProjectUser !== '') {
                    let user = this.chooseProjectUser
                    editRoletData.roleIds = [user]
                } if (this.chooseProjectValue !== '') {
                    let mid = this.chooseProjectValue
                    editRoletData.mid = mid
                } if (this.form.name !== '') {
                    editRoletData.username = this.form.name
                } if (this.form.telephone !== '') {
                    editRoletData.telephone = this.form.telephone
                } if (this.form.email !== '') {
                    editRoletData.email = this.form.email
                }
                console.log(editRoletData)
                apiFun.postRoleEdit(
                    editRoletData
                ).then(
                    res => {
                        console.log('用户信息提交成功！')
                        console.log(res)
                        this.queryRole()
                    }
                )
                this.drawer = false,
                    this.$message({
                        message: '用户信息编辑成功！',
                        type: 'success'
                    });
            }
        }
    },
}
</script>

<style scoped>
/* 新增弹窗 */
.optional {
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

.content .employeeMsg {
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

/* 抽屉样式 */
.el-drawer__title {
    font-size: 18px !important;
}
</style>
