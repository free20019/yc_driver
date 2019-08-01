(function ($) {
	var state = {
		tagId: '',
		queryCheckBoxAll: ' .checkboxAll',
		queryCheckBox: ' tbody input.checkbox[type=checkbox]'
	};
	
	function queryCheckBoxAllEffect(thisOne) {
		var queryCheckBox = state.tagId + state.queryCheckBox;
		if ($(thisOne).is(":checked")){
			$(queryCheckBox).prop('checked', true)
		}else{
			$(queryCheckBox).prop('checked', false)
		}
	}
	function queryCheckBoxEffect() {
		var queryCheckBoxAll = state.tagId + state.queryCheckBoxAll;
		var queryCheckBox = state.tagId + state.queryCheckBox;

		var checkboxAll = $(queryCheckBoxAll);
		var checkboxAllLength = $(queryCheckBox).length;
		var checkboxCheckedLength = $(queryCheckBox + ':checked').length;

		if (checkboxAllLength > checkboxCheckedLength && checkboxCheckedLength > 0) {
			checkboxAll.get(0).indeterminate = true;
		} else {
			if (checkboxCheckedLength === 0) checkboxAll.prop('checked', false);
			else checkboxAll.prop('checked', true);
			checkboxAll.get(0).indeterminate = false;
		}
	}
	
	function CheckBox(id, option) {
		$.extend(state, option);
		state.tagId = id;
		var queryCheckBoxAll = id + state.queryCheckBoxAll,
			queryCheckBox = id + state.queryCheckBox;
		$(queryCheckBoxAll).on('change', function () {
			queryCheckBoxAllEffect(this);
		});
		$(queryCheckBox).on('change', function () {
			queryCheckBoxEffect();
		})
	}

	window.CheckBox = CheckBox;
})(jQuery);