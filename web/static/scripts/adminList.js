//页面加载完成初始化页面
$(function() {
	AdminList.init();
});
var AdminList = {
	//初始化方法
	init : function() {
		AdminList.event.init();// 事件层
		AdminList.service.init();
        AdminList.validate.adminForm();
	},
	//事件层
	event : {
		//事件层初始化方法	
	    init : function() {
	    	AdminList.event.bindEvent(); //初始化绑定事件
            AdminList.event.lockBtnEvent();
            AdminList.event.removeBtnEvent();
            AdminList.event.batchRemoveBtnEvent();
            AdminList.event.editBtnEvent();
		},
		//绑定事件
		bindEvent : function() {
            $("#selectAll").on("click", function () {
                Util.method.checkBoxCheckAll("adminTbody", "CheckboxGroup1", this);
            });
            $(".close_bbs").on("click", function () {
               AdminList.event.resetAdminEvent();
            });
            $("#resetBtn").on("click",function(){
                AdminList.event.resetAdminEvent();
            });
            $("#addAdmin").on("click", function () {
                $("#subBtn").attr({
                    "disabled":false,
                    "class":"save-btn"
                });
                $("#saveAdminForm").data("oper","add");
                $("#saveAdminForm").data("info","info");
                $("#boxPassword").rules("add",{required: true, minlength: 6});
                $("#boxCheckPassword").rules("add",{required: true, minlength: 6, equalTo: "#boxPassword"});
                $("#adminBox").show();
            });
            $("#boxAccount").on("blur", function () {
                if(AdminList.validate.checkNull("boxAccount")){
                    var data = {account:$("#boxAccount").val().trim()};
                    AdminList.service.checkAccount(data,$(this));
                }else{
                    $("#subBtn").attr({
                        "disabled": true,
                        "class": "quxiao-btn"
                    });
                }
            });
        },
        lockBtnEvent:function(){
            $("#adminTbody").on("click","#lockBtn",function(){
                var $obj=$(this);
                var name=$obj.parent().parent().children().eq(2).html();
                MyAlert.prompt("确定要锁定/解锁管理员["+name+"]?","warn",function(){
                    var data={status:$obj.parent().data("lock"),id:$obj.parent().data("info"),name:name};
                    AdminList.service.lockService(data,$obj);
                });
            })
        },
        removeBtnEvent:function(){
            $("#adminTbody").on("click","#removeBtn",function(){
                var $obj=$(this);
                var name=$obj.parent().parent().children().eq(2).html();
                MyAlert.prompt("确定要删除管理员["+name+"]?","warn",function(){
                    var data={ids:$obj.parent().data("info"),name:name};
                    AdminList.service.removeService(data,$obj);
                });
            });
        },
        batchRemoveBtnEvent:function(){
            $("#batchRemove").on("click",function(){
                var $trs=$("#adminTbody input[name='CheckboxGroup1']:checked").parent().parent();
                var data={ids:"",name:""};
                $trs.each(function(){
                    data.ids=data.ids+$(this).children().last().data("info")+"-";
                    data.name=data.name+$(this).children().eq(2).html()+",";
                });
                data.ids=data.ids.substring(0,data.ids.length-1);
                data.name=data.name.substring(0,data.name.length-1);
                if(!data.ids){
                    MyAlert.prompt("请选择要删除的管理员!","info");
                }else{
                    MyAlert.prompt("确定要删除管理员["+data.name+"]?","warn",function(){
                        AdminList.service.removeService(data);
                    });
                }
            });
        },
        resetAdminEvent:function(){
            $("#saveAdminForm")[0].reset();
            $("#saveAdminForm .help-block").each(function(){
                $(this).empty();
            });
            $("#adminBox").hide();
        },
        editBtnEvent:function(){
            $("#adminTbody").on("click","#editBtn",function(){
                $("#boxPassword").rules("remove");
                $("#boxCheckPassword").rules("remove");
                $("#boxCheckPassword").rules("add",{equalTo: "#boxPassword"});
                var data={id:$(this).parent().data("info")};
                $("#subBtn").attr({
                    "disabled":false,
                    "class":"save-btn"
                });
                $("#saveAdminForm").data("oper","update");
                AdminList.service.adminDetail(data,$("#saveAdminForm"));
                $("#adminBox").show();
            })
        },
	},
	service:{
		init:function(){
			Util.method.sendAjax("admin/init","GET",null,"JSON",function(result){
				if(result.status==1){
					var data=result.data;
                    var role=$("#role").data("role");
                    var department=$("#department").data("department");
					$.each(data,function(k,v){
                        if(k=='roles'){
                            $.each(v,function(){
                                var obj="<option value='"+this.role_id+"'>"+this.role_name+"</option>";
                                $("#role").append($(obj));
                                var check="<label><input type='checkbox' name='role' value='"+this.role_id+"'  class='checkbox'><span class='check-span'>"+this.role_name+"</span></label>";
                                $("#roleCheck").append($(check));
                                if(role!=""&&role==this.role_id){
                                    $("#role").val(this.role_id);
                                }
                            });
                        }else if(k=='departments'){
                            $.each(v,function(){
                                var obj="<option value='"+this+"'>"+this+"</option>"
                                $("#department").append($(obj));
                                if(department!=""&&department==this){
                                    $("#department").val(this.toString());
                                }
                            });
                        }else{
                            return false;
                        }
					});
				}
			})
		},
        lockService:function(data,$obj){
            Util.method.sendAjax("admin/lock","POST",data,"JSON",function(result){
                if(result.status==1){
                    $obj.parent().data("lock",result.data);
                    $obj.parent().prev().html(result.msg);
                    MyAlert.prompt("操作成功!","success");
                }else{
                    MyAlert.prompt(result.msg,"error");
                }
            });
        },
        removeService:function(data){
            Util.method.sendAjax("admin/remove","POST",data,"JSON",function(result){
                if(result.status==1){
                    MyAlert.prompt(result.msg,"success",function(){
                        location.reload(true);
                    });
                }else{
                    MyAlert.prompt(result.msg,"error",function(){
                        location.reload(true);
                    });
                }
            });
        },
        checkAccount:function(data,$obj){
            Util.method.sendAjax("admin/check","POST",data,"JSON",function(result){
                if(result.status!=1){
                    $obj.next().html(result.msg);
                    $("#subBtn").attr({
                        "disabled": true,
                        "class": "quxiao-btn"
                    });
                }else{
                    $obj.next().empty();
                    $("#subBtn").attr({
                        "disabled":false,
                        "class":"save-btn"
                    });
                }
            });
        },
        adminDetail:function(data,$obj){
            Util.method.sendAjax("admin/detail","POST",data,"JSON",function(result){
                if(result.status==1){
                    $.each(result.data,function(k,v){
                        if(k=='admin'){
                            $obj.find("input[name='account']").val(v.account);
                            $obj.find("input[name='name']").val(v.name);
                            $obj.find("input[name='department']").val(v.department);
                            $obj.find("input[name='comment']").val(v.comment);
                            $obj.data("info", v.admin_id);
                            $obj.data("default", v.account);
                        }
                        if(k=='roles'){
                            $obj.find("input[name='role']").each(function(){
                                var $roleInput= $(this);
                                $.each(v,function(){
                                   if($($roleInput).val()==this.role_id){
                                       $roleInput[0].checked=true;
                                    }
                                });
                            });
                        }
                    });

                }else{
                    $(".close_bbs").click();
                    MyAlert.prompt(result.msg,"warn");
                }
            });
        }
	},
	//验证功能模块
	validate: {
        /**
         * 检查是否为空
         * @param id 要检查的input的id
         * @returns {Boolean} 如果是空则返回false，否则返回true
         */
        checkNull: function (id) {
            var str = $("#" + id).val();
            if (!str) {
                return false;
            }
            return true;
        },
        adminForm: function () {
            $("#saveAdminForm").validate({
                onfocusout: function (element) {
                    $(element).valid();
                },
                submitHandler: function () {
                    $(".loading").show();
                    $("#subBtn").attr({
                        "disabled": true,
                        "class": "quxiao-btn"
                    });
                    var oper=$("#saveAdminForm").data("oper");
                    var info=$("#saveAdminForm").data("info");
                    var url="admin/save?oper="+oper;
                    if(info!="info"){
                        url=url+"&info="+info+"&def="+$("#saveAdminForm").data("default");
                    }
                    $("#saveAdminForm").ajaxSubmit({
                        url: url,
                        type: "POST",
                        dataType: "JSON",
                        success: function (result) {
                            $(".loading").hide();
                            if (result.status == 0) {
                                MyAlert.prompt(result.msg, "error", function () {
                                    location.reload(true);
                                });
                                return false;
                            }
                            if (result.status == 1) {
                                MyAlert.prompt(result.msg, "success", function () {
                                    location.reload(true);
                                });
                                return false;
                            }
                        },
                        error: function (e) {
                            console.info(e);
                            location.reload(true);
                        }
                    });
                    return false;
                },
                rules: {
                    account: {required: true},
                    password: {required: true, rangelength: [6,18]},
                    checkpassword: {required: true, rangelength: [6,18], equalTo: "#boxPassword"},
                    name: {required: true},
                    department: {required: true},
                    role: {required: true},
                },
                errorPlacement: function (error, element) {
                    if (element[0].type == "checkbox") {
                        $(element).parent().parent().next().html($(error));
                    } else {
                        $(element).next().html($(error));
                    }
                },
                messages: {
                    account: {required: "请输入账号"},
                    password: {required: "请输入密码", rangelength: "请输入{0}~{1}位密码"},
                    checkpassword: {required: "请输入确认密码", rangelength: "请输入{0}~{1}位确认密码", equalTo: "密码输入不一致"},
                    name: {required: "请输入姓名"},
                    department: {required: "请输入部门"},
                    role: {required: "请选择角色"},
                }
            });
        },
    },
};