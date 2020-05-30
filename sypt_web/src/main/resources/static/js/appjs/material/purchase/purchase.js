
var prefix = getContext()+"/material/purchase"
$(function() {
    getIndustryId()
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
						// showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型

						sortable: true,
						sortName: 'dateOfProduction',
	                    sortOrder: 'desc',

						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParamsType : "",

                        // showExport: true,//显示导出按钮
                        // exportDataType: "selected",//导出所选择的数据
                        // exportTypes: ['excel'],//到处excel
                        // Icons: 'fa-caret-square-down',//导出按钮图标
                        // exportOptions:{
                        //     ignoreColumn: [0,7,10],  //忽略某一列的索引
                        //     fileName: '原材料进库列表',  //文件名称设置
                        //     worksheetName: '原材料进库列表',  //表格工作区名称
                        //     tableName: '原材料进库列表',
                        //     excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                        //     // onMsoNumberFormat: DoOnMsoNumberFormat
                        // },
						// //设置为limit则会发送符合RESTFull格式的参数
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
							     pageNumber : params.pageNumber,
                                  pageSize : params.pageSize,
								sortName : params.sortName,
								sortOrder : params.sortOrder,
								companyId:$("#companyId option:selected").val()
					           // name:$('#searchName').val(),
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
									field : 'purchaseId', 
									title : '编号',
                                                                    align : 'center'
								},
																{
									field : 'materialName',
									title : '原料',
                                                                    align : 'center'
								},
																{
									field : 'dateOfProduction', 
									title : '原料生产日期(出厂日期)',
                                                                    align : 'center',
																	sortable:true
								},
																{
									field : 'supplierName',
									title : '供应商',
                                                                    align : 'center'
								},
																{
									field : 'purchaseNo', 
									title : '批次',
                                                                    align : 'center'
								},
																{
									field : 'purchaser', 
									title : '采购人',
                                                                    align : 'center'
								},
							   {
								    field : 'dateStore',
								    title : '入库日期',
								   align : 'center'
							   },
																{
									field : 'reportImg', 
									title : '检测图片',
                                     align : 'center',
									formatter : function(value, row, index) {
                                       return '<img style="cursor:pointer;" src="'+row.reportImg+'" width="120" height="80" onclick="showImg(\''+row.reportImg+'\')">'
									}

								},
																{
									field : 'number', 
									title : '数量',
                                                                    align : 'center'
								},
																{
									field : 'identifier', 
									title : '入库标识',
                                                                    align : 'center'
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.purchaseId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.purchaseId
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.purchaseId
												+ '\')"><i class="fa fa-key"></i></a> ';
										return e + d ;
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

function purchaseImport() {
    layer.open({
        type : 2,
        title : '导入',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/purchaseImport' // iframe的url
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
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'purchaseId' : id
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
			ids[i] = row['purchaseId'];
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
function showImg(url){
    layer.open({
        type : 2,
        title : '查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '55%', '70%' ],
        content : url
    });
}

function tableExport(){
    $('#exampleTable').tableExport(
        {
            type: 'excel',//导出excel
            escape: 'false' ,
            ignoreColumn: [0,12],  //忽略某一列的索引
            fileName: '原材料进库记录',  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: '原材料进库记录',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
        }
    )
}
$('#btnExport').bind('click', function () {
    tableExport()
})