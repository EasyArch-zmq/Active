// const BASE_URL = 'http://121.199.21.197:63394'
window.onload=function () {

    $.ajax({
        url: `${BASE_URL}` + '/CityAddress',
        method: 'post',
        contentType: "application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log(res[0])
            console.log(res.length)
            let pro, options;
            for(let i=0; i<res.length; i++)
            {  pro =$("#city");
                options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
            pro.append(options);
        }
    })

    $('#city').change(function(){
        var opt=$("#city").val();
        console.log(opt)
        // var d=[{Province:opt}]
        $.ajax({
            url: `${BASE_URL}` + '/CountyAddress',
            // data: d,
            data: '{ "city": "'+opt+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res);
                let pro, options;
                for(let i=0; i<res.length; i++)
                {  pro =$('#country');
                    options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
                pro.append(options);
            }
        })
    });
    $('#country').change(function(){
        var city=$("#city").val();
        var pro=$("#country").val();
        $.ajax({
            url: `${BASE_URL}` + '/StreetAddress',
            data:'{"city":"'+city+'","county":"'+pro+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                let pro, options;
                for(let i=0; i<res.length; i++)
                {  pro =$("#street");
                    options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
                pro.append(options);
            }
        })
    });
    $("#street").change(function(){
        var city=$("#city").val();
        var pro=$("#country").val();
        // var town=$("#town").val();
        var str=$("#street").val();
        $.ajax({
            url: `${BASE_URL}` + '/SpecificAddress',
            data:'{"city":"'+city+'","county":"'+pro+'","street":"'+str+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                $("#auto").empty();
                for(var i=0;i<res.length;i++)
                    $("#auto").append('<option label="'+res[i]+'" value="'+res[i]+'"></option>');
            }
        })
    });

    $('#special_address').change(function(){
        var city=$("#city").val();
        var pro=$("#country").val();
        // var town=$("#town").val();
        var str=$("#street").val();
        var spe=$("#special_address").val();
        // alert(spe)
        $.ajax({
            url: `${BASE_URL}` + '/Mac_Address',
            data:'{"city":"'+city+'","county":"'+pro+'","street":"'+str+'","special_address":"'+spe+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                let pro, options;
                for(let i=0; i<res.length; i++)
                {  pro =$("#box");
                    options += '<option value="' + res[i].location+res[i].mac_address+ '" >' + res[i].location+res[i].mac_address+ '</option>';}
                pro.append(options);
            }
        })
    });

    $.ajax({
        url: `${BASE_URL}` + '/CityAddress',
        method: 'post',
        contentType: "application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log(res[0])
            console.log(res.length)
            let pro, options;
            for(let i=0; i<res.length; i++)
            {  pro =$("#city1");
                options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
            pro.append(options);
        }
    })

    $('#city1').change(function(){
        var opt=$("#city1").val();
        console.log(opt)
        // var d=[{Province:opt}]
        $.ajax({
            url: `${BASE_URL}` + '/CountyAddress',
            // data: d,
            data: '{ "city": "'+opt+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res);
                let pro, options;
                for(let i=0; i<res.length; i++)
                {  pro =$('#country1');
                    options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
                pro.append(options);
            }
        })
    });
    $('#country1').change(function() {
        var city=$("#city1").val();
        var pro=$("#country1").val();
        $.ajax({
            url: `${BASE_URL}` + '/StreetAddress',
            data:'{"city":"'+city+'","county":"'+pro+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                let pro, options;
                for(let i=0; i<res.length; i++)
                {  pro =$("#street1");
                    options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
                pro.append(options);
            }
        })
    });
    // $('#town1').change(function() {
    //     var city=$("#city1").val();
    //     var pro=$("#country1").val();
    //     var town=$("#town1").val();
    //     $.ajax({
    //         url: `${BASE_URL}` + '/StreetAddress',
    //         data:'{"city":"'+city+'","county":"'+pro+'","town":"'+town+'"}',
    //         method: 'post',
    //         contentType: "application/json",
    //         success: function (res) {
    //             console.log(res)
    //             let pro, options;
    //             for(let i=0; i<res.length; i++)
    //             {  pro =$("#street1");
    //                 options += '<option value="' + res[i] + '" >' + res[i]+ '</option>';}
    //             pro.append(options);
    //         }
    //     })
    // });
    $("#street1").change(function(){
        var city=$("#city1").val();
        var pro=$("#country1").val();
        // var town=$("#town1").val();
        var str=$("#street1").val();
        $.ajax({
            url: `${BASE_URL}` + '/SpecificAddress',
            data:'{"city":"'+city+'","county":"'+pro+'","street":"'+str+'"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                for(var i=0;i<res.length;i++)
                    $("#auto1").append('<option label="'+res[i]+'" value="'+res[i]+'"></option>');
            }
        })
    });
}