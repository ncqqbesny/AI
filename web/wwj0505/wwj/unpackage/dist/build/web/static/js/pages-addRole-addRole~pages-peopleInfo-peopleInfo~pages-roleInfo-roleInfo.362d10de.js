(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-addRole-addRole~pages-peopleInfo-peopleInfo~pages-roleInfo-roleInfo"],{"0ce4":function(e,t,r){"use strict";var n=r("550f"),a=r.n(n);a.a},"1c59":function(e,t,r){"use strict";var n=r("6d61"),a=r("6566");n("Set",(function(e){return function(){return e(this,arguments.length?arguments[0]:void 0)}}),a)},"29b8":function(e,t,r){"use strict";r.d(t,"b",(function(){return n})),r.d(t,"c",(function(){return a})),r.d(t,"a",(function(){}));var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("v-uni-view",{staticClass:"uni-forms-item",class:["is-direction-"+e.localLabelPos,e.border?"uni-forms-item--border":"",e.border&&e.isFirstBorder?"is-first-border":""]},[e._t("label",[r("v-uni-view",{staticClass:"uni-forms-item__label",class:{"no-label":!e.label&&!e.required},style:{width:e.localLabelWidth,justifyContent:e.localLabelAlign}},[e.required?r("v-uni-text",{staticClass:"is-required"},[e._v("*")]):e._e(),r("v-uni-text",[e._v(e._s(e.label))])],1)]),r("v-uni-view",{staticClass:"uni-forms-item__content"},[e._t("default"),r("v-uni-view",{staticClass:"uni-forms-item__error",class:{"msg--active":e.msg}},[r("v-uni-text",[e._v(e._s(e.msg))])],1)],2)],2)},a=[]},"2bd5":function(e,t,r){"use strict";r.r(t);var n=r("75ef"),a=r.n(n);for(var i in n)["default"].indexOf(i)<0&&function(e){r.d(t,e,(function(){return n[e]}))}(i);t["default"]=a.a},"36c2":function(e,t,r){"use strict";r("7a82");var n=r("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=n(r("920b")),i=n(r("92a6")),u=n(r("f07e")),l=n(r("c964")),o=n(r("276c")),s=n(r("e954")),f=n(r("0122"));r("4d63"),r("c607"),r("ac1f"),r("2c3e"),r("25f0"),r("d3b7"),r("5319"),r("b64b"),r("e25e"),r("d401"),r("466d"),r("00b4"),r("2ca0"),r("c740"),r("6062"),r("3ca3"),r("ddb0"),r("99af"),r("c975"),r("14d9"),r("4de4"),r("e9c4");var c={email:/^\S+?@\S+?\.\S+?$/,idcard:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,url:new RegExp("^(?!mailto:)(?:(?:http|https|ftp)://|//)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$","i")},d={int:"integer",bool:"boolean",double:"number",long:"number",password:"string"};function m(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"",r=["label"];r.forEach((function(t){void 0===e[t]&&(e[t]="")}));var n=t;for(var a in e){var i=new RegExp("{"+a+"}");n=n.replace(i,e[a])}return n}var h={integer:function(e){return h.number(e)&&parseInt(e,10)===e},string:function(e){return"string"===typeof e},number:function(e){return!isNaN(e)&&"number"===typeof e},boolean:function(e){return"boolean"===typeof e},float:function(e){return h.number(e)&&!h.integer(e)},array:function(e){return Array.isArray(e)},object:function(e){return"object"===(0,f.default)(e)&&!h.array(e)},date:function(e){return e instanceof Date},timestamp:function(e){return!(!this.integer(e)||Math.abs(e).toString().length>16)},file:function(e){return"string"===typeof e.url},email:function(e){return"string"===typeof e&&!!e.match(c.email)&&e.length<255},url:function(e){return"string"===typeof e&&!!e.match(c.url)},pattern:function(e,t){try{return new RegExp(e).test(t)}catch(r){return!1}},method:function(e){return"function"===typeof e},idcard:function(e){return"string"===typeof e&&!!e.match(c.idcard)},"url-https":function(e){return this.url(e)&&e.startsWith("https://")},"url-scheme":function(e){return e.startsWith("://")},"url-web":function(e){return!1}},b=function(){function e(t){(0,o.default)(this,e),this._message=t}return(0,s.default)(e,[{key:"validateRule",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r,n,a,i){var l,o,s,f,c,d,m,h,b;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(l=null,o=r.rules,s=o.findIndex((function(e){return e.required})),!(s<0)){e.next=8;break}if(null!==n&&void 0!==n){e.next=6;break}return e.abrupt("return",l);case 6:if("string"!==typeof n||n.length){e.next=8;break}return e.abrupt("return",l);case 8:if(f=this._message,void 0!==o){e.next=11;break}return e.abrupt("return",f["default"]);case 11:c=0;case 12:if(!(c<o.length)){e.next=35;break}if(d=o[c],m=this._getValidateType(d),Object.assign(d,{label:r.label||'["'.concat(t,'"]')}),!v[m]){e.next=20;break}if(l=v[m](d,n,f),null==l){e.next=20;break}return e.abrupt("break",35);case 20:if(!d.validateExpr){e.next=26;break}if(h=Date.now(),b=d.validateExpr(n,i,h),!1!==b){e.next=26;break}return l=this._getMessage(d,d.errorMessage||this._message["default"]),e.abrupt("break",35);case 26:if(!d.validateFunction){e.next=32;break}return e.next=29,this.validateFunction(d,n,a,i,m);case 29:if(l=e.sent,null===l){e.next=32;break}return e.abrupt("break",35);case 32:c++,e.next=12;break;case 35:return null!==l&&(l=f.TAG+l),e.abrupt("return",l);case 37:case"end":return e.stop()}}),e,this)})));return function(t,r,n,a,i){return e.apply(this,arguments)}}()},{key:"validateFunction",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r,n,a,i){var l,o,s;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return l=null,e.prev=1,o=null,e.next=5,t.validateFunction(t,r,a||n,(function(e){o=e}));case 5:s=e.sent,(o||"string"===typeof s&&s||!1===s)&&(l=this._getMessage(t,o||s,i)),e.next=12;break;case 9:e.prev=9,e.t0=e["catch"](1),l=this._getMessage(t,e.t0.message,i);case 12:return e.abrupt("return",l);case 13:case"end":return e.stop()}}),e,this,[[1,9]])})));return function(t,r,n,a,i){return e.apply(this,arguments)}}()},{key:"_getMessage",value:function(e,t,r){return m(e,t||e.errorMessage||this._message[r]||t["default"])}},{key:"_getValidateType",value:function(e){var t="";return e.required?t="required":e.format?t="format":e.arrayType?t="arrayTypeFormat":e.range?t="range":void 0!==e.maximum||void 0!==e.minimum?t="rangeNumber":void 0!==e.maxLength||void 0!==e.minLength?t="rangeLength":e.pattern?t="pattern":e.validateFunction&&(t="validateFunction"),t}}]),e}(),v={required:function(e,t,r){return e.required&&function(e,t){return void 0===e||null===e||("string"===typeof e&&!e||(!(!Array.isArray(e)||e.length)||"object"===t&&!Object.keys(e).length))}(t,e.format||(0,f.default)(t))?m(e,e.errorMessage||r.required):null},range:function(e,t,r){for(var n=e.range,a=e.errorMessage,i=new Array(n.length),u=0;u<n.length;u++){var l=n[u];h.object(l)&&void 0!==l.value?i[u]=l.value:i[u]=l}var o=!1;return Array.isArray(t)?o=new Set(t.concat(i)).size===i.length:i.indexOf(t)>-1&&(o=!0),o?null:m(e,a||r["enum"])},rangeNumber:function(e,t,r){if(!h.number(t))return m(e,e.errorMessage||r.pattern.mismatch);var n=e.minimum,a=e.maximum,i=e.exclusiveMinimum,u=e.exclusiveMaximum,l=i?t<=n:t<n,o=u?t>=a:t>a;return void 0!==n&&l?m(e,e.errorMessage||r["number"][i?"exclusiveMinimum":"minimum"]):void 0!==a&&o?m(e,e.errorMessage||r["number"][u?"exclusiveMaximum":"maximum"]):void 0!==n&&void 0!==a&&(l||o)?m(e,e.errorMessage||r["number"].range):null},rangeLength:function(e,t,r){if(!h.string(t)&&!h.array(t))return m(e,e.errorMessage||r.pattern.mismatch);var n=e.minLength,a=e.maxLength,i=t.length;return void 0!==n&&i<n?m(e,e.errorMessage||r["length"].minLength):void 0!==a&&i>a?m(e,e.errorMessage||r["length"].maxLength):void 0!==n&&void 0!==a&&(i<n||i>a)?m(e,e.errorMessage||r["length"].range):null},pattern:function(e,t,r){return h["pattern"](e.pattern,t)?null:m(e,e.errorMessage||r.pattern.mismatch)},format:function(e,t,r){var n=Object.keys(h),a=d[e.format]?d[e.format]:e.format||e.arrayType;return n.indexOf(a)>-1&&!h[a](t)?m(e,e.errorMessage||r.typeError):null},arrayTypeFormat:function(e,t,r){if(!Array.isArray(t))return m(e,e.errorMessage||r.typeError);for(var n=0;n<t.length;n++){var a=t[n],i=this.format(e,a,r);if(null!==i)return i}return null}},p=function(e){(0,a.default)(r,e);var t=(0,i.default)(r);function r(e,n){var a;return(0,o.default)(this,r),a=t.call(this,r.message),a._schema=e,a._options=n||null,a}return(0,s.default)(r,[{key:"updateSchema",value:function(e){this._schema=e}},{key:"validate",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r){var n;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(n=this._checkFieldInSchema(t),n){e.next=5;break}return e.next=4,this.invokeValidate(t,!1,r);case 4:n=e.sent;case 5:return e.abrupt("return",n.length?n[0]:null);case 6:case"end":return e.stop()}}),e,this)})));return function(t,r){return e.apply(this,arguments)}}()},{key:"validateAll",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r){var n;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(n=this._checkFieldInSchema(t),n){e.next=5;break}return e.next=4,this.invokeValidate(t,!0,r);case 4:n=e.sent;case 5:return e.abrupt("return",n);case 6:case"end":return e.stop()}}),e,this)})));return function(t,r){return e.apply(this,arguments)}}()},{key:"validateUpdate",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r){var n;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(n=this._checkFieldInSchema(t),n){e.next=5;break}return e.next=4,this.invokeValidateUpdate(t,!1,r);case 4:n=e.sent;case 5:return e.abrupt("return",n.length?n[0]:null);case 6:case"end":return e.stop()}}),e,this)})));return function(t,r){return e.apply(this,arguments)}}()},{key:"invokeValidate",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r,n){var a,i,l,o,s;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:a=[],i=this._schema,e.t0=(0,u.default)().keys(i);case 3:if((e.t1=e.t0()).done){e.next=15;break}return l=e.t1.value,o=i[l],e.next=8,this.validateRule(l,o,t[l],t,n);case 8:if(s=e.sent,null==s){e.next=13;break}if(a.push({key:l,errorMessage:s}),r){e.next=13;break}return e.abrupt("break",15);case 13:e.next=3;break;case 15:return e.abrupt("return",a);case 16:case"end":return e.stop()}}),e,this)})));return function(t,r,n){return e.apply(this,arguments)}}()},{key:"invokeValidateUpdate",value:function(){var e=(0,l.default)((0,u.default)().mark((function e(t,r,n){var a,i,l;return(0,u.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:a=[],e.t0=(0,u.default)().keys(t);case 2:if((e.t1=e.t0()).done){e.next=13;break}return i=e.t1.value,e.next=6,this.validateRule(i,this._schema[i],t[i],t,n);case 6:if(l=e.sent,null==l){e.next=11;break}if(a.push({key:i,errorMessage:l}),r){e.next=11;break}return e.abrupt("break",13);case 11:e.next=2;break;case 13:return e.abrupt("return",a);case 14:case"end":return e.stop()}}),e,this)})));return function(t,r,n){return e.apply(this,arguments)}}()},{key:"_checkFieldInSchema",value:function(e){var t=Object.keys(e),n=Object.keys(this._schema);if(new Set(t.concat(n)).size===n.length)return"";var a=t.filter((function(e){return n.indexOf(e)<0})),i=m({field:JSON.stringify(a)},r.message.TAG+r.message["defaultInvalid"]);return[{key:"invalid",errorMessage:i}]}}]),r}(b);p.message=new function(){return{TAG:"",default:"验证错误",defaultInvalid:"提交的字段{field}在数据库中并不存在",validateFunction:"验证无效",required:"{label}必填",enum:"{label}超出范围",timestamp:"{label}格式无效",whitespace:"{label}不能为空",typeError:"{label}类型无效",date:{format:"{label}日期{value}格式无效",parse:"{label}日期无法解析,{value}无效",invalid:"{label}日期{value}无效"},length:{minLength:"{label}长度不能少于{minLength}",maxLength:"{label}长度不能超过{maxLength}",range:"{label}必须介于{minLength}和{maxLength}之间"},number:{minimum:"{label}不能小于{minimum}",maximum:"{label}不能大于{maximum}",exclusiveMinimum:"{label}不能小于等于{minimum}",exclusiveMaximum:"{label}不能大于等于{maximum}",range:"{label}必须介于{minimum}and{maximum}之间"},pattern:{mismatch:"{label}格式不匹配"}}};var g=p;t.default=g},"37cb":function(e,t,r){var n=r("c58d");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=r("4f06").default;a("5215aafa",n,!0,{sourceMap:!1,shadowMode:!1})},3811:function(e,t,r){"use strict";r("7a82");var n=r("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=n(r("0122")),i=n(r("f07e")),u=n(r("fc11")),l=n(r("c964"));r("a9e3"),r("14d9"),r("d3b7"),r("159b"),r("a434");var o={name:"uniFormsItem",options:{virtualHost:!0},provide:function(){return{uniFormItem:this}},inject:{form:{from:"uniForm",default:null}},props:{rules:{type:Array,default:function(){return null}},name:{type:[String,Array],default:""},required:{type:Boolean,default:!1},label:{type:String,default:""},labelWidth:{type:[String,Number],default:""},labelAlign:{type:String,default:""},errorMessage:{type:[String,Boolean],default:""},leftIcon:String,iconColor:{type:String,default:"#606266"}},data:function(){return{errMsg:"",userRules:null,localLabelAlign:"left",localLabelWidth:"65px",localLabelPos:"left",border:!1,isFirstBorder:!1}},computed:{msg:function(){return this.errorMessage||this.errMsg}},watch:{"form.formRules":function(e){this.init()},"form.labelWidth":function(e){this.localLabelWidth=this._labelWidthUnit(e)},"form.labelPosition":function(e){this.localLabelPos=this._labelPosition()},"form.labelAlign":function(e){}},created:function(){var e=this;this.init(!0),this.name&&this.form&&this.$watch((function(){var t=e.form._getDataValue(e.name,e.form.localData);return t}),(function(t,r){var n=e.form._isEqual(t,r);if(!n){var a=e.itemSetValue(t);e.onFieldChange(a,!1)}}),{immediate:!1})},destroyed:function(){this.__isUnmounted||this.unInit()},methods:{setRules:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:null;this.userRules=e,this.init(!1)},setValue:function(){},onFieldChange:function(e){var t=arguments,r=this;return(0,l.default)((0,i.default)().mark((function n(){var a,l,o,s,f,c,d,m,h,b,v,p;return(0,i.default)().wrap((function(n){while(1)switch(n.prev=n.next){case 0:if(a=!(t.length>1&&void 0!==t[1])||t[1],l=r.form,o=l.formData,l.localData,s=l.errShowType,f=l.validateCheck,c=l.validateTrigger,d=l._isRequiredField,m=l._realName,h=m(r.name),e||(e=r.form.formData[h]),b=r.itemRules.rules&&r.itemRules.rules.length,r.validator&&b&&0!==b){n.next=7;break}return n.abrupt("return");case 7:if(v=d(r.itemRules.rules||[]),p=null,"bind"!==c&&!a){n.next=18;break}return n.next=12,r.validator.validateUpdate((0,u.default)({},h,e),o);case 12:p=n.sent,v||void 0!==e&&""!==e||(p=null),p&&p.errorMessage?("undertext"===s&&(r.errMsg=p?p.errorMessage:""),"toast"===s&&uni.showToast({title:p.errorMessage||"校验错误",icon:"none"}),"modal"===s&&uni.showModal({title:"提示",content:p.errorMessage||"校验错误"})):r.errMsg="",f(p||null),n.next=19;break;case 18:r.errMsg="";case 19:return n.abrupt("return",p||null);case 20:case"end":return n.stop()}}),n)})))()},init:function(){var e=arguments.length>0&&void 0!==arguments[0]&&arguments[0],t=this.form||{},r=t.validator,n=t.formRules,i=t.childrens,u=(t.formData,t.localData),l=t._realName,o=t.labelWidth,s=t._getDataValue;t._setDataValue;if(this.localLabelAlign=this._justifyContent(),this.localLabelWidth=this._labelWidthUnit(o),this.localLabelPos=this._labelPosition(),this.form&&e&&i.push(this),r&&n){this.form.isFirstBorder||(this.form.isFirstBorder=!0,this.isFirstBorder=!0),this.group&&(this.group.isFirstBorder||(this.group.isFirstBorder=!0,this.isFirstBorder=!0)),this.border=this.form.border;var f=l(this.name),c=this.userRules||this.rules;"object"===(0,a.default)(n)&&c&&(n[f]={rules:c},r.updateSchema(n));var d=n[f]||{};this.itemRules=d,this.validator=r,this.itemSetValue(s(this.name,u))}},unInit:function(){var e=this;if(this.form){var t=this.form,r=t.childrens,n=t.formData,a=t._realName;r.forEach((function(t,r){t===e&&(e.form.childrens.splice(r,1),delete n[a(t.name)])}))}},itemSetValue:function(e){var t=this.form._realName(this.name),r=this.itemRules.rules||[],n=this.form._getValue(t,e,r);return this.form._setDataValue(t,this.form.formData,n),n},clearValidate:function(){this.errMsg=""},_isRequired:function(){return this.required},_justifyContent:function(){if(this.form){var e=this.form.labelAlign,t=this.labelAlign?this.labelAlign:e;if("left"===t)return"flex-start";if("center"===t)return"center";if("right"===t)return"flex-end"}return"flex-start"},_labelWidthUnit:function(e){return this.num2px(this.labelWidth?this.labelWidth:e||(this.label?65:"auto"))},_labelPosition:function(){return this.form&&this.form.labelPosition||"left"},isTrigger:function(e,t,r){return"submit"!==e&&e?"bind":void 0===e?"bind"!==t?t?"submit":""===r?"bind":"submit":"bind":"submit"},num2px:function(e){return"number"===typeof e?"".concat(e,"px"):e}}};t.default=o},"550f":function(e,t,r){var n=r("e5a4");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=r("4f06").default;a("850ed68c",n,!0,{sourceMap:!1,shadowMode:!1})},6062:function(e,t,r){"use strict";r("1c59")},6859:function(e,t,r){"use strict";r("7a82");var n=r("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.typeFilter=t.type=t.setDataValue=t.realName=t.rawData=t.objSet=t.objGet=t.name2arr=t.isRequiredField=t.isRealName=t.isNumber=t.isEqual=t.isBoolean=t.getValue=t.getDataValueType=t.getDataValue=t.deepCopy=void 0;var a=n(r("0122"));r("e9c4"),r("7db0"),r("d3b7"),r("a9e3"),r("13d5"),r("ac1f"),r("00b4"),r("5319"),r("d81d"),r("7039"),r("d401"),r("25f0");t.deepCopy=function(e){return JSON.parse(JSON.stringify(e))};var i=function(e){return"int"===e||"double"===e||"number"===e||"timestamp"===e};t.typeFilter=i;t.getValue=function(e,t,r){var n=r.find((function(e){return e.format&&i(e.format)})),a=r.find((function(e){return e.format&&"boolean"===e.format||"bool"===e.format}));return n&&(t=t||0===t?c(Number(t))?Number(t):t:null),a&&(t=!!d(t)&&t),t};t.setDataValue=function(e,t,r){return t[e]=r,r||""};var u=function(e,t){return f(t,e)};t.getDataValue=u;t.getDataValueType=function(e,t){var r=u(e,t);return{type:m(r),value:r}};t.realName=function(e){var t=s(e);if("object"===(0,a.default)(t)&&Array.isArray(t)&&t.length>1){var r=t.reduce((function(e,t){return e+"#".concat(t)}),"_formdata_");return r}return t[0]||e};t.isRealName=function(e){return/^_formdata_#*/.test(e)};t.rawData=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},t=JSON.parse(JSON.stringify(e)),r={};for(var n in t){var a=l(n);o(r,a,t[n])}return r};var l=function(e){var t=e.replace("_formdata_#","");return t=t.split("#").map((function(e){return c(e)?Number(e):e})),t};t.name2arr=l;var o=function(e,t,r){return"object"!==(0,a.default)(e)||s(t).reduce((function(e,t,n,a){return n===a.length-1?(e[t]=r,null):(t in e||(e[t]=/^[0-9]{1,}$/.test(a[n+1])?[]:{}),e[t])}),e),e};function s(e){return Array.isArray(e)?e:e.replace(/\[/g,".").replace(/\]/g,"").split(".")}t.objSet=o;var f=function(e,t){var r=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"undefined",n=s(t),a=n.reduce((function(e,t){return(e||{})[t]}),e);return a&&void 0===a?r:a};t.objGet=f;var c=function(e){return!isNaN(Number(e))};t.isNumber=c;var d=function(e){return"boolean"===typeof e};t.isBoolean=d;t.isRequiredField=function(e){for(var t=!1,r=0;r<e.length;r++){var n=e[r];if(n.required){t=!0;break}}return t};var m=function(e){var t={};return"Boolean Number String Function Array Date RegExp Object Error".split(" ").map((function(e,r){t["[object "+e+"]"]=e.toLowerCase()})),null==e?e+"":"object"===(0,a.default)(e)||"function"===typeof e?t[Object.prototype.toString.call(e)]||"object":(0,a.default)(e)};t.type=m;t.isEqual=function(e,t){if(e===t)return 0!==e||1/e===1/t;if(null==e||null==t)return e===t;var r=toString.call(e),n=toString.call(t);if(r!==n)return!1;switch(r){case"[object RegExp]":case"[object String]":return""+e===""+t;case"[object Number]":return+e!==+e?+t!==+t:0===+e?1/+e===1/t:+e===+t;case"[object Date]":case"[object Boolean]":return+e===+t}if("[object Object]"==r){var a=Object.getOwnPropertyNames(e),i=Object.getOwnPropertyNames(t);if(a.length!=i.length)return!1;for(var u=0;u<a.length;u++){var l=a[u];if(e[l]!==t[l])return!1}return!0}return"[object Array]"==r?e.toString()==t.toString():void 0}},"68d4":function(e,t,r){"use strict";r.r(t);var n=r("29b8"),a=r("764f");for(var i in a)["default"].indexOf(i)<0&&function(e){r.d(t,e,(function(){return a[e]}))}(i);r("0ce4");var u=r("f0c5"),l=Object(u["a"])(a["default"],n["b"],n["c"],!1,null,"7b4c51d5",null,!1,n["a"],void 0);t["default"]=l.exports},"6e2c":function(e,t,r){"use strict";r.r(t);var n=r("ab50"),a=r("2bd5");for(var i in a)["default"].indexOf(i)<0&&function(e){r.d(t,e,(function(){return a[e]}))}(i);r("fecb");var u=r("f0c5"),l=Object(u["a"])(a["default"],n["b"],n["c"],!1,null,"d3d14bf0",null,!1,n["a"],void 0);t["default"]=l.exports},7039:function(e,t,r){"use strict";var n=r("23e7"),a=r("d039"),i=r("057f").f,u=a((function(){return!Object.getOwnPropertyNames(1)}));n({target:"Object",stat:!0,forced:u},{getOwnPropertyNames:i})},"75ef":function(e,t,r){"use strict";r("7a82");var n=r("ee27").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=n(r("f07e")),i=n(r("c964")),u=n(r("fc11"));r("a9e3"),r("7db0"),r("d3b7"),r("99af"),r("159b"),r("c975"),r("e9c4"),r("14d9"),r("ddb0");var l=n(r("36c2")),o=r("6859"),s=n(r("e143"));s.default.prototype.binddata=function(e,t,r){if(r)this.$refs[r].setValue(e,t);else{var n;for(var a in this.$refs){var i=this.$refs[a];if(i&&i.$options&&"uniForms"===i.$options.name){n=i;break}}if(!n)return console.error("当前 uni-froms 组件缺少 ref 属性");n.setValue(e,t)}};var f={name:"uniForms",emits:["validate","submit"],options:{virtualHost:!0},props:{value:{type:Object,default:function(){return null}},modelValue:{type:Object,default:function(){return null}},model:{type:Object,default:function(){return null}},rules:{type:Object,default:function(){return{}}},errShowType:{type:String,default:"undertext"},validateTrigger:{type:String,default:"submit"},labelPosition:{type:String,default:"left"},labelWidth:{type:[String,Number],default:""},labelAlign:{type:String,default:"left"},border:{type:Boolean,default:!1}},provide:function(){return{uniForm:this}},data:function(){return{formData:{},formRules:{}}},computed:{localData:function(){var e=this.model||this.modelValue||this.value;return e?(0,o.deepCopy)(e):{}}},watch:{rules:{handler:function(e,t){this.setRules(e)},deep:!0,immediate:!0}},created:function(){this.childrens=[],this.inputChildrens=[],this.setRules(this.rules)},methods:{setRules:function(e){this.formRules=Object.assign({},this.formRules,e),this.validator=new l.default(e)},setValue:function(e,t){var r=this.childrens.find((function(t){return t.name===e}));return r?(this.formData[e]=(0,o.getValue)(e,t,this.formRules[e]&&this.formRules[e].rules||[]),r.onFieldChange(this.formData[e])):null},validate:function(e,t){return this.checkAll(this.formData,e,t)},validateField:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[],r=arguments.length>1?arguments[1]:void 0;t=[].concat(t);var n={};return this.childrens.forEach((function(r){var a=(0,o.realName)(r.name);-1!==t.indexOf(a)&&(n=Object.assign({},n,(0,u.default)({},a,e.formData[a])))})),this.checkAll(n,[],r)},clearValidate:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[];e=[].concat(e),this.childrens.forEach((function(t){if(0===e.length)t.errMsg="";else{var r=(0,o.realName)(t.name);-1!==e.indexOf(r)&&(t.errMsg="")}}))},submit:function(e,t,r){var n=this,a=function(e){var t=n.childrens.find((function(t){return t.name===e}));t&&void 0===n.formData[e]&&(n.formData[e]=n._getValue(e,n.dataValue[e]))};for(var i in this.dataValue)a(i);return r||console.warn("submit 方法即将废弃，请使用validate方法代替！"),this.checkAll(this.formData,e,t,"submit")},checkAll:function(e,t,r,n){var u=this;return(0,i.default)((0,a.default)().mark((function i(){var l,s,f,c,d,m,h,b,v,p,g;return(0,a.default)().wrap((function(i){while(1)switch(i.prev=i.next){case 0:if(u.validator){i.next=2;break}return i.abrupt("return");case 2:for(f in l=[],s=function(e){var t=u.childrens.find((function(t){return(0,o.realName)(t.name)===e}));t&&l.push(t)},e)s(f);r||"function"!==typeof t||(r=t),!r&&"function"!==typeof r&&Promise&&(c=new Promise((function(e,t){r=function(r,n){r?t(r):e(n)}}))),d=[],m=JSON.parse(JSON.stringify(e)),i.t0=(0,a.default)().keys(l);case 10:if((i.t1=i.t0()).done){i.next=23;break}return h=i.t1.value,b=l[h],v=(0,o.realName)(b.name),i.next=16,b.onFieldChange(m[v]);case 16:if(p=i.sent,!p){i.next=21;break}if(d.push(p),"toast"!==u.errShowType&&"modal"!==u.errShowType){i.next=21;break}return i.abrupt("break",23);case 21:i.next=10;break;case 23:if(Array.isArray(d)&&0===d.length&&(d=null),Array.isArray(t)&&t.forEach((function(e){var t=(0,o.realName)(e),r=(0,o.getDataValue)(e,u.localData);void 0!==r&&(m[t]=r)})),"submit"===n?u.$emit("submit",{detail:{value:m,errors:d}}):u.$emit("validate",d),{},g=(0,o.rawData)(m,u.name),r&&"function"===typeof r&&r(d,g),!c||!r){i.next=33;break}return i.abrupt("return",c);case 33:return i.abrupt("return",null);case 34:case"end":return i.stop()}}),i)})))()},validateCheck:function(e){this.$emit("validate",e)},_getValue:o.getValue,_isRequiredField:o.isRequiredField,_setDataValue:o.setDataValue,_getDataValue:o.getDataValue,_realName:o.realName,_isRealName:o.isRealName,_isEqual:o.isEqual}};t.default=f},"764f":function(e,t,r){"use strict";r.r(t);var n=r("3811"),a=r.n(n);for(var i in n)["default"].indexOf(i)<0&&function(e){r.d(t,e,(function(){return n[e]}))}(i);t["default"]=a.a},ab50:function(e,t,r){"use strict";r.d(t,"b",(function(){return n})),r.d(t,"c",(function(){return a})),r.d(t,"a",(function(){}));var n=function(){var e=this.$createElement,t=this._self._c||e;return t("v-uni-view",{staticClass:"uni-forms"},[t("v-uni-form",[this._t("default")],2)],1)},a=[]},c58d:function(e,t,r){var n=r("24fb");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */',""]),e.exports=t},e5a4:function(e,t,r){var n=r("24fb");t=n(!1),t.push([e.i,'@charset "UTF-8";\n/* 水平间距 */\n/* 水平间距 */.uni-forms-item[data-v-7b4c51d5]{position:relative;display:flex;margin-bottom:22px;flex-direction:row}.uni-forms-item__label[data-v-7b4c51d5]{display:flex;flex-direction:row;align-items:center;text-align:left;font-size:14px;color:#606266;height:36px;padding:0 12px 0 0;vertical-align:middle;flex-shrink:0;box-sizing:border-box}.uni-forms-item__label.no-label[data-v-7b4c51d5]{padding:0}.uni-forms-item__content[data-v-7b4c51d5]{position:relative;font-size:14px;flex:1;box-sizing:border-box;flex-direction:row}.uni-forms-item .uni-forms-item__nuve-content[data-v-7b4c51d5]{display:flex;flex-direction:column;flex:1}.uni-forms-item__error[data-v-7b4c51d5]{color:#f56c6c;font-size:12px;line-height:1;padding-top:4px;position:absolute;top:100%;left:0;transition:-webkit-transform .3s;transition:transform .3s;transition:transform .3s,-webkit-transform .3s;-webkit-transform:translateY(-100%);transform:translateY(-100%);opacity:0}.uni-forms-item__error .error-text[data-v-7b4c51d5]{color:#f56c6c;font-size:12px}.uni-forms-item__error.msg--active[data-v-7b4c51d5]{opacity:1;-webkit-transform:translateY(0);transform:translateY(0)}.uni-forms-item.is-direction-left[data-v-7b4c51d5]{flex-direction:row}.uni-forms-item.is-direction-top[data-v-7b4c51d5]{flex-direction:column}.uni-forms-item.is-direction-top .uni-forms-item__label[data-v-7b4c51d5]{padding:0 0 8px;line-height:1.5715;text-align:left;white-space:normal}.uni-forms-item .is-required[data-v-7b4c51d5]{color:#dd524d;font-weight:700}.uni-forms-item--border[data-v-7b4c51d5]{margin-bottom:0;padding:10px 0;border-top:1px #eee solid}.uni-forms-item--border .uni-forms-item__content[data-v-7b4c51d5]{flex-direction:column;justify-content:flex-start;align-items:flex-start}.uni-forms-item--border .uni-forms-item__content .uni-forms-item__error[data-v-7b4c51d5]{position:relative;top:5px;left:0;padding-top:0}.is-first-border[data-v-7b4c51d5]{border:none}',""]),e.exports=t},fecb:function(e,t,r){"use strict";var n=r("37cb"),a=r.n(n);a.a}}]);