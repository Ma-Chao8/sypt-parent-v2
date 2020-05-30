$(function () {
    var resultData = getCountData()

    $("#dayCount").text(resultData.dayCount)

    $("#monthCount").text(resultData.monthCount)

    $("#countNum").text(resultData.countNum)

    $("#countMonthByUser").text(resultData.countMonthByUser)

    table1(resultData)

    // companyRankCount(resultData)

});

function table1(resultData) {
    //图标
    // var lineChart = echarts.init(document.getElementById("echarts-line-chart"));
    // var lineoption = {
    //     title : {
    //         text: '这一周扫码量变化'
    //     },
    //     tooltip : {
    //         trigger: 'axis'
    //     },
    //     legend: {
    //         data:['最高气温','最低气温']
    //     },
    //     grid:{
    //         x:40,
    //         x2:40,
    //         y2:24
    //     },
    //     calculable : true,
    //     xAxis : [
    //         {
    //             type : 'category',
    //             boundaryGap : false,
    //             data : ['周一','周二','周三','周四','周五','周六','周日']
    //         }
    //     ],
    //     yAxis : [
    //         {
    //             type : 'value',
    //             axisLabel : {
    //                 formatter: '{value} °C'
    //             }
    //         }
    //     ],
    //     series : [
    //         {
    //             name:'最高气温',
    //             type:'line',
    //             data:[11, 11, 15, 13, 12, 13, 10],
    //             markPoint : {
    //                 data : [
    //                     {type : 'max', name: '最大值'},
    //                     {type : 'min', name: '最小值'}
    //                 ]
    //             },
    //             markLine : {
    //                 data : [
    //                     {type : 'average', name: '平均值'}
    //                 ]
    //             }
    //         },
    //         {
    //             name:'最低气温',
    //             type:'line',
    //             data:[1, -2, 2, 5, 3, 2, 0],
    //             markPoint : {
    //                 data : [
    //                     {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
    //                 ]
    //             },
    //             markLine : {
    //                 data : [
    //                     {type : 'average', name : '平均值'}
    //                 ]
    //             }
    //         }
    //     ]
    // };
    // lineChart.setOption(lineoption);
    // $(window).resize(lineChart.resize);

    var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
    var pieoption = {
        title : {
            text: '单位类别占比',
            // subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['1星企业','2星企业','3星企业','4星企业','5星企业']
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                // data:[
                //     {value:335, name:'直接访问'},
                //     {value:310, name:'邮件营销'},
                //     {value:234, name:'联盟广告'},
                //     {value:135, name:'视频广告'},
                //     {value:1548, name:'搜索引擎'}
                // ]
                data: resultData.companyCountLevelVOS
            }
        ]
    };
    pieChart.setOption(pieoption);
    $(window).resize(pieChart.resize);


    var barChart = echarts.init(document.getElementById("echarts-bar-chart"));
    var baroption = {
        title : {
            text: '月扫码量图'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['月扫码量']
        },
        grid:{
            x:30,
            x2:40,
            y2:24
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                // data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月','1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月','1月','2月','3月','4月','5月','6月','7月']
                data : ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']

            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'每日扫码量',
                type:'bar',
                data: resultData.anyDayList,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            }
            // {
            //     name:'降水量',
            //     type:'bar',
            //     data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3,2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3,2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6],
            //     markPoint : {
            //         data : [
            //             {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
            //             {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
            //         ]
            //     },
            //     markLine : {
            //         data : [
            //             {type : 'average', name : '平均值'}
            //         ]
            //     }
            // }
        ]
    };
    barChart.setOption(baroption);
    window.onresize = barChart.resize;

    var pieChartRight = echarts.init(document.getElementById("flot-pie-chart"));
    var pieoptionRight = {
        title : {
            text: '各行业占比',
            // subtext: '纯属虚构',
            text: '单位类别占比',
            // subtext: '纯属虚构',
            text: '单位类别占比',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['国家级龙头企业','省级龙头企业','市级龙头企业','区级龙头企业']
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data: resultData.companyCountRankVOS
            }
        ]
    };
    pieChartRight.setOption(pieoptionRight);
    $(window).resize(pieChartRight.resize);
}


//
function companyRankCount(resultData) {
    // getCountData()
    var companyCountRankVOS = resultData.companyCountRankVOS;
    var data = [{
        label: "国家级龙头企业",
        data: 0,
        color: "#D3CCC8",
    }, {
        label: "省级龙头企业",
        data: 0,
        color: "#9BBAA9",
    }, {
        label: "市级龙头企业",
        data: 0,
        color: "#79d2c0",
    }, {
        label: "区级龙头企业",
        data: 0,
        color: "#41D293",
    }];


    for(var i in data){
        var source = data[i]
        for (var j in companyCountRankVOS){
            var obj = companyCountRankVOS[j];
            if (source.label == obj.rank){
                // result={label:"国家龙头企业",data:obj.num,color:sourcess.color}
                source.data = obj.num
            }
        }
    }

    console.log(data)

    var plotObj = $.plot($("#flot-pie-chart"), data, {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: false
        }
    });
}


function getCountData() {
    var resultData = '';
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/main/count",
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {

            if (data.code == 0) {
                // parent.layer.msg("操作成功");
                resultData = data.data
            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
    return resultData;
}