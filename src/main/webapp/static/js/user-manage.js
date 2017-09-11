var UserManage = {
	editBtn : function(value, row, index) {
			var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"infoDS('"
					+ index + "');\">";
			button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
			return button;
	},
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
					debugger
					$.ajax({
						method : 'post',
						url : 'user-manage/addUser',
						data : {
							'userId' : "1",
							'userName' : "code2"
						},
						async : false,
						success : function(data) {
							if (data == 'false') {
								flag = true;
							}
						}
					});
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
	/**
	 * 删除按钮事件
	 */
	remove : function() {
		var selections = $("#user-datagrid").datagrid('getSelections');
		if (selections.length == 0) {
			$.messager.alert('提示', '请至少选择一条记录进行操作!');
			return;
		}
		var ids = [];
		for(var i = 0;i < selections.length; i++){
			ids.push(selections[i].userId);
		}
		$.messager.confirm('提示', '确认要删除吗?', function(flag) {
			if (flag) {
				$.ajax({
					url : "user-manage/delete",
					data : JSON.stringify(ids),
					method : 'post',
					dataType : 'json',
					contentType: "application/json; charset=utf-8", 
					success : function(data) {
						grid.datagrid('reload');
					}
				});
			}
		});
	}
}