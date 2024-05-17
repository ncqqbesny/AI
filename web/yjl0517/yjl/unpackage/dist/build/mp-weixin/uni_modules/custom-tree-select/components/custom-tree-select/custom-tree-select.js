(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["uni_modules/custom-tree-select/components/custom-tree-select/custom-tree-select"],{"18b2":function(e,t,a){"use strict";(function(e){var i=a("47a9");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=i(a("7ca3")),l=i(a("af34")),r=a("9f8b");function s(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);t&&(i=i.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,i)}return a}function d(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?s(Object(a),!0).forEach((function(t){(0,n.default)(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):s(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}var o=new Set,h={name:"custom-tree-select",components:{dataSelectItem:function(){Promise.all([a.e("common/vendor"),a.e("uni_modules/custom-tree-select/components/custom-tree-select/data-select-item")]).then(function(){return resolve(a("4b60"))}.bind(null,a)).catch(a.oe)}},model:{prop:"value",event:"input"},props:{canSelectAll:{type:Boolean,default:!1},safeArea:{type:Boolean,default:!0},search:{type:Boolean,default:!1},clearResetSearch:{type:Boolean,default:!1},animation:{type:Boolean,default:!0},"is-mask-click":{type:Boolean,default:!0},"mask-background-color":{type:String,default:"rgba(0,0,0,0.4)"},"background-color":{type:String,default:"none"},"safe-area":{type:Boolean,default:!0},choseParent:{type:Boolean,default:!0},placeholder:{type:String,default:"请选择"},confirmText:{type:String,default:"完成"},confirmTextColor:{type:String,default:"#007aff"},contentHeight:{type:String},disabledList:{type:Array,default:function(){return[]}},listData:{type:Array,default:function(){return[]}},dataLabel:{type:String,default:"name"},dataValue:{type:String,default:"id"},dataChildren:{type:String,default:"children"},linkage:{type:Boolean,default:!1},clearable:{type:Boolean,default:!1},mutiple:{type:Boolean,default:!1},disabled:{type:Boolean,default:!1},showChildren:{type:Boolean,default:!1},border:{type:Boolean,default:!1},pathMode:{type:Boolean,default:!1},pathHyphen:{type:String,default:"/"},load:{type:Function,default:function(){}},lazyLoadChildren:{type:Boolean,default:!1},value:{type:Array,default:function(){return[]}}},data:function(){return{defaultContentHeight:"500px",treeData:[],filterTreeData:[],clearTimerList:[],selectedListBaseinfo:[],showPopup:!1,clickOpenTimer:null,isSelectedAll:!1,scrollTop:0,searchStr:""}},computed:{selectList:function(){return this.value||[]}},watch:{listData:{deep:!0,immediate:!0,handler:function(e){e&&(o.clear(),this.treeData=this.initData(e),this.value&&(this.changeStatus(this.treeData,this.value,!0),this.filterTreeData.length&&this.changeStatus(this.filterTreeData,this.value)),this.showPopup&&(this.resetClearTimerList(),this.renderTree(this.treeData)))}},value:{immediate:!0,handler:function(e){e&&(this.changeStatus(this.treeData,this.value,!0),this.filterTreeData.length&&this.changeStatus(this.filterTreeData,this.value))}}},mounted:function(){this.getContentHeight(e.getSystemInfoSync())},methods:{goTop:function(){var e=this;this.scrollTop=10,this.$nextTick((function(){e.scrollTop=0}))},getReflectNode:function(e,t){var a=(0,l.default)(t);while(a.length){var i,n=a.shift();if(n[this.dataValue]===e[this.dataValue])return n;null!==(i=n[this.dataChildren])&&void 0!==i&&i.length&&a.push.apply(a,(0,l.default)(n[this.dataChildren]))}return{}},getContentHeight:function(e){var t=e.screenHeight;this.defaultContentHeight="".concat(Math.floor(.7*t),"px")},handleSearch:function(){var t=arguments.length>0&&void 0!==arguments[0]&&arguments[0];this.resetClearTimerList(),t?this.clearResetSearch&&this.renderTree(this.treeData):this.renderTree(this.searchValue(this.searchStr,this.treeData)),this.goTop(),e.hideKeyboard()},searchValue:function(e,t){var a=this,i=[];return t.forEach((function(t){var l;if(t.visible)if(t[a.dataLabel].toString().toLowerCase().indexOf(e.toLowerCase())>-1)i.push(t);else if(null!==(l=t[a.dataChildren])&&void 0!==l&&l.length){var r,s=a.searchValue(e,t[a.dataChildren]);if(null!==s&&void 0!==s&&s.length)e&&!t.showChildren&&null!==(r=t[a.dataChildren])&&void 0!==r&&r.length&&(t.showChildren=!0),i.push(d(d({},t),{},(0,n.default)({},a.dataChildren,s)))}})),i},renderTree:function(e){var t,a=(0,r.paging)(e);(t=this.filterTreeData).splice.apply(t,[0,this.filterTreeData.length].concat((0,l.default)((null===a||void 0===a?void 0:a[0])||[]))),this.lazyRenderList(a,1)},lazyRenderList:function(e,t){for(var a=this,i=function(t){var i;i=setTimeout((function(){var i;(i=a.filterTreeData).push.apply(i,(0,l.default)(e[t]))}),500*t),a.clearTimerList.push((function(){return clearTimeout(i)}))},n=t;n<e.length;n++)i(n)},resetClearTimerList:function(){var e=(0,l.default)(this.clearTimerList);this.clearTimerList=[],e.forEach((function(e){return e()}))},open:function(){var e=this;this.disabled||(this.showPopup=!0,this.$nextTick((function(){e.$refs.popup.open(),e.renderTree(e.treeData)})))},close:function(){this.$refs.popup.close()},change:function(t){var a=this;t.show?(e.$on("custom-tree-select-node-click",this.handleNodeClick),e.$on("custom-tree-select-name-click",this.handleHideChildren),e.$on("custom-tree-select-load",this.handleLoadNode)):(e.$off("custom-tree-select-node-click",this.handleNodeClick),e.$off("custom-tree-select-name-click",this.handleHideChildren),e.$off("custom-tree-select-load",this.handleLoadNode),this.resetClearTimerList(),this.searchStr="",this.animation?setTimeout((function(){a.showPopup=!1}),200):this.showPopup=!1),this.$emit("change",t)},maskClick:function(){this.$emit("maskClick")},initData:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:void 0,a=arguments.length>2&&void 0!==arguments[2]?arguments[2]:[];if(!Array.isArray(e))return[];for(var i=[],r=0;r<e.length;r++){var s,h,u=[].concat((0,l.default)(a),[e[r][this.dataLabel]]),c=d(d({},e[r]),{},(s={},(0,n.default)(s,this.dataLabel,e[r][this.dataLabel]),(0,n.default)(s,this.dataValue,e[r][this.dataValue]),(0,n.default)(s,"path",u.join(this.pathHyphen)),s));c.checked=this.selectList.includes(e[r][this.dataValue]),c.disabled=!1,(Boolean(e[r].disabled)||this.disabledList.includes(c[this.dataValue]))&&(c.disabled=!0),c.partChecked=Boolean(void 0!==e[r].partChecked&&e[r].partChecked),c.partChecked&&c.partCheckedSet.add(c[this.dataValue]),!c.partChecked&&(this.isSelectedAll=!1);var f=void 0===t||t,p=void 0===e[r].visible||Boolean(e[r].visible);if(c.visible=f===p?f:!(!f||!p),c.showChildren="showChildren"in e[r]&&void 0!=e[r].showChildren?e[r].showChildren:this.showChildren,null!==(h=e[r][this.dataChildren])&&void 0!==h&&h.length){var v=this.initData(e[r][this.dataChildren],c.visible,u);c[this.dataChildren]=v,!c.checked&&v.some((function(e){return e.checked||e.partChecked}))&&(c.partChecked=!0,o.add(c[this.dataValue]))}i.push(c)}return i},getChildren:function(e){var t;if(null===(t=e[this.dataChildren])||void 0===t||!t.length)return[];for(var a=e[this.dataChildren].reduce((function(e,t){return t.visible?[].concat((0,l.default)(e),[t]):e}),[]),i=0;i<e[this.dataChildren].length;i++)a.push.apply(a,(0,l.default)(this.getChildren(e[this.dataChildren][i])));return a},getParentNode:function(e,t){for(var a=[],i=0;i<t.length;i++){var n;if(t[i][this.dataValue]===e[this.dataValue])return!0;if(null!==(n=t[i][this.dataChildren])&&void 0!==n&&n.length){var r=this.getParentNode(e,t[i][this.dataChildren]);"boolean"===typeof r&&r?a=[t[i]]:Array.isArray(r)&&r.length&&(a=[].concat((0,l.default)(r),[t[i]]))}}return a},handleNodeClick:function(e,t){var a=this,i=this.getReflectNode(e,this.treeData);if(i.checked="boolean"===typeof t?t:!i.checked,i.partChecked=!1,o.delete(i[this.dataValue]),this.mutiple)if(this.linkage){var n=(0,l.default)(this.selectList),r=this.getParentNode(i,this.treeData),s=this.getChildren(i).filter((function(e){return!e.disabled}));if(i.checked){if(n=Array.from(new Set([].concat((0,l.default)(n),[i[this.dataValue]]))),s.length&&(n=Array.from(new Set([].concat((0,l.default)(n),(0,l.default)(s.map((function(e){return e[a.dataValue]})))))),s.forEach((function(e){e.partChecked=!1,o.delete(e[a.dataValue])}))),r.length){var d=!1;while(r.length){var h=r.shift();if(!h.disabled)if(d)h.partChecked=!0,o.add(h[this.dataValue]);else{var u=h[this.dataChildren].filter((function(e){return e.visible&&!e.disabled})).every((function(e){return e.checked}));u?(h.checked=!0,h.partChecked=!1,o.delete(h[this.dataValue]),n=Array.from(new Set([].concat((0,l.default)(n),[h[this.dataValue]])))):(h.partChecked=!0,o.add(h[this.dataValue]),d=!0)}}}}else n=n.filter((function(e){return e!==i[a.dataValue]})),s.length&&s.forEach((function(e){n=n.filter((function(t){return t!==e[a.dataValue]}))})),r.length&&r.forEach((function(e){n.includes(e[a.dataValue])&&(e.checked=!1),n=n.filter((function(t){return t!==e[a.dataValue]}));var t=e[a.dataChildren].filter((function(e){return e.visible&&!e.disabled})).some((function(e){return e.checked||e.partChecked}));e.partChecked=t,t?o.add(e[a.dataValue]):o.delete(e[a.dataValue])}));this.$emit("input",n)}else{var c=null;c=i.checked?Array.from(new Set([].concat((0,l.default)(this.selectList),[i[this.dataValue]]))):this.selectList.filter((function(e){return e!==i[a.dataValue]})),this.$emit("input",c)}else{var f=[];i.checked&&(f=[i[this.dataValue]]),this.$emit("input",f)}},handleHideChildren:function(e){var t=!e.showChildren;this.getReflectNode(e,this.treeData).showChildren=t,this.getReflectNode(e,this.filterTreeData).showChildren=t},changeStatus:function(e,t){var a=arguments.length>2&&void 0!==arguments[2]&&arguments[2],i=(0,l.default)(e),n=!0;a&&(this.selectedListBaseinfo=[]);while(i.length){var r,s=i.shift();t.includes(s[this.dataValue])?(this.$set(s,"checked",!0),a&&this.selectedListBaseinfo.push(s),s.partChecked=!1,o.delete(s[this.dataValue])):(this.$set(s,"checked",!1),s.visible&&!s.disabled&&(n=!1),o.has(s[this.dataValue])?this.$set(s,"partChecked",!0):this.$set(s,"partChecked",!1)),null!==(r=s[this.dataChildren])&&void 0!==r&&r.length&&i.push.apply(i,(0,l.default)(s[this.dataChildren]))}this.isSelectedAll=n,a&&this.$emit("selectChange",(0,l.default)(this.selectedListBaseinfo))},removeSelectedItem:function(e){var t=this;if(this.isSelectedAll=!1,this.linkage)this.handleNodeClick(e,!1),this.$emit("removeSelect",e);else{var a=this.selectList.filter((function(a){return a!==e[t.dataValue]}));this.$emit("removeSelect",e),this.$emit("input",a)}},handleSelectAll:function(){var t=this;if(this.isSelectedAll=!this.isSelectedAll,this.isSelectedAll){if(!this.mutiple)return void e.showToast({title:"单选模式下不能全选",icon:"none",duration:1e3});var a=[];this.treeData.forEach((function(e){var i;(e.visible||e.disabled&&e.checked)&&(a=Array.from(new Set([].concat((0,l.default)(a),[e[t.dataValue]]))),null!==(i=e[t.dataChildren])&&void 0!==i&&i.length&&(a=Array.from(new Set([].concat((0,l.default)(a),(0,l.default)(t.getChildren(e).filter((function(e){return!e.disabled||e.disabled&&e.checked})).map((function(e){return e[t.dataValue]}))))))))})),this.$emit("input",a)}else this.clear()},clear:function(){var e=this;if(!this.disabled){var t=[];o.clear(),this.selectedListBaseinfo.forEach((function(a){a.visible&&a.checked&&a.disabled&&t.push(a[e.dataValue])})),this.$emit("input",t)}},handleLoadNode:function(e){var t=this,a=e.source,i=e.target,n=this.getReflectNode(a,this.treeData);this.$set(n,this.dataChildren,this.initData(i,a.visible,a.path.split(this.pathHyphen))),this.$nextTick((function(){t.handleHideChildren(n)}))}}};t.default=h}).call(this,a("df3c")["default"])},"2d16f":function(e,t,a){},"622f":function(e,t,a){"use strict";a.r(t);var i=a("18b2"),n=a.n(i);for(var l in i)["default"].indexOf(l)<0&&function(e){a.d(t,e,(function(){return i[e]}))}(l);t["default"]=n.a},"74eb":function(e,t,a){"use strict";var i=a("2d16f"),n=a.n(i);n.a},"952b":function(e,t,a){"use strict";a.d(t,"b",(function(){return n})),a.d(t,"c",(function(){return l})),a.d(t,"a",(function(){return i}));var i={uniIcons:function(){return Promise.all([a.e("common/vendor"),a.e("uni_modules/uni-icons/components/uni-icons/uni-icons")]).then(a.bind(null,"18fa"))},uniPopup:function(){return a.e("uni_modules/uni-popup/components/uni-popup/uni-popup").then(a.bind(null,"7822"))},uniEasyinput:function(){return a.e("uni_modules/uni-easyinput/components/uni-easyinput/uni-easyinput").then(a.bind(null,"9ccf"))}},n=function(){var e=this,t=e.$createElement,a=(e._self._c,e.selectList.length),i=e.selectList.length,n=!e.selectList.length||!e.clearable,l=e.selectList.length&&e.clearable,r=e.showPopup?e.treeData.length:null,s=e.showPopup&&r?e.filterTreeData.length:null;e.$mp.data=Object.assign({},{$root:{g0:a,g1:i,g2:n,g3:l,g4:r,g5:s}})},l=[]},f2dd:function(e,t,a){"use strict";a.r(t);var i=a("952b"),n=a("622f");for(var l in n)["default"].indexOf(l)<0&&function(e){a.d(t,e,(function(){return n[e]}))}(l);a("74eb");var r=a("828b"),s=Object(r["a"])(n["default"],i["b"],i["c"],!1,null,"7c594d71",null,!1,i["a"],void 0);t["default"]=s.exports}}]);
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'uni_modules/custom-tree-select/components/custom-tree-select/custom-tree-select-create-component',
    {
        'uni_modules/custom-tree-select/components/custom-tree-select/custom-tree-select-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('df3c')['createComponent'](__webpack_require__("f2dd"))
        })
    },
    [['uni_modules/custom-tree-select/components/custom-tree-select/custom-tree-select-create-component']]
]);
