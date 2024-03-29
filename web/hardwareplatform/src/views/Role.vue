<template>
    <div class="searchBar">
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-position="left">
                <el-form-item label="角色名称" prop="roleName" class="searchBox">
                    <el-input v-model="searchForm.roleName" placeholder="请输入设备编号" />
                </el-form-item>
                <el-form-item label="角色描述" prop="description" class="searchBox">
                    <el-input v-model="searchForm.description" placeholder="请输入设备编号" />
                </el-form-item>
                <el-form-item class="searchBox">
                    <el-button type="primary" @click="getRoleList" class="btn">查询</el-button>
                    <el-button @click="resetFrom" class="btn">重置</el-button>
                </el-form-item>
            </el-form>
       
    </div>
    <div class="main">
        <div class="activeBar">
             <div class="btnGroup">
                    <el-button type="primary" @click="showAddDialog" class="btn">新增</el-button>
                    <el-button type="danger" @click="delRoleList" class="btn">删除</el-button>
            </div>
        </div>
        <div>
            <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" class="selection"> </el-table-column>
                <el-table-column type="index" label="序号" class="number"> </el-table-column>
                <el-table-column prop="roleName" label="角色名称" class="userName"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.roleName  }}
                        </div>
                    </template> 
                </el-table-column>
                <el-table-column prop="roleId" label="角色ID" class="userId"> </el-table-column>
                <el-table-column prop="description" label="角色描述" class="userId">
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.description  }}
                        </div>
                    </template> 
                </el-table-column>
                <el-table-column prop="roleStatus" label="角色状态" class="userId">
                    <template #default="scope">
                        <el-button :type="scope.row.roleStatus == 1 ? 'success' : 'warning'" plain>
                            {{ scope.row.roleStatus == 1 ? '启用' : '禁用' }}
                        </el-button>
                    </template> 
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" class="createTime"> </el-table-column>
                <el-table-column label="操作" class="operation">
                    <template #default="scope">
                       <div class="btnGroup">
                            <span @click="showDrawer(scope.row)" > 
                                    授权
                            </span>
                            <span @click="showEditDialog(scope.row)" > 
                                    编辑
                            </span>
                       </div>
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
    <el-dialog
        v-model="addDialogVisible"
        :title="editType ? '角色编辑' : '角色新增'"
        width="30%"
        align-center
    >
        <el-form :model="form" ref="Form" label-width="120px">
            <el-form-item label="选择项目" prop="mId">
                <el-select v-model="form.mId" class="m-2" placeholder="请选择" filterable  >
                    <el-option
                    v-for="item in midList"
                        :key="item.mid"
                        :label="item.mname"
                        :value="item.mid"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="form.roleName" />
            </el-form-item>
            <el-form-item label="角色状态" prop="roleStatus">
                <el-radio-group v-model="form.roleStatus">
                    <el-radio :label="1">启用</el-radio>
                    <el-radio :label="0">禁用</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="角色描述" prop="description">
                <el-input v-model="form.description"  type="textarea"/>
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="addDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="addRole">
                提交
            </el-button>
        </span>
        </template>
    </el-dialog>
    <el-drawer
        v-model="drawer"
        title="授权菜单"
        class="drawer"
        :direction="direction"
        :before-close="handleClose"
    >
    <el-form :model="form" label-width="120px">
        <el-tree
            @check = "nodeCheckChange"
            :data="menuList"
            show-checkbox
            node-key="menuId"
            :default-checked-keys="menuChecked"
            :props="defaultProps"
        />
    </el-form>
  </el-drawer>
</template> 

<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

