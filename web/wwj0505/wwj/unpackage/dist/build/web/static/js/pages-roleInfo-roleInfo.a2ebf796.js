(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-roleInfo-roleInfo"],{"004f":function(e,t,a){"use strict";a("7a82");var n=a("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("d81d"),a("a9e3"),a("4de4"),a("d3b7"),a("c975");var i=n(a("f3f3")),o=a("664c"),l=a("b85b"),r={data:function(){return{menuList:[],menuList2:[],roleFrom:{roleName:"",description:"",menuIds:[]},roleId:"",msgType:"success",checkNumberInArray:l.checkNumberInArray}},computed:{loginUser:function(){return this.$store.state.loginUser},menu:function(){return this.$store.state.menu}},methods:{getMenu:function(){var e=this;(0,o.getMenuList)({pageNum:1,pageSize:999}).then((function(t){console.log(t.data.data),200==t.data.code&&(e.menuList=(0,l.arrToTree)(t.data.data.list,0),e.menuList2=t.data.data.list,console.log("转化成=>"),console.log(e.menuList))}))},saveUser:function(){console.log(this.roleFrom.menuIds);var e={};e=(0,i.default)({},this.roleFrom);var t=this.roleFrom.menuIds.join(",");e.menuIds=t,(0,o.setRoleInfo)(e).then((function(e){console.log(e),uni.showToast({title:"保存成功"}),uni.navigateBack()}))},delUser:function(e){this.msgType=e,this.$refs.alertDialog.open()},dialogConfirm:function(){console.log("点击确认"),(0,o.delRoleInfo)({ids:this.roleFrom.roleId}).then((function(e){console.log(e),uni.showToast({title:"删除成功"}),uni.navigateBack()}))},dialogClose:function(){console.log("点击关闭")},queryRole:function(){var e=this;(0,o.getRoleList)({pageNum:1,pageSize:20,roleId:this.roleId}).then((function(t){console.log(t.data.data);var a,n,o=(0,i.default)({},t.data.data.list[0]);o.menuIds&&(a=o.menuIds.split(","),n=a.map(Number),n=n.filter((function(e,t,a){return a.indexOf(e)===t})),o.menuIds=n),e.roleFrom=o,console.log(e.roleFrom.menuIds)}))}},onLoad:function(e){this.roleId=e.roleId},onShow:function(){this.getMenu(),this.queryRole()}};t.default=r},"0a77":function(e,t,a){"use strict";var n=a("831d"),i=a.n(n);i.a},"17c0":function(e,t,a){var n=a("24fb");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */*[data-v-6e67917a]{margin:0;padding:0;box-sizing:border-box}.custom-tree-select-content.border[data-v-6e67917a]{border-left:1px solid #c8c7cc}.custom-tree-select-content[data-v-6e67917a] .uni-checkbox-input{margin:0!important}.custom-tree-select-content .item-content[data-v-6e67917a]{margin:0 0 12px;display:flex;justify-content:space-between;align-items:center;position:relative}.custom-tree-select-content .item-content[data-v-6e67917a]::after{content:"";position:absolute;top:0;left:0;bottom:0;width:3px;background-color:#fff;-webkit-transform:translateX(-2px);transform:translateX(-2px);z-index:1}.custom-tree-select-content .item-content .left[data-v-6e67917a]{flex:1;display:flex;align-items:center}.custom-tree-select-content .item-content .left .right-icon[data-v-6e67917a]{transition:.15s ease}.custom-tree-select-content .item-content .left .right-icon.active[data-v-6e67917a]{-webkit-transform:rotate(90deg);transform:rotate(90deg)}.custom-tree-select-content .item-content .left .smallcircle-filled[data-v-6e67917a]{width:14px;height:13.6px;display:flex;align-items:center}.custom-tree-select-content .item-content .left .smallcircle-filled .smallcircle-filled-icon[data-v-6e67917a]{-webkit-transform-origin:center;transform-origin:center;-webkit-transform:scale(.55);transform:scale(.55)}.custom-tree-select-content .item-content .left .loading-icon-box[data-v-6e67917a]{margin-right:5px;width:14px;height:100%;display:flex;justify-content:center;align-items:center}.custom-tree-select-content .item-content .left .loading-icon-box .loading-icon[data-v-6e67917a]{-webkit-transform-origin:center;transform-origin:center;-webkit-animation:rotating-data-v-6e67917a infinite .2s ease;animation:rotating-data-v-6e67917a infinite .2s ease}.custom-tree-select-content .item-content .left .name[data-v-6e67917a]{flex:1}.check-box[data-v-6e67917a]{width:23.6px;height:23.6px;border:1px solid #c8c7cc;border-radius:3px;display:flex;justify-content:center;align-items:center}.check-box.disabled[data-v-6e67917a]{background-color:#e1e1e1}.check-box .part-checked[data-v-6e67917a]{width:60%;height:2px;background-color:#007aff}@-webkit-keyframes rotating-data-v-6e67917a{from{-webkit-transform:rotate(0);transform:rotate(0)}to{-webkit-transform:rotate(1turn);transform:rotate(1turn)}}@keyframes rotating-data-v-6e67917a{from{-webkit-transform:rotate(0);transform:rotate(0)}to{-webkit-transform:rotate(1turn);transform:rotate(1turn)}}',""]),e.exports=t},"1dd8":function(e,t,a){var n=a("24fb");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.uni-popup-dialog[data-v-56681c50]{width:300px;border-radius:11px;background-color:#fff}.uni-dialog-title[data-v-56681c50]{display:flex;flex-direction:row;justify-content:center;padding-top:25px}.uni-dialog-title-text[data-v-56681c50]{font-size:16px;font-weight:500}.uni-dialog-content[data-v-56681c50]{display:flex;flex-direction:row;justify-content:center;align-items:center;padding:20px}.uni-dialog-content-text[data-v-56681c50]{font-size:14px;color:#6c6c6c}.uni-dialog-button-group[data-v-56681c50]{display:flex;flex-direction:row;border-top-color:#f5f5f5;border-top-style:solid;border-top-width:1px}.uni-dialog-button[data-v-56681c50]{display:flex;flex:1;flex-direction:row;justify-content:center;align-items:center;height:45px}.uni-border-left[data-v-56681c50]{border-left-color:#f0f0f0;border-left-style:solid;border-left-width:1px}.uni-dialog-button-text[data-v-56681c50]{font-size:16px;color:#333}.uni-button-color[data-v-56681c50]{color:#007aff}.uni-dialog-input[data-v-56681c50]{flex:1;font-size:14px;border:1px #eee solid;height:40px;padding:0 10px;border-radius:5px;color:#555}.uni-popup__success[data-v-56681c50]{color:#4cd964}.uni-popup__warn[data-v-56681c50]{color:#f0ad4e}.uni-popup__error[data-v-56681c50]{color:#dd524d}.uni-popup__info[data-v-56681c50]{color:#909399}',""]),e.exports=t},2159:function(e,t,a){"use strict";a.r(t);var n=a("e7d9"),i=a("cb15");for(var o in i)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return i[e]}))}(o);a("e08f");var l=a("f0c5"),r=Object(l["a"])(i["default"],n["b"],n["c"],!1,null,"6e67917a",null,!1,n["a"],void 0);t["default"]=r.exports},"23a1":function(e,t,a){"use strict";a.r(t);var n=a("f363"),i=a("604d");for(var o in i)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return i[e]}))}(o);a("8f8b");var l=a("f0c5"),r=Object(l["a"])(i["default"],n["b"],n["c"],!1,null,"6fd75a0a",null,!1,n["a"],void 0);t["default"]=r.exports},2765:function(e,t,a){var n=a("17c0");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var i=a("4f06").default;i("656334e9",n,!0,{sourceMap:!1,shadowMode:!1})},"34aa":function(e,t,a){var n=a("24fb");t=n(!1),t.push([e.i,".container[data-v-9ffd77a6]{\n\t/* background: url() cover; */}.roleFrom[data-v-9ffd77a6]{margin:%?25?%;width:%?660?%;background:#fff;border-radius:%?25?%;box-shadow:%?5?% %?5?% %?5?% #ccc;margin:%?15?% auto;padding:%?20?%;margin-top:%?15?%\n\t/* border: #06BFF7 5rpx solid;\n\tbox-shadow: 0 0 25px rgb(0, 106, 255) inset;\n\tcolor: #fff; */}.BtnGroup[data-v-9ffd77a6]{margin-top:%?35?%}.BtnGroup .btn[data-v-9ffd77a6]{margin-top:%?20?%}.input[data-v-9ffd77a6]{border-radius:%?15?%}.from .btn[data-v-9ffd77a6]{width:%?650?%}",""]),e.exports=t},"36c8":function(e,t,a){"use strict";a("7a82");var n=a("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var i=n(a("fc11")),o=n(a("f3f3")),l=n(a("d0ff"));a("d3b7"),a("6062"),a("3ca3"),a("ddb0"),a("14d9"),a("159b"),a("c975"),a("d401"),a("25f0"),a("a434"),a("99af"),a("caad6"),a("2532"),a("13d5"),a("a630"),a("4de4"),a("d81d"),a("ac1f"),a("1276");var r=a("99ca"),s=n(a("2159")),c=new Set,d={name:"custom-tree-select",components:{dataSelectItem:s.default},model:{prop:"value",event:"input"},props:{canSelectAll:{type:Boolean,default:!1},safeArea:{type:Boolean,default:!0},search:{type:Boolean,default:!1},clearResetSearch:{type:Boolean,default:!1},animation:{type:Boolean,default:!0},"is-mask-click":{type:Boolean,default:!0},"mask-background-color":{type:String,default:"rgba(0,0,0,0.4)"},"background-color":{type:String,default:"none"},"safe-area":{type:Boolean,default:!0},choseParent:{type:Boolean,default:!0},placeholder:{type:String,default:"请选择"},confirmText:{type:String,default:"完成"},confirmTextColor:{type:String,default:"#007aff"},contentHeight:{type:String},disabledList:{type:Array,default:function(){return[]}},listData:{type:Array,default:function(){return[]}},dataLabel:{type:String,default:"name"},dataValue:{type:String,default:"id"},dataChildren:{type:String,default:"children"},linkage:{type:Boolean,default:!1},clearable:{type:Boolean,default:!1},mutiple:{type:Boolean,default:!1},disabled:{type:Boolean,default:!1},showChildren:{type:Boolean,default:!1},border:{type:Boolean,default:!1},pathMode:{type:Boolean,default:!1},pathHyphen:{type:String,default:"/"},load:{type:Function,default:function(){}},lazyLoadChildren:{type:Boolean,default:!1},value:{type:Array,default:function(){return[]}}},data:function(){return{defaultContentHeight:"500px",treeData:[],filterTreeData:[],clearTimerList:[],selectedListBaseinfo:[],showPopup:!1,clickOpenTimer:null,isSelectedAll:!1,scrollTop:0,searchStr:""}},computed:{selectList:function(){return this.value||[]}},watch:{listData:{deep:!0,immediate:!0,handler:function(e){e&&(c.clear(),this.treeData=this.initData(e),this.value&&(this.changeStatus(this.treeData,this.value,!0),this.filterTreeData.length&&this.changeStatus(this.filterTreeData,this.value)),this.showPopup&&(this.resetClearTimerList(),this.renderTree(this.treeData)))}},value:{immediate:!0,handler:function(e){e&&(this.changeStatus(this.treeData,this.value,!0),this.filterTreeData.length&&this.changeStatus(this.filterTreeData,this.value))}}},mounted:function(){this.getContentHeight(uni.getSystemInfoSync())},methods:{goTop:function(){var e=this;this.scrollTop=10,this.$nextTick((function(){e.scrollTop=0}))},getReflectNode:function(e,t){var a=(0,l.default)(t);while(a.length){var n,i=a.shift();if(i[this.dataValue]===e[this.dataValue])return i;null!==(n=i[this.dataChildren])&&void 0!==n&&n.length&&a.push.apply(a,(0,l.default)(i[this.dataChildren]))}return{}},getContentHeight:function(e){var t=e.screenHeight;this.defaultContentHeight="".concat(Math.floor(.7*t),"px")},handleSearch:function(){var e=arguments.length>0&&void 0!==arguments[0]&&arguments[0];this.resetClearTimerList(),e?this.clearResetSearch&&this.renderTree(this.treeData):this.renderTree(this.searchValue(this.searchStr,this.treeData)),this.goTop(),uni.hideKeyboard()},searchValue:function(e,t){var a=this,n=[];return t.forEach((function(t){var l;if(t.visible)if(t[a.dataLabel].toString().toLowerCase().indexOf(e.toLowerCase())>-1)n.push(t);else if(null!==(l=t[a.dataChildren])&&void 0!==l&&l.length){var r,s=a.searchValue(e,t[a.dataChildren]);if(null!==s&&void 0!==s&&s.length)e&&!t.showChildren&&null!==(r=t[a.dataChildren])&&void 0!==r&&r.length&&(t.showChildren=!0),n.push((0,o.default)((0,o.default)({},t),{},(0,i.default)({},a.dataChildren,s)))}})),n},renderTree:function(e){var t,a=(0,r.paging)(e);(t=this.filterTreeData).splice.apply(t,[0,this.filterTreeData.length].concat((0,l.default)((null===a||void 0===a?void 0:a[0])||[]))),this.lazyRenderList(a,1)},lazyRenderList:function(e,t){for(var a=this,n=function(t){var n;n=setTimeout((function(){var n;(n=a.filterTreeData).push.apply(n,(0,l.default)(e[t]))}),500*t),a.clearTimerList.push((function(){return clearTimeout(n)}))},i=t;i<e.length;i++)n(i)},resetClearTimerList:function(){var e=(0,l.default)(this.clearTimerList);this.clearTimerList=[],e.forEach((function(e){return e()}))},open:function(){var e=this;this.disabled||(this.showPopup=!0,this.$nextTick((function(){e.$refs.popup.open(),e.renderTree(e.treeData)})))},close:function(){this.$refs.popup.close()},change:function(e){var t=this;e.show?(uni.$on("custom-tree-select-node-click",this.handleNodeClick),uni.$on("custom-tree-select-name-click",this.handleHideChildren),uni.$on("custom-tree-select-load",this.handleLoadNode)):(uni.$off("custom-tree-select-node-click",this.handleNodeClick),uni.$off("custom-tree-select-name-click",this.handleHideChildren),uni.$off("custom-tree-select-load",this.handleLoadNode),this.resetClearTimerList(),this.searchStr="",this.animation?setTimeout((function(){t.showPopup=!1}),200):this.showPopup=!1),this.$emit("change",e)},maskClick:function(){this.$emit("maskClick")},initData:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:void 0,a=arguments.length>2&&void 0!==arguments[2]?arguments[2]:[];if(!Array.isArray(e))return[];for(var n=[],r=0;r<e.length;r++){var s,d,u=[].concat((0,l.default)(a),[e[r][this.dataLabel]]),f=(0,o.default)((0,o.default)({},e[r]),{},(s={},(0,i.default)(s,this.dataLabel,e[r][this.dataLabel]),(0,i.default)(s,this.dataValue,e[r][this.dataValue]),(0,i.default)(s,"path",u.join(this.pathHyphen)),s));f.checked=this.selectList.includes(e[r][this.dataValue]),f.disabled=!1,(Boolean(e[r].disabled)||this.disabledList.includes(f[this.dataValue]))&&(f.disabled=!0),f.partChecked=Boolean(void 0!==e[r].partChecked&&e[r].partChecked),f.partChecked&&f.partCheckedSet.add(f[this.dataValue]),!f.partChecked&&(this.isSelectedAll=!1);var h=void 0===t||t,p=void 0===e[r].visible||Boolean(e[r].visible);if(f.visible=h===p?h:!(!h||!p),f.showChildren="showChildren"in e[r]&&void 0!=e[r].showChildren?e[r].showChildren:this.showChildren,null!==(d=e[r][this.dataChildren])&&void 0!==d&&d.length){var v=this.initData(e[r][this.dataChildren],f.visible,u);f[this.dataChildren]=v,!f.checked&&v.some((function(e){return e.checked||e.partChecked}))&&(f.partChecked=!0,c.add(f[this.dataValue]))}n.push(f)}return n},getChildren:function(e){var t;if(null===(t=e[this.dataChildren])||void 0===t||!t.length)return[];for(var a=e[this.dataChildren].reduce((function(e,t){return t.visible?[].concat((0,l.default)(e),[t]):e}),[]),n=0;n<e[this.dataChildren].length;n++)a.push.apply(a,(0,l.default)(this.getChildren(e[this.dataChildren][n])));return a},getParentNode:function(e,t){for(var a=[],n=0;n<t.length;n++){var i;if(t[n][this.dataValue]===e[this.dataValue])return!0;if(null!==(i=t[n][this.dataChildren])&&void 0!==i&&i.length){var o=this.getParentNode(e,t[n][this.dataChildren]);"boolean"===typeof o&&o?a=[t[n]]:Array.isArray(o)&&o.length&&(a=[].concat((0,l.default)(o),[t[n]]))}}return a},handleNodeClick:function(e,t){var a=this,n=this.getReflectNode(e,this.treeData);if(n.checked="boolean"===typeof t?t:!n.checked,n.partChecked=!1,c.delete(n[this.dataValue]),this.mutiple)if(this.linkage){var i=(0,l.default)(this.selectList),o=this.getParentNode(n,this.treeData),r=this.getChildren(n).filter((function(e){return!e.disabled}));if(n.checked){if(i=Array.from(new Set([].concat((0,l.default)(i),[n[this.dataValue]]))),r.length&&(i=Array.from(new Set([].concat((0,l.default)(i),(0,l.default)(r.map((function(e){return e[a.dataValue]})))))),r.forEach((function(e){e.partChecked=!1,c.delete(e[a.dataValue])}))),o.length){var s=!1;while(o.length){var d=o.shift();if(!d.disabled)if(s)d.partChecked=!0,c.add(d[this.dataValue]);else{var u=d[this.dataChildren].filter((function(e){return e.visible&&!e.disabled})).every((function(e){return e.checked}));u?(d.checked=!0,d.partChecked=!1,c.delete(d[this.dataValue]),i=Array.from(new Set([].concat((0,l.default)(i),[d[this.dataValue]])))):(d.partChecked=!0,c.add(d[this.dataValue]),s=!0)}}}}else i=i.filter((function(e){return e!==n[a.dataValue]})),r.length&&r.forEach((function(e){i=i.filter((function(t){return t!==e[a.dataValue]}))})),o.length&&o.forEach((function(e){i.includes(e[a.dataValue])&&(e.checked=!1),i=i.filter((function(t){return t!==e[a.dataValue]}));var t=e[a.dataChildren].filter((function(e){return e.visible&&!e.disabled})).some((function(e){return e.checked||e.partChecked}));e.partChecked=t,t?c.add(e[a.dataValue]):c.delete(e[a.dataValue])}));this.$emit("input",i)}else{var f=null;f=n.checked?Array.from(new Set([].concat((0,l.default)(this.selectList),[n[this.dataValue]]))):this.selectList.filter((function(e){return e!==n[a.dataValue]})),this.$emit("input",f)}else{var h=[];n.checked&&(h=[n[this.dataValue]]),this.$emit("input",h)}},handleHideChildren:function(e){var t=!e.showChildren;this.getReflectNode(e,this.treeData).showChildren=t,this.getReflectNode(e,this.filterTreeData).showChildren=t},changeStatus:function(e,t){var a=arguments.length>2&&void 0!==arguments[2]&&arguments[2],n=(0,l.default)(e),i=!0;a&&(this.selectedListBaseinfo=[]);while(n.length){var o,r=n.shift();t.includes(r[this.dataValue])?(this.$set(r,"checked",!0),a&&this.selectedListBaseinfo.push(r),r.partChecked=!1,c.delete(r[this.dataValue])):(this.$set(r,"checked",!1),r.visible&&!r.disabled&&(i=!1),c.has(r[this.dataValue])?this.$set(r,"partChecked",!0):this.$set(r,"partChecked",!1)),null!==(o=r[this.dataChildren])&&void 0!==o&&o.length&&n.push.apply(n,(0,l.default)(r[this.dataChildren]))}this.isSelectedAll=i,a&&this.$emit("selectChange",(0,l.default)(this.selectedListBaseinfo))},removeSelectedItem:function(e){var t=this;if(this.isSelectedAll=!1,this.linkage)this.handleNodeClick(e,!1),this.$emit("removeSelect",e);else{var a=this.selectList.filter((function(a){return a!==e[t.dataValue]}));this.$emit("removeSelect",e),this.$emit("input",a)}},handleSelectAll:function(){var e=this;if(this.isSelectedAll=!this.isSelectedAll,this.isSelectedAll){if(!this.mutiple)return void uni.showToast({title:"单选模式下不能全选",icon:"none",duration:1e3});var t=[];this.treeData.forEach((function(a){var n;(a.visible||a.disabled&&a.checked)&&(t=Array.from(new Set([].concat((0,l.default)(t),[a[e.dataValue]]))),null!==(n=a[e.dataChildren])&&void 0!==n&&n.length&&(t=Array.from(new Set([].concat((0,l.default)(t),(0,l.default)(e.getChildren(a).filter((function(e){return!e.disabled||e.disabled&&e.checked})).map((function(t){return t[e.dataValue]}))))))))})),this.$emit("input",t)}else this.clear()},clear:function(){var e=this;if(!this.disabled){var t=[];c.clear(),this.selectedListBaseinfo.forEach((function(a){a.visible&&a.checked&&a.disabled&&t.push(a[e.dataValue])})),this.$emit("input",t)}},handleLoadNode:function(e){var t=e.source,a=e.target;this.$set(t,this.dataChildren,this.initData(a,t.visible,t.path.split(this.pathHyphen))),this.handleHideChildren(t)}}};t.default=d},"376b":function(e,t,a){var n=a("1dd8");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var i=a("4f06").default;i("08e980a8",n,!0,{sourceMap:!1,shadowMode:!1})},"4b31":function(e,t,a){"use strict";a.r(t);var n=a("004f"),i=a.n(n);for(var o in n)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return n[e]}))}(o);t["default"]=i.a},"604d":function(e,t,a){"use strict";a.r(t);var n=a("36c8"),i=a.n(n);for(var o in n)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return n[e]}))}(o);t["default"]=i.a},"681e":function(e,t,a){var n=a("e102");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var i=a("4f06").default;i("55279e57",n,!0,{sourceMap:!1,shadowMode:!1})},"705f":function(e,t,a){"use strict";a.r(t);var n=a("c195"),i=a.n(n);for(var o in n)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return n[e]}))}(o);t["default"]=i.a},"7b83":function(e){e.exports=JSON.parse('{"uni-popup.cancel":"取消","uni-popup.ok":"确定","uni-popup.placeholder":"请输入","uni-popup.title":"提示","uni-popup.shareTitle":"分享到"}')},8092:function(e){e.exports=JSON.parse('{"uni-popup.cancel":"取消","uni-popup.ok":"確定","uni-popup.placeholder":"請輸入","uni-popup.title":"提示","uni-popup.shareTitle":"分享到"}')},"831d":function(e,t,a){var n=a("34aa");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var i=a("4f06").default;i("560eca44",n,!0,{sourceMap:!1,shadowMode:!1})},8369:function(e,t,a){"use strict";a.r(t);var n=a("8d79"),i=a("4b31");for(var o in i)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return i[e]}))}(o);a("0a77");var l=a("f0c5"),r=Object(l["a"])(i["default"],n["b"],n["c"],!1,null,"9ffd77a6",null,!1,n["a"],void 0);t["default"]=r.exports},"88a8":function(e,t,a){"use strict";a.d(t,"b",(function(){return n})),a.d(t,"c",(function(){return i})),a.d(t,"a",(function(){}));var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-uni-view",{staticClass:"uni-popup-dialog"},[a("v-uni-view",{staticClass:"uni-dialog-title"},[a("v-uni-text",{staticClass:"uni-dialog-title-text",class:["uni-popup__"+e.dialogType]},[e._v(e._s(e.titleText))])],1),"base"===e.mode?a("v-uni-view",{staticClass:"uni-dialog-content"},[e._t("default",[a("v-uni-text",{staticClass:"uni-dialog-content-text"},[e._v(e._s(e.content))])])],2):a("v-uni-view",{staticClass:"uni-dialog-content"},[e._t("default",["checkbox"===e.inputType?a("v-uni-input",{staticClass:"uni-dialog-input",attrs:{placeholder:e.placeholderText,focus:e.focus,type:"checkbox"},model:{value:e.val,callback:function(t){e.val=t},expression:"val"}}):"radio"===e.inputType?a("input",{directives:[{name:"model",rawName:"v-model",value:e.val,expression:"val"}],staticClass:"uni-dialog-input",attrs:{placeholder:e.placeholderText,focus:e.focus,type:"radio"},domProps:{checked:e._q(e.val,null)},on:{change:function(t){e.val=null}}}):a("input",{directives:[{name:"model",rawName:"v-model",value:e.val,expression:"val"}],staticClass:"uni-dialog-input",attrs:{placeholder:e.placeholderText,focus:e.focus,type:e.inputType},domProps:{value:e.val},on:{input:function(t){t.target.composing||(e.val=t.target.value)}}})])],2),a("v-uni-view",{staticClass:"uni-dialog-button-group"},[a("v-uni-view",{staticClass:"uni-dialog-button",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.closeDialog.apply(void 0,arguments)}}},[a("v-uni-text",{staticClass:"uni-dialog-button-text"},[e._v(e._s(e.closeText))])],1),a("v-uni-view",{staticClass:"uni-dialog-button uni-border-left",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.onOk.apply(void 0,arguments)}}},[a("v-uni-text",{staticClass:"uni-dialog-button-text uni-button-color"},[e._v(e._s(e.okText))])],1)],1)],1)},i=[]},"8d79":function(e,t,a){"use strict";a.d(t,"b",(function(){return i})),a.d(t,"c",(function(){return o})),a.d(t,"a",(function(){return n}));var n={uniForms:a("6e2c").default,uniFormsItem:a("68d4").default,uniEasyinput:a("7b0c").default,customTreeSelect:a("23a1").default,uniPopup:a("9ed3").default,uniPopupDialog:a("fae9").default},i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-uni-view",{staticClass:"container"},[a("v-uni-view",{staticClass:"roleFrom"},[a("uni-forms",{ref:"roleFrom",attrs:{modelValue:e.roleFrom}},[a("uni-forms-item",{attrs:{label:"身份名称",name:"roleName","label-width":"250"}},[a("uni-easyinput",{staticClass:"input",attrs:{placeholder:"请输入身份名称"},model:{value:e.roleFrom.roleName,callback:function(t){e.$set(e.roleFrom,"roleName",t)},expression:"roleFrom.roleName"}})],1),a("uni-forms-item",{attrs:{label:"身份描述",name:"description","label-width":"250"}},[a("uni-easyinput",{staticClass:"input",attrs:{type:"textarea",autoHeight:!0,placeholder:"请输入身份描述"},model:{value:e.roleFrom.description,callback:function(t){e.$set(e.roleFrom,"description",t)},expression:"roleFrom.description"}})],1),a("uni-forms-item",{attrs:{label:"身份权限",name:"roleName","label-width":"250"}},[a("custom-tree-select",{staticClass:"input",attrs:{listData:e.menuList,dataLabel:"menuName",dataValue:"menuId",dataChildren:"children",mutiple:!0,linkage:!0},model:{value:e.roleFrom.menuIds,callback:function(t){e.$set(e.roleFrom,"menuIds",t)},expression:"roleFrom.menuIds"}})],1)],1),a("v-uni-view",{staticClass:"BtnGroup"},[e.checkNumberInArray(e.menu,"28")?a("v-uni-button",{staticClass:"btn",attrs:{type:"primary"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.saveUser()}}},[e._v("保存修改")]):e._e(),e.checkNumberInArray(e.menu,"29")?a("v-uni-button",{staticClass:"btn",attrs:{type:"error"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.delUser("warn")}}},[e._v("删除角色")]):e._e()],1),a("uni-popup",{ref:"alertDialog",attrs:{type:"dialog"}},[a("uni-popup-dialog",{attrs:{type:e.msgType,cancelText:"取消",confirmText:"删除",title:"通知",content:"确认删除角色吗?"},on:{confirm:function(t){arguments[0]=t=e.$handleEvent(t),e.dialogConfirm.apply(void 0,arguments)},close:function(t){arguments[0]=t=e.$handleEvent(t),e.dialogClose.apply(void 0,arguments)}}})],1)],1)],1)},o=[]},"8f8b":function(e,t,a){"use strict";var n=a("681e"),i=a.n(n);i.a},"99ca":function(e,t,a){"use strict";a("7a82"),Object.defineProperty(t,"__esModule",{value:!0}),t.isString=function(e){return"string"===typeof e},t.paging=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50;if(!Array.isArray(e)||!e.length)return e;var a=[];return e.forEach((function(e,n){var i=Math.floor(n/t);a[i]||(a[i]=[]),a[i].push(e)})),a},a("d3b7"),a("159b"),a("14d9")},bc1e:function(e,t,a){"use strict";var n=a("376b"),i=a.n(n);i.a},c195:function(e,t,a){"use strict";a("7a82");var n=a("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("a9e3");var i=n(a("f02b")),o=a("37dc"),l=n(a("fd7e")),r=(0,o.initVueI18n)(l.default),s=r.t,c={name:"uniPopupDialog",mixins:[i.default],emits:["confirm","close"],props:{inputType:{type:String,default:"text"},value:{type:[String,Number],default:""},placeholder:{type:[String,Number],default:""},type:{type:String,default:"error"},mode:{type:String,default:"base"},title:{type:String,default:""},content:{type:String,default:""},beforeClose:{type:Boolean,default:!1},cancelText:{type:String,default:""},confirmText:{type:String,default:""}},data:function(){return{dialogType:"error",focus:!1,val:""}},computed:{okText:function(){return this.confirmText||s("uni-popup.ok")},closeText:function(){return this.cancelText||s("uni-popup.cancel")},placeholderText:function(){return this.placeholder||s("uni-popup.placeholder")},titleText:function(){return this.title||s("uni-popup.title")}},watch:{type:function(e){this.dialogType=e},mode:function(e){"input"===e&&(this.dialogType="info")},value:function(e){this.val=e}},created:function(){this.popup.disableMask(),"input"===this.mode?(this.dialogType="info",this.val=this.value):this.dialogType=this.type},mounted:function(){this.focus=!0},methods:{onOk:function(){"input"===this.mode?this.$emit("confirm",this.val):this.$emit("confirm"),this.beforeClose||this.popup.close()},closeDialog:function(){this.$emit("close"),this.beforeClose||this.popup.close()},close:function(){this.popup.close()}}};t.default=c},cb15:function(e,t,a){"use strict";a.r(t);var n=a("f425"),i=a.n(n);for(var o in n)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return n[e]}))}(o);t["default"]=i.a},e08f:function(e,t,a){"use strict";var n=a("2765"),i=a.n(n);i.a},e102:function(e,t,a){var n=a("24fb");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.custom-tree-select-content .select-list[data-v-6fd75a0a]{padding-left:10px;min-height:35px;border:1px solid #e5e5e5;border-radius:3px;display:flex;justify-content:space-between;align-items:center}.custom-tree-select-content .select-list.active[data-v-6fd75a0a]{padding:calc(4px / 2) 0 calc(4px / 2) 10px}.custom-tree-select-content .select-list .left[data-v-6fd75a0a]{flex:1}.custom-tree-select-content .select-list .left .select-items[data-v-6fd75a0a]{display:flex;flex-wrap:wrap}.custom-tree-select-content .select-list .left .select-item[data-v-6fd75a0a]{margin:4px 10px 4px 0;padding:4px 5px;max-width:auto;height:auto;background-color:#eaeaea;border-radius:3px;color:#333;display:flex;align-items:center}.custom-tree-select-content .select-list .left .select-item .name[data-v-6fd75a0a]{flex:1;padding-right:10px;font-size:14px}.custom-tree-select-content .select-list .left .select-item .close[data-v-6fd75a0a]{width:18px;height:18px;display:flex;justify-content:center;align-items:center;overflow:hidden}.custom-tree-select-content .select-list .right[data-v-6fd75a0a]{margin-right:5px;display:flex;justify-content:flex-end;align-items:center}.custom-tree-select-content .select-list.disabled[data-v-6fd75a0a]{background-color:#f5f7fa}.custom-tree-select-content .select-list.disabled .left .select-item .name[data-v-6fd75a0a]{padding:0}.custom-tree-select-content .popup-content[data-v-6fd75a0a]{flex:1;background-color:#fff;border-top-left-radius:20px;border-top-right-radius:20px;display:flex;flex-direction:column}.custom-tree-select-content .popup-content .title[data-v-6fd75a0a]{padding:8px 3rem;border-bottom:1px solid #c8c7cc;font-size:14px;display:flex;justify-content:space-between;position:relative}.custom-tree-select-content .popup-content .title .left[data-v-6fd75a0a]{position:absolute;left:10px}.custom-tree-select-content .popup-content .title .center[data-v-6fd75a0a]{flex:1;text-align:center}.custom-tree-select-content .popup-content .title .right[data-v-6fd75a0a]{position:absolute;right:10px}.custom-tree-select-content .popup-content .search-box[data-v-6fd75a0a]{margin:8px 10px 0;background-color:#fff;display:flex;align-items:center}.custom-tree-select-content .popup-content .search-box .search-btn[data-v-6fd75a0a]{margin-left:10px;height:35px;line-height:35px}.custom-tree-select-content .popup-content .select-content[data-v-6fd75a0a]{margin:8px 10px;flex:1;overflow:hidden;position:relative}.custom-tree-select-content .popup-content .scroll-view-box[data-v-6fd75a0a]{touch-action:none;flex:1;position:absolute;top:0;right:0;bottom:0;left:0}.custom-tree-select-content .popup-content .sentry[data-v-6fd75a0a]{height:48px}.custom-tree-select-content .no-data[data-v-6fd75a0a]{width:auto;color:#999;font-size:12px}.custom-tree-select-content .no-data.center[data-v-6fd75a0a]{text-align:center}',""]),e.exports=t},e7d9:function(e,t,a){"use strict";a.d(t,"b",(function(){return i})),a.d(t,"c",(function(){return o})),a.d(t,"a",(function(){return n}));var n={uniIcons:a("ae53").default},i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-uni-view",{staticClass:"custom-tree-select-content",class:{border:e.border&&e.node[e.dataChildren]&&e.node[e.dataChildren].length&&e.node.showChildren},style:{marginLeft:(e.level?14:0)+"px"}},[e.node.visible?a("v-uni-view",{staticClass:"custom-tree-select-item"},[a("v-uni-view",{staticClass:"item-content"},[a("v-uni-view",{staticClass:"left"},[e.node[e.dataChildren]&&e.node[e.dataChildren].length?a("v-uni-view",{class:["right-icon",{active:e.node.showChildren}],on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.nameClick(e.node)}}},[a("uni-icons",{attrs:{type:"forward",size:"14",color:"#333"}})],1):a("v-uni-view",{staticClass:"smallcircle-filled"},[a("uni-icons",{staticClass:"smallcircle-filled-icon",attrs:{type:"smallcircle-filled",size:"10",color:"#333"}})],1),e.loadingArr.includes(e.node[e.dataValue])?a("v-uni-view",{staticClass:"loading-icon-box"},[a("uni-icons",{staticClass:"loading-icon",attrs:{type:"spinner-cycle",size:"14",color:"#333"}})],1):e._e(),a("v-uni-view",{staticClass:"name",style:e.node.disabled?"color: #999":"",on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.nameClick(e.node)}}},[a("v-uni-text",[e._v(e._s(e.node[e.dataLabel]))])],1)],1),e.choseParent||!e.choseParent&&!e.node[e.dataChildren]||!e.choseParent&&e.node[e.dataChildren]&&!e.node[e.dataChildren].length?a("v-uni-view",{class:["check-box",{disabled:e.node.disabled}],on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.nodeClick(e.node)}}},[!e.node.checked&&e.node.partChecked&&e.linkage?a("v-uni-view",{staticClass:"part-checked"}):e._e(),e.node.checked?a("uni-icons",{attrs:{type:"checkmarkempty",size:"18",color:e.node.disabled?"#333":"#007aff"}}):e._e()],1):e._e()],1)],1):e._e(),e.node.showChildren&&e.node[e.dataChildren]&&e.node[e.dataChildren].length?a("v-uni-view",e._l(e.listData,(function(t){return a("data-select-item",{key:t[e.dataValue],attrs:{node:t,dataLabel:e.dataLabel,dataValue:e.dataValue,dataChildren:e.dataChildren,choseParent:e.choseParent,border:e.border,linkage:e.linkage,level:e.level+1,load:e.load,lazyLoadChildren:e.lazyLoadChildren}})})),1):e._e()],1)},o=[]},f02b:function(e,t,a){"use strict";a("7a82"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n={data:function(){return{}},created:function(){this.popup=this.getParent()},methods:{getParent:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"uniPopup",t=this.$parent,a=t.$options.name;while(a!==e){if(t=t.$parent,!t)return!1;a=t.$options.name}return t}}};t.default=n},f363:function(e,t,a){"use strict";a.d(t,"b",(function(){return i})),a.d(t,"c",(function(){return o})),a.d(t,"a",(function(){return n}));var n={uniIcons:a("ae53").default,uniPopup:a("9ed3").default,uniEasyinput:a("7b0c").default},i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-uni-view",{staticClass:"custom-tree-select-content"},[a("v-uni-view",{class:["select-list",{disabled:e.disabled},{active:e.selectList.length}],on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.open.apply(void 0,arguments)}}},[a("v-uni-view",{staticClass:"left"},[e.selectList.length?a("v-uni-view",{staticClass:"select-items"},e._l(e.selectedListBaseinfo,(function(t){return a("v-uni-view",{key:t[e.dataValue],staticClass:"select-item"},[a("v-uni-view",{staticClass:"name"},[a("v-uni-text",[e._v(e._s(e.pathMode?t.path:t[e.dataLabel]))])],1),e.disabled||t.disabled?e._e():a("v-uni-view",{staticClass:"close",on:{click:function(a){a.stopPropagation(),arguments[0]=a=e.$handleEvent(a),e.removeSelectedItem(t)}}},[a("uni-icons",{attrs:{type:"closeempty",size:"16",color:"#999"}})],1)],1)})),1):a("v-uni-view",{staticClass:"no-data",staticStyle:{color:"#6a6a6a"}},[a("v-uni-text",[e._v(e._s(e.placeholder))])],1)],1),a("v-uni-view",{staticClass:"right"},[e.selectList.length&&e.clearable?e._e():a("uni-icons",{attrs:{type:"bottom",size:"14",color:"#999"}}),e.selectList.length&&e.clearable?a("uni-icons",{attrs:{type:"clear",size:"24",color:"#c0c4cc"},nativeOn:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.clear.apply(void 0,arguments)}}}):e._e()],1)],1),e.showPopup?a("uni-popup",{ref:"popup",attrs:{animation:e.animation,"is-mask-click":e.isMaskClick,"mask-background-color":e.maskBackgroundColor,"background-color":e.backgroundColor,"safe-area":e.safeArea,type:"bottom"},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.change.apply(void 0,arguments)},maskClick:function(t){arguments[0]=t=e.$handleEvent(t),e.maskClick.apply(void 0,arguments)}}},[a("v-uni-view",{staticClass:"popup-content",style:{height:e.contentHeight||e.defaultContentHeight}},[a("v-uni-view",{staticClass:"title"},[e.mutiple&&e.canSelectAll?a("v-uni-view",{staticClass:"left",on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.handleSelectAll.apply(void 0,arguments)}}},[a("v-uni-text",[e._v(e._s(e.isSelectedAll?"取消全选":"全选"))])],1):e._e(),a("v-uni-view",{staticClass:"center"},[a("v-uni-text",[e._v(e._s(e.placeholder))])],1),a("v-uni-view",{staticClass:"right",style:{color:e.confirmTextColor},on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.close.apply(void 0,arguments)}}},[a("v-uni-text",[e._v(e._s(e.confirmText))])],1)],1),e.search?a("v-uni-view",{staticClass:"search-box"},[a("uni-easyinput",{attrs:{maxlength:-1,prefixIcon:"search",placeholder:"搜索","confirm-type":"search"},on:{confirm:function(t){arguments[0]=t=e.$handleEvent(t),e.handleSearch(!1)},clear:function(t){arguments[0]=t=e.$handleEvent(t),e.handleSearch(!0)}},model:{value:e.searchStr,callback:function(t){e.searchStr=t},expression:"searchStr"}}),a("v-uni-button",{staticClass:"search-btn",attrs:{type:"primary",size:"mini"},on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.handleSearch(!1)}}},[e._v("搜索")])],1):e._e(),e.treeData.length?a("v-uni-view",{staticClass:"select-content"},[a("v-uni-scroll-view",{staticClass:"scroll-view-box",attrs:{"scroll-top":e.scrollTop,"scroll-y":"true"},on:{touchmove:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t)}}},[e.filterTreeData.length?e._e():a("v-uni-view",{staticClass:"no-data center"},[a("v-uni-text",[e._v("暂无数据")])],1),e._l(e.filterTreeData,(function(t){return a("data-select-item",{key:t[e.dataValue],attrs:{node:t,dataLabel:e.dataLabel,dataValue:e.dataValue,dataChildren:e.dataChildren,choseParent:e.choseParent,border:e.border,linkage:e.linkage,load:e.load,lazyLoadChildren:e.lazyLoadChildren}})})),a("v-uni-view",{staticClass:"sentry"})],2)],1):a("v-uni-view",{staticClass:"no-data center"},[a("v-uni-text",[e._v("暂无数据")])],1)],1)],1):e._e()],1)},o=[]},f425:function(e,t,a){"use strict";a("7a82");var n=a("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var i=n(a("f07e")),o=n(a("c964")),l=n(a("d0ff"));a("a9e3"),a("a434"),a("99af"),a("14d9"),a("d3b7"),a("159b");var r=n(a("2159")),s=a("99ca"),c={name:"data-select-item",components:{"data-select-item":r.default},props:{node:{type:Object,default:function(){return{}}},choseParent:{type:Boolean,default:!0},dataLabel:{type:String,default:"name"},dataValue:{type:String,default:"value"},dataChildren:{type:String,default:"children"},border:{type:Boolean,default:!1},linkage:{type:Boolean,default:!1},level:{type:Number,default:0},load:{type:Function,default:function(){}},lazyLoadChildren:{type:Boolean,default:!1}},data:function(){return{listData:[],clearTimerList:[],loadingArr:[]}},computed:{watchData:function(){var e=this.node,t=this.dataChildren;return{node:e,dataChildren:t}}},watch:{watchData:{immediate:!0,handler:function(e){var t=e.node,a=e.dataChildren;t.showChildren&&t[a]&&t[a].length&&(this.resetClearTimerList(),this.renderTree(t[a]))}}},methods:{renderTree:function(e){var t,a=(0,s.paging)(e);(t=this.listData).splice.apply(t,[0,this.listData.length].concat((0,l.default)((null===a||void 0===a?void 0:a[0])||[]))),this.lazyRenderList(a,1)},lazyRenderList:function(e,t){for(var a=this,n=function(t){var n;n=setTimeout((function(){var n;(n=a.listData).push.apply(n,(0,l.default)(e[t]))}),500*t),a.clearTimerList.push((function(){return clearTimeout(n)}))},i=t;i<e.length;i++)n(i)},resetClearTimerList:function(){var e=(0,l.default)(this.clearTimerList);this.clearTimerList.splice(0,this.clearTimerList.length),e.forEach((function(e){return e()}))},nameClick:function(e){var t=this;return(0,o.default)((0,i.default)().mark((function a(){var n,o;return(0,i.default)().wrap((function(a){while(1)switch(a.prev=a.next){case 0:if(null!==(n=e[t.dataChildren])&&void 0!==n&&n.length||!t.lazyLoadChildren){a.next=12;break}return t.loadingArr.push(e[t.dataValue]),a.prev=2,a.next=5,t.load(e);case 5:o=a.sent,Array.isArray(o)&&uni.$emit("custom-tree-select-load",{source:e,target:o});case 7:return a.prev=7,t.loadingArr=[],a.finish(7);case 10:a.next=14;break;case 12:!e.showChildren&&e[t.dataChildren]&&e[t.dataChildren].length?t.renderTree(e[t.dataChildren]):(t.resetClearTimerList(),t.listData.splice(0,t.listData.length)),uni.$emit("custom-tree-select-name-click",e);case 14:case"end":return a.stop()}}),a,null,[[2,,7,10]])})))()},nodeClick:function(e){e.disabled||uni.$emit("custom-tree-select-node-click",e)}},options:{styleIsolation:"shared"}};t.default=c},fae9:function(e,t,a){"use strict";a.r(t);var n=a("88a8"),i=a("705f");for(var o in i)["default"].indexOf(o)<0&&function(e){a.d(t,e,(function(){return i[e]}))}(o);a("bc1e");var l=a("f0c5"),r=Object(l["a"])(i["default"],n["b"],n["c"],!1,null,"56681c50",null,!1,n["a"],void 0);t["default"]=r.exports},fd7e:function(e,t,a){"use strict";a("7a82");var n=a("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var i=n(a("ff93")),o=n(a("7b83")),l=n(a("8092")),r={en:i.default,"zh-Hans":o.default,"zh-Hant":l.default};t.default=r},ff93:function(e){e.exports=JSON.parse('{"uni-popup.cancel":"cancel","uni-popup.ok":"ok","uni-popup.placeholder":"pleace enter","uni-popup.title":"Hint","uni-popup.shareTitle":"Share to"}')}}]);