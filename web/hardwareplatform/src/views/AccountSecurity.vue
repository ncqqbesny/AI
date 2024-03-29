<template>
    <div class="content">
        <div class="accountMsg" :data="userData">
            <div class="accountMsgTitle">账户</div>
            <div class="accountMsgLi email">
                <span class="accountMsgTit">邮箱地址</span><span class="accountMsgDetails">{{ userData.email }}</span>
                <el-button text @click="msgDialogVisible('email')" class="modify">
                    修改
                </el-button>
            </div>
            <div class="accountMsgLi number">
                <span class="accountMsgTit">联系方式</span><span class="accountMsgDetails">{{ userData.telephone }}</span>
                <el-button text @click="msgDialogVisible('number')" class="modify">
                    修改
                </el-button>
            </div>
            <div class="accountMsgLi password">
                <span class="accountMsgTit">平台密码</span><span class="accountMsgDetails">********</span>
                <el-button text @click="msgDialogVisible('password')" class="modify">
                    修改
                </el-button>
            </div>
        </div>
        <!-- 修改用户信息对话框 -->
        <el-dialog v-model="dialogVisible" :title="msgDialogTitle" width="30%" :before-close="handleClose">
            <el-input v-model="emailInput" placeholder="请输入新的邮箱地址" v-show="type == 'email'" />
            <el-input v-model="numberInput" placeholder="请输入新的联系方式" v-show="type == 'number'" />
            <el-input v-model="passwordInput" placeholder="请输入新的平台密码" v-show="type == 'password'" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="msgVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import apiFun from '../network/api';

export default {
    name: 'userCenter',
    data() {
        return {
            // 用户信息修改
            emailInput: '',
            // 用户信息弹窗
            dialogVisible: false,
            // 用户信息输入框
            numberInput: '',
            passwordInput: '',
            userData: [],
            // 类型选择
            type: '',
        }
    },
    computed: {
        msgDialogTitle() {
            return this.type == 'email' ? '修改邮箱地址' : this.type == 'number' ? '修改联系方式' : '修改平台密码'
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryUserMsg();
    },
    methods: {
        // 明文加密
        encodepassword(password, eles = []) {
            eles.push(password);
            return encode("0x12", eles);
        },
        // 修改用户信息对话框弹出控制
        msgDialogVisible(type) {
            this.type = type
            this.dialogVisible = true
        },
        // 获取用户信息并展示
        queryUserMsg() {
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
        // 用户信息修改
        msgVisible() {
            if (this.type == 'email' && this.emailInput == '' || this.type == 'number' && this.numberInput == '' || this.type == 'password' && this.passwordInput == '') {
                this.$message({
                    message: '请输入您要修改的信息！',
                    type: 'warning'
                });
            } else {
                let userData = {}
                if (this.emailInput !== '') {
                    userData.email = this.emailInput
                } if (this.numberInput !== '') {
                    userData.telephone = this.numberInput
                } if (this.passwordInput !== '') {
                    userData.password = this.encodepassword(passwordInput)
                }
                userData.userId = JSON.parse(localStorage.user_data).id,
                    userData.username = JSON.parse(localStorage.user_data).name,
                    userData.companyName = ''
                    apiFun.postModifyUserMsg(
                        userData
                    ).then(
                        res => {
                            console.log('用户信息提交成功！')
                            console.log(res)
                            this.queryUserMsg()
                        }
                    )
                this.dialogVisible = false
                this.$message({
                    message: '用户信息修改成功！!',
                    type: 'success'
                });
            }
        }
    },
}
</script>

<style scoped>
/* 右侧内容栏 */
.content {
    width: 2180;
    min-height: 400px;
    background-color: #ffffff;
    box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

.content .accountMsg {
    padding: 40px;
}

/* 账号安全内容 */
.accountMsg .accountMsgTitle {
    margin-bottom: 20px;
    font-size: 18px;
    font-weight: 700;
    color: #333333;
}

.accountMsg .accountMsgLi {
    width: 2100;
    height: 60px;
    line-height: 60px;
    border-bottom: 1px solid #D6D8DC;
}

.accountMsg .accountMsgTit {
    margin-right: 40px;
    font-size: 16px;
    color: #D6D8DC;
}

.accountMsg .accountMsgDetails {
    margin-right: 40px;
    font-size: 16px;
    color: #333333;
}

.accountMsg .modify {
    font-size: 16px;
    color: #C3A35D;
}
</style>
