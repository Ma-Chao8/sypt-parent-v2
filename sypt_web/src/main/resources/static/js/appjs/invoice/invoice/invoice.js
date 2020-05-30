
var prefix = getContext() + "/invoice/invoice"
$(function() {
	getWarehouseId();
	selectAgentAll();
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
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
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						sortable : true, // 是否启用排序
						sortName : 'deliverDate',// 初始化的时候排序的字段
						sortOrder : 'desc',
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParamsType : "",

						// showExport: true,//显示导出按钮
						// exportDataType: "selected",//导出所选择的数据
						// exportTypes: ['excel'],//到处excel
						// Icons: 'fa-caret-square-down',//导出按钮图标
						// exportOptions:{
						// 	ignoreColumn: [0,7],  //忽略某一列的索引
						// 	fileName: '发货列表',  //文件名称设置
						// 	worksheetName: '发货列表',  //表格工作区名称
						// 	tableName: '发货列表',
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
								agentId:$("#agentId").find("option:selected").val(),
								beginDate: $("#beginDate").val(),
								endDate: $("#endDate").val(),
					            searchKey:$('#searchKey').val(),
								warehouseId:$('#warehouseId option:selected').val()
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
									field : 'invoiceId', 
									title : '货单号',
									align : "center"
								},
							    {
								    field : 'agentName',
								    title : '经销商',
									align : "center"
							    },
							    {
							    	field : 'linkman',
									title : '联系人',
									align : "center"
							    },
																{
									field : 'tel', 
									title : '联系电话',
									align : "center"
								},
																{
									field : 'deliverAddres', 
									title : '收货地址',
									align : "center"
								},
																{
									field : 'deliverDate', 
									title : '发货时间' ,
									sortable:true,
									align: 'center'
								},
							   {
									field : 'warehouseName',
									title : '所属仓库' ,
									align: 'center'
							   },
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var a = '<a class="btn btn-primary btn-sm '
											+ s_edit_h
											+ '" href="#" mce_href="#" title="打印" onclick="Printing(\''
											+ row.invoiceId
											+ '\')"><i class="fa fa-print"></i></a> ';
										var e = '<a class="btn btn-warning btn-sm '+s_details_h+'" href="#" mce_href="#" title="详情" onclick="details(\''
											+ row.invoiceId
											+ '\')">详情</a> ';
										return a+e;

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
function details(id) {
	layer.open({
		type : 2,
		title : '详情',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/details/' + id // iframe的url
	});
}
function Printing(invoiceId) {
	var index = layer.open({
		type : 2,
		title : '发货详情打印',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '100%', '100%' ],
		maxmin : true,
		content : prefix + '/Printing/' + invoiceId // iframe的url
	});
	layer.full(index);
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'invoiceId' : id
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
			ids[i] = row['invoiceId'];
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
            ignoreColumn: [0,7],  //忽略某一列的索引
            fileName: '发货记录',  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: '发货记录',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
        }
    )
}
$('#btnExport').bind('click', function () {
    tableExport()
})