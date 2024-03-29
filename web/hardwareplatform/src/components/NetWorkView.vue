<template>
    <div class="searchBar">
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-position="left">
                <el-form-item label="设备编号" prop="acDeviceSn" class="searchBox">
                        <el-input v-model="searchForm.acDeviceSn" placeholder="请输入设备编号" />
                </el-form-item>
                <el-form-item label="设备名称" prop="acDeviceName" class="searchBox">
                    <el-input v-model="searchForm.acDeviceName" placeholder="请输入设备名称" />
                </el-form-item>
                <el-form-item label="在线状态" prop="status" class="searchBox" v-show="!serachFold">
                    <el-select v-model="searchForm.status" placeholder="在线状态" class="choose" >
                        <el-option  label="所有设备" value="" class="optionLi">
                        </el-option>
                        <el-option  label="在线" :value="1" class="optionLi">
                        </el-option>
                        <el-option  label="离线" :value="0" class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="版本号" prop="acDeviceHw" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.acDeviceHw" placeholder="请输入版本号" />
                </el-form-item>
                <el-form-item label="MAC地址" prop="acMac" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.acMac" placeholder="MAC地址" />
                </el-form-item>
                <el-form-item label="显示名称" prop="displayDeviceName" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.displayDeviceName" placeholder="显示名称" />
                </el-form-item>
                <el-form-item label="位置显示名称" prop="sortorderDeviceName" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.sortorderDeviceName" placeholder="位置显示名称" />
                </el-form-item>
                <el-form-item label="绑定时间" prop="bindTime" class="searchBox" v-show="!serachFold">
                    <el-config-provider :locale="locale">
                        <el-date-picker
                            v-model="searchForm.bindTime"
                            type="daterange"
                            unlink-panels
                            range-separator="To"
                            start-placeholder="起始时间"
                            end-placeholder="结束时间"
                            :shortcuts="shortcuts"
                            :size="size"
                            format="YYYY/MM/DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                        />
                    </el-config-provider>
                </el-form-item>
                <el-form-item label="出厂时间" prop="outTime" class="searchBox" v-show="!serachFold">
                    <el-config-provider :locale="locale">
                        <el-date-picker
                            v-model="searchForm.outTime"
                            type="daterange"
                            unlink-panels
                            range-separator="To"
                            start-placeholder="起始时间"
                            end-placeholder="结束时间"
                            :shortcuts="shortcuts"
                            :size="size"
                            format="YYYY/MM/DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                        />
                    </el-config-provider>
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
                    <el-form-item label="项目" prop="address" class="searchBox" v-show="!serachFold">
                        <el-select v-model="searchForm.mid" class="m-2" placeholder="请选择" filterable>
                            <el-option v-for="item in midList" :key="item.mid" :label="item.mname" :value="item.mid" />
                        </el-select>
                    </el-form-item>
                    <el-form-item class="searchBox">
                        <el-button type="primary" @click="getNetWorkList" class="btn">查询</el-button>
                        <el-button @click="resetFrom" class="btn">重置</el-button>
                        <el-button  type="primary"  text @click="serachFold = !serachFold" class="btn">{{ serachFold ? '展开' : '折叠' }}</el-button>
                    </el-form-item>
            </el-form>
       
    </div>
    <div class="main">
        <div class="activeBar">
            <div class="queryBox">
                <div class="btnGroup">
                    <el-button type="primary" @click="showDialog()" class="btn">新增</el-button>
                    <el-button type="danger" @click="delItem" class="btn">删除</el-button>
                    <el-button type="success" @click="upfileShow = true" class="btn">上传</el-button>
                </div>
            </div>
        </div>
        <div>
            <el-table :data="tableData" border style="width: 100% " max-height="535"  @selection-change="handleSelectionChange"
                :default-sort="{ prop: 'acDeviceSn', order: 'ascending' }" fit="true"
            >
                <el-table-column type="selection" class="selection"> </el-table-column>
                <el-table-column prop="acDeviceSn" label="设备编号" class="webPort" sortable :width="flexColumnWidth('设备编号','acDeviceSn')"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.acDeviceSn }}
                        </div>  
                    </template>   
                </el-table-column>
                <el-table-column prop="province" label="地理位置" class="address"  :width="flexColumnWidth('地理位置','province',60)">
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.province + scope.row.city }}
                        </div>
                    </template> 
                </el-table-column>
                <el-table-column prop="acDeviceName" label="设备名称" class="onlineState" show-overflow-tooltip :width="flexColumnWidth('设备名称','acDeviceName')"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.acDeviceName }}
                        </div>
                    </template> 
                </el-table-column>
                <!-- <el-table-column prop="acUnit" label="单位" class="onlineState"> </el-table-column> -->
                <el-table-column prop="model" label="设备型号" class="onlineState" :width="flexColumnWidth('设备型号','model')"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.model }}
                        </div>
                    </template> 
                </el-table-column>
                <el-table-column prop="status" label="在线状态" class="onlineState" width="80"> 
                    <template #default="scope">
                        <el-tag class="ml-2" :type="scope.row.status == 1 ?  'success' : 'danger'">{{scope.row.status == 1 ? '在线' : '离线'}}</el-tag>
                    </template> 
                </el-table-column>
                <el-table-column prop="acRuntime" label="工作时长" class="workingHours" :width="flexColumnWidth('工作时长','acRuntime',25)">
                    <template #default="scope">
                        <span>{{ changeTimeFormat(scope.row.acRuntime) }}</span>
                    </template> 
                </el-table-column>
                <el-table-column prop="acMac" label="MAC" class="macNumber" :width="flexColumnWidth('MAC','acMac')"> </el-table-column>
                <el-table-column prop="acApOnlineNum" label="接入AP" class="onlineAP" :width="flexColumnWidth('接入AP','acApOnlineNum',0)"> </el-table-column>
                <!-- <el-table-column prop="acUserOnlineNum" label="在线用户" class="onlineUser"> </el-table-column> -->
                <!-- <el-table-column prop="acIp" label="WEB端口" class="webPort"> </el-table-column> -->
                <el-table-column prop="bindTime" label="绑定时间" class="webPort" :width="flexColumnWidth('绑定时间','bindTime',10)"> </el-table-column>
                <el-table-column prop="outTime" label="	出厂时间" class="webPort" :width="flexColumnWidth('出厂时间','outTime',10)"> </el-table-column>
                <el-table-column prop="acDeviceHw" label="版本号" class="version" width="60"> </el-table-column>
                <el-table-column prop="updateTime" label="最后更新时间" class="webPort" :width="flexColumnWidth('最后更新时间','updateTime',10)"> </el-table-column>
                <el-table-column label="操作" fixed="right" width="120">
                    <template #default="scope">
                        <div class="btnGroup operation">
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
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
            v-model="dialogvisible"
            :title="editType ?  '编辑设备' : '新增设备'"
            width="30%"
            align-center
        >
            <el-form :model="form" label-width="120px"  ref="form" :rules="addFormRules" class="addForm">
                <el-form-item label="设备编号" prop="acDeviceSn">
                    <el-input v-model="form.acDeviceSn" />
                </el-form-item>
                <el-form-item label="显示名称" prop="displayDeviceName" >
                    <el-input v-model="form.displayDeviceName" />
                </el-form-item>
                <el-form-item label="安装位置" prop="displaySortorder" >
                    <el-input v-model="form.displaySortorder" type="number"/>
                </el-form-item>
                <el-form-item label="位置显示名称" prop="sortorderDeviceName" >
                    <el-input v-model="form.sortorderDeviceName"/>
                </el-form-item>
                <el-form-item label="设备名称" prop="acDeviceName" v-if="!this.editType">
                    <el-input v-model="form.acDeviceName" />
                </el-form-item>
                <el-form-item label="设备型号" prop="model">
                    <el-input v-model="form.model" />
                </el-form-item>
                <el-form-item label="软件版本号" prop="acDeviceSw">
                    <el-input v-model="form.acDeviceSw" />
                </el-form-item>
                <el-form-item label="硬件版本号" prop="acDeviceHw">
                    <el-input v-model="form.acDeviceHw" />
                </el-form-item>
                <el-form-item label="MAC" prop="acMac">
                    <el-input v-model="form.acMac" />
                </el-form-item>
                <el-form-item label="项目" prop="mid">
                    <el-select v-model="form.mid" class="m-2" placeholder="请选择" filterable>
                        <el-option v-for="item in midList" :key="item.mid" :label="item.mname" :value="item.mid" />
                    </el-select>
                </el-form-item>
                <el-form-item label="设备类型" prop="deviceType">
                    <el-select v-model="form.deviceType" placeholder="请选择设备类型" class="choose" @change="getNetWorkList">
                        <el-option v-for="item in options" :key="item.paramVal" :label="item.paramName" :value="item.paramVal"
                            class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="省" prop="province">
                    <el-select v-model="form.province" placeholder="请选择省份" class="choose" >
                        <el-option v-for="item in cityData" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="cityChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="市" prop="city">
                    <el-select v-model="form.city" placeholder="请选择市级" class="choose" >
                        <el-option v-for="item in cityList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="areaChange(item.districts)">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="区" v-if="form.city != ''" prop="area">
                    <el-select v-model="form.area" placeholder="请选择市级" class="choose" >
                        <el-option v-for="item in areaList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="出厂时间" prop="outTime">
                    <el-config-provider :locale="locale">
                        <el-date-picker
                            v-model="form.outTime"
                            type="datetime"
                            placeholder="选择出厂时间"
                            format="YYYY/MM/DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            class="datetime"
                            @change="outtimeChange"
                        />
                    </el-config-provider>
                </el-form-item>
                <el-form-item label="绑定时间" prop="bindTime" >
                    <el-config-provider :locale="locale" >
                        <el-date-picker
                            v-model="form.bindTime"
                            type="datetime"
                            placeholder="选择设备绑定时间"
                            format="YYYY/MM/DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            @change="bindtimeChange"
                            :picker-options="pickerOptions1"
                        />
                    </el-config-provider>
                   
                </el-form-item>
              
                <el-form-item label="URL" prop="url">
                    <el-input v-model="form.url" />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input
                        v-model="form.remark"
                        :autosize="{ minRows: 3, maxRows: 6 }"
                        type="textarea"
                        placeholder="请输入备注"
                    />
                </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogvisible = false">取消</el-button>
                <el-button type="primary" @click="addOrEdit">
                    提交
                </el-button>
            </span>
            </template>
        </el-dialog>
        <el-dialog
            v-model="infoVisible"
            title="设备详情"
            width="50%"
            align-center
        >
            <el-form :model="infoForm" label-width="150px">
                <el-form-item label="显示名称" prop="displayDeviceName" >
                    {{ infoForm.displayDeviceName }}
                </el-form-item>
                <el-form-item label="安装位置" prop="acDeviceName" >
                    {{ infoForm.displaySortorder }}
                </el-form-item>
                <el-form-item label="位置显示名称">
                    {{ infoForm.sortorderDeviceName }}
                </el-form-item>
                <el-form-item label="设备名称">
                    {{ infoForm.acDeviceName }}
                </el-form-item>
                <el-form-item label="设备编号">
                    {{ infoForm.acDeviceSn }}
                </el-form-item>
                <el-form-item label="设备型号">
                    {{ infoForm.model }}
                </el-form-item>
                <el-form-item label="单位">
                    {{ infoForm.acUnit }}
                </el-form-item>
                <el-form-item label="在线AP数量">
                    {{ infoForm.acApOnlineNum }}
                </el-form-item>
                <el-form-item label="AC运行时间">
                    {{ infoForm.acCurrentTime }}
                </el-form-item>
                <el-form-item label="AC软件版本号">
                    {{ infoForm.acDeviceSw }}
                </el-form-item>
                <el-form-item label="AC硬件版本号">
                    {{ infoForm.acDeviceHw }}
                </el-form-item>
                <el-form-item label="拨号方式">
                    {{ infoForm.acDialMode }}
                </el-form-item>
                <el-form-item label="WLAN接口DNS服务器">
                    {{ infoForm.acDns }}
                </el-form-item>
                <el-form-item label="WAN接口网关地址">
                    {{ infoForm.acGateway }}
                </el-form-item>
                <el-form-item label="WAN接口IP地址">
                    {{ infoForm.acIp }}
                </el-form-item>
                <el-form-item label="Mac地址">
                    {{ infoForm.acMac }}
                </el-form-item>
                <el-form-item label="AC运行时长">
                    {{ infoForm.acRuntime }}
                </el-form-item>
                <el-form-item label="WLAN子关掩码">
                    {{ infoForm.acSubmask }}
                </el-form-item>
                <el-form-item label="AC在线用户数量">
                    {{ infoForm.acUserOnlineNum }}
                </el-form-item>
                <el-form-item label="具体地址">
                    {{ infoForm.address }}
                </el-form-item>
                <el-form-item label="具体地址">
                    {{ infoForm.deviceType }}
                </el-form-item>
                <el-form-item label="状态信息发送时间间隔">
                    {{ infoForm.interval }}
                </el-form-item>
                <el-form-item label="经纬度">
                    {{  '经度：' + infoForm.longitude }}
                    <br>
                    {{  '维度：' + infoForm.latitude}}
                </el-form-item>
                <el-form-item label="出货日期">
                    {{ infoForm.outDate }}
                </el-form-item>
                <el-form-item label="出厂时间">
                    {{ infoForm.outTime }}
                </el-form-item>
                <el-form-item label="上传的json字符串">
                    {{ infoForm.upJson }}
                </el-form-item>
                <el-form-item label="信息上传次数">
                    {{ infoForm.upNum }}
                </el-form-item>
                <el-form-item label="更新时间">
                    {{ infoForm.upDate }}
                </el-form-item>
                <el-form-item label="远程地址">
                    {{ infoForm.url }}
                </el-form-item>
                <el-form-item label="备注">
                    {{ infoForm.remark }}
                </el-form-item>
                <el-form-item label="参数详情" >
                    <span class="infoText" >{{infoForm.acOnlineApInfo}}</span>
                    <el-input
                        v-model="infoForm.acOnlineApInfo"
                        :autosize="{ minRows: 2, maxRows: 4 }"
                        type="textarea"
                        placeholder="Please input"
                    />
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog
            v-model="upfileShow"
            title="导入文件"
            width="30%"
            align-center
        >
            <el-upload
                class="upload"
                accept=".xls,.xlsx"
                drag
                :action="actionUrl"
                multiple
                :headers="headers"
                :before-upload="beforeAvatarUpload"
                :on-success="UpSuccess"
            >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                    拖入文件 或 <em>点击上传</em>
                </div>
            </el-upload>
        </el-dialog>
        <el-config-provider :locale="locale">
            <div class="block">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" 
                    v-model:currentPage="currentPage1" :page-sizes="[10, 30, 50, 100]" :page-size="pageSize"
                    layout="total, sizes, prev, pager, next" :total="total" background>
                </el-pagination>
            </div>
        </el-config-provider>
    </div>
