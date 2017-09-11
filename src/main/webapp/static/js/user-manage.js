var UserManage = {
	add :function(){
		$("#add_dialog").dialog({
			title : '新增',
			width : 600,
			height : 500,
			closed : false,
			cache : false,
			resizable : false,
			href : "user-manage/view/edit",
			modal : true,
			onLoad : function() {
				
			},
			buttons : [ {
				id : 'add_save_ds_btn',
				iconCls: "icon-save",
				text : '保存',
				handler : function(){
					
				}
			}, {
				iconCls: "icon-cancel",
				text : '关闭',
				handler : function() {
					$("#add_dialog").dialog('close');
				}
			} ]
		});
	},
	editBtn : function(value, row, index) {
		var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"infoDS('"
				+ index + "');\">";
		button = button
				+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
		return button;
	}
}