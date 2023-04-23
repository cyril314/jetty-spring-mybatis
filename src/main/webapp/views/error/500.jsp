<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<meta charset="UTF-8">
<title>500 error!</title>
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="static/css/error.css">
</head>
<body>
	<div class="full-screen">
      <div class='container'>
        <span class="error-num">5</span>
        <div class='eye'></div>
        <div class='eye'></div>
        <p class="sub-text">It's broken, but it's not your fault.</p>
        <a href="javascript:void();" onclick="javascript:window.history.go(-1);">Go back</a>
      </div>
    </div>
</body>
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/error.js"></script>
</html>
