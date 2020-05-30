function load() {
    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: ctxPath + "procedures/banner/" + $("meta[name=proceduresId]").attr("content") + "/list", // 服务器数据的加载地址
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
            return {
                //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                pageNumber: params.pageNumber,
                pageSize: params.pageSize
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
                field: 'bannerId',
                title: '编号',
                align: 'center'
            },
            {
                field: 'bannerTitle',
                title: '标题',
                align: 'center'
            },
            {
                field: 'bannerUrl',
                title: '图片',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value) {
                        return "<img src='" + value + "' width='50px' height='50px'>";
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'bannerSequence',
                title: '轮播顺序',
                align: 'center'
            },
            {
                field: 'bannerRedirectUrl',
                title: '跳转连接',
                align: 'center'
            },
            {
                title: '操作',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var text = "";
                    text += '<a class="btn btn-warning btn-sm ' + s_edit_h + '" data-name="bannerId" data-bind="' + row.bannerId + '" title="编辑" data-option="edit"><i class="fa fa-edit"></i></a> ';
                    text += '<a class="btn btn-danger btn-sm ' + s_remove_h + '" data-name="bannerId" data-bind="' + row.bannerId + '" title="删除" data-option="remove"><i class="fa fa-remove"></i></a> ';
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

            case "bannerAdd": {
                add($(this).attr("data-bind"));
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

            case "batchRemove": {
                batchRemove();
                break
            }
        }
    });
});


function add(proceduresId) {
    layer.open({
        type: 2,
        title: '添加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
        content: ctxPath + 'procedures/banner/' + proceduresId + '/add' // iframe的url
    });
}





function edit(banner_id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['95%', '95%'],
        content: ctxPath + 'procedures/banner/edit/' + banner_id // iframe的url
    });
}


function remove(banner_id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: ctxPath + "procedures/banner/del",
            type: "post",
            data: {
                'bannerId': banner_id
            },
            success: function (result) {
                layer.msg(result.msg);
                if (result.code == 0) {
                    reLoad();
                }
            },
            error: function (error) {
                layer.msg("请求错误：" + error.status);
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
            ids.push(row['bannerId']);
        });
        $.ajax({
            url: ctxPath + 'procedures/banner/del',
            type: 'POST',
            data: {
                "bannerId": ids
            },
            traditional: true,
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