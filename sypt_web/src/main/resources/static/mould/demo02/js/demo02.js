$(function () {

    mySwiper = new Swiper(".swiper-container", {
        loop: true,
        autoplay: 5000,
        // 如果需要分页器
        pagination: '.swiper-pagination',
        //autoplayDisableOnInteraction: false,

    });

    $(document).on("click", ".area dt", function () {
        if (!$(this).next("dd").is(":animated")) {
            $(this).next("dd").slideToggle("slow");
        }
    });
});

