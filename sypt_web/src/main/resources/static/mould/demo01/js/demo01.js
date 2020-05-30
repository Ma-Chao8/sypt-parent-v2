$(function () {
    var mySwiper;
    if (window.iniSwiper) {
        mySwiper = iniSwiper()
    } else {
        mySwiper = new Swiper('.swiper-container', {
            loop: true, // 循环模式选项
            autoplay: 5000,
            pagination: {// 如果需要分页器
                el: '.swiper-pagination',
            }
        });
    }

    $("#fc-content").on("click", ".area02 dt", function () {
        $(this).find("dd").slideToggle("500")
    });

    $("#fc-content").on("click", "h5 a", function () {
        $(this).find(".zhankai").toggleClass("rotate90");
        $(this).parents('.fc-sub2').children('.contentBox3').slideToggle("500")
    })
});