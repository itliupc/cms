<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="Navigator" style="height:150px;" >
		<p style="padding:5px;margin:0;">Select language:</p>
		<ul>
			<li><a href="javascript:void(0)" onclick="showcontent('java')">Java</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('cshape')">C#</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('vb')">VB</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('erlang')">Erlang</a></li>
		</ul>
	</div>
	<div id="content" region="center" title="aaaa">
		<table id="tt" class="easyui-datagrid" style="height:100%"
				url="user-manage/list" toolbar="#toolbar"
				idField="userId" fitColumns="true" fit="true"
				rownumbers="true" pagination="true" border="false">
			<thead>
				<tr>
					<th field="name" width="100">Item ID</th>
					<th field="userName" width="100">Product ID</th>
					<th field="listprice" width="100" align="right">List Price</th>
					<th field="unitcost" width="100" align="right">Unit Cost</th>
					<th field="attr1" width="100">Attribute</th>
					<th field="status" width="100" align="center">Stauts</th>
					<th data-options="field:'fck', width:60, formatter:UserManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:UserManage.add()">New</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">Destroy</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow')">Save</a>
		</div>
	</div>
</div>
<div id="add_dialog"></div>
<script type="text/javascript" src="static/js/user-manage.js"></script>
