(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-orderList-orderList"],{"1c48":function(t,e,a){"use strict";a.r(e);var i=a("d949"),n=a("f58e");for(var r in n)["default"].indexOf(r)<0&&function(t){a.d(e,t,(function(){return n[t]}))}(r);a("eb30");var o=a("828b"),c=Object(o["a"])(n["default"],i["b"],i["c"],!1,null,"dabda300",null,!1,i["a"],void 0);e["default"]=c.exports},"30f7":function(t,e,a){"use strict";a("6a54"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")},a("7a76"),a("c9b5")},4733:function(t,e,a){"use strict";a("6a54"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=function(t){if(Array.isArray(t))return(0,i.default)(t)};var i=function(t){return t&&t.__esModule?t:{default:t}}(a("8d0b"))},"7e7d":function(t,e,a){"use strict";a("6a54");var i=a("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.arrToTree=function t(e,a){var i=[];return e.forEach((function(n){if(n.parentMenuId===a){var r=t(e,n.menuId);r.length&&(n.children=r),i.push(n)}})),i},e.arrToTree2=function(t){var e={},a=[];return t.forEach((function(t){e[t.mid]=(0,n.default)((0,n.default)({},t),{},{children:[]})})),t.forEach((function(t){var i=e[t.parentMid];i?i.children.push(e[t.mid]):a.push(e[t.mid])})),a},e.checkNumberInArray=function(t,e){return t.includes(e)},e.encode=function(t,e){for(var a=parseInt(t),i=[],n=0;n<e.length;++n){var r=e[n];i.push(r.length^a);for(var o=0;o<r.length;++o)i.push(r[o].charCodeAt(0)^a)}i.push(a);var c="CV16"+i.join("%");return c};var n=i(a("9b1b"));a("e966"),a("aa9c"),a("bf0f"),a("2797"),a("4626"),a("5ac7")},"911e":function(t,e,a){"use strict";a("6a54");var i=a("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0,a("aa9c");var n=i(a("b7c7")),r=i(a("9b1b")),o=a("03c2"),c=a("7e7d"),s=(i(a("85b1")),{data:function(){return{searchFrom:{pageSize:10,pageNum:1,sort:0},type:"1",code:"",address:"",total:0,active:null,tableData:[],checkNumberInArray:c.checkNumberInArray,loading:!1}},computed:{loginUser:function(){return this.$store.state.loginUser},menu:function(){return this.$store.state.menu}},methods:{getData:function(){var t=this,e=(0,r.default)({},this.searchFrom);""!=this.code&&(e.extendEventNumber=this.code),this.active&&(e.status=this.active),this.type&&(e.type=this.type),(0,o.findOrder)(e).then((function(e){if(console.log(e),200!=e.data.code)return console.log("请求数据失败"),uni.showToast({title:e.data.msg,icon:"error"});var a;t.loading=!1,t.total=e.data.data.total;var i=e.data.data.list;(a=t.tableData).push.apply(a,(0,n.default)(i))}))},ReTableList:function(){console.log("到底了")},StatustoString:function(t,e){var a="";if(1==e)switch(t){case 0:a="待提交";break;case 1:a="待审核";break;case 2:a="已审核";break;case 3:a="已驳回";break;case 4:a="已完成";break;default:a="";break}else switch(t){case 0:a="待提交";break;case 1:a="待审核";break;case 2:a="已审核";break;case 3:a="已驳回";break;default:a="已完成";break}return a},changeActive:function(t){t?(this.active=t,console.log(this.active)):this.active=null,this.tableData=[],this.getData()},imgsToArr:function(t){var e=t.split(",");return e[0]},applyNameToString:function(t,e){var a=1==e?"申请":2==e?"归还":"报废",i=t||"成员";return i+"提交的"+a+"单"},toInfo:function(t){var e=this;console.log(t),uni.setStorage({key:"orderItem",data:t,success:function(t){uni.navigateTo({url:"../orderInfo/orderInfo?code="+e.code})}})}},onLoad:function(t){console.log(t.code),t.code&&(this.code=t.code),t.type&&(this.type=t.type)},onShow:function(){this.searchFrom.pageNum=1,this.tableData=[],(0,c.checkNumberInArray)(this.menu,"8")?this.getData():uni.showToast({title:"没有查看权限"})},onReachBottom:function(){this.loading?console.log("请等待加载结果"):this.tableData.length<this.total?(this.loading=!0,this.searchFrom.pageNum++,this.getData()):console.log("已经加载全部数据")}});e.default=s},aa9f:function(t,e,a){var i=a("d4c3");i.__esModule&&(i=i.default),"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var n=a("967d").default;n("68903192",i,!0,{sourceMap:!1,shadowMode:!1})},b7c7:function(t,e,a){"use strict";a("6a54"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=function(t){return(0,i.default)(t)||(0,n.default)(t)||(0,r.default)(t)||(0,o.default)()};var i=c(a("4733")),n=c(a("d14d")),r=c(a("5d6b")),o=c(a("30f7"));function c(t){return t&&t.__esModule?t:{default:t}}},d14d:function(t,e,a){"use strict";a("6a54"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=function(t){if("undefined"!==typeof Symbol&&null!=t[Symbol.iterator]||null!=t["@@iterator"])return Array.from(t)},a("01a2"),a("e39c"),a("bf0f"),a("844d"),a("18f7"),a("de6c"),a("08eb")},d4c3:function(t,e,a){var i=a("c86c");e=i(!1),e.push([t.i,".container[data-v-dabda300]{\n\t/* background:linear-gradient(180deg,#689DB1 0%,#F0F0F3 100%); */height:100vh;\n\t/* overflow: scroll; */font-family:alyuan}.view_title[data-v-dabda300]{width:%?706?%;margin:%?22?% auto 0;font-size:%?39?%}.view_name[data-v-dabda300]{width:%?706?%;margin:%?34?% auto;font-size:%?30?%;text-align:center}.item_icon img[data-v-dabda300]{width:%?80?%;height:%?80?%;font-weight:700}.item_label[data-v-dabda300]{padding-left:%?25?%;font-size:%?35?%;line-height:%?80?%;font-weight:700}.activeBox[data-v-dabda300]{margin-top:%?64?%;width:%?579?%;font-size:%?23?%;color:#87735f;font-weight:700;margin:0 auto;display:flex;justify-content:space-between}.active_line[data-v-dabda300]{height:%?5?%;margin-left:25%;width:75%;background:#c80000}.card[data-v-dabda300]{width:%?702?%;margin:%?26?% auto 0;border-radius:%?20?%;background:#fff;box-shadow:%?0?% %?0?% %?10?% rgba(0,0,0,.3);\n\t/* padding: 20rpx; */display:flex}.card_left uni-image[data-v-dabda300]{margin:%?35?%;width:%?142?%;height:%?106?%}.card_right[data-v-dabda300]{width:%?463?%;margin-top:%?35?%;color:#87735f;font-size:%?23?%;margin-right:%?38?%}.card_right .card_info[data-v-dabda300]{margin-top:%?10?%;display:flex;justify-content:space-between;color:#9e9e9e}.card_right .card_info .status[data-v-dabda300]{margin-right:%?20?%}",""]),t.exports=e},d949:function(t,e,a){"use strict";a.d(e,"b",(function(){return i})),a.d(e,"c",(function(){return n})),a.d(e,"a",(function(){}));var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"container"},[a("v-uni-view",{staticClass:"view_title"},[a("v-uni-text",{},[t._v(t._s(1==t.type?"物资调用申请":"物资归还申请"))])],1),a("v-uni-view",{staticClass:"view_name"},[a("v-uni-text",[t._v(t._s(t.code))])],1),a("v-uni-view",{staticClass:"activeBox"},[a("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.changeActive()}}},[t._v("全部"),t.active?t._e():a("v-uni-view",{staticClass:"active_line"})],1),a("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.changeActive(1)}}},[t._v("待审核"),1==t.active?a("v-uni-view",{staticClass:"active_line"}):t._e()],1),a("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.changeActive(2)}}},[t._v("已批准"),2==t.active?a("v-uni-view",{staticClass:"active_line"}):t._e()],1),a("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.changeActive(3)}}},[t._v("已驳回"),3==t.active?a("v-uni-view",{staticClass:"active_line"}):t._e()],1)],1),a("v-uni-scroll-view",{staticClass:"list",attrs:{"scroll-y":!0},on:{scrolltolower:function(e){arguments[0]=e=t.$handleEvent(e),t.ReTableList.apply(void 0,arguments)}}},t._l(t.tableData,(function(e,i){return a("v-uni-view",{key:i,staticClass:"list_item",on:{click:function(a){arguments[0]=a=t.$handleEvent(a),t.toInfo(e)}}},[a("v-uni-view",{staticClass:"card"},[a("v-uni-view",{staticClass:"card_left"},[a("v-uni-image",{attrs:{src:t.imgsToArr(e.urls),mode:"aspectFit"}})],1),a("v-uni-view",{staticClass:"card_right"},[a("v-uni-view",{staticClass:"card_title"},[t._v("物资调用单号："+t._s(e.orderNumber))]),a("v-uni-view",{staticClass:"card_info"},[a("v-uni-view",{staticClass:"user"},[a("v-uni-text",[t._v(t._s(e.applyPhone+" "))]),a("v-uni-text",[t._v(t._s(" "+e.applyName))])],1),a("v-uni-view",{staticClass:"status"},[a("v-uni-text",[t._v(t._s(t.StatustoString(e.status,e.type)))])],1)],1)],1)],1)],1)})),1)],1)},n=[]},eb30:function(t,e,a){"use strict";var i=a("aa9f"),n=a.n(i);n.a},f58e:function(t,e,a){"use strict";a.r(e);var i=a("911e"),n=a.n(i);for(var r in i)["default"].indexOf(r)<0&&function(t){a.d(e,t,(function(){return i[t]}))}(r);e["default"]=n.a}}]);