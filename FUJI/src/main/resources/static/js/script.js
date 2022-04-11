
//往上置頂TOP
$(document).ready(function () {
    $(".scroll-top").on('click', function () {
        $('html,body').scrollTop(0);
    });
    $(window).on('scroll', function () {
        if ($(this).scrollTop() > 200) {
            $('.scroll-top').fadeIn("fast");
        } else {
            $('.scroll-top').stop().fadeOut("fast");
        }
    });
    
});
// 註冊會員
$(document).ready(function () {
    $("#registerMember").on('shown.bs.modal', function () {
        $(this).find('input[type="text-name"]').focus();
    });
});
//會員登入
$(document).ready(function () {
    $("#loginModal").on('shown.bs.modal', function () {
        $(this).find('input[type="text-account"]').focus();
    });
});
function showLoginModal() {
    var myModal = new bootstrap.Modal(document.getElementById('loginModal'))
    myModal.show()
}
function showCartModal() {
  $('#cartModal').modal('show')
}
function hideCartModal() {
  $('#cartModal').modal('hide')
}
