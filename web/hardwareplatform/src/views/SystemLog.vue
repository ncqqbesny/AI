<template>
  <div class="main">
    <div class="activeBar">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="操作内容">
          <el-input v-model="searchForm.msg" placeholder="请输入操作内容" />
        </el-form-item>
        <el-form-item label="操作人">
          <el-input v-model="searchForm.userName" placeholder="请输入操作人" />
        </el-form-item>
        <el-form-item label="时间选择">
          <el-config-provider :locale="locale">
            <el-date-picker v-model="searchForm.searchTime" type="daterange" unlink-panels range-separator="To" start-placeholder="起始时间" end-placeholder="结束时间" :shortcuts="shortcuts" :size="size" format="YYYY/MM/DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
          </el-config-provider>
        </el-form-item>
        <el-form-item>
          <div class="btn search" @click="getLogList">查询</div>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="tableData" border style="width: 100% " class="tablebox" :row-style="{ height: '60px' }" :default-sort="{ prop: 'createTime', order: 'descending' }" :fit="true" @selection-change="handleSelectionChange">
        <el-table-column prop="logId" label="序号" class="number" width="60px" fixed> </el-table-column>
        <el-table-column prop="createTime" label="操作时间" sortable fixed class="createTime" width="140px"> </el-table-column>
        <el-table-column prop="userId" label="操作人ID" sortable fixed class="operatorId" width="100px"> </el-table-column>
        <el-table-column prop="userName" label="操作人" sortable fixed class="operator" width="80px"> </el-table-column>
        <el-table-column prop="ip" label="IP" class="operatorIp" sortable width="120px"> </el-table-column>
        <el-table-column prop="module" label="模块" sortable class="operationContent" width="120px"> </el-table-column>
        <el-table-column prop="url" label="接口" sortable class="operationContent" width="300px">
          <template #default="scope">
            <div class="leftText">
              {{ scope.row.url }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作内容" class="operationContent" align="left" width="100px">
          <template #default="scope">
            <el-popover effect="light" trigger="hover" placement="top" width="auto">
              <template #default>
                <div>1.操作内容: {{ scope.row.msg }}</div>
                <div>2.方法: {{ scope.row.method }}</div>
              </template>
              <template #reference>
                <el-tag>
                  {{Formatter(scope.row.msg) }}
                </el-tag>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="param" label="调用数据" sortable class="leftText">
          <template #default="scope">
            <div class="leftText">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div>返回值: {{ scope.row.returnValue }}</div>
                </template>
                <template #reference>
                  <el-tag>
                    <div class="leftText">
                      {{ scope.row.param }}
                    </div>
                  </el-tag>
                </template>
              </el-popover>
            </div>
          </template>
        </el-table-column>
        <!--el-table-column prop="param" label="参数" class="operationContent"> </el-table-column-->
        <!--el-table-column prop="returnValue" label="返回值" class="operationContent" :formatter="Formatter">
                </el-table-column-->
      </el-table>
    </div>
    <el-config-provider :locale="locale">
      <div class="block">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" v-model:currentPage="currentPage2" :page-sizes="[10, 30, 50, 100]" :page-size="10" layout="total, sizes, prev, pager, next" :total="total" background>
        </el-pagination>
      </div>
    </el-config-provider>
  </div>
</template>
<script>
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import apiFun from "../network/api";
import $ from "jquery";
export default {
  data() {
    return {
      locale: zhCn,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      currentPage1: 1,
      currentPage2: 1,
      currentPage3: 1,
      currentPage4: 1,
      options: [
        {
          value: "选项1",
          label: "管理员"
        },
        {
          value: "选项2",
          label: "付费角色"
        },
        {
          value: "选项3",
          label: "LINKOM角色"
        }
      ],
      tableData: [],
      value: "",
      logSelection: [],
      searchForm: {
        searchTime: "",
        msg: "",
        userName: ""
      }
    };
  },
  filters: {
    verifyReturnValueFilter(value) {
      if (value === "null") {
        return "无返回值";
      }
    }
  },
  methods: {
    // 修改页范围
    handleSizeChange(val) {
      this.pageSize = val;
      this.getLogList();
      console.log(`共 ${val} 条数据`);
    },

    Formatter(row, column) {
      if (row.indexOf("成功") != -1) {
        return "操作成功";
      } else {
        return "操作失败";
      }
    },
    // 修改页码
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getLogList();
      console.log(`当前页: ${val}`);
    },
    // 选择事件
    handleSelectionChange(val) {
      this.logSelection = val;
      console.log(this.logSelection);
    },
    // 删除日志
    delUserList() {
      let ids = [];
      this.logSelection.forEach(log => ids.push(log.logId));
      apiFun.postSystemLogDel(ids).then(res => {
        if (res.code == 200) {
          this.getLogList();
        }
      });
    },
    getLogList() {
      console.log(this.searchForm.bindTime);
      let data = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        MId: localStorage.user_mid,
        sort: 0
      };
      if (this.searchForm.searchTime != null) {
        data.createDateFrom = this.searchForm.searchTime[0];
        data.createDateTo = this.searchForm.searchTime[1];
      }
      if (this.searchForm.msg != null && this.searchForm.msg != "") {
        data.msg = "%" + this.searchForm.msg + "%";
      }
      if (this.searchForm.userName != null && this.searchForm.userName != "") {
        data.userName = "%" + this.searchForm.userName + "%";
      }
      apiFun
        .getSystemLogList(data)

        .then(res => {
          if (res.code == 200) {
            this.tableData = [];
            this.tableData = res.data.list;
            this.total = res.data.total;
          } else {
            console.log("查询系统日志列表失败", res);
          }
        });
    },
    windowResize() {
      var pageTopH = $(".headerUp").innerHeight();
      var pageBotpH = $(".headerDown").innerHeight();
      var mainH = $(window).height() - pageTopH - pageBotpH - 130;
      $(".tablebox").css("height", mainH);
      //$(".tablebox").attr("height", mainH);
    }
  },
  mounted() {
    this.getLogList();
    this.windowResize();
  }
};
</script>

