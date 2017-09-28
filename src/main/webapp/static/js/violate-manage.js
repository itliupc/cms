var ViolateManage = (function () {
	return {
		mergeCell : function(data){
			var rows = data.rows;
			var rowCount = data.total;
			if(rowCount > 1){
				var span = 1;
				var preId = 0;
				var curId = 0;
				for(var i = 0; i <= rowCount; i++){
					if(i == rowCount) {
						curId = 0;
					} else {
						curId = rows[i].car.id;
					}
					if(preId == curId) {
						span += 1;
					} else {
						var index = i -span;
						$("#violate-datagrid").datagrid('mergeCells',{
							index : index,
							field : 'carNum',
							rowspan : span,
							colspan : null
						});
						$("#violate-datagrid").datagrid('mergeCells',{
							index : index,
							field : 'operateNum',
							rowspan : span,
							colspan : null
						});
						$("#violate-datagrid").datagrid('mergeCells',{
							index : index,
							field : 'ownerName',
							rowspan : span,
							colspan : null
						});
						$("#violate-datagrid").datagrid('mergeCells',{
							index : index,
							field : 'ownerPhone',
							rowspan : span,
							colspan : null
						});
						span = 1;
						preId = curId;
					}
				}
			}
			
		},
		editBtn : function(value, row, index) {
				var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"ViolateManage.edit('"
						+ index + "');\">";
				button = button
						+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
				return button;
		},
		formatHasDeal : function(value, row, index) {
			if(0==value){
				return '';
			}else{
				return "已缴费";
			}
		},
		formatRowDate : function(value, row, index){
			return DateUtil.formatDatebox(value);
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
			var carNum=$("#violate-search").find("input[name='carNum']").val().toUpperCase();
			var operateNum=$("#violate-search").find("input[name='operateNum']").val().toUpperCase();
			var hasDeal=$("#violate-hasDeal").combobox('getValue');
			$("#violate-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum,'hasDeal':hasDeal});
		},
		/**
		 * 重置按钮事件
		 */
		reset: function(){
			$("#violate-search").form('load',{'carNum':'','operateNum':'','hasDeal':''});
			$("#violate-datagrid").datagrid('load',{'carNum':'','operateNum':'','hasDeal':''});
		},
		/**
		 * 新增按钮事件
		 */
		add :function(){
			$("#add_violate_dialog").dialog({
				title : '新增',
				width : 700,
				height : 190,
				closed : false,
				cache : false,
				resizable : false,
				href : "violate-manage/view/add",
				modal : true,
				onLoad : function() {
					$("#violate-add").find("input[name='carNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-add');
					});
					$("#violate-add").find("input[name='operateNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-add');
					});
					$("#violate-add").find("input[name='ownerName']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-add');
					});
					$("#violate-add").find("input[name='ownerPhone']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-add');
					});
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#violate-add").form('validate')){
							var carId = $("#violate-add").find("input[name='carId']").val();
							var recordDate = $("#violate-add").find("input[name='recordDate']").val();
							var hasDeal=$("#violate-has-deal").combobox('getValue');
							$.ajax({
								method : 'post',
								url : 'violate-manage/addViolate',
								data : {
									'carId' : carId,
									'recordDate' : recordDate,
									'hasDeal' : hasDeal
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#add_violate_dialog").dialog('close');
										$("#violate-datagrid").datagrid('reload');
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
						$("#add_violate_dialog").dialog('close');
					}
				} ]
			});
		},
		/**
		 * 编辑按钮事件
		 */
		edit :function(index){
			var record = $("#violate-datagrid").datagrid('getRows')[index];
			$("#edit_violate_dialog").dialog({
				title : '编辑',
				width : 700,
				height : 190,
				closed : false,
				cache : false,
				resizable : false,
				href : "violate-manage/view/edit",
				modal : true,
				onLoad : function() {
					record.carNum = record.car.carNum;
					record.operateNum = record.car.operateNum;
					record.ownerName = record.car.ownerName;
					record.ownerPhone = record.car.ownerPhone;
					$("#violate-edit").form('load',record);
					$("#violate-edit").form('load',record);
					$("#violate-edit").find("input[name='carNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-edit');
					});
					$("#violate-edit").find("input[name='operateNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-edit');
					});
					$("#violate-edit").find("input[name='ownerName']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-edit');
					});
					$("#violate-edit").find("input[name='ownerPhone']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('violate-edit');
					});
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#violate-edit").form('validate')){
							var id=$.trim($("#violate-edit").find("input[name='id']").val());
							var carId = $("#violate-edit").find("input[name='carId']").val();
							var recordDate = $("#violate-edit").find("input[name='recordDate']").val();
							var hasDeal=$("#violate-has-deal").combobox('getValue');
							$.ajax({
								method : 'post',
								url : 'violate-manage/editViolate',
								data : {
									'id' : id,
									'carId' : carId,
									'recordDate' : recordDate,
									'hasDeal' : hasDeal
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#edit_violate_dialog").dialog('close');
										$("#violate-datagrid").datagrid('reload');
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
						$("#edit_violate_dialog").dialog('close');
					}
				} ]
			});
		},
		/**
		 * 删除按钮事件
		 */
		remove : function() {
			var selections = $("#violate-datagrid").datagrid('getSelections');
			if (selections.length == 0) {
				$.messager.alert('提示', '请至少选择一条记录进行操作!',"info");
				return;
			}
			var ids = [];
			for(var i = 0;i < selections.length; i++){
				ids.push(selections[i].id);
			}
			$.messager.confirm('提示', '确认要删除吗?', function(flag) {
				if (flag) {
					$.ajax({
						url : "violate-manage/deleteViolate",
						data : JSON.stringify(ids),
						method : 'post',
						dataType : 'json',
						contentType: "application/json; charset=utf-8", 
						success : function(data) {
							$("#violate-datagrid").datagrid('reload');
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
			var importDialog = $("<div id='violate-import'></div>").dialog({
			    title: '数据导入',
			    width: 400,
			    height: 160,
			    closed: false,
			    cache: false,
			    resizable : false,
			    content: '<form method="POST" enctype="multipart/form-data" action="imp-manage/import/violate"><table style="margin-top:10px;"><tr style="height:30px;"><td style="width:30%;text-align: center;">违章清单:</td><td><input id="upfile" name="upfile" type="file"/></td></tr></table></form>',
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
		                            url:'imp-manage/import/violate',  
		                            dataType: 'text',  
		                            success: function(result){
		                            	$.messager.progress('close');
		                            	$("#violate-datagrid").datagrid('reload');
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
					var carNum=$("#violate-search").find("input[name='carNum']").val().toUpperCase();
					var operateNum=$("#violate-search").find("input[name='operateNum']").val().toUpperCase();
					var hasDeal=$("#violate-hasDeal").combobox('getValue');
					window.location.href = "exp-manage/export/violate?param1="+carNum+"&param2="+operateNum+"&param3="+hasDeal;
				}
			});
		}
	};
})();
