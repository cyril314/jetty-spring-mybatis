<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../header.jsp">
		<jsp:param name="title" value="后台管理系统" />
	</jsp:include>
    <link href="<%=basePath %>/static/bui/css/main-min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="header">
    <img alt="logo" src="<%=basePath %>/static/favicon.ico" style="float:left;margin-left: 12px;height: 31px;">
    <div class="dl-title">
        <a href="<%=basePath %>" title="跳转首页" target="_blank">
            <span class="lp-title-port">jetty</span>
            <span class="dl-title-text">管理系统</span>
        </a>
    </div>
    <div class="dl-log">
    	欢迎您，<span class="dl-log-user">${users.username}</span>
    	<a href="<%=basePath %>/logout" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
</div>
<div class="content">
    <div class="dl-main-nav">
        <div class="dl-inform">
        	<div class="dl-inform-title">
        		<s class="dl-inform-icon dl-up"></s>
        	</div>
        </div>
        <ul id="J_Nav"  class="nav-list ks-clear">
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统管理</div></li>
			<li class="nav-item dl-selected"><div class="nav-item-inner nav-order">业务管理</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten"></ul>
</div>
<script type="text/javascript" src="<%=basePath %>/static/bui/js/config-min.js"></script>
<script>
    BUI.use('common/main', function () {
        var config = [
            {
                id: '1', homePage: '2', menu: [{
                    text: '系统管理',
                    items: [
                        {id: '2', text: '首页', href: '<%=basePath %>/admin/index', closeable: false, visible: false},
                        {id: '3', text: '用户管理', href: '<%=basePath %>/sysuser/list'},
                        {id: '4', text: '角色管理', href: '<%=basePath %>/sysrole/list'},
                        {id: '5', text: '资源管理', href: '<%=basePath %>/sysres/list'}
                    ]
                }]
            },
            {
                id: '11', homePage: '15', menu: [{
                    text: '业务管理',
                    items: [
                        {id: '15', text: '管理', href: '<%=basePath %>/admin/index'}
                    ]
                }]
            }
        ];
        new PageUtil.MainPage({
            modulesConfig: config
        });
    });
</script>
</body>
</html>