<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>Left Nav</title>
</head>
    <body>
    	<div class="sidebar " role="navigation">
				<div class="navbar-default">
					<ul class="nav">
						<li>
							<a href="${ctx}/smsUser/toUserManage" ><i class="fa fa-fw fa-cubes"></i> &nbsp;用户管理</a>
						</li>
						<li>
							<a href="${ctx}/smsApplyRecord/toSmsApplyRecord" ><i class="fa fa-fw fa-shopping-cart"></i> &nbsp;申购历史</a>
						</li>
<%-- 						<c:if test="${loginUser.type==1}"> --%>
							<li>
								<a href="${ctx}/smsConsumption/toSmsCount" ><i class="fa fa-fw fa-tasks"></i> &nbsp;短信统计</a>
							</li>
<%-- 						</c:if> --%>
						<li>
							<a href="${ctx}/smsConsumption/toSmsConsumption" ><i class="fa fa-fw fa-list-alt"></i> &nbsp;消费记录</a>
						</li>
						<c:if test="${loginUser.type==0}">
						<li>
							<a href="${ctx}/smsConsumption/toSendMsg" ><i class="fa fa-fw fa-envelope"></i> &nbsp;发送短信</a>
						</li>
						</c:if>
						<c:if test="${loginUser.type==1}">
							<li>
								<a href="${ctx}/smsPackage/toSmsPackage" ><i class="fa fa-fw fa-gear"></i> &nbsp;套餐类型定义</a>
							</li>
							<li>
								<a href="${ctx}/smsConsumption/toSmsCountByDept" ><i class="fa fa-fw fa-envelope"></i> &nbsp;部门短信统计</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
 	</body>
</html>