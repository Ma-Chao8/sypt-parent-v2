$().ready(function() {
	getWarehouseNameByUpdate(null)
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : getContext() + "/warehouse/warehouseManager/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
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
jQuery.validator.addMethod("english", function(value, element) {
	//var chrnum = /^([a-zA-Z]+)$/;
	var chrnum =/^\w+$/;
	return this.optional(element) || (chrnum.test(value));
}, "只能输入字母,下划线，数字");

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			username : {
				required : true,
				english : true
			},
			realName : {
				required : true
			},

		},
		messages : {
			username : {
				required : icon + "请输入用户名",
				english : icon + "用户名由字母、下划线或者数字组成"
			},
			realName : {
				required : icon + "请输入姓名"
			},

		}
	})
}