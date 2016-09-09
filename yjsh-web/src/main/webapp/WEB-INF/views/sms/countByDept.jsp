<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="${ctx}/assets/js/json2.js"></script>
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
		<script type="text/javascript" src="${ctx}/assets/js/countByDept.js" ></script>
		<link rel="stylesheet" href="${ctx}/assets/css/style.css" />
		
		<!--ie兼容性-->
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/respond.min.js"></script>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/html5shiv.min.js"></script>
	<![endif]-->
		<script type="text/javascript">
			var loginUserId = '${loginUser.id}';
			var loginUserType = '${loginUser.type}';
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
							<div class="panel-heading">部门短信统计</div>
							<div class="panel-body">
								<!--search -->
								<c:if test="${loginUser.type==1}">
										<div class="col-lg-5"></div>
										<div class="col-lg-4" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 305px;">
											<span>部门：</span>
										</div>
										<div class="col-lg-3" style="float: right;">
											<div class="input-group">
	                                            	<input type="text" id="search_customerName" class="form-control" placeholder="Search...">
	                                            <span class="input-group-btn">
	                                                <button class="btn btn-default" type="button" id="countSearchBtn">
	                                                    <i class="fa fa-search"></i>
	                                                </button>
	                                            </span>
	                                        </div>
										</div>
									</c:if>
								<!--search end-->
								<!-- datatables -->
								<table class="table table-hover table-striped table-bordered" id="count_tableByDept">
									<thead>
										<tr>
											<th>部门</th>
											<th>短信总量</th>
											<th>已发送量</th>
											<th>短信剩余</th>
											<th>消费记录</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								<!-- datatables end-->
							</div>
						</div>
					</div>
				</div>
				<!--model count -->
				<div class="modal fade" id="countHistory_model" tabindex="-1" role="dialog" aria-labelledby="countModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:900px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="countModalLabel">消费历史明细</h4>
							</div>
							<div class="modal-body" >
								<!-- data table -->
								<table class="table table-hover table-striped table-bordered" id="countHistory_table">
									<thead>
										<tr>
											<th>企业ID</th>
											<th>企业名称</th>
											<th>手机号码</th>
											<th>发送时间</th>
											<th style="width:35%;">短信内容</th>
											<th>发送状态</th>
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
				<!--model count end -->
				
			</div>
		</div>
	</body>
</html>
