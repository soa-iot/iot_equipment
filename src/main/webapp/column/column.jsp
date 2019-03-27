<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<%-- 栏目导航 --%>
<%-- 设置绝对路径 --%>
<c:set value="${pageContext.request.scheme}${'://'}${pageContext.request.serverName}${':'}${pageContext.request.serverPort}${pageContext.request.contextPath}${'/'}" var="basePath"></c:set>
<base href="${basePath}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="欧增奇">
<link rel="icon" href="favicon.ico" />
<link href="column/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="column/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="column/themes/icon.css" />
<script type="text/javascript" src="column/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="column/js/jquery.easyui.min-1.2.0.js"></script>
<script type="text/javascript" src='column/js/outlook.js'></script>
<script type="text/javascript" src='column/js/power.js'></script>
<title>栏目导航</title>
<script type="text/javascript">
	var _url = "index.action?type=3&column=${columnType}#";
	//总控制
	$(document).ready(function()
	{
		getPowerUrl("${columnType}");
	});
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden">
	<noscript>
		<div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<ul id="css3menu" style="padding: 0px; margin: 0px; list-type: none; float: left; margin-left: 40px;">
		<li>
			<a class="active" name="basic" href="javascript:;"></a>
		</li>
	</ul>
	<div region="west" hide="true" split="true" style="width: 180px;" id="west">
		<div id='wnav' class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->
		</div>
	</div>
	<div id="mainPanle" region="center" style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"></div>
	</div>
</body>
</html>