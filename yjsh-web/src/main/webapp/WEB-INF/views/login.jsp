<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
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
		
		<!--ie兼容性-->
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/respond.min.js"></script>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/html5shiv.min.js"></script>
	<![endif]-->
		<style>
			.login-panel{margin-top: 110px;}
			.login-title{margin-bottom: 0px; margin-top: 0px;}

		</style>
	</head>
	<body >
		<div class="container">
			<div class="row">
				<div class="col-lg-4 col-lg-offset-4">
					<div class="login-panel panel panel-default">
						<div class="panel-heading">
							<h3 class="login-title">登录</h3>
						</div>
						<div class="panel-body">
							<form role="form">
								<fieldset>
									<div class="form-group input-group">
										<span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
										<input class="form-control" placeholder="ç¨æ·å" name="username" type="email" autofocus/>
									</div>
									<div class="form-group input-group">
										<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
										<input class="form-control" placeholder="å¯ç " name="Password" type="password"/>
									</div>
									<div class="checkbox">
										<label>
											<input name="remenber" type="checkbox" value="remember me"/>记住我
										</label>
									</div>
									<a href="index.html" class="btn btn-success btn-block">登录</a>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
