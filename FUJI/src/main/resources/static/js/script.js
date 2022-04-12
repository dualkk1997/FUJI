
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
function postData(url, data) {
    // Default options are marked with *
    return fetch(url, {
      body: JSON.stringify(data), // must match 'Content-Type' header
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      credentials: 'same-origin', // include, same-origin, *omit
      headers: {
        'user-agent': 'Mozilla/4.0 MDN Example',
        'content-type': 'application/json'
      },
      method: 'POST', // *GET, POST, PUT, DELETE, etc.
      mode: 'cors', // no-cors, cors, *same-origin
      redirect: 'follow', // manual, *follow, error
      referrer: 'no-referrer', // *client, no-referrer
    })
     // 輸出成 json
}
function getData(url){
    return fetch(url, {
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, same-origin, *omit
        headers: {
          'user-agent': 'Mozilla/4.0 MDN Example',
          'content-type': 'application/json'
        },
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, cors, *same-origin
        redirect: 'follow', // manual, *follow, error
        referrer: 'no-referrer', // *client, no-referrer
      })
}