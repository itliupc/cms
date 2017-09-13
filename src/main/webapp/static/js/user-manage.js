var UserManage = {
	editBtn : function(value, row, index) {
			var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"UserManage.edit('"
					+ index + "');\">";
			button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
			if(1 == row.status){
				button = button
					+ "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"启用账号\" data-options=\"iconCls:'icon-lock-go',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"UserManage.updateStatus('"
						+ row.userId + "');\">";
			
				button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-lock-go l-btn-icon-left\">启用账号</span></span></a>";
			}else{
				button = button
					+ "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"停用账号\" data-options=\"iconCls:'icon-lock',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"UserManage.updateStatus('"
						+ row.userId + "');\">";
			
				button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-lock l-btn-icon-left\">停用账号</span></span></a>";
			}
			
			return button;
	},
	formatStatus : function(value, row, index) {
		if(0==value){
			return '启用';
		}else{
			return '停用';
		}
	},
	/**
	 * 查询按钮事件
	 */
	query: function(){
		var name=$("#user-search").find("input[name='name']").val();
		var userName=$("#user-search").find("input[name='userName']").val();
		$("#user-datagrid").datagrid('load',{'name':name,'userName':userName});
	},
	/**
	 * 重置按钮事件
	 */
	reset: function(){
		$("#user-search").form('load',{'name':'','userName':''});
		$("#user-datagrid").datagrid('load',{'name':'','userName':''});
	},
	/**
	 * 新增按钮事件
	 */
	add :function(){
		$("#add_user_dialog").dialog({
			title : '新增',
			width : 400,
			height : 240,
			closed : false,
			cache : false,
			resizable : false,
			href : "user-manage/view/add",
			modal : true,
			onLoad : function() {
			},
			buttons : [ {
				iconCls: "icon-save",
				text : '保存',
				handler : function(){
					if($("#user-add").form('validate')){
						var name=$.trim($("#user-add").find("input[name='name']").val());
						var userName=$.trim($("#user-add").find("input[name='userName']").val());
						var status=$("#user-status").combobox('getValue');
						$.ajax({
							method : 'post',
							url : 'user-manage/addUser',
							data : {
								'name' : name,
								'userName' : userName,
								'status' : status
							},
							async : false,
							success : function(data) {
								if(data.result){
									$("#add_user_dialog").dialog('close');
									$("#user-datagrid").datagrid('reload');
									$.messager.alert('提示','保存成功,初始化密码为:123456！');
								}else{
									$.messager.alert('提示',data.message);
								}
							}
						});
					}
				}
			}, {
				iconCls: "icon-cancel",
				text : '关闭',
				handler : function() {
					$("#add_user_dialog").dialog('close');
				}
			} ]
		});
	},
	/**
	 * 编辑按钮事件
	 */
	edit :function(index){
		var record = $("#user-datagrid").datagrid('getRows')[index];
		$("#edit_user_dialog").dialog({
			title : '编辑',
			width : 400,
			height : 240,
			closed : false,
			cache : false,
			resizable : false,
			href : "user-manage/view/edit",
			modal : true,
			onLoad : function() {
				$("#user-edit").form('load',record);
			},
			buttons : [ {
				iconCls: "icon-save",
				text : '保存',
				handler : function(){
					if($("#user-edit").form('validate')){
						var userId=$.trim($("#user-edit").find("input[name='userId']").val());
						var name=$.trim($("#user-edit").find("input[name='name']").val());
						var userName=$.trim($("#user-edit").find("input[name='userName']").val());
						var status=$("#user-status").combobox('getValue');
						
						$.ajax({
							method : 'post',
							url : 'user-manage/editUser',
							data : {
								'userId':userId,
								'name' : name,
								'userName' : userName,
								'status' : status
							},
							async : false,
							success : function(data) {
								if(data.result){
									$("#edit_user_dialog").dialog('close');
									$("#user-datagrid").datagrid('reload');
									$.messager.alert('提示','保存成功！');
								}else{
									$.messager.alert('提示',data.message);
								}
							}
						});
					}
				}
			}, {
				iconCls: "icon-cancel",
				text : '关闭',
				handler : function() {
					$("#edit_user_dialog").dialog('close');
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
					url : "user-manage/deleteUser",
					data : JSON.stringify(ids),
					method : 'post',
					dataType : 'json',
					contentType: "application/json; charset=utf-8", 
					success : function(data) {
						$("#user-datagrid").datagrid('reload');
						$.messager.alert('提示','删除成功！');
					}
				});
			}
		});
	},
	/**
	 * 重置密码
	 */
	resetPassword : function() {
		var selections = $("#user-datagrid").datagrid('getSelections');
		if (selections.length == 0) {
			$.messager.alert('提示', '请至少选择一条记录进行操作!');
			return;
		}
		var ids = [];
		for(var i = 0;i < selections.length; i++){
			ids.push(selections[i].userId);
		}
		$.messager.confirm('提示', '确认要重置密码吗?', function(flag) {
			if (flag) {
				$.ajax({
					url : "user-manage/resetPassword",
					data : JSON.stringify(ids),
					method : 'post',
					dataType : 'json',
					contentType: "application/json; charset=utf-8", 
					success : function(data) {
						$("#user-datagrid").datagrid('reload');
						$.messager.alert('提示','密码重置成功,初始化密码为:123456！');
					}
				});
			}
		});
	},
	
	/**
	 * 修改状态
	 */
	updateStatus : function(userId) {
		$.messager.confirm('提示', '确认要更新账号状态吗?', function(flag) {
			if (flag) {
				$.ajax({
					method : 'post',
					url : 'user-manage/updateStatus',
					data : {
						'userId':userId
					},
					async : false,
					success : function(data) {
						if(data.result){
							$("#user-datagrid").datagrid('reload');
							$.messager.alert('提示',data.data);
						}else{
							$.messager.alert('提示',data.message);
						}
					}
				});
			}
		});
	}
}