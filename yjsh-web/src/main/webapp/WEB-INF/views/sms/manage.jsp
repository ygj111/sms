<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!--<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
   	 	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title>三和短信平台</title>
		<script type="text/javascript">
			var ctx="${ctx}";
		</script>
		<!--jQuery-->
		<script type="text/javascript" src="${ctx}/assets/jquery/1.11.3/jquery.min.js" ></script>
		<!--bootstrap-->
		<script type="text/javascript" src="${ctx}/assets/bootstrap/3.3.5/js/bootstrap.min.js" ></script>
		<link rel="stylesheet" href="${ctx}/assets/bootstrap/3.3.5/css/bootstrap.min.css" />
		<!--font-awsome-->
		<link rel="stylesheet" href="${ctx}/assets/fontawesome/css/font-awesome.min.css" />
		<!--mentisMenu-->
		<script type="text/javascript" src="${ctx}/assets/metisMenu/2.2.0/metisMenu.min.js" ></script>
		<link rel="stylesheet" href="${ctx}/assets/metisMenu/2.2.0/metisMenu.min.css" />
		<!--datatables-->
		<script type="text/javascript" src="${ctx}/assets/datatables/1.10.10/jquery.dataTables.min.js" ></script>
		<script type="text/javascript" src="${ctx}/assets/datatables/1.10.10/dataTables.bootstrap.min.js" ></script>
		<link rel="stylesheet" href="${ctx}/assets/datatables/1.10.10/dataTables.bootstrap.min.css" />
		<!--<link rel="stylesheet" href="${ctx}/assets/datatables/1.10.10/jquery.dataTables.min.css" />-->
		
		<!--index js css-->
		<script type="text/javascript" src="${ctx}/assets/js/manage.js" ></script>
<%-- 		<script type="text/javascript" src="${ctx}/assets/js/manage_apply.js" ></script> --%>
		<link rel="stylesheet" href="${ctx}/assets/css/style.css" />
		
		<!--ie兼容性-->
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/respond.min.js"></script>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/html5shiv.min.js"></script>
	[endif]-->
		<script type="text/javascript">
			var loginUserId = '${loginUser.id}';
			var loginUserType = '${loginUser.type}';
			var loginUsername = '${loginUser.username}';
			var dept = '${loginUser.dept}';
			var length = "";
			var roleLength="";
			$(document).ready(function(){
					$.ajax({
						type : "POST",
						url : "${ctx}/smsUser/getUserType",
						data :  {}, 
						dataType : "json",
						async:true,
						success : function(data) {
							roleLength=data.length;
							$.each(data, function(i, value){
								$("#select2").append("<option value="+i+"  >"+value+"</option>");
							});
							
						}
					}); 
				$.ajax({
					type : "POST",
					url : "${ctx}/smsUser/getDept",
					data :  {}, 
					dataType : "json",
					async:false,
					success : function(data) {
						length=data.length;
						$.each(data, function(i, value){
							$("#select").append("<option >"+value+"</option>");
						});
						
					}
					
				});
			});
			function getDeptLength(){
				return length;
			}
			function getRoleLength(){
				return roleLength;
			}
		</script>
	</head>
	<body>
		<div class="wrapper">
			<!-- top nav -->
		    <jsp:include page="inc/nav_top.jsp"></jsp:include>
		    <!-- End of top nav -->
			<!-- side bar -->
        	<jsp:include page="inc/nav_left.jsp"></jsp:include>
        	<!-- End side bar -->
			<div id="showspace">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-info ">
							<div class="panel-heading">用户管理</div>
							<div class="panel-body">
								<!--search and model-->
								<div class="row">
									<div class="col-lg-3">
										<div class="btn-group">
											<c:if test="${loginUser.type==1  || loginUser.type==2}">
												<button class="btn btn-default" id="btn_add" type="button" >
													<i class="fa fa-plus"></i> 新增
												</button>
											</c:if>
											<button class="btn btn-default" id="btn_modify" type="button" >
												<i class="fa fa-pencil"></i> 修改
											</button>
											<c:if test="${loginUser.type==1|| loginUser.type==2}">
												<button class="btn btn-default" id="btn_del" type="button" data-toggle="tooltip" >
													<i class="fa fa-minus"></i> 删除
												</button>
											</c:if>
