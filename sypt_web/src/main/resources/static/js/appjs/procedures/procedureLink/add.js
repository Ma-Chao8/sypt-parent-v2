$().ready(function() {
	// initSelect2();
	getProcedures(null)
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var selectData = getSelectedData();
	// console.log("selectData==="+selectData);
	$("#linkRoles").val = selectData
    var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache : false,
		type : "POST",
		url : getContext()+"/procedures/procedureLink/save",
		data : formData,// 你的formid
		async : false,
        contentType: false,
        processData: false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
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
		rules : {
            procedureLinkName : {
				required : true
			},
			sort : {
                required : true
			}
		},
		messages : {
            procedureLinkName : {
				required : icon + "请输入环节名称"
			},
            sort : {
                required : icon + "请输入排序值"
            }
		}
	})
}