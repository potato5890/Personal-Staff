var $ = function (id) {//�õ��ؼ�
	if("string" == typeof id){//���ַ���ʱ�����ַ����õ��ؼ�
		return document.getElementById(id);
	}
	else {//�ǿؼ�ʱֱ�ӷ��ؿؼ�
		return id;
	}
};

var Class = {
	create: function() {
		return function() {
			this.initialize.apply(this, arguments);
		};
  	}
};

Object.extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
	return destination;
};

var TransformView = Class.create();
TransformView.prototype = {
	//��������,��������,�л�����,�л�����
	initialize: function(container, slider, parameter, count, options) {
		if(parameter <= 0 || count <= 0) {
			return;
		}
		var oContainer = $(container);
		var oSlider = $(slider);
		var oThis = this;

		this.Index = 0;//��ǰ������
		
		this._parameter = parameter;//�л�����
		this._count = count || 0;//�л�����
		this._target = 0;//Ŀ�����
		this._timer = null;//��ʱ��
		this._slider = oSlider;//�����Ķ���
		
		this.SetOptions(options);

		this.Up = !!this.options.Up;
		this.Step = Math.abs(this.options.Step);
		this.Time = Math.abs(this.options.Time);
		this.Auto = !!this.options.Auto;
		this.Pause = Math.abs(this.options.Pause);
		this.onStart = this.options.onStart;
		this.onFinish = this.options.onFinish;

		oContainer.style.overflow = "hidden";
		oContainer.style.position = "relative";
		oSlider.style.position = "absolute";
		oSlider.style.top = oSlider.style.left = 0;
  	},

  	SetOptions: function(options) {//����Ĭ������
		this.options = {//Ĭ��ֵ
			Up:			true,//�Ƿ����ϣ�true����,false����
			Step:		5,//�����ı仯��
			Time:		20,//������ʱ
			Auto:		true,//�Ƿ��Զ�ת�������Զ���ͼ
			Pause:		4000,//ͣ�ٵ�ʱ��ֻ��AutoΪtrueʱ����Ч
			onStart:	function(){},//��ʼת��ʱִ��
			onFinish:	function(){}//���ת��ʱִ��
		};
		Object.extend(this.options, options || {});
  	},

  	Start: function() {//��ʼ�л�����
		if(this.Index < 0){
			this.Index = this._count - 1;
		} else if (this.Index >= this._count){ 
			this.Index = 0; 
		}
		
		this._target = -1 * this._parameter * this.Index;
		this.onStart();
		this.Move();
  	},

  	Move: function() {//�ƶ�
  		clearTimeout(this._timer);
  		var oThis = this;
  		var style = this.Up ? "top" : "left";//��ʲô�����ƶ�
  		var iNow = parseInt(this._slider.style[style]) || 0;
  		var iStep = this.GetStep(this._target, iNow);
  		if (iStep != 0) {
  			this._slider.style[style] = (iNow + iStep) + "px";
  			this._timer = setTimeout(
  				function(){ 
  					oThis.Move();
  				}, 
  				this.Time
  			);
  		} else {
  			this._slider.style[style] = this._target + "px";
  			this.onFinish();
  			if (this.Auto) { 
  				this._timer = setTimeout(
  					function(){
  						oThis.Index++; oThis.Start(); 
  					}, 
  					this.Pause
  				); 
  			}
  		}
  	},

  	GetStep: function(iTarget, iNow) {//��ò���
  		var iStep = (iTarget-iNow)/this.Step;
  		if (iStep == 0){
  			return 0;
  		}
  		if (Math.abs(iStep) < 1) {
  			return (iStep > 0 ? 1 : -1);
  		}
  		return iStep;
  	},

  	Stop: function(iTarget, iNow) {//ֹͣ
  		clearTimeout(this._timer);
  		this._slider.style[this.Up ? "top" : "left"] = this._target + "px";
  	}
};

window.onload = function(){
	function Each(list, fun){
		for (var i=0, len=list.length; i<len; i++){ 
			fun(list[i], i);
		}
	};
	var objs = $("idNum").getElementsByTagName("li");
	var tv = new TransformView("idTransformView", "idSlider", 210, 3, {
		onStart : function(){//��ť��ʽ
			Each(objs, function(o, i){
				o.className = (tv.Index == i) ? "on" : "";
			});
		}
	});
	tv.Start();
	Each(objs, function(o, i){
		o.onmouseover = function(){
			o.className = "on";
			tv.Auto = false;
			tv.Index = i;
			tv.Start();
		};
		o.onmouseout = function(){
			o.className = "";
			tv.Auto = true;
			tv.Start();
		};
	});
	
	scrollIn();//״̬������ѭ��
	adsChange();//��������ֻ�
};

var flag = true;
function adsChange() {//��������ֻ�
	var adsImage = $(showAds);
	if (flag) {
		adsImage.src = "image/showblog1.jpg";
		flag = false;
	} else {
		adsImage.src = "image/showblog2.png";
		flag = true;
	} 
	
	window.setTimeout("adsChange()",3000); 
}

var Message="��ӭ������ʳ������������������ļң�";
var place=1;
function scrollIn() {
	window.status=Message.substring(0, place);
	if (place >= Message.length) {
		place=1;
		window.setTimeout("scrollOut()",400); 
	} else {
		place++;
		window.setTimeout("scrollIn()",120); 
	} 
}
function scrollOut() {
	window.status=Message.substring(place, Message.length);
	if (place >= Message.length) {
		place=1;
		window.setTimeout("scrollIn()", 150);
	} else {
		place++;
		window.setTimeout("scrollOut()", 120);
	}
}