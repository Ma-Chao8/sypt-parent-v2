$(function () {
    var mySwiper = new Swiper(".swiper-container", {
        loop: true,
        autoplay: 5000,
        pagination: '.swiper-pagination'
    });

    $("#phone").on("click", ".area02 dt,.img_multi01 dt,.area05 dt,.img_multi02 dt,.area03 dt,.area04 dt", function () {
        if (!$(this).next("dd").is(":animated")) {
            $(this).next("dd").slideToggle("slow");
        }
    });
});