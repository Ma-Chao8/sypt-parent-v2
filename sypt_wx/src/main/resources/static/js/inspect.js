/**
 * Created by TianMa-Android on 2019/3/4.
 */
(function (w, $, m, wx) {
    $(document).on("click", ".mui-saomiao .mui-icon-add-saomiao", function () {
        wx.scanQRCode({
            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                var result = getResult(res.resultStr); // 当needResult 为 1 时，扫码返回的结果
                var code = getBigCode(result);
                if (!code) {
                    code = getSmallCode(result);
                }
                if (!code) {
                    m.alert("您输入的不是合法的码", "提示");
                    return;
                }
                inspect(code);
            }
        });
    });

    $(document).on("click", ".mui-saomiao button[name=scan]", function () {
        inspect($(".mui-saomiao input[name=code]").val());
        $(".mui-saomiao input[name=code]").val("");
    });


    function getResult(resultStr) {
        var serial = resultStr.split(",");
        var result = serial[serial.length - 1];
        return result;
    }

    /**
     * 稽查
     * @param code
     */
    function inspect(code) {
        if (!code) {
            m.alert("请输入稽查码", "提示");
            return;
        }

        $("#inspect_info").hide();

        $.ajax({
            url: ctxPath + "invoice/inspect",
            type: "GET",
            dataType: "JSON",
            data: {code: code},
            success: function (result) {
                if (result.success) {
                    var data = result.data;
                    $("#inspect_code").text(data.inspectCode);
                    $("#product_name").text(data.productName);
                    $("#agent_name").text(data.agentName);
                    $("#linkman").text(data.linkman);
                    $("#tel").text(data.tel);
                    $("#deliver_date").text(timetrans(data.deliverDate));
                    $("#inspect_info").show();
                } else {
                    m.alert(result.data, "温馨提示");
                }
            },
            error: function (result) {
                m.alert("网络请求错误，请稍后重新操作", "提示");
            }
        })
    }

    /**
     *
     * @param code
     * @returns {*}
     */
    function getBigCode(code) {
        var code_configs = app.config.big_code_configs;
        for (var index = 0; index < code_configs.length; index++) {
            var patten = code_configs[index];
            var codes = code.match(patten);
            if (codes && codes.length > 0) {
                return codes[1]
            }
        }
        return null;
    }

    /**
     *
     * @param code
     * @returns {*}
     */
    function getSmallCode(code) {
        var code_configs = app.config.small_code_config;
        for (var index = 0; index < code_configs.length; index++) {
            var patten = code_configs[index];
            var codes = code.match(patten);
            if (codes && codes.length > 0) {
                return codes[1]
            }
        }
        return null;
    }

})(window, jQuery, mui, wx);

function timetrans(date){
    var date = new Date(date);//如果date为13位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y+M+D+h+m+s;
}