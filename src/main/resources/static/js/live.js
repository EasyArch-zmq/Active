const BASE_URL = 'http://121.199.21.197:63394'

//设置范围值
function  fanwei() {

    let red=document.getElementById("red").value;
    let green=document.getElementById("green").value;
    var city=$("#city").val();
    var county=$("#country").val();
    var town=$("#town").val();
    var street=$("#street").val();
    var special_address=$("#special_address").val()===""?"null":$("#special_address").val();
    var box=$('#box').val();
    let addressall = city + "," + county + "," + town + "," + street+","+special_address;
    // let mac = $("#mac").val();
    console.log(red)
    $.ajax({
        url: `${BASE_URL}`+"/insertColorValue",
        method:'post',
        data:'{"red":"'+red+'","green":"'+green+'","mac_address":"'+box+'"}',
        contentType:"application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: (res) => {
            // console.log("ssssssssssokla")
            alert("保存成功！")
            // document.getElementById('state').innerHTML = "保存成功！";
            // $('#state').hidden
        }
    })

    console.log('{"red":"'+red+'","green":"'+green+'"}');
}
