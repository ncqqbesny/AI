<template>
    <div class="DataScreen">
        <el-table :data="LAndSData" height="550" style="width: 85%; position: absolute; background-color: transparent;"
            :header-cell-style="{ background: '#0b2345', color: '#70c7f4' }" ref="singleTable"
            @current-change="handleCurrentChange" :row-class-name="tableRowClassName" class="styleTable">
            <el-table-column prop="name" label="设备名称" width="120" class="tabelHead" />
            <el-table-column prop="area" label="设备位置" width="180" />
            <el-table-column prop="status" label="设备状态" />
        </el-table>
    </div>
</template>

<script>
import apiFun from '../../network/api'

export default {
    name: 'control',
    data() {
        return {
            LAndSData: [],
        }
    },
    mounted() {
        this.getLAndS()
    },
    methods: {
        //奇偶行背景色不同 
        setCurrent(row) {
            this.$refs.singleTable.setCurrentRow(row);
        },
        handleCurrentChange(val) {
            this.currentRow = val;
        },
        tableRowClassName({ row, rowIndex }) {
            if ((rowIndex + 1) % 2 === 0) {
                return 'success-row';
            }
            return '';
        },
        // 获取设备位置和状态
        getLAndS() {
            apiFun.getLAndS(
                {
                    // 获取10条数据
                    displayLine: 10,
                    // 发送mid参数
                    mId: JSON.parse(localStorage.user_mid)
                }
            ).then(
                res => {
                    console.log('设备位置和状态获取成功！')
                    console.log(res)
                    this.LAndSData = res.data
                    console.log(this.LAndSData)
                }
            )
        }
    }
}
</script>

<style scoped>
:deep(.el-table__inner-wrapper::before) {
    background: none;
}

:deep(.el-table td.el-table__cell, .el-table th.el-table__cell.is-leaf) {
    border-color: red;
}
</style>

<style>
.DataScreen {
    position: relative;
    padding-left: 20px;
}

.DataScreen .el-table {
    background-color: none !important;
}

.el-table {
    background-color: none !important;
}

/* 修改头部背景和字体颜色 */
.DataScreen .el-table th {
    background: #0b2345;
    color: #70c7f4;
    font-weight: 500;
    text-align: center;
    border-bottom: none;
}

/* 修改表头字体颜色 */
.DataScreen .el-table thead {
    font-size: 16px;
    color: #70c7f4;
    text-align: center;
}

/* 修改表格行背景 */
.DataScreen .el-table tr {
    background: #081524;
}

/* 修改表格行单元格边框 */
.DataScreen .el-table tr td {
    border: none;
}

/* 修改表格内容字体颜色 */
.DataScreen .el-table {
    color: #fff;
    text-align: center;
}

/* 修改表格内容字体居中 */
.DataScreen .el-table--enable-row-transition .el-table__body td.el-table__cell {
    text-align: center;
}

/* 取消表格hover颜色 */
.DataScreen .el-table tbody tr:hover>td {
    background-color: rgba(0, 0, 0, 0) !important
}

/* 设置表格基偶行颜色 */
.DataScreen .el-table .success-row {
    background: #0b2345;
}

/* 取消表格上下横线 */
/* .DataScreen .el-table {
    background-color: none !important;
} */


.DataScreen>>>.el-table th.is-leaf {
    /* 去除上边框 */
    border: none;
}

.DataScreen>>>.el-table::before {
    /* 去除下边框 */
    height: 0;
}
</style>
