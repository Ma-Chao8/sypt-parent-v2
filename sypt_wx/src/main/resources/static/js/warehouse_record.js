/**
 * Created by TianMa-Android on 2019/2/28.
 */
(function (w, $, m, wx) {
    m.init();
    m('.mui-scroll-wrapper').scroll();




    //******************************************************************
    //**************************** 产品*********************************
    //******************************************************************
    //初始化产品数据
    $("#selectProduct").click(function () {
        //请求产品数据
        $("#productPopover .search-form input[name=product_name]").val("");
        m('#productPopover').popover('show');//show hide toggle
        getProducts(true)
    });

    //条件搜索产品
    $("#productPopover .search-form button[name=search]").click(function () {
        getProducts(true)
    });

    //加载更多产品
    $("#productPopover .load-more").click(function () {
        getProducts(false)
    });

    //选择产品
    $(document).on("click", "#productPopover ul li", function () {
        m('#productPopover').popover('hide');//show hide toggle
        var product_id = $(this).find("a").attr("data");
        var product_name = $(this).find("a").text();
        $("#selectProduct").text(product_name ? product_name : "选择产品");
        $("#selectProduct").attr("data", product_id ? product_id : "");
    });

    var productPage = 1;
    var pageSize =10;

    /**
     * 获取产品数据
     * @param isRefresh 是否是刷新操作
     */
    function getProducts(isRefresh) {
        var product_name = $("#productPopover .search-form input[name=product_name]").val();
        if (!product_name) {
            product_name = "";
        }

        if (isRefresh) {
            m("#productPopover .mui-scroll-wrapper").scroll().scrollTo(0, 0);
            $("#productPopover ul").empty();
            productPage = 1;
        }

        $.ajax({
            url: w.ctxPath + "product/page",
            type: "GET",
            dataType: "JSON",
            data: {start: productPage,pageSize:pageSize, productName: product_name},
            success: function (result) {
                if (result.success) {
                    var items = result.data.records;
                    if (items.length == 0) {
                        m.toast('没有更多数据了', {duration: 'short', type: 'div'});
                        return;
                    }
                    productPage++;
                    for (var index in items) {
                        var item = items[index];
                        $("#productPopover ul").append('<li class="mui-table-view-cell"><a data="' + item.productId + '">' + item.productName + '</a></li>')
                    }
                } else {
                    m.alert(result.message, "错误提示");
                }

            },
            error: function (result) {
                m.alert("网络请求错误，清稍后重新操作", "系统提示");
            }
        })
    }


    //******************************************************************
    //**************************** 扫码类型*********************************
    //******************************************************************


    //******************************************************************
    //**************************** 添加码*********************************
    //******************************************************************

    var TYPE_BIG_CODE = 1; //大码
    var TYPE_SMALL_CODE = 2; //小码
    $(document).on("click", ".mui-saomiao .mui-icon-add-saomiao", function () {
        var type = $("input[type='radio']:checked").val();
        if (!type) {
            m.alert("请选择扫码类型", "提示");
            return;
        }

        //扫码操作
        wx.scanQRCode({
            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                var result = getResult(res.resultStr); // 当needResult 为 1 时，扫码返回的结果
                if (type == TYPE_BIG_CODE) {
                    var bigCode = getBigCode(result);
                    if (!bigCode) {
                        m.alert("您输入的不是合法的码", "提示");
                        return;
                    }
                    addCdoe(TYPE_BIG_CODE, bigCode);
                } else if (type == TYPE_SMALL_CODE) {
                    var smallCode = getSmallCode(result);
                    if (!smallCode) {
                        m.alert("您输入的不是合法的码", "提示");
                        return;
                    }
                    addCdoe(TYPE_SMALL_CODE, smallCode);
                }
            }
        });

    });

    $(document).on("click", "button[name=add]", function () {
        if (addCdoe($("input[type='radio']:checked").val(), $("input[name=code]").val())) {
            $("input[name=code]").val("");
        }
    });

    //删除扫码记录
    $(document).on("click", "#codeList ul li delete", function () {
        $(this).parents("li").remove();
        computeCodeCount();
    });


    $(".footer button").click(function () {
        if ($(this).hasClass("mui-btn-clear")) {
            m.confirm("是否清空数据", "危险警告", function (callback) {
                if (callback.index == 1) {
                    clearCodeList();
                }
            });
        } else if ($(this).hasClass("mui-btn-confirm")) {
            m.confirm("是否提交数", "温馨提示", function (callback) {
                if (callback.index == 1) {
                    submitData();
                }
            });
        }
    });

    function getResult(resultStr) {
        var serial = resultStr.split(",");
        var result = serial[serial.length - 1];
        return result;
    }

    /**
     * 添加码
     * @param type
     * @param code
     */
    function addCdoe(type, code) {
        if (!type) {
            m.alert("请选择扫码类型", "提示");
            return false;
        }

        if (!code) {
            m.alert("请输入扫码内容", "提示");
            return false;
        }

        if (codeRepeat(code)) {
            m.alert("请勿重复扫码", "提示");
            return false;
        }
        $("#codeList ul").prepend('<li><code>' + code + '</code><type data="' + type + '">' + (type == TYPE_BIG_CODE ? "大码" : "小码") + '</type><delete style="cursor: pointer">删除</delete></li>');
        computeCodeCount();
        return true;
    }

    /**
     *
     * @param code
     * @returns {*}
     */
    function getBigCode(code) {
        var code_configs = app.config.big_code_configs;
        for (var index = 0; index < code_configs.length; index++) {
            var patten = code_configs[index];
            var codes = code.match(patten);
            if (codes && codes.length > 0) {
                return codes[1]
            }
        }
        return null;
    }


    function getSmallCode(code) {
        var code_configs = app.config.small_code_config;
        for (var index = 0; index < code_configs.length; index++) {
            var patten = code_configs[index];
            var codes = code.match(patten);
            if (codes && codes.length > 0) {
                return codes[1]
            }
        }
        return null;
    }


    /**
     * 判断码是否重复
     */
    function codeRepeat(code) {
        var codes = $(document).find("#codeList ul li code");
        for (var index = 0; index < codes.length; index++) {
            if (code === $(codes[index]).text()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算扫码数量
     */
    function computeCodeCount() {
        var bigCount = 0;
        var smallCount = 0;
        var codes = $(document).find("#codeList ul li");
        $("#codeCount").text(codes.length);
        for (var index = 0; index < codes.length; index++) {
            var li = codes[index];
            if ($(li).find("type").attr("data") == TYPE_BIG_CODE) {
                bigCount++;
            } else if ($(li).find("type").attr("data") == TYPE_SMALL_CODE) {
                smallCount++;
            }
        }
        $("#bigCodeCount").text(bigCount);
        $("#smallCodeCount").text(smallCount);
    }


    /**
     * 获取数据
     * @returns {{agent_id: *, agent_name: (*|XMLList), products: [*]}}
     */
    function getData() {
        var codes = [];
        var codeList = $(document).find("#codeList ul li");
        for (var index = 0; index < codeList.length; index++) {
            var li = codeList[index];
            if ($(li).find("type").attr("data") == TYPE_BIG_CODE) {
                codes[index] = {
                    type: $(li).find("type").attr("data"),
                    bigCode: $(li).find("code").text()
                }
            } else if ($(li).find("type").attr("data") == TYPE_SMALL_CODE) {
                codes[index] = {
                    type: $(li).find("type").attr("data"),
                    smallCode: $(li).find("code").text()
                }
            }
        }

        var product = {
            productId: $("#selectProduct").attr("data"),
            productName: $("#selectProduct").text(),
            codes: codes
        };

        var codeData = {
            // agentId: $("#selectAgent").attr("data"),
            // agentName: $("#selectAgent").text(),
            products: [product]
        };

        //console.log(codeData);
        return codeData;
    }

    /**
     * 提交数据
     */
    function submitData() {
        console.log(getData())
        //判断产品是否为空
        if (getData().products[0].productId=='') {
            m.alert("请选择产品", "提示");
            return false;
        }
        if (getData().products[0].codes.length==0) {
            m.alert("请添加产品大码或小码", "提示");
            return false;
        }

        $.ajax({
            url: ctxPath + "warehouseRecord/save",
            type: "POST",
            dataType: "JSON",
            data: JSON.stringify(getData()),
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.success) {
                    m.toast(result.message, {duration: 'short', type: 'div'});
                    clearCodeList();
                } else {
                    m.alert(result.message);
                }
            }
            ,
            error: function (result) {
                m.alert("网络请求错误，请稍后重新操作", "错误提示");
            }
        })
    }

    /**
     * 清空扫码列表
     */
    function clearCodeList() {
        $("#codeList ul").empty();
        computeCodeCount();
    }

})(window, jQuery, mui, wx);