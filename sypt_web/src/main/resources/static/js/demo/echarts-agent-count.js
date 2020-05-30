$(function () {
    var resultData = getCountData()

    $("#agentDayCount").text(resultData.agentDayCount)

    $("#agentMonthCount").text(resultData.agentMonthCount)

    $("#traceFileBindCountDay").text(resultData.traceFileBindCountDay)

    $("#traceFileBindCountCount").text(resultData.traceFileBindCountCount)

    wxUserMap(resultData)

});

function wxUserMap(resultData) {
    var mapChart = echarts.init(document.getElementById("echarts-map-chart"));
    var mapoption = {
        title : {
            text: '微信用户',
            // subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            x:'left',
            data:['微信用户']
        },
        dataRange: {
            min: 0,
            max: 2500,
            x: 'left',
            y: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            x: 'right',
            y: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        roamController: {
            show: true,
            x: 'right',
            mapTypeControl: {
                'china': true
            }
        },
        series : [
            {
                name: '微信用户',
                type: 'map',
                mapType: 'china',
                roam: false,
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                },
                // data:[
                //     {name: '北京',value: Math.round(Math.random()*1000)},
                //     {name: '天津',value: Math.round(Math.random()*1000)},
                //     {name: '上海',value: Math.round(Math.random()*1000)},
                //     {name: '重庆',value: Math.round(Math.random()*1000)},
                //     {name: '河北',value: Math.round(Math.random()*1000)},
                //     {name: '河南',value: Math.round(Math.random()*1000)},
                //     {name: '云南',value: Math.round(Math.random()*1000)},
                //     {name: '辽宁',value: Math.round(Math.random()*1000)},
                //     {name: '黑龙江',value: Math.round(Math.random()*1000)},
                //     {name: '湖南',value: Math.round(Math.random()*1000)},
                //     {name: '安徽',value: Math.round(Math.random()*1000)},
                //     {name: '山东',value: Math.round(Math.random()*1000)},
                //     {name: '新疆',value: Math.round(Math.random()*1000)},
                //     {name: '江苏',value: Math.round(Math.random()*1000)},
                //     {name: '浙江',value: Math.round(Math.random()*1000)},
                //     {name: '江西',value: Math.round(Math.random()*1000)},
                //     {name: '湖北',value: Math.round(Math.random()*1000)},
                //     {name: '广西',value: Math.round(Math.random()*1000)},
                //     {name: '甘肃',value: Math.round(Math.random()*1000)},
                //     {name: '山西',value: Math.round(Math.random()*1000)},
                //     {name: '内蒙古',value: Math.round(Math.random()*1000)},
                //     {name: '陕西',value: Math.round(Math.random()*1000)},
                //     {name: '吉林',value: Math.round(Math.random()*1000)},
                //     {name: '福建',value: Math.round(Math.random()*1000)},
                //     {name: '贵州',value: Math.round(Math.random()*1000)},
                //     {name: '广东',value: Math.round(Math.random()*1000)},
                //     {name: '青海',value: Math.round(Math.random()*1000)},
                //     {name: '西藏',value: Math.round(Math.random()*1000)},
                //     {name: '四川',value: Math.round(Math.random()*1000)},
                //     {name: '宁夏',value: Math.round(Math.random()*1000)},
                //     {name: '海南',value: Math.round(Math.random()*1000)},
                //     {name: '台湾',value: Math.round(Math.random()*1000)},
                //     {name: '香港',value: Math.round(Math.random()*1000)},
                //     {name: '澳门',value: Math.round(Math.random()*1000)}
                // ]
                data: resultData.wxUserInfoVOList
            }
        ]
    };
    mapChart.setOption(mapoption);
    $(window).resize(mapChart.resize);
}


function getCountData() {
    var resultData = '';
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/main/agentMainCount",
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