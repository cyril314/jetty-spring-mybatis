function getElementByClassName(opts) {
    var searchClass = opts.searchClass || opts;
    var node = opts.node || document;
    var tag = opts.tag || '*';
    var result = [];
    
    if(document.getElementsByClassName) {
        // 浏览器支持
       var nodes = node.getElementsByClassName(searchClass);
       if(tag !== "*") {
            for(var i = 0; node = nodes[i++];) {
                if(node.tagName === tag.toUpperCase()) {
                    result.push(node);
                }
            }
       } else {
            result = nodes;
       }
    } else {
        // 使IE8以下的浏览器能够支持该属性
        var els = node.getElementsByTagName(tag);
        var elsLen = els.length;
        var i, j;
        var pattern = new RegExp("(^|\\s)" + searchClass + "(\\s|$)");
        for (i = 0, j = 0; i < elsLen; i++) {
            if(pattern.test(els[i].className)) {
                // 检测正则表达式
                result[j] = els[i];
                j++;
            }
        }
    }
    return result;
};
	
$(".full-screen").mousemove(function(event) {
	var eye = $(".eye");
	var x = (eye.offset().left) + (eye.width() / 2);
	var y = (eye.offset().top) + (eye.height() / 2);
	var rad = Math.atan2(event.pageX - x, event.pageY - y);
	var rot = (rad * (180 / Math.PI) * -1) + 180;
	eye.css({
		'-webkit-transform': 'rotate(' + rot + 'deg)',
		'-moz-transform': 'rotate(' + rot + 'deg)',
		'-ms-transform': 'rotate(' + rot + 'deg)',
		'transform': 'rotate(' + rot + 'deg)'
	});
});