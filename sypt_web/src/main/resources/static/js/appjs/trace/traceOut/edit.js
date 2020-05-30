$().ready(function() {
    getReport(getReportId(),true)
    getPurchase(getPurchaseId(),true)
    getProduct(getProductId())
    // getAttr(null)
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update(fieldValue) {
    var formData = new FormData($("#signupForm")[0]);
    if (fieldValue!=null){
        for(var i in fieldValue){
            formData.append("traceOutFieldDOList["+[i]+"].attrFieldId",fieldValue[i].attrFieldId)
            formData.append("traceOutFieldDOList["+[i]+"].values",fieldValue[i].values)
            formData.append("traceOutFieldDOList["+[i]+"].attrId",fieldValue[i].attrId)
        }
    }
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx+"/trace/traceOut/update",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}