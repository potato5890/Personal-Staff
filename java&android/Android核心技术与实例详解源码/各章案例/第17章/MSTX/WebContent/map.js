function load() {
	if (GBrowserIsCompatible()) {
		//������ͼ����
		var map = new GMap2(document.getElementById("map"));
		map.addControl(new GLargeMapControl());//��Ӵ��ͼ������
		//map.addControl(new GOverviewMapControl());//�������ͼ����        
		//map.addControl(new GScaleControl());//��ӱ�����
		map.addControl(new GMapTypeControl());//��ӵ�ͼ���ͣ����ǡ���ͼ����ϣ�����  
		map.enableScrollWheelZoom();//����������������
		map.setCenter(new GLatLng(39.92, 116.46), 14); //���õ�ͼ���ĵ㾭γ�ȼ����ű���
		GEvent.addListener(map,"click", function(overlay, latlng) {     
			if (latlng) { 					
				document.getElementById("info_lon").value = latlng.lng();
				document.getElementById("info_lat").value = latlng.lat();
				//�ڵ�ͼ����ʾ���򴰿�
				jd="%u7ECF%u5EA6";//����
				wd="%u7EAC%u5EA6";//γ��
				var str = "<font size='4'>"+unescape(jd)+":"+latlng.lng()+"<br/>"+unescape(wd)+":"+latlng.lat()+"</font>";
				var myHtml =unescape("<font size='4'>"+jd+":"+latlng.lng()+"<br/>"+wd+":"+latlng.lat()+"</font>");//ϣ����ʾ�ڴ����е�HTML���Լ����ɼ���
				map.openInfoWindowHtml(new GLatLng(latlng.lat(),latlng.lng()), str);//�ڴ�λ�ô򿪴���
				//δ��latlng.lat()��latlng.lng()���Ǵ����ݿ��ж����ľ�γ��
			}
		});
	}
}