$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        commit();
        return false;
    }
});

var commit = function () {
    var formData = new FormData($("#dataForm")[0]);
    $.ajax({
        cache: true,
        type: "POST",
        url: ctxPath + "procedures/footlink/add",
        data: formData,// 你的formid
        async: false,
        processData: false,
        contentType: false,
        error: function (error) {
            parent.layer.alert("网络连接错误：" + error.status);
        },
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg)
            }
        }
    });
};

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#dataForm").validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入名称"
            }
        }
    })
}