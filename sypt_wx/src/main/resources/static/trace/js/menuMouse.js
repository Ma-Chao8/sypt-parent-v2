(function (w) {
    var imageMenuData = [
        [{
            text: "删除图片",
            func: function () {
                //console.log(this);
                if ($(this).parent().is('a')) {
                    ($(this).parent("a").siblings().length <= 0) ? alert("请不要删空") : $(this).parent("a").remove();
                } else {
                    ($(this).siblings().length <= 0) ? alert("请不要删空") : $(this).remove();
                }
                checkele("#fc-banner", "img");
            }
        }, {
            text: "新增图片",
            func: function (e) {
                //console.log(this);
                showImgWindow(this, TYPE_CONTENT_ADD);
            }
        }, {
            text: "替换图片",
            func: function (e) {
                //console.log(this);
                showImgWindow(this, TYPE_CONTENT_REPLACE);
            }

        }
        ]
    ];

    var LogoMenuData = [[{
        text: "删除图片",
        func: function () {
            $swiperImg = $(this).attr("name");
            currentThat = $("#phone").find('#fc-head .swiper-slide img[name=' + $swiperImg + ']');
            //if(currentThat.parent().attr('data-swiper-slide-index') == currentThat.parent().next().attr('data-swiper-slide-index')){
            if ($('.swiper-wrapper>div').length == 3) {
                layer.confirm("最后一张图片删除之后将没有轮播图,确定删除吗?", {
                    btn: ['确定', '取消']
                }, function () {
                    $('.swiper-wrapper').empty();
                    layer.close(layer.index);
                });
            } else {
                $(currentThat).parent(".swiper-slide").remove()
            }
        }
    }, {
        text: "新增图片",
        func: function (e) {
            showImgWindow(this, TYPE_BANNER_ADD)
        }
    }, {
        text: "替换图片",
        func: function () {
            showImgWindow(this, TYPE_BANNER_REPLACE)
        }
    }]];

    var subMenuDate1 = {
        text: "删除当前一行",
        func: function () {
            if ($(this).is(".fc-sub3 span")) {
                if ($(this).parent().parent().siblings().length <= 0) {
                    alert("请不要删空")
                } else {
                    $(this).parent().parent().remove()
                }
            } else {
                if ($(this).parents("li").siblings().length <= 0) {
                    alert("请不要删空")
                } else {
                    $(this).parents("li").remove()
                }
            }

            checkele("#fc-content", "dl")
        }
    }

    var subMenuDate2 = {
        text: "删除当前区域",
        func: function () {
            //console.log(this)
            if ($(this).is(".fc-sub2 span")) {
                $(this).parents('.area').remove()
            }
            if ($(this).is(".fc-sub3 span")) {
                $(this).parents(".fc-sub2").remove()
            }
            if ($(this).is("dl dt span")) {
                $(this).parents(".area").remove()
            }
            checkele("#fc-content", "dl")
        }
    };

    var mySwiper;
    w.iniSwiper = function () {
        if (!mySwiper) {
            mySwiper = new Swiper(".swiper-container", {
                loop: true,
                autoplay: 5000,
                // 如果需要分页器
                pagination: '.swiper-pagination',
                //autoplayDisableOnInteraction: false,
            });

        }
        return mySwiper;
    };

    $("#phone").on("mousedown", '#fc-content-body dd img', function (e) {
        if (e.which == 3) {
            var self = this;
            $.smartMenu.remove();
            $(this).smartMenu(imageMenuData, {
                name: "image",
                offsetX: 2,
                offsetY: 2,
            });
        }
    });

    // 轮播图右击
    $("#phone").on("mousedown", '#fc-head .swiper-slide img', function (e) {
        if (e.which == 3) {
            var self = this;
            $.smartMenu.remove();
            $(this).smartMenu(LogoMenuData, {
                name: "image",
                offsetX: 2,
                offsetY: 2,
            });

        }
    });

    var subMenuDate;
    $("#phone").on("mousedown", '#fc-content-body h5 span,#fc-content-body dt span', function (e) {
        if (e.which == 3) {
            $.smartMenu.remove();
            if ($(this).is("dt span")) {
                subMenuDate = [
                    [subMenuDate2]
                ]
            } else {
                subMenuDate = [
                    [subMenuDate1, subMenuDate2]
                ]
            }
            $(this).smartMenu(subMenuDate, {
                name: "ctrlP"
            });
        }
    });
    //
    var TYPE_CONTENT_ADD = 1, TYPE_CONTENT_REPLACE = 2, TYPE_BANNER_ADD = 3, TYPE_BANNER_REPLACE = 4;
    var imgSelectWindow, imgTarget, select_type;

    /**
     *
     * @param e
     * @param type
     */
    var showImgWindow = function (e, type) {
        select_type = type;
        //console.log(this, e, type);
        imgTarget = e;
        imgSelectWindow = layer.open({
            type: 2,
            title: '选择图片',
            shadeClose: false,
            shade: 0.8,
            area: ['100%', '100%'],
            content: contextRoot + 'common/sysFile/img/list' //iframe的url
        });
    };

    /**
     *
     * @param ele
     */
    function addImg(ele) {
        var newImg = $(imgTarget).clone(true);
        $(newImg).attr("name", $(ele).find("img").attr("id"));
        $(newImg).attr("src", $(ele).find("img").attr("src"));
        $(imgTarget).after(newImg);
    }

    /**
     *
     * @param ele
     */
    function replaceImg(ele) {
        $(imgTarget).attr("name", $(ele).find("img").attr("id"));
        $(imgTarget).attr("src", $(ele).find("img").attr("src"));
    }

    /**
     *
     * @param ele
     */
    function addBannerImg(ele) {
        var text = '<div class="swiper-slide"><img name="'
            + $(ele).find("img").attr("id")
            + '" src="'
            + $(ele).find("img").attr("src")
            + '"></div>';

        var parent = $(imgTarget).parents("div[class=swiper-wrapper]");
        $(parent).prepend(text);
        iniSwiper().init();
    }

    /**
     * 替换图片
     * @param ele
     */
    function replaceBannerImg(ele) {
        //$("img[name='" + $(imgTarget).attr("name") + "']").attr("id", $(ele).find("img").attr("id"));
        $("img[name='" + $(imgTarget).attr("name") + "']").attr("name", $(ele).find("img").attr("id"));
        $("img[name='" + $(imgTarget).attr("name") + "']").attr("src", $(ele).find("img").attr("src"));
    }

    /**
     * 图片选择回调
     * @param ele
     */
    w.onMenuFinish = function (ele) {
        if (imgSelectWindow) {
            layer.close(imgSelectWindow);
        }
        if (ele && imgTarget && select_type) {
            switch (select_type) {
                case TYPE_CONTENT_ADD: {
                    addImg(ele);
                    break
                }

                case TYPE_CONTENT_REPLACE: {
                    replaceImg(ele);
                    break
                }

                case TYPE_BANNER_ADD: {
                    addBannerImg(ele);
                    break
                }

                case TYPE_BANNER_REPLACE: {
                    replaceBannerImg(ele);
                    break
                }
            }
        }
    }
})
(window);