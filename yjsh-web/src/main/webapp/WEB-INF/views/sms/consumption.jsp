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
		<script type="text/javascript" src="${ctx}/assets/js/consumption.js" ></script>
		<link rel="stylesheet" href="${ctx}/assets/css/style.css" />
		
		<script type="text/javascript" src="${ctx}/assets/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js" ></script>
		<script type="text/javascript" src="${ctx}/assets/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.fr.js" ></script>
		<link rel="stylesheet" href="${ctx}/assets/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" />
		
		<!--ie兼容性-->
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/respond.min.js"></script>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/html5shiv.min.js"></script>
	<![endif]-->
		<script type="text/javascript">
		var loginUserId = '${loginUser.id}';
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
							<div class="panel-heading">消费记录明细</div>
							<div class="panel-body">
								<!--search and page-->
								<c:if test="${loginUser.type==1}">
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; padding-left: 10px;">
											<span>时间:</span>
										</div>
										<div class="col-lg-1">
											<input id="search_startTime" type="text" class="form-control"
												placeholder="请输入开始时间" style="border-radius: 4px;width:130px;margin-left:-50px;" />
												    <span class="add-on"><i class="icon-remove"></i></span>
										</div>
										<div class="col-lg-1">
											<input id="search_endTime" type="text" class="form-control"
												placeholder="请输入结束时间" style="border-radius: 4px;width:130px" />
										</div>
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px; margin-left: 45px;">
											<span>企业ID:</span>
										</div>
										<div class="col-lg-2" >
											<div class="input-group">
	                                           	<input type="text" id="search_customerId" class="form-control" placeholder="Search..." style="border-radius:4px;margin-left:-30px;">
	                                        </div>
										</div>
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px;margin-left:-35px;">
											<span>企业名称:</span>
										</div>
										<div class="col-lg-2">
											<div class="input-group">
	                                            	<input type="text" id="search_customerName" class="form-control" placeholder="Search..." style="border-radius:4px;margin-left:-10px;">
	                                        </div>
										</div>
										<div class="col-lg-1" style="padding-right: 0px; padding-top: 10px; font-size: 15px;margin-left:-10px; ">
											<span>发送状态:</span>
										</div>
										<div class="col-lg-2" style="float: right;">
											<div class="input-group">
	                                            	<input type="text" id="search_sendStatus" class="form-control" placeholder="Search...">
	                                            <span class="input-group-btn">
	                                                <button class="btn btn-default" type="button" id="consuSearchBtn">
	                                                    <i class="fa fa-search"></i>
	                                                </button>
	                                            </span>
	                                        </div>
										</div>
									</c:if>
								<!--search and page end-->
								<!-- data table -->
								<table class="table table-hover table-striped table-bordered" id="record_table">
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
				
				
				
			</div>
		</div>
	</body>
</html>
