 var basePath = "https://"+self.location.host+"/yc_driver/";
 var power_buts = "";
 $.ajax({
	 type: "POST",
	url: basePath + "login/index",
	data:{},
	timeout : 180000,
	dataType: 'json',
	success:function(data){
		console.log(data)
		var menu = eval(data.info);
		console.log(menu)
		power_buts = data.power_but;
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
	}         
});
//bootstrap-datetimepicker default option
var dateDefaultOption = {
	language:  'zh-CN',
	format: 'yyyy-mm-dd',
	autoclose: 1,
	startView: 2,
	minView: 2
};
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt)
{ //author: meizz
	var o = {
		"M+" : this.getMonth()+1,                 //月份
		"d+" : this.getDate(),                    //日
		"h+" : this.getHours(),                   //小时
		"m+" : this.getMinutes(),                 //分
		"s+" : this.getSeconds(),                 //秒
		"q+" : Math.floor((this.getMonth()+3)/3), //季度
		"S"  : this.getMilliseconds()             //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	return fmt;
};
function setformat(obj) {
	if(obj.length==0){
		return "";
	}
	var y = (obj.getFullYear()).toString();
	var m = (obj.getMonth() + 1).toString();
	if (m.length == 1) {
		m = "0" + m;
	}
	var d = obj.getDate().toString();
	if (d.length == 1) {
		d = "0" + d;
	}
	var time = y + "-" + m + "-" + d;
	return time;
}
function setformat1(obj) {
	if(obj.length==0){
		return "";
	}
	var y = (obj.getFullYear()).toString();
	var m = (obj.getMonth() + 1).toString();
	if (m.length == 1) {
		m = "0" + m;
	}
	var d = obj.getDate().toString();
	if (d.length == 1) {
		d = "0" + d;
	}
	var h = obj.getHours().toString();
	if (h.length == 1) {
		h = "0" + h;
	}
	var mi = obj.getMinutes().toString();
	if (mi.length == 1) {
		mi = "0" + mi;
	}
	var s = obj.getSeconds().toString();
	if (s.length == 1) {
		s = "0" + s;
	}
	var time = y + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s;
	return time;
}
function comboboxDFun (id) {
	if (!id) {console.error('combobox方法需要传入id值');return false}
	$(id + ' .iFComboBox').off('click').on('click', function () {
		if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
		var thisOne = this;
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
		} else {
			$(this).addClass('selected');
			$(this).find('.iFList').on('click', function () {
				if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
			}).find('li').off('click').on('click', function () {
				$(this).addClass('selected').siblings('.selected').removeClass('selected');
				$(thisOne).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
				$(thisOne).find('input').trigger('change');
			});
		}
	});
}
/**
 * 添加ComboBox事件
 * @param panelId：表单或查询条件面板的id
 */
function addEventComboBox(panelId) {
	$(panelId + ' .iFComboBox').find('input[type=text]').off('.comboboxInput').on('focus.comboboxInput', function () {
		$(this).siblings('.iFList').show();
	}).on('blur.comboboxInput', function () {
		var thisOne = $(this);
		setTimeout(function () {
			thisOne.siblings('.iFList').hide();
		}, 200);
	}).end().find('a').off('click').on('click', function () {
		if ($(this).siblings('.iFList').css('display')==='none'){
			$(this).siblings('input[type=text]').focus();
		}
	}).end().find('.iFList li').off('click').on('click', function () {
		$(this).addClass('selected').siblings('.selected').removeClass('selected');
		var value = $(this).text();
		var key = $(this).attr('data-value');
		$(this).parents('.iFList').siblings('input[type=text]').attr({value: value, 'data-value': key}).trigger('change');
	});
}
/**
 * 添加ComboBoxList事件
 * @param id：combobox的id
 */
function addEventComboBoxList(id) {
	$(id + '.iFComboBox').find('.iFList li').off('click').on('click', function () {
		$(this).addClass('selected').siblings('.selected').removeClass('selected');
		var value = $(this).text();
		var key = $(this).attr('data-value');
		$(this).parents('.iFList').siblings('input[type=text]').attr('data-value', key);
		$(this).parents('.iFList').siblings('input[type=text]').val(value);
		$(this).parents('.iFList').siblings('input[type=text]').trigger('change');
	});
}
/**
*  下拉框通用方法，传入一个id、data  直接将内容放入下拉框
*/
function xlktyff(id,data){
	for(var i=0;i<data.length;i++){
		$("#"+id).append('<li data-value="'+data[i].id+'">'+data[i].name+'</li>');
	}
	selectonclick(id);
}
function selectonclick(id){
	$("#"+id).find('.iFList').on('click', function () {
		if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
	}).find('li').off('click').on('click', function () {
		$("#"+id).addClass('selected').siblings('.selected').removeClass('selected');
		$("#"+id).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
		$("#"+id).find('input').trigger('change');
	});
}
//form提交将字符串转json
function getFormJson(id) {
    var o = {};
    var a = $("#"+id).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}