$(function () {
    var ctxPath = $("meta[name=ctxPath]").attr("content");

    $('#exampleTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: ctxPath + "code/out/record", // 服务器数据的加载地址
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
            console.log(res);
            return {
                "total": res.data.total,//总数
                "rows": res.data.records   //数据
            };
        },
        columns: [
            {
                field: 'id',
                title: '编号',
                align: 'center'
            },
            {
                field: 'companyName',
                title: '商户',
                align: 'center'
            },
            {
                field: 'serialStart',
                title: '起始序列号',
                align: 'center'
            },
            {
                field: 'serialEnd',
                title: '结束序列号',
                align: 'center'
            },
            // {
            //     field: 'ident',
            //     title: '产品标识',
            //     align: 'center'
            // },
            {
                field: 'creatDate',
                title: '出码时间',
                align: 'center'
            },
            {
                field: 'createUsername',
                title: '创建人',
                align: 'center'
            }]
    });

    $(document).on("click", "button", function () {
        switch ($(this).attr("data-operate")) {
            case "add": {
                add();
                break;
            }
        }
    });

    window.reload = function () {
        $('#exampleTable').bootstrapTable('refresh');
    };

    /**
     * 添加出码记录
     */
    function add() {
        layer.open({
            type: 2,
            title: '增加',
            maxmin: true,
            shadeClose: false, // 点击遮罩关闭层
            area: ['800px', '520px'],
            content: ctxPath + 'code/view/out/record/add' // iframe的url
        });
    }
});