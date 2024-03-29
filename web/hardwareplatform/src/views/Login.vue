<template>
    <div class="bg">
        <img src="../assets/common/pic/logoTW2.png" alt="" class="logo">
        <div class="win">
            <div class="fr login" ref="login">
                <div class="companyTitle">宁讯科技-专注无线智能产品及方案</div>
                <div class="title">云&nbsp;智&nbsp;能&nbsp;维&nbsp;护&nbsp;平&nbsp;台</div>
                <form action="#" class="main">
                    <div class="please userName">
                        <img src="../assets/common/icons/userName.png" alt="" class="icons">
                        <input type="text" name="userName" placeholder="请键入您的账号" class="input" v-model="formLogin.userName">
                    </div>
                    <div class="please passWord">
                        <img src="../assets/common/icons/passWord.png" alt="" class="icons">
                        <input type="password" name="passWord" placeholder="请键入您的密码" class="input"
                            v-model="formLogin.passWord">
                    </div>
                    <!-- <div class="please" v-show="regModel">
                        <img src="../assets/common/icons/passWord.png" alt="" class="icons">
                        <input type="password" name="passWord" placeholder="请再次键入您的密码" class="input" v-model="formLogin.passWord">
                    </div> -->
                    <div class="please authCode">
                        <img src="../assets/common/icons/authCode.png" alt="" class="icons">
                        <input type="text" name="code" placeholder="请键入验证码" class="input codeInput"
                            v-model="formLogin.code">
                        <div class="codePic" @click="refreshCode">
                            <dentify :identifyCode="identifyCode"></dentify>
                        </div>
                    </div>
                    <div class="btn loginBtn" @click="loginClick(formLogin.userName, formLogin.passWord)">
                        登&nbsp;&nbsp;&nbsp;&nbsp;录</div>
                    <div class="toSign" @click="toCompany">没有账号？去注册=></div>
                </form>
            </div>
            <form action="#">
                <div class="fr company" ref="company">
                    <div class="title">请输入您的公司信息</div>
                    <div class="main">
                        <div class="please">
                            <img src="../assets/common/icons/company.png" alt="" class="icons">
                            <input type="text" name="companyName" placeholder="请键入您公司的名称" class="input"
                                v-model="formSign.companyName">
                        </div>
                        <div class="please">
                            <img src="../assets/common/icons/email.png" alt="" class="icons">
                            <input type="text" name="companyEmail" placeholder="请键入您公司的邮箱地址" class="input"
                                v-model="formSign.companyEmail">
                        </div>
                        <div class="please">
                            <img src="../assets/common/icons/phone.png" alt="" class="icons">
                            <input type="tel" name="companyPhone" oninput="value=value.replace(/[^\d]/g,'')" maxlength="11"
                                placeholder="输入您公司的联系方式" class="input" v-model="formSign.companyPhone">
                        </div>
                        <div class="btn loginBtn" @click="toUser">下一步</div>
                        <div class="toSign" @click="tologin">已有账号？去登录=></div>
                    </div>
                </div>
                <div class="fr user" ref="user">
                    <div class="title">请输入您的用户信息</div>
                    <div class="main">
                        <div class="please">
                            <img src="../assets/common/icons/userName.png" alt="" class="icons">
                            <input type="text" name="userName" placeholder="请键入您的用户名" class="input"
                                v-model="formSign.userName">
                        </div>
                        <div class="please">
                            <img src="../assets/common/icons/name.png" alt="" class="icons">
                            <input type="text" name="name" placeholder="请键入您的姓名" class="input" v-model="formSign.name">
                        </div>
                        <div class="please">
                            <img src="../assets/common/icons/passWord.png" alt="" class="icons">
                            <input type="password" name="signPassWord" placeholder="请键入您的密码" class="input"
                                v-model="formSign.signPassWord">
                        </div>
                        <div class="please">
                            <img src="../assets/common/icons/phone.png" alt="" class="icons">
                            <input type="tel" name="phone" oninput="value=value.replace(/[^\d]/g,'')" maxlength="11"
                                placeholder="请键入您的联系方式" class="input" v-model="formSign.phone">
                        </div>
                        <el-button class="btn" :plain="true" @click="signClick">注&nbsp;&nbsp;&nbsp;&nbsp;册</el-button>
                        <div class="backAndAllback">
                            <span class="goback" @click="toCompany">上一步</span>
                            <span class="toSign" @click="tologin">已有账号？去登录=></span>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <span class="brand">©2022 LINKOM 杭州宁讯信息科技有限公司 当前版本：V2022 1.1.0</span>
    </div>
</template>

