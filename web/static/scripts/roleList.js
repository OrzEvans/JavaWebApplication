//页面加载完成初始化页面
$(function() {
	RoleList.init();
});
var RoleList = {
	//初始化方法
	init : function() {
		RoleList.event.init();// 事件层
		RoleList.service.init();
        RoleList.validate.roleForm();
	},
	//事件层
	event : {
		//事件层初始化方法
	    init : function() {
	    	RoleList.event.bindEvent(); //初始化绑定事件
            RoleList.event.removeBtnEvent();
            RoleList.event.batchRemoveBtnEvent();
            RoleList.event.editBtnEvent();
		},
		//绑定事件
		bindEvent : function() {
            $("#selectAll").on("click", function () {
                Util.method.checkBoxCheckAll("roleTbody", "CheckboxGroup1", this);
            });
            $(".close_bbs").on("click", function () {
               RoleList.event.resetRoleEvent();
            });
            $("#resetBtn").on("click",function(){
                RoleList.event.resetRoleEvent();
            });
            $("#addRole").on("click", function () {
                $("#subBtn").attr({
                    "disabled":false,
                    "class":"save-btn"
                });
                $("#saveRoleForm").data("oper","add");
                $("#saveRoleForm").data("info","info");
                $("#roleBox").show();
            });
            $("#boxRoleName").on("blur", function () {
                if(RoleList.validate.checkNull("boxRoleName")){
                    var data = {role:$("#boxRoleName").val().trim()};
                    RoleList.service.checkRoleName(data,$(this));
                }else{
                    $("#subBtn").attr({
                        "disabled": true,
                        "class": "quxiao-btn"
                    });
                }
            });
            $("#boxRoleFlag").on("blur", function () {
                if(RoleList.validate.checkNull("boxRoleFlag")){
                    var data = {name:$("#boxRoleFlag").val().trim()};
                    RoleList.service.checkRoleFlag(data,$(this));
                }else{
                    $("#subBtn").attr({
                        "disabled": true,
                        "class": "quxiao-btn"
                    });
                }
            });
        },
        removeBtnEvent:function(){
            $("#roleTbody").on("click","#removeBtn",function(){
                var $obj=$(this);
                var name=$obj.parent().parent().children().eq(2).html();
                MyAlert.prompt("确定要删除角色["+name+"]?","warn",function(){
                    var data={ids:$obj.parent().data("info")};
                    RoleList.service.removeService(data,$obj);
                });
            });
        },
        batchRemoveBtnEvent:function(){
            $("#batchRemove").on("click",function(){
                var $trs=$("#roleTbody input[name='CheckboxGroup1']:checked").parent().parent();
                var data={ids:"",name:""};
                $trs.each(function(){
                    data.ids=data.ids+$(this).children().last().data("info")+"-";
                    data.name=data.name+$(this).children().eq(2).html()+",";
                });
                data.ids=data.ids.substring(0,data.ids.length-1);
                data.name=data.name.substring(0,data.name.length-1);
                if(!data.ids){
                    MyAlert.prompt("请选择要删除的角色!","info");
                }else{
                    MyAlert.prompt("确定要删除角色["+data.name+"]?","warn",function(){
                        RoleList.service.removeService(data);
                    });
                }
            });
        },
        resetRoleEvent:function(){
            $("#saveRoleForm")[0].reset();
            $("#saveRoleForm .help-block").each(function(){
                $(this).empty();
            });
            $("#roleBox").hide();
        },
        editBtnEvent:function(){
            $("#roleTbody").on("click","#editBtn",function(){
                var data={id:$(this).parent().data("info"),name:$(this).parent().parent().children().eq(2).html()};
                $("#subBtn").attr({
                    "disabled":false,
                    "class":"save-btn"
                });
                $("#saveRoleForm").data("oper","update");
                RoleList.service.roleDetail(data,$("#saveRoleForm"));
                $("#roleBox").show();
            })
        },
	},
	service:{
		init:function(){
			Util.method.sendAjax("admin/actionInit","GET",null,"JSON",function(result){
				if(result.status==1){
					$.each(result.data,function(){
                        var check="<label><input type='checkbox' name='action' value='"+this.action_id+"'  class='checkbox'><span class='check-span'>"+this.action_name+"</span></label>";
                        $("#actionCheck").append($(check));
					});
				}
			})
		},
        removeService:function(data){
            Util.method.sendAjax("admin/removeRole","POST",data,"JSON",function(result){
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
        checkRoleName:function(data,$obj){
            Util.method.sendAjax("admin/roleCheck","POST",data,"JSON",function(result){
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
        checkRoleFlag:function(data,$obj){
            Util.method.sendAjax("admin/flagCheck","POST",data,"JSON",function(result){
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
        roleDetail:function(data,$obj){
            Util.method.sendAjax("admin/roleDetail","POST",data,"JSON",function(result){
                if(result.status==1){
                    $obj.find("input[name='role_name']").val(data.name);
                    $obj.data("info",data.id);
                    $.each(result.data,function() {
                        var action =this.action_id;
                        $obj.find("input[name='action']").each(function () {
                            if ($(this).val() == action) {
                                $(this)[0].checked = true;
                            }
                        });
                    });
                    $("#boxRoleFlag").val(result.msg);
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
        roleForm: function () {
            $("#saveRoleForm").validate({
                onfocusout: function (element) {
                    $(element).valid();
                },
                submitHandler: function () {
                    $(".loading").show();
                    $("#subBtn").attr({
                        "disabled": true,
                        "class": "quxiao-btn"
                    });
                    var oper=$("#saveRoleForm").data("oper");
                    var info=$("#saveRoleForm").data("info");
                    var url="admin/saveRole?oper="+oper;
                    if(info!="info"){
                        url=url+"&info="+info;
                    }
                    $("#saveRoleForm").ajaxSubmit({
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
                    role_name: {required: true},
                    action: {required: true},
                    role_flag:{required:true},
                },
                errorPlacement: function (error, element) {
                    if (element[0].type == "checkbox") {
                        $(element).parent().parent().next().html($(error));
                    } else {
                        $(element).next().html($(error));
                    }
                },
                messages: {
                    role_name: {required: "请输入角色名称"},
                    action: {required: "请选择权限"},
                    role_flag:{required:"请输入角色标记"},
                }
            });
        },
    },
};