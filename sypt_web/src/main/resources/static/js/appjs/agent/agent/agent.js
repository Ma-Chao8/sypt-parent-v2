
var prefix = getContext()+"/agent/agent"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
						//showRefresh : true,
						//showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						sortable: true, //是否启用排序
						sortName : 'createDate',//初始化的时候排序的字段
						sortOrder: 'desc', //排序方式
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParamsType : "",
						// showExport: true,//显示导出按钮
						// exportDataType: "selected",//导出所选择的数据
						// exportTypes: ['excel'],//到处excel
						// Icons: 'fa fa-download',//导出按钮图标
                        //
						// exportOptions:{
						// 	ignoreColumn: [0,12],  //忽略某一列的索引
						// 	fileName: '经销商列表',  //文件名称设置
						// 	worksheetName: '经销商列表',  //表格工作区名称
						// 	tableName: '经销商列表',
						// 	excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
						// 	// onMsoNumberFormat: DoOnMsoNumberFormat
						// },

						// //设置为limit则会发送符合RESTFull格式的参数
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
							     pageNumber : params.pageNumber,
                                  pageSize : params.pageSize,
								sortName : params.sortName,
								sortOrder : params.sortOrder,
					            agentName:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						responseHandler : function(res){
                            console.log(res);
                            return {
                                "total": res.data.total,//总数
                                "rows": res.data.records   //数据
                             };
                        },
						columns : [
								{
									checkbox : true
								},
																{
									field : 'agentId', 
									title : '编号',
									align : 'center'
								},
								// 								{
								// 	field : 'levelName',
								// 	title : '等级',
                                //                                     align : 'center'
								// },
								// 								{
								// 	field : 'userId',
								// 	title : '用户id'
								// },
								// 								{
								// 	field : 'parentUserId',
								// 	title : '直接上级id'
								// },
								// 								{
								// 	field : 'inviteUserId',
								// 	title : '邀请人用户id'
								// },
								// 								{
								// 	field : 'companyId',
								// 	title : '经销商所属商户'
								// },
																{
									field : 'agentName', 
									title : '代理商名字',
                                                                    align : 'center'
								},
																{
									field : 'linkman', 
									title : '联系人',
                                                                    align : 'center'
								},
																{
									field : 'tel', 
									title : '电话',
                                                                    align : 'center'
								},
																{
									field : 'email', 
									title : '邮箱',
                                                                    align : 'center'
								},
                            {
                                field : 'province',
                                title : '省级',
                                align : 'center'
                            },
                            {
                                field : 'city',
                                title : '市级',
                                align : 'center'
                            },
                            {
                                field : 'area',
                                title : '区级',
                                align : 'center'
                            },
                            {
                                field : 'address',
                                title : '详细地址',
								formatter : function (value, row, index) {
									return row.province + row.city + row.area + value
                                },
                                align : 'center'
                            },

																{
									field : 'createDate',
									title : '创建时间',
									sortable : true
								},
																{
									field : 'status', 
									title : '状态',
									formatter : function (value, row, index) {
										var result = '';
										if (value == 1){
											result = '可用'
										}else{
											result = '禁用'
										}
										return result
									}
                                                                    ,
                                                                    align : 'center'
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
                                        var a = '<a class="btn btn-primary btn-sm '+s_address_h+'" href="#" mce_href="#" title="地址" onclick="address(\''
                                            + row.agentId
                                            + '\')"><i class="fa fa-book"></i></a> ';

										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.agentId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										// var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										// 		+ row.agentId
										// 		+ '\')"><i class="fa fa-remove"></i></a> ';
                                        if (row.status == 1) {
                                            var d = '<a class="btn btn-danger btn-sm '+ s_status_h+'" href="#" title="禁用" ' +
                                                'mce_href="#" onclick="status(\'' + row.agentId + '\',\''+ 0 + '\')">' +
                                                '<i class="fa fa-lock"></i></a>'
                                        }else{
                                            var d = '<a class="btn btn-success btn-sm '+ s_status_h+'" href="#" title="使用" ' +
                                                'mce_href="#" onclick="status(\'' + row.agentId + '\',\''+ 1 + '\')"><i class="fa fa-unlock"></i></a>'
                                        }
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.agentId
												+ '\')"><i class="fa fa-key"></i></a> ';
										return a + e + d ;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function agentImport() {
	layer.open({
		type : 2,
		title : '导入',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/agentImport' // iframe的url
	});
}

function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function address(id) {
    layer.open({
        type : 2,
        title : '地址',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : getContext() + '/agent/agentAddress/agentAddress/' + id // iframe的url
    });
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'agentId' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function status(id,status) {
    layer.confirm('确定要处理选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix+"/status",
            type : "post",
            data : {
                'agentId' : id,
				'status' : status
            },
            success : function(r) {
                if (r.code==0) {
                    layer.msg(r.msg);
                    reLoad();
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['agentId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}

function tableExport(){
    $('#exampleTable').tableExport(
        {
            type: 'excel',//导出excel
            escape: 'false' ,
            ignoreColumn: [0,12],  //忽略某一列的索引
            fileName: '经销商记录',  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: '经销商记录',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
        }
    )
}
$('#btnExport').bind('click', function () {
    tableExport()
})