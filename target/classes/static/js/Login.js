const BASE_URL = 'http://121.199.21.197:63394'
$(document).ready(() => {
    $('#g_Login').click(() => {
        let username = $('#user_name').val()
        let password = $('#user_pwd').val()
        console.log(username+password)
        $.ajax({
            url: `${BASE_URL}`+'/g_login',
            data: '{ "username": "'+username+'", "password": "'+password+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                if(res === 'F'){
                    alert("登陆失败，用户名或密码错误！")}

                else{
                        window.localStorage.setItem('g_username', $('#user_name').val());
                        window.localStorage.setItem('token', res.split(':')[1]);
                        window.location.href = "http://121.199.21.197:63394/nav"
                    }
            }
        })
    })
})

$(document).ready(() => {
    $('#p_Login').click(() => {
        let username = $('#user_name1').val()
        let password = $('#user_pwd1').val()
        console.log(username+password)
        $.ajax({
            url: `${BASE_URL}`+'/p_login',
            method: 'post',
            data: '{ "username": "' + username + '", "password": "' + password + '" }',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res);
                alert(res.token)
                if (res === 'F') {
                    alert("登陆失败，用户名或密码错误！")

                } else {
                    window.localStorage.setItem('p_username', $('#user_name1').val());
                    window.localStorage.setItem('p_token', res.split(':')[1]);
                    window.location.href = "http://121.199.21.197:63394/p_main"
                }
            }
        })
    })
})
function PwdLogin() {
    var login = document.getElementsByClassName("login_con");
    login[0].classList.remove("hidden");
    login[0].classList.add("show");
    login[1].classList.remove("show");
    login[1].classList.add("hidden");
    var tags = document.getElementsByClassName("top_tag");
    tags[0].classList.add("active");
    tags[1].classList.remove("active");


    // ad.style.backgroundImage='url(https://static.zcool.cn/v1.1.43/passport4.0/images/login-ground.jpg)';
}
function QrcodeLogin() {
    var login = document.getElementsByClassName("login_con");
    login[0].classList.remove("show");
    login[0].classList.add("hidden");
    login[1].classList.remove("hidden");
    login[1].classList.add("show");
    var tags = document.getElementsByClassName("top_tag");
    tags[1].classList.add("active");
    tags[0].classList.remove("active");

}