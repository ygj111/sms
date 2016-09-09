<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
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
		<script type="text/javascript" src="${ctx}/assets/jquery.validate.min.js" ></script>
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
		<link rel="stylesheet" href="${ctx}/assets/css/style.css" />
		
		<!--ie兼容性-->
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/respond.min.js"></script>
  		<script type='text/javascript' src="${ctx}/assets/bootstrap/html5shiv.min.js"></script>
	<![endif]-->
	</head>
	<style>
			.login-panel{margin-top: 110px;}
			.login-title{margin-bottom: 0px; margin-top: 0px;}

	</style>
    <body>
    	<div class="container">
			<div class="row">
				<div class="col-lg-4 col-lg-offset-4">
					<div class="login-panel panel panel-default">
						<div class="panel-heading">
							<h3 class="login-title">请登录</h3>
						</div>
						<div class="panel-body">
							<form id="loginForm" action="${ctx}/login" method="post">
								<fieldset>
									<div class="form-group">
										<i class="fa fa-user fa-fw"></i> <input class="form-control" placeholder=" 用户名" id="username" name="username" type="text" onfocus="$('#loginTips').remove();"/>
									</div>
									<div class="form-group">
										<i class="fa fa-key fa-fw"></i> <input class="form-control" placeholder="密码" name="password" id="password" type="password" onfocus="$('#loginTips').remove();"/>
									</div>
									<div id="loginTips"><font color="red">${loginTips}</font></div>
<!-- 									<div class="checkbox"> -->
<!-- 										<label> -->
<!-- 											<input name="remenber" type="checkbox" value="remember me"/>记住我 -->
<!-- 										</label> -->
<!-- 									</div> -->
									<button id="loginBtn" type="submit" class="btn btn-success btn-block">登录</button>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
    	
    	<script type="text/javascript">
		$(document).ready(function () {
			$("#loginForm").validate( {
				rules: {
					username: "required",
					password: "required"
				},
				messages: {
					username: "请输入用户名",
					password: "请输入密码"
				},
				errorElement: "em",
				errorPlacement: function ( error, element ) {
					// Add the `help-block` class to the error element
					error.addClass( "help-block" );

					if ( element.prop( "type" ) === "checkbox" ) {
						error.insertAfter( element.parent( "label" ) );
					} else {
						error.insertAfter( element );
					}
				},
				highlight: function ( element, errorClass, validClass ) {
					$( element ).parents().addClass( "has-error" ).removeClass( "has-success" );
				},
				unhighlight: function (element, errorClass, validClass) {
					$( element ).parents().addClass( "has-success" ).removeClass( "has-error" );
				}
			} );
		});
		</script>
 	</body>
</html>