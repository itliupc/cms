var ManageManage = (function () {
	return {
		editBtn : function(value, row, index) {
				var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"编辑\" data-options=\"iconCls:'icon-edit',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"ManageManage.edit('"
						+ index + "');\">";
				button = button
						+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-edit l-btn-icon-left\">编辑</span></span></a>";
				return button;
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
			var carNum=$("#manage-search").find("input[name='carNum']").val().toUpperCase();
			var operateNum=$("#manage-search").find("input[name='operateNum']").val().toUpperCase();
			var deadline=$("#manage-deadline").combobox('getValue');
			$("#manage-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum,'deadline':deadline});
		},
		/**
		 * 重置按钮事件
		 */
		reset: function(){
			$("#manage-search").form('load',{'carNum':'','operateNum':'','deadline':'0'});
			$("#manage-datagrid").datagrid('load',{'carNum':'','operateNum':'','deadline':'0'});
		},
		/**
		 * 新增按钮事件
		 */
		add :function(){
			$("#add_manage_dialog").dialog({
				title : '新增',
				width : 700,
				height : 190,
				closed : false,
				cache : false,
				resizable : false,
				href : "manage-manage/view/add",
				modal : true,
				onLoad : function() {
					$("#manage-add").find("input[name='carNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-add');
					});
					$("#manage-add").find("input[name='operateNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-add');
					});
					$("#manage-add").find("input[name='ownerName']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-add');
					});
					$("#manage-add").find("input[name='ownerPhone']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-add');
					});
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#manage-add").form('validate')){
							var carId = $("#manage-add").find("input[name='carId']").val();
							var endDate = $("#manage-add").find("input[name='endDate']").val();
							$.ajax({
								method : 'post',
								url : 'manage-manage/addManage',
								data : {
									'carId' : carId,
									'endDate' : endDate
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#add_manage_dialog").dialog('close');
										$("#manage-datagrid").datagrid('reload');
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
						$("#add_manage_dialog").dialog('close');
					}
				} ]
			});
		},
		/**
		 * 编辑按钮事件
		 */
		edit :function(index){
			var record = $("#manage-datagrid").datagrid('getRows')[index];
			$("#edit_manage_dialog").dialog({
				title : '编辑',
				width : 700,
				height : 190,
				closed : false,
				cache : false,
				resizable : false,
				href : "manage-manage/view/edit",
				modal : true,
				onLoad : function() {
					record.carNum = record.car.carNum;
					record.operateNum = record.car.operateNum;
					record.ownerName = record.car.ownerName;
					record.ownerPhone = record.car.ownerPhone;
					$("#manage-edit").form('load',record);
					$("#manage-edit").form('load',record);
					$("#manage-edit").find("input[name='carNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-edit');
					});
					$("#manage-edit").find("input[name='operateNum']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-edit');
					});
					$("#manage-edit").find("input[name='ownerName']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-edit');
					});
					$("#manage-edit").find("input[name='ownerPhone']").parent().unbind('click').bind('click',function(){
						CommonUtil.carSelect('manage-edit');
					});
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#manage-edit").form('validate')){
							var id=$.trim($("#manage-edit").find("input[name='id']").val());
							var carId = $("#manage-edit").find("input[name='carId']").val();
							var endDate = $("#manage-edit").find("input[name='endDate']").val();
							$.ajax({
								method : 'post',
								url : 'manage-manage/editManage',
								data : {
									'id' : id,
									'carId' : carId,
									'endDate' : endDate
								},
								async : false,
								success : function(data) {
									if(data.result){
										$("#edit_manage_dialog").dialog('close');
										$("#manage-datagrid").datagrid('reload');
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
						$("#edit_manage_dialog").dialog('close');
					}
				} ]
			});
		},
		/**
		 * 删除按钮事件
		 */
		remove : function() {
			var selections = $("#manage-datagrid").datagrid('getSelections');
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
						url : "manage-manage/deleteManage",
						data : JSON.stringify(ids),
						method : 'post',
						dataType : 'json',
						contentType: "application/json; charset=utf-8", 
						success : function(data) {
							$("#manage-datagrid").datagrid('reload');
							$.messager.alert('提示','删除成功！',"info");
						}
					});
				}
			});
		},
		/**
		 * 数据导出
		 */
		exportData : function(){
			$.messager.confirm('提示', '确认要导出数据吗?', function(flag) {
				if (flag) {
					var carNum=$("#manage-search").find("input[name='carNum']").val().toUpperCase();
					var operateNum=$("#manage-search").find("input[name='operateNum']").val().toUpperCase();
					var deadline=$("#manage-deadline").combobox('getValue');
					window.location.href = "exp-manage/export/manage?param1="+carNum+"&param2="+operateNum+"&param3="+deadline;
				}
			});
		}
	};
})();
