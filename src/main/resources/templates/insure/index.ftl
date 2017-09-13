<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:120px;" >
		<form id="insure-search" class="easyui-form">
			<table width='800px' style="margin:15px auto">
				<tr>
					<td width='100px' align="right">
						<label for="carNum" >carNum:</label>
					</td>
					<td width='200px' align="left">
						<input class="easyui-textbox" type="text" name="carNum"></input>
					</td>
					<td width='100px' align="right">
						<label for="operateNum" >operateNum:</label>
					</td>
					<td width='200px' align="left">
						<input class="easyui-textbox" type="text" name="operateNum"></input>
					</td>
					<td  style="text-align:center"  width='100px'>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:InsureManage.query()">查询</a>
					</td>
					<td  style="text-align:center" width='100px'>
						<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:InsureManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="insure-content" region="center" title="列表区">
		<table id="insure-datagrid" class="easyui-datagrid" style="height:100%"
				url="insure-manage/list" toolbar="#insure-toolbar"
				idField="id" fitColumns="true" fit="true"
				rownumbers="true" pagination="true" border="false">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" hidden="true"></th>
					<th field="carNum" width="100">carNum</th>
					<th field="operateNum" width="100">operateNum</th>
					<th field="forceInsure" width="100">forceInsure</th>
					<th field="busInsure" width="100">busInsure</th>
					<th field="updateUser" width="100">updateUser</th>
					<th data-options="field:'fck', width:60, formatter:InsureManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="insure-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:InsureManage.add()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:InsureManage.remove()">删除</a>
		</div>
	</div>
</div>
<div id="add_insure_dialog"></div>
<div id="edit_insure_dialog"></div>
<script type="text/javascript" src="static/js/insure-manage.js"></script>
