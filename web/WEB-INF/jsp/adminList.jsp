<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="../top.jsp"%>
        <script type="text/javascript" src="static/basic/validate/jquery.validate.min.js"></script>
        <script type="text/javascript" src="static/basic/validate/additional-methods.min.js"></script>
        <script type="text/javascript" src="static/basic/validate/jquery.metadata.js"></script>
        <script type="text/javascript" src="static/basic/js/jquery.form.js"></script>
        <script type="text/javascript" src="static/scripts/adminList.js"></script>
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
							<li><a href="admin/list" class="tab-active">账号管理</a></li>
							<li><a href="admin/role" >权限管理</a></li>
						</ul>
					</div>
					<!--查询类-->
					<div class="summary">
                        <shiro:hasPermission name="admin:add">
						<div class="btn-group" id="addAdmin">
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
                        <form action="admin/search" method="post">
                            <input type="text" name="account" placeholder="账号" class="form-control" id="account" value="${admin.account}"/>
                            <input type="text" name="name" placeholder="真实姓名" class="form-control" id="name" value="${admin.name}"/>
                            <select class="form-control" id="role" name="role" data-role="${role}">
                                <option value="0">请选择角色</option>
                            </select>
							<select class="form-control" id="department" name="department" data-department="${admin.department}">
								<option value="0">请选择部门</option>
							</select>
                            <div class="btn-group" onclick="">
                                <input type="submit" class="btn-search"  value="查询" />
                                <i class="fa fa-search"></i>
                            </div>
                        </form>
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
								<th >账号</th>
								<th >姓名</th>
								<th >部门</th>
								<th >最后登录时间</th>
                                <th >锁定状态</th>
								<th class="table-t10">操作</th>
							</tr>
							</thead>
							<tbody id="adminTbody">
							<c:choose>
								<c:when test="${result.status==0}">
                                    <tr>
                                        <td colspan='8'>
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
											<td >${i.account}</td>
											<td >${i.name}</td>
											<td >${i.department}</td>
											<td >${fn:substring(i.last_login_time,0,19)}</td>
                                            <td >
                                                <c:if test="${i.is_locked=='N'}">未锁定</c:if>
                                                <c:if test="${i.is_locked=='Y'}">已锁定</c:if>
                                            </td>
											<td class="table-t10" data-info="${i.admin_id}" data-lock="${i.is_locked}">
                                                <shiro:hasPermission name="admin:edit">
												<a class="table-btn btn-warning" title="锁定/解锁" id="lockBtn" ><i class="fa fa-lock"></i></a>
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
    <div class="box" id="adminBox">
        <section class="widget max-wid" style="margin-top: 100px;">
            <header>
                <hgroup>
                    <h1 id="adminBoxTitle">账号管理</h1>
                    <a class="close_bbs"></a>
                </hgroup>
            </header>
            <div class="popup-body">
                <div class="wssu width-w100">
                    <form id="saveAdminForm" action="" method="post" data-oper="oper" data-info="info" data-default="default">
                        <ul class="wssu-con">
                            <li>账号</li>
                            <li>
                                <input type="text" value="" placeholder="账号" id="boxAccount" name="account"  class="input-text width-w90" />
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>密码</li>
                            <li>
                                <input type="password" value="" placeholder="密码" name="password" id="boxPassword"  class="input-text width-w90 " />
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>确认密码</li>
                            <li>
                                <input type="password" value="" placeholder="确认密码" name="checkpassword" id="boxCheckPassword" class="input-text width-w90" />
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>姓名</li>
                            <li>
                                <input type="text" value="" placeholder="姓名" name="name" id="boxName" class="input-text width-w90" />
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>部门</li>
                            <li>
                                <input type="text" value="" placeholder="部门" name="department" id="boxDepartment"  class="input-text width-w90" />
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>备注</li>
                            <li>
                                <textarea name="" rows="" cols="" name="comment" id="boxComment" class="teatare-text width-w90 height-h01"></textarea>
                                <p class=" help-block"></p>
                            </li>
                        </ul>
                        <ul class="wssu-con">
                            <li>请选择角色</li>
                            <li>
                                <p class="p-checkbox" id="roleCheck">
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
