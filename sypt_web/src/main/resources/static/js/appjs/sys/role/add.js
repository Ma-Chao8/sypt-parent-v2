//var menuTree;

var menuIds;
$(function() {
	getMenuTreeData();
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		save();
	}
});

function getAllSelectNodes() {
	var ref = $('#menuTree').jstree(true); // 获得整个树

	menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组

	$("#menuTree").find(".jstree-undetermined").each(function(i, element) {
		menuIds.push($(element).closest('.jstree-node').attr("id"));
	});
}
function getMenuTreeData() {
	$.ajax({
		type : "GET",
		url : getContext()+"/sys/menu/tree",
		success : function(menuTree) {
			loadMenuTree(menuTree);
		}
	});
}
function loadMenuTree(menuTree) {
	$('#menuTree').jstree({
		'core' : {
			'data' : menuTree
		},
		"checkbox" : {
			"three_state" : true,
		},
		"plugins" : [ "wholerow", "checkbox" ]
	});
	//$('#menuTree').jstree("open_all");

}

function save() {
	$('#menuIds').val(menuIds);
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : getContext()+"/sys/role/save",
		data : role, // 你的formid

		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

				parent.layer.close(index);

			} else {
				parent.layer.msg(data.msg);
			}
		}
	});
}

jQuery.validator.addMethod('english',function (value,element) {
	var chrnum =/^\w+$/;
	return this.optional(element) || (chrnum.test(value));
},'用户名由英文、数字或下划线组成');
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			roleName : {
				required : true
			},
			roleSign : {
				required : true,
				english : true
			}
		},
		messages : {
			roleName : {
				required : icon + "请输入角色名"
			},
			roleSign : {
				required : icon + "请输入角色签名"
			}
		}
	});
}