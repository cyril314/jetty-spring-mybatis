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
        <jsp:param name="title" value="角色管理" />
    </jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
            <div class="row">
                <div class="control-group span9">
                    <label class="control-label">角色名称：</label>
                    <div class="controls">
                        <input type="text" name="roleName" value="${roleName}"/>
                    </div>
                </div>
                <div class="span6 offset2">
                    <button type="submit" id="btnSearch" class="button button-primary">搜索</button>
					<button type="reset" id="btnReset" class="button">重置</button>
                    <button type="button" class="button" onclick="javascript:top.topManager.reloadPage();">刷新</button>
                </div>
            </div>
        </form>
    </div>
    <div class="search-grid-container">
        <div id="grid"></div>
    </div>
</div>
</body>
<script type="text/javascript">
    BUI.use(['common/search','common/page'],function (Search) {
        var Store = BUI.Data.Store,Grid = BUI.Grid, columns = [
            {title: '序号', width: 30, fixed: true, elCls: 'x-grid-rownumber', renderer: function(value,obj,index){
                return index + 1 + store.__attrVals.pageIndex * 15;
            }},
            {title: '创建时间', width: 100, dataIndex: 'creatdate', renderer: function(value){
                return value ? new Date(value).Format() : '-';
            }},
            {title: '角色名字', width: 100, dataIndex: 'roleName', selectable: true},
            {title: '角色描述', width: 100, dataIndex: 'roleDesc', showTip: true},
            {title: '角色状态', width: 100, dataIndex: 'enabled', renderer: function (value) {
                return value ? '正常' : '禁用';
            }},
            {title: '操作',dataIndex:'enabled', width: 100, renderer:function(value,obj) {
                var staName = value?'禁用':'启用'
                ,editStr =  Search.createLink({id: 'edit' + obj.id, title: '编辑角色', text: '编辑',
                    href: '<%=basePath %>/sysrole/edit?id='+ obj.id})
                , stateStr = '<span class="grid-command btn-sta">'+staName+'</span>'
                , authStr = Search.createLink({id: 'auth' + obj.id, title: '设置权限', text: '权限',
                    href: '<%=basePath %>/sysrole/setAssign?id='+ obj.id});
                return editStr + "| " + stateStr + "| " + authStr;
            }}
        ],
        store = Search.createStore('<%=basePath %>/sysrole/list',{
            proxy: {
                method: 'POST',
                limitParam: 'pageSize',
                pageIndexParam: 'pageNum',
                pageStart: 1
            },
            root: 'list',
            totalProperty: 'count',
            pageSize: 15,
            autoSync: true
        }),
        gridCfg = Search.createGridCfg(columns,{
            forceFit: true,
            itemStatusFields : { //设置数据跟状态的对应关系
                selected : 'selected',
                disabled : 'disabled'
            },
            plugins : [Grid.Plugins.AutoFit] // 插件形式引入多选表格
        });

        let search = new Search({
            store : store,
            gridCfg : gridCfg
        }),dialog = new BUI.Grid.Plugins.DialogEditing({
            closeAction : 'destroy',
            title:'设置权限',
            width:400,
            height:300,
            loader : {
                url : '<%=basePath %>/sysrole/setAssign',
                autoLoad : false, //不自动加载
                lazyLoad : false, //不延迟加载
            },
            mask:true
        });
        grid = search.get('grid');
        grid.on('itemclick',function(ev){
            var sender = $(ev.domTarget); //点击的Dom
            if(sender.hasClass('btn-sta')){
                $.ajax({
                    method: 'POST',
                    url: '<%=basePath %>/sysrole/enabled',
                    dataType: 'json',
                    data: {id : ev.record.id},
                    success: function(data){
                        if (data.code == "200") { //成功
                            search.load();
                        }else{ //失败
                            BUI.Message.Alert('操作失败！');
                        }
                    }
                });
            }
        });
    });
</script>
</html>