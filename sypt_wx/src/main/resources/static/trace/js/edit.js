var contextRoot = $("meta[name=ctxPath]").attr("content");
/*
 * 这个参数名字不要变动
 */
var fc_tplObject;
$(function () {
    var titleColr = {};
    $("#subchangeColor").on("click", "input", function () {
        var titleIcon = $("#ImgSlect option:selected").val();
        var titleBg = $("#bgSlect option:selected").val();
        titleColr.titleIcon = titleIcon;
        titleColr.titleBg = titleBg;
        var baseUrl = "/img/bgIcon/";
        if ($("#phone").find("#fc-content").length > 0) {
            $("#phone #fc-content").find('.fc-sub1').each(function () {
                $(this).find("img:first").attr("src", baseUrl + titleIcon + ".png")
                $(this).css({
                    "backgroundImage": "url(" + baseUrl + "Bg_" + titleBg + ".png)"
                })
            })
        }
    });

    $("#ImgSlect select").msDropDown();
    $("#Imglist li").bind("DOMNodeInserted", function (e) {
    });

    //console.log($("#bgSlect option").eq(2).data("bgcolor"))
    $("#bgSlect option").each(function () {
        var $color = $(this).data("bgcolor");
        $(this).css({
            "backgroundColor": "#" + $color,
            "height": "20px"
        })
    });
    var timeflag = false;
    $("#modelist").on("click", 'li .addIcon', function () {

        if (timeflag) {
            return;
        }
        if (!fc_tplObject) {
            alert("请稍等片刻，正在努力加载。。。")
            return;
        }
        var $Id = $(this).siblings(".dragIcon").attr("parentId")
        var appendHTML = $.grep(fc_tplObject, function (item, index) {
            if (item.style == $Id) {
                return item;
            }
        })[0].sub;

        var arr = []
        var flag = true
        $("#phone").children().find(".area").each(function () {
            if (arr.indexOf($(this).attr("name")) < 0) {
                arr.push($(this).attr("name"))
                flag = true
            } else {
                flag = false
            }
        })

        if (flag) {
            $("#phone #fc-content-body").append(appendHTML)
        } else {
            $("#phone #fc-content-body >div.area").eq(-2).after(appendHTML)
        }

        var scrollTop = $('#phone #fc-content-body .area').last().offset()
        var $bodyTop = $('#phone #demoTpl').scrollTop()
        var $height = $('#phone #demoTpl').height()
        timeflag = true;
        $('#phone #demoTpl').animate({"scrollTop": (scrollTop.top + $bodyTop - $height + 100) + "px"}, 500, function () {
            timeflag = false;
        })
    });

    ///加载相应的demo样式
    loadMould();

    /**
     * 加载模板
     */
    function loadMould() {
        var traceFileId = $("meta[name=traceFileId]").attr("content");
        $("#phone").empty();
        $("#phone").load(contextRoot + "trace/view/mould/edit/" + traceFileId, loadFinish);
    }

    /**
     * 模板加载完成时回调
     */
    function loadFinish() {
        //初始化template的html模板，获取多级标题的模板
        fc_tplObject = getTemplate($("#phone").children());
        //创建左侧样式列表
        creatList(fc_tplObject);
        //确定可编辑区域
        $("#phone #fc-content-body").find(".fc-sub1,.fc-sub2,.fc-sub3").find('span').attr("contenteditable", true)
        $("#fc-content-body img:not(dt img)").attr("contenteditable", true);
        $("#phone #fc-content-body").on("DOMNodeInserted", function (event) {
            if ($(event.target).is("img")) {
                $(event.target).attr("contenteditable", true)
            } else {
                $(event.target).find('span,img:not(dt img)').attr("contenteditable", true)
            }
        });
        bindDrag("#modelist li .dragIcon");
        bindDrag("#Imglist li .btn", 'images');
        bindDrop("#fc-content-body", fc_tplObject)
    }

    /**
     * 加载图片
     */
    var page = 0;
    $(document).on("click", "a", function () {
        switch ($(this).attr("data-operate")) {
            case "refreshImg": {
                loadImg(true);
                break;
            }
            case "loadMore": {
                loadImg(false);
                break;
            }
        }
    });

    /* footer operate function */
    $("#phone").on("mousedown", '.footer-layout .footer-item', function (e) {
        if (e.which == 3) {
            $.smartMenu.remove();

            $(this).smartMenu([[{
                text: "编辑",
                func: function () {
                    //console.log(this);
                    $("#myModal").modal('show');
                    $(document).find(".footer-edit-layout .footer-edit-content").empty();
                    $(this).parents(".footer-layout").find(".footer-item").each(function (i, v) {
                        var url = $(this).find("a").attr("href");
                        var title = $(this).find("h5").text();
                        var imgUrl = $(this).find("img").attr("src");
                        var imgId = $(this).find("img").attr("name");
                        //console.log(url, title, imgUrl);
                        var option = {
                            title: title,
                            url: url,
                            imgUrl: imgUrl,
                            imgId: imgId
                        };
                        $(document).find(".footer-edit-layout .footer-edit-content").append(buildLiHtml(option));
                    })
                }
            }]], {
                name: "footer"
            });
        }
    });

    $(document).on("click", "button", function () {
        switch ($(this).attr("data-operate")) {
            case "footerItemAdd": {//添加
                var li = buildLiHtml(null);
                $(this).parents("li").after(li);
                break;
            }

            case "footerItemDelete": {//删除
                $(this).parents("li").remove();
                break;
            }

            case "footerEditConfirm": {//确定
                renderFooterLayout();
                break;
            }
            default: {
                break;
            }
        }
    });

    var imgSelectWindow, targetImg;
    $(document).on("click", ".footer-edit-item-icon img", function () {
        targetImg = this;
        imgSelectWindow = layer.open({
            type: 2,
            title: '选择图片',
            shadeClose: false,
            shade: 0.8,
            area: ['100%', '100%'],
            content: contextRoot + 'common/sysFile/img/list' //iframe的url
        });
    });

    window.imgSelect = function (ele) {
        //console.log(ele);
        if (onMenuFinish) {
            onMenuFinish(ele);
        }

        if (imgSelectWindow) {
            layer.close(imgSelectWindow);
        }

        if (ele && targetImg) {
            $(targetImg).attr("name", $(ele).find("img").attr("id"));
            $(targetImg).attr("src", $(ele).find("img").attr("src"));
        }
    };

    loadImg(true);

    function loadImg(isRefresh) {
        if (isRefresh) {
            page = 1;
            $("#Imglist ul").empty();
        }
        $.ajax({
            url: contextRoot + "file/list",
            type: "GET",
            dataType: "JSON",
            data: {
                pageNumber: page,
                pageSize: 50,
                type: "image"
            },
            success: function (result) {
                if (result.code === 0 && result.data) {
                    var records = result.data.records;
                    if (!records || records.length < 1) {
                        if (!isRefresh) {
                            layer.msg("没有更多数据了");
                        }
                        return;
                    }
                    page++;
                    renderImgList(records);
                    bindDrag("#Imglist li .btn", 'images');
                }
            },
            error: function (error) {
                console.log(error);
            }
        })
    }

    /**
     * 渲染图片模块
     *
     * @param datas
     */
    function renderImgList(datas) {
        if (!datas)
            return;

        for (var i in  datas) {
            var record = datas[i];
            var li = $("<li></li>");
            //
            var contentDiv = $('<div class="f-l pl-20"></div>');
            $(contentDiv).append('<img name="' + record.id + '" src="' + record.url + '" onclick="picboost(' + record.url + ')"/>');

            //
            var operateDiv = $('<div class="f-r pr-20"></div>');
            $(operateDiv).append('<input name="1" type="button" draggable="true"  value="增加" class="btn btn-success radius">');
            $(operateDiv).append('<input name="2" type="button" draggable="true" value="替换" class="btn btn-primary radius ml-10" >');
            //$(operateDiv).append('<input name="" type="button" draggable="true" value="删除" onclick="deleteImg()" class="btn btn-default radius ml-10">');
            //
            $(li).append(contentDiv);
            $(li).append(operateDiv);
            $("#Imglist ul").append(li);
        }
    }

    /**
     * 构建li元素标签
     * @param option
     * @returns {HTMLLIElement}
     */
    function buildLiHtml(option) {
        var img_icon = document.createElement("img");
        img_icon.setAttribute("title", "点击修改");
        img_icon.setAttribute("alt", "图标");
        if (option && option.imgUrl) {
            img_icon.setAttribute("src", option.imgUrl);
        } else {
            img_icon.setAttribute("src", "/img/placeholder.png");
        }
        if (option && option.imgId) {
            img_icon.setAttribute("src", options.imgId);
        }
        var div_item_icon = document.createElement("div");
        div_item_icon.setAttribute("class", "footer-edit-item-icon");
        div_item_icon.appendChild(img_icon);
        //
        var input_title = document.createElement("input");
        input_title.setAttribute("name", "title");
        input_title.setAttribute("type", "text");
        input_title.setAttribute("class", "form-control");
        input_title.setAttribute("autocomplete", "off");
        input_title.setAttribute("placeholder", "导航标题");
        if (option && option.title) {
            input_title.setAttribute("value", option.title);
        }

        var input_link = document.createElement("input");
        input_link.setAttribute("name", "link");
        input_link.setAttribute("type", "text");
        input_link.setAttribute("class", "form-control");
        input_link.setAttribute("autocomplete", "off");
        input_link.setAttribute("placeholder", "链接地址");
        if (option && option.url) {
            input_link.setAttribute("value", option.url);
        }
        var div_item_input = document.createElement("div");
        div_item_input.setAttribute("class", "footer-edit-item-input");
        div_item_input.appendChild(input_title);
        div_item_input.appendChild(input_link);
        //
        var btn_remove = document.createElement("button");
        btn_remove.innerHTML = "删除";
        btn_remove.setAttribute("class", "btn btn-danger");
        btn_remove.setAttribute("data-operate", "footerItemDelete");
        var btn_add = document.createElement("button");
        btn_add.innerHTML = "添加";
        btn_add.setAttribute("class", "btn btn-success");
        btn_add.setAttribute("data-operate", "footerItemAdd");
        var div_item_operate = document.createElement("div");
        div_item_operate.setAttribute("class", "footer-edit-item-operate");
        div_item_operate.appendChild(btn_remove);
        div_item_operate.appendChild(btn_add);
        //
        var li = document.createElement("li");
        li.setAttribute("class", "footer-edit-item");
        li.appendChild(div_item_icon);
        li.appendChild(div_item_input);
        li.appendChild(div_item_operate);
        //console.log(li);
        return li;
    }

    /**
     * 构建按底部链接项目
     * @param option
     * @returns {HTMLLIElement}
     */
    function buildFooterItem(option) {
        /* <li class='footer-item white'>
            <a href="">
                <img name="1" src='http://qny1.tianma315.com/FhIluG389dgH8RAwxHHxa6z9-Czf'/>
                <h5>公司简介</h5>
            </a>
        </li>*/
        var img = document.createElement("img");
        if (option && option.imgId) {
            img.setAttribute("name", option.imgId);
        }
        if (option && option.imgUrl) {
            img.setAttribute("src", option.imgUrl);
        }
        var h5 = document.createElement("h5");
        if (option && option.title) {
            h5.innerHTML = option.title;
        }
        var a = document.createElement("a");
        if (option && option.link) {
            a.setAttribute("href", option.link);
        }
        a.appendChild(img);
        a.appendChild(h5);
        //
        var li = document.createElement("li")
        li.setAttribute("class", "footer-item white");
        li.appendChild(a);
        return li;
    }

    /**
     * 重新渲染底部链接
     */
    function renderFooterLayout() {
        $(document).find(".footer-layout").empty();//清空内容
        //重新渲染
        $(".footer-edit-layout .footer-edit-content").find("li").each(function (i, ele) {
            //console.log(this, i, ele);
            var link = $(this).find("input[name=link]").val();
            var title = $(this).find("input[name=title]").val();
            var imgId = $(this).find("img").attr("name");
            var imgUrl = $(this).find("img").attr("src");

            var li = buildFooterItem({
                link: link,
                title: title,
                imgId: imgId,
                imgUrl: imgUrl
            });
            $(document).find(".footer-layout").append(li);
        });
        $("#myModal").modal('hide');
    }

    getPurchaseByUpdate(getPurchaseId());
    getReportByUpdate(getReportId());
});

