/**
 * 生成分页代码并绑定请求数据
 * @param options 以对象的形式
 * 	可用参数{
 * 			pageDivId:"父div的id",
 *			startPage:"页码起始页",
 *			totalPages:"总页码数",
 *			pageCount:"要显示的页码总数",
 *			sendData:"ajax请求的额外参数，后台接收时，要加载几条数据，该值存储在pageLength中，从第几条数据加载，该值数据存储在pageNum中",
 *			sendUrl:"ajax请求的url",
 *			loadData:function(result){"此处为ajax请求的回调函数"}
 * 		 }
 */
(function ($) {
	$.fn.pagetool = function(options){
		//定义初始化参数
			var defaults={
					//起始页码
					startPage:1,
					//总共页数
					totalPages:10,
					//要显示的页码数
					pageCount:5,
					//每页显示的条数
					itemsCount:5,
					//ajax请求的额外参数
					sendData:{},
					//ajax请求的url
					sendUrl:"",
					historyUrl:"",
					pageFlag:0,
					//ajax请求的回调函数
					loadData:function(result){
						//MyAlert.prompt(result.msg, "error");
					},
			};
			//定义传入参数options替换为defaults
			var options = $.extend(defaults, options);
			var num=1;
			options['pageDivId']=this[0].id;
			var $obj=this;
			//页码按钮事件
			var pageEvent=function(obj){
				 $('html,body').animate({ scrollTop: 0 }, 200);
				//初始化页码数为1
				num=1;
				//取得点击按钮的页码数
				var current=$("#"+options.pageDivId+" .fen-ye").text();
				//如果是后退按钮
				if($(this).is($("#"+options.pageDivId+" #pageBack"))){
					
					if($("#"+options.pageDivId+" .fen-ye").text()-1<=0){
						return;
					}
					//判断是否是第一页，若果是则将页码数定义为1，避免出现页码为0或负数的情况
					num=$("#"+options.pageDivId+" .fen-ye").text()-1 > 0 ? $("#"+options.pageDivId+" .fen-ye").text()-1 : 1;
					//清除选中样式
					$("#"+options.pageDivId).find(".fen-ye").removeClass("fen-ye");
					//增加当前页码为选中样式
					$("#"+options.pageDivId+" #page"+num).addClass("fen-ye");
					//取得后退按钮后的页码数
					var backPage=$("#"+options.pageDivId+" #pageBack").next().text();
					//如果当前页码数小于后退按钮后的页码数，则重新加载分页标签
					if(num<backPage){
						$obj.pagetool({
							//再次加载分页标签只改变页码数，其他值不变
							startPage:parseInt(backPage)-options.pageCount,
							totalPages:options.totalPages,
							pageCount:options.pageCount,
							itemsCount:options.itemsCount,
							pageFlag:num,
							loadingId:options.loadingId,
							sendData:options.sendData,
							sendUrl:options.sendUrl,
							historyUrl:options.historyUrl,
							loadData:options.loadData
						});
						//将当前页标签添加为选中样式
						//$("#"+options.pageDivId+" #page"+num).addClass("fen-ye");
					}
					//如果是前进按钮
				}else if($(this).is($("#"+options.pageDivId+" #pageForward"))){
					if(parseInt($("#"+options.pageDivId+" .fen-ye").text())+1 >options.totalPages){
						return;
					}
					//判断前进页码是否大于总页数，若果大于总页数则为总页数，否则前进按钮前的页数+1
					num=parseInt($("#"+options.pageDivId+" .fen-ye").text())+1 >defaults.totalPages ? defaults.totalPages:parseInt($("#"+options.pageDivId+" .fen-ye").text())+1 ;
					//清除选中样式
					$("#"+options.pageDivId).find(".fen-ye").removeClass("fen-ye");
					//取得前进按钮前的页码数
					var forwardPage=$("#"+options.pageDivId+" #pageForward").prev().text();
					//如果页码大于前进按钮前的页码数，则重新加载分页标签
					if(num>forwardPage){
						var pageStart=0;
						if(options.pageCount%2==0){
							pageStart=num;
						}else{
							pageStart=parseInt(num/options.pageCount)*options.pageCount+1;
						}
						$obj.pagetool({
							//再次加载分页标签只改变页码数，其他值不变
							startPage:pageStart,
							totalPages:options.totalPages,
							pageCount:options.pageCount,
							pageFlag:num,
							itemsCount:options.itemsCount,
							sendData:options.sendData,
							sendUrl:options.sendUrl,
							loadingId:options.loadingId,
							historyUrl:options.historyUrl,
							loadData:options.loadData
						});
						//为当前页码增加选中样式
					}
						$("#"+options.pageDivId+" #page"+num).addClass("fen-ye");
					}else{
						//清除选中样式
						$("#"+options.pageDivId).find(".fen-ye").removeClass("fen-ye");
						//增加点击的选中样式
						$(this).addClass("fen-ye");
						//如果点击的数字页码，则取得点击的页码数
						num=$("#"+options.pageDivId).find(".fen-ye").text();
					}
					//如果页码数相同，则不提交数据
					if(num==current){
						return;
					}
					var pageNum=options.itemsCount*(num-1)+1;
					//封装额外数据与当前页码数
					options.sendData["pageNum"]=num;
					options.sendData["pageLength"]=options.itemsCount;
					//发送ajax请求，并处理回调函数
					if($("#"+options.loadingId).length>0){
						$("#"+options.loadingId).showLoading();
					}
					var state=options.sendData;
					state["config"]=options.pageDivId;
					state["pageFlag"]=options.pageFlag;
					
					history.pushState(state, null,options.historyUrl+"/"+num);
					Util.method.sendAjax(defaults.sendUrl,"POST",state,"JSON",options.loadData);
			};
			//定义跳转页码的按钮单击事件
			var pageSubmitEvent=function(){
			    $('html,body').animate({ scrollTop: 0 }, 200);
				//取得输入框内的页码数
				var current=$("#"+options.pageDivId).find(".fen-ye").text();
				num=$("#"+options.pageDivId+" #currentPage").val();
				if(num==""||num==0){
					num=1;
				}
				var pageNum=options.itemsCount*(num-1)+1;
				options.sendData["pageNum"]=num;
				options.sendData["pageLength"]=options.itemsCount;
				//如果页码数相同，则不提交数据
				if(num==current||num>options.totalPages||isNaN(num)){
					$("#"+options.pageDivId+" #currentPage").val("");
					return;
				}
				num=parseInt(num);
				var startFlag=0;
				if(num%options.pageCount==0){
					startFlag=num-(options.pageCount-1);
				}else if(num<=options.totalPages&&num>(options.totalPages-options.pageCount)){
					startFlag=num-options.pageCount+1;
				}else{
					startFlag=num-(num%options.pageCount)+1;
				}
				$obj.pagetool({
					//再次加载分页标签只改变页码数，其他值不变
					startPage:num,
					totalPages:options.totalPages,
					pageCount:options.pageCount,
					pageFlag:num,
					itemsCount:options.itemsCount,
					sendData:options.sendData,
					sendUrl:options.sendUrl,
					loadingId:options.loadingId,
					historyUrl:options.historyUrl,
					loadData:options.loadData
				});
				//发送ajax请求，并处理回调函数
				if($("#"+options.loadingId).length>0){
					$("#"+options.loadingId).showLoading();
				}
				var state=options.sendData;
				state["config"]=options.pageDivId;
				state["pageFlag"]=options.pageFlag;
				history.pushState(state, null,options.historyUrl+"/"+num);
				Util.method.sendAjax(defaults.sendUrl,"POST",state,"JSON",options.loadData);
			};
			//判断其实标签是否大于0，不大于0则默认为1
			options.startPage=options.startPage>0? options.startPage: 1;
			//清空父div下的页码标签
			$("#"+options.pageDivId).empty();
			//拼接页码标签
			var ulFirst="<table border='0' cellspacing='0' cellpadding='0' class='fy-table'><tr><td>"+
						"<form id='pagePage'  method='post'  name='page'><div id='pageDiv' class='fy-con' ><ul class='pagination'><li id='pageBack'></li>";
			var li="";
			//获取起始页，如果起始页大于总页数，则为总页数，否则为起始页
			var startPage=options.startPage>options.totalPages ?options.totalPages:options.startPage;
			var begin =1;
			if(startPage-options.pageCount>0){
				var flag=startPage%options.pageCount;
				if(flag!=0){
					var i= parseInt(startPage/options.pageCount);
					begin=i*options.pageCount+1;
					if(begin+options.pageCount>options.totalPages){
						begin=options.totalPages-options.pageCount+1;
					}
				}else{
					var i= parseInt(startPage/options.pageCount)-1;
					begin=i*options.pageCount+1;
				}
			}else{
				begin=1;
			}

			//获取总页数，如果起始页大约总页数，则为总页数+1，否则如果开始也+页码数之和大于总页数，则为总页数+1，否则为开始也+页码数
			var all=begin >=options.totalPages ? options.totalPages+1 : parseInt(options.pageCount)+parseInt(begin)>options.totalPages?options.totalPages+1 : parseInt(options.pageCount)+parseInt(begin);
			//遍历生成页码，并定义首个页码为选中状态
			for(i=begin;i<all;i++){
//				if(i==begin){
//					li=li+"<li id='page"+i+"'  >" +i+"</li>";
//				}else{
					li=li+"<li id='page"+i+"' >" +i+"</li>";
//				}
			}
			
			//生成前进按钮到页码跳转区域
			var ulLast="<li id='pageForward' class='last' ></li><span class='span-f'>총" +
					"<label>"+options.totalPages+"</label>" +"페이지</span>" +
					"<span class='span-shuru'>제" +
					"<input type='text' name='currentPage' id='currentPage' >" +"페이지로</span>" +
					"<a class='fy-btn' id='pageSubmit'>GO</a></ul></div></form></td></tr></table>";
			//拼接整个页码标签
			var str=ulFirst+li+ulLast;
			//将页码标签追加到需要引入的父div上
			$("#"+options.pageDivId).append($(str));
			
			if(options.pageFlag==0){
				$("#"+options.pageDivId+" #page"+startPage).addClass("fen-ye");
			}else{
				$("#"+options.pageDivId+" #page"+options.pageFlag).addClass("fen-ye");
			}
			$("#"+options.pageDivId+" pagePage").on("submit",function(){
				return false;
			});
			//取消页码绑定的单击事件
			$("#"+options.pageDivId+" li").unbind("click");
			//绑定页码绑定的单击事件
			$("#"+options.pageDivId+" li").bind("click",pageEvent);
			//取消跳转按钮单击事件
			$("#"+options.pageDivId+" #pageSubmit").unbind("click");
			//绑定跳转按钮单击事件
			$("#"+options.pageDivId+" #pageSubmit").bind("click",pageSubmitEvent);
			$("#currentPage").on("keyup",function(){
				num=$(this).val();
				if(isNaN(num)){
					$(this).val(1);
				}
			});
			$("#pagePage").submit(function(){return false;});
			//return {historyConfig:options.pageDivId,sendUrl:options.sendUrl,pageNum:num,sendData:options.sendData,loadData:options.loadData};
	}
})(jQuery);