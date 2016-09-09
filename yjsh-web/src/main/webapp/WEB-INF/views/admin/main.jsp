<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="${ctx}/assets/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="${ctx}/assets/fontawesome/css/font-awesome.min.css">
		<link href="${ctx}/assets/metisMenu/2.2.0/metisMenu.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="${ctx}/assets/css/sidebar.css">
    	<script src="${ctx}/assets/jquery/1.11.3/jquery.min.js"></script>
    	<script src="${ctx}/assets/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    	<script src="${ctx}/assets/metisMenu/2.2.0/metisMenu.min.js"></script>
    <title></title>
</head>
<body>
    <!-- top nav -->
    <jsp:include page="inc/nav_top.jsp"></jsp:include>
    <!-- End of top nav -->
    <!-- Main Container -->
    <div id="main-container">
        <!-- side bar -->
        <jsp:include page="inc/nav_left.jsp"></jsp:include>
        <!-- End side bar -->
        <!-- content -->
        <div id="main_content" class="main-content">
            main content
        </div>
        <!-- End content -->
    </div>
    <!-- End Main Container -->
    <script language="javascript">
        $("#side-menu").metisMenu();
    </script>
</body>
</html>