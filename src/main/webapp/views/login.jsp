<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>用户登录</title>
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="<%=basePath %>/static/favicon.ico">
<link rel="stylesheet" href="<%=basePath %>/static/css/login.css">
<script type="text/javascript">
	if (window.top != null && window.top.location != window.location) {
        window.top.location = window.location;
    }
</script>
</head>
<body>
	<div class="login">
		<h2>欢迎登录</h2>
		<div class="login-top">
			<h1>LOGIN FORM</h1>
			<form id="Login" method="post" action="<%=basePath%>/checkLogin">
				<div>
					<input type="text" id="username" name="username" placeholder="请输入用户名" />
				</div>
				<div>
					<input type="password" id="password" name="password" placeholder="请输入密码" />
				</div>
				<div class="forgot">
					<a href="#">forgot Password</a>
					<button type="submit">登录</button>
					<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
						<span style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</span>
					</c:if>
				</div>
			</form>
		</div>
		<div class="login-bottom">
			<h3>
				New User &nbsp;<a href="#">Register</a>&nbsp Here
			</h3>
		</div>
	</div>
	<div class="copyright">
		<p>Copyright &copy; 2015.Company name All rights reserved.</p>
	</div>
</body>
<script type="text/javascript" src="<%=basePath %>/static/js/jquery.min.js"></script>
</html>