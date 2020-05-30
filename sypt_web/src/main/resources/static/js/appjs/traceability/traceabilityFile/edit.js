$().ready(function() {
    getProduct(getProductId());
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
		url : getContext()+"/traceability/traceabilityFile/update",
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
			traceabilityFileName : {
				required : true
			},
			introduce :{
				required : true
			}
		},
		messages : {
			traceabilityFileName : {
				required : icon + "请输入溯源档案名称"
			},
			introduce :{
				required : icon + "请输入溯源介绍语"
			}
		}
	})
}