import {get,post} from './request'
let header = window.Glob.baseURL_HD
let apiFun={};
// ------外部接口------ //
// 高德地图接口
apiFun.getCityData = p => get('https://restapi.amap.com/v3/config/district',p)
// -----登录页面接口----- //
// 登录接口封装
apiFun.postLogin = p => post('/hdptbase/api/user/login', p)
apiFun.postReg = p => post('/hdptbase/api/user/regUser',p)
apiFun.postUserInfo = p => post('/hdptbase/api/user/info',p)
// 登出接口封装
apiFun.postExit = p => ('/sys/logout',p)
// -----用户中心接口----- //
// 用户公司信息展示接口封装
apiFun.getCompany = p => get('/hdptbase/api/userInfo/query/'+p)
// 用户公司信息修改接口封装
apiFun.postModifyCompany = p => post('/hdptbase/api/userInfo/updateInfo',p)
// 用户信息展示接口封装
apiFun.postUserMsg = p => post('/hdptbase/api/user/info',p)
// 用户信息修改接口封装
apiFun.postModifyUserMsg = p => post('/hdptbase/api/user/updateUser',p)
// 用户项目列表接口封装
apiFun.getPorject = p => get('/hdptbase/api/merchant/findList',p)
// 用户项目列表编辑接口封装
apiFun.postPorjectEdit = p => post('/hdptbase/api/merchant/updateMerchant',p)
// 用户项目列表添加接口封装
apiFun.postPorjectAdd = p => post('/hdptbase/api/merchant/addMerchant',p)
// 用户项目列表删除接口封装
apiFun.postPorjectDelete = p => post('/hdptbase/api/merchant/deleteMerchant',p)
// 用户项目列表分页查询接口封装
apiFun.getPagingPorject = p => get('/hdptbase/api/merchant/getPageMerchant',p)
// 网络设备绑定接口封装
apiFun.postNetworkEquipment = p =>post(header + '/hdptdevice/saveNetDeviceListInfo',p)
// 器具柜设备绑定接口封装
apiFun.postCabinetEquipment = p =>post(header + '/qjg/saveCabinetInfo',p)
// 用户角色列表接口封装
apiFun.getRoleMsg = p => get('/hdptbase/api/user/getPageByUser',p)
// 用户角色列表删除接口封装
apiFun.postRoleDelete = p => post('/hdptbase/api/user/deleteByExample',p)
// 用户角色列表新增接口封装
apiFun.postRoleAdd = p => post('/hdptbase/api/user/addInfo',p)
// 用户角色列表编辑接口封装
apiFun.postRoleEdit = p => post('/hdptbase/api/user/updateUser',p)


// ---角色管理页面接口---- //
// 角色管理页面查询列表
apiFun.getRoleList = p => get('/hdptbase/api/role/getPageBySelective',p)
// 角色管理页面删除角色
apiFun.postDelRoleList = p=> post('/hdptbase/api/role/deleteByExample',p)
// 角色管理页面新增角色
apiFun.postAddRole = p => post('/hdptbase/api/role/addInfo',p)
// 角色管理页面授权页查询菜单
apiFun.getMenu = p => get('/hdptbase/api/menu/getInfoById/' + p)
// 分页查询菜单
apiFun.getMenuList = p => get('/hdptbase/api/menu/getPageBySelective',p)
// 角色管理页面获取项目
apiFun.getMidList = p => get('/hdptbase/api/merchant/findList',p)
// 角色管理编辑角色
apiFun.UpdateRoleInfo = p => post('/hdptbase/api/role/updateByExample',p)
// ---系统日志页面接口---- //
// 系统日志页面获取
apiFun.getSystemLogList = p => get('/hdptbase/getLogInfo',p)
// 系统日志页面删除日志
apiFun.postSystemLogDel = p => post('/hdptbase/delLogInfo',p) 

// ---网络设备页面接口--- //
// 获取网络设备列表
apiFun.getNetWorkList = p => get(header + '/hdptdevice/getNetDevicePageList',p)
// 新增网络设备
apiFun.postSaveNetWork = P => post(header + '/hdptdevice/saveNetDeviceListInfo',P)
// 编辑网络设备
apiFun.postEditNetWork = p => post(header + '/hdptdevice/updateNetDeviceListInfo',p)
// 删除网络设备
apiFun.postDelNetWord = p => post(header + '/hdptdevice/delLogInfo',p)

// 获得智能设备列表
apiFun.getSmartDevicePageList = p => get(header + '/smartdevice/getSmartDevicePageList',p)
// 保存智能设备
apiFun.postSaveSmartListInfo = p=> post(header + '/smartdevice/saveSmartListInfo',p)
// 删除智能设备
apiFun.postDelSmartDeviceInfo = p => post(header + '/smartdevice/delSmartDeviceInfo', p)
//开锁
apiFun.postOpenLock = p => post(header + '/hdptdevice/openLock', p)

//关锁
apiFun.postCloseLock = p=> post(header + '/hdptdevice/closeLock',p)

// 获取器具柜设备列表
apiFun.getPageCabList = p => get(header + '/qjg/getPageCabList' ,p)
// 新增器具柜
apiFun.postAddCabList = p => post(header + '/qjg/saveCabinetInfo',p)
// 删除柜
apiFun.postDelCabList = p => post(header + '/qjg/delLogInfo',p)

//----系统参数接口----//
// 获取系统参数
apiFun.getSystemParams = p => post('/hdptbase/api/sysparam/getSysParamByType',p)

// 数据大屏页面接口
// 获取设备在线数和离线数
// 获取设备地区数量排行
apiFun.getDAndQ = p => get(header + '/dataScreen/getProvinceDeviceNum',p)
// 获取设备名称和数量
apiFun.getEAndQ = p => get(header + '/dataScreen/getDeviceTypeNum',p)
// 获取地图数据
apiFun.getMapData = p => get(header + '/dataScreen/getAreaDeviceNum',p)
// 获取平台访客人数和注册人数
apiFun.getOAndO = p => get(header + '/dataScreen/getStatusDeviceNum',p)
// 获取设备位置和状态
apiFun.getLAndS = p => get(header + '/dataScreen/getDeviceAreaStatus',p)


// 无线定位接口
// 获取设备信息
apiFun.getNetList = p => get(header + '/hdptdevice/getNetDeviceListByMids',p)


// 参数配置
apiFun.getParams = p => post('/hdptbase/api/sysparam/getSysParamByTypeAndCode',p)
export default apiFun;
