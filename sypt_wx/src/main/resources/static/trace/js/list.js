(function (w) {
    var bindLayer, bindAutoLayer;
    var ctx = $("meta[name=ctxPath]").attr("content");
    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: ctx + "trace/list", // 服务器数据的加载地址
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

        sortable: true,
        sortName: 'createdDate',
        sortOrder: 'desc',

        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        search: true, // 是否显示搜索框
        // showColumns : true, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParamsType: "", //设置为limit则会发送符合RESTFull格式的参数
        queryParams: function (params) {
            return {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                searchKey: params.searchText,
                sortName : params.sortName,
                sortOrder : params.sortOrder
            };
        },
        // 返回false将会终止请求
        responseHandler: function (res) {
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
                field: 'traceFileId', // 列字段名
                title: '编号', // 列标题
                align: 'center'
            }, {
                field: 'traceFileName',
                title: '档案名称',
                align: 'center'
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'center'
            }, {
                field: 'reportName',
                title: '报告名称',
                align: 'center'
            }, {
                field: 'purchaseNo',
                title: '批次',
                align: 'center'
            },{
                field: 'username',
                title: '创建人',
                align: 'center'
            },{
                field: 'createdDate',
                title: '创建时间',
                sortable: true,
                formatter: function (value, row, index) {
                    return new Date(value).format("yyyy-MM-dd")
                },
                align: 'center'
            }, {
                field: 'bindCount',
                title: '出库次数',
                align: 'center'
            }, {
                field: 'codeCount',
                title: '产品使用量',
                align: 'center'
            }, {
                title: '操作',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var text = "";
                    //console.log(row)
                    text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="preview" id="' + row.traceFileId + '" title="预览"><i class="fa fa-eye"></i></a> ';
                    text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="edit" id="' + row.traceFileId + '" title="编辑"><i class="fa fa-edit"></i></a> ';
                    text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="clone" id="' + row.traceFileId + '" title="复制"><i class="fa fa-clone"></i></a> ';
                    if (row.isTrace==1){
                        if (row.isOpen == 0){
                            text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="open" id="' + row.traceFileId + '" name="'+row.productId+'" title="启动关联"><i class="fa fa-check-circle"></i></a> ';
                        }else{
                            text += '<a class="btn btn-danger btn-sm ' + s_edit_h + '" data-operate="close" id="' + row.traceFileId + '" name="'+row.productId+'" title="关闭关联"><i class="fa fa-remove"></i></a> ';
                        }
                    }else {
                        if (row.outType == 1){
                            text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="autoBind" id="' + row.traceFileId + '" title="自动出码"><i class="fa fa-paper-plane"></i></a> ';
                        }else if(row.outType == 2){
                            text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="bind" id="' + row.traceFileId + '" title="绑码出库"><i class="fa fa-paper-plane-o"></i></a> ';
                        }else if(row.outType == 3){
                            text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="autoBind" id="' + row.traceFileId + '" title="自动出码"><i class="fa fa-paper-plane"></i></a> ';
                            text += '<a class="btn btn-primary btn-sm ' + s_edit_h + '" data-operate="bind" id="' + row.traceFileId + '" title="绑码出库"><i class="fa fa-paper-plane-o"></i></a> ';
                        }
                    }

                    text += '<a class="btn btn-warning btn-sm ' + s_edit_h + '" data-operate="delete" id="' + row.traceFileId + '" title="删除"><i class="fa fa-remove"></i></a>';
                    return text;
                }
            }
        ]
    });

    $(document).on("click", "a,button", function (e) {
        e.preventDefault();
        switch ($(this).attr("data-operate")) {
            case "preview": {
                preview($(this).attr("id"));
                break
            }
            case "autoBind": {
                bindAuto($(this).attr("id"));
                break
            }
            case "bind": {
                bind($(this).attr("id"));
                break
            }
            case "add": {
                add();
                break
            }
            case "edit": {
                editView($(this).attr("id"));
                break
            }
            case "clone": {
                cloneView($(this).attr("id"));
                break
            }
            case "delete": {
                remove($(this).attr("id"));
                break
            }
            case "open":{
                console.log($(this).attr("name"))
                open($(this).attr("id"),$(this).attr("name"),1);
                break
            }
            case "close":{
                console.log($(this).attr("name"))
                open($(this).attr("id"),$(this).attr("name"),0);
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
            content: ctx + 'trace/view/add' // iframe的url
        });
    }

    /**
     * 溯源档案编辑
     * @param id
     */
    function editView(id) {
        var url = ctx + 'trace/view/edit/' + id;
        //console.log(id, url, this);
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
     * 溯源档案编辑
     * @param id
     */
    function cloneView(id) {
        var url = ctx + 'trace/view/clone/' + id;
        //console.log(id, url, this);
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
     * 预览
     * @param id
     */
    function preview(id) {
        layer.open({
            type: 2,
            title: '预览',
            shadeClose: true,
            shade: 0.8,
            area: ['35%', "95%"],
            content: ctx + "trace/view/preview/" + id //iframe的url
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
                url: ctx + "trace/remove",
                type: "post",
                data: {
                    'traceFileId': id
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
                url: ctx + 'trace/remove/batch',
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

    /**
     * 绑码
     *
     * @param id 档案id
     */
    function bind(id) {
        bindLayer = layer.open({
            type: 2,
            title: '绑码出库',
            maxmin: true,
            shadeClose: true, // 点击遮罩关闭层
            area: ['30%', '30%'],
            content: ctx + 'trace/view/bind/' + id // iframe的url
        });
    }

    /**
     * 自动绑码
     *
     * @param id 档案id
     */
    function bindAuto(id) {
        bindAutoLayer = layer.open({
            type: 2,
            title: '自动绑码',
            maxmin: true,
            shadeClose: true, // 点击遮罩关闭层
            area: ['30%', '30%'],
            content: ctx + 'trace/view/bind/auto/' + id // iframe的url
        });
    }

    function open(id,productId,isOpen){
        $.ajax({
            url: ctx + 'trace/open',
            type: 'POST',
            data: {
                'traceFileId': id,
                'isOpen':isOpen,
                'productId':productId
            },
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
    }

    /**
     * 关闭layer弹窗
     */
    w.closeLayer = function () {
        if (bindLayer) {
            layer.close(bindLayer);
            //刷新表格
            tableReload()
        }

        if (bindAutoLayer) {
            layer.close(bindAutoLayer);
            //刷新表格
            tableReload()
        }
    }


})(window);