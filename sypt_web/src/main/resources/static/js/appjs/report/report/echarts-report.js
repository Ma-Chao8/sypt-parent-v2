$(function () {
    var resultData = getCountData()

    $("#countDay").text(resultData.countDay)

    $("#countMonth").text(resultData.countMonth)

    $("#countAll").text(resultData.countAll)


    var checkRank = resultData.checkRank
    var checkRankHtml = '';
    for (var i in checkRank){
        var item = checkRank[i];
        checkRankHtml+="<tr><td class=\"no-borders\">\n" +
            "        <i class=\"fa fa-circle text-navy\"></i>\n" +
            "        </td>\n" +
            "        <td class=\"no-borders\">"+item.name+"</td>" +
            "    </tr>"
    }
    $("#checkRank").append(checkRankHtml);

    var newestRecord = resultData.newestRecord
    var newestRecordHtml = '';
    for (var i in newestRecord){
        var item = newestRecord[i];
        newestRecordHtml+="<tr><td class=\"no-borders\">\n" +
            "        <i class=\"fa fa-circle text-navy\"></i>\n" +
            "        </td>\n" +
            "        <td class=\"no-borders\">"+item+"</td>" +
            "    </tr>"
    }
    $("#newestRecord").append(newestRecordHtml);

    $("#reportImgCountAll").text(resultData.reportImgCountAll)
    table1(resultData)
    // checkMechanismProportion(resultData)

});


function table1(resultData) {
    var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
    var pieoption = {
        title : {
            text: '检测类型占比图',
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
            data:resultData.checkTypeDOS
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:resultData.checkTypeProportion
            }
        ]
    };
    pieChart.setOption(pieoption);
    $(window).resize(pieChart.resize);

    var lineChart = echarts.init(document.getElementById("echarts-line-chart"));
    var lineoption = {
        title : {
            text: '当月检测变化'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['当月检测']
        },
        grid:{
            x:40,
            x2:40,
            y2:24
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                // data : ['周一','周二','周三','周四','周五','周六','周日']
                data : ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']

            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value}'
                }
            }
        ],
        series : [
            {
                name:'每天检测',
                type:'line',
                data: resultData.reportAnyDayCount,
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
            },
            // {
            //     name:'最低气温',
            //     type:'line',
            //     data:[1, -2, 2, 5, 3, 2, 0],
            //     markPoint : {
            //         data : [
            //             {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
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
    lineChart.setOption(lineoption);
    $(window).resize(lineChart.resize);


    var pieChart = echarts.init(document.getElementById("flot-pie-chart"));
    var pieoption = {
        title : {
            text: '检测机构占比',
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
            data:resultData.checkMechanismDOS
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:resultData.checkMechanismProportion
            }
        ]
    };
    pieChart.setOption(pieoption);
    $(window).resize(pieChart.resize);
}

function checkMechanismProportion(resultData) {
    var checkMechanismDOS = resultData.checkMechanismDOS
    var checkMechanismProportion =  resultData.checkMechanismProportion
    var data = [{
        label: "数据 1",
        data: 21,
    }, {
        label: "数据 2",
        data: 3,
    }, {
        label: "数据 3",
        data: 15,
    }, {
        label: "数据 4",
        data: 52,
    }];

    var resultArr =new Array();
    for(var i in checkMechanismDOS){
        var source = checkMechanismDOS[i]
        var resultObj = '';
        for (var j in checkMechanismProportion){
            var obj = checkMechanismProportion[j];
            if (source == obj.name){
                // result={label:"国家龙头企业",data:obj.num,color:sourcess.color}
                // sourcess.data = obj.num
                resultObj = {label:source,data:obj.value}
                resultArr.push(resultObj)
            }
        }
    }

    var plotObj = $.plot($("#flot-pie-chart"), resultArr, {
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
        url : getContext()+"/main/reportCount",
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