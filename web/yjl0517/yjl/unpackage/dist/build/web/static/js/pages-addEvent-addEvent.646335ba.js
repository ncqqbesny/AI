(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-addEvent-addEvent"],{"17fcd":function(t,e,a){"use strict";a("6a54");var n=a("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0,a("aa77"),a("bf0f"),a("795c"),a("c223");var o=n(a("9b1b")),i=a("7e7d"),r=a("03c2"),d={data:function(){return{latitude:30.312919,longitude:120.066184,covers:[],data:{address:"",remark:""},mname:"",nowDate:"",checkNumberInArray:i.checkNumberInArray,peopleNumer:2}},computed:{loginUser:function(){return this.$store.state.loginUser},loginUserInfo:function(){return this.$store.state.loginUserInfo},menu:function(){return this.$store.state.menu}},methods:{openMap:function(){console.log(123456);var t=this;uni.chooseLocation({success:function(e){t.latitude=e.latitude,t.longitude=e.longitude,t.covers=[{id:0,latitude:e.latitude,longitude:e.longitude,iconPath:"/static/icon/makers.png"}],t.data.address=e.address+e.name,console.log("位置名称："+e.name),console.log("详细地址："+e.address),console.log("纬度："+e.latitude),console.log("经度："+e.longitude)}}),console.log(this.covers)},showAlert:function(){this.$refs.alertAdd.open()},save:function(){var t=(0,o.default)({},this.data);t.latitude=this.latitude,t.longitude=this.longitude,t.code=this.mname+this.nowDate,t.optUsername=this.loginUserInfo.name,t.title=this.data.remark,t.mid=this.loginUserInfo.mid,console.log(t),(0,r.saveEvent)(t).then((function(t){if(200!=t.data.code)return uni.showToast({title:t.data.msg,icon:"error"});uni.showToast({title:"应急指令已发送"}),uni.navigateBack()}))},getMerchant:function(){var t=this;(0,r.getQueryProject)({MId:this.loginUserInfo.mid}).then((function(e){200==e.data.code&&(e.data.data.find((function(e){return e.mid==t.loginUserInfo.mid}))&&(t.mname=e.data.data.find((function(e){return e.mid==t.loginUserInfo.mid})).mname),console.log(t.mname))}))},getPeopleNumber:function(){var t=this;(0,r.getSendMsgPhone)().then((function(e){200==e.data.code&&(console.log(e.data),t.peopleNumer=e.data.data)}))}},onShow:function(){var t=new Date,e=t.getFullYear(),a=String(t.getMonth()+1).padStart(2,"0"),n=String(t.getDate()).padStart(2,"0"),o=String(t.getHours()).padStart(2,"0"),i=String(t.getMinutes()).padStart(2,"0"),r=String(t.getSeconds()).padStart(2,"0");this.nowDate="".concat(e).concat(a).concat(n).concat(o).concat(i).concat(r),this.getMerchant(),this.getPeopleNumber()},onReachBottom:function(){console.log("触底啦")}};e.default=d},"1a3d":function(t,e,a){"use strict";a.r(e);var n=a("17fcd"),o=a.n(n);for(var i in n)["default"].indexOf(i)<0&&function(t){a.d(e,t,(function(){return n[t]}))}(i);e["default"]=o.a},"5ba3":function(t,e,a){"use strict";var n=a("ba00"),o=a.n(n);o.a},"73e1":function(t,e,a){"use strict";var n=a("29d8");t.exports=/Version\/10(?:\.\d+){1,2}(?: [\w./]+)?(?: Mobile\/\w+)? Safari\//.test(n)},"795c":function(t,e,a){"use strict";var n=a("8bdb"),o=a("db04").start,i=a("73e1");n({target:"String",proto:!0,forced:i},{padStart:function(t){return o(this,t,arguments.length>1?arguments[1]:void 0)}})},"7e7d":function(t,e,a){"use strict";a("6a54");var n=a("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.arrToTree=function t(e,a){var n=[];return e.forEach((function(o){if(o.parentMenuId===a){var i=t(e,o.menuId);i.length&&(o.children=i),n.push(o)}})),n},e.arrToTree2=function(t){var e={},a=[];return t.forEach((function(t){e[t.mid]=(0,o.default)((0,o.default)({},t),{},{children:[]})})),t.forEach((function(t){var n=e[t.parentMid];n?n.children.push(e[t.mid]):a.push(e[t.mid])})),a},e.checkNumberInArray=function(t,e){return t.includes(e)},e.encode=function(t,e){for(var a=parseInt(t),n=[],o=0;o<e.length;++o){var i=e[o];n.push(i.length^a);for(var r=0;r<i.length;++r)n.push(i[r].charCodeAt(0)^a)}n.push(a);var d="CV16"+n.join("%");return d};var o=n(a("9b1b"));a("e966"),a("aa9c"),a("bf0f"),a("2797"),a("4626"),a("5ac7")},"80e6":function(t,e,a){"use strict";a.r(e);var n=a("9e93"),o=a("1a3d");for(var i in o)["default"].indexOf(i)<0&&function(t){a.d(e,t,(function(){return o[t]}))}(i);a("5ba3");var r=a("828b"),d=Object(r["a"])(o["default"],n["b"],n["c"],!1,null,"70c89a38",null,!1,n["a"],void 0);e["default"]=d.exports},"9e93":function(t,e,a){"use strict";a.d(e,"b",(function(){return o})),a.d(e,"c",(function(){return i})),a.d(e,"a",(function(){return n}));var n={uniEasyinput:a("9ccf").default,uniPopup:a("7822").default,uniPopupDialog:a("eb8c").default},o=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"container"},[a("v-uni-view",{staticClass:"view_name"},[a("v-uni-text",{},[t._v("事件编号："+t._s(t.mname)+t._s(t.nowDate))])],1),a("v-uni-view",{staticClass:"map",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.openMap.apply(void 0,arguments)}}},[a("v-uni-map",{staticStyle:{width:"100%",height:"485rpx"},attrs:{latitude:t.latitude,longitude:t.longitude,markers:t.covers}})],1),a("v-uni-view",{staticClass:"btn",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.openMap.apply(void 0,arguments)}}},[t._v("选择地点")]),a("v-uni-view",{staticClass:"address"},[a("v-uni-text",[t._v("事件地址：")]),a("uni-easyinput",{attrs:{disableColor:!0,placeholder:"添加详细地址",inputBorder:!1,maxlength:20,type:"textarea",autoHeight:!0},model:{value:t.data.address,callback:function(e){t.$set(t.data,"address",e)},expression:"data.address"}})],1),a("v-uni-view",{staticClass:"remark"},[a("v-uni-text",[t._v("事件备注：")]),a("uni-easyinput",{attrs:{disableColor:!0,placeholder:"设备备注",inputBorder:!1,maxlength:20,type:"textarea",autoHeight:!0},model:{value:t.data.remark,callback:function(e){t.$set(t.data,"remark",e)},expression:"data.remark"}})],1),a("v-uni-view",{staticClass:"footer"},[a("v-uni-view",{staticClass:"footet_item"},[a("v-uni-view",{staticClass:"item_info"},[t._v("当前待命应急人员："+t._s(t.peopleNumer)+"人")]),a("v-uni-view",{staticClass:"item_btn",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.showAlert.apply(void 0,arguments)}}},[t._v("发起应急指令")]),a("uni-popup",{ref:"alertAdd",attrs:{type:"dialog"}},[a("uni-popup-dialog",{attrs:{type:"info",cancelText:"关闭",confirmText:"确认",title:"通知",content:"确认发起应急指令?"},on:{confirm:function(e){arguments[0]=e=t.$handleEvent(e),t.save.apply(void 0,arguments)},close:function(e){arguments[0]=e=t.$handleEvent(e),t.dialogClose.apply(void 0,arguments)}}})],1)],1)],1)],1)},i=[]},ba00:function(t,e,a){var n=a("d9c6");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var o=a("967d").default;o("2b39ecdc",n,!0,{sourceMap:!1,shadowMode:!1})},d9c6:function(t,e,a){var n=a("c86c");e=n(!1),e.push([t.i,"/* @font-face {\r\n\tfont-family: 'alihei';\r\n\tsrc: url('https://yjl.linkom.com.cn/loadfile/ht.ttf') format('truetype');\r\n}\r\n\r\n\r\n\r\n@font-face {\r\n\tfont-family: 'alyuan';\r\n\tsrc: url('https://yjl.linkom.com.cn/loadfile/fy.ttf') format('truetype');\r\n} */\r\n/* h5跨域 */@font-face{font-family:alihei;src:url(/yjlcdn/loadfile/ht.ttf) format(\"truetype\")}@font-face{font-family:alyuan;src:url(/yjlcdn/loadfile/fy.ttf) format(\"truetype\")}.container[data-v-70c89a38]{min-height:100vh;padding-bottom:%?45?%}.container[data-v-70c89a38]::before{content:\"\";display:table;height:0}.container[data-v-70c89a38]::before{content:\"\";display:table;height:0}.searchBox[data-v-70c89a38]{margin:%?25?%}.view_name[data-v-70c89a38]{margin-left:%?22?%;margin-top:%?22?%;font-size:%?39?%;font-family:alihei;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}.map[data-v-70c89a38]{width:%?630?%;margin:0 auto;/* 容器 6 */border-radius:%?35?%;margin-top:%?68?%;overflow:hidden;box-shadow:inset 0 %?3?% %?3?% 5px rgba(0,0,0,.3)}.btn[data-v-70c89a38]{margin:%?25?% auto;width:%?250?%;display:flex;justify-content:center;height:%?80?%;align-items:center;background-color:#f5f4f6;box-shadow:%?10?% 0 %?10?% rgba(0,0,0,.3);border-radius:%?80?%;font-family:alyuan;transition:.1s}.btn[data-v-70c89a38]:active{background-color:rgba(0,0,0,.2)}.address[data-v-70c89a38]{margin:0 auto;width:%?630?%;height:%?141?%;border-radius:%?35?%;overflow:hidden;background:#f0f0f3;font-family:alyuan;padding:%?10?% %?14?%;font-size:%?27?%;line-height:%?40?%;box-shadow:inset 0 %?3?% %?3?% rgba(0,0,0,.3)}.remark[data-v-70c89a38]{margin:%?23?% auto %?250?%;width:%?630?%;height:234px;overflow:hidden;border-radius:%?35?%;font-family:alyuan;font-size:%?27?%;padding:%?10?% %?14?%;line-height:%?40?%;background:#f0f0f3;box-shadow:inset 0 %?3?% %?3?% rgba(0,0,0,.3)}.footer[data-v-70c89a38]{border-radius:%?35?% %?35?% 0 0;position:fixed;bottom:0;width:%?750?%;height:%?210?%;background-color:#f5f4f6;box-shadow:0 %?-10?% %?10?% rgba(0,0,0,.3);display:flex;align-items:center;justify-content:center}.footet_item[data-v-70c89a38]{width:%?595?%;height:%?108?%;border-radius:%?54?%;background:#005f7a;display:flex}.item_info[data-v-70c89a38]{width:%?315?%;height:%?108?%;line-height:%?108?%;text-align:center;font-size:%?25?%;color:#f0f0f3;font-family:alyuan}.item_btn[data-v-70c89a38]{width:%?280?%;height:%?108?%;border-radius:%?54?%;background-color:#87735f;text-align:center;line-height:%?108?%;font-size:%?32?%;xt-align:center;color:#f0f0f3;transition:.1s}.item_btn[data-v-70c89a38]:active{background-color:rgba(0,0,0,.2)}[data-v-70c89a38] .uni-easyinput__content{font-size:%?27?%!important;background:none!important;color:#9e9e9e!important;font-family:alyuan}.uni-easyinput__content[data-v-70c89a38]{font-size:%?27?%!important;background:none!important;color:#9e9e9e!important;font-family:alyuan}",""]),t.exports=e},db04:function(t,e,a){"use strict";var n=a("bb80"),o=a("c435"),i=a("9e70"),r=a("f298"),d=a("862c"),c=n(r),s=n("".slice),u=Math.ceil,l=function(t){return function(e,a,n){var r,l,f=i(d(e)),h=o(a),g=f.length,v=void 0===n?" ":i(n);return h<=g||""===v?f:(r=h-g,l=c(v,u(r/v.length)),l.length>r&&(l=s(l,0,r)),t?f+l:l+f)}};t.exports={start:l(!1),end:l(!0)}},f298:function(t,e,a){"use strict";var n=a("497b"),o=a("9e70"),i=a("862c"),r=RangeError;t.exports=function(t){var e=o(i(this)),a="",d=n(t);if(d<0||d===1/0)throw new r("Wrong number of repetitions");for(;d>0;(d>>>=1)&&(e+=e))1&d&&(a+=e);return a}}}]);