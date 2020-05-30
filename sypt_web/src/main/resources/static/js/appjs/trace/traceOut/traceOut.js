var prefix = getContext() + "/trace/traceOut"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded", //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParamsType: "", // //设置为limit则会发送符合RESTFull格式的参数
                queryParams: function (params) {
                    params["batch"] = $('#searchName').val();
                    params["endDate"] = $('#endDate').val();
                    params["beginDate"] = $('#beginDate').val();
                    return params;
                },
                sortOrder: "desc",
                sortName: "createDate",
                // 返回false将会终止请求
                responseHandler: function (res) {
                    console.log(res);
                    return {
                        "total": res.data.total,//总数
                        "rows": res.data.records   //数据
                    };
                },
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'traceOutId',
                        title: '编号',
                        align: 'center',
                        sortable: true
                    },
                    {
                        field: 'batch',
                        title: '批次'
                        , align: 'center'
                    },
                    {
                        field: 'startSerial',
                        title: '开始序号'
                        , align: 'center'
                    },
                    {
                        field: 'endSerial',
                        title: '结束序号'
                        , align: 'center'
                    },
                    {
                        field: 'createDate',
                        title: '创建日期',
                        align: 'center',
                        sortable: true
                    },
                    {
                        field: 'productName',
                        title: '产品'
                        , align: 'center'
                    },
                    {
                        field: 'reportName',
                        title: '检测报告'
                        , align: 'center'
                    },
                    {
                        field: 'purchaseNo',
                        title: '原材料入库批次'
                        , align: 'center'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var a = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="溯源流程" onclick="toOutContent(\''
                                + row.productId
                                + '\',\'' + row.traceOutId + '\')"><i class="fa fa-list"></i></a> ';
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.traceOutId
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.traceOutId
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var t = '<a class="btn btn-warning btn-sm ' + s_edit_h + '" href="#" title="绑定"  mce_href="#" onclick="bindLink(\''
                                + row.productId
                                + '\')"><i class="fa fa-file-text"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                                + row.traceOutId
                                + '\')"><i class="fa fa-key"></i></a> ';
                            return e + d;
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function toOutContent(productId, traceOutId) {
    // console.log("id==="+traceOutId)
    layer.open({
        type: 2,
        title: '溯源流程',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['85%', '85%'],
        content: prefix + '/toOutContent/' + productId + '/' + traceOutId// iframe的url
    });
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['85%', '85%'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['85%', '85%'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function bindLink(productd) {
    layer.open({
        type: 2,
        title: '绑定',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['100%', '100%'],
        content: getContext() + '/procedures/linkContentData/guide/' + productd // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'traceOutId': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
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
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['traceOutId'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}