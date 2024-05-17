(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-editPassWord-editPassWord"],{1038:function(e,t,n){"use strict";n("6a54");var r=n("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=r(n("9b1b"));n("aa9c");var i=n("7e7d"),a=n("03c2"),s={name:"changePwd",data:function(){return{errShow:!1,errMsg:"错误",oldPwd:"",newPwd:"",newPwds:"",userFrom:{}}},computed:{loginUser:function(){return this.$store.state.loginUser},loginUserInfo:function(){return this.$store.state.loginUserInfo}},methods:{encodepassword:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:[];return t.push(e),(0,i.encode)("0x12",t)},showErr:function(e){var t=this;this.errShow=!0,this.errMsg=e,setTimeout((function(){t.errShow=!1}),2e3)},confirm:function(){var e=this;this.oldPwd?this.encodepassword(this.oldPwd)==this.userFrom.password?this.newPwd===this.newPwds?this.newPwd.length<6||this.newPwd.length>12?this.showErr("密码长度为6~12位！"):this.encodepassword(this.newPwd)==this.loginUserInfo.password?this.showErr("新密码不能与旧密码一致！"):this.setUser():this.showErr("两次输入密码不一致，请重新输入"):setTimeout((function(){e.showErr("旧密码输入错误,请重新输入")}),500):setTimeout((function(){e.showErr("请输入旧密码")}),500)},getUser:function(){var e=this;console.log(this.loginUser),(0,a.queryId)(this.loginUser.id).then((function(t){console.log(t),e.userFrom=(0,o.default)({},t.data.data)})).catch((function(e){console.error(e)}))},setUser:function(){var e={};e=(0,o.default)({},this.userFrom),e.password=this.encodepassword(this.newPwd),e.opType=3,(0,a.setUserInfo)(e).then((function(e){console.log(e),200==e.data.code?(uni.showToast({title:"密码修改成功，即将返回登录页"}),setTimeout((function(){uni.removeStorage({key:"user_token",success:function(e){uni.redirectTo({url:"/pages/index/index"})}})}),1500)):uni.showToast({icon:"error",title:e.data.msg})}))}},onShow:function(){this.getUser()}};t.default=s},1975:function(e,t,n){var r=n("ca28");r.__esModule&&(r=r.default),"string"===typeof r&&(r=[[e.i,r,""]]),r.locals&&(e.exports=r.locals);var o=n("967d").default;o("a7f6abdc",r,!0,{sourceMap:!1,shadowMode:!1})},"5d5c":function(e,t,n){"use strict";n.d(t,"b",(function(){return r})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){}));var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-uni-view",{staticClass:"changePwd"},[n("v-uni-text",{staticClass:"changePwd_title"},[e._v("设置新密码")]),n("v-uni-form",[n("v-uni-view",{staticClass:"changePwd_form_wrap"},[n("v-uni-text",[e._v("旧密码：")]),n("v-uni-input",{attrs:{type:"password",placeholder:"请输入当前密码"},model:{value:e.oldPwd,callback:function(t){e.oldPwd=t},expression:"oldPwd"}})],1),n("v-uni-view",{staticClass:"changePwd_form_wrap"},[n("v-uni-text",[e._v("新密码：")]),n("v-uni-input",{attrs:{type:"password",placeholder:"请设置新密码"},model:{value:e.newPwd,callback:function(t){e.newPwd=t},expression:"newPwd"}})],1),n("v-uni-view",{staticClass:"changePwd_form_wrap"},[n("v-uni-text",[e._v("确认密码：")]),n("v-uni-input",{attrs:{type:"password",placeholder:"请再次输入新密码"},model:{value:e.newPwds,callback:function(t){e.newPwds=t},expression:"newPwds"}})],1)],1),n("v-uni-text",{staticClass:"tips"},[e._v("密码长度6-12位，由字母数字_构成")]),n("v-uni-view",{staticClass:"btn"},[n("v-uni-button",{on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.confirm.apply(void 0,arguments)}}},[e._v("确定")])],1),n("v-uni-view",{staticClass:"err",class:e.errShow?"errShow":"err"},[e._v(e._s(e.errMsg))])],1)},o=[]},"7e7d":function(e,t,n){"use strict";n("6a54");var r=n("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.arrToTree=function e(t,n){var r=[];return t.forEach((function(o){if(o.parentMenuId===n){var i=e(t,o.menuId);i.length&&(o.children=i),r.push(o)}})),r},t.arrToTree2=function(e){var t={},n=[];return e.forEach((function(e){t[e.mid]=(0,o.default)((0,o.default)({},e),{},{children:[]})})),e.forEach((function(e){var r=t[e.parentMid];r?r.children.push(t[e.mid]):n.push(t[e.mid])})),n},t.checkNumberInArray=function(e,t){return e.includes(t)},t.encode=function(e,t){for(var n=parseInt(e),r=[],o=0;o<t.length;++o){var i=t[o];r.push(i.length^n);for(var a=0;a<i.length;++a)r.push(i[a].charCodeAt(0)^n)}r.push(n);var s="CV16"+r.join("%");return s};var o=r(n("9b1b"));n("e966"),n("aa9c"),n("bf0f"),n("2797"),n("4626"),n("5ac7")},"9f79":function(e,t,n){"use strict";n.r(t);var r=n("1038"),o=n.n(r);for(var i in r)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return r[e]}))}(i);t["default"]=o.a},c5f4:function(e,t,n){"use strict";var r=n("1975"),o=n.n(r);o.a},ca28:function(e,t,n){var r=n("c86c");t=r(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.changePwd[data-v-de0cffb6]{width:100vw;height:100vh;background-color:#f1f1f1;padding:40px 16px;box-sizing:border-box}.changePwd_title[data-v-de0cffb6]{width:100%;font-size:28px}.changePwd_form_wrap[data-v-de0cffb6]{width:100%;margin-top:28px;margin-bottom:18px;border-bottom:1px solid #ccc;padding:12px 0}uni-text[data-v-de0cffb6]{width:80px;text-align:left}uni-input[data-v-de0cffb6]{margin-top:%?12?%;border:0;background-color:#f1f1f1}.tips[data-v-de0cffb6]{color:#666}.btn[data-v-de0cffb6]{width:100%;display:flex;justify-content:center;margin-top:%?40?%}.btn uni-button[data-v-de0cffb6]{width:92%;height:40px;border-radius:4px;margin-top:28px;color:#fff;background-color:#5677fc;border:0}.err[data-v-de0cffb6]{position:absolute;top:0;left:0;width:100%;height:0;line-height:46px;background-color:#e65557;transition:all .5s;overflow:hidden;color:#fff;padding-left:8px}.errShow[data-v-de0cffb6]{height:46px;transition:all .5s;z-index:999999999}',""]),e.exports=t},e82a:function(e,t,n){"use strict";n.r(t);var r=n("5d5c"),o=n("9f79");for(var i in o)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return o[e]}))}(i);n("c5f4");var a=n("828b"),s=Object(a["a"])(o["default"],r["b"],r["c"],!1,null,"de0cffb6",null,!1,r["a"],void 0);t["default"]=s.exports}}]);