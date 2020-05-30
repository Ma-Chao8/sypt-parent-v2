$(function () {
    var mySwiper = new Swiper(".swiper-container", {
        loop: true,
        autoplay: 5000,
        pagination: '.swiper-pagination'
    });

    $("#phone").on("click", ".area-collapse dt", function () {
        if (!$(this).next("dd").is(":animated")) {
            $(this).next("dd").slideToggle("slow");
        }
    });
});