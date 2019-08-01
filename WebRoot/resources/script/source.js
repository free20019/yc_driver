var Source = (function () {
	function Source(init) {
		var _this = this;
		this.get = function (name, key) {
			for (var i=0;i<_this.data.length;i++) {
				var item = _this.data[i];
				if (name === item.name){
					if (arguments.length === 1) return item;
					 else return item[key];
				}
			}
			return null;
		};
		this.set = function (name, key, val) {
			var item = _this.get(name);
			if (item){
				if (key instanceof Object) {
					for (var k in key) item[k] = key[k];
				} else {
					item[key] = val;
				}
			} else {
				item = {name: name};
				if (key instanceof Object) {
					for (var k in key) item[k] = key[k];
				} else {
					item[key] = val;
				}
				_this.data.push(item);
			}
		};
		this.getValues = function () {
			var data = {};
			for (var i = 0; i < _this.data.length; i++) {
				var item = _this.data[i];
				data[item.name] = item.value;
			}
			return data;
		};
		this.regular = function (item) {
			if (0 === item.value.length) return {status: false, msg: '请输入' + item.title};
			else if (!new RegExp(item.reg).test(item.value)) return {status: false, msg: (item.errText ? item.errText : item.title + '格式不正确')};
			else return {status: true, data: item.value};
		};
		this.bindSource = function (item, type) {
			var tagList = document.getElementsByName(item.name);
			for (var i = 0; i < tagList.length; i++) {
				var tagItem = tagList[i];
				/*防止循环遍历后无法找到对应标签*/
				(function (tagItem) {
					if (_this.isInputTag(tagItem.tagName)) {
						if (type && 'syncData' === type) tagItem.value = item.value;
						else {
							function tagItemKeyUp() {
								item.value = this.value;
								_this.bindSource(item, 'syncData');
								if (item.reg) {
									var regularInfo = _this.regular(item);
									var style = tagItem.getAttribute('style');
									if (!style) style = {};
									else style = _this.toJSON(style);
									if (!regularInfo.status) {
										tagItem.setAttribute('style', _this.toString(_this.colorStatus.error(style)));
									} else {
										tagItem.setAttribute('style', _this.toString(_this.colorStatus.primary(style)));
									}
								}
							}
							function tagItemBlur() {
								var regularInfo = item.reg ? _this.regular(item) : null;
								var style = tagItem.getAttribute('style');
								if (!style) style = {};
								else style = _this.toJSON(style);
								if (regularInfo instanceof Object && !regularInfo.status) {
									tagItem.setAttribute('style', _this.toString(_this.colorStatus.error(style)));
								} else if (regularInfo instanceof Object && regularInfo.status || style['border-color'] && '#a94442' !== style['border-color']) {
									tagItem.setAttribute('style', _this.toString(_this.colorStatus.default(style)));
								}
							}
							tagItem.removeEventListener('keyup', tagItemKeyUp);
							tagItem.addEventListener('keyup', tagItemKeyUp);
							tagItem.removeEventListener('blur', tagItemBlur);
							tagItem.addEventListener('blur', tagItemBlur)
						}
					} else {
						tagItem.innerText = item.value;
					}
				})(tagItem);
			}
		};
		this.toJSON = function (data) {
			var json = {};
			var list = data.split(';');
			for (var i = 0; i < list.length; i++) {
				var item = list[i].split(':');
				if (item[0]) json[item[0]] = item[1];
			}
			return json;
		};
		this.toString = function (data) {
			var string = '';
			for (var key in data) {
				var value = data[key];
				string += key + ':' + value + ';';
			}
			return string;
		};
		this.isInputTag = function (tagName) {
			switch (tagName.toUpperCase()) {
				case 'INPUT':
				case 'SELECT':
				case 'TEXTAREA':
					return true;
				default:
					return false;
			}
		};
		this.colorStatus = {
			primary: function (style) {
				style['border-color'] = '#66afe9';
				style['-webkit-box-shadow'] = 'inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6)';
				style['box-shadow'] = 'inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6)';
				return style;
			},
			error: function (style) {
				style['border-color'] = '#a94442';
				style['-webkit-box-shadow'] = 'inset 0 1px 1px rgba(0,0,0,.075), 0 0 6px #ce8483';
				style['box-shadow'] = 'inset 0 1px 1px rgba(0,0,0,.075), 0 0 6px #ce8483';
				return style;
			},
			default: function (style) {
				style['border-color'] = '#ccc';
				style['-webkit-box-shadow'] = 'none';
				style['box-shadow'] = 'none';
				return style;
			}
		};
		this.data = init.fields;
		this.dialogPlugin = init.dialogPlugin;
		for (var i=0;i<_this.data.length;i++){
			var item = this.data[i];
			if (item instanceof Object) _this.bindSource(item);
			else throw "\u683C\u5F0F\u4E0D\u6B63\u786E\uFF0C\u6B63\u786E\u4F20\u5165\u683C\u5F0F\uFF1A{name: '', title: '', value: '', reg: /^$/}";
		}
	}
	return Source;
}());