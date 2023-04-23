<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../header.jsp">
        <jsp:param name="title" value="用户管理" />
    </jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
            <div class="row">
                <div class="control-group span9">
                    <label class="control-label">用户名称：</label>
                    <div class="controls">
                        <input type="text" name="username" value="${username}"/>
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
        <script type="text/javascript">
            BUI.use(['common/search','common/page'],function (Search) {
                var Store = BUI.Data.Store,Grid = BUI.Grid, columns = [
                        {title: '序号', width: 30, fixed: true, elCls: 'x-grid-rownumber', renderer: function(value,obj,index){
                            return index + 1 + store.__attrVals.pageIndex * 15;
                        }},
                        {title: '创建时间', width: 100, dataIndex: 'creatdate', renderer: function(value){
                            return value ? new Date(value).Format() : '-';
                        }},
                        {title: '登陆用户名', width: 100, dataIndex: 'username', selectable: true},
                        {title: '描述', width: 100, dataIndex: 'desc', showTip: true},
                        {title: '状态', width: 100, dataIndex: 'enabled', renderer: function (value) {
                            return value ? '正常' : '禁用';
                        }},
                        {title: '操作',dataIndex:'', width: 100, renderer:function(value,obj) {
                            var editStr =  Search.createLink({id : 'edit' + obj.id, title : '编辑用户', text : '编辑',
                                href : '<%=basePath %>/sysuser/edit?id='+ obj.id
                            }),
                            delStr = '<span class="grid-command btn-del" title="删除用户">删除</span>';
                            return editStr + "| " + delStr;
                        }}
                    ],
                    store = new Store({
                        url: '<%=basePath %>/sysuser/list',
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
                    gridCfg = Search.createGridCfg(columns,{
                        forceFit: true,
                        tbar: {
                            items: [
                                {text: '<i class="icon-plus"></i>新建', btnCls: 'button button-small', handler: function () {
                                    top.topManager.openPage({ id : 'user', href : '<%=basePath %>/sysuser/edit', title : '添加用户'});
                                }}
                            ]
                        },
                        plugins : [Grid.Plugins.AutoFit] // 插件形式引入多选表格
                    });

                var search = new Search({
                        store : store,
                        gridCfg : gridCfg
                    }),
                grid = search.get('grid');
                //监听事件
                grid.on('cellclick',function(ev){
                    var sender = $(ev.domTarget); //点击的Dom
                    if(sender.hasClass('btn-del')){
                        $.ajax({
                            method: 'POST',
                            url: '<%=basePath %>/sysuser/delete',
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
    </div>
</div>
</body>
</html>