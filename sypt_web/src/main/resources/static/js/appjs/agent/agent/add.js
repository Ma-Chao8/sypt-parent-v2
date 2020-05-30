$().ready(function() {
    //getAgentLevelByUpdate(null)
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
		url : getContext()+"/agent/agent/save",
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
            agentName : {
				required : true
			},
            linkman : {
                required : true
            },
            tel : {
                required : true
            },
            address : {
                required : true
            }
		},
		messages : {
            agentName : {
                required : icon + "请输入经销商名称"
            },
            linkman : {
                required : icon + "请输入联系人"
            },
            tel : {
                required : icon + "请输入电话号码"
            },
            address : {
                required : icon + "请输入详细地址"
            }
		}
	})
}