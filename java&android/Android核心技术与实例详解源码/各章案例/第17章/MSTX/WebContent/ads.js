var $ = function (id) {//得到控件
	if("string" == typeof id){//是字符串时根据字符串得到控件
		return document.getElementById(id);
	}
	else {//是控件时直接返回控件
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
	//容器对象,滑动对象,切换参数,切换数量
	initialize: function(container, slider, parameter, count, options) {
		if(parameter <= 0 || count <= 0) {
			return;
		}
		var oContainer = $(container);
		var oSlider = $(slider);
		var oThis = this;

		this.Index = 0;//当前的索引
		
		this._parameter = parameter;//切换参数
		this._count = count || 0;//切换数量
		this._target = 0;//目标参数
		this._timer = null;//定时器
		this._slider = oSlider;//滑动的对象
		
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

  	SetOptions: function(options) {//设置默认属性
		this.options = {//默认值
			Up:			true,//是否向上，true向上,false向左
			Step:		5,//滑动的变化率
			Time:		20,//滑动延时
			Auto:		true,//是否自动转换，即自动换图
			Pause:		4000,//停顿的时间只有Auto为true时才有效
			onStart:	function(){},//开始转换时执行
			onFinish:	function(){}//完成转换时执行
		};
		Object.extend(this.options, options || {});
  	},

  	Start: function() {//开始切换设置
		if(this.Index < 0){
			this.Index = this._count - 1;
		} else if (this.Index >= this._count){ 
			this.Index = 0; 
		}
		
		this._target = -1 * this._parameter * this.Index;
		this.onStart();
		this.Move();
  	},

  	Move: function() {//移动
  		clearTimeout(this._timer);
  		var oThis = this;
  		var style = this.Up ? "top" : "left";//向什么方向移动
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

  	GetStep: function(iTarget, iNow) {//获得步长
  		var iStep = (iTarget-iNow)/this.Step;
  		if (iStep == 0){
  			return 0;
  		}
  		if (Math.abs(iStep) < 1) {
  			return (iStep > 0 ? 1 : -1);
  		}
  		return iStep;
  	},

  	Stop: function(iTarget, iNow) {//停止
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
		onStart : function(){//按钮样式
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
	
	scrollIn();//状态栏文字循环
	adsChange();//长广告条轮换
};

var flag = true;
function adsChange() {//长广告条轮换
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

var Message="欢迎光临美食天下网，这里就是您的家！";
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