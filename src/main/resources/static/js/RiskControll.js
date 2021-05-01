const myChart = echarts.init(document.getElementById("bar"));
const BASE_URL = 'http://121.199.21.197:63394'
var people=[];
var time=[];
var resd;
var requer;
var numtitle="风险防控图";
window.onload=function() {
    // let username = $('#username').val();
    let username =window.localStorage.getItem('g_username')
    console.log(username)
    // let username = "ocbbc";
    $.ajax({
        url: `${BASE_URL}` + '/selectDefaultNumber',
        data: '{ "username": "' + username + '"}',
        method: 'post',
        contentType: "application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log(res)
            resd=res;
            numtitle=resd[0].info_inCons_List[0].mac_address;
                $("#change").empty();
                var k=document.createElement('span');
                k.innerHTML=res[0].construction+":";
                k.className="aa";
                k.style.marginLeft="20px"
                $('#change').append(k)
            // $("#picuter").src=res.piture_url;
                var li = document.getElementById('cars');
                for (let i = 0; i < res[0].info_inCons_List.length; i++) {
                    var a = document.createElement('button');
                        a.innerHTML =resd[0].info_inCons_List[i].location;
                    a.className = "sela";
                    a.id = "uk"+i;
                    a.onclick=ak(i);
                    if(i===0) a.style.color="#0FB9EF";
                    $('#change').append(a)
                    // li.append(new Option(,resd[0].info_inCons_List[i].location));
                }
            // }
            for (let i  = 0; i < res[0].info_inCons_List[0].list_inCons.length; i++) {
                time.push(res[0].info_inCons_List[0].list_inCons[i].time);
            }
            console.log(time)
            for (let i = 0; i <res[0].info_inCons_List[0].list_inCons.length; i++) {
                people.push(res[0].info_inCons_List[0].list_inCons[i].num);
            }

            let option = SettingOption(time,people)
            myChart.setOption(option)
        }
    })

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
            for (let i = 0; i < res.length; i++) {
                pro = $("#city");
                options += '<option value="' + res[i] + '" >' + res[i] + '</option>';
            }
            pro.append(options);
        }
    })
    $('#city').change(function () {
        var opt = $("#city").val();
        console.log(opt)
        // var d=[{Province:opt}]
        $.ajax({
            url: `${BASE_URL}` + '/CountyAddress',
            // data: d,
            data: '{ "city": "' + opt + '"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res);
                let pro, options;
                for (let i = 0; i < res.length; i++) {
                    pro = $('#country');
                    options += '<option value="' + res[i] + '" >' + res[i] + '</option>';
                }
                pro.append(options);
            }
        })
    });
    $('#country').change(function () {
        var city = $("#city").val();
        var pro = $("#country").val();
        $.ajax({
            url: `${BASE_URL}` + '/StreetAddress',
            data: '{"city":"' + city + '","county":"' + pro + '"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                let pro, options;
                for (let i = 0; i < res.length; i++) {
                    pro = $("#street");
                    options += '<option value="' + res[i] + '" >' + res[i] + '</option>';
                }
                pro.append(options);
            }
        })
    });
    $("#street").change(function () {
        var city = $("#city").val();
        var pro = $("#country").val();
        var town = $("#town").val();
        var str = $("#street").val();
        $.ajax({
            url: `${BASE_URL}` + '/SpecificAddress',
            data: '{"city":"' + city + '","county":"' + pro + '","town":"' + town + '","street":"' + str + '"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                $("#auto").empty();
                for (var i = 0; i < res.length; i++)
                    $("#auto").append('<option label="' + res[i] + '" value="' + res[i] + '"></option>');
            }
        })
    });
    $('#special_address').change(function () {
        var city = $("#city").val();
        var pro = $("#country").val();
        var town = $("#town").val();
        var str = $("#street").val();
        var spe = $("#special_address").val();
        // alert(spe)
        $.ajax({
            url: `${BASE_URL}` + '/Mac_Address',
            data: '{"city":"' + city + '","county":"' + pro + '","town":"' + town + '","street":"' + str + '","special_address":"' + spe + '"}',
            method: 'post',
            contentType: "application/json",
            beforeSend: function(request) {
                request.setRequestHeader("token",window.localStorage.getItem("token") );
            },
            success: function (res) {
                console.log(res)
                let pro, options;
                for (let i = 0; i < res.length; i++) {
                    pro = $("#box");
                    options += '<option value="' + res[i].location + res[i].mac_address + '" >' + res[i].location + res[i].mac_address + '</option>';
                }
                pro.append(options);
            }
        })
    });
}

