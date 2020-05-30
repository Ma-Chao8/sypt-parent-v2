(function (w) {
    var ctx = $("meta[name=ctxPath]").attr("content");
    /**
     * 请求响应参数
     *
     * @param resp
     * @returns {{total: number, rows: Array}|{total: *, rows: (number|*|Array|string)}}
     */
    var responseHandler = function (resp) {
        if (resp.data) {
            return {
                total: resp.data.total,//总数
                rows: resp.data.records   //数据
            }
        }
        return {
            total: 0,//总数
            rows: []   //数据
        };
    };

    /**
     * 构建请求参数
     *
     * @param params
     * @returns {{pageNumber: *, pageSize: *, searchKey: string}}
     */
    var buildQueryParams = function (params) {
        return {
            pageNumber: params.pageNumber,
            pageSize: params.pageSize,
            // searchKey: params.searchText,
            searchKey:$("#searchKey").val(),
            beginDate: $("#beginDate").val(),
            endDate: $("#endDate").val(),
            sortName : params.sortName,
            sortOrder : params.sortOrder
        };
    };

    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: ctx + "trace//bind/record", // 服务器数据的加载地址
        // showRefresh: true,
        // showToggle : true,
        // showColumns : true,
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: true, // 设置为true会在底部显示分页条
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",  //发送到服务器的数据编码类型

        sortable:true,
        sortName:'createDate',
        sortOrder:'desc',

        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        // search: true, // 是否显示搜索框
        // showColumns : true, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParamsType: "", //设置为limit则会发送符合RESTFull格式的参数

        //  showExport: true,//显示导出按钮
        //  exportDataType: "selected",//导出所选择的数据
        // exportTypes: ['excel'],//到处excel
        // Icons: 'fa-caret-square-down',//导出按钮图标
        // exportOptions:{
        //     ignoreColumn: [0,8],  //忽略某一列的索引
        //     fileName: '出库列表',  //文件名称设置
        //     worksheetName: '出库列表',  //表格工作区名称
        //     tableName: '出库列表',
        //     excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
        //     // onMsoNumberFormat: DoOnMsoNumberFormat
        // },

        queryParams: buildQueryParams,
        // 返回false将会终止请求
        responseHandler: responseHandler,
        columns: [
            {
              checkbox : true
            },
            {
                field: 'recordId', // 列字段名
                title: '编号', // 列标题
                align: 'center'
            }, {
                field: 'traceFileName',
                title: '档案名称',
                align: 'center'
            }, {
                field: 'username',
                title: '创建人',
                align: 'center'
            }, {
                field: 'createDate',
                title: '出码时间',
                sortable: true,
                formatter: function (value, row, index) {
                    return new Date(value).format("yyyy-MM-dd")
                },
                align: 'center'
            }, {
                field: 'codeNumber',
                title: '数量',
                align: 'center'
            }, {
                field: 'startSerial',
                title: '起始序列号',
                align: 'center'
            }, {
                field: 'endSerial',
                title: '终止序列号',
                align: 'center'
            }, {
                title: '操作',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var text = "";
                    if (row.codeBatchId != null) {
                        text += '<a class="btn btn-primary btn-sm" data-operate="download" id="' + row.codeBatchId + '" title="下载码文件"><i class="fa fa-download"></i></a> ';
                    } else {
                        text += '<a class="btn btn-primary btn-sm" data-operate="revert" id="' + row.recordId + '" title="撤回"><i class="fa fa-reply"></i></a> ';
                    }
                    return text;
                }
            }
        ]
    });

    $(document).on("click", "a,button", function (e) {
        e.preventDefault();
        switch ($(this).attr("data-operate")) {
            case "revert": {
                revert($(this).attr("id"));
                break
            }
            case "download": {
                downloadFile($(this).attr("id"));
                break
            }
        }
    });


    /**
     * 记录撤回
     *
     * @param id
     */
    function revert(id) {
        layer.confirm('确定要撤销选中的记录？', {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                url: ctx + "trace/bind/record/revert",
                type: "post",
                data: {
                    recordId: id
                },
                success: function (result) {
                    if (result.success) {
                        layer.msg(result.msg);
                        tableReload();
                    } else {
                        layer.alert(result.msg);
                    }
                },
                error: function (error) {
                    console.log(error);
                    layer.alert("网络错误，请稍后重新操作");
                }
            });
        })
    }

    /**
     * 下载码文件
     *
     * @param id
     */
    function downloadFile(id) {
        window.location.href = ctx + "trace/view/code/download/" + id;
    }


    w.tableReload = function () {
        $('#exampleTable').bootstrapTable('refresh');
    };


})(window);

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function clearCheckInput() {
        $("#searchKey").val('')
        $("#beginDate").val('')
        $("#endDate").val('')
    $('#exampleTable').bootstrapTable('refresh');
}