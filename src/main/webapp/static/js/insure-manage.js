var InsureManage = (function () {
	return {
		editBtn : function(value, row, index) {
				var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"InsureManage.edit('"
						+ index + "');\">";
				button = button
						+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
				return button;
		},
		formatOutBuy : function(value, row, index) {
			if(0==value){
				return '';
			}else{
				return "是";
			}
		},
		outBuyStyle : function(value, row, index){
			if(1==value){
				return 'background-color:green;color:#fff;font-weight:bold;';
			}
		},
		formatHasReceive : function(value, row, index) {
			if(0==value){
				return '是';
			}else{
				return '';
			}
		},
		hasReceiveStyle : function(value, row, index){
			if(0==value){
				return 'background-color:#0D8CEF;color:#fff;font-weight:bold;';
			}
		},
		formatHasPay : function(value, row, index) {
			if(0==value){
				return '是';
			}else{
				return '';
			}
		},
		hasPayStyle : function(value, row, index){
			if(0==value){
				return 'background-color:yellow;color:black;font-weight:bold;';
			}
		},
		formatRowDate : function(value, row, index){
			return DateUtil.formatDatebox(value);
		},
		dateRowStyle : function(value, row, index){
			var currentDate = new Date();
			currentDate.setHours(0);
			currentDate.setMinutes(0);
			currentDate.setSeconds(0);
			currentDate.setMilliseconds(0);
			var currentTimes = currentDate.getTime();
			currentDate.setMonth(currentDate.getMonth()+2);
			var afterTimes = currentDate.getTime();
			if(!value){//日期空:白色
				return 'background-color:white;';
			}else if(currentTimes > value){//已过期:灰色
				return 'background-color:grey;color:#fff;font-weight:bold;';
			} else if(afterTimes > value){//即将过期:黄色
				return 'background-color:red;color:#fff;font-weight:bold;';
			}
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
			var carNum=$("#insure-search").find("input[name='carNum']").val().toUpperCase();
			var operateNum=$("#insure-search").find("input[name='operateNum']").val().toUpperCase();
			var deadline=$("#insure-search").find("#search-deadline").combobox('getValue');
			$("#insure-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum,'deadline':deadline});
		},
		/**
		 * 重置按钮事件
		 */
		reset: function(){
			$("#insure-search").form('load',{'carNum':'','operateNum':'','deadline':''});
			$("#insure-datagrid").datagrid('load',{'carNum':'','operateNum':'','deadline':''});
		},
		/**
		 * 新增按钮事件
		 */
		add :function(){
			$("#add_insure_dialog").dialog({
				title : '新增',
				width : 700,
				height : 240,
				closed : false,
				cache : false,
				resizable : false,
				href : "insure-manage/view/add",
				modal : true,
				onLoad : function() {
					$("#insure-add").find("input[name='carNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-add');
					});
					$("#insure-add").find("input[name='operateNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-add');
					});
					$("#insure-add").find("input[name='ownerName']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-add');
					});
					$("#insure-add").find("input[name='ownerPhone']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-add');
					});
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#insure-add").form('validate')){
							var carId = $("#insure-add").find("input[name='carId']").val();
							var forceInsure = $("#insure-add").find("input[name='forceInsure']").val();
							var busInsure = $("#insure-add").find("input[name='busInsure']").val();
							var outBuy=$("#insure-add").find("#insure-out-buy").combobox('getValue');
							var hasPay = 1; var hasReceive = 1;
							var doType=$("#insure-add").find("#insure-do-type").combobox('getValue');
							if("1" == doType){
								hasPay = 0;
							} else if("2" == doType) {
								hasReceive = 0;
							}
							$.ajax({
								method : 'post',
								url : 'insure-manage/addInsure',
								data : {
									'carId' : carId,
									'forceInsure' : forceInsure,
									'busInsure' : busInsure,
									'outBuy' : outBuy,
									'hasReceive' : hasReceive,
									'hasPay' : hasPay
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#add_insure_dialog").dialog('close');
										$("#insure-datagrid").datagrid('reload');
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
				width : 700,
				height : 240,
				closed : false,
				cache : false,
				resizable : false,
				href : "insure-manage/view/edit",
				modal : true,
				onLoad : function() {
					record.carNum = record.car.carNum;
					record.operateNum = record.car.operateNum;
					record.ownerName = record.car.ownerName;
					record.ownerPhone = record.car.ownerPhone;
					if(0 == record.hasPay){
						record.doType = 1;
					} else if(0 == record.hasReceive){
						record.doType = 2;
					} else{
						record.doType = 0;
					}
					$("#insure-edit").form('load',record);
					$("#insure-edit").find("input[name='carNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-edit');
					});
					$("#insure-edit").find("input[name='operateNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-edit');
					});
					$("#insure-edit").find("input[name='ownerName']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-edit');
					});
					$("#insure-edit").find("input[name='ownerPhone']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('insure-edit');
					});
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#insure-edit").form('validate')){
							var id=$.trim($("#insure-edit").find("input[name='id']").val());
							var carId=$.trim($("#insure-edit").find("input[name='carId']").val());
							var forceInsure = $("#insure-edit").find("input[name='forceInsure']").val();
							var busInsure = $("#insure-edit").find("input[name='busInsure']").val();
							var outBuy=$("#insure-edit").find("#insure-out-buy").combobox('getValue');
							var hasPay = 1; var hasReceive = 1;
							var doType=$("#insure-edit").find("#insure-do-type").combobox('getValue');
							if("1" == doType){
								hasPay = 0;
							} else if("2" == doType) {
								hasReceive = 0;
							}
							$.ajax({
								method : 'post',
								url : 'insure-manage/editInsure',
								data : {
									'id' : id,
									'carId' : carId,
									'forceInsure' : forceInsure,
									'busInsure' : busInsure,
									'outBuy' : outBuy,
									'hasReceive' : hasReceive,
									'hasPay' : hasPay
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#edit_insure_dialog").dialog('close');
										$("#insure-datagrid").datagrid('reload');
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
						url : "insure-manage/deleteInsure",
						data : JSON.stringify(ids),
						method : 'post',
						dataType : 'json',
						contentType: "application/json; charset=utf-8", 
						success : function(data) {
							$("#insure-datagrid").datagrid('reload');
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
			var importDialog = $("<div id='insure-import'></div>").dialog({
			    title: '数据导入',
			    width: 400,
			    height: 160,
			    closed: false,
			    cache: false,
			    resizable : false,
			    content: '<form method="POST" enctype="multipart/form-data" action="imp-manage/import/insure"><table style="margin-top:10px;"><tr style="height:30px;"><td style="width:30%;text-align: center;">保险清单:</td><td><input id="upfile" name="upfile" type="file"/></td></tr></table></form>',
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
		                            url:'imp-manage/import/insure',  
		                            dataType: 'text',  
		                            success: function(insureImportResult){
		                            	$.messager.progress('close');
		                            	$("#insure-datagrid").datagrid('reload');
		                            	if(insureImportResult && insureImportResult.length > 0){
		                            		if(insureImportResult.indexOf("导航菜单") > 0){
		                            			$.messager.alert('提示','Excel导入成功！',"info");
		                            		}else{
		                            			$.messager.alert('提示',insureImportResult+"对应建运号数据错误。");
		                            		}
		                            	}else{
		                            		$.messager.alert('提示','Excel导入成功！',"info");
		                            	}
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
					var carNum=$("#insure-search").find("input[name='carNum']").val().toUpperCase();
					var operateNum=$("#insure-search").find("input[name='operateNum']").val().toUpperCase();
					var deadline=$("#search-deadline").combobox('getValue');
					window.location.href = "exp-manage/export/insure?param1="+carNum+"&param2="+operateNum+"&param3="+deadline;
				}
			});
		}
	};
})();
