/**
 * Created by TianMa-Android on 2019/3/4.
 */
(function (w, $, m, wx) {
    m('.mui-scroll-wrapper').scroll();
    $(document).on("click", ".mui-saomiao .mui-icon-add-saomiao", function () {
        //扫码操作
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
                addCode(code)
            }
        });

    });

    $(document).on("click", ".mui-saomiao button[name=add]", function () {
        //
        addCode($(".mui-saomiao input[name=code]").val());
        $(".mui-saomiao input[name=code]").val("")
    });

    $(document).on("click", ".footer .mui-btn-clear", function () {
        m.confirm("是否清空数据", "危险警告", function (callback) {
            if (callback.index == 1) {
                clearCodeList();
            }
        });
    });

    $(document).on("click", ".footer .mui-btn-confirm", function () {
        m.confirm("是否提交数", "温馨提示", function (callback) {
            if (callback.index == 1) {
                submitData();
            }
        });
    });

    //删除扫码记录
    $(document).on("click", "#codeList ul li delete", function () {
        $(this).parents("li").remove();
    });


    function getResult(resultStr) {
        var serial = resultStr.split(",");
        var result = serial[serial.length - 1];
        return result;
    }

    /**
     * 添加码到列表
     * @param code
     */
    function addCode(code) {
        if (!code) {
            m.alert("退货码不能为空", "提示");
            return;
        }

        if (codeRepeat(code)) {
            m.alert("请勿重复扫码", "提示");
            return false;
        }

        $("#codeList ul").prepend("<li><code>" + code + "</code><delete style='cursor: pointer'>删除</delete></li>")
    }


    /**
     * 判断码是否重复
     */
    function codeRepeat(code) {
        var codes = $(document).find("#codeList ul li code");
        for (var index = 0; index < codes.length; index++) {
            if (code === $(codes[index]).text()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清空扫码列表
     */
    function clearCodeList() {
        $("#codeList ul").empty();
    }

    /**
     * 获取扫入的码
     * @returns {string}
     */
    function getCodes() {
        var codes = "";
        var lis = $(document).find("#codeList ul li");
        for (var index = 0; index < lis.length; index++) {
            var li = lis[index];
            codes += $(li).find("code").text();
            if (index != lis.length) {
                codes += ","
            }
        }

        return codes;
    }

    /**
     * 提交数据
     */
    function submitData() {
        var codes = getCodes();
        if (!codes || getCodes() === "") {
            m.alert("请添加退货码", "提示");
            return;
        }

        $.ajax({
            url: ctxPath + "invoice/returned",
            type: "POST",
            dataType: "JSON",
            data: {codes: codes},
            success: function (result) {
                if (result.success) {
                    m.toast(result.message, {duration: 'short', type: 'div'});
                    //
                    clearCodeList();
                } else {
                    m.alert(result.msg, "提示");
                }
            },
            error: function (result) {
                m.alert("网络请求错误，请稍后重新操作", "提示");
            }
        })
    }


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