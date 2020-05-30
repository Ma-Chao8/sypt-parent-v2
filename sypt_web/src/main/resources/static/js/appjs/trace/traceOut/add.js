$().ready(function() {
	getReportByUpdate(null)
	getPurchaseByUpdate(null)
    getProduct(getProductId())
	getAttr(null)
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save(fieldValue) {
	var index1 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
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
		url : ctx+"/trace/traceOut/save",
		data : formData,// 你的formid
		async : false,
        contentType: false,
        processData: false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				layer.close(index1)
				layer.msg("操作成功");
				// parent.layer.msg("操作成功");
				// parent.reLoad();
				setTimeout(function () {
					parent.location.href = ctx + "trace/traceOut";
					// var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
					// parent.layer.close(index);
				},3000);

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
				required : icon + "请输入姓名"
			}
		}
	})
}