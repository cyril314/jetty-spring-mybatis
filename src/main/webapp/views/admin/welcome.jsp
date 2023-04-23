<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="../header.jsp">
		<jsp:param name="title" value="欢迎页" />
	</jsp:include>
</head>
<body>
<div class="container">
	<div class="detail-page">
		<h2>欢迎使用admin 后台！</h2>
		<div class="detail-section"> 
        	<table cellspacing="0" class="table table-head-bordered table-bordered">
	            <thead>
		            <tr>
		              <th colspan="2">服务器信息</th>
		            </tr>
	            </thead>
				<tbody>
					<tr>
			            <th width="30%">服务器计算机名</th>
			            <td><span id="lbServerName">${os.computerName}</span></td>
			        </tr>
			        <tr>
			            <td>服务器MAC</td>
			            <td>${os.mac}</td>
			        </tr>
			        <tr>
			            <td>服务器IP地址</td>
			            <td>${os.ip}</td>
			        </tr>
			        <tr>
			            <td>服务器端口</td>
			            <td>${port}</td>
			        </tr>
			        <tr>
			            <td>项目所在文件夹</td>
			            <td>${os.itemPath}</td>
			        </tr>
			        <tr>
			            <td>服务器操作系统</td>
			            <td>${os.osname}</td>
			        </tr>
			        <tr>
			            <td>用户工作目录</td>
			            <td>${os.userdir}</td>
			        </tr>
			        <tr>
			            <td>当前系统用户名</td>
			            <td>${os.sysUserName}</td>
			        </tr>
			        <tr>
			            <td>服务器当前时间</td>
			            <td id="clock">${os.sysTime}</td>
			        </tr>
			        <tr>
			            <td>JAVA版本</td>
			            <td>${os.java}</td>
			        </tr>
			        <tr>
			            <td>虚拟机内存总量</td>
			            <td>${os.vmRamTotal}</td>
			        </tr>
			        <tr>
			            <td>当前程序占用内存</td>
			            <td>${os.useRamTotal}</td>
			        </tr>
				</tbody>
			</table>
		</div> 
	</div>
</div>
</body>
<script type="text/javascript" src="<%=basePath %>/static/js/common.js"></script>
<script>
$(function () {
    setInterval(function () {
        var time = new Date().Format("yyyy-MM-dd HH:mm:ss");
        document.getElementById("clock").innerHTML = time;
    }, 1000);
});
</script>
</html>