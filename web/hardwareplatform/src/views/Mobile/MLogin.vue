<template>
  <div class="bg">
    <div class="logo">
      <img src="../../assets/mobile/pic/logo.png" alt="" class="pic">
    </div>
    <div class="text">云 智 能 维 护 平 台</div>
    <form action="#">
      <div class="loginBox">
        <div class="user">
          <img src="../../assets/mobile/pic/userName.png" alt="">
          <input type="text" name="userName" placeholder="请输入您的账号" v-model="MformLogin.userName">
        </div>
        <div class="password">
          <img src="../../assets/mobile/pic/passWord.png" alt="">
          <input type="password" name="password" placeholder="请输入您的密码" v-model="MformLogin.passWord">
        </div>
        <div class="code">
          <img src="../../assets/mobile/pic/authCode.png" alt="">
          <input type="text" name="code" placeholder="请输入验证码" v-model="MformLogin.code">
          <div class="codePic" @click="refreshCode">
            <MSIdentify :identifyCode="identifyCode"></MSIdentify>
          </div>
        </div>
        <div class="btn" @click="loginClick(MformLogin.userName, MformLogin.passWord)">登&nbsp;录</div>
      </div>
    </form>
    <div class="txt">专注无线智能产品及方案</div>
  </div>
</template>
  
<script>
import MSIdentify from "../../components/MSIdentify.vue"
import apiFun from "../../network/api"
import { encode } from "../../assets/common/js/encryption"

export default {
  components: {
    MSIdentify,
  },
  data() {
    return {
      // 表单提交内容
      MformLogin: {
        userName: "",
        passWord: "",
        code: "", //验证码输入框
      },
      identifyCodes: "1234567890abcdefjhijklinopqrsduvwxyz", //随机串内容,从这里随机抽几个显示验证码
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
      this.$router.replace({ path: '/mLogin' });
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
      console.log("用户输入的验证码:", this.MformLogin.code);
      if (this.identifyCode == this.MformLogin.code.toLowerCase()) {
        console.log('验证通过')
        this.getUserLogin(userName, passWord)
      } else {
        console.log('验证失败')
        this.MformLogin.code = ''
        this.$message({
          message: '验证码错误，并请检查后重新输入!',
          type: 'warning',
          customClass: "messageDiy",
        });
      }
    },
    // 用户登陆接口
    getUserLogin(userName, passWord) {
      console.log(passWord)
      console.log(this.MformLogin.passWord)
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
              type: 'warning',
              customClass: "messageDiy",
            });
          } else if (res.code == 200) {
            localStorage.setItem('token', res.token)
            localStorage.setItem('user_data', JSON.stringify(res.result))
            this.$router.push({ path: '/mUser' })
            this.getUserInfo()
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
  }
}
</script>

<style>
@media (max-width: 730px) {
  .el-message--warning .el-message__content {
    line-height: 100px;
    font-size: 80px;
  }
}
</style>

<style scoped>
/* 登陆背景图 */
.bg {
  padding-top: 100px;
  width: 100vw;
  height: 100vh;
  background: url('../../assets/mobile/pic/bg.png');
  background-size: 100vw 100vh;
  background-repeat: no-repeat;
  background-position: center;
}

.bg .logo {
  margin: 0 auto;
  margin-top: 500px;
  margin-bottom: 50px;
  padding-left: 120px;
  text-align: center;
}

.bg .logo .pic {
  width: 500px;
}

.bg .text {
  margin-bottom: 100px;
  text-align: center;
  font-size: 200px;
  font-weight: 700;
  color: #F5F5F5;
}

/* 登录窗口 */
.loginBox {
  margin: 0 auto;
  padding: 150px;
  width: 2200px;
  min-height: 2400px;
  border-radius: 100px;
  background: rgba(40, 113, 181, .2);
}

.loginBox .user,
.password,
.code {
  padding-left: 100px;
  width: 1900px;
  height: 250px;
  line-height: 250px;
  border: 1px solid #1296DB;
  border-radius: 400px;
}

.loginBox .user,
.password {
  margin-bottom: 150px;
}

.loginBox .code {
  position: relative;
  margin-bottom: 400px;
}

.loginBox .user img,
.password img,
.code img {
  margin-right: 50px;
  width: 150px;
  height: 150px;
  vertical-align: middle;
}

.loginBox .user input,
.password input,
.code input {
  width: 1200px;
  height: 120px;
  font-size: 100px;
  background-color: transparent;
  border: none;
  color: #1296DB;
}

.loginBox .user input::placeholder,
.password input::placeholder,
.code input::placeholder {
  color: #1296DB;
}

.loginBox .user input:focus,
.password input:focus,
.code input:focus {
  outline: none;
}

.loginBox .code input {
  width: 700px;
  height: 120px;
}

.loginBox .code .codePic {
  position: absolute;
  display: inline-block;
  right: 150px;
  bottom: 55px;
}

.loginBox .btn {
  margin: 0 auto;
  border-radius: 200px;
  width: 1000px;
  height: 250px;
  text-align: center;
  line-height: 260px;
  background-color: #1296DB;
  font-size: 100px;
  color: #F5F5F5;
}

/* 底部小字 */
.bg .txt {
  margin-top: 200px;
  height: 64px;
  font-family: kaiti;
  text-align: center;
  color: #F5F5F5;
  font-size: 100px;
}
</style>