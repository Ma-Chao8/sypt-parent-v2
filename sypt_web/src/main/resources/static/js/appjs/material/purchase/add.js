$().ready(function() {
    getSupplierByUpdate(null);
    getMaterialByUpdate(null);
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
    var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache : false,
		type : "POST",
		url : getContext()+"/material/purchase/save",
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
            purchaser : {
                required : true
            },
            purchaseNo : {
                required : true
            }
        },
        messages : {
            purchaser : {
                required : icon + "请输入采购人姓名"
            },
            purchaseNo : {
                required : icon + "请输入采购批次"
            }
        }
	})
}