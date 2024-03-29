<template>
  <div class="common-layout">
    <el-container>
      <el-aside class="silderBar">
        <el-row class="tac">
          <el-col :span="12">
            <div class="logoBox">
                <span class="name">
                  <img src="../assets/common/pic/logoTW2.png" alt="" class="logo">
                  云智能维护平台
                </span>
            </div>
            <el-menu :default-active="$route.path" class="el-menu-vertical-demo menu" :unique-opened='true' @open="handleOpen" @close="handleClose" router>
                  <CustomElMenu v-for="(menu,i) in menuIdArray" :key="i" :item="menu"></CustomElMenu>
            </el-menu>
          </el-col>
        </el-row>
      </el-aside>
      <el-container>
        <el-header class="headerUp">
          <div class="fl">欢迎来到LINKOM云智能维护平台，今日是{{ nowDate }}{{ nowTime }}。当前软件版本为：V2022 1.1.0</div>
          <el-dropdown class="fr">
            <span class="el-dropdown-link">
              <img src="../assets/common/pic/logoY.png" alt="" class="avatar">
              <span class="username">杭州宁讯科技有限公司</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu class="pullDown">
                <el-dropdown-item class="pullDownTitle" @click="accountSetting" style="margin: 0;">
                  <span>账户设置</span>
                </el-dropdown-item>
                <el-dropdown-item class="pullDownTitle" @click="passwordChange" style="margin: 0;">
                  <span>密码修改</span>
                </el-dropdown-item>
                <el-dropdown-item class="pullDownTitle" @click="exitPlatform" style="margin: 0;">
                  <span>退出平台</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-header>
        <el-header class="headerDown">
          <div class="btnGroup">
            <el-button type="primary" @click="backView" class="iconfont btn" >返回</el-button>
          </div>
          <el-breadcrumb separator="/" class="crumbs">
            <template v-for="(item, index) in breadList">
              <el-breadcrumb-item v-if="item.name" :key="index" :to="item.path">{{ item.meta.title }}</el-breadcrumb-item>
            </template>
          </el-breadcrumb>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
        <el-footer class="footer">
          <div>专注无线智能产品及方案</div>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import apiFun from "../network/api"
import { arrToTree } from '../assets/common/js/encryption'
import CustomElMenu from '../components/customElMenu.vue'
export default {
  data() {
    return {
      timer: null,
      nowDate: '',
      nowTime: '',
      breadList: [],
      menuIdArray:[],
      menuList: [
        {
          id: 1,
          name: "课程主页",
          icon: "el-icon-menu",
          url: "/manage/workbench/index",
        },
        {
          id: 2,
          name: "课程大纲",
          icon: "el-icon-setting",
          url: "/manage/workbench/manage-course",
          children: [
            {
              id: 21,
              name: "子1",
              children: [
                {
                  id: 31,
                  name: "子2",
                },
              ],
            },
          ],
        },
        {
          id: 3,
          name: "课程管理",
          icon: "el-icon-setting",
          url: "/manage/workbench/manage-course",
        },
        {
          id: 4,
          name: "课程管理",
          icon: "el-icon-setting",
          url: "/manage/workbench/manage-course",
        },
      ],

    }
  },
  components: {
    CustomElMenu,
  },
  mounted() {
    this.timer = setInterval(() => {
      this.setNowTimes()
    }, 1000)
  },
  // 页面挂载时调用方法
  mounted: function () {
    //需要触发的函数
    this.queryMenuid();
    this.setNowTimes();
  },
  methods: {
    setNowTimes() {
      //获取当前时间
      let myDate = new Date()
      let year = String(myDate.getFullYear())
      let month = myDate.getMonth() + 1
      let day = String(myDate.getDate() < 10 ? '0' + myDate.getDate() : myDate.getDate())
      let hour = String(myDate.getHours() < 10 ? '0' + myDate.getHours() : myDate.getHours())
      let minutes = String(myDate.getMinutes() < 10 ? '0' + myDate.getMinutes() : myDate.getMinutes())
      let seconds = String(myDate.getSeconds() < 10 ? '0' + myDate.getSeconds() : myDate.getSeconds())
      this.nowDate = year + '年' + month + '月' + day + '日'
      this.nowTime = hour + ':' + minutes + ':' + seconds
    },
    getMatched() {
      this.breadList = this.$route.matched;
    },
    accountSetting() {
      this.$router.push({ path: '/userCenter' })
    },
    passwordChange() {
      this.$router.push({ path: '/userCenter' })
    },
    exitPlatform() {
      apiFun.postExit(
        this.$router.push({ path: '/login' })
      ).then(
        console.log('用户登出！')
      )
    },
    queryMenuid() {
      apiFun.getMenu(
        // 发送id参数
        JSON.parse(localStorage.user_data).id
      ).then(
        res => {
          console.log(res)
          this.menuIdArray = res.data
          this.menuIdArray = arrToTree(res.data, 0)
          console.log(this.menuIdArray)
        }
      )
    },
    arrToTree() {
      
    },
    findMenu(val){
      return this.menuIdArray.find(item => item.menuId == val) ? true :false
    },
    backView(){
      this.$router.go(-1)
    }
  },
  created() {
    this.getMatched()
    console.log(this.findMenu(1))
  },
  watch: {
    $route(to, from) {
      this.breadList = this.$route.matched;
    }
  }
}
</script>

