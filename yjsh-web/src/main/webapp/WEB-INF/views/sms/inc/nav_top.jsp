<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>Top Nav</title>
</head>
    <body>
    		<nav class="navbar">
				<div class="navbar-header">
					<!--<a class="navbar-brand" href="../index.html">三和软件短信平台</a>-->
					<a class="navbar-brand" href="${ctx}/smsUser/toUserManage">三和软件短信平台</a>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<!--消息提示-->
					<li class="dropdown">
					</li>
					<!--管理员-->
					<li class="dropdown ">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<i class="fa fa-user fa-fw"></i>
							<i class="fa fa-caret-down"></i>
						</a>
						<ul class="dropdown-menu ">
							<!--<li><a href="#"><i class="fa fa-user fa-fw"></i> 用户管理</a></li>-->
<!-- 							<li><a href="#"><i class="fa fa-gear fa-fw"></i> 权限设置</a></li> -->
							<li class="divider"></li>
							<li><a href="${ctx}/login"><i class="fa fa-sign-out fa-fw"></i> 退出</a></li>
							<!--<li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> 退出</a></li>-->
						</ul>
					</li>
				</ul>
			</nav>
 	</body>
</html>