</template>

<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import apiFun from "../network/api"
import { stringify } from 'qs'
// console.log(cityData)
function checkUrl(rules,str){
        console.log(str)
        var isURL = /^(?:https?:\/\/)?(?:www\.)?[\w-]+(?:\.[\w-]+)+[\w.,@?^=%&:/~+#-]*$/;
  
        // 判断是否是合法的域名
        var isDomain = /^(?:(?:https?|ftp):\/\/)?(?:www\.)?([a-zA-Z0-9_-]+(?:(?:\.[a-zA-Z0-9_-]+)+))$/;
        
        // 判断是否是合法的IP地址
        var isIP = /^(?:\d{1,3}\.){3}\d{1,3}$/;
        
        // 使用正则表达式进行匹配
        // console.log( isURL.test(str) || isDomain.test(str) || isIP.test(str))
        return isURL.test(str) || isDomain.test(str) || isIP.test(str);
    }
    console.log(checkUrl('192.168.0.1'))
    const  addFormRules={
                acDeviceSn: [
                    { required: true, message: '请输入设备编号', trigger: 'blur' }
                ],
                acMac:[
                    { required:true , message:'请输入MAC地址' , trigger:'blur'}
                ]
            }
let maxTime = new Date()
export default {
    props:{
        deviceType:{
            type: String,
            default: "",
        }
    },
    data() {
        return {
            locale: zhCn,
            currentPage1: 1,
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
            tableData: [],
            value: '',
            Selection:[],
            dialogvisible:false,
            infoVisible:false,
            upfileShow:false,
            form:{
                acApOnlineNum: '',
                acCurrentTime: '',
                acDeviceHw: '',
                acDeviceSn: '',
                acDeviceName:'',
                acDeviceSw: '',
                acDialMode: '',
                acDns: '',
                acGateway: '',
                acIp: '',
                acMac: '',
                acOnlineApInfo: '',
                acRuntime: '',
                acSubmask: '',
                acTimeZone: '',
                acUserOnlineNum: '',
                address: '',
                area: '',
                bindTime: '',
                city: '',
                createTime: '',
                deviceType: '',
                displayDeviceName:'',
                displaySortorder:'',
                id: 0,
                interval: 0,
                latitude: '',
                longitude: '',
                mid: null,
                outTime: '',
                province: '',
                remark: '',
                status: '',
                upJson: '',
                upNum: 0,
                updateTime: '',
                url: '',
                sortorderDeviceName:''
            },
            midList:[],
            infoForm:{
                id: null,
                acDeviceSn: null,
                acDeviceName: null,
                model: null,
                acUnit: null,
                longitude: null,
                latitude: null,
                province: null,
                city: null,
                area: null,
                address: null,
                status: null,
                upNum: null,
                url: null,
                acDeviceHw: null,
                acDeviceSw: null,
                acMac: null,
                acDialMode: null,
                acIp: null,
                acSubmask: null,
                acGateway: null,
                acDns: null,
                acCurrentTime: null,
                acRuntime:null,
                acTimeZone: null,
                acApOnlineNum: null,
                isMain: null,
                acUserOnlineNum: null,
                acOnlineApInfo: null,
                remark: null,
                interval: null,
                count: null,
                upJson: null,
                deviceType: null,
                displayDeviceName:'',
                displaySortorder:'',
                outTime: null,
                bindTime: null,
                createTime: null,
                updateTime: null,
                mid: null,
                sortorderDeviceName:null
            },
            editType:false,
            paramsType:'net_device_type',
            // 省市区
            cityData:[],
            cityList:[],
            areaList:[],
            // 
            SearchCityList:[],
            SearchAreaList:[],
            // 搜索需要
            serachFold:true,
            searchForm:{
                bindTime:'',
                acDeviceSn:'',
                acDeviceName:'',
                status:'',
                province:'',
                city:'',
                area:'',
                address:'',
                acDeviceHw:'',
                outTime:'',
                mid:'',
                acMac:'',
                displayDeviceName:'',
                sortorderDeviceName:''
            },
            // 新增的表单规则
            addFormRules:addFormRules,
            maxTime:maxTime,
            // 时间选择规则
            ppickerOptions0: {
                disabledDate: (time) => {
                    if (this.value2) {
                        return time.getTime() > this.value2
                    } else {
                        return time.getTime() > Date.now()
                    }
                }
            },
            pickerOptions1: {
                disabledDate: (time) => {
                    //小于最小时间或者大于最大时间都不可选
                    return time.getTime() < (this.form.outTime.getTime())-24*3600000 ;
                }
            },

        }
    },
    computed: {
        // 请求头设置
        headers(){
            const token=localStorage.getItem('token')
            return{
                token:token
            };
        },
        // 动态计算上传地址
        actionUrl(){
            const url = window.Glob.baseURL_HD + '/hdptdevice/import'
            return url 
        },
        
        // 动态计算 时间可选范围
        pickerOptions(){
            let _this = this
            return {
                disabledDate(time) {
                    const times =  86400000 * 7//一周的毫秒数
                    let curSelectTime = new Date(_this.minDate).getTime()
                    let before = curSelectTime - times//前一周毫秒数
                    let after = curSelectTime + times//后一周毫秒数
                    return time.getTime() > after || time.getTime() < before
                },
                onPick({maxDate, minDate}) {
                    if (!maxDate) {
                    _this.minDate = minDate
                    }
                }
            }
        }
    },
    methods: {
        outtimeChange(val){
            let outTime = new Date(val).getTime()
            let nowTime = new Date().getTime()
            if(outTime > nowTime){
                this.$message({
                    message: '出厂时间不可晚于当前日期，请重新选择',
                    grouping: true,
                    type: 'warning',
                })
                this.form.outTime = ''
            }
        },
        bindtimeChange(val){
            console.log(val)
            console.log(this.form.outTime)
            let bindtime = new Date(val).getTime()
            let outTime = new Date(this.form.outTime).getTime()
            console.log(outTime)
            console.log(bindtime)
            let nowTime = new Date().getTime()
            if(bindtime > nowTime){
                this.$message({
                    message: '绑定时间不可晚于当前日期，请重新选择',
                    grouping: true,
                    type: 'warning',
                })
                this.form.bindTime = ''
            }
            if(bindtime > outTime){
                console.log('绑定时间大于出厂时间')
            }else{
                console.log('出厂时间大于绑定时间')
                this.form.bindTime = ''
                this.$message({
                    message: '绑定时间不可早于出厂时间，请重新选择',
                    grouping: true,
                    type: 'warning',
                })
            }
        },
        // 打印
        cityChange(val){
            this.cityList = val
        },
        areaChange(val){
            // console.log('区级',val)
            this.areaList = val
        },
        SearchCityChange(val){
            console.log(val)
            this.SearchCityList = val
        },
        SearchAreaChange(val){
            this.SearchAreaList = val
        },
        // 时间转换
        changeTimeFormat(str){
            if (!str){
                return '暂无时间'
            }
            return str.replace(/d|h|m|s/g, function(match) {
                return match === 'd' ? '天' : match === 'h' ? '小时' : match === 'm' ? '分' : '秒';
            })
        },
        handleSizeChange(val) {
            this.pageSize = val
            console.log(`共 ${val} 条数据`)
            this.getNetWorkList()
        },
        handleCurrentChange(val) {
            this.pageNum = val
            console.log(`当前页: ${val}`)
            this.getNetWorkList()
        },
        // 选择事件
        handleSelectionChange(val){   
            this.Selection = val
            console.log(this.Selection)
        },
        // 获取数据列表
        getNetWorkList(){
            // console.log(this.searchForm.bindTime)
            let data = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                deviceType:this.deviceType,
                sort:0,
                MId:parseInt(localStorage.getItem("user_mid"))
            }
            if(this.searchForm.acDeviceName.length != 0){
                data.acDeviceName = '%' + this.searchForm.acDeviceName + '%'
            }
            if(this.searchForm.acDeviceSn.length != 0){
                data.acDeviceSn = '%' + this.searchForm.acDeviceSn + '%'
            }
            if(this.searchForm.status.length != 0){
                data.status = this.searchForm.status 
            }
            if(this.searchForm.acDeviceHw != 0){
                data.acDeviceHw = this.searchForm.acDeviceHw 
            }
            if(this.searchForm.area.length != 0){
                data.area = this.searchForm.area
            }
            if(this.searchForm.city.length != 0){
                data.city = this.searchForm.city
            }
            if(this.searchForm.province.length != 0){
                data.province = this.searchForm.province
            }
            if(this.searchForm.address.length != 0){
                data.address = '%' + this.searchForm.address + '%'
            }
            if(this.searchForm.bindTime != null){
                data.bindDateFrom = this.searchForm.bindTime[0],
                data.bindDateTo = this.searchForm.bindTime[1]
            }
            if(this.searchForm.bindTime.length != 0){
                data.outDateFrom = this.searchForm.outTime[0],
                data.outDateTo = this.searchForm.outTime[1]
            }
            if(this.searchForm.mid.length != 0){
                data.MId = this.searchForm.mid
            }
            if(this.searchForm.acMac.length != 0){
                data.acMac = '%' + this.searchForm.acMac + '%'
            }
            if(this.searchForm.displayDeviceName.length != 0){
                data.displayDeviceName = '%' + this.searchForm.displayDeviceName + '%'
            }
            
            if(this.searchForm.sortorderDeviceName.length != 0){
                data.sortorderDeviceName = '%' + this.searchForm.sortorderDeviceName + '%'
            }
            apiFun.getNetWorkList(
                data
            ).then(
                res => {
                    if(res.code == 200){
                        this.tableData = res.data.list
                        this.total = res.data.total
                    }else{
                        console.log('查询网络设备列表失败',res)
                    }
                }
            )
        },
        // 跳转
        hrefTo(url){
            this.$confirm(url, '即将前往设备维护界面',  {
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
        // 删除
        delItem(){
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
                        this.Selection.forEach((item) => ids.push(item.id))
                        console.log(this.Selection)
                        apiFun.postDelNetWord(
                        ids
                        ).then(
                            res => {
                                if(res.code == 200){
                                    this.getNetWorkList()
                                    this.$message({
                                        message: '已成功删除该设备',
                                        grouping: true,
                                        type: 'success',
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
        // 显示新增框
        showDialog(){
            this.dialogvisible = true
            this.editType = false
            console.log(this.$refs)
            if(this.$refs.form){
                this.$refs.form.resetFields()
            }
        },
        showEditDialog(item){
            this.dialogvisible = true
                this.editType = true
            this.$nextTick(() => { // 注意看这里
                this.form={...item}
                this.form.mid = parseInt(this.form.mid)
               
            });
            // console.log(item)
            // console.log(this.form)
        },
        // 显示详情框
        showInfoDialog(item){
            // this.infoForm = JSON.parse(item)
            this.infoForm = item
            this.infoVisible = true
            console.log(this.infoForm)
        },
        // 新增或修改
        addOrEdit(){
            console.log(this.editType)
            let data
            data = this.form
            // data.bindTime = this.form.bindTime.toISOString().replace('T', ' ').slice(0, -5);
            // data.outTime = this.form.outTime.toISOString().replace('T', ' ').slice(0, -5);
            // const formattedDate = originalDate.
            
            console.log(data)
            
            this.$refs['form'].validate((valid) => {  //开启校验
                console.log(valid)
                if (valid) {   // 如果校验通过，请求接口，允许提交表单
                    if(this.editType == true){
                        apiFun.postEditNetWork(
                            [data]
                        ).then(
                            res => {
                                if(res.code == 200){
                                    this.getNetWorkList()
                                    this.dialogvisible = false
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
                    }else{
                        // data.mid = JSON.parse(localStorage.getItem('user_mid'))
                        apiFun.postSaveNetWork(
                            [data]
                        ).then(
                            res => {
                                if(res.code == 200){
                                    this.getNetWorkList()
                                    this.dialogvisible = false
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
                    }
                } else {   //校验不通过
                    return false;
                }
            });
           
        },
        // 获取下拉框
        getOptions(){
            let data = new URLSearchParams()
            data.append("paramType",this.paramsType)
            apiFun.getSystemParams (
                data
            ).then(
                res => {
                    console.log(res)
                    if(res.code == 200){
                        this.options =  res.data
                    }
                }
            )
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
        // 限制文件上传大小及类型
        beforeAvatarUpload(file){
            console.log(file)
            const whiteList = ["xls", "xlsx"];
            if (whiteList.indexOf(file.name.substring(file.name.lastIndexOf(".") + 1)) === -1) {
                this.$message({
                    message: "上传文件只能是xls、xlsx格式",
                    grouping: true,
                    type: 'error',
                })
                return false;
            }
            // 将文件size转为MB
            if((file.size / 1024 / 1024) > 10){
                this.$message({
                    message: '文件大小不能超过10MB',
                    grouping: true,
                    type: 'error',
                })
                return false;
            }
        },
        // 文件上传成功
        UpSuccess(res){
            this.upfileShow = false
            console.log(res)
            let type
            if(res.code == 200){
                type = 'success'
            }else{
                type = 'error'
            }
            this.$message({
                message: res.msg,
                grouping: true,
                type: type,
            })
        },
        // 重置搜索表单
        resetFrom(){
            this.$refs.searchForm.resetFields()
            this.getNetWorkList()
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
      flexColumnWidth (label, prop,extraWidth = 0) {
        // console.log('label', label)
        // console.log('prop', prop)
        // 1.获取该列的所有数据
        const arr = this.tableData.map(x => x[prop])
        arr.push(label) // 把每列的表头也加进去算
        // console.log(arr)
        // 2.计算每列内容最大的宽度 + 表格的内间距（依据实际情况而定）
        // console.log(label,this.getMaxLength(arr))
        let labelWidth = this.getMaxLength(arr) 
        if(extraWidth.length != 0){
            labelWidth = labelWidth + extraWidth
        }
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
        this.getNetWorkList()
        this.getOptions()
        this.changeMidList()
        console.log(this.changeTimeFormat(""))
    },
    

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
    height: 100%;
    max-width: 100%;
    overflow: scroll;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

/* 表格头部栏 */
.activeBar {
    margin-bottom: 20px;
}
.activeBar .query .inputLabel{
    display: inline-block;
}
.activeBar .btn {
    height: 40px;
    width: 80px;
    text-align: center;
    /* line-height: 40px; */
    font-size: 16px;
}
.activeBar .choose {
    width: 300px;
    height: 40px;
    border-radius: 0;
}

/* .el-input__wrapper {
    width: 300px;
    height: 40px;
    border-radius: 0;
    border: 1px solid #D6D8DC;
} */

.el-select-dropdown__item {
    height: 40px;
    line-height: 40px;
    color: #333333;
}


/* 表格样式 */
.el-table {
    font-size: 16px;
    color: #333333;
}

.btnGroup{
    display: flex;
    
}
.operation.btnGroup {
    justify-content: space-around;
}
.operation.btnGroup .iconfont{
    width: 30px;
    height: 30px;
}

.leftText{
    text-align: start;
}
.upload{
    overflow: hidden;
}

.infoText{
    width: 396px ;
    display: inline-block;
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
/* 搜索框样式 */
.el-input__wrapper {
    /* width: 300px; */
    height: 40px;
    border-radius: 0;
}

.el-date-editor.el-input {
    width: 300px;
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

/* 表单样式 */
.addForm .el-form-item{
    margin-bottom: 25px;
}
.addForm .el-form-item__label,
.addForm .el-form-item__content{
    height: 40px;
}
.el-form-item__error{
    margin-top: 5px;
}
/* 分页器样式 */
.block {
    float: right;
    margin-top: 20px;
    margin-bottom: 20px;
}
</style>
