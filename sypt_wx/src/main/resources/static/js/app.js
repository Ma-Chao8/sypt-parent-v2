(function () {
    var ctxPath = "/";


})();

/**
 * Created by TianMa-Android on 2019/9/5.
 */

(function (w, $, m, wx) {
    m.init();
    if (!window.ctxPath)
        window.ctxPath = "/trace-wx/";

    w.app = {};
    w.app.config = {
        big_code_configs: [],
        small_code_config: []
    };

    $.ajax({
        url: ctxPath + "sys/code/config",
        type: "GET",
        dataType: "JSON",
        success: function (result) {
            if (result.success) {
                var config = result.data;
                w.app.config.big_code_configs = config.big_code_configs;
                w.app.config.small_code_config = config.small_code_config;
            } else {
                m.alert(result.message, "提示")
            }
        },
        error: function (data) {
            m.alert("网络请求错误，请稍后从新操作", "错误提示");
        }
    });


    function isWx() {
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (isWeixin) {
            return true;
        } else {
            return false;
        }
    }

    // $.ajax({
    //     url: ctxPath + "wx/scan/config",
    //     type: "GET",
    //     dataType: "JSON",
    //     data: {url: window.location.href},
    //     success: function (result) {
    //         if (result.success) {
    //             var data = result.data;
    //             wx.config({
    //                 debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    //                 appId: data.appId, // 必填，公众号的唯一标识
    //                 timestamp: data.timestamp, // 必填，生成签名的时间戳
    //                 nonceStr: data.nonceStr, // 必填，生成签名的随机串
    //                 signature: data.signature, // 必填，签名，见附录1
    //                 jsApiList: [
    //                     'scanQRCode'
    //                 ]// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    //             });
    //         } else {
    //             m.alert(result.message, "提示")
    //         }
    //     },
    //     error: function (data) {
    //         m.alert("网络请求错误，请稍后从新操作", "错误提示");
    //     }
    // });

    wx.error(function (res) {
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体
        //错误信息可以打开config的debug模式查看，也可以在返回的res参数中查
        //看，对于SPA可以在这里更新签名。
        m.alert(res)
    });
})(window, jQuery, mui, wx);