<%-- 											<c:if test="${loginUser.type==1}"> --%>
<!-- 												<button class="btn btn-default" id="btn_resetAccount" type="button" data-toggle="tooltip" > -->
<!-- 													<i class="fa fa-minus"></i> 同步短信量 -->
<!-- 												</button> -->
<%-- 											</c:if> --%>
										</div>
									</div>
									<c:if test="${loginUser.type==1}">
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 25px;">
											<span>所属部门:</span>
										</div>
										<div class="col-lg-2" >
											<div class="input-group">
	                                           	<input type="text" id="search_dept" class="form-control" placeholder="Search..." style="border-radius:4px">
	                                        </div>
										</div>
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 45px;">
											<span>企业ID:</span>
										</div>
										<div class="col-lg-2 qqid" >
											<div class="input-group">
	                                           	<input type="text" id="search_customerId" class="form-control" placeholder="Search..." style="border-radius:4px">
	                                        </div>
										</div>
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 25px;">
											<span>企业名称:</span>
										</div>
										<div class="col-lg-2 qymc" style="float: right;">
											<div class="input-group">
	                                            	<input type="text" id="search_customerName" class="form-control" placeholder="Search...">
	                                            <span class="input-group-btn">
	                                                <button class="btn btn-default" type="button" id="manageSearchBtn">
	                                                    <i class="fa fa-search"></i>
	                                                </button>
	                                            </span>
	                                        </div>
										</div>
									</c:if>
									<c:if test="${loginUser.type==2}">
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 45px;">
											<span>企业ID:</span>
										</div>
										<div class="col-lg-3 qqid" >
											<div class="input-group">
	                                           	<input type="text" id="search_customerId" class="form-control" placeholder="Search..." style="border-radius:4px">
	                                        </div>
										</div>
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 25px;">
											<span>企业名称:</span>
										</div>
										<div class="col-lg-4 qymc" style="float: right;">
											<div class="input-group">
	                                            	<input type="text" id="search_customerName" class="form-control" placeholder="Search...">
	                                            <span class="input-group-btn">
	                                                <button class="btn btn-default" type="button" id="manageSearchBtn">
	                                                    <i class="fa fa-search"></i>
	                                                </button>
	                                            </span>
	                                        </div>
										</div>
									</c:if>
								</div>
								<!-- data table -->
								<table class="table table-hover table-striped table-bordered" id="manage_table" style="width: 100%;">
									<thead>
										<tr>
											<th><input type="checkbox" id="selectAll" name="selectAll"> 全选</th>
											<th>企业ID</th>
											<th>企业名称</th>
											<th>用户名称</th>
											<th>短信总量</th>
											<th>联系人</th>
											<th>联系方式</th>
											<th>类型</th>
											<th>短信申购</th>
											<th>所属部门</th>
											<th>申购记录</th>
										</tr>
									</thead>
									<tbody id="manage_tbody">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!--model add-->
				<div class="modal fade" id="tablegl_updata" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="myModalLabel">修改用户数据</h4>
								
							</div>
							<div class="modal-body">
								<form id="manage_form" role="form" method="post" action="">
									<input id="id" name="id" type="hidden" class="form-control"/>
									<div class="form-group input-group">
										<span  class="input-group-addon">企业ID &nbsp;&nbsp;&nbsp;</span>
										<input id="customerId" type="text" name="customerId" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span  class="input-group-addon">企业名称</span>
										<input id="customerName" type="text" name="customerName" class="form-control"/>
									</div>
									
									<div class="form-group input-group">
										<span   class="input-group-addon">用户名称</span>
										<input id="username" type="text" name="username" class="form-control"/>
									</div>
									<div class="form-group input-group dxl" style="display:none;">
										<span  class="input-group-addon">短信量</span>
										<input id="msgAmount" type="text" name="msgAmount" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span  class="input-group-addon">联系人&nbsp;&nbsp;&nbsp;</span>
										<input id="contactor" type="text" name="contactor" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span  class="input-group-addon">联系方式</span>
										<input id="contactorInfo" type="text" name="contactorInfo" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span  class="input-group-addon">密码 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										<input id="password" type="password" name="password" class="form-control"/>
									</div>
									<div class="form-group input-group ssbm">
										<span  class="input-group-addon">所属部门&nbsp;&nbsp;&nbsp;&nbsp;</span>
										<select id="select" name="dept1" class="form-control" >
                            			</select>
										<input id ="dept" type="hidden" name="dept" class="form-control" value=""/>
									</div>
									<div class="form-group input-group userType">
										<span  class="input-group-addon">用户类型&nbsp;&nbsp;&nbsp;&nbsp;</span>
										 <select id="select2" name="userType1" class="form-control" >
                            			</select> 
                            			<input id="type" type="hidden" name="type" class="form-control"/>
									</div>
										<!-- <input id="type" type="hidden" name="type" class="form-control"/> -->
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" id="btn_save" class="btn btn-primary">确定保存</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							</div>
						</div>
					</div>
				</div>
				<!--model add end-->
				
				<!--model apply-->
				<div class="modal fade" id="apply_model" tabindex="-1" role="dialog" aria-labelledby="applyModalLabel" aria-hidden="true" style="width: 100%;">
					<div class="modal-dialog" style="width:900px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="applyModalLabel">购买套餐</h4>
							</div>
							<div class="modal-body">
								<table class="table table-hover table-striped table-bordered" id="packageApply_table">
									<thead>
										<tr>
											<th style="width: 8%;" id="th1"><input type="checkbox" id="checkAll" name="select_all"> 全选</th>
											<th style="width: auto;" id="th2">套餐名</th>
											<th style="width: auto%;" id="th3">金额</th>
											<th style="width: auto%;" id="th4">短信量</th>
											<th style="width: 40%;" id="th5">套餐说明</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" id="apply_save" class="btn btn-primary">确定购买</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							</div>
						</div>
					</div>
				</div>
				<!--model apply end-->
				
				<!--model consumption -->
				<div class="modal fade" id="consumption_model" tabindex="-1" role="dialog" aria-labelledby="consumptionModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:900px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="consumptionModalLabel">消费历史明细</h4>
							</div>
							<div class="modal-body" >
								<!-- data table -->
								<table class="table table-hover table-striped table-bordered" id="history_table">
									<thead>
										<tr>
											<th><input type="checkbox" id="checkAll" name="select_all">全选</th>
											<th>申购编号</th>
											<th>企业ID</th>
											<th>企业名称</th>
											<th>金额</th>
											<th>短信量</th>
											<th>申购时间</th>
											<th>审批日期</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								<!-- data table end-->
							</div>
						</div>
					</div>
				</div>
				<!--model consumption -->
			</div>
		</div>
	</body>
</html>
