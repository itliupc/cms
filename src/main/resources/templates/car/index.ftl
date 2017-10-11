<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:120px;" >
		<form id="car-search" class="easyui-form">
			<table style="margin:15px auto;min-width:885px;">
				<tr>
					<td width='100px' align="right">
						<label for="carNum" >车号:</label>
					</td>
					<td width='200px' align="left" class="uppercase">
						<input class="easyui-textbox" type="text" name="carNum"></input>
					</td>
					<td width='100px' align="right">
						<label for="operateNum" >建运号:</label>
					</td>
					<td width='200px' align="left" class="uppercase">
						<input class="easyui-textbox" type="text" name="operateNum"></input>
					</td>
					<td  style="text-align:center"  width='100px'>
						<a href="#" style="margin-right:10px;" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:CarManage.query()">查询</a>
					</td>
					<td  style="text-align:center" width='100px'>
						<a href="#" style="margin-left:10px;" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:CarManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="car-content" region="center" title="车辆信息">
		<table id="car-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'car-manage/list',toolbar:'#car-toolbar',
				idField:'id',fitColumns:'true',fit:'true',
				rownumbers:'true',pagination:'true',border:'false'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" hidden="true"></th>
					<th field="carNum" width="60">车号</th>
					<th field="operateNum" width="70">建运号</th>
					<th field="ownerName" width="60">车主姓名</th>
					<th field="ownerPhone" width="70">联系方式</th>
					<th field="user.name" width="60" data-options="formatter: CarManage.formatOperateUser">操作员</th>
					<th data-options="field:'fck', width:60, formatter:CarManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="car-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:CarManage.add()">新增</a>
			<#if userRole == 0>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:CarManage.remove()">删除</a>
			</#if>
			<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="javascript:CarManage.importData()">数据导入</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:CarManage.exportData()">数据导出</a>
		</div>
	</div>
</div>
<div id="add_car_dialog"></div>
<div id="edit_car_dialog"></div>
<script type="text/javascript" src="static/js/car-manage.js"></script>