import apiFun from "../network/api"
import {arrToTree} from "../assets/common/js/encryption"
export default {
    data() {
        return {
            // 中文插件
            locale: zhCn,
            // 当前页
            currentPage: 1,
            // 页码
            pageNum:1,
            // 每页范围
            pageSize:10,
            // 条数
            total:10,
            // 查询选项
            options: [
                {
                    value: '选项1',
                    label: '管理员',
                },
                {
                    value: '选项2',
                    label: '付费角色',
                },
                {
                    value: '选项3',
                    label: 'LINKOM角色',
                }
            ],
            // 表格数据
            tableData:[],
            // 选择项
            value: '',
            RoleSelection:[],
            addDialogVisible:false,
            // 表单数据
            form:{
                roleName:'',
                description:'',
                roleStatus:1,
                mId:''
            },
            // isParent:'0',
            parentMid:'',
            drawer:false,
            midList:[],
            mid:'',
            editType:false,
            defaultProps : {
                children: 'children',
                label: 'menuName',
            },
            menuChecked:[],
            menuList:[
                
            ],
            // 搜索栏部分 数据
            searchForm:{
                roleName:'',
                description:''
            }
        }
    },
    methods: {
        // 修改页范围
        handleSizeChange(val) {
            this.pageSize = val
            this.getRoleList()
            console.log(`共 ${val} 条数据`)
        },
        // 修改页码
        handleCurrentChange(val) {
            this.pageNum = val
            this.getRoleList()
            console.log(`当前页: ${val}`)
        },
        // 选择事件
        handleSelectionChange(val){   
            this.RoleSelection = val
            // console.log(this.UserSelection)
        },
        // 删除用户
        delRoleList(){
            if ( confirm('确定要删除吗')== true) {
                let ids = []
                this.RoleSelection.forEach((role) => ids.push(role.roleId))
                let data = new URLSearchParams()
                data.append("ids",ids.join(','))
                apiFun.postDelRoleList(
                    data
                ).then(
                    res => {
                        if(res.code == 200){
                            this.getRoleList()
                        }else{
                            console.log('删除角色失败',res)
                        }
                    }
                )
            } else {
                console.log('取消删除')
            }
           
        },
        // 查询用户列表
        getRoleList(){
            let data = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                mId:localStorage.user_mid
            }
            if(this.searchForm.roleName.length != 0){
                data.roleName = '%' + this.searchForm.roleName + '%'
            }
            if(this.searchForm.description.length != 0){
                data.description = '%' + this.searchForm.description + '%'
            }
            apiFun.getRoleList(
                data
            ).then(
                res => {
                    if(res.code == 200){
                        this.tableData = res.data.list
                        this.total = res.data.total
                    }else{
                        console.log('查询用户列表失败',res)
                    }
                }
            )
        },
        // 显示新增
        showAddDialog(){
            this.addDialogVisible = true
            this.editType = false
            this.$refs.Form.resetFields()
        },
        // 显示修改
        showEditDialog(item){
            this.addDialogVisible = true
            this.editType = true
            console.log(item)
            this.$nextTick(() => { // 注意看这里
                this.form = {...item}
            }); 
            // apiFun.getMidList(
            //     {
            //         MId:item.mId
            //     }
            // ).then(
            //     res => {
            //         console.log(res)
            //         if(res.code == 200){
            //             this.parentMid = res.data[0].parentMid
            //             this.isParent = res.data[0].isParent
            //             this.changeMidList()
            //             console.log('当前是否为子',this.isParent)
            //         }else{
            //             // alert(res.msg)
            //             console.log('查询失败',res)
            //         }
            //     }
            // )
        },
        // 新增用户
        addRole(){
            if(this.editType){
                let data = this.form
                apiFun.UpdateRoleInfo(
                    data
                ).then(
                    (res)=>{
                        console.log(res)
                        if(res.code == 200){
                            this.getRoleList()
                            this.addDialogVisible = false
                            this.$message({
                                message: "修改成功",
                                grouping: true,
                                type: 'success',
                            })
                        }else{
                            // alert(res.msg)
                            this.$message({
                                message: res.msg,
                                grouping: true,
                                type: 'error',
                            })
                            // console.log('新增失败',res)
                        }
                    }
                )
            }else{
                let data = this.form
                if(this.form.mId.length == 0){
                    this.form.mId = localStorage.getItem('user_mid')
                }
                data.userId = 0
                    apiFun.postAddRole(
                        data
                    ).then(
                        (res)=>{
                            console.log(res)
                            if(res.code == 200){
                                this.getRoleList()
                                this.addDialogVisible = false  
                                this.$message({
                                    message: "新增成功",
                                    grouping: true,
                                    type: 'success',
                                })
                            }else{
                                // alert(res.msg)
                                this.$message({
                                    message: res.msg,
                                    grouping: true,
                                    type: 'error',
                                })
                            }
                        }
                    )
            }
        },
        // 显示抽屉
        showDrawer(item){
            this.drawer = true
            this.form = {...item}
            console.log(item)
            if(!item.menuIds){
                this.menuChecked = []
            }else{
                this.menuChecked = item.menuIds.split(',')
            }
            this.getMenu()
        },
        getMenu(){
            apiFun.getMenuList(
                {
                    pageNum:1,
                    pageSize:20
                }
            ).then(
                res => {
                    console.log(res)
                    if(res.code == 200){
                        this.menuList = arrToTree(res.data.list,0)
                        console.log('转化成=>')
                        console.log(this.menuList)
                    }
                }
            )
        },
        // 查询项目列表
        changeMidList(){
            apiFun.getMidList(
                {
                    userId:JSON.parse(localStorage.user_data).id
                }
            ).then(
                res => {
                    console.log(res)
                    if(res.code == 200){
                        this.getRoleList()
                        this.midList = res.data
                        console.log(this.midList)
                    }else{
                        // alert(res.msg)
                        console.log('查询失败',res)
                    }
                }
            )
        },
        nodeCheckChange(checkedNodes,checkedKeys,halfCheckedNodes,halfCheckedKeys){
            console.log(checkedKeys)
            this.form.menuIds = checkedKeys.checkedKeys.join(',')
            console.log(this.form.menuIds)
            apiFun.UpdateRoleInfo(
                this.form
            ).then(
                (res)=>{
                    console.log(res)
                    if(res.code == 200){
                        this.getRoleList()
                    }else{
                        alert(res.msg)
                        console.log('授权失败',res)
                    }
                }
            )
        },
        // 重置搜索表单
        resetFrom(){
            this.$refs.searchForm.resetFields()
            this.getRoleList()
        }
    },
    mounted(){
        // 进入页面时，刷新当前页面
        this.getRoleList()
        this.changeMidList()
    }
}
</script>

