/**
 * Created by TianMa-Android on 2019/3/13.
 */
(function (w, $, m) {
    $(document).on("click", "a[data-target=collapse]", function () {
        if ($(this).parents(".box").find(".product-box").hasClass("collapse")) {
            $(this).parents(".box").find(".product-box").removeClass("collapse");
            $(this).parents(".box").find(".product-box").attr("style", "display:block;");
            $(this).text("折叠")
        } else {
            $(this).parents(".box").find(".product-box").addClass("collapse");
            $(this).parents(".box").find(".product-box").attr("style", "display:none;");
            $(this).text("展开")
        }
    });

    getList(true);

    /**
     * 获取货单列表数据
     *
     * @param isRefresh 刷新
     */
    var page = 1;
    function getList(isRefresh) {
        if (isRefresh) {
            page = 1;
            $("#invoice_list").empty();
        }

        $.ajax({
            url: ctxPath + "invoice/deliver/list",
            type: "GET",
            dataType: "JSON",
            data: {page: page},
            success: function (result) {
                if (result.success) {
                    if (!result.data || result.data.length == 0) {
                        m.toast("没有更多数据了", {duration: 'short', type: 'div'});
                        return;
                    }
                    page++;
                    var list = result.data;
                    console.log(list);
                    for (var i = 0; i < list.length; i++) {
                        var item = list[i];
                        var text =
                            '<li class="mui-table-view-cell">' +
                            '<div class="box">' +
                            '<div>' +
                            '<span>经销商:</span>' +
                            '<span>' + item.agentName + '</span>' +
                            '</div>' +
                            '<div class="box-flex">' +
                            '<div>' +
                            '<span>货单号：</span>' +
                            '<span>' + item.invoiceId + '</span>' +
                            '</div>' +
                            '<div>' +
                            '<span>发货时间：</span>' +
                            '<span>' + new Date(item.deliverDate).Format("yyyy-MM-dd") + '</span>' +
                            '</div>' +
                            '</div>' +
                            '<div class="box-flex">' +
                            '<div><span>联系人：</span><span>' + item.linkman + '</span></div>' +
                            '<div><span>联系电话：</span><span>' + item.tel + '</span></div>' +
                            '</div>' +
                            '<div><span>收货地址：</span><span>'+item.deliverAddres+'</span></div>' +
                            '<div class="product-box collapse">' +
                            '<div class="product-box-header">产品信息</div>' +
                            '<div class="product-box-body">' +
                            '<table width="100%">' +
                            '<thead>' +
                            '<tr>' +
                            '<th width="40%" style="text-align: center">产品名称</th>' +
                            '<th width="60%" style="text-align: center">发货码</th>' +
                            '</tr>' +
                            '</thead>' +
                            '<tbody>';

                        var products = item.products;
                        for (var index in  products) {
                            var product = products[index];
                            var productName = product.productName;
                            var tr = '<tr><td style="text-align: center">' + productName + '</td><td style="text-align: center">';
                            var codes = product.codes;
                            for (var pIndex in codes) {
                                var code = codes[pIndex];
                                if (code.bigCode) {
                                    tr += '<div><span>' + code.bigCode + '</span>&nbsp;&nbsp;<span>(大码)</span></div>';
                                } else if (code.smallCode) {
                                    tr += '<div><span>' + code.smallCode + '</span>&nbsp;&nbsp;<span>(小码)</span></div>';
                                }
                            }
                            tr += '</td></tr>';
                            text += tr;
                        }

                        text += '</tbody>' +
                            '</table>' +
                            '</div>' +
                            '</div>' +
                            '<div class="mui-text-center" style="margin-top: 15px; cursor: pointer; color: #ff5053">' +
                            '<a data-target="collapse">展开</a>' +
                            '</div>' +
                            '</div>' +
                            '</li>';
                        $("#invoice_list").append(text);
                    }
                } else {
                    m.toast(result.message, {duration: 'short', type: 'div'});
                }
            },
            error: function (result) {
                console.log(result);
                m.alert("网络请求失败，请稍后重新操作", "错误提示");
            }
        })
    }

    $(document).on("click", ".load-more", function () {
        getList(false);
    })

})(window, jQuery, mui);