//档案提交
function submitData() {
    reloadProduct(function (data) {
        var subIndex = layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            area: '516px',
            shadeClose: true,
            content: $('#tong'),
            btn: ['确定', '取消'],
            yes: function (index) {
                function buildData() {
                    var $title = $('input[name="title"]').val();
                    if ($title == '' || $title == null) {
                        layer.alert('请先输入标题');
                        return;
                    }

                    var $postData = pageToDate("#fc-content-body", ".area");
                    var $product_id = $("#product").find("option:selected").val();
                    var $reportId = $("#reportId").find("option:selected").val();
                    var $purchaseId = $("#purchaseId").find("option:selected").val();

                    /**
                     * 获取轮播图数据
                     *
                     * @returns {Array}
                     */
                    function buildBanners() {
                        var banners = [];
                        $(".swiper-container .swiper-wrapper .swiper-slide").each(function (index, item) {
                            var i = $(item).attr("data-swiper-slide-index");
                            var name = $(item).find("img").attr("name");
                            banners[i] = {"imgId": name};
                        });
                        return banners;
                    }

                    /**
                     * 获取底部链接
                     *
                     * @returns {Array}
                     */
                    function buildFootLinks() {
                        var $footLinkData = [];
                        $('.footer-layout .footer-item').each(function (i, v) {
                            var footLink = {};
                            footLink["title"] = $(v).find('h5').text();
                            footLink["link"] = $(v).find('a').attr('href');
                            footLink["iconUrl"] = $(v).find('img').attr('src');
                            footLink["imgId"] = $(v).find('img').attr('name');
                            $footLinkData.push(footLink);
                        });
                        return $footLinkData;
                    }

                    $postData["traceFileName"] = $title;
                    $postData["productId"] = $product_id;
                    $postData["reportId"] = $reportId;
                    $postData["purchaseId"] = $purchaseId;
                    $postData["banners"] = buildBanners();
                    $postData["footLinks"] = buildFootLinks();
                    $postData["introduce"] = $("textarea[name=introduce]").val();
                    $postData["traceFileId"] = $("meta[name=traceFileId]").attr("content");
                    return $postData;
                }

                //var loading = layer.load(2);
                //var data = JSON.stringify(buildData());

                var data = buildData();
                if (!data) {
                    return;
                }
                if (!data.traceFileName) {
                    layer.alert('请先输入标题');
                    return;
                } else if (!data.introduce) {
                    layer.alert('介绍语不能为空');
                    return;
                }
                var loading = layer.load(2);
                var dataJson = JSON.stringify(data);
                //console.log(data);
                $.ajax({
                    url: contextRoot + 'trace/edit',
                    type: 'POST',
                    dataType: 'JSON',
                    data: dataJson,
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        layer.close(loading);
                        if (result.success) {
                            layer.msg(result.msg);
                            layer.close(subIndex);
                        } else {
                            layer.alert(result.msg)
                        }
                    },
                    error: function (data) {
                        layer.close(loading);
                        layer.alert("网络请求失败，请稍后重新操作");
                    }
                });
            }
        });
    }, function (msg) {
        layer.alert(me);
    }, function (msg) {
        layer.alert(msg);
    });
}

