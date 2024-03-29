var prefix = getContext() + "/traceability/traceabilityFile"
$(function () {
    load();
});

function load() {
    $('#exampleTable').bootstrapTable({
        url: prefix + "/list", // 服务器数据的加载地址
        method: 'get', // 服务器数据的请求方式 get or post
        toolbar: '#exampleToolbar',
        iconSize: 'outline',
        dataType: "json", // 服务器返回的数据类型
        striped: true, // 设置为true会有隔行变色效果
        pagination: true, // 设置为true会在底部显示分页条
        singleSelect: false, // 设置为true将禁止多选
        //发送到服务器的数据编码类型
        // contentType : "application/x-www-form-urlencoded",
        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        //search : true, // 是否显示搜索框
        // showRefresh: true,
        // showToggle: true,   showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sortable: true,
        sortName: "createDate",
        sortOrder: "desc",
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParamsType: "",
        queryParams: function (params) {
            console.log(params);
            return params;
        },
        responseHandler: function (res) {
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
                field: 'traceabilityFileId',
                title: '编号',
                align: 'center',
                sortable: true
            },
            {
                field: 'traceabilityFileName',
                title: '档案名称',
                align: 'center'
            },
            {
                field: 'productName',
                title: '产品名称',
                align: 'center'
            },
            {
                field: 'procedureName',
                title: '模版名称',
                align: 'center'
            },
            {
                field: 'themeName',
                title: '主题名称',
                align: 'center'
            },
            {
                field: 'introduce',
                title: '介绍语',
                align: 'center'
            },
            {
                field: 'state',
                title: '是否取消',
                formatter: function (value, row, index) {
                    var result = '';
                    if (value == 0) {
                        result = '否'
                    } else {
                        result = '是'
                    }
                    return result;
                }
            },
            {
                field: 'name',
                title: '创建人',
            },
            {
                field: 'createDate',
                title: '创建时间',
                sortable: true,
            },
            {
                title: '操作',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var a = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="溯源流程录入" onclick="toOutContent(\''
                        + row.productId
                        + '\',\'' + row.traceabilityFileId + '\')"><i class="fa fa-list"></i></a> ';
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                        + row.traceabilityFileId
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    var d = '<a class="btn btn-warning btn-sm " href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + row.traceabilityFileId
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    var f = '<a class="btn btn-success btn-sm" href="#" title="批次出库"  mce_href="#" onclick="addTraceOut(\''
                        + row.traceabilityFileId
                        + '\',\'' + row.productId + '\')"><i class="fa fa-paper-plane-o"></i></a> ';
                    var b = '<a class="btn btn-success btn-sm " href="#" title="查看"  mce_href="#" onclick="check(\''
                        + row.traceabilityFileId
                        + '\',\'' + row.productId + '\')"><i class="fa fa-eye-slash"></i></a> ';
                    return a + b + f + e + d;
                }
            }]
    });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function check(traceabilityFileId, productId) {
    layer.open({
        type: 2,
        title: '查看',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['375px', '600px'],
        content: getContext() + "/traceability/traceabilityFile/preview/" + traceabilityFileId + '/' + productId// iframe的url
    });
}

function toOutContent(productId, traceabilityFileId) {
    // console.log("id==="+traceOutId)
    layer.open({
        type: 2,
        title: '溯源流程',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['85%', '85%'],
        content: getContext() + "/trace/traceOut" + '/toOutContent/' + productId + '/' + traceabilityFileId// iframe的url
    });
}

function addTraceOut(traceabilityFileId, productId) {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/addTraceOut/' + traceabilityFileId + '/' + productId // iframe的url
    });
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
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
                'traceabilityFileId': id
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
            ids[i] = row['traceabilityFileId'];
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