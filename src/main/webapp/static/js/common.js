/**
 * Created with JetBrains PhpStorm.
 */
function U() {
    var url = arguments[0] || [];
    var param = arguments[1] || {};
    var url_arr = url.split('/');

    if (!$.isArray(url_arr) || url_arr.length < 2 || url_arr.length > 3) {
        return '';
    }

    if (url_arr.length == 2)
        url_arr.unshift(_GROUP_);

    var pre_arr = ['g', 'm', 'a'];

    var arr = [];
    for (d in pre_arr)
        arr.push(pre_arr[d] + '=' + url_arr[d]);

    for (d in param)
        arr.push(d + '=' + param[d]);

    return _APP_ + '?' + arr.join('&');
}


function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}

function StringBuffer() {
    this.__strings__ = new Array();
}

StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
    return this; //方便链式操作
}
StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    if (isEmpty(fmt)) {
        fmt = "yyyy-MM-dd hh:mm:ss";
    }
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return fmt;
}

function yyyyMMddhhmmssFormat(val) {
    return val.replace(/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/, "$1-$2-$3 $4:$5:$6")
}

function calendarMonth(id) {
    var datepicker = new BUI.Calendar.MonthPicker({
        trigger: '#' + id,
        month: 1,
        align: {points: ['tl', 'tl']},
        autoRender: true,
        autoHide: true,
        year: new Date().getFullYear(),
        success: function (obj) {
            var month = this.get('month'), year = this.get('year');
            $("#" + id).val(year + '-' + (month + 1));
            this.hide();
        }
    });
}

function renovate() {
    location.replace(location.href);
};

function clearForm(obj) {
    var form = $(obj).parents("form");
    $(form)[0].reset();
    $("input[type='text']").attr("value", "");
    $("select option:first").attr("selected", true).siblings("option").attr("selected", false);
};
