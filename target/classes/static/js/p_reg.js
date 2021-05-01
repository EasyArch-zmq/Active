const BASE_URL = 'http://121.199.21.197:63394'
$(document).ready(() => {
    $('.signin_btn').click(() => {
        let username = $('#p_username').val()
        let password = $('#p_password').val()
        let address = "北京市，朝阳区，来广营街道"
        $.ajax({
            url:`${BASE_URL}`+'/p_register',
            method: 'post',
            data:'{"username":"'+username+'","password":"'+password+'","address":"'+address+'"}',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: (res) => {
                console.log(res)
            }
        })
    })
})