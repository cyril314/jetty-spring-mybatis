<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<meta charset="UTF-8">
<title>首页</title>
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="<%=basePath%>/static/favicon.ico">
<link rel="stylesheet" href="<%=basePath%>/static/css/index.css">
</head>
<body>
	<div id="loader"></div>
	<!-- Parallax Container -->
	<div id="container" class="parallax-container snow">
		<ul id="christmas_scene" class="christmas-scene">
			<li class="layer" data-depth="0.80"><div class="layer-5 layer-photo photo-zoom"></div></li>
			<li class="layer" data-depth="0.60"><div class="layer-4 layer-photo photo-zoom"></div></li>
			<li class="layer" data-depth="0.40"><div class="layer-3 layer-photo photo-zoom"></div></li>
			<li class="layer" data-depth="0.20"><div class="layer-2 layer-photo photo-zoom"></div></li>
			<li class="layer" data-depth="0.00"><div class="layer-1 layer-photo"></div></li>
		</ul>
		<!-- Countdown Container -->
		<div id="countdown_container"></div>
		<!-- Merry Christmas Text -> You can replace with anything you like! -->
		<div class="merry-christmas-text">Merry Christmas</div>
		<!-- Contact Pole Image -> From here the E-mail modal is triggered -->
		<div id="index_pole">
			<img src="<%=basePath%>/static/img/index-pole.png" class="img-responsive" alt="mail-pole" data-toggle="modal"
			     data-target="#contact_modal"
			>
		</div>
		<!-- Christmas Tree -->
		<img src="<%=basePath%>/static/img/index-christmas-tree.png" alt="christmas-tree" id="christmas_tree">
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/jquery.parallax.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/snow.js"></script>
<script type="text/javascript">
	//Parallax Init
    $('#christmas_scene').parallax({
        scalarX: 5,
        scalarY: 15,
        invertY: false
    });
    $(window).on('load', function () {
        if (window.top != null && window.top.location != window.location) {
            window.top.location = window.location;
        }
        Main.init();
        $('#loader').fadeOut();
    });
    $(window).on('resize', function () {
        Main.init();
    });
    var Main = (function ($) {
        return {
            init: function () {
                Main.setElementsHeight();
                Main.setParallaxHeight();
            },
            setElementsHeight: function () {
                var height = $(window).height();
                var width = height / 2.5;
                if (height <= 400) {
                    width = height / 2;
                } else if (height <= 500) {
                    width = height / 3.5;
                } else if (height <= 700) {
                    width = height / 3;
                } else if (height <= 800) {
                    width = height / 2.8;
                }
                $('#christmas_tree').css({
                    'width': width,
                    'margin-left': -(width / 2)
                });
                $('#index_pole').css('margin-left', -(width / 1.2));
                $('#index_pole img').css('width', width / 3);
            },
            setParallaxHeight: function () {
                var height = $(window).height();
                $('#christmas_scene .layer-photo').css('height', height);
            }
        }
    })($);
</script>
</html>