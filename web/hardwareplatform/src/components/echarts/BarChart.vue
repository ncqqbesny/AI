<template>
    <div id="vue-echarts" ref="refEcharts"> </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: 'barChart',
    data() {
        return {}
    },
    methods: {
        initCharts() {
            let myChart = echarts.init(this.$refs.refEcharts);
            myChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {},
                color: ['#2a6cf0'],
                xAxis: {
                    show: false,
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    axisTick: {
                        show: false, //不显示坐标轴刻度线
                    },
                    axisLine: {
                        show: false, //不显示坐标轴线
                    },
                    axisLabel: {
                        show: false, //不显示坐标轴上的文字
                    },
                    splitLine: {     //网格线
                        "show": false
                    },
                    data: ['4G路由器', '5G路由器', 'AC控制器', '无线AP', '无线快速切换基站', '吸顶AP', '智能保管柜', '业务办理一体机', '无线车载单元', '应急物资舱'],
                },
                series: [{
                    type: 'bar',
                    barWidth: 13,
                    data: [56, 48, 34, 70, 45, 90, 35, 89, 105, 67,],
                    label: {
                        show: true,
                        formatter: '{b}',
                        position: 'left',
                        align: 'left',
                        offset: [10, -20],
                        border: 0,
                        color: '#ffffff'
                    },
                }
                ]
            });

        }
    },
    mounted() {
        this.initCharts();
    }
}
</script>

<style scoped>
#vue-echarts {
    position: absolute;
    top: 580px;
    left: -5px;
    width: 630px;
    height: 700px;
    margin: 0 auto;
}
</style>