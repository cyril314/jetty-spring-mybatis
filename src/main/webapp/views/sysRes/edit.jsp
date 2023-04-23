<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../header.jsp">
        <jsp:param name="title" value="编辑资源" />
    </jsp:include>
	<link href="${basePath}/static/bui/css/iconpicker.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
	<div class="row">
		<form id="J_Form" class="form-horizontal" action="<%=basePath %>/sysres/save" method="post">
			<input type="hidden" name="id" value="${srb.id}" />
			<div class="control-group">
				<label class="control-label"><s>*</s>资源名称：</label>
				<div class="controls">
					<input name="name" type="text" value="${srb.name}" class="input-large" data-rules="{required:true}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><s>*</s>资源类型：</label>
				<div class="controls">
					<select name="type" class="input-normal">
		                <option value="M" ${srb.type=='M'?'selected="selected"':''}>目录</option>
		                <option value="C" ${srb.type=='C'?'selected="selected"':''}}>菜单</option>
		                <option value="A" ${srb.type=='A'?'selected="selected"':''}}>按钮</option>
					</select>

				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><s>*</s>资源优先权：</label>
				<div class="controls">
					<input type="text" name="priority" value="${srb.priority}" class="input-large" data-rules="{required:true}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><s>*</s>资源链接：</label>
				<div class="controls">
					<input type="text" name="resurl" value="${srb.resurl}" class="input-large" data-rules="{required:true}">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">图标：</label>
				<div class="controls">
					<input type="text" id="myicon"  name="icon" value="${srb.icon}" class="input-large">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">资源描述：</label>
				<div class="controls  control-row-auto">
					<textarea class="control-row4 input-large" id="description" name="description">${srb.description}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">资源状态：</label>
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
<script type="text/javascript" src="${basePath}/static/bui/js/extensions/iconpicker.js"></script>
<script>
new BUI.Form.HForm({
    srcNode : '#J_Form',
    submitType : 'ajax',
    callback : function(data){
        if (data.code == "200") {
            BUI.Message.Alert('保存成功','hide','success');
            top.topManager.reloadPage('5');
            top.topManager.closePage();
        }else{
            BUI.Message.Alert('保存失败','hide','error');
        }
    }
}).render();
$(function () {
	$('#myicon').iconpicker();
	$('#myicon').on('iconpickerSelected', function (event) {
	    console.log(event);
	});
});
</script>
</html>
