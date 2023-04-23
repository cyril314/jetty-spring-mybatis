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
        <jsp:param name="title" value="设置权限" />
    </jsp:include>
</head>
<body>
<div class="container">
	<div class="row">
		<form id="J_Form" class="form-horizontal" action="<%=basePath %>/sysrole/saveAssign" method="post">
			<input type="hidden" name="roleId" value="${roleId}"/>
			<input type="hidden" name="menus" value="${menuIds}" id="menus"/>
			<div class="control-group">
				<div id="menuTree"></div>
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
BUI.use(['bui/tree'],function (Tree) {
    let data =${treeNodes},tree = new Tree.TreeList({
        render: '#menuTree',
        checkType: 'all',
        showLine: true,
        nodes : data
    });
    tree.render();
	var form = new BUI.Form.HForm({
	    srcNode : '#J_Form',
	    submitType : 'ajax',

	    callback : function(data){
	        if (data.code == "0") {
	            BUI.Message.Alert('保存成功','success');
	            top.topManager.reloadPage('4');
	            top.topManager.closePage();
	        }else{
	            BUI.Message.Alert('保存失败','error');
	        }
	    }
	}).render();
	form.on('beforesubmit',function(){
        var checkedNodes = tree.getCheckedNodes();
        var str = '';
        BUI.each(checkedNodes,function(node){
            str += node.id + ',';
        });
	    console.log(str);
        $("#menus").val(str);
	});
});
</script>
</script>
</html>
