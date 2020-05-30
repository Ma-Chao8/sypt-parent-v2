$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var imgValues=[];
	if ($("li img").length>1){
		$("li img").each(function(){
			var src=$(this).attr("src")
			$size=$(this).data("size")
			//把头部的data:image/png;base64,（注意有逗号）去掉
			var arr = src.split(',');
			var	mime = arr[1];
			if (mime!=undefined){
				var $item={"url":mime,"size":$size}
				imgValues.push($item)
			}
		})
	}
	var forms = new Object();
	forms.contentName = $("#contentName").val();
	forms.type = $("input[name='type']:checked").val();
	if (imgValues.length == 0){
		forms.defaultValue = $("#defaultValue").val();
	}else {
		forms.imgValues = imgValues;
	}
	forms.sort = $("#sort").val();
	forms.isView = $("#isView").val();
	forms.proceduresLinkId = $("#proceduresLinkId").val();

    console.log("forms="+JSON.stringify(forms));

	$.ajax({
		type : "POST",
		dataType: "json",
		url : getContext()+"/procedures/linkContent/save",
		data : JSON.stringify(forms),// 你的formid
		contentType: "application/json",
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
            contentName : {
				required : true
			},
            sort : {
                required : true
            }
		},
		messages : {
            contentName : {
				required : icon + "请输入名称"
			},
            sort : {
                required : icon + "请输入排序"
            }
		}
	})
}