var prefix = getContext() + "/warehouse/warehouseRecord"
$(function () {
    getWarehouseId();
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                showRefresh: true,
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

                sortable: true,
                sortName: 'createDate',
                sortOrder: 'desc',

                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                search: true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParamsType: "",

                // showExport: true,//显示导出按钮
                // exportDataType: "selected",//导出所选择的数据
                // exportTypes: ['excel'],//到处excel
                // Icons: 'fa-caret-square-down',//导出按钮图标
                // exportOptions:{
                //     ignoreColumn: [0,9],  //忽略某一列的索引
                //     fileName: '入库列表',  //文件名称设置
                //     worksheetName: '入库列表',  //表格工作区名称
                //     tableName: '入库列表',
                //     excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                //     // onMsoNumberFormat: DoOnMsoNumberFormat
                // },

                // //设置为limit则会发送符合RESTFull格式的参数
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        pageNumber: params.pageNumber,
                        pageSize: params.pageSize,
                        searchName: params.searchText,
                        sortName : params.sortName,
                        sortOrder : params.sortOrder,
                        warehouseId:$('#warehouseId option:selected').val()
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
                    	checkbox : true
                    },
                    {
                        field: 'id',
                        title: '编号',
                        align: "center"
                    },
                    {
                        field: 'productName',
                        title: '产品名称',
                        align: "center"
                    },
                    {
                        field: 'productSpec',
                        title: '产品规格',
                        align: "center"
                    },
                    {
                        field: 'type',
                        title: '码类型',
                        align: "center",
                        formatter : function (value, row, index) {
                            var type='';
                            if (value === 1){
                                type = '大码'
                            }else if(value === 2){
                                type = '小码'
                            }
                            return type;
                        }
                    },
                    {
                        field: 'number',
                        title: '数量',
                        align: "center"
                    },
                    {
                        field: 'username',
                        title: '操作人',
                        align: "center"
                    },
                    {
                        field: 'warehouseName',
                        title: '所属仓库',
                        align: "center"
                    },
                    {
                        field: 'createDate',
                        title: '入库时间',
                        align: "center",
                        sortable : true
                    },
                    {
                        title: '码详情',
                        align: "center",
                        formatter: function (value, row, index) {
                            // var text = "<div class='box'>" + "<div class='box-header'><span style='color: #0e90d2'>展开↓</span></div>" +
                            //     "<div class='box-body hidden'>" + "<ul class='list-group'>";
                            // for (var i in  row.warehouseRecordCode) {
                            //     var item = row.warehouseRecordCode[i];
                            //     text += "<li class='list-group-item'><span>" + item.smallCode + "</span>&nbsp;&nbsp;" +
                            //         "<a href='javascript:void(0)' operate='change' data='" + item.id + "'><i class='fa fa-edit'></i>变更</a>" +
                            //         "</li>";
                            // }
                            // text += "</ul></div></div>";
                            // var text = '<a class="btn btn-success btn-sm '+ s_status_h+'" href="#" title="查看码详情" ' +
                            //     'mce_href="#" onclick="selects(\'' + row.warehouseRecordCode + '\')"><i class="fa fa-unlock"></i></a>';

                            var text = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="查看码详情" onclick="selects(\''
                                + row.id
                                + '\')"><i class="fa fa-book"></i></a> ';
                            return text;
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function edit(list) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + list // iframe的url
    });
}
function selects(id) {
    layer.open({
        type: 2,
        title: '码详情',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/selects/' + id // iframe的url
    });
}

// $(document).on("click", ".box-header span", function () {
//     var box = $(this).parents("div[class=box]");
//     var body = $(box).find(".box-body");
//     if ($(body).hasClass("hidden")) {
//         $(body).removeClass("hidden");
//         $(this).text("收起↑");
//     } else {
//         $(this).text("展开↓");
//         $(body).addClass("hidden");
//     }
// });

// $(document).on("click", "a", function () {
//     switch ($(this).attr("operate")) {
//         case "change": {
//             //console.log(this);
//             smallCodeChange($(this).attr("data"));
//             break;
//         }
//     }
// });

// function smallCodeChange(id) {
//     layer.confirm('你是否要变更当前信息？变更信息可能会造成一些不可控的风险，请谨慎操作！', {
//         btn: ['确定', '取消'] //按钮
//     }, function () {
//         layer.prompt({title: '请输入小码', formType: 0}, function (value, index) {
//             layer.close(index);
//             handleChange(id, value);
//         });
//
//
//     }, function () {
//         //
//
//     });
//
//
//     // function handleChange(code_id, code) {
//     //
//     //     if (!code) {
//     //         layer.msg("小码不能为空");
//     //         return
//     //     }
//     //     var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
//     //     $.ajax({
//     //         url: prefix + "/change",
//     //         type: "POST",
//     //         dataType: "JSON",
//     //         data: {id: id, smallCode: code},
//     //         success: function (result) {
//     //             layer.closeAll();
//     //             layer.msg(result.message);
//     //             if (result.success) {
//     //                 setTimeout(function () {
//     //                     window.location.reload();
//     //                 }, 2000);
//     //             }
//     //         },
//     //         error: function (result) {
//     //             layer.alert("网路错误，请稍后重新操作", {
//     //                 closeBtn: 0
//     //             });
//     //             layer.closeAll();
//     //         }
//     //
//     //     })
//     //
//     //
//     // }
//
//     function resetPwd(id) {
//     }
// }