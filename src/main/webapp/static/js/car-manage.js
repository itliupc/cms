var InsureManage = (function () {
	return {
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
		}
	};
})();
