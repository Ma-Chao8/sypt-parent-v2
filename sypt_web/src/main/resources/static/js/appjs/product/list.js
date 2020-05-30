(function (w) {
    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: ctx + "product/list", // 服务器数据的加载地址
        showRefresh: true,
        // showToggle : true,
        // showColumns : true,
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: true, // 设置为true会在底部显示分页条
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",  //发送到服务器的数据编码类型
        sortable: true, // 是否启用排序
        sortName: 'createdDate',// 初始化的时候排序的字段
        sortOrder: 'desc',
        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        search: true, // 是否显示搜索框
        // showColumns : true, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParamsType: "", //设置为limit则会发送符合RESTFull格式的参数

        // showExport: true,//显示导出按钮
        // exportDataType: "selected",//导出所选择的数据
        // exportTypes: ['excel'],//到处excel
        // Icons: 'fa-caret-square-down',//导出按钮图标
        // exportOptions:{
        //     ignoreColumn: [0,9,11],  //忽略某一列的索引
        //     fileName: '产品列表',  //文件名称设置
        //     worksheetName: '产品列表',  //表格工作区名称
        //     tableName: '产品列表',
        //     excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
        //     // onMsoNumberFormat: DoOnMsoNumberFormat
        // },
        queryParams: function (params) {
            return {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                productName: params.searchText,
                sortName: params.sortName,
                sortOrder: params.sortOrder
            };
        },
        // 返回false将会终止请求
        responseHandler: function (res) {
            return {
                "total": res.data.total,//总数
                "rows": res.data.records   //数据
            };
        },
        columns: [
            { // 列配置项
                // 数据类型，详细参数配置参见文档http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
                checkbox: true
                // 列表中显示复选框
            }, {
                field: 'productId', // 列字段名
                title: '编号', // 列标题
                align: 'center',
                sortable: true
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'center'
            }, {
                field: 'describe',
                title: '产品描述',
                align: 'center'
            }, {
                field: 'boxSize',
                title: '产品包装数量',
                align: 'center'
            }, {
                field: 'productSpec',
                title: '产品规格',
                align: 'center'
            }, {
                field: 'name',
                title: '溯源模版名称',
                align: 'center'
            }, {
                field: 'productImg',
                title: '产品图片',
                formatter: function (value, row, index) {
                    return '<img src="' + value + '" style="height: 100px;width: 100px"/>'
                },
                align: 'center'
            }, {
                field: 'createdDate',
                title: '创建时间',
                sortable: true,
                formatter: function (value, row, index) {
                    return new Date(value).format("yyyy-MM-dd")
                }
            }, {
                title: '操作',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="edit" title="编辑" data-id="' + row.productId + '"><i class="fa fa-edit"></i></a> ';
                    var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" data-operate="delete" data-id="' + row.productId + '" title="删除"><i class="fa fa-remove"></i></a> ';
                    return e + d;
                }
            }
        ]
    });


    $(document).on("click", "a,button", function () {
        switch ($(this).attr("data-operate")) {

            case "add": {
                add();
                break
            }
            case "edit": {
                edit($(this).attr("data-id"));
                break
            }
            case "delete": {
                remove($(this).attr("data-id"));
                break
            }
            case "batchDelete": {
                batchRemove();
                break
            }
            case "query": {
                tableReload();
                break
            }


        }
    });


    w.tableReload = function () {
        $('#exampleTable').bootstrapTable('refresh');
    };

    function add() {
        // iframe层
        layer.open({
            type: 2,
            title: '添加',
            maxmin: true,
            shadeClose: false, // 点击遮罩关闭层
            area: ['80%', '95%'],
            content: ctx + 'product/view/add' // iframe的url
        });
    }

    function remove(id) {
        layer.confirm('确定要删除选中的记录？', {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                url: ctx + "product/remove",
                type: "post",
                data: {
                    'productId': id
                },
                success: function (r) {
                    layer.msg(r.msg);
                    if (r.code === 0) {
                        tableReload();
                    }
                },
                error: function (error) {
                    layer.alert("网络错误，请稍后重新操作");
                }
            });
        })

    }


    function edit(id) {
        layer.open({
            type: 2,
            title: '角色修改',
            maxmin: true,
            shadeClose: true, // 点击遮罩关闭层
            area: ['80%', '95%'],
            content: ctx + 'product/view/edit/' + id // iframe的url
        });
    }

    function batchRemove() {
        var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
        if (rows.length == 0) {
            layer.msg("请选择要删除的数据");
            return;
        }
        layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
            btn: ['确定', '取消']
        }, function () {
            var ids = new Array();
            $.each(rows, function (i, row) {
                ids[i] = row['productId'];
            });
            $.ajax({
                url: ctx + 'product/remove/batch',
                type: 'POST',
                dataType: 'JSON',
                data: "ids=" + ids,
                success: function (result) {
                    layer.msg(result.msg);
                    if (result.code == 0) {
                        tableReload();
                    }
                },
                error: function (error) {
                    layer.alert("网络错误，请稍后重新操作");
                }
            });
        }, function () {
        });
    }
})(window);
var prefix = getContext() + "/product"

function productImport() {
    layer.open({
        type: 2,
        title: '导入',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/productImport' // iframe的url
    });
}

function tableExport() {
    $('#exampleTable').tableExport(
        {
            type: 'excel',//导出excel
            escape: 'false',
            ignoreColumn: [0, 9, 11],  //忽略某一列的索引
            fileName: '产品记录',  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: '产品记录',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
        }
    )
}

$('#btnExport').bind('click', function () {
    tableExport()
})