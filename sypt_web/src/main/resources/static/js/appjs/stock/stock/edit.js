$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : getContext() + "/stock/stock/update",
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
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			productName : {
				required : true
			},
			productSpec : {
				required : true
			},
			bigNumber : {
				required : true
			},
			smallNumber : {
				required : true
			}
		},
		messages : {
			productName : {
				required : icon + "请输入产品名称"
			},
			productSpec : {
				required : icon + "请输入产品规格"
			},
			bigNumber : {
				required : icon + "请输入大码库存"
			},
			smallNumber : {
				required : icon + "请输入小码库存"
			}


		}
	})
}