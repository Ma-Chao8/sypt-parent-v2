(function (w) {
    var ctx = $("meta[name=ctxPath]").attr("content");
    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: ctx + "company/list", //服务器数据的加载地址
        showRefresh: true,
        // showToggle : true,
        // showColumns : true,
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, //设置为true会有隔行变色效果
        dataType: "json", //服务器返回的数据类型
        pagination: true, //设置为true会在底部显示分页条
        singleSelect: false, //设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",  //发送到服务器的数据编码类型
        pageSize: 10, //如果设置了分页，每页数据条数
        pageNumber: 1, //如果设置了分布，首页页码
        search: true, //是否显示搜索框
        // showColumns : true, //是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParamsType: "", //设置为limit则会发送符合RESTFull格式的参数
        queryParams: function (params) {
            return {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                searchKey: params.searchText
            };
        },
        // 返回false将会终止请求
        responseHandler: function (res) {
            console.log(res);
            if (res.data) {
                return {
                    "total": res.data.total,//总数
                    "rows": res.data.records   //数据
                }
            }
            return {
                "total": 0,//总数
                "rows": []   //数据
            };
        },
        columns: [
            { // 列配置项
                // 数据类型，详细参数配置参见文档http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
                checkbox: true
                // 列表中显示复选框
            }, {
                field: 'companyId', // 列字段名
                title: '编号' // 列标题
            }, {
                field: 'companyName',
                title: '档案名称'
            }, {
                field: 'linkman',
                title: '联系人'
            }, {
                field: 'tel',
                title: '联系电话'
            }, {
                field: 'companyAddress',
                title: '公司地址'
            }, {
                field: 'createDate',
                title: '注册时间',
                formatter: function (value, row, index) {
                    return new Date(value).format("yyyy-MM-dd")
                }
            }, {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {
                    var text = "";
                    text += '<a class="btn btn-info btn-sm ' + s_bind_h + '" data-operate="bind" id="' + row.companyId + '" title="绑定模版"><i class="glyphicon glyphicon-list"></i></a> '
                    text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="edit" id="' + row.companyId + '" title="编辑"><i class="fa fa-edit"></i></a> ';
                    // text += '<a class="btn btn-warning btn-sm ' + s_edit_h + '" data-operate="delete" id="' + row.companyId + '" title="冻结"><i class="fa fa-remove"></i></a>';
                    return text;
                }
            }
        ]
    });

    $(document).on("click", "*[data-operate]", function (e) {
        e.preventDefault();
        console.log(this);
        switch ($(this).attr("data-operate")) {
            case "add": {
                add();
                break
            }
            case "edit": {
                editView($(this).attr("id"));
                break
            }
            case "delete": {
                remove($(this).attr("id"));
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
            case "bind" :{
                bind($(this).attr("id"));
                break
            }
        }
    });

    w.tableReload = function () {
        $('#exampleTable').bootstrapTable('refresh');
    };


    /**
     * 溯源档案添加
     */
    function add() {
        layer.open({
            type: 2,
            title: '<span>添加</span>',
            maxmin: false,
            shadeClose: false, // 点击遮罩关闭层
            area: ['100%', '100%'],
            content: ctx + 'company/view/add' // iframe的url
        });
    }

    /**
     * 溯源档案编辑
     * @param id
     */
    function editView(id) {
        var url = ctx + 'company/view/edit/' + id;
        console.log(id, url, this);
        layer.open({
            type: 2,
            title: '档案编辑',
            maxmin: false,
            shadeClose: true, // 点击遮罩关闭层
            area: ['100%', '100%'],
            content: url // iframe的url
        });
    }

    /**
     * 商户绑定模版
     * @param id
     */
    function bind(id) {
        layer.open({
            type : 2,
            title : '绑定模版',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '520px' ],
            content : ctx + 'company/view/bind/' + id // iframe的url
        });
    }

    /**
     * 溯源档案删除
     * @param id
     */
    function remove(id) {
        layer.confirm('确定要删除选中的记录？', {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                url: ctx + "company/remove",
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


    /**
     * 批量删除
     */
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
                url: ctx + 'company/remove/batch',
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