<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../header.jsp">
        <jsp:param name="title" value="资源管理" />
    </jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
            <div class="row">
                <div class="control-group span9">
                    <label class="control-label">资源名称：</label>
                    <div class="controls">
                        <input type="text" name="name" value="${name}" />
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
BUI.use(['common/search','common/page'], function (Search) {
    var columns = [
        {title: '序号', width: 40, fixed: true, elCls: 'x-grid-rownumber', renderer: function (value, obj, index) {
            return index + 1 + store.__attrVals.pageIndex * 15;
        }},
        {title: '创建时间', width: 100, dataIndex: 'creatdate', renderer: function (value) {
            return value ? new Date(value).Format() : '-';
        }},
        {title: '图标', width: 30, dataIndex: 'icon', elCls: 'x-grid-rownumber', renderer: function (value) {
            return '<span class="' + value + '"></span>';
        }},
        {title: '资源名称', width: 100, dataIndex: 'name', selectable: true},
        {title: '资源类型', width: 100, dataIndex: 'type', renderer: function (value) {
            return value=='M' ? '目录' : value=='C' ? '菜单' : '按钮';
        }},
        {title: '资源优先级', width: 100, dataIndex: 'priority', selectable: true},
        {title: '资源链接', width: 100, dataIndex: 'resurl', selectable: true},
        {title: '资源描述', width: 100, dataIndex: 'description', showTip: true},
        {title: '资源状态', width: 100, dataIndex: 'enabled', renderer: function (value) {
            return value ? '正常' : '禁用';
        }},
        {title: '操作',dataIndex:'enabled', width: 100, renderer:function(value,obj) {
            var staName = value?'禁用':'启用',
            editStr =  Search.createLink({
                id : 'edit' + obj.id,
                title : '编辑资源',
                text : '编辑',
                href : '<%=basePath %>/sysres/edit?id='+ obj.id
            }), stateStr = '<span class="grid-command btn-sta">'+staName+'</span>',
            delStr = '<span class="grid-command btn-del" title="删除资源">删除</span>';
            return editStr + "| " + stateStr + "| " + delStr;
        }}
    ],
    store = Search.createStore('<%=basePath %>/sysres/list',{
        proxy: {
            method: 'POST',
            limitParam: 'pageSize',
            pageIndexParam: 'pageNum',
            pageStart: 1
        },
        root: 'list',
        totalProperty: 'count',
        pageSize: 15
    }),
    gridCfg = Search.createGridCfg(columns, {
        forceFit: true,
        tbar: {
            items: [
                {text: '<i class="icon-plus"></i>新建', btnCls: 'button button-small', handler: function () {
                    top.topManager.openPage({ id : 'res', href : '<%=basePath %>/sysres/edit', title : '添加资源'});
                }}
            ]
        },
        plugins: [BUI.Grid.Plugins.AutoFit] // 插件形式引入多选表格
    });

    var search = new Search({
        store: store,
        gridCfg: gridCfg
    }),
    grid = search.get('grid');
    //监听事件
    grid.on('cellclick',function(ev){
        var sender = $(ev.domTarget); //点击的Dom
        if(sender.hasClass('btn-del')){
            $.ajax({
                method: 'POST',
                url: '<%=basePath %>/sysres/delete',
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
        }else if(sender.hasClass('btn-sta')){
            $.ajax({
                method: 'POST',
                url: '<%=basePath %>/sysres/enabled',
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