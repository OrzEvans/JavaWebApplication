/**
 * js工具类
 */
/**	使用说明：使用此函数调用即可MyAlert.prompt();
 	* 自定义弹窗
	 * @param msg 信息(String类型)
	 * @param promptType 弹窗种类(String类型):
	 * 			<li>info:信息</li>
	 * 			<li>confirm:提示</li>
	 * 			<li>warn:警告</li>
	 * 			<li>error:错误</li>
	 * 			<li>success:成功</li>
	 * 			<li>input:输入（后面三个参数中，需要再传入一个接受输入框数据的函数即：一个回调函数method）</li>
	 * 			<li>custom:自定义（只有使用此弹窗类型时，后面三个参数才生效）</li>
	 * @param mehtod 自定义确认按钮的回调函数(function类型)
	 * @param btnType 自定义按钮类型(String类型):
	 * 			<li>ok:确定</li>
	 * 			<li>cancel:取消</li>
	 * 			<li>all:全部</li>
	 *			<li>none:没有按钮</li>
	 * @param title 自定义标题(String类型)
	 * <hr>
	 * 此方法只是封装了一个弹窗插件<br>
	 * 若要修改提示信息需要改动Util/prompt/xcConfirm.js文件中的文本内容
	 */
(function($){
	window.wxc = window.wxc || {};
	window.wxc.xcConfirm = function(popHtml, type, options) {
	    var btnType = window.wxc.xcConfirm.btnEnum;
		var eventType = window.wxc.xcConfirm.eventEnum;
		var popType = {
			info: {
				title: "정보",//标题信息
				icon: "1px 0",//蓝色i
				btn: btnType.ok
			},
			success: {
				title: "성공",//标题信息
				icon: "1px -30px",//绿色对勾
				btn: btnType.ok
			},
			error: {
				title: "에러",//标题信息
				icon: "-30px -30px",//红色叉
				btn: btnType.ok
			},
			confirm: {
				title: "메세지",//标题信息
				icon: "-30px 1px",//黄色问号
				btn: btnType.okcancel
			},
			warning: {
				title: "경고",//标题信息
				icon: "1px -61px",//黄色叹号
				btn: btnType.okcancel
			},
			input: {
				title: "데이타를 입력하세요",//标题信息
				icon: "",
				btn: btnType.okcancel
			},
			custom: {
				title: "",
				icon: "",
				btn: btnType.ok
			}
		};
		var itype = type ? type instanceof Object ? type : popType[type] || {} : {};//格式化输入的参数:弹窗类型
		var config = $.extend(true, {
			//属性
			title: "", //自定义的标题
			//icon: "", //图标
			btn: btnType.ok, //按钮,默认单按钮
			//事件
			onOk: $.noop,//点击确定的按钮回调
			onCancel: $.noop,//点击取消的按钮回调
			onClose: $.noop//弹窗关闭的回调,返回触发事件
		}, itype, options);
		
		var $txt = $("<p>").html(popHtml);//弹窗文本dom
		var $tt = $("<span>").addClass("tt").text(config.title);//标题
		var icon = config.icon;
		var $icon = icon ? $("<div>").addClass("bigIcon").css("backgroundPosition",icon) : "";
		var btn = config.btn;//按钮组生成参数
		
		var popId = creatPopId();//弹窗索引
		
		var $box = $("<div>").addClass("xcConfirm");//弹窗插件容器
		var $layer = $("<div>").addClass("xc_layer");//遮罩层
		var $popBox = $("<div>").addClass("popBox");//弹窗盒子
		var $ttBox = $("<div>").addClass("ttBox");//弹窗顶部区域
		var $txtBox = $("<div>").addClass("txtBox");//弹窗内容主体区
		var $btnArea = $("<div>").addClass("btnArea");//按钮区域
		
		var $ok = $("<a>").addClass("sgBtn").addClass("ok").text("확인");//确定按钮
		var $cancel = $("<a>").addClass("sgBtn").addClass("cancel").text("취소");//取消按钮
		var $input = $("<input>").addClass("inputBox");//输入框
		var $clsBtn = $("<a>").addClass("clsBtn");//关闭按钮
		
		//建立按钮映射关系
		var btns = {
			ok: $ok,
			cancel: $cancel
		};
		
		init();
		
		function init(){
			//处理特殊类型input
			if(popType["input"] === itype){
				$txt.append($input);
			}
			
			creatDom();
			bind();
		}
		
		function creatDom(){
			$popBox.append(
				$ttBox.append(
					$clsBtn
				).append(
					$tt
				)
			).append(
				$txtBox.append($icon).append($txt)
			).append(
				$btnArea.append(creatBtnGroup(btn))
			);
			$box.attr("id", popId).append($layer).append($popBox);
			$("body").append($box);
		}
		
		function bind(){
			//点击确认按钮
			$ok.click(doOk);
			
			//回车键触发确认按钮事件
			$(window).bind("keydown", function(e){
				if(e.keyCode == 13) {
					if($("#" + popId).length == 1){
						doOk();
					}
				}
			});
			
			//点击取消按钮
			$cancel.click(doCancel);
			
			//点击关闭按钮
			$clsBtn.click(doClose);
		}

		//确认按钮事件
		function doOk(){
			var $o = $(this);
			var v = $.trim($input.val());
			if ($input.is(":visible"))
		        config.onOk(v);
		    else
		        config.onOk();
			$("#" + popId).remove(); 
			config.onClose(eventType.ok);
		}
		
		//取消按钮事件
		function doCancel(){
			var $o = $(this);
			config.onCancel();
			$("#" + popId).remove(); 
			config.onClose(eventType.cancel);
		}
		
		//关闭按钮事件
		function doClose(){
			$("#" + popId).remove();
			config.onClose(eventType.close);
			$(window).unbind("keydown");
		}
		
		//生成按钮组
		function creatBtnGroup(tp){
			var $bgp = $("<div>").addClass("btnGroup");
			$.each(btns, function(i, n){
				if( btnType[i] == (tp & btnType[i]) ){
					$bgp.append(n);
				}
			});
			return $bgp;
		}

		//重生popId,防止id重复
		function creatPopId(){
			var i = "pop_" + (new Date()).getTime()+parseInt(Math.random()*100000);//弹窗索引
			if($("#" + i).length > 0){
				return creatPopId();
			}else{
				return i;
			}
		}
	};
	
	//按钮类型
	window.wxc.xcConfirm.btnEnum = {
		ok: parseInt("0001",2), //确定按钮
		cancel: parseInt("0010",2), //取消按钮
		okcancel: parseInt("0011",2) //确定&&取消
	};
	
	//触发事件类型
	window.wxc.xcConfirm.eventEnum = {
		ok: 1,
		cancel: 2,
		close: 3
	};
	
	//弹窗类型
	window.wxc.xcConfirm.typeEnum = {
		info: "info",
		success: "success",
		error:"error",
		confirm: "confirm",
		warning: "warning",
		input: "input",
		custom: "custom"
	};

})(jQuery);