<style scoped>

/* 搜索栏 */
.searchBar{
    width: 2180px;
    height: auto;
    margin:10px 0 20px 0;
    max-width: 100%;
    border-radius: 5px;
    background: #fff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

.searchBar .searchBox{
    width: 600 px;
    margin:10px 30px;
}
/* 搜索栏-按钮样式 */
.searchBar .btn {
    height: 40px;
    width: 80px;
    text-align: center;
    line-height: 40px;
    font-size: 16px;
}
.main {
    padding: 20px;
    width: 2180px;
    min-height: 714px;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

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
.main .btn {
    width: 80px;
    height: 40px;
}

.main .search {
    margin-right: 20px;
}

.main .add {
    margin-right: 20px;
    border-radius: 5px;
}

.main .del {
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
.number {
    width: 50px;
}
.userName {
    width: 510px;
    text-align: start;
}
.userId {
    width: 510px;
}
.createTime {
    width: 510px;
}
.operation {
    width: 510px;
}


.leftText{
    text-align: start;
    margin-left: 24px;
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
}
.el-table__cell:last-child > .btnGroup{
    display: flex;
    justify-content: space-around;
}
/* 分页器样式 */
.block {
    float: right;
    margin-top: 20px;
    margin-bottom: 20px;
}
/* 抽屉样式 */
.drawer .el-drawer__title{
    font-size: 24px;
}
</style>
