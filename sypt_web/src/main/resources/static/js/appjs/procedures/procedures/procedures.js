var prefix = getContext() + "/procedures/procedures";

function load() {
    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/list", // 服务器数据的加载地址
        //	showRefresh : true,
        //	showToggle : true,
        //	showColumns : true,
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: true, // 设置为true会在底部显示分页条
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        //search : true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParamsType: "",
        // //设置为limit则会发送符合RESTFull格式的参数
        queryParams: function (params) {
            return params;
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        sortable: true,
        sortOrder: "desc",
        sortName: "createDate",
        // 返回false将会终止请求
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
                field: 'procedureId',
                title: '编号',
                align: 'center',
                sortable: true
            },
            {
                field: 'name',
                title: '名称',
                align: 'center'
            },
            {
                field: 'createDate',
                title: '创建时间',
                sortable: true,
                formatter: function (value, row, index) {
                    console.log(value, row, index);
                    var date = new Date(value).format('yyyy-MM-dd');
                    return date;
                },
                align: 'center'
            },
            {
                field: 'isOpen',
                title: '状态',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '启用'
                    } else {
                        return '关闭'
                    }
                },
                align: 'center'
            },
            {
                field: 'bannerEnable',
                title: '轮播图',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '开启';
                    } else {
                        return '关闭';
                    }
                },
                align: 'center'
            },
            {
                field: 'footlinkAnable',
                title: '底部链接',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '开启';
                    } else {
                        return '关闭';
                    }
                },
                align: 'center'
            },
            {
                title: '操作',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var text = "";
                    text += '<a class="btn btn-primary btn-sm ' + s_bind_h + '" data-name="procedureId" data-bind="' + row.procedureId + '" title="关联环节" data-option="associate"><i class="fa fa-tasks"></i></a> ';
                    if (row.bannerEnable == 1) {
                        text += '<a class="btn btn-success btn-sm ' + s_edit_h + '" data-name="procedureId" data-bind="' + row.procedureId + '" title="轮播图" data-option="banner"><i class="fa fa-photo"></i></a> ';
                    } else {
                        text += '<a disabled="true" class="btn btn-default btn-sm ' + s_edit_h + '" title="轮播图"><i class="fa fa-photo"></i></a> ';

                    }
                    if (row.footlinkAnable) {
                        text += '<a class="btn btn-info btn-sm ' + s_edit_h + '" data-name="procedureId" data-bind="' + row.procedureId + '" title="底部链接" data-option="footlink"><i class="fa fa-tags"></i></a> ';
                    } else {
                        text += '<a disabled="ture" class="btn btn-default btn-sm ' + s_edit_h + '" title="底部链接"><i class="fa fa-tags"></i></a> ';
                    }
                    text += '<a class="btn btn-warning btn-sm ' + s_edit_h + '" data-name="procedureId" data-bind="' + row.procedureId + '" title="编辑" data-option="edit"><i class="fa fa-edit"></i></a> ';
                    text += '<a class="btn btn-danger btn-sm ' + s_remove_h + '" data-name="procedureId" data-bind="' + row.procedureId + '" title="删除" data-option="remove"><i class="fa fa-remove"></i></a> ';
                    return text;
                }
            }]
    });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

$(function () {
    load();
    $(document).on("click", "a", function () {
        switch ($(this).attr("data-option")) {
            case "associate": {
                associate($(this).attr("data-bind"));
                break;
            }
            case "banner": {
                banner($(this).attr("data-bind"));
                break;
            }
            case "footlink": {
                footlink($(this).attr("data-bind"));
                break;
            }
            case "edit": {
                edit($(this).attr("data-bind"));
                break;
            }
            case "remove": {
                remove($(this).attr("data-bind"));
                break;
            }
        }
    });
});


function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
        content: prefix + '/add' // iframe的url
    });
}

function associate(id) {
    layer.open({
        type: 2,
        title: '环节关联',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
        content: ctxPath + 'procedures/procedureLink/' + id // iframe的url
    });
}


function banner(id) {
    layer.open({
        type: 2,
        title: '轮播图',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
        content: ctxPath + 'procedures/banner/' + id // iframe的url
    });
}


function footlink(id) {
    layer.open({
        type: 2,
        title: '底部链接',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
        content: ctxPath + 'procedures/footlink/' + id // iframe的url
    });
}


function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
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
                'procedureId': id
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
            ids[i] = row['procedureId'];
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