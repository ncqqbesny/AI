(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-orderInfo-orderInfo"],{"078c":function(e,t,i){var n=i("bf1f");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=i("967d").default;a("1483e6f8",n,!0,{sourceMap:!1,shadowMode:!1})},"0c25":function(e,t,i){"use strict";var n=i("3437"),a=i.n(n);a.a},"175b":function(e,t,i){"use strict";var n=i("b550"),a=i.n(n);a.a},"2ed8":function(e,t,i){"use strict";(function(e){i("6a54");var n=i("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=n(i("b7c7")),r=n(i("9b1b")),o=n(i("2634")),s=n(i("2fdc"));i("64aa"),i("bf0f"),i("2797"),i("aa9c"),i("dd2b"),i("5c47"),i("0506"),i("dc8a"),i("c223"),i("18f7"),i("de6c"),i("bd06"),i("e966"),i("20f3");var c=i("5475"),l=i("3bf8"),u=n(i("6e39")),d=n(i("3a59")),f={name:"uniFilePicker",components:{uploadImage:u.default,uploadFile:d.default},options:{virtualHost:!0},emits:["select","success","fail","progress","delete","update:modelValue","input"],props:{value:{type:[Array,Object],default:function(){return[]}},disabled:{type:Boolean,default:!1},disablePreview:{type:Boolean,default:!1},delIcon:{type:Boolean,default:!0},autoUpload:{type:Boolean,default:!0},limit:{type:[Number,String],default:9},mode:{type:String,default:"grid"},fileMediatype:{type:String,default:"image"},fileExtname:{type:[Array,String],default:function(){return[]}},title:{type:String,default:""},listStyles:{type:Object,default:function(){return{border:!0,dividline:!0,borderStyle:{}}}},imageStyles:{type:Object,default:function(){return{width:"auto",height:"auto"}}},readonly:{type:Boolean,default:!1},returnType:{type:String,default:"array"},sizeType:{type:Array,default:function(){return["original","compressed"]}},sourceType:{type:Array,default:function(){return["album","camera"]}}},data:function(){return{files:[],localValue:[]}},watch:{value:{handler:function(e,t){this.setValue(e,t)},immediate:!0}},computed:{filesList:function(){var e=[];return this.files.forEach((function(t){e.push(t)})),e},showType:function(){return"image"===this.fileMediatype?this.mode:"list"},limitLength:function(){return"object"===this.returnType?1:this.limit?this.limit>=9?9:this.limit:1}},created:function(){e.config&&e.config.provider||(this.noSpace=!0,e.chooseAndUploadFile=c.chooseAndUploadFile),this.form=this.getForm("uniForms"),this.formItem=this.getForm("uniFormsItem"),this.form&&this.formItem&&this.formItem.name&&(this.rename=this.formItem.name,this.form.inputChildrens.push(this))},methods:{clearFiles:function(e){var t=this;0===e||e?this.files.splice(e,1):(this.files=[],this.$nextTick((function(){t.setEmit()}))),this.$nextTick((function(){t.setEmit()}))},upload:function(){var e=[];return this.files.forEach((function(t,i){"ready"!==t.status&&"error"!==t.status||e.push(Object.assign({},t))})),this.uploadFiles(e)},setValue:function(e,t){var i=this;return(0,s.default)((0,o.default)().mark((function t(){var n,a,r,c;return(0,o.default)().wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(n=function(){var e=(0,s.default)((0,o.default)().mark((function e(t){var n,a;return(0,o.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(n=/cloud:\/\/([\w.]+\/?)\S*/,a="",a=t.fileID?t.fileID:t.url,!n.test(a)){e.next=8;break}return t.fileID=a,e.next=7,i.getTempFileURL(a);case 7:t.url=e.sent;case 8:return t.url&&(t.path=t.url),e.abrupt("return",t);case 10:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),"object"!==i.returnType){t.next=10;break}if(!e){t.next=7;break}return t.next=5,n(e);case 5:t.next=8;break;case 7:e={};case 8:t.next=19;break;case 10:e||(e=[]),a=0;case 12:if(!(a<e.length)){t.next=19;break}return r=e[a],t.next=16,n(r);case 16:a++,t.next=12;break;case 19:i.localValue=e,i.form&&i.formItem&&!i.is_reset&&(i.is_reset=!1,i.formItem.setValue(i.localValue)),c=Object.keys(e).length>0?e:[],i.files=[].concat(c);case 23:case"end":return t.stop()}}),t)})))()},choose:function(){this.disabled||(this.files.length>=Number(this.limitLength)&&"grid"!==this.showType&&"array"===this.returnType?uni.showToast({title:"您最多选择 ".concat(this.limitLength," 个文件"),icon:"none"}):this.chooseFiles())},chooseFiles:function(){var t=this,i=(0,l.get_extname)(this.fileExtname);e.chooseAndUploadFile({type:this.fileMediatype,compressed:!1,sizeType:this.sizeType,sourceType:this.sourceType,extension:i.length>0?i:void 0,count:this.limitLength-this.files.length,onChooseFile:this.chooseFileCallback,onUploadProgress:function(e){t.setProgress(e,e.index)}}).then((function(e){t.setSuccessAndError(e.tempFiles)})).catch((function(e){console.log("选择失败",e)}))},chooseFileCallback:function(e){var t=this;return(0,s.default)((0,o.default)().mark((function i(){var n,a,s,c,u,d,f,p;return(0,o.default)().wrap((function(i){while(1)switch(i.prev=i.next){case 0:n=(0,l.get_extname)(t.fileExtname),a=1===Number(t.limitLength)&&t.disablePreview&&!t.disabled||"object"===t.returnType,a&&(t.files=[]),s=(0,l.get_files_and_is_max)(e,n),c=s.filePaths,u=s.files,n&&n.length>0||(c=e.tempFilePaths,u=e.tempFiles),d=[],f=0;case 7:if(!(f<u.length)){i.next=21;break}if(!(t.limitLength-t.files.length<=0)){i.next=10;break}return i.abrupt("break",21);case 10:return u[f].uuid=Date.now(),i.next=13,(0,l.get_file_data)(u[f],t.fileMediatype);case 13:p=i.sent,p.progress=0,p.status="ready",t.files.push(p),d.push((0,r.default)((0,r.default)({},p),{},{file:u[f]}));case 18:f++,i.next=7;break;case 21:t.$emit("select",{tempFiles:d,tempFilePaths:c}),e.tempFiles=u,t.autoUpload&&!t.noSpace||(e.tempFiles=[]);case 24:case"end":return i.stop()}}),i)})))()},uploadFiles:function(e){var t=this;return e=[].concat(e),c.uploadCloudFiles.call(this,e,5,(function(e){t.setProgress(e,e.index,!0)})).then((function(e){return t.setSuccessAndError(e),e})).catch((function(e){console.log(e)}))},setSuccessAndError:function(e,t){var i=this;return(0,s.default)((0,o.default)().mark((function t(){var n,a,r,s,c,l,u;return(0,o.default)().wrap((function(t){while(1)switch(t.prev=t.next){case 0:n=[],a=[],r=[],s=[],c=(0,o.default)().mark((function t(c){var l,u,d;return(0,o.default)().wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(l=e[c],u=l.uuid?i.files.findIndex((function(e){return e.uuid===l.uuid})):l.index,-1!==u&&i.files){t.next=4;break}return t.abrupt("return","break");case 4:if("request:fail"!==l.errMsg){t.next=12;break}i.files[u].url=l.path,i.files[u].status="error",i.files[u].errMsg=l.errMsg,a.push(i.files[u]),s.push(i.files[u].url),t.next=26;break;case 12:if(i.files[u].errMsg="",i.files[u].fileID=l.url,d=/cloud:\/\/([\w.]+\/?)\S*/,!d.test(l.url)){t.next=21;break}return t.next=18,i.getTempFileURL(l.url);case 18:i.files[u].url=t.sent,t.next=22;break;case 21:i.files[u].url=l.url;case 22:i.files[u].status="success",i.files[u].progress+=1,n.push(i.files[u]),r.push(i.files[u].fileID);case 26:case"end":return t.stop()}}),t)})),l=0;case 6:if(!(l<e.length)){t.next=14;break}return t.delegateYield(c(l),"t0",8);case 8:if(u=t.t0,"break"!==u){t.next=11;break}return t.abrupt("break",14);case 11:l++,t.next=6;break;case 14:n.length>0&&(i.setEmit(),i.$emit("success",{tempFiles:i.backObject(n),tempFilePaths:r})),a.length>0&&i.$emit("fail",{tempFiles:i.backObject(a),tempFilePaths:s});case 16:case"end":return t.stop()}}),t)})))()},setProgress:function(e,t,i){this.files.length;var n=Math.round(100*e.loaded/e.total),a=t;i||(a=this.files.findIndex((function(t){return t.uuid===e.tempFile.uuid}))),-1!==a&&this.files[a]&&(this.files[a].progress=n-1,this.$emit("progress",{index:a,progress:parseInt(n),tempFile:this.files[a]}))},delFile:function(e){var t=this;this.$emit("delete",{tempFile:this.files[e],tempFilePath:this.files[e].url}),this.files.splice(e,1),this.$nextTick((function(){t.setEmit()}))},getFileExt:function(e){var t=e.lastIndexOf("."),i=e.length;return{name:e.substring(0,t),ext:e.substring(t+1,i)}},setEmit:function(){var e=[];"object"===this.returnType?(e=this.backObject(this.files)[0],this.localValue=e||null):(e=this.backObject(this.files),this.localValue||(this.localValue=[]),this.localValue=(0,a.default)(e)),this.$emit("input",this.localValue)},backObject:function(e){var t=[];return e.forEach((function(e){t.push({extname:e.extname,fileType:e.fileType,image:e.image,name:e.name,path:e.path,size:e.size,fileID:e.fileID,url:e.url,uuid:e.uuid,status:e.status,cloudPath:e.cloudPath})})),t},getTempFileURL:function(t){return(0,s.default)((0,o.default)().mark((function i(){var n;return(0,o.default)().wrap((function(i){while(1)switch(i.prev=i.next){case 0:return t={fileList:[].concat(t)},i.next=3,e.getTempFileURL(t);case 3:return n=i.sent,i.abrupt("return",n.fileList[0].tempFileURL||"");case 5:case"end":return i.stop()}}),i)})))()},getForm:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"uniForms",t=this.$parent,i=t.$options.name;while(i!==e){if(t=t.$parent,!t)return!1;i=t.$options.name}return t}}};t.default=f}).call(this,i("861b")["default"])},3437:function(e,t,i){var n=i("e1ff");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=i("967d").default;a("8ca70194",n,!0,{sourceMap:!1,shadowMode:!1})},"3a59":function(e,t,i){"use strict";i.r(t);var n=i("7fd3"),a=i("c314");for(var r in a)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return a[e]}))}(r);i("175b");var o=i("828b"),s=Object(o["a"])(a["default"],n["b"],n["c"],!1,null,"1fecfc2e",null,!1,n["a"],void 0);t["default"]=s.exports},"3bf8":function(e,t,i){"use strict";i("6a54");var n=i("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.get_files_and_is_max=t.get_file_info=t.get_file_ext=t.get_file_data=t.get_extname=void 0;var a=n(i("2634")),r=n(i("2fdc"));i("20f3"),i("5c47"),i("a1c1"),i("bf0f"),i("2797"),i("5ef2"),i("aa9c"),i("c223");var o=function(e){var t=e.lastIndexOf("."),i=e.length;return{name:e.substring(0,t),ext:e.substring(t+1,i)}};t.get_file_ext=o;t.get_extname=function(e){if(Array.isArray(e))return e;var t=e.replace(/(\[|\])/g,"");return t.split(",")};t.get_files_and_is_max=function(e,t){var i=[],n=[];return t&&0!==t.length?(e.tempFiles.forEach((function(e){var a=o(e.name),r=a.ext.toLowerCase();-1!==t.indexOf(r)&&(n.push(e),i.push(e.path))})),n.length!==e.tempFiles.length&&uni.showToast({title:"当前选择了".concat(e.tempFiles.length,"个文件 ，").concat(e.tempFiles.length-n.length," 个文件格式不正确"),icon:"none",duration:5e3}),{filePaths:i,files:n}):{filePaths:i,files:n}};var s=function(e){return new Promise((function(t,i){uni.getImageInfo({src:e,success:function(e){t(e)},fail:function(e){i(e)}})}))};t.get_file_info=s;var c=function(){var e=(0,r.default)((0,a.default)().mark((function e(t){var i,n,r,c,l,u=arguments;return(0,a.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(i=u.length>1&&void 0!==u[1]?u[1]:"image",n=o(t.name),r=n.ext.toLowerCase(),c={name:t.name,uuid:t.uuid,extname:r||"",cloudPath:t.cloudPath,fileType:t.fileType,url:t.path||t.path,size:t.size,image:{},path:t.path,video:{}},"image"!==i){e.next=14;break}return e.next=7,s(t.path);case 7:l=e.sent,delete c.video,c.image.width=l.width,c.image.height=l.height,c.image.location=l.path,e.next=15;break;case 14:delete c.image;case 15:return e.abrupt("return",c);case 16:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}();t.get_file_data=c},4398:function(e,t,i){"use strict";i.r(t);var n=i("8ee4"),a=i("c145");for(var r in a)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return a[e]}))}(r);i("0c25");var o=i("828b"),s=Object(o["a"])(a["default"],n["b"],n["c"],!1,null,"6d2c27ec",null,!1,n["a"],void 0);t["default"]=s.exports},5475:function(e,t,i){"use strict";(function(e){i("6a54"),Object.defineProperty(t,"__esModule",{value:!0}),t.chooseAndUploadFile=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{type:"all"};if("image"===e.type)return c(a(e),e);if("video"===e.type)return c(r(e),e);return c(o(e),e)},t.uploadCloudFiles=function(t){var i=arguments.length>1&&void 0!==arguments[1]?arguments[1]:5,n=arguments.length>2?arguments[2]:void 0;t=JSON.parse(JSON.stringify(t));var a=t.length,r=0,o=this;return new Promise((function(s){while(r<i)c();function c(){var i=r++;if(i>=a)!t.find((function(e){return!e.url&&!e.errMsg}))&&s(t);else{var l=t[i],u=o.files.findIndex((function(e){return e.uuid===l.uuid}));l.url="",delete l.errMsg,e.uploadFile({filePath:l.path,cloudPath:l.cloudPath,fileType:l.fileType,onUploadProgress:function(e){e.index=u,n&&n(e)}}).then((function(e){l.url=e.fileID,l.index=u,i<a&&c()})).catch((function(e){l.errMsg=e.errMsg||e.message,l.index=u,i<a&&c()}))}}}))},i("bf0f"),i("5c47"),i("a1c1"),i("2797"),i("20f3"),i("fd3c"),i("d4b5"),i("aa77"),i("bd06");var n="chooseAndUploadFile:fail";function a(e){var t=e.count,i=e.sizeType,a=void 0===i?["original","compressed"]:i,r=e.sourceType,o=e.extension;return new Promise((function(e,i){uni.chooseImage({count:t,sizeType:a,sourceType:r,extension:o,success:function(t){e(s(t,"image"))},fail:function(e){i({errMsg:e.errMsg.replace("chooseImage:fail",n)})}})}))}function r(e){var t=e.camera,i=e.compressed,a=e.maxDuration,r=e.sourceType,o=e.extension;return new Promise((function(e,c){uni.chooseVideo({camera:t,compressed:i,maxDuration:a,sourceType:r,extension:o,success:function(t){var i=t.tempFilePath,n=t.duration,a=t.size,r=t.height,o=t.width;e(s({errMsg:"chooseVideo:ok",tempFilePaths:[i],tempFiles:[{name:t.tempFile&&t.tempFile.name||"",path:i,size:a,type:t.tempFile&&t.tempFile.type||"",width:o,height:r,duration:n,fileType:"video",cloudPath:""}]},"video"))},fail:function(e){c({errMsg:e.errMsg.replace("chooseVideo:fail",n)})}})}))}function o(e){var t=e.count,i=e.extension;return new Promise((function(e,a){var r=uni.chooseFile;if("undefined"!==typeof wx&&"function"===typeof wx.chooseMessageFile&&(r=wx.chooseMessageFile),"function"!==typeof r)return a({errMsg:n+" 请指定 type 类型，该平台仅支持选择 image 或 video。"});r({type:"all",count:t,extension:i,success:function(t){e(s(t))},fail:function(e){a({errMsg:e.errMsg.replace("chooseFile:fail",n)})}})}))}function s(e,t){return e.tempFiles.forEach((function(e,i){e.name||(e.name=e.path.substring(e.path.lastIndexOf("/")+1)),t&&(e.fileType=t),e.cloudPath=Date.now()+"_"+i+e.name.substring(e.name.lastIndexOf("."))})),e.tempFilePaths||(e.tempFilePaths=e.tempFiles.map((function(e){return e.path}))),e}function c(e,t){var i=t.onChooseFile;t.onUploadProgress;return e.then((function(e){if(i){var t=i(e);if("undefined"!==typeof t)return Promise.resolve(t).then((function(t){return"undefined"===typeof t?e:t}))}return e})).then((function(e){return!1===e?{errMsg:"chooseAndUploadFile:ok",tempFilePaths:[],tempFiles:[]}:e}))}}).call(this,i("861b")["default"])},"6c39":function(e,t,i){"use strict";var n=i("078c"),a=i.n(n);a.a},"6e39":function(e,t,i){"use strict";i.r(t);var n=i("b538"),a=i("89ea");for(var r in a)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return a[e]}))}(r);i("d917");var o=i("828b"),s=Object(o["a"])(a["default"],n["b"],n["c"],!1,null,"5cf66a2a",null,!1,n["a"],void 0);t["default"]=s.exports},"73e1":function(e,t,i){"use strict";var n=i("29d8");e.exports=/Version\/10(?:\.\d+){1,2}(?: [\w./]+)?(?: Mobile\/\w+)? Safari\//.test(n)},"795c":function(e,t,i){"use strict";var n=i("8bdb"),a=i("db04").start,r=i("73e1");n({target:"String",proto:!0,forced:r},{padStart:function(e){return a(this,e,arguments.length>1?arguments[1]:void 0)}})},"7e7d":function(e,t,i){"use strict";i("6a54");var n=i("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.arrToTree=function e(t,i){var n=[];return t.forEach((function(a){if(a.parentMenuId===i){var r=e(t,a.menuId);r.length&&(a.children=r),n.push(a)}})),n},t.arrToTree2=function(e){var t={},i=[];return e.forEach((function(e){t[e.mid]=(0,a.default)((0,a.default)({},e),{},{children:[]})})),e.forEach((function(e){var n=t[e.parentMid];n?n.children.push(t[e.mid]):i.push(t[e.mid])})),i},t.checkNumberInArray=function(e,t){return e.includes(t)},t.encode=function(e,t){for(var i=parseInt(e),n=[],a=0;a<t.length;++a){var r=t[a];n.push(r.length^i);for(var o=0;o<r.length;++o)n.push(r[o].charCodeAt(0)^i)}n.push(i);var s="CV16"+n.join("%");return s};var a=n(i("9b1b"));i("e966"),i("aa9c"),i("bf0f"),i("2797"),i("4626"),i("5ac7")},"7fd3":function(e,t,i){"use strict";i.d(t,"b",(function(){return n})),i.d(t,"c",(function(){return a})),i.d(t,"a",(function(){}));var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"uni-file-picker__files"},[e.readonly?e._e():i("v-uni-view",{staticClass:"files-button",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.choose.apply(void 0,arguments)}}},[e._t("default")],2),e.list.length>0?i("v-uni-view",{staticClass:"uni-file-picker__lists is-text-box",style:e.borderStyle},e._l(e.list,(function(t,n){return i("v-uni-view",{key:n,staticClass:"uni-file-picker__lists-box",class:{"files-border":0!==n&&e.styles.dividline},style:0!==n&&e.styles.dividline&&e.borderLineStyle},[i("v-uni-view",{staticClass:"uni-file-picker__item"},[i("v-uni-view",{staticClass:"files__name"},[e._v(e._s(t.name))]),e.delIcon&&!e.readonly?i("v-uni-view",{staticClass:"icon-del-box icon-files",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.delFile(n)}}},[i("v-uni-view",{staticClass:"icon-del icon-files"}),i("v-uni-view",{staticClass:"icon-del rotate"})],1):e._e()],1),t.progress&&100!==t.progress||0===t.progress?i("v-uni-view",{staticClass:"file-picker__progress"},[i("v-uni-progress",{staticClass:"file-picker__progress-item",attrs:{percent:-1===t.progress?0:t.progress,"stroke-width":"4",backgroundColor:t.errMsg?"#ff5a5f":"#EBEBEB"}})],1):e._e(),"error"===t.status?i("v-uni-view",{staticClass:"file-picker__mask",on:{click:function(i){i.stopPropagation(),arguments[0]=i=e.$handleEvent(i),e.uploadFiles(t,n)}}},[e._v("点击重试")]):e._e()],1)})),1):e._e()],1)},a=[]},"89ea":function(e,t,i){"use strict";i.r(t);var n=i("ba10"),a=i.n(n);for(var r in n)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return n[e]}))}(r);t["default"]=a.a},"8e8d":function(e,t,i){var n=i("ad82");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=i("967d").default;a("3bfb2165",n,!0,{sourceMap:!1,shadowMode:!1})},"8ee4":function(e,t,i){"use strict";i.d(t,"b",(function(){return a})),i.d(t,"c",(function(){return r})),i.d(t,"a",(function(){return n}));var n={uniFilePicker:i("af77").default,uniPopup:i("7822").default,uniPopupDialog:i("eb8c").default},a=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"container"},[i("v-uni-view",{staticClass:"view_title"},[i("v-uni-text",{},[e._v(e._s(1==e.formData.type?"物资调用申请":"物资归还申请"))])],1),i("v-uni-view",{staticClass:"eventInfo"},[i("v-uni-view",{staticClass:"event_id"},[e._v("事件编号："+e._s(e.code))]),i("v-uni-view",{staticClass:"event_address"},[i("v-uni-view",{staticClass:"title"},[e._v("处理地址：")]),e._v(e._s(e.eventData.address))],1),i("v-uni-view",{staticClass:"evenet_remark"},[e._v("事件备注："+e._s(e.eventData.remark))]),i("v-uni-view",{staticClass:"evenet_desc"},[e._v("物资取用说明:"+e._s(e.formData.yjDesc))]),i("v-uni-view",{staticClass:"event_img"},[i("uni-file-picker",{attrs:{readonly:!0,value:e.imgValue,"file-mediatype":"image"}})],1),i("v-uni-view",{staticClass:"event_footer"},[e._v(e._s(e.StatustoString(e.formData.status,e.formData.type)))])],1),i("v-uni-view",{staticClass:"btnGroup"},[2==e.formData.status&&e.checkNumberInArray(e.menu,"9")?i("v-uni-view",{staticClass:"btn toAdd",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.toAdd(e.formData.orderNumber,2)}}},[e._v("发起物资归还申请")]):e._e(),2==e.formData.status&&e.checkNumberInArray(e.menu,"8")?i("v-uni-view",{staticClass:"btn toList",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.toList(e.code,"2")}}},[e._v("查看物资归还申请")]):e._e(),1==e.formData.status&&e.checkNumberInArray(e.menu,"10")?i("v-uni-view",{staticClass:"btn toAudit"},[i("v-uni-view",{staticClass:"notapproved",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.showNot()}}},[e._v("驳回")]),i("v-uni-view",{staticClass:"approved",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.showApproved()}}},[e._v("批准")]),i("uni-popup",{ref:"alertNot",attrs:{type:"dialog"}},[i("uni-popup-dialog",{attrs:{type:"info",cancelText:"取消",confirmText:"确认",title:"通知",content:"确认驳回此条申请?"},on:{confirm:function(t){arguments[0]=t=e.$handleEvent(t),e.changeStatus(3)},close:function(t){arguments[0]=t=e.$handleEvent(t),e.dialogClose.apply(void 0,arguments)}}})],1),i("uni-popup",{ref:"alertApproved",attrs:{type:"dialog"}},[i("uni-popup-dialog",{attrs:{type:"success",cancelText:"取消",confirmText:"确认",title:"通知",content:"确认同意此条申请?"},on:{confirm:function(t){arguments[0]=t=e.$handleEvent(t),e.changeStatus(2)},close:function(t){arguments[0]=t=e.$handleEvent(t),e.dialogClose.apply(void 0,arguments)}}})],1)],1):e._e()],1)],1)},r=[]},"9f20":function(e,t,i){"use strict";i.r(t);var n=i("2ed8"),a=i.n(n);for(var r in n)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return n[e]}))}(r);t["default"]=a.a},ad82:function(e,t,i){var n=i("c86c");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.uni-file-picker__container[data-v-5cf66a2a]{display:flex;box-sizing:border-box;flex-wrap:wrap;margin:-5px}.file-picker__box[data-v-5cf66a2a]{position:relative;width:33.3%;height:0;padding-top:33.33%;box-sizing:border-box}.file-picker__box-content[data-v-5cf66a2a]{position:absolute;top:0;right:0;bottom:0;left:0;margin:5px;border:1px #eee solid;border-radius:5px;overflow:hidden}.file-picker__progress[data-v-5cf66a2a]{position:absolute;bottom:0;left:0;right:0;\n  /* border: 1px red solid; */z-index:2}.file-picker__progress-item[data-v-5cf66a2a]{width:100%}.file-picker__mask[data-v-5cf66a2a]{display:flex;justify-content:center;align-items:center;position:absolute;right:0;top:0;bottom:0;left:0;color:#fff;font-size:12px;background-color:rgba(0,0,0,.4)}.file-image[data-v-5cf66a2a]{width:100%;height:100%}.is-add[data-v-5cf66a2a]{display:flex;align-items:center;justify-content:center}.icon-add[data-v-5cf66a2a]{width:50px;height:5px;background-color:#f1f1f1;border-radius:2px}.rotate[data-v-5cf66a2a]{position:absolute;-webkit-transform:rotate(90deg);transform:rotate(90deg)}.icon-del-box[data-v-5cf66a2a]{display:flex;align-items:center;justify-content:center;position:absolute;top:3px;right:3px;height:26px;width:26px;border-radius:50%;background-color:rgba(0,0,0,.5);z-index:2;-webkit-transform:rotate(-45deg);transform:rotate(-45deg)}.icon-del[data-v-5cf66a2a]{width:15px;height:2px;background-color:#fff;border-radius:2px}',""]),e.exports=t},af77:function(e,t,i){"use strict";i.r(t);var n=i("bdb6"),a=i("9f20");for(var r in a)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return a[e]}))}(r);i("6c39");var o=i("828b"),s=Object(o["a"])(a["default"],n["b"],n["c"],!1,null,"504220e6",null,!1,n["a"],void 0);t["default"]=s.exports},b538:function(e,t,i){"use strict";i.d(t,"b",(function(){return n})),i.d(t,"c",(function(){return a})),i.d(t,"a",(function(){}));var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"uni-file-picker__container"},[e._l(e.filesList,(function(t,n){return i("v-uni-view",{key:n,staticClass:"file-picker__box",style:e.boxStyle},[i("v-uni-view",{staticClass:"file-picker__box-content",style:e.borderStyle},[i("v-uni-image",{staticClass:"file-image",attrs:{src:t.url,mode:"aspectFill"},on:{click:function(i){i.stopPropagation(),arguments[0]=i=e.$handleEvent(i),e.prviewImage(t,n)}}}),e.delIcon&&!e.readonly?i("v-uni-view",{staticClass:"icon-del-box",on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.delFile(n)}}},[i("v-uni-view",{staticClass:"icon-del"}),i("v-uni-view",{staticClass:"icon-del rotate"})],1):e._e(),t.progress&&100!==t.progress||0===t.progress?i("v-uni-view",{staticClass:"file-picker__progress"},[i("v-uni-progress",{staticClass:"file-picker__progress-item",attrs:{percent:-1===t.progress?0:t.progress,"stroke-width":"4",backgroundColor:t.errMsg?"#ff5a5f":"#EBEBEB"}})],1):e._e(),t.errMsg?i("v-uni-view",{staticClass:"file-picker__mask",on:{click:function(i){i.stopPropagation(),arguments[0]=i=e.$handleEvent(i),e.uploadFiles(t,n)}}},[e._v("点击重试")]):e._e()],1)],1)})),e.filesList.length<e.limit&&!e.readonly?i("v-uni-view",{staticClass:"file-picker__box",style:e.boxStyle},[i("v-uni-view",{staticClass:"file-picker__box-content is-add",style:e.borderStyle,on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.choose.apply(void 0,arguments)}}},[e._t("default",[i("v-uni-view",{staticClass:"icon-add"}),i("v-uni-view",{staticClass:"icon-add rotate"})])],2)],1):e._e()],2)},a=[]},b550:function(e,t,i){var n=i("e768");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=i("967d").default;a("7741e6c8",n,!0,{sourceMap:!1,shadowMode:!1})},ba10:function(e,t,i){"use strict";i("6a54"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,i("64aa"),i("c223"),i("bf0f"),i("2797"),i("aa9c"),i("5ef2");var n={name:"uploadImage",emits:["uploadFiles","choose","delFile"],props:{filesList:{type:Array,default:function(){return[]}},disabled:{type:Boolean,default:!1},disablePreview:{type:Boolean,default:!1},limit:{type:[Number,String],default:9},imageStyles:{type:Object,default:function(){return{width:"auto",height:"auto",border:{}}}},delIcon:{type:Boolean,default:!0},readonly:{type:Boolean,default:!1}},computed:{styles:function(){return Object.assign({width:"auto",height:"auto",border:{}},this.imageStyles)},boxStyle:function(){var e=this.styles,t=e.width,i=void 0===t?"auto":t,n=e.height,a=void 0===n?"auto":n,r={};"auto"===a?"auto"!==i?(r.height=this.value2px(i),r["padding-top"]=0):r.height=0:(r.height=this.value2px(a),r["padding-top"]=0),r.width="auto"===i?"auto"!==a?this.value2px(a):"33.3%":this.value2px(i);var o="";for(var s in r)o+="".concat(s,":").concat(r[s],";");return o},borderStyle:function(){var e=this.styles.border,t={};if("boolean"===typeof e)t.border=e?"1px #eee solid":"none";else{var i=e&&e.width||1;i=this.value2px(i);var n=e&&e.radius||3;n=this.value2px(n),t={"border-width":i,"border-style":e&&e.style||"solid","border-color":e&&e.color||"#eee","border-radius":n}}var a="";for(var r in t)a+="".concat(r,":").concat(t[r],";");return a}},methods:{uploadFiles:function(e,t){this.$emit("uploadFiles",e)},choose:function(){this.$emit("choose")},delFile:function(e){this.$emit("delFile",e)},prviewImage:function(e,t){var i=[];1===Number(this.limit)&&this.disablePreview&&!this.disabled&&this.$emit("choose"),this.disablePreview||(this.filesList.forEach((function(e){i.push(e.url)})),uni.previewImage({urls:i,current:t}))},value2px:function(e){return"number"===typeof e?e+="px":-1===e.indexOf("%")&&(e=-1!==e.indexOf("px")?e:e+"px"),e}}};t.default=n},bdb6:function(e,t,i){"use strict";i.d(t,"b",(function(){return n})),i.d(t,"c",(function(){return a})),i.d(t,"a",(function(){}));var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"uni-file-picker"},[e.title?i("v-uni-view",{staticClass:"uni-file-picker__header"},[i("v-uni-text",{staticClass:"file-title"},[e._v(e._s(e.title))]),i("v-uni-text",{staticClass:"file-count"},[e._v(e._s(e.filesList.length)+"/"+e._s(e.limitLength))])],1):e._e(),"image"===e.fileMediatype&&"grid"===e.showType?i("upload-image",{attrs:{readonly:e.readonly,"image-styles":e.imageStyles,"files-list":e.filesList,limit:e.limitLength,disablePreview:e.disablePreview,delIcon:e.delIcon},on:{uploadFiles:function(t){arguments[0]=t=e.$handleEvent(t),e.uploadFiles.apply(void 0,arguments)},choose:function(t){arguments[0]=t=e.$handleEvent(t),e.choose.apply(void 0,arguments)},delFile:function(t){arguments[0]=t=e.$handleEvent(t),e.delFile.apply(void 0,arguments)}}},[e._t("default",[i("v-uni-view",{staticClass:"is-add"},[i("v-uni-view",{staticClass:"icon-add"}),i("v-uni-view",{staticClass:"icon-add rotate"})],1)])],2):e._e(),"image"!==e.fileMediatype||"grid"!==e.showType?i("upload-file",{attrs:{readonly:e.readonly,"list-styles":e.listStyles,"files-list":e.filesList,showType:e.showType,delIcon:e.delIcon},on:{uploadFiles:function(t){arguments[0]=t=e.$handleEvent(t),e.uploadFiles.apply(void 0,arguments)},choose:function(t){arguments[0]=t=e.$handleEvent(t),e.choose.apply(void 0,arguments)},delFile:function(t){arguments[0]=t=e.$handleEvent(t),e.delFile.apply(void 0,arguments)}}},[e._t("default",[i("v-uni-button",{attrs:{type:"primary",size:"mini"}},[e._v("选择文件")])])],2):e._e()],1)},a=[]},bf1f:function(e,t,i){var n=i("c86c");t=n(!1),t.push([e.i,".uni-file-picker[data-v-504220e6]{\nbox-sizing:border-box;overflow:hidden;width:100%;\nflex:1}.uni-file-picker__header[data-v-504220e6]{padding-top:5px;padding-bottom:10px;\ndisplay:flex;\njustify-content:space-between}.file-title[data-v-504220e6]{font-size:14px;color:#333}.file-count[data-v-504220e6]{font-size:14px;color:#999}.is-add[data-v-504220e6]{\ndisplay:flex;\nalign-items:center;justify-content:center}.icon-add[data-v-504220e6]{width:50px;height:5px;background-color:#f1f1f1;border-radius:2px}.rotate[data-v-504220e6]{position:absolute;-webkit-transform:rotate(90deg);transform:rotate(90deg)}",""]),e.exports=t},c145:function(e,t,i){"use strict";i.r(t);var n=i("fe67"),a=i.n(n);for(var r in n)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return n[e]}))}(r);t["default"]=a.a},c314:function(e,t,i){"use strict";i.r(t);var n=i("d0c2"),a=i.n(n);for(var r in n)["default"].indexOf(r)<0&&function(e){i.d(t,e,(function(){return n[e]}))}(r);t["default"]=a.a},d0c2:function(e,t,i){"use strict";i("6a54"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,i("64aa"),i("bf0f"),i("2797"),i("aa9c"),i("c223"),i("5ef2");var n={name:"uploadFile",emits:["uploadFiles","choose","delFile"],props:{filesList:{type:Array,default:function(){return[]}},delIcon:{type:Boolean,default:!0},limit:{type:[Number,String],default:9},showType:{type:String,default:""},listStyles:{type:Object,default:function(){return{border:!0,dividline:!0,borderStyle:{}}}},readonly:{type:Boolean,default:!1}},computed:{list:function(){var e=[];return this.filesList.forEach((function(t){e.push(t)})),e},styles:function(){return Object.assign({border:!0,dividline:!0,"border-style":{}},this.listStyles)},borderStyle:function(){var e=this.styles,t=e.borderStyle,i=e.border,n={};if(i){var a=t&&t.width||1;a=this.value2px(a);var r=t&&t.radius||5;r=this.value2px(r),n={"border-width":a,"border-style":t&&t.style||"solid","border-color":t&&t.color||"#eee","border-radius":r}}else n.border="none";var o="";for(var s in n)o+="".concat(s,":").concat(n[s],";");return o},borderLineStyle:function(){var e={},t=this.styles.borderStyle;if(t&&t.color&&(e["border-color"]=t.color),t&&t.width){var i=t&&t.width||1,n=t&&t.style||0;"number"===typeof i?i+="px":i=i.indexOf("px")?i:i+"px",e["border-width"]=i,"number"===typeof n?n+="px":n=n.indexOf("px")?n:n+"px",e["border-top-style"]=n}var a="";for(var r in e)a+="".concat(r,":").concat(e[r],";");return a}},methods:{uploadFiles:function(e,t){this.$emit("uploadFiles",{item:e,index:t})},choose:function(){this.$emit("choose")},delFile:function(e){this.$emit("delFile",e)},value2px:function(e){return"number"===typeof e?e+="px":e=-1!==e.indexOf("px")?e:e+"px",e}}};t.default=n},d917:function(e,t,i){"use strict";var n=i("8e8d"),a=i.n(n);a.a},db04:function(e,t,i){"use strict";var n=i("bb80"),a=i("c435"),r=i("9e70"),o=i("f298"),s=i("862c"),c=n(o),l=n("".slice),u=Math.ceil,d=function(e){return function(t,i,n){var o,d,f=r(s(t)),p=a(i),h=f.length,v=void 0===n?" ":r(n);return p<=h||""===v?f:(o=p-h,d=c(v,u(o/v.length)),d.length>o&&(d=l(d,0,o)),e?f+d:d+f)}};e.exports={start:d(!1),end:d(!0)}},e1ff:function(e,t,i){var n=i("c86c");t=n(!1),t.push([e.i,'.container[data-v-6d2c27ec]{min-height:100vh;font-family:alyuan}.container[data-v-6d2c27ec]::before{content:"";display:table;height:0}.view_title[data-v-6d2c27ec]{width:%?706?%;margin:%?22?% auto 0;font-size:%?39?%;font-weight:700}.eventInfo[data-v-6d2c27ec]{position:relative;background:#fff;width:%?702?%;min-height:%?911?%;margin:%?61?% auto 0;font-size:%?23?%;color:#9e9e9e;font-weight:700;border-radius:%?39?%;box-shadow:%?0?% %?0?% %?10?% rgba(0,0,0,.3);padding-bottom:%?60?%}.eventInfo[data-v-6d2c27ec]::before{content:"";display:table;height:0}.eventInfo>uni-view[data-v-6d2c27ec]{margin-left:%?35?%;margin-right:%?35?%}.eventInfo .event_id[data-v-6d2c27ec]{margin-top:%?24?%}.event_address[data-v-6d2c27ec],.evenet_remark[data-v-6d2c27ec]{margin-top:%?42?%}.evenet_desc[data-v-6d2c27ec]{margin-top:%?85?%}.event_img[data-v-6d2c27ec]{margin-top:%?85?%}.eventInfo .event_id[data-v-6d2c27ec]{font-size:%?30?%;color:#000}.event_footer[data-v-6d2c27ec]{position:absolute;bottom:0;width:%?668?%;color:#87735f;text-align:center;margin-bottom:%?20?%}.from[data-v-6d2c27ec]{width:%?710?%;margin:%?20?%;background:#fff;padding-bottom:%?20?%}.from_item[data-v-6d2c27ec]{margin:%?10?%}.from_item .btn[data-v-6d2c27ec]{border-radius:%?10?%;text-align:center;height:%?80?%;line-height:%?80?%;margin:%?10?%}.from_item .btn.default[data-v-6d2c27ec]{background:#fff;border:%?1?% solid #ccc}.from_item .btn.primary[data-v-6d2c27ec]{color:#fff;background:#007aff;border:%?1?% solid #ccc}.from_item .btn.warn[data-v-6d2c27ec]{color:#fff;background:#e64340;border:%?1?% solid #ccc}.item_label[data-v-6d2c27ec]{height:%?80?%;line-height:%?80?%}.item_value>uni-text[data-v-6d2c27ec]{text-indent:2em;margin-left:2em}.btnGroup[data-v-6d2c27ec]{width:%?702?%;margin:%?150?% auto 0}.btnGroup .btn[data-v-6d2c27ec]{height:%?118?%;border-radius:%?59?%;font-family:alyuan;font-size:%?35?%;line-height:%?118?%;text-align:center;color:#f0f0f3}.btn.toAdd[data-v-6d2c27ec]{background:#005f7a;margin-bottom:%?16?%}.btn.toList[data-v-6d2c27ec]{background:#87735f}.btn.toAudit[data-v-6d2c27ec]{background:#c80000;display:flex;justify-content:space-between}.btn.toAudit>uni-view[data-v-6d2c27ec]{text-align:center}.btn.toAudit .notapproved[data-v-6d2c27ec]{width:%?318?%}.btn.toAudit .approved[data-v-6d2c27ec]{width:%?384?%;background:#009c1a;border-radius:%?59?%}',""]),e.exports=t},e768:function(e,t,i){var n=i("c86c");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.uni-file-picker__files[data-v-1fecfc2e]{display:flex;flex-direction:column;justify-content:flex-start}.uni-file-picker__lists[data-v-1fecfc2e]{position:relative;margin-top:5px;overflow:hidden}.file-picker__mask[data-v-1fecfc2e]{display:flex;justify-content:center;align-items:center;position:absolute;right:0;top:0;bottom:0;left:0;color:#fff;font-size:14px;background-color:rgba(0,0,0,.4)}.uni-file-picker__lists-box[data-v-1fecfc2e]{position:relative}.uni-file-picker__item[data-v-1fecfc2e]{display:flex;align-items:center;padding:8px 10px;padding-right:5px;padding-left:10px}.files-border[data-v-1fecfc2e]{border-top:1px #eee solid}.files__name[data-v-1fecfc2e]{flex:1;font-size:14px;color:#666;margin-right:25px;word-break:break-all;word-wrap:break-word}.icon-files[data-v-1fecfc2e]{position:static;background-color:initial}.is-list-card[data-v-1fecfc2e]{border:1px #eee solid;margin-bottom:5px;border-radius:5px;box-shadow:0 0 2px 0 rgba(0,0,0,.1);padding:5px}.files__image[data-v-1fecfc2e]{width:40px;height:40px;margin-right:10px}.header-image[data-v-1fecfc2e]{width:100%;height:100%}.is-text-box[data-v-1fecfc2e]{border:1px #eee solid;border-radius:5px}.is-text-image[data-v-1fecfc2e]{width:25px;height:25px;margin-left:5px}.rotate[data-v-1fecfc2e]{position:absolute;-webkit-transform:rotate(90deg);transform:rotate(90deg)}.icon-del-box[data-v-1fecfc2e]{display:flex;margin:auto 0;align-items:center;justify-content:center;position:absolute;top:0;bottom:0;right:5px;height:26px;width:26px;z-index:2;-webkit-transform:rotate(-45deg);transform:rotate(-45deg)}.icon-del[data-v-1fecfc2e]{width:15px;height:1px;background-color:#333}@media (min-width:768px){.uni-file-picker__files[data-v-1fecfc2e]{max-width:375px}}',""]),e.exports=t},f298:function(e,t,i){"use strict";var n=i("497b"),a=i("9e70"),r=i("862c"),o=RangeError;e.exports=function(e){var t=a(r(this)),i="",s=n(e);if(s<0||s===1/0)throw new o("Wrong number of repetitions");for(;s>0;(s>>>=1)&&(t+=t))1&s&&(i+=t);return i}},fe67:function(e,t,i){"use strict";i("6a54");var n=i("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,i("795c"),i("c223"),i("fd3c");var a=n(i("9b1b")),r=i("7e7d"),o=i("03c2"),s={data:function(){return{formData:{},code:"",address:"",eventData:{},imgValue:[],checkNumberInArray:r.checkNumberInArray}},computed:{loginUserInfo:function(){return this.$store.state.loginUserInfo},menu:function(){return this.$store.state.menu}},methods:{showNot:function(){this.$refs.alertNot.open()},showApproved:function(){this.$refs.alertApproved.open()},StatustoString:function(e,t){var i="";if(1==t)switch(e){case 0:i="待提交";break;case 1:i="待审核";break;case 2:i="已审核";break;case 3:i="已驳回";break;case 4:i="已完成";break;default:i="";break}else switch(e){case 0:i="待提交";break;case 1:i="待审核";break;case 2:i="已审核";break;case 3:i="已驳回";break;default:i="已完成";break}return i},changeStatus:function(e){var t={};t=(0,a.default)({},this.formData);var i=new Date,n=i.getFullYear(),r=String(i.getMonth()+1).padStart(2,"0"),s=String(i.getDate()).padStart(2,"0"),c=String(i.getHours()).padStart(2,"0"),l=String(i.getMinutes()).padStart(2,"0"),u=String(i.getSeconds()).padStart(2,"0");0==e?(t.rejectName=this.loginUserInfo.name,t.rejectUser=this.loginUserInfo.username,t.rejectTime="".concat(n,"-").concat(r,"-").concat(s," ").concat(c,":").concat(l,":").concat(u),t.rejectStatus=1):1==e?(t.rejectStatus=0,t.rejectDesc=null,t.applyName=this.loginUserInfo.name,t.applyUser=this.loginUserInfo.username,t.applyTime="".concat(n,"-").concat(r,"-").concat(s," ").concat(c,":").concat(l,":").concat(u)):2==e&&(t.rejectStatus=0,t.rejectDesc=null,t.checkName=this.loginUserInfo.name,t.checkUser=this.loginUserInfo.username,t.checkTime="".concat(n,"-").concat(r,"-").concat(s," ").concat(c,":").concat(l,":").concat(u)),t.status=e,(0,o.saveOrder)(t).then((function(t){return 200!=t.data.code?uni.showToast({title:t.data.msg,icon:"none"}):(0==e?uni.showToast({title:"草稿已保存"}):uni.showToast({title:"表单已上传"}),uni.navigateBack(),400==t.data.status?uni.showToast({title:t.data.msg,icon:"none"}):void 0)}))},delAlert:function(){this.$refs.alertDialog.open()},dialogConfirm:function(){console.log("点击确认"),(0,o.delOrder)([this.formData.orderNumber]).then((function(e){200==e.data.code?(console.log(e),uni.showToast({title:"草稿已删除"}),uni.navigateBack()):uni.showToast({icon:"error",title:e.data.msg})}))},dialogClose:function(){console.log("点击关闭")},toAdd:function(e,t){var i=this.code;console.log(e,t),setTimeout((function(){uni.redirectTo({url:"../addOrder/addOrder?type="+t+"&code="+i+"&orderNumber="+e})}),300)},findData:function(){var e=this;(0,o.findEventInfo)({code:this.code}).then((function(t){console.log(t),200==t.data.code&&(e.eventData=t.data.data[0])}))},toList:function(e,t){setTimeout((function(){uni.navigateTo({url:"../orderList/orderList?code="+e+"&type="+t})}),300)}},onLoad:function(e){e.code&&(this.code=e.code,this.address=e.address)},onShow:function(){var e;this.formData=uni.getStorageSync("orderItem"),this.findData(),console.log(this.formData),this.imgValue=[],""!=this.formData.urls&&(e=this.formData.urls.split(","),console.log(this.imgValue),this.imgValue=e.map((function(e){return{url:e,name:"",extname:""}})))}};t.default=s}}]);