var MyAlert={
		/**	使用说明：使用此函数调用即可MyAlert.prompt();参数类型除了function都是String类型
	 	 * 自定义弹窗，参数搭配方式：（1,2）||(1,2,3)||(1,2,"cunstom",4,5)
		 * @param msg 信息(String类型)
		 * @param promptType 弹窗种类(String类型):
		 * 			<li>msg:信息（若需要指定确认按钮的事件，则需要再传入函数即：一个回调函数method）</li>
		 * 			<li>confirm:提示（若需要指定确认按钮的事件，则需要再传入函数即：一个回调函数method）</li>
		 * 			<li>warn:警告（若需要指定确认按钮的事件，则需要再传入函数即：一个回调函数method）</li>
		 * 			<li>error:错误（若需要指定确认按钮的事件，则需要再传入函数即：一个回调函数method）</li>
		 * 			<li>success:成功（若需要指定确认按钮的事件，则需要再传入函数即：一个回调函数method）</li>
		 * 			<li>input:输入（若需要指定确认按钮的事件，则需要再传入函数即：一个回调函数method）</li>
		 * 			<li>custom:自定义（只有使用此弹窗类型时，后面三个参数才生效,否则报错）</li>
		 * @param mehtod 自定义确认按钮的回调函数(function类型)
		 * @param btnType 自定义按钮类型(String类型):
		 * 			<li>ok:确定</li>
		 * 			<li>cancel:取消</li>
		 * 			<li>all:全部</li>
		 *			<li>none:没有按钮</li>
		 * @param title 自定义标题(String类型)
		 * <hr>
		 * 此方法只是封装了一个弹窗插件<br>
		 * 若要修改提示信息需要改动Util/prompt/xcConfirm.js文件中的文本内容
		 */
		prompt:function(msg,promptType,method,btnType,title){
			//设置默认弹窗类型
			var type=window.wxc.xcConfirm.typeEnum.info;
			//设置默认按钮
			var button=window.wxc.xcConfirm.btnEnum.ok;
			//判断弹窗类型
			switch(promptType.toUpperCase()){	
					case "INFO":
						break;
					case "CONFIRM":
						type= window.wxc.xcConfirm.typeEnum.confirm;
						break;
					case "WARN":
						type=window.wxc.xcConfirm.typeEnum.warning;
						break;
					case "ERROR":
						type=window.wxc.xcConfirm.typeEnum.error;
						break;
					case "SUCCESS":
						type=window.wxc.xcConfirm.typeEnum.success;
						break;
					case "INPUT":
						type=window.wxc.xcConfirm.typeEnum.input;
						break;
					case "CUSTOM":
						type="custom";
						break;
			}
			//若为自定义弹窗需指定按钮类型，标题，单机确定按钮后的回调函数
			if(type=="custom"){
				switch(btnType.toUpperCase()){
				case "OK":
					button=window.wxc.xcConfirm.btnEnum.ok;
					break;
				case "CANCEL":
					button=window.wxc.xcConfirm.btnEnum.cancel;
					break;
				case "ALL":
					button=window.wxc.xcConfirm.btnEnum.okcancel;
					break;
				case "NONE":
					button="";
				}
				var option = {
						title: title,
						btn: button,
						onOk: function(){
							method();
						},
				};
				window.wxc.xcConfirm(msg, "custom", option);
				return;
				//若为输入框，则需要指定单击确定按钮后的回调函数
			}else if(type=="input"){
				window.wxc.xcConfirm(msg, type,{onOk:function(v){
					method(v);
				}});
				return ;
				//采用插件支持样式的弹窗
			}else{
				window.wxc.xcConfirm(msg,type);
				//绑定确定按钮单击事件
				$(".sgBtn.ok").on("click",function(){
					if(typeof(method)!="undefined"||method!=null){
						method();
					}
				});
			}
		}
	};
