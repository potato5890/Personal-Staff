function load() {
	if (GBrowserIsCompatible()) {
		//创建地图对象
		var map = new GMap2(document.getElementById("map"));
		map.addControl(new GLargeMapControl());//添加大地图控制条
		//map.addControl(new GOverviewMapControl());//添加缩略图控制        
		//map.addControl(new GScaleControl());//添加比例尺
		map.addControl(new GMapTypeControl());//添加地图类型（卫星、地图、混合）控制  
		map.enableScrollWheelZoom();//允许用鼠标滚轮缩放
		map.setCenter(new GLatLng(39.92, 116.46), 14); //设置地图中心点经纬度及缩放比例
		GEvent.addListener(map,"click", function(overlay, latlng) {     
			if (latlng) { 					
				document.getElementById("info_lon").value = latlng.lng();
				document.getElementById("info_lat").value = latlng.lat();
				//在地图上显示气球窗口
				jd="%u7ECF%u5EA6";//经度
				wd="%u7EAC%u5EA6";//纬度
				var str = "<font size='4'>"+unescape(jd)+":"+latlng.lng()+"<br/>"+unescape(wd)+":"+latlng.lat()+"</font>";
				var myHtml =unescape("<font size='4'>"+jd+":"+latlng.lng()+"<br/>"+wd+":"+latlng.lat()+"</font>");//希望显示在窗口中的HTML，自己生成即可
				map.openInfoWindowHtml(new GLatLng(latlng.lat(),latlng.lng()), str);//在此位置打开窗口
				//未来latlng.lat()、latlng.lng()就是从数据库中读出的经纬度
			}
		});
	}
}