<style scoped>
.main {
  padding: 20px;
  width: 2180px;
  min-height: 414px;
  background-color: #ffffff;
  box-shadow: 5px 2px 5px 1px rgba(0, 0, 0, 0.2);
}

/* 搜索栏 */
.activeBar {
  margin-bottom: 20px;
}
.leftText {
  text-align: start;
}
.activeBar .choose {
  width: 300px;
  height: 40px;
  border-radius: 0;
}

.el-input__wrapper {
  width: 300px;
  height: 40px;
  border-radius: 0;
  border: 1px solid #d6d8dc;
}

.el-select-dropdown__item {
  height: 40px;
  line-height: 40px;
  color: #333333;
}

/* 按钮样式 */
.main .btn {
  display: inline-block;
  width: 80px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  font-size: 16px;
  color: #f1f2f6;
  background-color: #c3a35d;
}

.main .search {
  margin-right: 20px;
}

.main .del {
  border-radius: 5px;
}

/* 表格样式 */
.el-table {
  font-size: 16px;
  color: #333333;
}

/* 表格宽度 */
.selection {
  width: 50px;
}
.number {
  width: 50px;
}
.operator {
  width: 405px;
}
.operatorId {
  width: 405px;
}
.operatorIp {
  width: 410px;
}
.createTime {
  width: 410px;
}
.operationContent {
  width: 410px;
}
</style>

<style>
/* 搜索框样式 */
.el-input__wrapper {
  width: 300px;
  height: 40px;
  border-radius: 0;
}

/* 表格样式 */
.el-table .cell {
  padding: 0;
  text-align: center;
}

.el-table__cell {
  padding: 0;
  height: 50px;
}

/* 分页器样式 */
.block {
  float: right;
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>