$(function () {

    var mySwiper = new Swiper(".swiper-container", {
        loop: true,
        autoplay: 5000,
        pagination: '.swiper-pagination',
    });


    $("#fc-content").on("click", ".area02 dl dt,.img_multi01 dl dt,.area04 dl dt,.img_single01 dl dt", function () {
        if (!$(this).parent("dl").find("dd").is(":animated")) {
            $(this).parent("dl").find("dd").slideToggle("slow");
            $(this).find(".iconfont").toggleClass('rotate90');
        }
    })
});