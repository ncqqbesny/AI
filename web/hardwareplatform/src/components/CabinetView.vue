<template>
    <div class="searchBar">
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-position="left">
                <el-form-item label="货柜编号" prop="cabinetNo" class="searchBox">
                        <el-input v-model="searchForm.cabinetNo" placeholder="请输入货柜编号" />
                </el-form-item>
                <el-form-item label="货柜名称" prop="cabinetName" class="searchBox">
                        <el-input v-model="searchForm.cabinetName" placeholder="请输入货柜名称" />
                </el-form-item>
                <el-form-item label="在线状态" prop="cabinetName" class="searchBox" v-show="!serachFold">
                    <el-select v-model="searchForm.cabinetStatus" placeholder="请选择" class="choose">
                        <el-option v-for="item in cabinetStatusList" :key="item.value" :label="item.label"
                            :value="item.value" class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="省" prop="province" class="searchBox" v-show="!serachFold">
                    <el-select v-model="searchForm.province" placeholder="请选择省份" class="choose" >
                        <el-option v-for="item in cityData" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="SearchCityChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="市" prop="city" class="searchBox" v-show="!serachFold">
                    <el-select v-model="searchForm.city" placeholder="请选择市级" class="choose" :disabled="searchForm.province.length == 0">
                        <el-option v-for="item in SearchCityList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="SearchAreaChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="区" prop="area" class="searchBox" v-show="!serachFold">
                    <el-select v-model="searchForm.area" placeholder="请选择市级" class="choose"  :disabled="searchForm.city.length == 0">
                        <el-option v-for="item in SearchAreaList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地址" prop="address" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.address" placeholder="请输入地址" />
                </el-form-item>
                <el-form-item class="searchBox">
                    <el-button type="primary" @click="getCabList" class="btn">查询</el-button>
                    <el-button @click="resetFrom" class="btn">重置</el-button>
                    <el-button  type="primary"  text @click="serachFold = !serachFold" class="btn">{{ serachFold ? '展开' : '折叠' }}</el-button>
                </el-form-item>
            </el-form>
    </div>
    <div class="main">
        <div class="activeBar">
                <div class="btnGroup">
                    <el-button type="primary" @click="showDiaLog" class="btn">新增</el-button>
                    <el-button type="danger" @click="delCab" class="btn">删除</el-button>
                </div>
        </div>
        <div>
            <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" > </el-table-column>
                <el-table-column type="index" label="序号"> </el-table-column>
                <el-table-column prop="cabinetNo" label="货柜编号" :width="flexColumnWidth('货柜编号','cabinetNo')"> </el-table-column>
                <el-table-column prop="address" label="位置" :width="flexColumnWidth('位置','address')"> </el-table-column>
                <el-table-column prop="cabinetName" label="货柜名称" :width="flexColumnWidth('货柜名称','cabinetName')"> </el-table-column>
                <el-table-column prop="cabinetStatus" label="在线状态"  :width="flexColumnWidth('在线状态','cabinetStatus')"> 
                    <template #default="scope">
                        <el-tag class="ml-2" :type="scope.row.cabinetStatus == 1 ?  'success' : 'info'">{{scope.row.cabinetStatus == 1 ? '在线' : '停用'}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="workLineTime" label="工作时长"    :width="flexColumnWidth('工作时长','workLineTime')">
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.workLineTime }}
                        </div>    
                    </template>
                </el-table-column>
                <el-table-column prop="mac" label="MAC"  :width="flexColumnWidth('MAC','mac')"> </el-table-column>
                <el-table-column prop="matterNum" label="物资数量" :width="flexColumnWidth('物资数量','matterNum')"> </el-table-column>
                <el-table-column prop="temperature" label="器具柜温度" :width="flexColumnWidth('器具柜温度','temperature')">
                </el-table-column>
                <el-table-column prop="humidity" label="器具柜温度" :width="flexColumnWidth('器具柜温度','humidity')">
                </el-table-column>
                <el-table-column prop="address" label="具体地址" :width="flexColumnWidth('具体地址','address')"> </el-table-column>
                <el-table-column prop="version" label="版本号" :width="flexColumnWidth('版本号','version')"> </el-table-column>
                <el-table-column label="操作" width="240" fixed="right">
                    <template #default="scope">
                        <div class="operation btnGroup">
                            <el-tooltip
                                effect="dark"
                                content="维护设备"
                                placement="top"
                            >
                                <el-button type="primary" @click="hrefTo(scope.row.url)" class="iconfont">&#xe600;</el-button>
                            </el-tooltip>
                            <el-tooltip
                                effect="dark"
                                content="编辑设备"
                                placement="top"
                            >
                                <el-button type="primary" @click="showEditDialog(scope.row)" class="iconfont">&#xe60f;</el-button>
                            </el-tooltip>
                            <el-tooltip
                                effect="dark"
                                content="设备详情"
                                placement="top"
                            >
                                <el-button type="primary" @click="showInfoDialog(scope.row)" class="iconfont">&#xe62d;</el-button>
                            </el-tooltip>
                             <el-tooltip
                                    effect="dark"
                                    content="开锁"
                                    placement="top"
                                >
                                    <el-button type="primary" @click="openLock('确认开锁')" class="iconfont" >&#xe600;</el-button>
                                </el-tooltip>
                                 <el-tooltip
                                    effect="dark"
                                    content="关锁"
                                    placement="top"
                                >
                                    <el-button type="primary" @click="closeLock('确认关锁')" class="iconfont">&#xe606;</el-button>
                                </el-tooltip>
                            <el-tooltip
                                effect="dark"
                                content="副柜查看"
                                placement="top"
                            >
                                <el-button type="primary" @click="showCabs(scope.row.batchNo)" class="iconfont">&#xe606;</el-button>
                            </el-tooltip>
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
        <el-dialog
            v-model="diaLogVisible"
            :title="editType ? '修改信息' : '新增货柜'"
            width="30%"
            align-center
        >
            <el-form :model="addForm" ref="addForm" label-width="120px">
                <el-form-item prop="cabinetNo" label="货柜编号" >
                    <el-input v-model="addForm.cabinetNo"></el-input>
                </el-form-item>
                <el-form-item prop="cabinetName" label="货柜名称" >
                    <el-input v-model="addForm.cabinetName"></el-input>
                </el-form-item>
                <el-form-item prop="batchNo" label="柜组编号" >
                    <el-input v-model="addForm.batchNo"></el-input>
                </el-form-item>
                <el-form-item label="货柜描述" prop="cabdesc">
                    <el-input v-model="addForm.cabdesc"  type="textarea" />
                </el-form-item>
                <el-form-item label="省" prop="province">
                    <el-select v-model="addForm.province" placeholder="请选择省份" class="choose" >
                        <el-option v-for="item in cityData" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="cityChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="市" prop="city" >
                    <el-select v-model="addForm.city" placeholder="请选择市级" class="choose" :disabled="addForm.province.length == 0">
                        <el-option v-for="item in cityList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="areaChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="区" prop="area">
                    <el-select v-model="addForm.area" placeholder="请选择市级" class="choose" :disabled="addForm.city.length == 0">
                        <el-option v-for="item in areaList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否启用" prop="cabinetStatus">
                    <el-radio-group v-model="addForm.cabinetStatus">
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="0">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="MAC" prop="mac">
                    <el-input v-model="addForm.mac" />
                </el-form-item>
                <el-form-item label="DNS" prop="dns">
                    <el-input v-model="addForm.dns" />
                </el-form-item>
                <el-form-item label="网关" prop="gateway">
                    <el-input v-model="addForm.gateway"   />
                </el-form-item>
                <el-form-item label="子关掩码" prop="submask">
                    <el-input v-model="addForm.submask"  />
                </el-form-item>
                <el-form-item label="ip" prop="ip">
                    <el-input v-model="addForm.ip" />
                </el-form-item>
                <el-form-item label="项目" prop="mid">
                    <el-select v-model="addForm.mid" class="m-2" placeholder="请选择" filterable>
                        <el-option v-for="item in midList" :key="item.mid" :label="item.mname" :value="item.mid" />
                    </el-select>
                </el-form-item>
                <el-form-item label="版本号" prop="version">
                    <el-input v-model="addForm.version" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="hiddenDialog">取消</el-button>
                    <el-button type="primary" @click="addCab">
                        提交
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog
            v-model="infoVisable"
            title="器具柜详情"
            width="40%"
            align-center
            :draggable="true"
        >  
        <el-descriptions
            class="margin-top"
            :column="3"
            :size="size"
            border

        >
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        货柜编号
                    </div>
                </template>
                {{ infoData.cabinetNo }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        货柜名称
                    </div>
                </template>
                {{  infoData.cabinetName }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        货柜状态
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoData.cabinetStatus == 1 ?  'success' : 'info'">{{infoData.cabinetStatus == 1 ? '在线' : '离线'}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        Mac地址
                    </div>
                </template>
                {{ infoData.mac }}
            </el-descriptions-item>
            <el-descriptions-item span="2">
                <template #label>
                    <div class="cell-item">
                        货柜描述
                    </div>
                </template>
                {{ infoData.cabdesc }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        DNS服务器
                    </div>
                </template>
                {{ infoData.dns }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        网关
                    </div>
                </template>
                {{ infoData.gateway }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        子网掩码
                    </div>
                </template>
                {{ infoData.submask }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        物资数量
                    </div>
                </template>
                {{ infoData.matterNum }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        温度
                    </div>
                </template>
                {{ infoData.temperature }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        湿度
                    </div>
                </template>
                {{ infoData.humidity }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        柜门
                    </div>
                </template>
                {{ infoData.openStauts }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        柜锁
                    </div>
                </template>
                {{ infoData.lock }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        柜灯
                    </div>
                </template>
                {{ infoData.light }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        版本号
                    </div>
                </template>
                {{ infoData.version }}
            </el-descriptions-item>
            <el-descriptions-item span="2">
                <template #label>
                    <div class="cell-item">
                        地址
                    </div>
                </template>
                {{ infoData.province + infoData.city + infoData.area }}
                <br>
                {{ infoData.address }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        IP
                    </div>
                </template>
                {{ infoData.ip }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        创建时间
                    </div>
                </template>
                {{ infoData.createTime }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        更新时间
                    </div>
                </template>
                {{ infoData.updateTime }}
            </el-descriptions-item>
        </el-descriptions>

        </el-dialog>
    </div>
</template>

<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import apiFun from "../network/api"

export default {
    data() {
        return {
            locale: zhCn,
            currentPage: 1,
            total:0,
            pageNum:1,
            pageSize:10,
            options: [
                {
                    value: '选项1',
                    label: '不论状态',
                },
                {
                    value: '选项2',
                    label: '当前在线',
                },
                {
                    value: '选项3',
                    label: '掉线状态',
                }
            ],
            // 搜索需要
            cabinetNo:'',
            cabinetName:'',
            tableData: [
            ],
            value: '',
            Selection:[],
            diaLogVisible:false,
            addForm:{
                batchNo:'',
                cabinetName:'',
                cabdesc: "",
                cabinetId: 0,
                cabinetNo: "",
                cabinetStatus: 1,
                createTime: "",
                dehumidification: "",
                dns: "",
                gateway: "",
                humidity: "",
                ip: "",
                isMain: 1,
                light: "",
                lock: "",
                mac: "",
                matterNum: 0,
                mid: null,
                openStauts: 0,
                runTime: "",
                slaveId: "",
                stateBolt: "",
                stateCantact: "",
                stationNo: "",
                submask: "",
                temperature: "",
                updateTime: "",
                ventilation: "",
                province:'',
                city:'',
                area:'',
                address:'',
            },
            editType:false,
            // 项目下拉列表
            midList:[],
            // 省市区数据
            cityData:[],
            cityList:[],
            areaList:[],
            cabinetStatusList:[
                {
                    label:'所有设备',
                    value:''
                },
                {
                    label:'离线设备',
                    value:0
                },
                {
                    label:'在线设备',
                    value:1
                }
            ],
            // 搜索需要
            serachFold:true,
            searchForm:{
                cabinetNo:'',
                cabinetName:'',
                cabinetStatus:'',
                province:'',
                city:'',
                area:'',
                address:'',
            },
            // 详情数据
            infoVisable:false,
            infoData:{
                cabinetNo:'',
                cabinetName:'',
                cabinetStatus:'',
                mac:'',
                dns:'',
                submask:'',
                cabdesc:'',
                gateway:'',
                matterNum:'',
                humidity:'',
                temperature:'',
                openStauts:'',
                lock:'',
                light:'',
                address:'',
                ip:''
            },
        }
    },
    methods: {
        // 修改页范围
        handleSizeChange(val) {
            this.pageSize = val
            this.getLogList()
            console.log(`共 ${val} 条数据`)
        },
        // 修改页码
        handleCurrentChange(val) {
            this.pageNum = val
            this.getCabList()
            console.log(`当前页: ${val}`)
        },
        // 选择事件
        handleSelectionChange(val){   
            this.Selection = val
            console.log(this.logSelection)
        },
        // 跳转
        hrefTo(url){
            this.$confirm(url, '即将前往',  {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(
              ()=>{
                window.open(url)
                return
              }
            ).catch(
                ()=>{
                    return
                }
            )
           
        },
        openLock(url) {
            this.$confirm(url, '即将开锁', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(
                () => {
                      apiFun.postOpenLock().then(
                        res => {
                            console.log(res)
                            if (res.code == 200) {
                                 this.$message({
                                    message: '操作成功',
                                    grouping: true,
                                    type: 'success',
                                })
                            } else {
                                this.$message({
                                    message: '操作失败',
                                    grouping: true,
                                    type: 'success',
                                })
                                console.log('查询器具柜设备列表失败', res)
                            }
                        }
                    )
                    
                }
            ).catch(
                () => {
                    return
                }
            )

        },
        closeLock(url) {
            this.$confirm(url, '即将关锁', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(
                () => {                   
                    apiFun.postCloseLock().then(
                        res => {
                            console.log(res)
                            if (res.code == 200) {
                                 this.$message({
                                    message: '操作成功',
                                    grouping: true,
                                    type: 'success',
                                })

                            } else {
                                 this.$message({
                                    message: '操作失败',
                                    grouping: true,
                                    type: 'success',
                                })
                                console.log('查询器具柜设备列表失败', res)
                            }
                        }
                    )
                }
            ).catch(
                () => {
                    return
                }
            )

        },
        // 获取柜列表
        getCabList(){
            console.log("正在搜索")
            let data =  {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                sort:0,
                isMain:1,
                MId:localStorage.getItem("user_mid")
            }
            if(this.searchForm.cabinetNo.length != 0){
                data.cabinetNo = this.searchForm.cabinetNo
            }
            // 遍历源对象的所有属性
            for (const prop in this.searchForm) {
                if (this.searchForm[prop].length !== 0) {
                    data[prop] = this.searchForm[prop];
                }
            }
            if(this.searchForm.cabinetNo.length != 0){
                data.cabinetNo = '%' + data.cabinetNo + '%'
            }
            if(this.searchForm.cabinetName.length != 0){
                data.cabinetName = '%' + data.cabinetName + '%'
            }
            if(this.searchForm.address.length != 0){
                data.address = '%' + data.address + '%'
            }
            apiFun.getPageCabList(
                data
            ).then(
                res => {
                    console.log(res)
                    if(res.code == 200){
                        this.tableData = res.data.list
                        this.total = res.data.total
                    }else{
                        console.log('查询器具柜设备列表失败',res)
                    }
                }
            )
        },
        // 新增器具柜
        addCab(){
            let data = this.addForm
            apiFun.postAddCabList(
                data
            ).then(
                res => {
                    if(res.code == 200){
                        this.diaLogVisible = false
                        this.$refs.addForm.resetFields()
                        this.getCabList()
                        this.$message({
                            message: '操作成功',
                            grouping: true,
                            type: 'success',
                        })
                    }else{
                        this.$message({
                            message: res.msg,
                            grouping: true,
                            type: 'error',
                        })
                    }
                }
            )
        },
        // 新增显示
        showDiaLog(){
            this.diaLogVisible = true
            if(this.$refs.addForm){
                this.$refs.addForm.resetFields()
            }
        },
        // 修改显示
        showEditDialog(item){
            this.diaLogVisible = true
            this.editType = true
            this.$nextTick(() => { // 注意看这里
                this.addForm = {...item}
            });
            // this.addForm = {...item}
        },
        // 修改隐藏
        hiddenDialog(){
            this.diaLogVisible = false
            this.$refs.addForm.resetFields()
        },
        // 详情显示
        showInfoDialog(data){
            this.infoVisable=true
            this.infoData = data
        },
        // 删除器具柜
        delCab(){
            if(this.Selection.length == 0){
                this.$message({
                    message: '请选择设备',
                    grouping: true,
                    type: 'message',
                })
            }else{
                this.$confirm('确定删除吗', '标题',  {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(
                    ()=>{
                        let ids = []
                        this.Selection.forEach((item) => ids.push(item.cabinetId))
                        console.log('选择的',this.Selection)
                        apiFun.postDelCabList(
                            ids
                        ).then(
                            res => {
                                if(res.code == 200){
                                    this.getCabList()
                                    this.$message({
                                        message: '已成功删除该设备',
                                        grouping: true,
                                        type: 'success',
                                    })
                                }else{
                                    this.$message({
                                        message: '删除操作失败',
                                        grouping: true,
                                        type: 'error',
                                    })
                                }
                            }
                        )
                        return
                    }
                ).catch(
                    ()=>{
                        this.$message({
                            message: '取消删除',
                            grouping: true,
                            type: 'message',
                        })
                        return
                    }
                )
            }
        },
        // 获取地区数据
        getCityData(url,params){
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
                    method:'get'
                }
            ).then(
                response => response.json()
                )
            .then(
                data => {
                    console.log(data)
                    this.cityData = data.districts[0].districts
                    console.log('当前城市数据',this.cityData) 
                }
            )
        },
        // 省市区切换
        cityChange(val){
            this.cityList = val
        },
        areaChange(val){
            console.log('区级',val)
            this.areaList = val
        },
        
        SearchCityChange(val){
            console.log(val)
            this.SearchCityList = val
        },
        SearchAreaChange(val){
            this.SearchAreaList = val
        },
         // 获取项目下拉列表
         changeMidList() {
            apiFun.getMidList(
                {
                    userId: JSON.parse(localStorage.user_data).id
                }
            ).then(
                res => {
                    console.log(res)
                    if (res.code == 200) {
                        this.midList = res.data
                        console.log(this.midList)
                    } else {
                        // alert(res.msg)
                        console.log('查询失败', res)
                    }
                }
            )
        },
        
        // 重置搜索表单
        resetFrom(){
            this.$refs.searchForm.resetFields()
            this.getCabList()
        },

        // 显示副柜
        showCabs(No){
            console.log(No)
            this.$store.commit('setSubCabsNo',{
                No:No
            })
            this.$router.push({ path: '/SubCabs' })
        },
        // 自适应宽度
       getMaxLength (arr) {
          return arr.reduce((acc, item) => {
          if (item) {
            const calcLen = this.getTextWidth(item)
            if (acc < calcLen) {
              acc = calcLen
            }
          }
            return acc
          }, 0)
        },
      getTextWidth (str) {
        let width = 0
        const html = document.createElement('span')
        html.innerText = str
        html.className = 'getTextWidth'
        document.querySelector('body').appendChild(html)
        width = document.querySelector('.getTextWidth').offsetWidth
        document.querySelector('.getTextWidth').remove()
        return width
      },
      flexColumnWidth (label, prop) {
        // console.log('label', label)
        // console.log('prop', prop)
        // 1.获取该列的所有数据
        const arr = this.tableData.map(x => x[prop])
        arr.push(label) // 把每列的表头也加进去算
        // console.log(arr)
        // 2.计算每列内容最大的宽度 + 表格的内间距（依据实际情况而定）
        console.log(label,this.getMaxLength(arr))
        let labelWidth = this.getMaxLength(arr)
        if(labelWidth <= 94){
            labelWidth = 94
        }else if(labelWidth > 220){
            labelWidth = 220
        }
        return (labelWidth + 24) + 'px'
      }
    },
    mounted(){
        this.getCityData(
            window.Glob.GETCITY_URL,
           {
                key:window.Glob.GETCITY_KEY,
                subdistrict:3
           }
        )
        this.getCabList()
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
    height: 81%;
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
.activeBar .btn {
    height: 40px;
    width: 80px;
    text-align: center;
    line-height: 40px;
    font-size: 16px;
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
/* .selection {
    width: 50px;
}
.number {
    width: 50px;
}
.address {
    width: 300px;
}
.onlineState {
    width: 150px;
}
.workingHours {
    width: 250px;
}
.macNumber {
    width: 300px;
}
.quantity {
    width: 145px;
}
.switch {
    width: 145px;
}
.webPort {
    width: 300px;
}
.version {
    width: 300px;
}
.operation {
    width: 150px;
} */
.btnGroup{
    display: flex;
    
}
.operation.btnGroup{
    justify-content: space-around;
}

.operation.btnGroup .iconfont{
    width: 30px;
    height: 30px;
    font-size: 18px;
}
.leftText{
    text-align: start;
}
</style>


<style>

/* 搜索框样式 */

.searchBar .searchBox .el-form-item__label{
    display: inline-block;
    width: 80px;
    overflow: hidden;
}
.searchBar .searchBox .el-input__wrapper{
    width: 580px;
}
@media only screen and (max-width: 1440px) {
    .searchBar .searchBox .el-form-item__label{
        display: inline-block;
        width: 120px;
        /* overflow: hidden; */
    }
    .searchBar .searchBox .el-input__wrapper{
        width: 540px;
    }
}
.searchBar .searchBox .el-range-editor.el-input__wrapper{
    height: 40px;
}
.el-collapse-item{
    margin-left: 18px;
}
.el-collapse-item .el-collapse-item__header{
    font-size: 24px;
}

/* 表格样式 */
.el-table .cell {
    padding: 0;
    /* text-align: center; */
}

.el-table__cell {
    padding: 0;
    height: 50px;
}

/* 分页器样式 */
.block {
    float: right;
    margin-top: 20px;
    margin-bottom: 20px;
}
</style>