//删除素材图片
function deleteImg(a, enclosure_id) {
    layer.confirm('确定要删除该图片吗？', {
        btn: ['删除', '取消'] //按钮
    }, function () {
        $.ajax({
            url: contextRoot + "user/traceability/json/delete/img/" + enclosure_id,
            type: 'get',
            dataType: 'json',
            success: function (result, statusText) {
                if (result.code === 0) {
                    layer.msg(result.msg);
                    $(a).parent().parent().remove();
                } else {
                    layer.alert(result.msg);
                }

            }, error: function (data) {
                layer.alert("网络错误，请稍后重新操作");
            }
        });
    });
}

/**
 * 重新加载产品下拉框
 *
 * @param success
 * @param error
 * @param exception
 */
function reloadProduct(success, error, exception) {
    var loading = layer.load(3);
    $('#product').empty();
    $.ajax({
        url: contextRoot + "product/all",
        dataType: "json",
        type: "GET",
        success: function (result) {
            if (result.code == 0) {
                if (success)
                    success(result.data);
                if (result.data && result.data.length > 0) {
                    $.each(result.data, function (i, v) {
                        $('#product').append('<option value="' + v.productId + '">' + v.productName + '</option>');
                    });
                }
            } else {
                if (error)
                    error("刷新产品列表失败");
            }
            layer.close(loading);
        },
        error: function (error) {
            if (exception)
                exception("网络错误，产品信息获取失败，请稍后重新操作");
            console.log(error);
            layer.close(loading);
        }
    });
}