// numtitle=resd[0].info_inCons_List[0].mac_address;
function SettingOption(Xdata, Ydata) {
    return {
        // 标题
        title: {
            text: numtitle,
            left: 'center',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
                // x轴
        xAxis: {
            name:'时间',
            axisLine: {
                lineStyle: {
                    color: '#000000'
                }
            },
            axisLabel :{
                interval:0
            },
            data: time
            // data: ['12:00','13:00',"14:00","15:00","16:00","17:00"]
        },
        yAxis: {
            name: '客流量(人)',
            axisLine: {
                lineStyle: {
                    color: '#000000'
                }
            },
        },
        label: {
            show: true,
            position: 'top',
            formatter: people//在柱状图的顶部显示出某个东西和这个东西的百分比值12
        },
        dataZoom : [
            {
                type: 'slider',
                show: true,
                start: 0,
                end: 100,
                handleSize: 8
            },
            {
                type: 'inside',
                start: 94,
                end: 100
            },
            {
                type: 'slider',
                show: true,
                yAxisIndex: 0,
                filterMode: 'empty',
                width: 12,
                height: '70%',
                handleSize: 8,
                showDataShadow: false,
                left: '93%'
            }
        ],
        // 数据

        series: [{
            type: 'bar',
            data: people,
            // data: [5, 20, 36, 10, 10, 20],
            itemStyle:{
                normal: {
                    //每根柱子颜色设置
                    // color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                    color: function(params) {
                        let colorList=[];
                        for(let i=0;i<100;i++)
                        {colorList.push("#67d3ce", "#42d3e7", "#66b9db","#42d3e7","#67d3ce")}
                        return colorList[params.dataIndex];
                    }
                }
            },
        }
        ]

    }
}
myChart.setOption(SettingOption(1,1))
function a(c){
    var u=c+1;
    numtitle=resd[0].info_inCons_List[c].mac_address;
    time=[];
    for (let i = 0; i <resd[0].info_inCons_List[c].list_inCons.length; i++) {
        time.push(resd[0].info_inCons_List[c].list_inCons[i].time);
    }
    people=[]
    for (let i = 0; i <resd[0].info_inCons_List[c].list_inCons.length; i++) {
        people.push(resd[0].info_inCons_List[c].list_inCons[i].num);
    }
    let option = SettingOption(time,people)
    myChart.setOption(option)

}





function ak(c) {

    $('#change').on('click', '#uk'+c,function (e) {
        // alert(c)
        for(var i=0;i<resd[0].info_inCons_List.length;i++){
            if(i===c)
            {document.getElementById('uk'+c).style.color="#64b0f2";continue}
         document.getElementById('uk'+i).style.color="black"}
         // document.getElementById('uk0').style.color="#64b0f2"
        numtitle=resd[0].info_inCons_List[c].mac_address;
        time=[];
        for (let i = 0; i <resd[0].info_inCons_List[c].list_inCons.length; i++) {
            time.push(resd[0].info_inCons_List[c].list_inCons[i].time);
        }
        people=[]
        for (let i = 0; i <resd[0].info_inCons_List[c].list_inCons.length; i++) {
            people.push(resd[0].info_inCons_List[c].list_inCons[i].num);
        }
        let option = SettingOption(time,people)
        myChart.setOption(option)
    });
}






