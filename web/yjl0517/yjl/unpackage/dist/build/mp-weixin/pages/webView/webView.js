(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/webView/webView"],{"0128":function(t,n,e){"use strict";e.r(n);var o=e("c3b5"),a=e("0742");for(var i in a)["default"].indexOf(i)<0&&function(t){e.d(n,t,(function(){return a[t]}))}(i);e("fd8a");var c=e("828b"),r=Object(c["a"])(a["default"],o["b"],o["c"],!1,null,null,null,!1,o["a"],void 0);n["default"]=r.exports},"0742":function(t,n,e){"use strict";e.r(n);var o=e("df3f"),a=e.n(o);for(var i in o)["default"].indexOf(i)<0&&function(t){e.d(n,t,(function(){return o[t]}))}(i);n["default"]=a.a},"40b4":function(t,n,e){},"6b3d":function(t,n,e){"use strict";(function(t,n){var o=e("47a9");e("faef");o(e("3240"));var a=o(e("0128"));t.__webpack_require_UNI_MP_PLUGIN__=e,n(a.default)}).call(this,e("3223")["default"],e("df3c")["createPage"])},c3b5:function(t,n,e){"use strict";e.d(n,"b",(function(){return o})),e.d(n,"c",(function(){return a})),e.d(n,"a",(function(){}));var o=function(){var t=this.$createElement;this._self._c},a=[]},df3f:function(t,n,e){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var o=e("03c2"),a={data:function(){return{gid:"",news:{},htmlStr:""}},onLoad:function(t){this.gid=t.gid,console.log(this.gid)},methods:{getDocList:function(t){var n=this;(0,o.getDoc)({pageNum:1,pageSize:5,sort:0,gid:t}).then((function(t){if(console.log(t),200==t.data.code){n.news=t.data.data.list[0];var e=t.data.data.list[0].content;console.log(e);var o=e.split("\r\n");console.log(o);var a="";a+="<h1>"+o[0]+"</h1>";for(var i=1;i<o.length;i++)o[i].startsWith("信息来源：")||o[i].startsWith("发布日期：")?a+='<p class="info">'+o[i]+"</p>":a+="<p>"+o[i]+"</p>";a+='<div class="footer"></div>',n.htmlStr=a,console.log(a)}}))}},onShow:function(){this.getDocList(this.gid)}};n.default=a},fd8a:function(t,n,e){"use strict";var o=e("40b4"),a=e.n(o);a.a}},[["6b3d","common/runtime","common/vendor"]]]);