$().ready(function () {
    validateRule();

    $(document).on("click", "a", function () {
        switch ($(this).attr("data-option")) {
            case "themeSelect": {
                layer.open({
                    type: 2,
                    title: '主题',
                    maxmin: true,
                    shadeClose: false, // 点击遮罩关闭层
                    area: ['95%', '95%'],
                    content: ctxPath + 'procedures/procedures/theme'// iframe的url
                });
                break
            }
        }
    });

    window.onSelect = function (ele) {
        console.log(ele);
        var iconUrl = $(ele).find("img").attr("src");
        var iconId = $(ele).find("img").attr("id");
        $("#themeWrapper").empty();
        var text = '';
        text += '<input name="themeId" type="hidden" value="' + iconId + '">';
        text += '<input name="themeIcon" type="hidden" value="' + iconUrl + '">';
        text += '<img id="" name="" src="' + iconUrl + '">';
        $("#themeWrapper").append(text);

    }

});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});

function update() {
    if (!$("input[name=themeId]").val()) {
        layer.alert("请选择主题");
        return;
    }
    $.ajax({
        cache: true,
        type: "POST",
        url: getContext() + "/procedures/procedures/update",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
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

}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入名字"
            }
        }
    })
}