<style scoped>
/* 侧边栏silderBar*/
.logoBox{
  width: 340px;
  display: flex;
  height: 60px;
  line-height: 60px;
  margin: 20px;
}
.silderBar {
  width: 340px;
  height: 100vh;
  background-color: #001529;
}

.toggle {
  height: 76px;
  line-height: 76px;
  color: #F1F2F6;
  text-align: center;
}

.toggle:hover {
  background-color: #0C2135;
}

.menu {
  background-color: transparent;
  border: none;
}

.logo {
  height: 40px;
  vertical-align: middle;
}

.name {
  font-size: 24px;
  font-weight: 700;
  color: #F1F2F6;
}

.itemLogo {
  cursor: default;
}

.itemLogo:hover {
  background-color: #001529 !important;
}

.itemLogo.is-active {
  background-color: #001529 !important;
}

.item {
  width: 340px;
  height: 76px;
  font-size: 16px;
  color: #F1F2F6;
  background-color: transparent;
}

.item.is-active {
  color: #F1F2F6;
  background-color: #1890FF;
}

.item.is-active:hover {
  background-color: #1890FF;
}

.item:hover {
  background-color: #0C2135;
}

.title {
  height: 76px;
}

.title:hover {
  background-color: #0C2135;
}

/* 侧边栏下拉菜单 */
.titLi {
  height: 76px !important;
  font-size: 16px;
  color: #F1F2F6;
  background-color: #0C2135;
}

.titLi.is-active {
  color: #1890FF !important;
}

.title.is-active {
  background-color: #1890FF !important;
}

.titLi:hover {
  background-color: #0C2135;
}

.sonTitle.is-active{
  background: #0C2135 !important;
}
/* 头部header */
.headerUp {
  position: relative;
  height: 76px;
  line-height: 76px;
  font-size: 16px;
  color: #999999;
  background-color: #ffffff;
  border-bottom: 1px solid #D6D8DC;
}

.headerUp .fr {
  height: 76px;
  line-height: 76px;
  font-size: 16px;
  color: #333333;
}

.headerUp .avatar {
  width: 40px;
}

.headerUp .username {
  margin-left: 10px;
  font-size: 16px;
  color: #333333;
}

.headerUp .pullDown {
  box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.3);
}

.headerDown {
  display: flex;
  height: 76px;
  line-height: 76px;
  background-color: #ffffff;
  box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}
.headerDown .btnGroup{
  height: 76px;
  margin-right: 20px;
}

.headerDown .btnGroup .btn{
  height: 40px;
    width: 80px;
    text-align: center;
    line-height: 40px;
    font-size: 16px;
}
.headerDown .crumbs {
  font-size: 16px;
  line-height: 76px;
}

/* 页脚footer */
.footer {
  position: fixed;
  bottom: 20px;
  right: 1022px;
  height: 18px;
  line-height: 18px;
  font-size: 16px;
  color: #333333;
  font-family: kaiti;
}
</style>

<!-- 全局样式 -->
<style>
.el-sub-menu {
  width: 340px;
}

.el-sub-menu:hover {
  background-color: #0C2135;
}

/* .el-menu .titLi {
  padding-left: 40px !important;
} */
.el-menu{
  width: 340px;
  background-color: #001529 !important;
}

.el-menu--inline{
  background: #0C2135 !important;
}
.el-menu-item{
  height: 76px !important;
  font-size: 20px !important;
  color: #F1F2F6 !important;
}
.el-menu-item.is-active {
  color: #409eff !important;
}
.el-menu-item:hover {
  background-color: #0C2135 !important;
}

.el-sub-menu__title {
  height: 76px !important;
  font-size: 20px !important;
  color: #F1F2F6 !important;
}

.el-sub-menu.is-active {
  background-color: #1890FF !important;
}
.el-sub-menu__title:hover {
  background-color: #0C2135 !important;
}
.el-dropdown-menu {
  width: 210px;
  min-height: 100px;
}

.el-dropdown-menu__item {
  margin-top: 80px;
  height: 40px;
  line-height: 40px;
  font-size: 16px;
  color: #333333;
}

.el-dropdown-link:focus {
  outline: none;
}

.el-dropdown-menu__item:not(.is-disabled) {
  color: #333333;
}

.el-dropdown-menu__item:not(.is-disabled):focus {
  color: #333333;
  background-color: #D6D8DC;
}</style>