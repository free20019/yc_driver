/*//wxTrips.html
$('.container-fluid .iFComboBox').on('click', function () {
 	var thisOne = this;
 	if ($(this).hasClass('selected')) {
 		$(this).removeClass('selected');
 	} else {
 		$(this).addClass('selected');
 		$(this).find('.iFList').on('click', function () {
 			if (event.stopPropagation) {
 		        // 閽堝 Mozilla 鍜�Opera
 		        event.stopPropagation();
 	        }else if (window.event) {
 		        // 閽堝 IE
 	        	window.event.cancelBubble = true;
 	        }
 		}).find('li').off('click').on('click', function () {
 			$(this).addClass('selected').siblings('.selected').removeClass('selected');
 			$(thisOne).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
 			$(thisOne).find('input').trigger('change');
 		});
 	}
 });
addEventComboBox('.container-fluid');
*//**
*  下拉框通用方法，传入一个id、data  直接将内容放入下拉框
*//*
function xlktyff(id,data){
	for(var i=0;i<data.length;i++){
		$(id).append('<li data-value="'+data[i].id+'">'+data[i].name+'</li>');
	}
	selectonclick(id);
}

function selectonclick(id){
	$(id).find('.iFList').on('click', function () {
		if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
	}).find('li').off('click').on('click', function () {
		$(id).addClass('selected').siblings('.selected').removeClass('selected');
		$(id).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
		$(id).find('input').trigger('change');
	});
}
*//**
*  下拉框通用和下拉框事件方法，传入一个id、data 直接将内容放入下拉框并且可以选中
*//*
function xlktyffComboboxDFun(id,data){
	if(data!=null){
		var data = data.datas;
		$(id).find('.iFList').html("");
		for (var i = 0; i < data.length; i++) {
			var cphms="<li data-value='"+data[i].id+"'>"+data[i].name+"</li>";
			$(id).find('.iFList').append(cphms);
		}
		$(id).find('.iFList').on('click', function () {
			if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
		}).find('li').off('click').on('click', function () {
			$(id).addClass('selected').siblings('.selected').removeClass('selected');
			$(id).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
			$(id).find('input').trigger('keyup');
		});
	}
}
*//**
 * 下拉框事件方法
 * @param id：标签Id
 * @returns {boolean}
 *//*
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
//内容

//function findqy(obj){
//	$.ajax({
//		type: "POST",
//		url: basePath + "common/findqy",
//		data:{},
//		timeout : 180000,
//		dataType: 'json',
//		success:function(data){
//			$(obj).empty();
//			for(var i=0; i<data.datas.length;i++){
//				$(obj).append('<li data-value="'+data.datas[i].id+'">'+data.datas[i].name+'</li>');
//			}
//		},
//		error: function(){
//		}         
//	});
//}
//车主下拉栏
function czcx(cz,ul){
			$.ajax({
				 type: "POST",
				url: basePath + "common/findclcz",
				data:{
					"vhic":''
				},
				timeout : 180000,
				dataType: 'json',
				success:function(data){
					xlktyff(ul,data.datas);
					comboboxDFun(cz);
				},
				error: function(){
				}         
			});
}
function czcxtj(obj,cz){
		console.log($(cz).find('input').val())
		var cpmhs=$(cz).find('input').val();
			$.ajax({
				 type: "POST",
				url: basePath + "common/findclcz",
				data:{
					"vhic":cpmhs
				},
				timeout : 180000,
				dataType: 'json',
				success:function(data){
					xlktyffComboboxDFun(cz,data);
				},
				error: function(){
				}         
			});
}
//鍖哄潡
function fundqk(obj){
	$.ajax({
		type: "POST",
		url: basePath + "common/findqk",
		data:{},
		timeout : 180000,
		dataType: 'json',
		success:function(data){
			console.log("data="+data)
			$(obj).empty();
			for(var i=0; i<data.datas.length;i++){
				$(obj).append('<li data-value="'+data.datas[i].id+'">'+data.datas[i].name+'</li>');
			}
		},
		error: function(){
		}         
	});
}
//鍏徃
function fundcomp(obj){
	$.ajax({
		 type: "POST",
		url: basePath + "common/findcomp",
		data:{
			"ba_id":""
		},
		timeout : 180000,
		dataType: 'json',
		success:function(data){
			$(obj).empty();
			for(var i=0; i<data.datas.length;i++){
				$(obj).append('<li data-value="'+data.datas[i].id+'">'+data.datas[i].name+'</li>');
			}
		},
		error: function(){
		}         
	});
}
//鍏徃鏉′欢鏌ヨ
function gscx(obj){
	$(obj).find('input').on('keyup',function(){
		var cpmhs=$(obj).find('input').val();
		if(cpmhs==""){
			$.ajax({
				 type: "POST",
				url: basePath + "common/findcomp_tj",
				data:{
					"comp_name":""
				},
				timeout : 180000,
				dataType: 'json',
				success:function(data){
					var data2 = data.datas;
					$(obj).find('.iFList').html("");
					for (var i = 0; i < data2.length; i++) {
						var cphms="<li data-value='"+data2[i].id+"'>"+data2[i].name+"</li>";
						$(obj).find('.iFList').append(cphms);
					}
					$(obj).find('.iFList').on('click', function () {
						if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
					}).find('li').off('click').on('click', function () {
						$(this).addClass('selected').siblings('.selected').removeClass('selected');
						$(obj).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
					});
				},
				error: function(){
				}         
			});
		}else{
			$.ajax({
				 type: "POST",
				url: basePath + "common/findcomp_tj",
				data:{
					"comp_name":cpmhs
				},
				timeout : 180000,
				dataType: 'json',
				success:function(data){
					var data2 = data.datas;
					$(obj).find('.iFList').html("");
					for (var i = 0; i < data2.length; i++) {
						var cphms="<li data-value='"+data2[i].id+"'>"+data2[i].name+"</li>";
						$(obj).find('.iFList').append(cphms);
					}
					$(obj).find('.iFList').on('click', function () {
						if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
					}).find('li').off('click').on('click', function () {
						$(this).addClass('selected').siblings('.selected').removeClass('selected');
						$(obj).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
					});
				},
				error: function(){
				}         
			});
		}
	});
}
//杞︾墝
function cpcx(obj,cp){
	$(cp).on('keyup',function(){
		var cpmhs=$(cp).val();
		if(cpmhs.length>2){
			$.ajax({
				 type: "POST",
				url: basePath + "common/findvhictj",
				data:{
					"vhic":cpmhs
				},
				timeout : 180000,
				dataType: 'json',
				success:function(data){
					var data2 = data.datas;
					$(obj).find('.iFList').html("");
					for (var i = 0; i < data2.length; i++) {
						var cphm="<li data-value='"+data2[i].id+"'>"+data2[i].name+"</li>";
						$(obj).find('.iFList').append(cphm);
					}
					$(obj).find('.iFList').on('click', function () {
						if (event.stopPropagation){event.stopPropagation();}else if (window.event) {window.event.cancelBubble = true;}
					}).find('li').off('click').on('click', function () {
						$(this).addClass('selected').siblings('.selected').removeClass('selected');
						$(obj).find('input').data('value', $(this).data('value')).val($(this).text()).end().removeClass('selected');
					});
				},
				error: function(){
				}         
			});
		}
	});
}
//缁翠慨浜哄憳
function findry(obj){
	$.ajax({
		type: "POST",
		url: basePath + "common/findwxry",
		data:{},
		timeout : 180000,
		dataType: 'json',
		success:function(data){
			$(obj).empty();
			for(var i=0; i<data.datas.length;i++){
				$(obj).append('<li data-value="'+data.datas[i].id+'">'+data.datas[i].name+'</li>');
			}
		},
		error: function(){
		}         
	});
}
//缁翠慨绫诲瀷
function findwxlx(obj){
	$.ajax({
		type: "POST",
		url: basePath + "common/findwxlx",
		data:{},
		timeout : 180000,
		dataType: 'json',
		success:function(data){
			console.log(data)
			$(obj).empty();
			for(var i=0; i<data.datas.length;i++){
				$(obj).append('<li data-value="'+data.datas[i].id+'">'+data.datas[i].name+'</li>');
			}
		},
		error: function(){
		}         
	});
}
//缁堢绫诲瀷
function findzdlx(obj){
	$.ajax({
		type: "POST",
		url: basePath + "common/findzdlx",
		data:{},
		timeout : 180000,
		dataType: 'json',
		success:function(data){
			$(obj).empty();
			for(var i=0; i<data.datas.length;i++){
				$(obj).append('<li data-value="'+data.datas[i].id+'">'+data.datas[i].name+'</li>');
			}
		},
		error: function(){
		}         
	});
}*/