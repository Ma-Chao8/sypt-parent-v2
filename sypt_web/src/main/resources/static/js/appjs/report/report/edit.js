$().ready(function() {
    formatDate();
    getCheckTypeByUpdate(getCheckTypeId())
    getCheckMechanismByUpdate(getcheckMechanismId())
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
		url : getContext()+"/report/report/update",
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
            reportName : {
                required : true
            },
            batchNumber : {
                required : true
            },
            // checkBy : {
            //     required : true
            // },
            // checkType : {
            //     required : true
            // },
            checkEnterprise : {
                required : true
            },
            checkDate : {
                required : true
            }
        },
        messages : {
            reportName : {
                required : icon + "请输入名称"
            },
            batchNumber : {
                required : icon + "请输入批次"
            },
            checkBy : {
                required : icon + "请输入检测人"
            },
            // checkType : {
            //     required : icon + "请输入检测类型"
            // },
            // checkEnterprise : {
            //     required : icon + "请输入检测企业机构"
            // },
            checkDate : {
                required : icon + "请输入检测时间"
            }
        }
	})
}