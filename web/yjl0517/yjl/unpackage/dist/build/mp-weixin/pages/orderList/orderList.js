(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/orderList/orderList"],{"12b5":function(t,e,a){"use strict";(function(t){var r=a("47a9");Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var n=r(a("af34")),o=r(a("7ca3")),c=a("03c2"),i=a("7e7d");r(a("85b1"));function s(t,e){var a=Object.keys(t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(t);e&&(r=r.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),a.push.apply(a,r)}return a}var u={data:function(){return{searchFrom:{pageSize:10,pageNum:1,sort:0},type:"1",code:"",address:"",total:0,active:null,tableData:[],checkNumberInArray:i.checkNumberInArray,loading:!1}},computed:{loginUser:function(){return this.$store.state.loginUser},menu:function(){return this.$store.state.menu}},methods:{getData:function(){var e=this,a=function(t){for(var e=1;e<arguments.length;e++){var a=null!=arguments[e]?arguments[e]:{};e%2?s(Object(a),!0).forEach((function(e){(0,o.default)(t,e,a[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(a)):s(Object(a)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(a,e))}))}return t}({},this.searchFrom);""!=this.code&&(a.extendEventNumber=this.code),this.active&&(a.status=this.active),this.type&&(a.type=this.type),(0,c.findOrder)(a).then((function(a){if(console.log(a),200!=a.data.code)return console.log("请求数据失败"),t.showToast({title:a.data.msg,icon:"error"});var r;e.loading=!1,e.total=a.data.data.total;var o=a.data.data.list;(r=e.tableData).push.apply(r,(0,n.default)(o))}))},ReTableList:function(){console.log("到底了")},StatustoString:function(t,e){var a="";if(1==e)switch(t){case 0:a="待提交";break;case 1:a="待审核";break;case 2:a="已审核";break;case 3:a="已驳回";break;case 4:a="已完成";break;default:a="";break}else switch(t){case 0:a="待提交";break;case 1:a="待审核";break;case 2:a="已审核";break;case 3:a="已驳回";break;default:a="已完成";break}return a},changeActive:function(t){t?(this.active=t,console.log(this.active)):this.active=null,this.tableData=[],this.getData()},imgsToArr:function(t){var e=t.split(",");return e[0]},applyNameToString:function(t,e){var a=1==e?"申请":2==e?"归还":"报废",r=t||"成员";return r+"提交的"+a+"单"},toInfo:function(e){var a=this;console.log(e),t.setStorage({key:"orderItem",data:e,success:function(e){t.navigateTo({url:"../orderInfo/orderInfo?code="+a.code})}})}},onLoad:function(t){console.log(t.code),t.code&&(this.code=t.code),t.type&&(this.type=t.type)},onShow:function(){this.searchFrom.pageNum=1,this.tableData=[],(0,i.checkNumberInArray)(this.menu,"8")?this.getData():t.showToast({title:"没有查看权限"})},onReachBottom:function(){this.loading?console.log("请等待加载结果"):this.tableData.length<this.total?(this.loading=!0,this.searchFrom.pageNum++,this.getData()):console.log("已经加载全部数据")}};e.default=u}).call(this,a("df3c")["default"])},"1c48":function(t,e,a){"use strict";a.r(e);var r=a("ca7b"),n=a("f58e");for(var o in n)["default"].indexOf(o)<0&&function(t){a.d(e,t,(function(){return n[t]}))}(o);a("af8d");var c=a("828b"),i=Object(c["a"])(n["default"],r["b"],r["c"],!1,null,null,null,!1,r["a"],void 0);e["default"]=i.exports},"1d42":function(t,e,a){},"908a":function(t,e,a){"use strict";(function(t,e){var r=a("47a9");a("faef");r(a("3240"));var n=r(a("1c48"));t.__webpack_require_UNI_MP_PLUGIN__=a,e(n.default)}).call(this,a("3223")["default"],a("df3c")["createPage"])},af8d:function(t,e,a){"use strict";var r=a("1d42"),n=a.n(r);n.a},ca7b:function(t,e,a){"use strict";a.d(e,"b",(function(){return r})),a.d(e,"c",(function(){return n})),a.d(e,"a",(function(){}));var r=function(){var t=this,e=t.$createElement,a=(t._self._c,t.__map(t.tableData,(function(e,a){var r=t.__get_orig(e),n=t.imgsToArr(e.urls),o=t.StatustoString(e.status,e.type);return{$orig:r,m0:n,m1:o}})));t.$mp.data=Object.assign({},{$root:{l0:a}})},n=[]},f58e:function(t,e,a){"use strict";a.r(e);var r=a("12b5"),n=a.n(r);for(var o in r)["default"].indexOf(o)<0&&function(t){a.d(e,t,(function(){return r[t]}))}(o);e["default"]=n.a}},[["908a","common/runtime","common/vendor"]]]);