var Util={
	/**
	 * 各类正则表达式
	 */
	regex:{
		password:/^[a-zA-Z0-9,\.\/]{6,14}$/,
		email:/^[a-z0-9_\-]+@([a-z0-9\-]+\.)?[a-z0-9\-]+\.[a-z]{2,}$/,
		phone:/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/,
		searchPhone:/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])$/,
		koNickName:/^[a-zA-Z0-9_\u4e00-\u9fa5\uAC00-\uD7A3\s]{2,12}$/,
	},
	/**
	 * 工具函数
	 */
	method:{
		/**
		 * 封装发送的ajax请求
		 * @param url 请求路径
		 * @param type 请求方式
		 * @param data 要发送的数据
		 * @param dataType 响应数据格式
		 * @param successFn 响应请求成功的回调函数
		 * @author Evans
		 */
		sendAjax:function(url,type,data,dataType,successFn){
			$.ajax({
				url:url||null,
				dataType:dataType||"JSON",
				type:type||"POST",
				data:data||null,
				traditional:true,
				success:function(result){
					successFn(result);
				},
				error:function() {
//				参数 	 XMLHttpRequest, textStatus, errorThrown 可用于检查错误信息
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
//                    MyAlert.prompt("数据加载失败，请联系管理员","error",function(){
//                    	$(".loading-indicator-overlay").hide();
//                    	$(".loading-indicator").hide();
//                    });
				}
			});
		},
		/**
		 * 增加cookie
		 * @param objName cookie名称
		 * @param objValue cookie内容
		 * @param objHours cookie过期时间，如果是0,则表示不设定过期时间，浏览器关闭时cookie自动消失  
		 * @author Evans
		 */
		addCookie:function (objName,objValue,objHours){
			//添加cookie  
			var str = objName + "=" + escape(objValue);  
			if(objHours > 0){
				//为0时不设定过期时间，浏览器关闭时cookie自动消失  
				var date = new Date();  
				var ms = objHours*3600*1000;  
				date.setTime(date.getTime() + ms);  
				str += "; expires=" + date.toGMTString();  
			}  
			document.cookie = str;  
		} ,
		/**
		 * 获取cookie
		 * @param objName 指定cookie的名称
		 * @returns 返回cookie内容，若不存在返回""
		 * @author Evans
		 */
		getCookie:function(objName){
			var allcookies=document.cookie; 
		    var arrStr = allcookies.split("; ");  
		    for(var i = 0;i < arrStr.length;i ++){  
		    	var temp = arrStr[i].split("=");  
		    	if(temp[0] == objName){
		    		return unescape(temp[1]);  
		    	}
		    }
		    return "";
		},
		/**
		 * 删除cookie
		 * @param name 要删除的cookie名称
		 * @author Evans
		 */
		delCookie:function (objName){
			//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间  
			var date = new Date();  
			date.setTime(date.getTime() - 10000);  
			document.cookie = objName + "=a; expires=" + date.toGMTString();  
		},
		/**
		 * 格式化long型日期转为字符串
		 * @param longTypeTime long型日期数
		 * @param flag boolean值，true:表示返回带具体时间的字符串，false:表示只返回日期
		 * @returns {String} 转换完的字符串，如果传入参数不合法，则返回空字符串
		 * @author Evans
		 */
		formatDate:function(longTypeTime,flag){
			var str="";
			if(isNaN(longTypeTime)){
				return str;
			}
			var date= new Date();
			date.setTime(longTypeTime);
			var year=date.getFullYear();
			var month=date.getMonth()+1;
			var day=date.getDate();
			var hours=date.getHours();
			var minutes=date.getMinutes();
			var seconds=date.getSeconds();
			var addZero=function(temp){
				if(temp<10){  
	                temp = "0" + temp;  
	            }
				return temp;
			};
			if(!flag){
				str=year+"-"+addZero(month)+"-"+addZero(day);
			}else{
				str=year+"-"+addZero(month)+"-"+addZero(day)+" "+addZero(hours)+":"+addZero(minutes)+":"+addZero(seconds);
			}
			return str;
		},
		/**
		 * 设置复选框的全选与全不选事件
		 * @param parentId 所有复选框的父id，或者表格中tbody的id
		 * @param checkBoxName 所有复选框的name值
		 * @param obj 触发单击事件的对象
		 * @author Evans
		 */
		checkBoxCheckAll:function(parentId,checkBoxName,obj){
			var checkboxs=$("#"+parentId+" input[name='"+checkBoxName+"']");
			var length=checkboxs.length;
			if(obj.checked==true){
				$.each(checkboxs,function(){
					this.checked=true;
				});
			}else{
				$.each(checkboxs,function(){
					this.checked=false;
				});
			};
		},
		/**
		 * 比较两个json对象是否相同
		 * @param objA 对象a
		 * @param objB 对象b
		 * @returns 返回boolean结果
		 * @author Evans
		 */
		Compare: function Compare(objA, objB) {
			function isObj(object) {
				return object && typeof (object) == 'object' && Object.prototype.toString.call(object).toLowerCase() == "[object object]";
				}
				function isArray(object) {
				return object && typeof (object) == 'object' && object.constructor == Array;
				}
				function getLength(object) {
				var count = 0;
				for (var i in object) count++;
				return count;
				}

				function CompareObj(objA, objB, flag) {
				for (var key in objA) {
				if (!flag) //跳出整个循环
				break;
				if (!objB.hasOwnProperty(key)) { flag = false; break; }
				if (!isArray(objA[key])) { //子级不是数组时,比较属性值
				if (objB[key] != objA[key]) { flag = false; break; }
				} else {
				if (!isArray(objB[key])) { flag = false; break; }
				var oA = objA[key], oB = objB[key];
				if (oA.length != oB.length) { flag = false; break; }
				for (var k in oA) {
				if (!flag) //这里跳出循环是为了不让递归继续
				break;
				flag = CompareObj(oA[k], oB[k], flag);
				}
				}
				}
				return flag;
				}	
				if (!isObj(objA) || !isObj(objB)) return false; //判断类型是否正确
				if (getLength(objA) != getLength(objB)) return false; //判断长度是否一致
				return CompareObj(objA, objB, true);//默认为true
		},
		/**
		 * 以某个字符串开始
		 * @author Evans
		 */
		startWith:function(str,prefix){
			 return str.slice(0, prefix.length) === prefix;
		},
		/**
		 * 以某个字符串结尾
		 * @author Evans
		 */
		endWith:function(str,suffix){
			return str.indexOf(suffix, str.length - suffix.length) !== -1;
		},
		/**
		 * 指定id的文本框只允许输入数字
		 */
		onlyNum:function(id){
			if(id&&id!=''){
				if($("#"+id).length==0||$("#"+id)[0].tagName!='INPUT'){
					console.info("This is not a input tag!");
					return false;
				}
				$("#"+id).on("keyup",function(){
					num=$(this).val();
					if(isNaN(num)){
						$(this).val("");
					}
				});
			}
		}
	}
};