//h1的接口请求
function search1(){
    var address=$("#city").val()+","+$("#country").val()+","+$("#street").val()+","+$("#special_address").val();
    console.log(address);
    $.ajax({
        url: "http://121.199.21.197:63394/statistic_Color_Data",
        data: '{ "address": "' + address+ '"}',
        method: 'post',
        contentType: "application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log(res)
            resd=res;
            console.log(requer+"wohsrequer")
            $("#change").empty();
            var k=document.createElement('span');
            k.innerHTML=$("#special_address").val()+":";
            k.className="aa";
            k.style.marginLeft="110px"
            $('#change').append(k)
            for (let i = 0; i < res.length; i++) {
                var a = document.createElement('button');
                if(res[i].color==="yellow")
                    a.innerHTML = "中活跃度";
                else if(res[i].color==="red")
                    a.innerHTML="高活跃度"
                else a.innerHTML="低活跃度"
                a.className = "sela";
                a.id = "buk"+i;
                a.onclick=bk(i);
                if(i===0) a.style.color="#0FB9EF";
                $('#change').append(a)
            }
            time=[];
            for (let i= 0; i < res[0].yellowInfoList.length; i++) {
                time.push(res[0].yellowInfoList[i].time+"\n"+res[0].yellowInfoList[i].mac_address);
            }
            people=[];
            for (let i = 0; i <res[0].yellowInfoList.length; i++) {
                people.push(res[0].yellowInfoList[i].number);
            }
            console.log("是我是我"+people)

            let option = SettingOption(time,people)
            myChart.setOption(option)
        }

    })
}
function bk(c) {

    $('#change').on('click', '#buk'+c,function (e) {
        for(var i=0;i<resd.length;i++){
            if(i===c)
            {document.getElementById('buk'+c).style.color="#64b0f2";continue;}
            document.getElementById('buk'+i).style.color="black";
        }
        var str;
        if(c===0) str=resd[c].yellowInfoList;
        if(c===1) str=resd[c].redInfoList;
        if(c===2) str=resd[c].greenInfoList;
        time=[];
        for (let i= 0; i < str.length; i++) {
            time.push(str[i].time+"\n"+str[i].mac_address);
        }
        people=[]
        for (let i= 0; i <str.length; i++) {
            people.push(str[i].number);
        }
        console.log(time)
        let option = SettingOption(time,people)
        myChart.setOption(option)
    });
}





//h5的接口请求
function acbd(){
    var address=$("#city").val()+","+$("#country").val()+","+$("#street").val()+","+$("#special_address").val();
    var time1=$('#start').val().split('T')[1];
    var year=$('#start').val().split('T')[0].split('-')[0];
    var month=$('#start').val().split('T')[0].split('-')[1];
    var day=$('#start').val().split('T')[0].split('-')[2];
    var time2=$('#end').val().split('T')[1];
    var year1=$('#end').val().split('T')[0].split('-')[0];
    var month1=$('#end').val().split('T')[0].split('-')[1];
    var day1=$('#end').val().split('T')[0].split('-')[2];
    let data = JSON.stringify({
        "year1": year,
        "month1": month,
        "day1": day,
        "year2": year1,
        "month2": month1,
        "day2": day1,
        "time1": time1,
        "time2": time2,
        "address":address
    })
    $.ajax({
        url: "http://121.199.21.197:63394/sameTime_Statistic",
        data: data,
        method: 'post',
        contentType: "application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log(res)
            $("#change").empty();

            time=[];
            for (let i= 0; i < res.length; i++) {
                time.push(res[i].mac_address);
            }
            people=[];
            for (let i = 0; i <res.length; i++) {
                people.push(res[i].num);
            }
            console.log("是我是我"+people)

            let option = SettingOption(time,people)
            myChart.setOption(option)
        }

    })
}
function bk(c) {

    $('#change').on('click', '#buk'+c,function (e) {
        for(var i=0;i<resd.length;i++){
            if(i===c)
            {document.getElementById('buk'+c).style.color="#64b0f2";continue;}
            document.getElementById('buk'+i).style.color="black";
        }
        var str;
        if(c===0) str=resd[c].yellowInfoList;
        if(c===1) str=resd[c].redInfoList;
        if(c===2) str=resd[c].greenInfoList;
        time=[];
        for (let i= 0; i < str.length; i++) {
            time.push(str[i].time+"\n"+str[i].mac_address);
        }
        people=[]
        for (let i= 0; i <str.length; i++) {
            people.push(str[i].number);
        }
        console.log(time)
        let option = SettingOption(time,people)
        myChart.setOption(option)
    });
}