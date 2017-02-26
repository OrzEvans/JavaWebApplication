//页面加载完成初始化页面
$(function() {
	Login.init();
});
var Login = {
	//初始化方法
	init : function() {
		Login.event.init();// 事件层
	},
	//事件层
	event : {
		//事件层初始化方法	
	    init : function() {
	    	Login.event.bindEvent(); //初始化绑定事件
		},
		//绑定事件
		bindEvent : function() {
			//用户名焦点事件
			$("#userName").on("blur",function(){
				Login.event.checkInput("userName","账号");
			});
			//用户名移除焦点事件
			$("#userName").on("focus",function(){
				Login.event.checkInput("userName","");
			});
			//密码焦点事件
			$("#password").on("blur",function(){
				Login.event.checkInput("password","密码");
			});
			//密码移除焦点事件
			$("#password").on("focus",function(){
				Login.event.checkInput("password","");
			});
			//提交按钮事件
			//$("#lg-form").on("submit",function(){
			//	Login.event.loginSubmit();
			//});
			//回车按钮事件
			$("body").keydown(function() {
				if (event.keyCode == "13") {//keyCode=13是回车键
					$('#subBtn').click();
		        }
		    });
		},
		/**
		 * 检查输入框并提示错误信息
		 * @param id 输入框id
		 * @param msg 提示信息
		 * @return 成功返回true，失败返回false
		 */
		checkInput : function(id,msg) {
			if(!Login.validate.checkNull(id)){
				if(msg){
					$("#"+id+"P").text("请输入"+msg);
				}else{
					$("#"+id+"P").text("");
				}
				return false;
			}else{
				$("#"+id+"P").text("");
				return true;
			}
		},
		/**
		 * 登录按钮单击事件
		 */
		loginSubmit:function(){
			if(Login.validate.checkLogin()){
				$("#subBtn").focus();
				$("body").unbind("keydown");
				$("body").keydown(function(event) {
					if (event.keyCode == "13") {//keyCode=13是回车键
						 return false; 
			        }
			    });
				$('.loading').show();
				return true;
			}else{
				return false;
			}
		}
	},
	//验证功能模块
	validate:{
			/**
			 * 检查登录结果
			 * @returns {Boolean} 通过返回true，否则返回false
			 */
			checkLogin:function(){
				return Login.event.checkInput("userName","账号")&&Login.event.checkInput("password","密码");
			},
			/**
			 * 检查是否为空
			 * @param id 要检查的input的id
			 * @returns {Boolean} 如果是空则返回false，否则返回true
			 */
			checkNull:function(id){
				var str=$("#"+id).val();
				if(!str){
					return false;
				}
					return true;
			},
		},
};