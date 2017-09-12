var UserManage = {
	editBtn : function(value, row, index) {
			var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"UserManage.edit('"
					+ index + "');\">";
			button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
			return button;
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
		$("#add_dialog").dialog({
			title : '新增',
			width : 400,
			height : 290,
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
					var name=$("#user-add").find("input[name='name']").val();
					var userName=$("#user-add").find("input[name='userName']").val();
					var password=$("#user-add").find("input[name='password']").val();
					$.ajax({
						method : 'post',
						url : 'user-manage/addUser',
						data : {
							'name' : name,
							'userName' : userName,
							'password' : password
						},
						async : false,
						success : function(data) {
							if(data.result){
								$("#add_dialog").dialog('close');
								$("#user-datagrid").datagrid('reload');
								$.messager.alert('提示','保存成功！');
							}else{
								
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
	 * 编辑按钮事件
	 */
	edit :function(index){
		var record = $("#user-datagrid").datagrid('getRows')[index];
		debugger
		$("#add_dialog").dialog({
			title : '编辑',
			width : 400,
			height : 290,
			closed : false,
			cache : false,
			resizable : false,
			href : "user-manage/view/edit",
			modal : true,
			onLoad : function() {
				
			},
			buttons : [ {
				iconCls: "icon-save",
				text : '保存',
				handler : function(){
					var name=$("#user-add").find("input[name='name']").val();
					var userName=$("#user-add").find("input[name='userName']").val();
					var password=$("#user-add").find("input[name='password']").val();
					$.ajax({
						method : 'post',
						url : 'user-manage/editUser',
						data : {
							'name' : name,
							'userName' : userName,
							'password' : password
						},
						async : false,
						success : function(data) {
							if(data.result){
								$("#add_dialog").dialog('close');
								$("#user-datagrid").datagrid('reload');
								$.messager.alert('提示','保存成功！');
							}else{
								
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
	}
}