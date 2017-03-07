<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="../top.jsp"%>
        <script type="text/javascript" src="static/basic/validate/jquery.validate.min.js"></script>
        <script type="text/javascript" src="static/basic/validate/additional-methods.min.js"></script>
        <script type="text/javascript" src="static/basic/validate/jquery.metadata.js"></script>
        <script type="text/javascript" src="static/basic/js/jquery.form.js"></script>
        <script type="text/javascript" src="static/scripts/roleList.js"></script>
		<title>管理员管理</title>
	</head>
	<body>
	<div id="contain">
		<section id="main-content">
			<section class="wrapper">
				<div class="adminuser-contain">
					<span class="span-title">管理员管理</span>
					<!--tab分页-->
					<div class="tab-con">
						<ul>
							<li><a href="admin/list" >账号管理</a></li>
							<li><a href="admin/role" class="tab-active" >权限管理</a></li>
						</ul>
					</div>
					<!--查询类-->
					<div class="summary">
						<shiro:hasPermission name="admin:add">
						<div class="btn-group" id="addRole">
							<input type="button" class="btn-add"  value="添加"  />
							<i class="fa fa-plus"></i>
						</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="admin:remove">
						<div class="btn-group" id="batchRemove">
							<input type="button" class="btn-del"  value="删除" />
							<i class="fa fa-trash-o"></i>
						</div>
						</shiro:hasPermission>
					</div>

					<!--表格-->
					<div class="table-content padding-p15">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="collect-table">
							<thead>
							<tr>
								<th class="table-t41">
									<input type="checkbox" name="CheckboxGroup1" class="Check-box" id="selectAll" />
								</th>
								<th class="table-t07">序号</th>
								<th >角色名称</th>
								<th >角色权限</th>
								<th class="table-t10">操作</th>
							</tr>
							</thead>
							<tbody id="roleTbody">
							<c:choose>
								<c:when test="${result.status==0}">
									<tr>
                                        <td colspan='5'>
                                            <p style="text-align: center">${result.msg}</p>
                                        </td>
                                    </tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${result.data}" var="i" varStatus="status">
										<tr>
											<td class="table-t41">
												<input type="checkbox" name="CheckboxGroup1" class="Check-box" />
											</td>
											<td class="table-t07">${status.index+1}</td>
											<td >${i.role_name}</td>
											<td >${i.actions}</td>
											<td class="table-t10" data-info="${i.role_id}" >
												<shiro:hasPermission name="admin:edit">
												<a class="table-btn btn-primary" title="编辑" id="editBtn" ><i class="fa fa-pencil"></i></a>
												</shiro:hasPermission>
												<shiro:hasPermission name="admin:remove">
												<a class="table-btn btn-danger" title="删除" id="removeBtn"><i class="fa fa-trash-o "></i></a>
												</shiro:hasPermission>
											</td>
										</tr>
									</c:forEach>

								</c:otherwise>
							</c:choose>
							</tbody>
						</table>


					</div>
                    <%--分页--%>
                    <div>
                        <c:if test="${result.status==1}">
                            <base:pageList list_number="${result.count}" frame_size="5" page_size="${result.pageSize }" post_url="" css_name="text-center,pagination"/>
                        </c:if>
                    </div>
					</div>
			</section>
		</section>
	</div>
	<%--弹窗--%>
    <div class="box" id="roleBox">
        <section class="widget max-wid" style="margin-top: 100px;">
            <header>
                <hgroup>
                    <h1 id="roleBoxTitle">权限管理</h1>
                    <a class="close_bbs"></a>
                </hgroup>
            </header>
            <div class="popup-body">
                <div class="wssu width-w100">
                    <form id="saveRoleForm" action="" method="post" data-oper="oper" data-info="info" data-default="default">
                        <ul class="wssu-con">
                            <li>角色名称</li>
                            <li>
                                <input type="text" value="" placeholder="角色名称" id="boxRoleName" name="role_name"  class="input-text width-w90" />
                                <p class=" help-block"></p>
                            </li>
                        </ul>
						<ul class="wssu-con">
							<li>角色标记</li>
							<li>
								<input type="text" value="" placeholder="角色标记" id="boxRoleFlag" name="role_flag"  class="input-text width-w90" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
								<p class=" help-block"></p>
							</li>
						</ul>
                        <ul class="wssu-con">
                            <li>请选择权限</li>
                            <li>
                                <p class="p-checkbox" id="actionCheck">
                                    <%--ajax异步加载角色列表--%>
                                </p>
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>&nbsp;</li>
                            <li>
								<shiro:hasPermission name="admin:add or admin:edit">
                                <input type="submit" class="save-btn" id="subBtn" value="保存"/>
								</shiro:hasPermission>
                                <input type="button" class="quxiao-btn" id="resetBtn"  value="取消"/>
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
        </section>
    </div>
	</body>
</html>
