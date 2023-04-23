<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../header.jsp">
        <jsp:param name="title" value="编辑角色" />
    </jsp:include>
</head>
<body>
<div class="container">
	<div class="row">
		<form id="J_Form" class="form-horizontal" action="<%=basePath %>/sysrole/save" method="post">
			<input type="hidden" name="id" value="${srb.id}" />
			<c:set var="disabled" value="${srb.id==null?'':'disabled=disabled' }" />
			<div class="control-group">
				<label class="control-label"><s>*</s>角色名字：</label>
				<div class="controls">
					<input type="text" name="roleName" value="${srb.roleName}" class="input-large" ${disabled}/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">角色说明：</label>
				<div class="controls  control-row-auto">
					<textarea class="control-row4 input-large" id="roleDesc" name="roleDesc" ${disabled}>${srb.roleDesc}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					<select name="enabled" class="input-normal">
		                <option value="0" ${srb.enabled?'':'selected="selected"'}>禁用</option>
		                <option value="1" ${srb.enabled?'selected="selected"':''}>正常</option>
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
<script type="text/javascript">
new BUI.Form.HForm({
    srcNode : '#J_Form',
    submitType : 'ajax',
    callback : function(data){
        if (data.code == "200") {
            BUI.Message.Alert('保存成功','hide','success');
            top.topManager.reloadPage('4');
            top.topManager.closePage();
        }else{
            BUI.Message.Alert('保存失败','hide','error');
        }
    }
}).render();
</script>
</html>
