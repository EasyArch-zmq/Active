const BASE_URL = 'http://121.199.21.197:63394'
var resd=[];
// var aaa=["https://test2346.oss-cn-beijing.aliyuncs.com/dt.jpg"]
var arry1=[],arry=[];
	$(document).ready(() => {
			// let username = window.location.href.split('?')[1].split('=')[1]
			// let username = "makesi"
		let username =window.localStorage.getItem('p_username')
		document.getElementById('user').innerText="用户名："+username;
		console.log(username)
			$.ajax({
				url:`${BASE_URL}` +"/selectAllNumber",
				method:'post',
				data:'{"username":"'+username+'"}',
				contentType:"application/json",
				token:window.localStorage.getItem("p_token"),
				// beforeSend: function(request) {
				// 	request.setRequestHeader("token",window.localStorage.getItem("p_token") );
				// },
				success: (res) => {
					console.log(res)
					resd=res;
				   document.getElementById('address1').innerHTML = res.userAddress;

					boxloading(0);
				}
			})
		})
function boxloading(c) {
	$('#ssx').empty();
	var aaa=resd.list_inAll[0].picture_url.split(',');
	arry=resd.list_inAll[c].list_inCons;
	for(let i=0;i<arry.length;i++) {
		var container = document.createElement("div");
		if(aaa.length<1)
			container.className = "col-xs-6 col-md-6 container1";
		else
		container.className = "col-xs-6 col-md-6 container1";
		// container.style.cssFloat = "left";
		container.id = arry[i].location;
		var light_container = document.createElement("div");
		light_container.className = "light-container";
		var light_address = document.createElement("div");
		light_address.className = "light-address";
		light_address.innerHTML =arry[i].location;
		light_container.appendChild(light_address)
		var img_light = document.createElement("img");//根据不同的数据的颜色
		img_light.className="logo";
		// img_light.style.height="500px"
		if (arry[i].number <= arry[i].color.green)
			img_light.src = "../static/assets/img/green.png";
		else if (arry[i].number <= arry[i].color.red)
			img_light.src = "../static/assets/img/yellow.png";
		else
			img_light.src = "../static/assets/img/red.png";
		img_light.className = "img-light";
		light_container.appendChild(img_light);
		var level = document.createElement("div");
		level.className = "level1"
		var levelnum = document.createElement("span");
		levelnum.className = "span1"
		if (arry[i].number <= arry[i].color.green)
			levelnum.innerHTML = "活跃度：" + "低" + "<br/>";
		else if (arry[i].number <= arry[i].color.red)
			levelnum.innerHTML = "活跃度：" + "中" + "<br/>";
		else
			levelnum.innerHTML = "活跃度：" + "高" + "<br/>";
		if(arry.length>=2)
		{
			container.className = "col-xs-6 col-md-6 container2";
			container.style.cssFloat="left";
			light_container.className = "light-container1";
			light_address.className = "light-address1";
			img_light.className = "img-light1";
			level.className = "level1";
			levelnum.className="span2"
				}
		level.appendChild(levelnum)
		light_container.appendChild(level);
		container.appendChild(light_container);
		document.getElementById('ssx').appendChild(container);
			}
	// console.log(aaa.length)
	var img=document.getElementById('imgstate');
	for(var i=0;i<aaa.length;i++)
	{
		var k=document.createElement('div');
		if(aaa.length>=2)
			k.className="col-md-6 col-sm-6 col-xs-12";
		 else k.className=" col-md-12 col-sm-12 col-xs-12";
		var u=document.createElement('img');
		u.src=aaa[i];
		if (aaa.length===2)
			u.className="plane";
		else u.className="planeone"
		k.append(u);
		img.append(k);

	}

}
//下拉选择建筑
$('#list').change(function () {
	var myselect=$('#list').val();
	for (let i=0;i<resd.list_inAll.length;i++)
	{
		if(myselect===resd.list_inAll[i].construction)
		{boxloading(i);
		break;}
		else
		{	$('#ssx').empty();
			continue;}

	}

})