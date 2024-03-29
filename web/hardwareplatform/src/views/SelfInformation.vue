<template>
    <div class="content">
        <div class="userMsg" :data="companyData">
            <div class="userMsgUp">
                <div class="circle">
                    <img src="../assets/common/pic/logoY.png" alt="" class="avatar">
                </div>
                <div class="companyMsg">
                    <div class="company">
                        <span>{{ companyData.companyName }}</span>
                        <el-button text @click="companyNameDialogVisible = true">
                            <span
                                style="font-weight: 700; color: #C3A35D; text-decoration: underline; cursor: pointer;">修改</span>
                        </el-button>
                    </div>
                    <div>
                        <span class="userTime">使用：</span><span class="days">365天</span>
                    </div>
                </div>
            </div>
            <div class="userMsgDown">
                <div class="userMsgDownTitle">详细资料</div>
                <div>
                    <div class="userMsgDownLi">
                        <span class="userMsgDownTit">公司名称</span>
                        <span class="userMsgDownDetails">{{ companyData.companyName }}</span>
                        <el-button text @click="companyNameDialogVisible = true">
                            <span
                                style="font-weight: 700; color: #C3A35D; text-decoration: underline; cursor: pointer;">修改</span>
                        </el-button>
                    </div>
                    <div class="userMsgDownLi">
                        <span class="userMsgDownTit">邮箱地址</span>
                        <span class="userMsgDownDetails">{{ companyData.email }}</span>
                        <el-button text @click="companyEmailDialogVisible = true">
                            <span
                                style="font-weight: 700; color: #C3A35D; text-decoration: underline; cursor: pointer;">修改</span>
                        </el-button>
                    </div>
                    <div class="userMsgDownLi">
                        <span class="userMsgDownTit">联系方式</span>
                        <span class="userMsgDownDetails">{{ companyData.telephone }}</span>
                        <el-button text @click="companyNumberDialogVisible = true">
                            <span
                                style="font-weight: 700; color: #C3A35D; text-decoration: underline; cursor: pointer;">修改</span>
                        </el-button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 修改公司名称对话框 -->
        <el-dialog v-model="companyNameDialogVisible" title="修改公司名称" width="768px">
            <el-input v-model="companyNameInput" placeholder="请输入新的公司名称" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="companyNameDialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="companyNameVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 修改公司邮箱对话框 -->
        <el-dialog v-model="companyEmailDialogVisible" title="修改邮箱地址" width="768px">
            <el-input v-model="companyEmailInput" placeholder="请输入新的邮箱地址" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="companyEmailDialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="companyEmailVisible">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 修改公司电话对话框 -->
        <el-dialog v-model="companyNumberDialogVisible" title="修改联系方式" width="768px">
            <el-input v-model="companyNumberInput" placeholder="请输入新的联系方式" />
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="companyNumberDialogVisible = false">取消</el-button>
                    <el-button type="primary" :plain="true" @click="companyNumberVisible">
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
    name: 'selfInformation',
    data() {
        return {
            // 公司名称输入框
            companyNameInput: '',
            // 公司信息输入框
            companyEmailInput: '',
            companyNumberInput: '',
            // 公司名称修改弹窗
            companyNameDialogVisible: false,
            // 公司邮箱修改弹窗
            companyEmailDialogVisible: false,
            // 公司电话修改弹窗
            companyNumberDialogVisible: false,
            // 公司信息数据
            companyData: [],
        }
    },
    // 页面挂载时调用方法
    mounted: function () {
        //需要触发的函数
        this.queryCompany();
    },
    methods: {
        // 获取公司信息并展示
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
        },
        // 公司名称修改
        companyNameVisible() {
            if (this.companyNameInput == '') {
                this.$message({
                    message: '请输入新的公司名称！',
                    type: 'warning'
                });
            } else {
                apiFun.postModifyCompany(
                    {
                        // 发送id参数
                        userId: JSON.parse(localStorage.user_data).id,
                        companyName: this.companyNameInput
                    }
                ).then(
                    res => {
                        console.log('公司名称提交成功！')
                        console.log(res)
                        this.queryCompany()
                    }
                )
                this.companyNameDialogVisible = false,
                this.$message({
                    message: '公司名称修改成功!',
                    type: 'success'
                });
            }
        },
        // 公司邮箱修改
        companyEmailVisible() {
            if (this.companyEmailInput == '') {
                this.$message({
                    message: '请输入新的邮箱地址！',
                    type: 'warning'
                });
            } else {
                apiFun.postModifyCompany(
                    {
                        // 发送id参数
                        userId: JSON.parse(localStorage.user_data).id,
                        email: this.companyEmailInput
                    }
                ).then(
                    res => {
                        console.log('邮箱地址提交成功！')
                        console.log(res)
                        this.queryCompany()
                    }
                )
                this.companyEmailDialogVisible = false,
                this.companyNameDialogVisible = false,
                this.$message({
                    message: '邮箱地址修改成功!',
                    type: 'success'
                });
            }
        },
        // 公司电话修改
        companyNumberVisible() {
            if (this.companyNumberInput == '') {
                this.$message({
                    message: '请输入新的联系方式！',
                    type: 'warning'
                });
            } else {
                apiFun.postModifyCompany(
                    {
                        // 发送id参数
                        userId: JSON.parse(localStorage.user_data).id,
                        telephone: this.companyNumberInput
                    }
                ).then(
                    res => {
                        console.log('联系方式提交成功！')
                        console.log(res)
                        this.queryCompany()
                    }
                )
                this.companyNumberDialogVisible = false,
                this.companyNameDialogVisible = false,
                this.$message({
                    message: '联系方式修改成功!',
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

.content .userMsg {
    padding: 40px;
}

/* 个人信息内容 */
.userMsgUp {
    margin-bottom: 40px;
    width: 2100px;
    height: 140px;
    border-bottom: 1px solid #D6D8DC;
}

.userMsgUp .circle {
    display: inline-block;
    margin-right: 20px;
    text-align: center;
    line-height: 94px;
    width: 100px;
    height: 100px;
    border-radius: 50px;
    border: 1px solid #D6D8DC;
}

.userMsgUp .circle .avatar {
    width: 82px;
}

.userMsgUp .companyMsg {
    display: inline-block;
}

.userMsgUp .companyMsg .company {
    font-size: 18px;
    font-weight: 700;
    color: #333333;
}

.userMsgUp .companyMsg .userTime {
    font-size: 16px;
    color: #333333;
}

.userMsgUp .companyMsg .days {
    font-size: 16px;
    color: #C3A35D;
}

.content .userMsg .userMsgDownTitle {
    font-size: 18px;
    font-weight: 700;
    color: #333333;
    margin-bottom: 20px;
}

.content .userMsg .userMsgDownLi {
    margin-bottom: 10px;
}

.content .userMsg .userMsgDownTit {
    margin-right: 10px;
    font-size: 16px;
    color: #D6D8DC;
}

.content .userMsg .userMsgDownDetails {
    font-size: 16px;
    color: #333333;
}
</style>
