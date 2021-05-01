const myChart = echarts.init(document.getElementById("bar"));
const BASE_URL = 'http://121.199.21.197:63394'
var people=[];
var time=[];
var numtitle="10号风险防控图";
window.onload=function() {
    let username =window.localStorage.getItem('p_username')
    // console.log(p_username)
    // let p_username = "oc";
    $.ajax({
        url: `${BASE_URL}` + '/selectDefaultNumber_p',
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
            document.getElementById('aa').innerHTML = res[0].construction+":";
            // var li = document.getElementById('cars');
            for (let i = 0; i < res[0].info_inCons_List.length; i++) {
                var a = document.createElement('button');
                a.innerHTML = resd[0].info_inCons_List[i].location;
                a.className = "sela btn  btn-sm";
                a.id = "uk"+i;
                a.onclick=ak(i);
                if(i===0) a.style.color="#2086D7";
                else a.style.color="black"
                $('#change').append(a)
            }
            for (let i  = 0; i < res[0].info_inCons_List[0].list_inCons.length; i++) {
                time.push(res[0].info_inCons_List[0].list_inCons[i].time);
            }
            console.log(time)
            for (let i = 0; i <res[0].info_inCons_List[0].list_inCons.length; i++) {
                people.push(res[0].info_inCons_List[0].list_inCons[i].num);
            }
            let option = SettingOption0(time,people)
            myChart.setOption(option)
        }
    })
    $.ajax({
        url:`${BASE_URL}` +"/selectAllNumber",
        method:'post',
        data:'{"username":"'+username+'"}',
        contentType:"application/json",
        beforeSend: function(request) {
            request.setRequestHeader("token",window.localStorage.getItem("token") );
        },
        success: (res) => {
            document.getElementById('address1').innerHTML = res.userAddress;
            document.getElementById('user').innerText=username;

        }})
}
function SettingOption0() {
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
                    color: function(params) {
                        let colorList=[];
                        for(let i=0;i<100;i++)
                        {colorList.push("#65d0cb", "#40cde0", "#64b5d6","#40ccdf","#67d3ce")}
                        return colorList[params.dataIndex];
                    }
                }
            },
        }
        ]

    }
}
myChart.setOption(SettingOption0(1,1))

function ak(c) {

    $('#header').on('click', '#uk'+c,function (e) {
        // alert(c)
        for(var i=0;i<resd[0].info_inCons_List.length;i++){
            if(i===c)
            {document.getElementById('uk'+c).style.color="#2086D7";continue}
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
        let option = SettingOption0(time,people)
        myChart.setOption(option)
    });
}