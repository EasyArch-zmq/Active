const myChart = echarts.init(document.getElementById("line"));
const BASE_URL = 'http://121.199.21.197:63394'

// myChart.setOption(option)
var yanval=[];
var ytime=[];
// var ylist=
//     [
//         {
//             yanval: 12,
//             time: "00"
//         }
//         ,{
//         yanval: 23,
//         time: "01"
//     }
//         ,
//         {
//             yanval: 105,
//             time: "02"
//         },
//         {
//             yanval: 75,
//             time: "03"
//         }
//
//     ]
// for(var i=0;i<ylist.length;i++) {
//     yanval.push(ylist[i].yanval);
// }
// for(var i=0;i<ylist.length;i++) {
//     ytime.push(ylist[i].time);
// }
window.onload=function(){
    // let username = $('#username').val();
    $.ajax({
        url: `${BASE_URL}`+'/yanGan',
        // data: '{ "username": "'+username+'"}',
        method: 'post',
        contentType: "application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log("请求成功")
            // alert("sds")
            console.log(res)
            console.log(res.length)
            for(let i=0;i<res.length;i++) {
                ytime.push(res[i].time);
            }
            for(let i=0;i<res.length;i++) {
                yanval.push(res[i].yanGan);
            }
            let option = SettingOption(ytime,yanval)
            myChart.setOption(option)
            console.log(ytime)

        }
    })
}
var SettingOption = (Xdata, Ydata) => {
    return {
        // 标题
        title: {
            text: 'xx区烟雾浓度图',
            left: 'center',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                            }
        },

        // x轴
        xAxis: {
            type: 'category',
            data:ytime
            // data: ['12:00',"14:00","16:00","18:00","20:00","22:00","24:00","02:00","04:00","06:00","08:00","10:00"]
        },
        yAxis: {
            name: '烟雾浓度(立方米)',
           },
        label: {
            show: true,
            position: 'top',
            lineStyle: {
                color: '#d14a61',
            },
            formatter:yanval    //在柱状图的顶部显示出某个东西和这个东西的百分比值12
        },
        // 数据
        series: [{
            type: 'line',
            data:yanval
            // data: [5, 20, 36, 10, 10, 20,5, 17, 36, 100, 10, 21],
        }
        ]
    }
}

myChart.setOption(SettingOption(1,1))