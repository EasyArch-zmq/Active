const BASE_URL = 'http://121.199.21.197:63394'
var people=[];
var time=[];
var yanval=[];
var ytime=[];
window.onload=function(){
    let username = $('#username').val();
    $.ajax({
        url: `${BASE_URL}`+'/selectDefaultNumber',
        data: '{ "username": "'+"username"+'"}',
        type: "post",
        contentType: "application/json;charset=UTF-8",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: function (res) {
            console.log(res)
            for(var i=0;i<res.list.length;i++) {
              time.push(res.list[i].time);
                }
           for(var i=0;i<res.list.length;i++) {
             people.push(res.list[i].num);
             }

        }
    })

    // $.ajax({
    //     url: `${BASE_URL}`+'/yanGan',
    //     data: '{ "username": "'+username+'"}',
    //     method: 'post',
    //     contentType: "application/json",
    //     success: function (res) {
    //         console.log(res)
    //         for(var i=0;i<res.length;i++) {
    //             ytime.push(res[i].time);
    //         }
    //         for(var i=0;i<res.length;i++) {
    //            yanval.push(res[i].num);
    //         }
    //
    //     }
    // })
}

// var list = [
//     {
//         num: 1,
//         time: "00"
//     },
//     {
//         num: 2,
//         time: "02"
//     },
//     {
//         num: 2,
//         time: "04"
//     },
//     {
//         num: 2,
//         time: "06"
//     },
//     {
//         num: 2,
//         time: "08"
//     },
//     {
//         num: 2,
//         time: "10"
//     },
//     {
//         num: 13,
//         time: "12"
//     }]
// var ylist=
//     [
//     {
//         yanval: 12,
//         time: "00"
//     }
//         ,{
//         yanval: 13,
//         time: "01"
//     }
//         ,
//         {
//             yanval: 15,
//             time: "02"
//         }
//
//     ]
//本地测试
// for(var i=0;i<list.length;i++) {
//     time.push(list[i].time);
// }
// for(var i=0;i<list.length;i++) {
//     people.push(list[i].num);
// }
// for(var i=0;i<ylist.length;i++) {
//     yanval.push(ylist[i].yanval);
// }
// for(var i=0;i<ylist.length;i++) {
//     ytime.push(ylist[i].time);
// }

// 路径配置
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
    }
});
// 使用
require(
    [
        'echarts',
        'echarts/chart/bar',
        'echarts/chart/line'
    ],
    drawEcharts
);

function drawEcharts(ec){
    drawBar(ec);
    drawLine(ec);
}
function drawBar(ec){
    var myBarChart = ec.init(document.getElementById('barMain'));
    var option = {
        // 标题
        title: {
            text: '风险防控图',
            x:'center'
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
                    color: '#000000',
                },

            },
            data: time
            // data: ['13:00',"14:00","15:00","16:00","17:00"]

        },
        yAxis: {
            name: '客流量(人)',
            axisLine: {
                lineStyle: {
                    color: '#000000'
                }
            },
        },
        // 数据
        series: [{
            type: 'bar',
            data: people,
            // data: [5, 20, 36, 10, 10, 20],
            itemStyle:{
                normal: {
                    //每根柱子颜色设置
                    color: function(params) {
                        let colorList = [
                            "#91eeee",
                            "#42d3e7",
                            "#5ca8db",
                            "#2394b1",
                            "#386cad",
                            "#4173a0",
                            "#3c5fa0"
                        ];
                        return colorList[params.dataIndex];
                    }
                }
            },
        }
        ]
    };
    myBarChart.setOption(option,true); //当setOption第二个参数为true时，会阻止数据合并
}


function drawLine(ec){
    var myLineChart = ec.init(document.getElementById('lineMain'));
    var option2 = {
        // 标题
        title: {
            text: '烟雾浓度图',
            x:'center'

        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },

        // x轴
        xAxis: {
            axisLine: {
                lineStyle: {
                    color: '#000000'
                }
            },
            name: '时间',
            type: 'category',
            // data: ['12:00',"14:00","16:00","18:00","20:00","22:00","24:00","02:00","04:00","06:00","08:00","10:00"]
            data: ytime,
        },
        yAxis: {
            name: '烟雾浓度(立方米)',
            axisLine: {
                lineStyle: {
                    color: '#000000'
                }
            },
        },
        // 数据
        series: [{
            type: 'line',
            // data: [5, 20, 36, 10, 10, 20,5, 17, 36, 100, 10, 21],
            data: yanval,
            itemStyle:{
                normal:{
                    color:'#4ad2ff'
                }
            },
        }
        ]
    };
    myLineChart.setOption(option2,true);
}
