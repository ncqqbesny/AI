<template>
    <div class="district">
        <div id="ringGraph" class="echartDiv" style="width: 350px;height: 200px;"></div>
    </div>
</template>
<script>
import * as echarts from 'echarts';
import { onMounted, nextTick } from 'vue';

export default {
    name: 'ringGraph',
    setup() {
        const echartInit = () => {
            var myChart = echarts.init(document.getElementById('ringGraph'));
            // 指定图表的配置项和数据
            let option = {
                tooltip: {
                    trigger: 'item'
                },
                series: [
                    {
                        name: '设备地区分布',
                        type: 'pie',
                        radius: ['60%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 70,
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: '700'
                            }
                        },
                        data: [
                            { value: 1048, name: '4G路由器', itemStyle: {color: '#2ade78'} },
                            { value: 735, name: '无线控制器', itemStyle: {color: '#3e8ccc'}  },
                            { value: 580, name: 'AC控制器', itemStyle: {color: '#dd7f3e'}  },
                            { value: 484, name: '5G路由器', itemStyle: {color: '#d8bf44'}  },
                            { value: 300, name: '无线AP', itemStyle: {color: '#a2da53'}  }
                        ],
                        normal: {
                            textStyle: {
                                color: '#ffffff'
                            }
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
        //挂载
        onMounted(() => {
            nextTick(() => {
                echartInit()
            })

        })

        return {

        };
    }
}
</script>

<style scoped>
.district {
    padding-left: 40px;
    padding-top: 20px;
}
</style>