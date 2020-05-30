(function () {
    var ctxPath = $("meta[name=ctxPath]").attr("content");


    /**
     * 提交表单
     */
    $("form[name=codeOutAdd]").submit(function () {
        if (!$("input[name=companyName]").val()) {
            layer.alert("请选择商户");
            return false;
        }


        $.ajax({
            url: ctxPath + "code/out/record/add",
            type: "POST",
            dataType: "JSON",
            data: $("form[name=codeOutAdd]").serialize(),
            success: function (result) {
                if (result.success) {
                    parent.layer.msg("操作成功");
                    parent.reload();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);

                } else {
                    layer.alert(result.msg)
                }
            },
            error: function (error) {
                layer.alert("网络链接失败：" + error.status)
            }
        });

        //
        return false;
    });

    $("select[name=companyId]").change(function () {
        //console.log($(this).val(), $(this).find("option:selected").text())
        if ($(this).val()) {
            $("input[name=companyName]").val($(this).find("option:selected").text())
        } else {
            $("input[name=companyName]").val("")
        }
    });


})();