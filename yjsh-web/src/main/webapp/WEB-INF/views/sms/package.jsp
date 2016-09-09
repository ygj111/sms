<%@ page contentType="text/html;charset=UTF-8" %>
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
		
		<link rel="stylesheet" href="${ctx}/assets/bootstrap/3.3.5/css/bootstrap.min.css" />
		<!--font-awsome-->
		<link rel="stylesheet" href="${ctx}/assets/fontawesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${ctx}/assets/metisMenu/2.2.0/metisMenu.min.css" />
		<link rel="stylesheet" href="${ctx}/assets/datatables/1.10.10/dataTables.bootstrap.min.css" />
		<!--<link rel="stylesheet" href="${ctx}/assets/datatables/1.10.10/jquery.dataTables.min.css" />-->
		<link rel="stylesheet" href="${ctx}/assets/css/style.css" />
		<!--jQuery-->
		<script type="text/javascript" src="${ctx}/assets/jquery/1.11.3/jquery.min.js" ></script>
		<!--bootstrap-->
		<script type="text/javascript" src="${ctx}/assets/bootstrap/3.3.5/js/bootstrap.min.js" ></script>
		<!--mentisMenu-->
		<script type="text/javascript" src="${ctx}/assets/metisMenu/2.2.0/metisMenu.min.js" ></script>
		<!--datatables-->
		<script type="text/javascript" src="${ctx}/assets/datatables/1.10.10/jquery.dataTables.min.js" ></script>
		<script type="text/javascript" src="${ctx}/assets/datatables/1.10.10/dataTables.bootstrap.min.js" ></script>
		<script type="text/javascript" src="${ctx}/assets/bootstrap/bootstrap-paginator.min.js" ></script>
		<script src="${ctx}/assets/js/json2.js"></script>
		
		<!--index js css-->
<%-- 		<script type="text/javascript" src="${ctx}/assets/js/indexjs.js" ></script> --%>
		<script type="text/javascript" src="${ctx}/assets/js/package.js"></script>
		
		<!--ie兼容性-->
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/respond.min.js"></script>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/html5shiv.min.js"></script>
	<![endif]-->
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
							<div class="panel-heading">套餐类型定义</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<div class="btn-group">
											<button class="btn btn-default" id="btn_add" type="button" data-toggle="modal">
												<i class="fa fa-plus"></i> 新增
											</button>
											<button class="btn btn-default" id="btn_update" type="button" data-toggle="modal">
												<i class="fa fa-pencil"></i> 修改
											</button>
											<button class="btn btn-default" id="btn_del" type="button" data-toggle="tooltip" >
												<i class="fa fa-minus"></i> 置为无效
											</button>
										</div>
									</div>
								
								</div>
								<!-- data table -->
								<table class="table table-hover table-striped table-bordered" id="package_table">
									<thead>
										<tr>
											<th style="width:8% ;"><input type="checkbox" id="checkAll" name="select_all"> 全选</th>
											<th style="width:auto ;">套餐名</th>
											<th style="width:auto ;">金额</th>
											<th style="width:auto ;">短信量</th>
											<th style="width:47% ;">套餐说明</th>
											<th style="width:auto ;">状态</th>
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
				<!--model add-->
				<div class="modal fade" id="modeltc_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title" id="myModalLabel">
									模态框（Modal）标题
								</h4>
								
							</div>
							<div class="modal-body">
								<form id="package_form" role="form" method="post" action="">
									<input id="id" name="id" type="hidden" class="form-control"/>
									<div class="form-group input-group">
										<span for="" class="input-group-addon">套餐名&nbsp;&nbsp;&nbsp;</span>
										<input id="packageName" name="packageName" type="text" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span for="" class="input-group-addon">短信量&nbsp;&nbsp;&nbsp;</span>
										<input id="msgAmount" name="msgAmount" type="text" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span for="" class="input-group-addon">金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										<input id="price" name="price" type="text" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span for="" class="input-group-addon">套餐说明</span>
										<input id="packageDes" name="packageDes" type="text" class="form-control"/>
									</div>
									<div class="form-group input-group">
										<span for="" class="input-group-addon">状态</span>
										<select id="status" name="status" class="form-control">
										  <option value ="1">有效</option>
										  <option value ="0">无效</option>
										</select>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button id="btn_save" type="button" class="btn btn-primary">保存</button>
								<button id="btn_cancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							</div>
						</div>
					</div>
				</div>
				<!--model add end-->
			
			
			
			
			
			</div>
		</div>
	</body>
</html>
