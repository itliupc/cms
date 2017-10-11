var CarManage = (function () {
	return {
		/**
		 * 查询按钮事件
		 */
		querySelect: function(){
			var carNum=$("#car-select-search").find("input[name='carNum']").val().toUpperCase();
			var operateNum=$("#car-select-search").find("input[name='operateNum']").val().toUpperCase();
			$("#car-select-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum});
		},
		/**
		 * 重置按钮事件
		 */
		resetSelect: function(){
			$("#car-select-search").form('load',{'carNum':'','operateNum':''});
			$("#car-select-datagrid").datagrid('load',{'carNum':'','operateNum':''});
		},
		
		editBtn : function(value, row, index) {
			var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"CarManage.edit('"
					+ index + "');\">";
			button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
			return button;
		},
		
		formatOperateUser : function(value, row, index){
			if(row.user){
				return row.user.name;
			}else{
				return "";
			}
		},
		
		/**
		 * 查询按钮事件
		 */
		query: function(){
			var carNum=$("#car-search").find("input[name='carNum']").val().toUpperCase();
			var operateNum=$("#car-search").find("input[name='operateNum']").val().toUpperCase();
			$("#car-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum});
		},
		/**
		 * 重置按钮事件
		 */
		reset: function(){
			$("#car-search").form('load',{'carNum':'','operateNum':''});
			$("#car-datagrid").datagrid('load',{'carNum':'','operateNum':''});
		},
		
		/**
		 * 新增按钮事件
		 */
		add :function(){
			$("#add_car_dialog").dialog({
				title : '新增',
				width : 500,
				height : 290,
				closed : false,
				cache : false,
				resizable : false,
				href : "car-manage/view/add",
				modal : true,
				onLoad : function() {
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#car-add").form('validate')){
							var carNum=$.trim($("#car-add").find("input[name='carNum']").val().toUpperCase());
							var operateNum=$.trim($("#car-add").find("input[name='operateNum']").val().toUpperCase());
							var ownerName = $("#car-add").find("input[name='ownerName']").val();
							var ownerPhone = $("#car-add").find("input[name='ownerPhone']").val();
							$.ajax({
								method : 'post',
								url : 'car-manage/addCar',
								data : {
									'carNum' : carNum,
									'operateNum' : operateNum,
									'ownerName' : ownerName,
									'ownerPhone' : ownerPhone
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#add_car_dialog").dialog('close');
										$("#car-datagrid").datagrid('reload');
										$.messager.alert('提示','保存成功！',"info");
									}else{
										$.messager.alert('提示',data.message,"info");
									}
								}
							});
						}
					}
				}, {
					iconCls: "icon-cancel",
					text : '关闭',
					handler : function() {
						$("#add_car_dialog").dialog('close');
					}
				} ]
			});
		},
		/**
		 * 编辑按钮事件
		 */
		edit :function(index){
			var record = $("#car-datagrid").datagrid('getRows')[index];
			$("#edit_car_dialog").dialog({
				title : '编辑',
				width : 500,
				height : 290,
				closed : false,
				cache : false,
				resizable : false,
				href : "car-manage/view/edit",
				modal : true,
				onLoad : function() {
					$("#car-edit").form('load',record);
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#car-edit").form('validate')){
							var id=$.trim($("#car-edit").find("input[name='id']").val());
							var carNum=$.trim($("#car-edit").find("input[name='carNum']").val().toUpperCase());
							var operateNum=$.trim($("#car-edit").find("input[name='operateNum']").val().toUpperCase());
							var ownerName = $("#car-edit").find("input[name='ownerName']").val();
							var ownerPhone = $("#car-edit").find("input[name='ownerPhone']").val();
							$.ajax({
								method : 'post',
								url : 'car-manage/editCar',
								data : {
									'id' : id,
									'carNum' : carNum,
									'operateNum' : operateNum,
									'ownerName' : ownerName,
									'ownerPhone' : ownerPhone
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#edit_car_dialog").dialog('close');
										$("#car-datagrid").datagrid('reload');
										$.messager.alert('提示','保存成功！',"info");
									}else{
										$.messager.alert('提示',data.message,"info");
									}
								}
							});
						}
					}
				}, {
					iconCls: "icon-cancel",
					text : '关闭',
					handler : function() {
						$("#edit_car_dialog").dialog('close');
					}
				} ]
			});
		},
		/**
		 * 删除按钮事件
		 */
		remove : function() {
			var selections = $("#car-datagrid").datagrid('getSelections');
			if (selections.length == 0) {
				$.messager.alert('提示', '请至少选择一条记录进行操作!',"info");
				return;
			}
			var ids = [];
			for(var i = 0;i < selections.length; i++){
				ids.push(selections[i].id);
			}
			$.messager.confirm('提示', '删除车辆信息，同时会删除车辆其他相关信息，确认要删除吗?', function(flag) {
				if (flag) {
					$.ajax({
						url : "car-manage/deleteCar",
						data : JSON.stringify(ids),
						method : 'post',
						dataType : 'json',
						contentType: "application/json; charset=utf-8", 
						success : function(data) {
							$("#car-datagrid").datagrid('reload');
							$.messager.alert('提示','删除成功！',"info");
						}
					});
				}
			});
		},
		/**
		 * 数据导入
		 */
		importData : function(){
			var importDialog = $("<div id='car-import'></div>").dialog({
			    title: '数据导入',
			    width: 400,
			    height: 160,
			    closed: false,
			    cache: false,
			    resizable : false,
			    content: '<form method="POST" enctype="multipart/form-data" action="imp-manage/import/car"><table style="margin-top:10px;"><tr style="height:30px;"><td style="width:30%;text-align: center;">车辆清单:</td><td><input id="upfile" name="upfile" type="file"/></td></tr></table></form>',
			    modal: true,
			    buttons : [{
					iconCls: "icon-save",
					text : '导入',
					handler : function() {
						var fileDir = importDialog.find("input[type='file']").val();  
		                var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
		                if(!fileDir){  
		                	$.messager.alert('提示','请选择需要导入的Excel文件！',"info");
		                } else  if(".xls" != suffix && ".xlsx" != suffix ){  
		                    $.messager.alert('提示','请选择Excel格式的文件导入！',"info");
		                } else {
		                	importDialog.dialog('close');
		                	$.messager.progress({title: '正在导入'});
		                	importDialog.find('form').ajaxSubmit({    
		                            url:'imp-manage/import/car',  
		                            dataType: 'text',  
		                            success: function(result){
		                            	$.messager.progress('close');
		                            	$("#car-datagrid").datagrid('reload');
		                            	$.messager.alert('提示','Excel导入成功！',"info");
		                            },  
		                            error: function(){
		                            	$.messager.alert('错误','Excel导入出错！',"warning");
		                            }  
		                        });   
		                }  
					}
				},{
					iconCls: "icon-cancel",
					text : '关闭',
					handler : function() {
						importDialog.dialog('close');
					}
				} ]
			});
		},
		/**
		 * 数据导出
		 */
		exportData : function(){
			$.messager.confirm('提示', '确认要导出数据吗?', function(flag) {
				if (flag) {
					var carNum=$("#car-search").find("input[name='carNum']").val().toUpperCase();
					var operateNum=$("#car-search").find("input[name='operateNum']").val().toUpperCase();
					window.location.href = "exp-manage/export/car?param1="+carNum+"&param2="+operateNum+"&param3=0";
				}
			});
		}
	};
})();
