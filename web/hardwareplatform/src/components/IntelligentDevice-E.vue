<template>
    <div class="searchBar">
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-position="left">
                <el-form-item label="设备编号" prop="deviceSn" class="searchBox">
                        <el-input v-model="searchForm.deviceSn" placeholder="请输入设备编号" />
                </el-form-item>
                <el-form-item label="设备名称" prop="deviceName" class="searchBox">
                    <el-input v-model="searchForm.deviceName" placeholder="请输入设备名称" />
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
                <el-form-item label="版本号" prop="deviceHw" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.deviceHw" placeholder="请输入版本号" />
                </el-form-item>
                <el-form-item label="MAC地址" prop="mac" class="searchBox" v-show="!serachFold">
                    <el-input v-model="searchForm.mac" placeholder="MAC地址" />
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
                    <el-form-item label="项目" prop="mid" class="searchBox" v-show="!serachFold">
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
                </div>
            </div>
        </div>
        <div>
            <el-table :data="tableData" border style="width: 100% " max-height="535"  @selection-change="handleSelectionChange"
                :default-sort="{ prop: 'deviceSn', order: 'ascending' }" fit="true"
            >
                <el-table-column type="selection" class="selection"> </el-table-column>
                <el-table-column prop="deviceSn" label="设备编号" class="webPort" sortable :width="flexColumnWidth('设备编号','deviceSn',20)"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.deviceSn }}
                        </div>  
                    </template>   
                </el-table-column>
                <el-table-column prop="province" label="地理位置" class="address"  :width="flexColumnWidth('地理位置','province',60)">
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.province ? scope.row.city ? scope.row.province + scope.row.city : '' : '' }}
                        </div>
                    </template> 
                </el-table-column>
                <el-table-column prop="deviceName" label="设备名称" class="onlineState" show-overflow-tooltip :width="flexColumnWidth('设备名称','deviceName')"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.deviceName }}
                        </div>
                    </template> 
                </el-table-column>
                <!-- <el-table-column prop="acUnit" label="单位" class="onlineState"> </el-table-column> -->
                <el-table-column prop="displayDeviceName" label="显示名称" class="onlineState" :width="flexColumnWidth('显示名称','displayDeviceName')"> 
                    <template #default="scope">
                        <div class="leftText">
                            {{ scope.row.displayDeviceName }}
                        </div>
                    </template> 
                </el-table-column>
                <el-table-column prop="status" label="在线状态" class="onlineState" width="80"> 
                    <template #default="scope">
                        <el-tag class="ml-2" :type="scope.row.status == 1 ?  'success' : 'danger'">{{scope.row.status == 1 ? '在线' : '离线'}}</el-tag>
                    </template> 
                </el-table-column>
                <el-table-column prop="runTime" label="工作时长" class="workingHours" :width="flexColumnWidth('工作时长','runTime',25)">
                    <template #default="scope">
                        <span>{{ changeTimeFormat(scope.row.runTime) }}</span>                                                                          
                    </template> 
                </el-table-column>
                <el-table-column prop="temperature" :label="deviceTypeCode == '0101' ? '环境温度' : '温度'" class="macNumber" :width="flexColumnWidth('温度','temperature')"> 
                    <template #default="scope">
                        {{ scope.row.temperature ? (~~(scope.row.temperature*100))/100 + '℃' : scope.row.temperature }}
                    </template> 
                </el-table-column>
                <el-table-column prop="humidity" :label="deviceTypeCode == '0101' ? '环境湿度' : '湿度'"  class="onlineAP" :width="flexColumnWidth('湿度','humidity',0)"> 
                    <template #default="scope">
                        {{ scope.row.humidity ?  (~~(scope.row.humidity*100))/100 + '%' : scope.row.humidity }}
                    </template> 
                </el-table-column>
                <!-- <el-table-column prop="acUserOnlineNum" label="在线用户" class="onlineUser"> </el-table-column> -->
                <!-- <el-table-column prop="acIp" label="WEB端口" class="webPort"> </el-table-column> -->
                <el-table-column prop="bindTime" label="绑定时间" class="webPort" :width="flexColumnWidth('绑定时间','bindTime',10)"> </el-table-column>
                <el-table-column prop="outTime" label="	出厂时间" class="webPort" :width="flexColumnWidth('出厂时间','outTime',10)"> </el-table-column>
                <el-table-column prop="deviceHw" label="版本号" class="version" width="60"> </el-table-column>
                <el-table-column prop="lastTime" label="最后更新时间" class="webPort" :width="flexColumnWidth('最后更新时间','lastTime',10)"> </el-table-column>
                <el-table-column label="操作" fixed="right" width="120">
                    <template #default="scope">
                        <div class="btnGroup operation">
                            <el-tooltip
                                effect="dark"
                                content="实时画面"
                                placement="top"
                                v-if="deviceTypeCode == '0102' ? true : false"
                            >
                                <el-button type="primary" @click="hrefTo('http://121.40.242.143:8086/linkomca/video.html')" class="iconfont">&#xe600;</el-button>
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
                <el-form-item label="设备编号" prop="deviceSn">
                    <el-input v-model="form.deviceSn" />
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
                <el-form-item label="设备名称" prop="deviceName" v-if="!this.editType">
                    <el-input v-model="form.deviceName" />
                </el-form-item>
                <!-- <el-form-item label="设备型号" prop="model">
                    <el-input v-model="form.model" />
                </el-form-item> -->
                <el-form-item label="软件版本号" prop="deviceSw">
                    <el-input v-model="form.deviceSw" />
                </el-form-item>
                <el-form-item label="硬件版本号" prop="deviceHw">
                    <el-input v-model="form.deviceHw" />
                </el-form-item>
                <!-- <el-form-item label="MAC" prop="mac">
                    <el-input v-model="form.mac" />
                </el-form-item>   -->
                <!-- <el-form-item label="gid" prop="gid">
                    <el-input v-model="form.gid" />
                </el-form-item> -->
                <el-form-item label="项目" prop="mid">
                    <el-select v-model="form.mid" class="m-2" placeholder="请选择" filterable>
                        <el-option v-for="item in midList" :key="item.mid" :label="item.mname" :value="item.mid" />
                    </el-select>
                </el-form-item>
                <el-form-item label="设备类型" prop="deviceTypeCode">
                    <el-select v-model="form.deviceTypeCode" placeholder="请选择设备类型" class="choose">
                        <el-option v-for="item in options" :key="item.paramVal" :label="item.label" :value="item.value"
                            class="optionLi">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地区" prop="province">
                    <!-- <span>省</span> -->
                    <el-select v-model="form.province" placeholder="请选择省份" class="choose Area" >
                        <el-option v-for="item in cityData" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="cityChange(item.districts)">
                        </el-option>
                    </el-select>
                    <span>---</span>
                    <el-select v-model="form.city" placeholder="请选择市级" class="choose Area">
                        <el-option v-for="item in cityList" :key="item.code" :label="item.name" :value="item.name"
                            class="optionLi" @click.native ="areaChange(item.districts)">
                        </el-option>
                    </el-select>
                    <span>---</span>
                    <el-select v-model="form.area" placeholder="请选择市级" class="choose Area">
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
                    <el-config-provider :locale="locale">
                        <el-date-picker
                            v-model="form.bindTime"
                            type="datetime"
                            placeholder="选择设备绑定时间"
                            format="YYYY/MM/DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            @change="bindtimeChange"
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
            :draggable="true"
        >
       

        <el-descriptions
            class="margin-top"
            :column="3"
            size="default"
            border
        >
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false" span="3">
                <template #label>
                            <div class="cell-item">
                                图片
                            </div>
                        </template>       
                <div class="infoImg" >
                    <img :src="infoForm.img" alt="" style="margin: 0 auto;">
                </div>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        显示名称
                    </div>
                </template>
                {{ infoForm.displayDeviceName }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        安装位置
                    </div>
                </template>
                {{ infoForm.displaySortorder }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        位置显示名称
                    </div>
                </template>
                {{ infoForm.sortorderDeviceName }}
            </el-descriptions-item>
            <!-- 第二行 -->
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        设备名称
                    </div>
                </template>
                {{ infoForm.deviceName }}
            </el-descriptions-item>
            <el-descriptions-item span="2">
                <template #label>
                    <div class="cell-item">
                        设备编号
                    </div>
                </template>
                {{ infoForm.deviceSn }}
            </el-descriptions-item>
            
            
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        软件版本号
                    </div>
                </template>
                {{ infoForm.deviceSw }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        硬件版本号
                    </div>
                </template>
                {{ infoForm.deviceHw }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        模版编码CODE
                    </div>
                </template>
                {{ infoForm.descCode }}
            </el-descriptions-item>
            
            <!-- 灭火器显示 -->
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        通讯心跳
                    </div>
                </template>
                {{ infoForm.keepalive }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        环境温度
                    </div>
                </template>
                {{ changeTem(infoForm.temperature) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        环境湿度
                    </div>
                </template>
                {{ changeHum(infoForm.humidity) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        灭火器颜色
                    </div>
                </template>
                {{ changeColor(infoForm.color) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        灭火器指针比例
                    </div>
                </template>
                {{ changeRat(infoForm.ratio) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        模块电量
                    </div>
                </template>
                {{ changePow(infoForm.power) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        文件名
                    </div>
                </template>
                {{ infoForm.file_name }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        执行结果
                    </div>
                </template>
                {{ infoForm.exe_result }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        倾斜角度
                    </div>
                </template>
                {{ changeAngle(infoForm.angle) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false" span="3">
                <template #label>
                    <div class="cell-item">
                        表头图片
                    </div>
                </template>
                {{ infoForm.img }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0101' ? true : false">
                <template #label>
                    <div class="cell-item">
                        图片路径
                    </div>
                </template>
                {{ infoForm.pic_url }}
            </el-descriptions-item>
            <!-- 环测网关显示 -->
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        震动
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoForm.vibrate == 0 ?  'success' : 'warning'">{{infoForm.vibrate == 0 ? '无震动' : '有震动'}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        烟感
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoForm.smoke_status == 0 ?  'success' : 'warning'">{{infoForm.smoke_status == 0 ? '正常' : '异常'}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        湿度
                    </div>
                </template>
                {{ changeHum(infoForm.humidity) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        温度
                    </div>
                </template>
                {{ changeTem(infoForm.temperature) }}
            </el-descriptions-item>
           
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        水浸
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoForm.water_status == 0 ?  'success' : 'info'">{{infoForm.water_status == 0 ? '正常' : '异常'}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        水浸电量
                    </div>
                </template>
                {{ changePow(infoForm.water_battery) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        气压
                    </div>
                </template>
                {{ changePressure(infoForm.pressure) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        噪声
                    </div>
                </template>
                {{ changeNolse(infoForm.nolse) }}
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        光照
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoForm.light == '1' ?  'success' : 'info'">{{infoForm.light == '1' ? '有光照' : '无光照'}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item v-if="deviceTypeCode == '0102' ? true : false">
                <template #label>
                    <div class="cell-item">
                        人员
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoForm.people == '1' ?  'success' : 'info'">{{infoForm.people == '1' ? '有人' : '无人'}}</el-tag>
            </el-descriptions-item>


            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        实际状态信息发送间隔（秒）
                    </div>
                </template>
                {{ infoForm.interval }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        经度
                    </div>
                </template>
                {{ infoForm.longitude }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        纬度
                    </div>
                </template>
                {{ infoForm.latitude }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        MAC地址
                    </div>
                </template>
                {{ infoForm.mac }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        项目名称
                    </div>
                </template>
                {{ infoForm.mname }}
            </el-descriptions-item>
            <el-descriptions-item span="1">
                <template #label>
                    <div class="cell-item">
                        业务发生时间
                    </div>
                </template>
                {{ infoForm.opHappTm }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        状态
                    </div>
                </template>
                <el-tag class="ml-2" :type="infoForm.status == 1 ?  'success' : 'info'">{{infoForm.status == 1 ? '在线' : '离线'}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        省
                    </div>
                </template>
                {{ infoForm.province }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        市
                    </div>
                </template>
                {{ infoForm.city }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        区
                    </div>
                </template>
                {{ infoForm.area }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        运行时长
                    </div>
                </template>
                {{ infoForm.runTime }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        出厂时间
                    </div>
                </template>
                {{ infoForm.outTime }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        绑定时间
                    </div>
                </template>
                {{ infoForm.bindTime }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        最后变更时间
                    </div>
                </template>
                {{ infoForm.lastTime }}
            </el-descriptions-item>
            <el-descriptions-item span="2">
                <template #label>
                    <div class="cell-item">
                        备注
                    </div>
                </template>
                {{ infoForm.remark }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        设备业务类型编码
                    </div>
                </template>
                {{ infoForm.devicetagCode }}
            </el-descriptions-item>
        </el-descriptions>

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
                deviceSn: [
                    { required: true, message: '请输入设备编号', trigger: 'blur' }
                ],
                mac:[
                    { required:true , message:'请输入MAC地址' , trigger:'blur'}
                ]
            }

export default {
    props:{
        deviceTypeCode:{
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
                    value: '0101',
                    label: '灭火器',
                },
                {
                    value: '0102',
                    label: '环测网关',
                }
            ],
            tableData: [],
            value: '',
            Selection:[],
            dialogvisible:false,
            infoVisible:false,
            upfileShow:false,
            form:{
                
            },
            midList:[],
            infoForm:{
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
                deviceSn:'',
                deviceName:'',
                status:'',
                province:'',
                city:'',
                area:'',
                address:'',
                deviceHw:'',
                outTime:'',
                mid:'',
                acMac:'',
                displayDeviceName:'',
                sortorderDeviceName:''
            },
            // 新增的表单规则
            addFormRules:addFormRules,
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
                deviceTypeCode:this.deviceTypeCode,
                sort:0,
                mid:parseInt(localStorage.getItem("user_mid"))
            }
            if(this.searchForm.deviceName.length != 0){
                data.deviceName = '%' + this.searchForm.deviceName + '%'
            }
            if(this.searchForm.deviceSn.length != 0){
                data.deviceSn = '%' + this.searchForm.deviceSn + '%'
            }
            if(this.searchForm.status.length != 0){
                data.status = this.searchForm.status 
            }
            if(this.searchForm.deviceHw != 0){
                data.deviceHw = this.searchForm.deviceHw 
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
                data.mid = this.searchForm.mid
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
            apiFun.getSmartDevicePageList(
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
            this.$confirm('即将前往监控界面', '提示',  {
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
                        this.Selection.forEach((item) => ids.push(item.gid))
                        console.log(this.Selection)
                        apiFun.postDelSmartDeviceInfo(
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
            this.form.area = ''
            this.form.city = ''
            this.$refs.form.resetFields()
           
            if(this.$refs.form){
                
            }
        },
        showEditDialog(item){
            this.$nextTick(() => { // 注意看这里
                this.form={...item}
                this.form.mid = parseInt(this.form.mid)
                this.dialogvisible = true
                this.editType = true
            });
            console.log(item)
            console.log(this.form)
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
                        apiFun.postSaveSmartListInfo(
                            data
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
                } else {   //校验不通过
                    return false;
                }
            });
           
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
      },
      changeColor(colorNum){
        let color
        switch (colorNum) {
            case 1:
                color = '红色'
                break;
            
            case 1:
                color = '绿色'
                break;
            
            case 1:
                color = '黄色'
                break;
            default:
                color = ''
                break;
        }
        return color
      },
      changeTem(tem){
        let str = ''
        if(!tem){
            str = '暂无数据'
        }else{
            str = (~~(tem*100))/100 + '℃' 
        }
        return str
      },
      changeHum(hum){
        let str = ''
        if(!hum){
            str = '0%'
        }else{
            str = (~~(hum*100))/100 + '%' 
        }
        return str
      },
      changeRat(rat){
        let str = ''
        if(!rat){
            str = '0%'
        }else{
            str = (rat * 100) + '%'
        }
        return str
      },
      changePow(power){
        let pow = ''
        if(power){
            pow = power + '%'
        }else{
            pow = '0%'
        }
        return pow
      },
      changeAngle(value){
        let angle = ''
        if(angle){
            angle = value + '°'
        }else{
            angle = '暂无数据'
        }
        return angle
      },
      changePressure(pressure){
        let newPressure
        if(pressure){
            newPressure = pressure + 'Pa'
        }else{
            newPressure = '暂无数据'
        }
        return newPressure
      },
      changeNolse(nolse){
        let newNolse 
        if(nolse){
            newNolse = nolse + 'dB'
        }else{
            newNolse = '暂无数据'
        }
        return newNolse
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

:deep(.Area .el-input__wrapper){
    width: 150px;
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
    text-indent:24px;
    text-align: start;
}
.upload{
    overflow: hidden;
}

.infoText{
    width: 396px ;
    display: inline-block;
}

/* 详情部分 */
.infoImg{
    width: 100%;
    display: flex;
    justify-content: center;;
}
.infoImg img{
    width: 640px;
    height: 480px;
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
