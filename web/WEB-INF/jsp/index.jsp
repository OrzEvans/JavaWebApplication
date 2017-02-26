<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
	<head>
		<%@include file="../top.jsp"%>
		<title>首页</title>
	</head>
	<body>
	<div id="contain">
		<!--
            描述：顶部
       -->

		<!--#include file="top.html"-->


		<!--
            描述：中间
        -->
		<section id="main-content">
			<section class="wrapper">
				<div class="adminuser-contain">
					<span class="span-title">电影管理</span>
					<!--tab分页-->
					<div class="tab-con">
						<ul>
							<li><a href="#" class="tab-active">会员资料</a></li>
							<li><a href="#" >会员资料</a></li>
							<li><a href="#" >会员资料</a></li>
						</ul>
					</div>
					<!--查询类-->
					<div class="summary">
						<div class="btn-group" onclick="">
							<input type="button" class="btn-add"  value="添加"  />
							<i class="fa fa-plus"></i>
						</div>
						<div class="btn-group" onclick="">
							<input type="button" class="btn-del"  value="删除" />
							<i class="fa fa-trash-o"></i>
						</div>
						<input type="text" value="" placeholder="文本输入框" class="form-control"/>
						<select class="form-control">
							<option value="0">类别</option>
						</select>
						<div class="btn-group" onclick="">
							<input type="button" class="btn-search"  value="查询" />
							<i class="fa fa-search"></i>
						</div>
					</div>

					<!--表格-->
					<div class="table-content padding-p15">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="collect-table">
							<thead>
							<tr>
								<th class="table-t41">
									<input type="checkbox" name="CheckboxGroup1" class="Check-box" />
								</th>
								<th class="table-t07">管理员ID</th>
								<th >账号</th>
								<th >姓名</th>
								<th >按钮</th>
								<th class="table-t10">操作</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td class="table-t41">
									<input type="checkbox" name="CheckboxGroup1" class="Check-box" />
								</td>
								<td class="table-t07">1</td>
								<td >o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1oo1bee_hoo1<span class="Y">【我们都是还dsfkjskf】</span></td>
								<td >张丽张思维bee_hoo1bebee_hoo1</td>
								<td >
									<a class="table-btn btn-default " title="" href="">1</a>
									<a class="table-btn btn-primary " title="" href="">2</a>
									<a class="table-btn btn-success " title="" href="">3</a>
									<a class="table-btn  btn-info " title="" href="">4</a>
									<a class="table-btn  btn-warning " title="" href="">5</a>
									<a class="table-btn btn-danger " title="" href="">6</a>
									<a class="table-btn btn-seeing " title="" href="">7</a>

								</td>
								<td class="table-t10">
									<a class="table-btn btn-primary" title="编辑" href=""><i class="fa fa-pencil"></i></a>
									<a class="table-btn btn-danger" title="删除"><i class="fa fa-trash-o "></i></a>
								</td>
							</tr>
							<tr>
								<td class="table-t41">
									<input type="checkbox" name="CheckboxGroup1" class="Check-box" />
								</td>
								<td class="table-t07">1</td>
								<td >o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1o1bee_hoo1bee_hoo1</td>
								<td >张丽张思维bee_hoo1bebee_hoo1</td>
								<td >
									<a class="table-btn btn-default " title="" href="">1</a>
									<a class="table-btn btn-primary " title="" href="">2</a>
									<a class="table-btn btn-success " title="" href="">3</a>
									<a class="table-btn  btn-info " title="" href="">4</a>
									<a class="table-btn  btn-warning " title="" href="">5</a>
									<a class="table-btn btn-danger " title="" href="">6</a>
									<a class="table-btn btn-seeing " title="" href="">7</a>

								</td>
								<td class="table-t10">
									<a class="table-btn btn-primary" title="编辑" href=""><i class="fa fa-pencil"></i></a>
									<a class="table-btn btn-danger" title="删除"><i class="fa fa-trash-o "></i></a>
								</td>
							</tr>
							</tbody>
						</table>


					</div>


					<!--表格-->
					<div class="table-content padding-p15">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" class="gather-table">
							<thead>
							<tr>
								<th class="table-t41">
									<input type="checkbox" name="CheckboxGroup1" class="Check-box" />
								</th>
								<th class="table-t07">管理员ID</th>
								<th >账号</th>
								<th >姓名</th>
								<th class="table-t10">操作</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td class="table-t41">
									<input type="checkbox" name="CheckboxGroup1" class="Check-box" />
								</td>
								<td class="table-t07">1</td>
								<td ><span class="Y">是积分收费就</span></td>
								<td >张丽张思维bee_hoo1bebee_ho张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1张丽张思维bee_hoo1bebee_hoo1o1</td>
								<td class="table-t10">
									<a class="table-btn btn-primary" title="编辑" href=""><i class="fa fa-pencil"></i></a>
									<a class="table-btn btn-danger" title="删除"><i class="fa fa-trash-o "></i></a>
								</td>
							</tr>
							</tbody>
						</table>

						<!--分页-->
						<div>
							<table border="0" cellspacing="0" cellpadding="0" class="fy-table">
								<tr>
									<td>
										<form id="page"  method="post"  name="page">
											<div id="pageDiv" class="fy-con" >
												<ul class="pagination">
													<li></li>
													<li class="fen-ye">1</li>
													<li>2</li>
													<li>3</li>
													<li>4</li>
													<li>5</li>
													<li class="last"></li>
													<span class="span-f">共<label>18</label> 页</span>
														<span class="span-shuru">到第
															<input type="text" name="currentPage" id="currentPage" value="1"/>页
														</span>
													<a class="fy-btn">GO</a>
												</ul>
											</div>
										</form>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>

				<!--
                    作者：994697185@qq.com
                    时间：2016-09-08
                    描述：网站名称
                -->
				<div class="wssu">
					<span class="span-title">网站设置</span>
					<ul class="wssu-con">
						<li>文本框</li>
						<li>
							<input type="text" value="" placeholder="用户名"  class="input-text width-w90 inpur-bor" />
							<p class=" help-block"></p>
						</li>
					</ul>

					<ul class="wssu-con">
						<li>文本区域文本区域</li>
						<li>
							<textarea name="" rows="" cols="" class="teatare-text width-w100 height-h01"></textarea>
							<p class=" help-block"></p>
						</li>

					</ul>
					<ul class="wssu-con">
						<li>网站大logo</li>
						<li>
							<input type="text" size="20" name="upfile3" id="upfile3" class="file-text">
							<input type="button" value="浏览" onclick="path3.click()" class="file-btn">
							<input type="file" id="path3" style="display:none" onchange="upfile3.value=this.value">
							<div style="width: 100%;  margin-top: 35px;">sdfsadfsdaf</div>
						</li>
					</ul>
					<ul class="wssu-con">
						<li>下拉菜单</li>
						<li>
							<select class="drop-down width-w30">
								<option>未开通</option>
								<option>正常</option>
								<option>黑名单</option>
								<option>到期用户</option>
							</select>
							<input type="text" value="" placeholder="用户名"  class="input-text  inpur-bor" />
						</li>
					</ul>
					<ul class="wssu-con">
						<li>下拉菜单</li>
						<li>
							<select class="drop-down width-w30">
								<option>未开通</option>
								<option>正常</option>
								<option>黑名单</option>
								<option>到期用户</option>
							</select>
							<span class="display-i align-r width-w10 ">张三</span>
							<input type="text" value="" placeholder="用户名"  class="input-text width-w30  inpur-bor" />
						</li>
					</ul>
					<ul class="wssu-con">
						<li>选框</li>
						<li>
							<p class="p-radio margin-t02">
								<label>
									<input type="radio" name="RadioGroup1" value="单选" id="RadioGroup1_0" class="radio" />
									是
								</label>

								<label>
									<input type="radio" name="RadioGroup1" value="单选" id="RadioGroup1_1" class="radio"/>
									否
								</label>
							</p>

						</li>
					</ul>
					<ul class="wssu-con">
						<li>选框</li>
						<li>
							<p class="p-checkbox">
								<label>
									<input type="checkbox" name="CheckboxGroup1" value="复选框" id="CheckboxGroup1_1" class="checkbox">
									<span class="check-span">复选框</span>
								</label>
								<label>
									<input type="checkbox" name="CheckboxGroup1" value="复选框" id="CheckboxGroup1_2" class="checkbox">
									<span>复选框</span>
								</label>
							</p>

						</li>
					</ul>


					<ul class="wssu-con">
						<li>开关</li>
						<li style="position: relative;">
							<label class="button">
								<input type="checkbox">
								<span></span>
								<span></span>
								<span></span>
							</label>
							<div class="bootstrap-switch-square">
								<input type="checkbox" data-toggle="switch" id="custom-switch-04" />
							</div>
							<p class=" help-block"></p>
						</li>
					</ul>

					<ul class="wssu-con">
						<li>&nbsp;</li>
						<li>
							<input type="button" class="save-btn" value="保存"/>
							<input type="button" class="quxiao-btn" value="取消"/>
						</li>
					</ul>
				</div>

			</section>
		</section>
		<!--
            作者：994697185@qq.com
            时间：2016-09-03
            描述：底部
        -->
		<footer>

		</footer>
	</div>
		
	</body>
</html>
