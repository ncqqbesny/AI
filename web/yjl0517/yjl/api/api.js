import {myRequest,unCodeRequest,hdRequest,hdUncodeRequest} from './request.js'

 // 登录
export function login (config) {  //登录
	return myRequest({
		url:'/hdptbase/api/user/login',
		method:'post',
		data:config
	})
}

// 用户（查询）
export function getUserList (config) {  //登录
	return unCodeRequest({
		url:'/hdptbase/api/user/getPageByUser',
		method:'get',
		data:config
	})
}
// 用户(单id查询)
export function queryId (params) {  //登录
	return unCodeRequest({
		url:'/hdptbase/user/query/'+ params,
		method:'get'
	})
}
// 用户（token查询）
export function getLoginUserInfo (params) {  //登录
	return myRequest({
		url:'/hdptbase/api/user/info',
		method:'post'
	})
}
// 修改用户信息
export function setUserInfo(config){
	return myRequest({
		url:'/hdptbase/api/user/updateUser',
		method:'post',
		data:config
	}		)
}
// 删除用户
export function delUseInfo(config){
	return unCodeRequest({
		url:'/hdptbase/api/user/deleteByExample',
		method:'post',
		data:config
	})
}
// 添加用户
export function addUserInfo(config){
	return myRequest({
		url:'/hdptbase/api/user/addInfo',
		method:'post',
		data:config
	})
}


// 角色 id查询
export function RoleQueryId (params) {  //登录
	return unCodeRequest({
		url:'/hdptbase/api/role/'+ params,
		method:'get'
	})
}
// 角色 按页查询
export function getRoleList(config){
	return unCodeRequest({
		url:'/hdptbase/api/role/getPageBySelective',
		method:'get',
		data:config
	})
}
// 角色 修改角色
export function setRoleInfo(config){
	return myRequest({
		url:'/hdptbase/api/role/updateByExample',
		method:'post',
		data:config
	})
}
// 角色 添加角色
export function addRoleInfo(config){
	return myRequest({
		url:'/hdptbase/api/role/addInfo',
		method:'post',
		data:config
	})
}
// 角色 删除角色
export function delRoleInfo(config){
	return unCodeRequest({
		url:'/hdptbase/api/role/deleteByExample',
		method:'post',
		data:config
	})
}
// 角色 数据权限查询
export function getPermissionById(config){
	return unCodeRequest({
		url:'/hdptbase/api/role/getPermissionById',
		method:'get',
		data:config
	})
}
// 菜单 按页查询
export function getMenuList(config){
	return unCodeRequest({
		url:'/hdptbase/api/menu/getPageBySelective',
		method:'get',
		data:config
	})
}

// 项目 按页查询
export function getProjectList(config){
	return unCodeRequest({
		url:'/hdptbase/api/merchant/getPageMerchant',
		method:'get',
		data:config
	})
}
// 项目 用户id查询
export function getQueryProject(config){
	return unCodeRequest({
		url:'/hdptbase/api/merchant/findList',
		method:'get',
		data:config
	})
}
// 项目 新增项目
export function addProject(config){
	return myRequest({
		url:'/hdptbase/api/merchant/addMerchant',
		method:'post',
		data:config
	})
}
// 项目 编辑项目
export function editProject(config){
	return myRequest({
		url:'/hdptbase/api/merchant/updateMerchant',
		method:'post',
		data:config
	})
}
// 项目 删除项目
export function delProject(config){
	return unCodeRequest({
		url:'/hdptbase/api/merchant/deleteMerchant',
		method:'post',
		data:config
	})
}

// 硬件 列表查询
export function getSmartList(config){
	return hdUncodeRequest({
		url:'/smartdevice/getSmartDevicePageList',
		method:'get',
		data:config
	})
}
// 硬件 条件查询
export function getSmart(config){
	return hdUncodeRequest({
		url:'/smartdevice/getSmartDeviceList',
		method:'get',
		data:config
	})
}
// 硬件 修改信息
export function setSmart(config){
	return hdRequest({
		url:'/smartdevice/saveSmartListInfo',
		method:'post',
		data:config
	})
}
// 硬件 获取控制列表
export function getBitList(config){
	return hdUncodeRequest({
		url:'/yjc/getBitCtrlPageList',
		method:'get',
		data:config
	})
}
// 硬件 操控
export function operBit(config){
	return hdRequest({
		url:'/yjc/operBitCtrl',
		method:'post',
		data:config
	})
}
// 硬件 删除
export function delSmart(config){
	return hdRequest({
		url:'/smartdevice/delSmartDeviceInfo',
		method:'post',
		data:config
	})
}
// 应急 查询列表
export function findOrder(config){
	return hdRequest({
		url:'/yjl/getOrderPageList',
		method:'get',
		data:config
	})
}
// 应急 图片上传
export function upImg(config){
	return hdRequest({
		url:'/yjl/uploadFiles',
		method:'POST',
		data:config
	})
}
// 应急 保存单据
export function saveOrder(config){
	return hdRequest({
		url:'/yjl/saveOrder',
		method:'POST',
		data:config
	})
}
// 应急 删除单据
export function delOrder(config){
	return hdRequest({
		url:'/yjl/delOrder',
		method:'POST',
		data:config
	})
}
// 应急 获取单据
export function getOrderList(config){
	return hdRequest({
		url:'/yjl/getOrderList',
		method:'GET',
		data:config
	})
}
// 事件 查询列表
export function findEvent(config){
	return hdRequest({
		url:'/event/getEventPageList',
		method:'get',
		data:config
	})
}
// 事件 查看详情
export function findEventInfo(config){
	return hdRequest({
		url:'/event/getEventList',
		method:'get',
		data:config
	})
}
// 事件 保存事件
export function saveEvent(config){
	return hdRequest({
		url:'/event/saveEvent',
		method:'post',
		data:config
	})
}
// 事件获取人员数量
export function getSendMsgPhone(config){
	return hdRequest({
		url:'/event/getSendMsgPhone',
		method:'get',
		data:config
	})
}
// 首页 文章获取
export function getDoc(config){
	return hdRequest({
		url:'/doc/getDocPageList',
		method:'get',
		data:config
	})
}