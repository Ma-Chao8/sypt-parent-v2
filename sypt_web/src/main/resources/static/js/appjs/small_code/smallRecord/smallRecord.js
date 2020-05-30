var prefix = getContext() + "/small_code/smallRecord"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
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

                sortable:true,
                sortName:'createDate',
                sortOrder: 'desc',

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
                        pageSize: params.pageSize,
                        bigCode: $('#searchName').val(),
                        sortName : params.sortName,
                        sortOrder : params.sortOrder
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
                        checkbox: true
                    },
                    {
                        field: 'bigCode',
                        title: '大码',
                        align: 'center'
                    },
                    // {
                    //     field: 'smallCode',
                    //     title: '小码',
                    //     align: 'center'
                    // },
                    {
                        title: '小码详情',
                        align: "center",
                        width: "300px",
                        formatter: function (value, row, index) {
                            var text = "<div class='box'>" + "<div class='box-header'><span style='color: #0e90d2'>展开↓</span></div>" +
                                "<div class='box-body hidden'>" + "<ul class='list-group'>";
                            for (var i in  row.smallCodes) {
                                var item = row.smallCodes[i];
                                text += "<li class='list-group-item'><span>" + item + "</span>&nbsp;&nbsp;" +
                                    "</li>";
                            }
                            text += "</ul></div></div>";
                            return text;
                        }
                    },
                    {
                        field: 'createDate',
                        title: '关联时间',
                        sortable:true
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.bigCode
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                                + row.id
                                + '\')"><i class="fa fa-key"></i></a> ';
                            return d;
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type: 2,
        title: '增加关联',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/product' // iframe的url
    });
}

function addProduct() {
    layer.open({
        type: 2,
        title: '关联并入库',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/bindProductInStock' // iframe的url
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

function remove(bigCode) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'bigCode': bigCode
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
            ids[i] = row['bigCode'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "bigCodes": ids
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

function printBigCode(codeType) {
    console.log(codeType);
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要打印的数据");
        return;
    }
    // 遍历所有选择的行数据，取每条数据对应的ID
    var bigCodes = new Array();
    $.each(rows, function (i, row) {
        bigCodes[i] = row['bigCode'];
    });
    console.log(bigCodes);

    var LODOP = getLodop();
    LODOP.PRINT_INIT()
    LODOP.SET_PRINTER_INDEX("2120TU(标签)") //指定打印机名称

    for (var i = 0; i < bigCodes.length; i++) {
        if (codeType == 0) {
            LODOP.ADD_PRINT_BARCODE("0.2cm", "0.4cm", "38mm", "15mm", "128C", bigCodes[i])
            LODOP.ADD_PRINT_TEXT("2cm", "0.3cm", "100%", "100%", getNowFormatDate() + "/溯源云平台")
        } else if (codeType == 1) {
            LODOP.ADD_PRINT_BARCODE("0.2cm", "1.1cm", "25mm", "25mm", "QRCode", bigCodes[i])
            // LODOP.ADD_PRINT_TEXT("2.5cm","0.3cm","100%","100%",getNowFormatDate()+"/溯源云平台")
        }
        LODOP.PRINT(); //直接打印
    }
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}


function getSmallRecordListVOByBigCode(bigCode){
    console.log(bigCode)
    var text = "";
    $.ajax({
        type: 'GET',
        data: {
            "bigCode": bigCode
        },
        url: prefix + '/getSmallRecordListVOByBigCode',
        success: function (r) {
            var smallCode = r.data.smallCodes

            text +=   "<ul class='list-group'>";
            for (var i in  smallCode) {
                var item = smallCode[i];
                text += "<li class='list-group-item'><span>" + item + "</span>&nbsp;&nbsp;" +
                    "</li>";
            }
            text+="</ul>"
        }
    });
    return text;
}

$(document).on("click", ".box-header span", function () {
    var box = $(this).parents("div[class=box]");
    var body = $(box).find(".box-body");
    if ($(body).hasClass("hidden")) {
        $(body).removeClass("hidden");
        $(this).text("收起↑");
    } else {
        $(this).text("展开↓");
        $(body).addClass("hidden");
    }
});