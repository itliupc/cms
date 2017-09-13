var InsureManage = {
	editBtn : function(value, row, index) {
			var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"InsureManage.edit('"
					+ index + "');\">";
			button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
			return button;
	},
	/**
	 * 查询按钮事件
	 */
	query: function(){
		var carNum=$("#insure-search").find("input[name='carNum']").val();
		var operateNum=$("#insure-search").find("input[name='operateNum']").val();
		$("#insure-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum});
	},
	/**
	 * 重置按钮事件
	 */
	reset: function(){
		$("#insure-search").form('load',{'carNum':'','operateNum':''});
		$("#insure-datagrid").datagrid('load',{'carNum':'','operateNum':''});
	},
	/**
	 * 新增按钮事件
	 */
	add :function(){
		$("#add_insure_dialog").dialog({
			title : '新增',
			width : 400,
			height : 280,
			closed : false,
			cache : false,
			resizable : false,
			href : "insure-manage/view/edit",
			modal : true,
			onLoad : function() {
			},
			buttons : [ {
				iconCls: "icon-save",
				text : '保存',
				handler : function(){
					var carNum=$.trim($("#insure-edit").find("input[name='carNum']").val());
					var operateNum=$.trim($("#insure-edit").find("input[name='operateNum']").val());
					var forceInsure = $.now();
					var busInsure = $.now();
					$.ajax({
						method : 'post',
						url : 'insure-manage/addInsure',
						data : {
							'carNum' : carNum,
							'operateNum' : operateNum,
							'forceInsure' : forceInsure,
							'busInsure' : busInsure
						},
						async : false,
						success : function(data) {
							if(data.result){
								$("#add_insure_dialog").dialog('close');
								$("#insure-datagrid").datagrid('reload');
								$.messager.alert('提示','保存成功,初始化密码为:123456！');
							}else{
								$.messager.alert('提示',data.message);
							}
						}
					});
				}
			}, {
				iconCls: "icon-cancel",
				text : '关闭',
				handler : function() {
					$("#add_insure_dialog").dialog('close');
				}
			} ]
		});
	},
	/**
	 * 编辑按钮事件
	 */
	edit :function(index){
		var record = $("#insure-datagrid").datagrid('getRows')[index];
		$("#edit_insure_dialog").dialog({
			title : '编辑',
			width : 400,
			height : 280,
			closed : false,
			cache : false,
			resizable : false,
			href : "insure-manage/view/edit",
			modal : true,
			onLoad : function() {
				$("#insure-edit").form('load',record);
			},
			buttons : [ {
				iconCls: "icon-save",
				text : '保存',
				handler : function(){
					var id=$.trim($("#insure-edit").find("input[name='id']").val());
					var carNum=$.trim($("#insure-edit").find("input[name='carNum']").val());
					var operateNum=$.trim($("#insure-edit").find("input[name='operateNum']").val());
					var forceInsure = $.now();
					var busInsure = $.now();
					$.ajax({
						method : 'post',
						url : 'insure-manage/editInsure',
						data : {
							'id' : id,
							'carNum' : carNum,
							'operateNum' : operateNum,
							'forceInsure' : forceInsure,
							'busInsure' : busInsure
						},
						async : false,
						success : function(data) {
							if(data.result){
								$("#edit_insure_dialog").dialog('close');
								$("#insure-datagrid").datagrid('reload');
								$.messager.alert('提示','保存成功！');
							}else{
								$.messager.alert('提示',data.message);
							}
						}
					});
				}
			}, {
				iconCls: "icon-cancel",
				text : '关闭',
				handler : function() {
					$("#edit_insure_dialog").dialog('close');
				}
			} ]
		});
	},
	/**
	 * 删除按钮事件
	 */
	remove : function() {
		var selections = $("#insure-datagrid").datagrid('getSelections');
		if (selections.length == 0) {
			$.messager.alert('提示', '请至少选择一条记录进行操作!');
			return;
		}
		var ids = [];
		for(var i = 0;i < selections.length; i++){
			ids.push(selections[i].id);
		}
		$.messager.confirm('提示', '确认要删除吗?', function(flag) {
			if (flag) {
				$.ajax({
					url : "insure-manage/deleteInsure",
					data : JSON.stringify(ids),
					method : 'post',
					dataType : 'json',
					contentType: "application/json; charset=utf-8", 
					success : function(data) {
						$("#insure-datagrid").datagrid('reload');
						$.messager.alert('提示','删除成功！');
					}
				});
			}
		});
	}
}