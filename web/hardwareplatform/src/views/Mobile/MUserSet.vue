<template>
    <div class="bg">
        <div class="navBar">
            <div>
                <span class="back" @click="backUser">
                    返回
                </span> 
            </div>
            <span class="pageName">详细资料</span>
            <span class="save" @click="saveUserMsg">
                保存
            </span>
        </div>
        <div class="detailMsg">
            <div class="detail">
                <span class="tit">公司名称</span>
                <input type="text" class="inp" :placeholder="companyData.companyName" v-show="!showNameInput" @blur="showNameInput = false" v-model="companyInput">
                <span class="text" v-show="showNameInput" @click="showNameInput = true">{{ companyData.companyName }}</span>
            </div>
            <div class="detail">
                <span class="tit">邮箱地址</span>
                <input type="text" class="inp" :placeholder="companyData.email" v-show="!showEmailInput" @blur="showEmailInput = false" v-model="emailInput">
                <span class="text" v-show="showEmailInput" @click="showEmailInput = true">{{ companyData.email }}</span>
            </div>
            <div class="detail">
                <span class="tit">联系方式</span>
                <input type="text" class="inp" :placeholder="companyData.telephone" v-show="!showTelInput" @blur="showTelInput = false" v-model="passwordInput">
                <span class="text" v-show="showTelInput" @click="showTelInput = true">{{ companyData.telephone }}</span>
            </div>
        </div>
        <div class="txt">专注无线智能产品及方案</div>
    </div>
</template>

<script>
import apiFun from '../../network/api';

export default {
    data() {
        return {
            companyData: [],
            userData: [],
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryCompany();
    },
    methods: {
        // 跳转至用户信息页面
        backUser() {
            this.$router.push('/mUser')
        },
        // 获取用户公司信息
        queryCompany() {
            apiFun.getCompany(
                // 发送id参数
                JSON.parse(localStorage.user_data).id
            ).then(
                res => {
                    console.log('公司信息获取成功！')
                    console.log(res)
                    if (JSON.parse(localStorage.user_data).id == res.data[0].userId) {
                        this.companyData = res.data[0]
                        console.log(this.companyData)
                    }
                }
            )
            apiFun.postUserMsg(
            ).then(
                res => {
                    console.log('角色信息获取成功！')
                    console.log(res)
                    if ((localStorage.user_data).id == res.userId) {
                        this.userData = res.user
                        console.log(this.userData)
                    }
                }
            )
        },
        // 用户公司信息修改
        saveUserMsg() {
            if (this.companyInput == '' || this.emailInput == '' || this.passwordInput == '') {
                this.$message({
                    message: '请输入您要修改的信息！',
                    type: 'warning'
                });
            } else {
                let userData = {}
                if (this.companyInput !== '') {
                    userData.companyName = this.companyInput
                } if (this.emailInput !== '') {
                    userData.email = this.emailInput
                } if (this.passwordInput !== '') {
                    userData.password = this.passwordInput
                }
                userData.userId = JSON.parse(localStorage.user_data).id,
                    userData.username = JSON.parse(localStorage.user_data).name,
                    apiFun.postModifyUserMsg(
                        userData
                    ).then(
                        res => {
                            console.log('用户信息提交成功！')
                            console.log(res)
                            this.queryCompany()
                        }
                    )
                this.dialogVisible = false
                this.$message({
                    message: '用户信息修改成功！',
                    type: 'success'
                });
            }
        }
    }
}
</script>
  
<style scoped>
/* 登陆背景图 */
.bg {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: url('../../assets/mobile/pic/bg.png');
    background-size: 100vw 100vh;
    background-repeat: no-repeat;
    background-position: center;
}

/* 导航栏 */
.navBar {
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 100px;
    padding-left: 100px;
    padding-right: 100px;
    width: 100vw;
    height: 300px;
    text-align: center;
    line-height: 300px;
    background: rgba(40, 113, 181, .6);
    color: #ffffff;
}
.navBar .back {
    height: 300px;
    line-height: 300px;
    font-size: 100px;
}

.navBar img {
    vertical-align: middle;
    width: 150px;
    height: 150px;
}

.navBar .pageName {
    font-size: 120px;
    font-weight: 700;
}

.navBar .save {
    font-size: 100px;
}

/* 信息栏 */
.detailMsg {
    padding-left: 100px;
    padding-right: 100px;
    background: rgba(40, 113, 181, .6);
    color: #ffffff;
}

.detailMsg .detail {
    height: 300px;
    line-height: 300px;
    border-bottom: 1px solid #2871B5;
}

.detailMsg .detail:last-child {
    border-bottom: none;
}

.detailMsg .detail .tit {
    margin-right: 100px;
    font-size: 100px;
    color: #D6D8DC;
}

.detailMsg .detail .inp {
    background: none;
    border: none;
    outline: none;
    font-size: 100px;
    color: #ffffff;
}

.detailMsg .detail .inp:focus {
    border: none;
}

.detailMsg .detail .inp::placeholder {
  color: #ffffff;
}

.detailMsg .detail .text {
    font-size: 100px;
    color: #ffffff;
}

/* 底部小字 */
.bg .txt {
    margin-top: 100px;
    font-family: kaiti;
    text-align: center;
    color: #BBBBBB;
    font-size: 100px;
}
</style>