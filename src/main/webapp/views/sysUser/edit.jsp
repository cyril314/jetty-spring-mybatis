<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../header.jsp">
        <jsp:param name="title" value="编辑用户" />
    </jsp:include>
</head>
<body>
<div class="container">
	<div class="row">
		<form id="J_Form" class="form-horizontal" action="<%=basePath %>/sysuser/save" method="post">
			<input type="hidden" name="id" value="${sub.id}" />
			<div class="control-group">
				<label class="control-label"><s>*</s>登陆名称：</label>
				<div class="controls">
					<input type="text" name="username" value="${sub.username}" class="input-large" data-rules="{required:true}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><s>*</s>用户名称：</label>
				<div class="controls">
					<input type="text" name="name" value="${sub.name}" class="input-large" data-rules="{required:true}">
				</div>
			</div>
			<c:if test="${sub.id==null}">
			<div class="control-group">
				<label class="control-label"><s>*</s>用户密码：</label>
				<div class="controls">
					<input type="password" name="password" class="input-large" data-rules="{required:true}">
				</div>
			</div>
			</c:if>
			<div class="control-group">
				<label class="control-label">描述：</label>
				<div class="controls  control-row-auto">
					<textarea class="control-row4 input-large" id="desc" name="desc">${sub.desc}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					<select name="enabled" class="input-normal">
		                <option value="0" ${sub.enabled?'':'selected="selected"'}>禁用</option>
		                <option value="1" ${sub.enabled?'selected="selected"':''}>正常</option>
					</select>
				</div>
			</div>
			<div class="row actions-bar">
				<div class="form-actions span13 offset3">
					<button type="submit" class="button button-primary">保存</button>
					<button type="button" class="button" onclick="javascript:top.topManager.closePage();">返回列表</button>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
<script>
new BUI.Form.HForm({
    srcNode : '#J_Form',
    submitType : 'ajax',
    callback : function(data){
        if (data.code == "200") {
            BUI.Message.Alert('保存成功','success');
            top.topManager.reloadPage('3');
            top.topManager.closePage();
        }else{
            BUI.Message.Alert('保存失败','hide','error');
        }
    }
}).render();
</script>
</html>
