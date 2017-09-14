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
				return '否';
			}else{
				return '是';
			}
		},
		formatHasReceive : function(value, row, index) {
			if(0==value){
				return '未领取';
			}else{
				return '已领取';
			}
		},
		formatHasPay : function(value, row, index) {
			if(0==value){
				return '未缴费';
			}else{
				return '已缴费';
			}
		},
		formatRow : function(index,row){
			var currentDate = new Date();
			currentDate.setHours(0);
			currentDate.setMinutes(0);
			currentDate.setSeconds(0);
			currentDate.setMilliseconds(0);
			var currentTimes = currentDate.getTime();
			currentDate.setMonth(currentDate.getMonth()+3);
			var afterTimes = currentDate.getTime();
			if(!row.forceInsure || !row.busInsure){//已过期:红色
				return 'background-color:red;color:#fff;font-weight:bold;';
			} else if(1 == row.outBuy){//外面购买：绿色
				return 'background-color:green;color:#fff;font-weight:bold;';
			} else if(0 == row.hasReceive){//未取：蓝色
				return 'background-color:#0D8CEF;color:#fff;font-weight:bold;';
			} else if(0 == row.hasPay){//未缴费：
				return 'background-color:#CC8CEF;color:#fff;font-weight:bold;';
			} else if(currentTimes > row.forceInsure || currentTimes > row.busInsure){//已过期:红色
				return 'background-color:red;color:#fff;font-weight:bold;';
			} else if(afterTimes > row.forceInsure || afterTimes > row.busInsure){//即将过期:黄色
				return 'background-color:yellow;color:#fff;font-weight:bold;';
			}
		},
		/**
		 * 查询按钮事件
		 */
		query: function(){
			var carNum=$("#insure-search").find("input[name='carNum']").val().toUpperCase();
			var operateNum=$("#insure-search").find("input[name='operateNum']").val().toUpperCase();
			var outBuy=$("#search-out-buy").combobox('getValue');
			var deadline=$("#search-deadline").combobox('getValue');
			$("#insure-datagrid").datagrid('load',{'carNum':carNum,'operateNum':operateNum,'outBuy':outBuy,'deadline':deadline});
		},
		/**
		 * 重置按钮事件
		 */
		reset: function(){
			$("#insure-search").form('load',{'carNum':'','operateNum':'','outBuy':'','deadline':''});
			$("#insure-datagrid").datagrid('load',{'carNum':'','operateNum':'','outBuy':'','deadline':''});
		},
		/**
		 * 新增按钮事件
		 */
		add :function(){
			$("#add_insure_dialog").dialog({
				title : '新增',
				width : 600,
				height : 440,
				closed : false,
				cache : false,
				resizable : false,
				href : "insure-manage/view/add",
				modal : true,
				onLoad : function() {
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#insure-add").form('validate')){
							var carNum=$.trim($("#insure-add").find("input[name='carNum']").val().toUpperCase());
							var operateNum=$.trim($("#insure-add").find("input[name='operateNum']").val().toUpperCase());
							var forceInsure = $("#insure-add").find("input[name='forceInsure']").val();
							var busInsure = $("#insure-add").find("input[name='busInsure']").val();
							var outBuy=$("#insure-out-buy").combobox('getValue');
							var hasReceive=$("#insure-has-receive").combobox('getValue');
							var hasPay=$("#insure-has-pay").combobox('getValue');
							$.ajax({
								method : 'post',
								url : 'insure-manage/addInsure',
								data : {
									'carNum' : carNum,
									'operateNum' : operateNum,
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
				width : 600,
				height : 440,
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
						if($("#insure-edit").form('validate')){
							var id=$.trim($("#insure-edit").find("input[name='id']").val());
							var carNum=$.trim($("#insure-edit").find("input[name='carNum']").val().toUpperCase());
							var operateNum=$.trim($("#insure-edit").find("input[name='operateNum']").val().toUpperCase());
							var forceInsure = $("#insure-edit").find("input[name='forceInsure']").val();
							var busInsure = $("#insure-edit").find("input[name='busInsure']").val();
							var outBuy=$("#insure-out-buy").combobox('getValue');
							var hasReceive=$("#insure-has-receive").combobox('getValue');
							var hasPay=$("#insure-has-pay").combobox('getValue');
							$.ajax({
								method : 'post',
								url : 'insure-manage/editInsure',
								data : {
									'id' : id,
									'carNum' : carNum,
									'operateNum' : operateNum,
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
		},
		/**
		 * 数据导入
		 */
		importData : function(){
			$.messager.alert('提示','待开发... ...');
		}
	};
})();