<script>
import dentify from "../components/SIdentify.vue"
import apiFun from "../network/api"
import { encode } from "../assets/common/js/encryption"
export default {
    name: 'Login',
    components: {
        dentify,
    },
    data() {
        return {
            // 表单提交内容
            formLogin: {
                userName: "",
                passWord: "",
                code: "", //验证码输入框
            },
            formSign: {
                companyName: "",
                companyEmail: "",
                companyPhone: "",
                userName: "",
                name: "",
                signPassWord: "",
                phone: "",
            },
            identifyCodes: "1234567890", //随机串内容,从这里随机抽几个显示验证码
            identifyCode: "", //验证码图片内容
        }
    },
    mounted() {
        // 初始化验证码
        this.identifyCode = "";
        //参数：（1）随机串内容。（2）验证码显示位数
        this.makeCode(this.identifyCodes, 4);
        // 判断浏览设备跳转路由
        if (this._isMobile()) {
            // 跳转至手机端路由
            console.log('欢迎进入移动版宁讯科技云智能维护平台！');
            this.$router.push({ path: '/mLogin' });
            console.log('移动版智能维护平台！');
        } else {
            // 跳转至 pc 端路由
            console.log('欢迎进入PC版宁讯科技云智能维护平台！')
            this.$router.replace("/login");
        }
    },
    methods: {
        // 判断浏览设备
        _isMobile() {
            let flag = navigator.userAgent.match(
                /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
            );
            return flag;
        },
        // 重置验证码
        refreshCode() {
            this.identifyCode = "";
            this.makeCode(this.identifyCodes, 4);
        },
        //获取验证码的值
        makeCode(o, l) {
            for (let i = 0; i < l; i++) {
                //通过循环获取字符串内随机几位
                this.identifyCode +=
                    this.identifyCodes[this.randomNum(0, this.identifyCodes.length)];
            }
        },
        //随机数字：用于当角标拿字符串的值
        randomNum(min, max) {
            return Math.floor(Math.random() * (max - min) + min);
        },
        // 明文加密
        encodepassword(password, eles = []) {
            eles.push(password);
            return encode("0x12", eles);
        },
        // 验证登录
        loginClick(userName, passWord) {
            console.log("验证码:", this.identifyCode);
            console.log("用户输入的验证码:", this.formLogin.code);           
            if (this.identifyCode == this.formLogin.code.toLowerCase()) {
                console.log('验证通过')
                if (!navigator.onLine){
                this.$message({
                    message: '网络断开了，请检查网线!',
                    type: 'warning'
                });
                console.log("网络坏掉了:", this.formLogin.code);
                }
                this.getUserLogin(userName, passWord)
            } else {
                console.log('验证失败')
                this.formLogin.code = ''
                this.$message({
                    message: '验证码错误，并请检查后重新输入!',
                    type: 'warning'
                });
            }
        },
        // 验证注册
        signClick() {
            if (this.formSign.companyName == '' || this.formSign.companyEmail == '' || this.formSign.companyEmail == '' || this.formSign.userName == '' || this.formSign.name == '' || this.formSign.signPassWord == '' || this.formSign.phone == '') {
                this.$message({
                    message: '请完善所有信息！',
                    type: 'warning'
                });
            } else {
                this.getUserSign()

            }
        },
        // 用户登陆接口
        getUserLogin(userName, passWord) {
            console.log(passWord)
            console.log(this.formLogin.passWord)
            apiFun.postLogin(
                {
                    username: userName,
                    password: this.encodepassword(passWord)
                }
            ).then(
                res => {
                    console.log('点击登录的接口返回结果', res)
                    if (res.status == 400) {
                        this.$message({
                            message: '账号或密码错误，请检查后重试！',
                            type: 'warning'
                        });
                    } else if (res.code == 200) {
                        localStorage.setItem('token', res.token)
                        localStorage.setItem('user_data', JSON.stringify(res.result))
                        this.$router.push({ path: '/DataScreen' })
                        this.getUserInfo()
                    }
                }
            )
        },
        // 用户注册接口
        getUserSign() {
            apiFun.postReg(
                {
                    companyName: this.formSign.companyName,
                    companyEmail: this.formSign.companyEmail,
                    companyTel: this.formSign.companyPhone,
                    username: this.formSign.userName,
                    name: this.formSign.name,
                    password: this.encodepassword(this.formSign.signPassWord),
                    telephone: this.formSign.phone,
                    roleIds: [],
                    roles: [
                        {
                            createTime: "",
                            description: "",
                            mId: 0,
                            menuIds: "",
                            roleId: 1,
                            roleName: "svip",
                            roleStatus: "",
                            updateTime: ""
                        }
                    ],
                }
            ).then(
                res => {
                    console.log('点击注册的接口返回结果', res)
                    if (res.status == 400) {
                        this.$message({
                            message: '注册失败，请联系管理员！',
                            type: 'warning'
                        });
                    } else if (res.code == 200) {
                        localStorage.setItem('token', res.token)
                        localStorage.setItem('user_data', JSON.stringify(res.result))
                        this.$router.push({ path: '/DataScreen' })
                        this.getUserLogin(this.formSign.userName, this.formSign.signPassWord)
                        this.getUserInfo()
                        this.$message({
                            message: '注册成功！',
                            type: 'success'
                        });
                    } else {
                        this.$message({
                            message: '已存在账号，请更换别一个用户名进行注册！',
                            type: 'warning'
                        });
                    }
                }
            )
        },
        // 登陆后获取用户信息
        getUserInfo() {
            apiFun.postUserInfo().then(
                res => {
                    if (res.code == 200) {
                        localStorage.setItem('user_mid', res.user.mid)
                    }
                }
            )
        },
        // 登录与注册窗口切换
        toCompany() {
            this.$refs.login.style.display = 'none'
            this.$refs.user.style.display = 'none'
            this.$refs.company.style.display = 'block'
        },
        toUser() {
            this.$refs.company.style.display = 'none'
            this.$refs.user.style.display = 'block'
        },
        tologin() {
            this.$refs.company.style.display = 'none'
            this.$refs.user.style.display = 'none'
            this.$refs.login.style.display = 'block'
        }
    }
} 
</script>

