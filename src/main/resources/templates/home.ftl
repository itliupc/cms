<div class="easyui-layout" style="height:100%;">
	<div region="north" split="false" border="false" style="overflow:hidden;height:197px;" >
		<div class="cui-table-container" style="overflow:hidden;margin-bottom: 0px;">
			<div class="head">常用功能</div>
			<div style="height: 104px; overflow: auto;">
				<div class="cui-kj-list">
					<div class="item">
						<img src="static/images/icon-01.png" onclick="openTab('insure-dashed')">
						<div class="item-label">车险管理</div>
					</div>
					<div class="item">
						<img src="static/images/icon-02.png" onclick="openTab('violate-dashed')">
						<div class="item-label">违章管理</div>
					</div>
					<div class="item">
						<img src="static/images/icon-03.png" onclick="openTab('gps-dashed')">
						<div class="item-label">GPS管理</div>
					</div>
					<div class="item">
						<img src="static/images/icon-04.png" onclick="openTab('operate-dashed')">
						<div class="item-label">营运管理</div>
					</div>
					<div class="item">
						<img src="static/images/icon-05.png" onclick="openTab('exam-dashed')">
						<div class="item-label">审车管理</div>
					</div>
					<div class="item">
						<img src="static/images/icon-06.png" onclick="openTab('manage-dashed')">
						<div class="item-label">管理费用</div>
					</div>
				</div>
			</div>
		</div>
		<div class="cui-table-container" style="overflow:hidden;border-bottom: none;margin-bottom:0px;">
			<div class="head" style="margin-bottom: 0px;margin-right:0px;">车辆汇总</div>
		</div>
	</div>
	<div id="home-content" region="center" border="false">
		<div style="width:99%;min-height:150px; height:100%;margin-left: 5px;margin-right: 5px;">
			<table id="home-datagrid" class="easyui-datagrid" style="height:100%;width:98%;"
				data-options="url:'home-manage/list',toolbar:'#home-toolbar',
					idField:'id',fitColumns:'true',fit:'true',
					rownumbers:'true',pagination:'true',border:'false'">
				<thead>
					<tr>
						<th field="id" hidden="true"></th>
						<th field="carNum" width="60">车号</th>
						<th field="operateNum" width="60">建运号</th>
						<th field="ownerName" width="60">车主姓名</th>
						<th data-options="field:'fck', width:20, formatter:HomeManage.operateBtn" align="left">操作</th>
					</tr>
				</thead>
			</table>
			<div id="home-toolbar">
				<input id="search-box" name="searchBox" class="search-box" placeholder="请输入车号、建运号、车主姓名" onkeydown="if(event.keyCode==13){HomeManage.query();}" />
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:HomeManage.query()">查询</a>
			</div>
		</div>
	</div>
</div>

