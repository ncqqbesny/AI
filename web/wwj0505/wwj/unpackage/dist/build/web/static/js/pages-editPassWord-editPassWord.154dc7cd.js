(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-editPassWord-editPassWord"],{"0a11":function(e,t,n){"use strict";n.r(t);var r=n("3b9e"),o=n("6398");for(var a in o)["default"].indexOf(a)<0&&function(e){n.d(t,e,(function(){return o[e]}))}(a);n("7253");var u=n("f0c5"),i=Object(u["a"])(o["default"],r["b"],r["c"],!1,null,"39a76b32",null,!1,r["a"],void 0);t["default"]=i.exports},"0d53":function(e,t,n){"use strict";n("7a82");var r=n("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=r(n("f3f3"));n("14d9");var a=n("b85b"),u=n("664c"),i={name:"changePwd",data:function(){return{errShow:!1,errMsg:"错误",oldPwd:"",newPwd:"",newPwds:"",userFrom:{}}},computed:{loginUser:function(){return this.$store.state.loginUser}},methods:{encodepassword:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:[];return t.push(e),(0,a.encode)("0x12",t)},showErr:function(e){var t=this;this.errShow=!0,this.errMsg=e,setTimeout((function(){t.errShow=!1}),2e3)},confirm:function(){var e=this;this.oldPwd?this.encodepassword(this.oldPwd)==this.userFrom.password?this.newPwd===this.newPwds?this.newPwd.length<6||this.newPwd.length>12?this.showErr("密码长度为6~12位！"):this.setUser():this.showErr("两次输入密码不一致，请重新输入"):setTimeout((function(){e.showErr("旧密码输入错误,请重新输入")}),500):setTimeout((function(){e.showErr("请输入旧密码")}),500)},getUser:function(){var e=this;console.log(this.loginUser),(0,u.queryId)(this.loginUser.id).then((function(t){console.log(t),e.userFrom=(0,o.default)({},t.data.data)})).catch((function(e){console.error(e)}))},setUser:function(){var e={};e=(0,o.default)({},this.userFrom),e.password=this.encodepassword(this.newPwd),(0,u.setUserInfo)(e).then((function(e){console.log(e),uni.showToast({title:"密码修改成功，即将返回登录页"}),setTimeout((function(){uni.removeStorage({key:"user_token",success:function(e){uni.redirectTo({url:"/pages/index/index"})}})}),1500)}))}},onShow:function(){this.getUser()}};t.default=i},"1e85":function(e,t,n){var r=n("24fb");t=r(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.changePwd[data-v-39a76b32]{width:100vw;height:100vh;background-color:#f1f1f1;padding:40px 16px;box-sizing:border-box}.changePwd_title[data-v-39a76b32]{width:100%;font-size:28px}.changePwd_form_wrap[data-v-39a76b32]{width:100%;margin-top:28px;margin-bottom:18px;border-bottom:1px solid #ccc;padding:12px 0}uni-text[data-v-39a76b32]{width:80px;text-align:left}uni-input[data-v-39a76b32]{margin-top:%?12?%;border:0;background-color:#f1f1f1}.tips[data-v-39a76b32]{color:#666}.btn[data-v-39a76b32]{width:100%;display:flex;justify-content:center;margin-top:%?40?%}.btn uni-button[data-v-39a76b32]{width:92%;height:40px;border-radius:4px;margin-top:28px;color:#fff;background-color:#5677fc;border:0}.err[data-v-39a76b32]{position:absolute;top:0;left:0;width:100%;height:0;line-height:46px;background-color:#e65557;transition:all .5s;overflow:hidden;color:#fff;padding-left:8px}.errShow[data-v-39a76b32]{height:46px;transition:all .5s;z-index:999999999}',""]),e.exports=t},"2e18":function(e,t,n){"use strict";n("7a82"),Object.defineProperty(t,"__esModule",{value:!0}),t.unCodeRequest=t.myRequest=t.hdUncodeRequest=t.hdRequest=void 0,n("d3b7");var r="https://wwjbase.okai360.com",o="https://wwjdevice.okai360.com";uni.setStorage({key:"baseUrl",data:r}),uni.setStorage({key:"hdUrl",data:o}),uni.setStorage({key:"Material",data:"https://www.okai360.com/loadfile/"});t.myRequest=function(e){return new Promise((function(t,n){uni.request({url:r+e.url,method:e.method||"GET",data:e.data||{},header:{token:uni.getStorageSync("user_token")},success:function(e){if(400==e.data.status)return uni.showToast({title:e.data.msg,icon:"none",success:function(){uni.redirectTo({url:"/pages/index/index"})}});t(e)},fail:function(e){console.log(e),n(e)}})}))};t.unCodeRequest=function(e){return new Promise((function(t,n){uni.request({url:r+e.url,method:e.method||"GET",data:e.data||{},header:{token:uni.getStorageSync("user_token"),"Content-Type":"application/x-www-form-urlencoded"},success:function(e){if(400==e.data.status)return uni.showToast({title:e.data.msg,icon:"none",success:function(){uni.redirectTo({url:"/pages/index/index"})}});t(e)},fail:function(e){console.log(e),n(e)}})}))};t.hdRequest=function(e){return new Promise((function(t,n){uni.request({url:o+e.url,method:e.method||"GET",data:e.data||{},header:{token:uni.getStorageSync("user_token")},success:function(e){if(400==e.data.status)return uni.showToast({title:e.data.msg,icon:"none",success:function(){uni.redirectTo({url:"/pages/index/index"})}});t(e)},fail:function(e){console.log(e),n(e)}})}))};t.hdUncodeRequest=function(e){return new Promise((function(t,n){uni.request({url:o+e.url,method:e.method||"GET",data:e.data||{},header:{token:uni.getStorageSync("user_token"),"Content-Type":"application/x-www-form-urlencoded"},success:function(e){if(400==e.data.status)return uni.showToast({title:e.data.msg,icon:"none",success:function(){uni.redirectTo({url:"/pages/index/index"})}});t(e)},fail:function(e){console.log(e),n(e)}})}))}},3279:function(e,t,n){var r=n("1e85");r.__esModule&&(r=r.default),"string"===typeof r&&(r=[[e.i,r,""]]),r.locals&&(e.exports=r.locals);var o=n("4f06").default;o("0f274366",r,!0,{sourceMap:!1,shadowMode:!1})},"3b9e":function(e,t,n){"use strict";n.d(t,"b",(function(){return r})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){}));var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-uni-view",{staticClass:"changePwd"},[n("v-uni-text",{staticClass:"changePwd_title"},[e._v("设置新密码")]),n("v-uni-form",[n("v-uni-view",{staticClass:"changePwd_form_wrap"},[n("v-uni-text",[e._v("旧密码：")]),n("v-uni-input",{attrs:{type:"password",placeholder:"请输入当前密码"},model:{value:e.oldPwd,callback:function(t){e.oldPwd=t},expression:"oldPwd"}})],1),n("v-uni-view",{staticClass:"changePwd_form_wrap"},[n("v-uni-text",[e._v("新密码：")]),n("v-uni-input",{attrs:{type:"password",placeholder:"请设置新密码"},model:{value:e.newPwd,callback:function(t){e.newPwd=t},expression:"newPwd"}})],1),n("v-uni-view",{staticClass:"changePwd_form_wrap"},[n("v-uni-text",[e._v("确认密码：")]),n("v-uni-input",{attrs:{type:"password",placeholder:"请再次输入新密码"},model:{value:e.newPwds,callback:function(t){e.newPwds=t},expression:"newPwds"}})],1)],1),n("v-uni-text",{staticClass:"tips"},[e._v("密码长度6-12位，由字母数字_构成")]),n("v-uni-view",{staticClass:"btn"},[n("v-uni-button",{on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.confirm.apply(void 0,arguments)}}},[e._v("确定")])],1),n("v-uni-view",{staticClass:"err",class:e.errShow?"errShow":"err"},[e._v(e._s(e.errMsg))])],1)},o=[]},6398:function(e,t,n){"use strict";n.r(t);var r=n("0d53"),o=n.n(r);for(var a in r)["default"].indexOf(a)<0&&function(e){n.d(t,e,(function(){return r[e]}))}(a);t["default"]=o.a},"664c":function(e,t,n){"use strict";n("7a82"),Object.defineProperty(t,"__esModule",{value:!0}),t.RoleQueryId=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/role/"+e,method:"get"})},t.addProject=function(e){return(0,r.myRequest)({url:"/hdptbase/api/merchant/addMerchant",method:"post",data:e})},t.addRoleInfo=function(e){return(0,r.myRequest)({url:"/hdptbase/api/role/addInfo",method:"post",data:e})},t.addUserInfo=function(e){return(0,r.myRequest)({url:"/hdptbase/api/user/addInfo",method:"post",data:e})},t.delOrder=function(e){return(0,r.hdRequest)({url:"/yjl/delOrder",method:"POST",data:e})},t.delProject=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/merchant/deleteMerchant",method:"post",data:e})},t.delRoleInfo=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/role/deleteByExample",method:"post",data:e})},t.delSmart=function(e){return(0,r.hdRequest)({url:"/smartdevice/delSmartDeviceInfo",method:"post",data:e})},t.delUseInfo=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/user/deleteByExample",method:"post",data:e})},t.editProject=function(e){return(0,r.myRequest)({url:"/hdptbase/api/merchant/updateMerchant",method:"post",data:e})},t.findEvent=function(e){return(0,r.hdRequest)({url:"/event/getEventPageList",method:"get",data:e})},t.findEventInfo=function(e){return(0,r.hdRequest)({url:"/event/getEventList",method:"get",data:e})},t.findOrder=function(e){return(0,r.hdRequest)({url:"/yjl/getOrderPageList",method:"get",data:e})},t.getBitList=function(e){return(0,r.hdUncodeRequest)({url:"/yjc/getBitCtrlPageList",method:"get",data:e})},t.getDoc=function(e){return(0,r.hdRequest)({url:"/doc/getDocPageList",method:"get",data:e})},t.getLoginUserInfo=function(e){return(0,r.myRequest)({url:"/hdptbase/api/user/info",method:"post"})},t.getMenuList=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/menu/getPageBySelective",method:"get",data:e})},t.getProjectList=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/merchant/getPageMerchant",method:"get",data:e})},t.getQueryProject=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/merchant/findList",method:"get",data:e})},t.getRoleList=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/role/getPageBySelective",method:"get",data:e})},t.getSmart=function(e){return(0,r.hdUncodeRequest)({url:"/smartdevice/getSmartDeviceList",method:"get",data:e})},t.getSmartList=function(e){return(0,r.hdUncodeRequest)({url:"/smartdevice/getSmartDevicePageList",method:"get",data:e})},t.getUserList=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/api/user/getPageByUser",method:"get",data:e})},t.login=function(e){return(0,r.myRequest)({url:"/hdptbase/api/user/login",method:"post",data:e})},t.operBit=function(e){return(0,r.hdRequest)({url:"/yjc/operBitCtrl",method:"post",data:e})},t.queryId=function(e){return(0,r.unCodeRequest)({url:"/hdptbase/user/query/"+e,method:"get"})},t.saveEvent=function(e){return(0,r.hdRequest)({url:"/event/saveEvent",method:"post",data:e})},t.saveOrder=function(e){return(0,r.hdRequest)({url:"/yjl/saveOrder",method:"POST",data:e})},t.setRoleInfo=function(e){return(0,r.myRequest)({url:"/hdptbase/api/role/updateByExample",method:"post",data:e})},t.setSmart=function(e){return(0,r.hdRequest)({url:"/smartdevice/saveSmartListInfo",method:"post",data:e})},t.setUserInfo=function(e){return(0,r.myRequest)({url:"/hdptbase/api/user/updateUser",method:"post",data:e})},t.upImg=function(e){return(0,r.hdRequest)({url:"/yjl/uploadFiles",method:"POST",data:e})};var r=n("2e18")},7253:function(e,t,n){"use strict";var r=n("3279"),o=n.n(r);o.a},b85b:function(e,t,n){"use strict";n("7a82");var r=n("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.arrToTree=function e(t,n){var r=[];return t.forEach((function(o){if(o.parentMenuId===n){var a=e(t,o.menuId);a.length&&(o.children=a),r.push(o)}})),r},t.arrToTree2=function(e){var t={},n=[];return e.forEach((function(e){t[e.mid]=(0,o.default)((0,o.default)({},e),{},{children:[]})})),e.forEach((function(e){var r=t[e.parentMid];r?r.children.push(t[e.mid]):n.push(t[e.mid])})),n},t.checkNumberInArray=function(e,t){return e.includes(t)},t.encode=function(e,t){for(var n=parseInt(e),r=[],o=0;o<t.length;++o){var a=t[o];r.push(a.length^n);for(var u=0;u<a.length;++u)r.push(a[u].charCodeAt(0)^n)}r.push(n);var i="CV16"+r.join("%");return i};var o=r(n("f3f3"));n("e25e"),n("14d9"),n("d3b7"),n("159b"),n("caad6"),n("2532")}}]);