<style scoped>
.bg {
    position: relative;
    width: 100vw;
    height: 100vh;
    background-image: url("../assets/common/pic/loginBG.png");
    background-size: 100vw 100vh;
    background-repeat: no-repeat;
    background-position: center;
}

.bg .brand {
    position: fixed;
    bottom: 20px;
    left:50%;
    transform:translateX(-50%);
    color: #F5F5F5;
    font-size: 14px;
}

.logo {
    position: fixed;
    top: 100px;
    left: 100px;
    height: 90px;
}

.win {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 1000px;
    height: 540px;
}

.win .text {
    margin-top: 200px;
}

.win .fr {
    position: fixed;
    bottom: 20px;
    left:50%;
    transform:translateX(-50%);
    width: 600px;
    height: 540px;
    background: rgba(12, 33, 53, 0.9);
}

.win .company,
.user,
.project {
    display: none;
}

.win .fr .changeCards {
    display: flex;
    justify-content: space-between;
}

.win .fr .changeCards .card {
    flex: 1;
    text-align: center;
    height: 48px;
    line-height: 32px;
    font-size: 24px;
    margin: 12px;
    color: #2871B5;
}

.win .fr .changeCards .card.active {
    color: #1296DB;
    border-bottom: #1296DB 2px solid;
}

.win .fr .companyTitle {
    margin-top: 40px;
    font-size: 32px;
    color: #f5f5f5;
    font-weight: 700;
    text-align: center;
}

.win .fr .title {
    margin-top: 20px;
    margin-bottom: 40px;
    font-size: 32px;
    color: #f5f5f5;
    font-weight: 700;
    text-align: center;
}

.win .fr .main {
    padding-left: 20px;
}

.win .fr .please {
    margin: 0 auto;
    margin-bottom: 20px;
    width: 400px;
    height: 42px;
    border-bottom: 1px solid #1296DB;
}

.win .fr .icons {
    width: 30px;
    height: 30px;
}

.win .fr .input {
    padding-left: 10px;
    width: 200px;
    height: 36px;
    font-size: 14px;
    color: #1296DB;
    background-color: transparent;
    border: none;
}

.win .fr .input::placeholder {
    color: #1296DB;
}

.win .fr .codeInput {
    width: 150px;
}

.win .fr .input:focus {
    outline: none;
}

.win .fr .authCode {
    margin-bottom: 10px;
}

.win .fr .codePic {
    position: absolute;
    display: inline-block;
    right: 90px;
    bottom: 180px;
}

.win .fr .btn {
    cursor: pointer;
    margin: 0 auto;
    margin-top: 10px;
    margin-bottom: 20px;
    width: 360px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    font-size: 14px;
    color: #F5F5F5;
    background-color: #1296DB;
    border: none;
    border-radius: 5px;
}

.win .fr .loginBtn {
    margin-top: 30px;
}

.win .fr .btn .write {
    color: #C3A35D;
    background: #F5F5F5;
}

.toSign {
    cursor: pointer;
    text-align: center;
    font-size: 14px;
    color: #1296DB;
}

.backAndAllback {
    text-align: center;
}

.goback {
    margin-right: 20px;
    cursor: pointer;
    font-size: 14px;
    color: #1296DB;
}
</style>
