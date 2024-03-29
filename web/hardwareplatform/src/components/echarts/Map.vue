<template>
    <div id="china_map_box">
        <div id="china_map"></div>
    </div>
</template>
   
<script>
// import echarts from "echarts";
import * as echarts from 'echarts'
// import 'echarts/map/js/china.js'
import chinajs from '../../assets/common/js/china.js'

export default {
    data() {
        return {
            //echart 配制option  
            options: {
                tooltip: {
                    triggerOn: "mousemove",   //mousemove、click
                    padding: 8,
                    borderWidth: 1,
                    borderColor: '#409eff',
                    backgroundColor: 'rgba(255,255,255,0.7)',
                    textStyle: {
                        color: '#000000',
                        fontSize: 13
                    },
                    formatter: function (e, t, n) {
                        let data = e.data;
                        //模拟数据
                        data.specialImportant = Math.random() * 500 | 0;
                        data.import = Math.random() * 500 | 0;
                        data.compare = Math.random() * 500 | 0;
                        data.common = Math.random() * 500 | 0;
                        data.specail = Math.random() * 500 | 0;

                        let context = `
                 <div>
                     <p><b style="font-size:15px;">${data.name}</b>（设备统计）</p>
                     <p class="tooltip_style"><span class="tooltip_left">AC控制器</span><span class="tooltip_right">${data.value}</span></p>
                     <p class="tooltip_style"><span class="tooltip_left">无线AP</span><span class="tooltip_right">${data.specialImportant}</span></p>
                     <p class="tooltip_style"><span class="tooltip_left">4G路由器</span><span class="tooltip_right">${data.import}</span></p>
                     <p class="tooltip_style"><span class="tooltip_left">5G路由器</span><span class="tooltip_right">${data.compare}</span></p>
                     <p class="tooltip_style"><span class="tooltip_left">应急物资舱</span><span class="tooltip_right">${data.common}</span></p>
                     <p class="tooltip_style"><span class="tooltip_left">无线车载单元单元</span><span class="tooltip_right">${data.specail}</span></p>
                 </div>
              `
                        return context;
                    }
                },
                visualMap: {
                    show: false,
                    left: 26,
                    bottom: 40,
                    showLabel: false,
                    pieces: [
                        {
                            gte: 100,
                            label: ">= 1000",
                            color: "#1e2f66"
                        },
                        {
                            gte: 500,
                            lt: 999,
                            label: "500 - 999",
                            color: "#1e2f66"
                        },
                        {
                            gte: 100,
                            lt: 499,
                            label: "100 - 499",
                            color: "#1e2f66"
                        },
                        {
                            gte: 10,
                            lt: 99,
                            label: "10 - 99",
                            color: "#1e2f66"
                        },
                        {
                            lt: 10,
                            label: '<10',
                            color: "#1e2f66"
                        }
                    ]
                },
                geo: {
                    map: "china",
                    scaleLimit: {
                        min: 1,
                        max: 2
                    },
                    zoom: 1,
                    top: 120,
                    label: {
                        normal: {
                            show: true,
                            fontSize: "14",
                            color: "rgba(255,255,255,0.7)"
                        }
                    },
                    itemStyle: {
                        normal: {
                            // shadowBlur: 10,
                            // shadowColor: 'rgba(0, 0, 0, 0.2)',
                            borderColor: "rgba(138, 190, 231)",
                            borderWidth: "1"
                        },
                        emphasis: {
                            areaColor: "#70c8f5",
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            borderWidth: 0
                        }
                    }
                },
                series: [
                    {
                        name: "突发事件",
                        type: "map",
                        geoIndex: 0,
                        data: []
                    }
                ]
            },
            //echart data
            dataList: [
                {
                    name: "南海诸岛",
                    value: 100,
                    eventTotal: 100,
                    specialImportant: 10,
                    import: 10,
                    compare: 10,
                    common: 40,
                    specail: 20
                },
                {
                    name: "北京",
                    value: 540
                },
                {
                    name: "天津",
                    value: 130
                },
                {
                    name: "上海",
                    value: 400
                },
                {
                    name: "重庆",
                    value: 750
                },
                {
                    name: "河北",
                    value: 130
                },
                {
                    name: "河南",
                    value: 830
                },
                {
                    name: "云南",
                    value: 110
                },
                {
                    name: "辽宁",
                    value: 19
                },
                {
                    name: "黑龙江",
                    value: 150
                },
                {
                    name: "湖南",
                    value: 690
                },
                {
                    name: "安徽",
                    value: 60
                },
                {
                    name: "山东",
                    value: 39
                },
                {
                    name: "新疆",
                    value: 4
                },
                {
                    name: "江苏",
                    value: 31
                },
                {
                    name: "浙江",
                    value: 104
                },
                {
                    name: "江西",
                    value: 36
                },
                {
                    name: "湖北",
                    value: 52
                },
                {
                    name: "广西",
                    value: 33
                },
                {
                    name: "甘肃",
                    value: 7
                },
                {
                    name: "山西",
                    value: 5
                },
                {
                    name: "内蒙古",
                    value: 778
                },
                {
                    name: "陕西",
                    value: 22
                },
                {
                    name: "吉林",
                    value: 4
                },
                {
                    name: "福建",
                    value: 18
                },
                {
                    name: "贵州",
                    value: 5
                },
                {
                    name: "广东",
                    value: 98
                },
                {
                    name: "青海",
                    value: 1
                },
                {
                    name: "西藏",
                    value: 0
                },
                {
                    name: "四川",
                    value: 44
                },
                {
                    name: "宁夏",
                    value: 4
                },
                {
                    name: "海南",
                    value: 22
                },
                {
                    name: "台湾",
                    value: 3
                },
                {
                    name: "香港",
                    value: 5
                },
                {
                    name: "澳门",
                    value: 555
                }
            ]
        };
    },
    methods: {
        //初始化中国地图
        initEchartMap() {
            let mapDiv = document.getElementById('china_map');
            let myChart = echarts.init(mapDiv);
            myChart.setOption(this.options);
        },
        //修改echart配制
        setEchartOption() {
            this.options.series[0]['data'] = this.dataList;
        }
    },
    created() {
        this.setEchartOption();
    },
    mounted() {
        this.$nextTick(() => {
            this.initEchartMap();
        })
    }
};
</script>
   
<style>
/* #china_map_box {
    position: absolute;
    bottom: -200px;
} */

#china_map {
    height: 850px;
}
</style>