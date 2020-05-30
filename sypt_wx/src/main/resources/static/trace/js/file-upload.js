function uploadFile(url, submit_id) {
    $.ajaxFileUpload({
        type: "POST",
        url: url,
        secureuri: false,//是否启用安全提交，默认为false
        fileElementId: [submit_id],//文件选择框的id属性
        async: false,
        dataType: "json",
        success: function (data) {
            console.log(data)
            if (data.success) {
                layer.msg(data.message, {
                    icon: 1,
                    time: 1000
                }, function () {
                    location.reload();
                });
            } else {
                layer.msg(data.message);
            }
        },
        error: function (data, status, errorMsg) {
            layer.msg(errorMsg);
        }
    });
}